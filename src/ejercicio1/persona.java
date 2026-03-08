package ejercicio1;

public class persona {

	public static abstract class Personaje {
		protected String nombre;
		protected int vida;
		protected int vidaMax;
		protected int ataque;
		protected int defensa;
		protected int mana;
		protected int manaMax;
		protected Armas arma;
		protected Estado[] estados = new Estado[5];
		protected int numEstados = 0;

		public Personaje(String nombre, int vida, int ataque, int defensa, int mana) {
			this.nombre = nombre;
			this.vida = vida;
			this.vidaMax = vida;
			this.ataque = ataque;
			this.defensa = defensa;
			this.mana = mana;
			this.manaMax = mana;
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
				real = 1;
			vida -= real;
			if (vida < 0)
				vida = 0;
		}

		public void regenerarMana() {
			mana += 3;
			if (mana > manaMax)
				mana = manaMax;
		}

		public void procesarEstados() {
			int i = 0;
			while (i < numEstados) {
				estados[i].aplicarEfecto(this);
				if (estados[i].turnosRestantes <= 0) {
					estados[i] = estados[numEstados - 1];
					estados[numEstados - 1] = null;
					numEstados--;
				} else {
					i++;
				}
			}
		}

		protected void pegarA(Personaje objetivo) {
			if (objetivo == null || !objetivo.estaVivo() || arma == null)
				return;
			System.out.println(nombre + " ataca a " + objetivo.nombre);
			int danio = arma.calcularDanio(this, objetivo);
			objetivo.recibirDanio(danio);
			System.out.println("   -> " + objetivo.nombre + " queda con vida " + objetivo.vida);
		}

		protected Personaje primerVivo(Personaje[] equipo) {
			for (int i = 0; i < equipo.length; i++) {
				if (equipo[i] != null && equipo[i].estaVivo())
					return equipo[i];
			}
			return null;
		}

		protected Personaje enemigoRandom(Personaje[] equipo) {
			int vivos = 0;

			for (int i = 0; i < equipo.length; i++) {
				if (equipo[i] != null && equipo[i].estaVivo()) {
					vivos++;
				}
			}

			if (vivos == 0) {
				return null;
			}

			int elegido = (int) (Math.random() * vivos);
			int contador = 0;

			for (int i = 0; i < equipo.length; i++) {
				if (equipo[i] != null && equipo[i].estaVivo()) {
					if (contador == elegido) {
						return equipo[i];
					}
					contador++;
				}
			}

			return null;
		}

		protected int contarVivos(Personaje[] equipo) {
			int c = 0;
			for (int i = 0; i < equipo.length; i++) {
				if (equipo[i] != null && equipo[i].estaVivo())
					c++;
			}
			return c;
		}

		public abstract void hacerTurno(Personaje[] enemigos, Personaje[] aliados);
	}

	public static class Jedi extends Personaje {

		public Jedi(String nombre) {
			super(nombre, 130, 24, 8, 20);
			this.arma = new Armas.SableLuz();
		}

		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			int vivos = contarVivos(enemigos);
			if (vivos >= 2 && mana >= 10) {
				mana -= 10;
				new hechizos.EmpujonFuerza().lanzar(this, enemigos);
			} else if (mana >= 10) {
				mana -= 10;
				new hechizos.Aplastamiento().lanzar(this, enemigos);
			} else if (mana >= 8) {
				mana -= 8;
				new hechizos.Telequinesis().lanzar(this, enemigos);
			} else {
				System.out.println(nombre + " usa sable de luz verde");
				pegarA(enemigoRandom(enemigos));
			}
		}
	}

	public static class SoldadoRebelde extends Personaje {

		public SoldadoRebelde(String nombre) {
			super(nombre, 110, 22, 4, 0);
			this.arma = new Armas.Blaster();
		}

		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			System.out.println(nombre + " dispara blaster");
			pegarA(enemigoRandom(enemigos));
		}
	}

	public static class Sanador extends Personaje {

		public Sanador(String nombre) {
			super(nombre, 100, 10, 4, 35);
			this.arma = new Armas.Blaster();
		}

		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			Personaje aliado = aliadoMasDebil(aliados);

			if (aliado != null && aliado.vida < 40 && mana >= 12) {
				mana -= 12;
				System.out.println(nombre + " ha curado a " + aliado.nombre);
				if (aliado.numEstados < aliado.estados.length) {
					aliado.estados[aliado.numEstados] = new Senadora();
					aliado.numEstados++;
				}
			} else {
				System.out.println(nombre + " golpea blaster");
				pegarA(enemigoRandom(enemigos));
			}
		}

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

	public static class Sith extends Personaje {

		public Sith(String nombre) {
			super(nombre, 130, 26, 7, 25);
			this.arma = new Armas.SableLuz();
		}

		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			int vivos = contarVivos(enemigos);
			if (vivos >= 2 && mana >= 10) {
				mana -= 10;
				new hechizos.EmpujonFuerza().lanzar(this, enemigos);
			} else if (mana >= 12) {
				mana -= 12;
				new hechizos.Estrangulamiento().lanzar(this, enemigos);
			} else if (mana >= 10) {
				mana -= 10;
				new hechizos.Aplastamiento().lanzar(this, enemigos);
			} else {
				System.out.println(nombre + " ataca con sable rojo");
				pegarA(enemigoRandom(enemigos));
			}
		}
	}

	public static class SoldadoImperial extends Personaje {

		public SoldadoImperial(String nombre) {
			super(nombre, 110, 21, 5, 0);
			this.arma = new Armas.Blaster();
		}

		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			System.out.println(nombre + " dispara");
			pegarA(enemigoRandom(enemigos));
		}
	}

	public static class Cazarrecompensas extends Personaje {

		public Cazarrecompensas(String nombre) {
			super(nombre, 105, 21, 4, 30);
			this.arma = new Armas.Blaster();
		}

		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			Personaje obj = enemigoRandom(enemigos);
			if (obj == null)
				return;

			if (mana >= 12) {
				mana -= 12;
				System.out.println(nombre + " lanza quemadura a " + obj.nombre + " (-12 mana)");
				obj.recibirDanio(10);
				if (obj.numEstados < obj.estados.length) {
					obj.estados[obj.numEstados] = new Quemaduras();
					obj.numEstados++;
				}
			} else {
				System.out.println(nombre + " dispara");
				pegarA(obj);
			}
		}
	}
}