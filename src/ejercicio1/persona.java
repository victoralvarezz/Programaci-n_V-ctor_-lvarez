package ejercicio1;

public class persona {

	public static abstract class Personaje {
		protected String nombre;
		protected int vida;
		protected int vidaMax; // para que Renovar no cure de más
		protected int ataque;
		protected int defensa;
		protected int mana;
		protected int manaMax; // para que no sume mucho mana
		// array de estados activos
		protected Estado[] estados = new Estado[5];
		protected int numEstados = 0;

		public Personaje(String nombre, int vida, int ataque, int defensa, int mana) {
			this.nombre = nombre;
			this.vida = vida;
			this.vidaMax = vida; // guarda el máximo al crear
			this.ataque = ataque;
			this.defensa = defensa;
			this.mana = mana;
			this.manaMax = mana; // guarda el máximo al crear
		}

		public void mostrarInfo() {
			System.out.println(nombre + " | Vida: " + vida + "/" + vidaMax + " | Atk: " + ataque + " | Def: " + defensa
					+ " | Mana: " + mana + "/" + manaMax);
		}

		public boolean estaVivo() {
			return vida > 0;
		}

		public void recibirDanio(int danio) {
			int real = danio - defensa;
			if (real < 1)
				real = 1; // siempre hace algo de daño
			vida -= real;
			if (vida < 0)
				vida = 0;
		}

		// regenera 3 de mana al final de cada ronda
		public void regenerarMana() {
			mana += 3;
			if (mana > manaMax) mana = manaMax; // no superar el máximo
		}

		// procesa todos los estados al final de cada ronda
		public void procesarEstados() {
			int i = 0;
			while (i < numEstados) {
				estados[i].aplicarEfecto(this);
				if (estados[i].turnosRestantes <= 0) {
					System.out.println("  [" + estados[i].nombre + "] ha expirado en " + nombre);
					estados[i] = estados[numEstados - 1];
					estados[numEstados - 1] = null;
					numEstados--;
				} else {
					i++;
				}
			}
		}

		// ataque a 1 enemigo
		protected void pegarA(Personaje objetivo, int danioBase) {
			if (objetivo == null || !objetivo.estaVivo())
				return;
			System.out.println(nombre + " ataca a " + objetivo.nombre);
			objetivo.recibirDanio(danioBase);
			System.out.println("   -> " + objetivo.nombre + " queda con vida " + objetivo.vida);
		}

		// ataque a todos (área)
		protected void pegarATodos(Personaje[] enemigos, int danioBase) {
			System.out.println(nombre + " usa ataque en área");
			for (int i = 0; i < enemigos.length; i++) {
				if (enemigos[i] != null && enemigos[i].estaVivo()) {
					enemigos[i].recibirDanio(danioBase);
					System.out.println("   -> " + enemigos[i].nombre + " queda con vida " + enemigos[i].vida);
				}
			}
		}

		// devuelve el primer personaje vivo del equipo
		protected Personaje primerVivo(Personaje[] equipo) {
			for (int i = 0; i < equipo.length; i++) {
				if (equipo[i] != null && equipo[i].estaVivo())
					return equipo[i];
			}
			return null;
		}

		// cuenta cuántos vivos hay en un equipo
		protected int contarVivos(Personaje[] equipo) {
			int c = 0;
			for (int i = 0; i < equipo.length; i++) {
				if (equipo[i] != null && equipo[i].estaVivo())
					c++;
			}
			return c;
		}

		// cada personaje decide su turno
		public abstract void hacerTurno(Personaje[] enemigos, Personaje[] aliados);
	}

	// JEDI = Guerrero (tanque + daño estable)
	// Ejemplo nombre: "Yoda"
	public static class Jedi extends Personaje {

		public Jedi(String nombre) {
			super(nombre, 130, 24, 8, 20); // vida alta y defensa alta
		}

		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			int vivos = contarVivos(enemigos);
			// si hay 2+ enemigos usa empujón (área)
			if (vivos >= 2 && mana >= 10) {
				mana -= 10;
				new hechizos.EmpujonFuerza().lanzar(this, enemigos);
			} else if (mana >= 10) {
				// si hay 1 enemigo usa aplastamiento
				mana -= 10;
				new hechizos.Aplastamiento().lanzar(this, enemigos);
			} else if (mana >= 8) {
				// si tiene poco mana usa telequinesis
				mana -= 8;
				new hechizos.Telequinesis().lanzar(this, enemigos);
			} else {
				// si no tiene mana, sable normal
				System.out.println(nombre + " usa sable de luz verde");
				pegarA(primerVivo(enemigos), ataque);
			}
		}
	}

	// SOLDADO REBELDE = daño a distancia constante
	// Ejemplo nombre: "Han Solo"
	public static class SoldadoRebelde extends Personaje {

		public SoldadoRebelde(String nombre) {
			super(nombre, 110, 22, 4, 0);
		}

		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			// disparo normal
			System.out.println(nombre + " dispara blaster");
			pegarA(primerVivo(enemigos), ataque);
		}
	}

	// SANADOR = Sacerdote (cura al aliado más débil)
	// Seria "Leia"
	public static class Sanador extends Personaje {

		public Sanador(String nombre) {
			super(nombre, 115, 10, 5, 40); // mana alto para curar
		}

		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			Personaje aliado = aliadoMasDebil(aliados);
			// si alguien tiene poca vida -> aplica Renovar
			if (aliado != null && aliado.vida < 70 && mana >= 12) {
				mana -= 12;
				System.out.println(nombre + " aplica Renovar a " + aliado.nombre);
				aliado.estados[aliado.numEstados] = new Senadora();
				aliado.numEstados++;
			} else {
				// si no, hace daño 10
				System.out.println(nombre + " golpea débilmente");
				pegarA(primerVivo(enemigos), 10);
			}
		}

		// busca el aliado vivo con menos vida
		private Personaje aliadoMasDebil(Personaje[] aliados) {
			Personaje min = null;
			for (int i = 0; i < aliados.length; i++) {
				if (aliados[i] != null && aliados[i].estaVivo()) {
					if (min == null || aliados[i].vida < min.vida) {
						min = aliados[i];
					}
				}
			}
			return min;
		}
	}

	// SITH = Guerrero oscuro ataque a dos personas
	// Ejemplo nombre: "Darth Vader" (usa hechizos)
	public static class Sith extends Personaje {

		public Sith(String nombre) {
			super(nombre, 125, 26, 7, 25);
		}

		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			int vivos = contarVivos(enemigos);
			// si hay 2+ enemigos usa empujón (área)
			if (vivos >= 2 && mana >= 10) {
				mana -= 10;
				new hechizos.EmpujonFuerza().lanzar(this, enemigos);
			} else if (mana >= 10) {
				// si hay 1 enemigo usa estrangulamiento
				mana -= 10;
				new hechizos.Estrangulamiento().lanzar(this, enemigos);
			} else {
				// si no tiene mana, sable rojo
				System.out.println(nombre + " ataca con sable rojo");
				pegarA(primerVivo(enemigos), ataque);
			}
		}
	}

	// SOLDADO IMPERIAL = disparo simple
	// Ejemplo nombre: "Stormtrooper"
	public static class SoldadoImperial extends Personaje {

		public SoldadoImperial(String nombre) {
			super(nombre, 110, 20, 5, 0);
		}

		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			System.out.println(nombre + " dispara");
			pegarA(primerVivo(enemigos), ataque);
		}
	}

	// CAZARRECOMPENSAS = mago
	// Ejemplo nombre: "Boba Fett"
	// nota: quema -5 por turno durante 3 turnos
	public static class Cazarrecompensas extends Personaje {

		public Cazarrecompensas(String nombre) {
			super(nombre, 100, 18, 4, 30);
		}

		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			Personaje obj = primerVivo(enemigos);
			if (obj == null) return;
			// habilidad especial (quemadura) si tiene mana
			if (mana >= 12) {
				mana -= 12;
				System.out.println(nombre + " lanza quemadura a " + obj.nombre + " (-12 mana)");
				obj.estados[obj.numEstados] = new Quemaduras();
				obj.numEstados++;
			} else {
				// si no tiene mana, dispara normal
				System.out.println(nombre + " dispara");
				pegarA(obj, ataque);
			}
		}
	}
}