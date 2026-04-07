package ejercicio1;

/**
 * Clase contenedora de todas las clases de personaje del juego. Contiene la
 * clase abstracta base Personaje y todas sus subclases: Jedi, Sith,
 * SoldadoRebelde, SoldadoImperial, Cazarrecompensas y Sanador.
 *
 * @author Victor
 * @version 1.0
 */
public class persona {

	/**
	 * Clase abstracta que representa cualquier personaje jugable o enemigo. Define
	 * los atributos comunes y los metodos que el motor de combate necesita invocar.
	 * Cada subclase define su propio comportamiento en hacerTurno.
	 *
	 * @author Victor
	 * @version 1.0
	 */
	public static abstract class Personaje {

		/** Nombre del personaje que se muestra en combate. */
		protected String nombre;

		/** Vida actual del personaje. Nunca baja de 0. */
		protected int vida;

		/** Vida maxima del personaje. */
		protected int vidaMax;

		/** Estadistica de ataque, usada en el calculo de daño del arma. */
		protected int ataque;

		/** Estadistica de defensa, reduce el daño recibido en ataques fisicos. */
		protected int defensa;

		/** Mana actual del personaje. */
		protected int mana;

		/** Mana maximo del personaje. */
		protected int manaMax;

		/** Arma equipada por el personaje. */
		protected Armas arma;

		/** Array de estados activos. Maximo 5 simultaneos. */
		protected Estado[] estados = new Estado[5];

		/** Numero de estados activos actualmente. */
		protected int numEstados = 0;

		/**
		 * Constructor de Personaje.
		 *
		 * @param nombre  nombre del personaje
		 * @param vida    vida maxima inicial
		 * @param ataque  estadistica de ataque
		 * @param defensa estadistica de defensa
		 * @param mana    mana maximo inicial
		 */
		public Personaje(String nombre, int vida, int ataque, int defensa, int mana) {
			this.nombre = nombre;
			this.vida = vida;
			this.vidaMax = vida;
			this.ataque = ataque;
			this.defensa = defensa;
			this.mana = mana;
			this.manaMax = mana;
		}

		/**
		 * Comprueba si el personaje sigue vivo en combate.
		 *
		 * @return true si vida mayor que 0, false si esta eliminado
		 */
		public boolean estaVivo() {
			return vida > 0;
		}

		/**
		 * Aplica daño al personaje restando primero la defensa. El daño minimo aplicado
		 * es 1. La vida nunca baja de 0.
		 *
		 * @param danio cantidad de daño bruto recibido
		 */
		public void recibirDanio(int danio) {
			int real = danio - defensa;
			if (real < 1)
				real = 1;
			vida -= real;
			if (vida < 0)
				vida = 0;
		}

		/**
		 * Regenera 3 puntos de mana al final de cada ronda. No supera el mana maximo
		 * del personaje.
		 */
		public void regenerarMana() {
			mana += 3;
			if (mana > manaMax)
				mana = manaMax;
		}

		/**
		 * Aplica un nuevo estado respetando la politica de stacking. Si ya existe un
		 * estado con el mismo nombre, se renueva su duracion. Si no existe y hay hueco,
		 * se añade al array.
		 *
		 * @param nuevoEstado el estado a aplicar
		 */
		public void aplicarEstado(Estado nuevoEstado) {
			for (int i = 0; i < numEstados; i++) {
				if (estados[i].nombre.equals(nuevoEstado.nombre)) {
					estados[i].turnosRestantes = nuevoEstado.turnosRestantes;
					System.out.println("  [" + nuevoEstado.nombre + "] renovado en " + nombre + " ("
							+ estados[i].turnosRestantes + " turnos)");
					return;
				}
			}
			if (numEstados < estados.length) {
				estados[numEstados] = nuevoEstado;
				numEstados++;
				System.out.println("  [" + nuevoEstado.nombre + "] aplicado a " + nombre);
			}
		}

		/**
		 * Procesa todos los estados activos al final de la ronda. Aplica el efecto de
		 * cada estado, reduce su duracion y elimina los expirados.
		 */
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

		/**
		 * Selecciona un enemigo vivo al azar del equipo dado.
		 *
		 * @param eq array del equipo enemigo
		 * @return un Personaje vivo aleatorio, o null si no hay ninguno
		 */
		protected Personaje enemigoRandom(Personaje[] eq) {
			int vivos = 0;
			for (Personaje p : eq)
				if (p != null && p.estaVivo())
					vivos++;
			if (vivos == 0)
				return null;
			int elegido = (int) (Math.random() * vivos);
			int cont = 0;
			for (Personaje p : eq) {
				if (p != null && p.estaVivo()) {
					if (cont == elegido)
						return p;
					cont++;
				}
			}
			return null;
		}

		/**
		 * Cuenta cuantos personajes siguen vivos en un equipo.
		 *
		 * @param eq array del equipo a contar
		 * @return numero de personajes vivos
		 */
		protected int contarVivos(Personaje[] eq) {
			int c = 0;
			for (Personaje p : eq)
				if (p != null && p.estaVivo())
					c++;
			return c;
		}

		/**
		 * Ataque basico usando el arma equipada.
		 *
		 * @param obj personaje objetivo del ataque
		 */
		public void ataqueBasico(Personaje obj) {
			if (obj == null || !obj.estaVivo())
				return;
			int danio = arma.calcularDanio(this, obj);
			System.out.println(nombre + " ataca a " + obj.nombre);
			obj.recibirDanio(danio);
			System.out.println("   -> " + obj.nombre + " queda con vida " + obj.vida);
		}

		/**
		 * Usa Empujon de la Fuerza si tiene mana. Coste: 10 mana.
		 * 
		 * @param enemigos array de personajes enemigos
		 */
		public void usarEmpujon(Personaje[] enemigos) {
			if (mana >= 10) {
				mana -= 10;
				new hechizos.EmpujonFuerza().lanzar(this, enemigos);
			} else
				System.out.println("No tiene mana suficiente");
		}

		/**
		 * Usa Aplastamiento si tiene mana. Coste: 10 mana.
		 * 
		 * @param obj personaje enemigo objetivo
		 */
		public void usarAplastamiento(Personaje obj) {
			if (mana >= 10) {
				mana -= 10;
				new hechizos.Aplastamiento().lanzar(this, new Personaje[] { obj });
			} else
				System.out.println("No tiene mana suficiente");
		}

		/**
		 * Usa Telequinesis si tiene mana. Coste: 8 mana.
		 * 
		 * @param obj personaje enemigo objetivo
		 */
		public void usarTelequinesis(Personaje obj) {
			if (mana >= 8) {
				mana -= 8;
				new hechizos.Telequinesis().lanzar(this, new Personaje[] { obj });
			} else
				System.out.println("No tiene mana suficiente");
		}

		/**
		 * Usa Estrangulamiento si tiene mana. Coste: 12 mana.
		 * 
		 * @param obj personaje enemigo objetivo
		 */
		public void usarEstrangulamiento(Personaje obj) {
			if (mana >= 12) {
				mana -= 12;
				new hechizos.Estrangulamiento().lanzar(this, new Personaje[] { obj });
			} else
				System.out.println("No tiene mana suficiente");
		}

		/**
		 * Usa Curacion si tiene mana. Restaura 25 de vida a un aliado. Coste: 10 mana.
		 * 
		 * @param aliado personaje aliado a curar
		 */
		public void usarCuracion(Personaje aliado) {
			if (mana >= 10) {
				mana -= 10;
				new hechizos.Curacion().lanzar(this, new Personaje[] { aliado });
			} else
				System.out.println("No tiene mana suficiente");
		}

		/**
		 * Usa Renovar si tiene mana. Aplica curacion por turnos. Coste: 12 mana.
		 * 
		 * @param aliado personaje aliado a curar
		 */
		public void usarRenovar(Personaje aliado) {
			if (mana >= 12) {
				mana -= 12;
				new hechizos.LanzarRenovar().lanzar(this, new Personaje[] { aliado });
			} else
				System.out.println("No tiene mana suficiente");
		}

		/**
		 * Usa Quemadura si tiene mana. Daño inmediato mas estado DoT. Coste: 12 mana.
		 * 
		 * @param obj personaje enemigo objetivo
		 */
		public void usarQuemadura(Personaje obj) {
			if (mana >= 12) {
				mana -= 12;
				new hechizos.LanzarQuemadura().lanzar(this, new Personaje[] { obj });
			} else
				System.out.println("No tiene mana suficiente");
		}

		/**
		 * Usa Veneno si tiene mana. Daño inmediato mas estado DoT. Coste: 10 mana.
		 * 
		 * @param obj personaje enemigo objetivo
		 */
		public void usarVeneno(Personaje obj) {
			if (mana >= 10) {
				mana -= 10;
				new hechizos.LanzarVeneno().lanzar(this, new Personaje[] { obj });
			} else
				System.out.println("No tiene mana suficiente");
		}

		/**
		 * Define el comportamiento del personaje en su turno. Cada subclase implementa
		 * su propia logica.
		 *
		 * @param enemigos array del equipo enemigo
		 * @param aliados  array del equipo aliado
		 */
		public abstract void hacerTurno(Personaje[] enemigos, Personaje[] aliados);
	}

	/**
	 * Jedi: vida 130, ataque 24, defensa 8, mana 20. Arma: SableLuz. Hechizos:
	 * Empujon, Aplastamiento, Telequinesis.
	 * 
	 * @author Victor
	 */
	public static class Jedi extends Personaje {
		/**
		 * Crea un Jedi y equipa el Sable de Luz.
		 * 
		 * @param nombre nombre del personaje
		 */
		public Jedi(String nombre) {
			super(nombre, 130, 24, 8, 20);
			this.arma = new Armas.SableLuz();
		}

		/**
		 * Turno automatico: elige hechizo al azar o ataque basico si no hay mana.
		 * 
		 * @param enemigos array del equipo enemigo
		 * @param aliados  array del equipo aliado
		 */
		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			Personaje obj = enemigoRandom(enemigos);
			if (obj == null)
				return;
			if (mana < 8) {
				ataqueBasico(obj);
				return;
			}
			int op = (int) (Math.random() * 3) + 1;
			if (op == 1 && mana >= 10)
				usarEmpujon(enemigos);
			else if (op == 2 && mana >= 10)
				usarAplastamiento(obj);
			else if (op == 3 && mana >= 8)
				usarTelequinesis(obj);
			else
				ataqueBasico(obj);
		}
	}

	/**
	 * Sith: vida 130, ataque 26, defensa 7, mana 25. Arma: SableLuz. Hechizos:
	 * Empujon, Aplastamiento, Estrangulamiento.
	 * 
	 * @author Victor
	 */
	public static class Sith extends Personaje {
		/**
		 * Crea un Sith y equipa el Sable de Luz.
		 * 
		 * @param nombre nombre del personaje
		 */
		public Sith(String nombre) {
			super(nombre, 130, 26, 7, 25);
			this.arma = new Armas.SableLuz();
		}

		/**
		 * Turno automatico: elige hechizo al azar o ataque basico si no hay mana.
		 * 
		 * @param enemigos array del equipo enemigo
		 * @param aliados  array del equipo aliado
		 */
		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			Personaje obj = enemigoRandom(enemigos);
			if (obj == null)
				return;
			if (mana < 10) {
				ataqueBasico(obj);
				return;
			}
			int op = (int) (Math.random() * 3) + 1;
			if (op == 1 && mana >= 10)
				usarEmpujon(enemigos);
			else if (op == 2 && mana >= 10)
				usarAplastamiento(obj);
			else if (op == 3 && mana >= 12)
				usarEstrangulamiento(obj);
			else
				ataqueBasico(obj);
		}
	}

	/**
	 * SoldadoRebelde: vida 110, ataque 22, defensa 4, mana 20. Arma: Blaster.
	 * 
	 * @author Victor
	 */
	public static class SoldadoRebelde extends Personaje {
		/**
		 * Crea un Soldado Rebelde y equipa el Blaster.
		 * 
		 * @param nombre nombre del personaje
		 */
		public SoldadoRebelde(String nombre) {
			super(nombre, 110, 22, 4, 20);
			this.arma = new Armas.Blaster();
		}

		/**
		 * Turno automatico: usa Veneno si tiene mana, si no ataque basico.
		 * 
		 * @param enemigos array del equipo enemigo
		 * @param aliados  array del equipo aliado
		 */
		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			Personaje obj = enemigoRandom(enemigos);
			if (obj == null)
				return;
			if (mana >= 10)
				usarVeneno(obj);
			else
				ataqueBasico(obj);
		}
	}

	/**
	 * SoldadoImperial: vida 110, ataque 21, defensa 5, mana 20. Arma: Blaster.
	 * 
	 * @author Victor
	 */
	public static class SoldadoImperial extends Personaje {
		/**
		 * Crea un Soldado Imperial y equipa el Blaster.
		 * 
		 * @param nombre nombre del personaje
		 */
		public SoldadoImperial(String nombre) {
			super(nombre, 110, 21, 5, 20);
			this.arma = new Armas.Blaster();
		}

		/**
		 * Turno automatico: usa Veneno si tiene mana, si no ataque basico.
		 * 
		 * @param enemigos array del equipo enemigo
		 * @param aliados  array del equipo aliado
		 */
		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			Personaje obj = enemigoRandom(enemigos);
			if (obj == null)
				return;
			if (mana >= 10)
				usarVeneno(obj);
			else
				ataqueBasico(obj);
		}
	}

	/**
	 * Cazarrecompensas: vida 105, ataque 21, defensa 4, mana 30. Arma: Blaster.
	 * 
	 * @author Victor
	 */
	public static class Cazarrecompensas extends Personaje {
		/**
		 * Crea un Cazarrecompensas y equipa el Blaster.
		 * 
		 * @param nombre nombre del personaje
		 */
		public Cazarrecompensas(String nombre) {
			super(nombre, 105, 21, 4, 30);
			this.arma = new Armas.Blaster();
		}

		/**
		 * Turno automatico: usa Quemadura si tiene mana, si no ataque basico.
		 * 
		 * @param enemigos array del equipo enemigo
		 * @param aliados  array del equipo aliado
		 */
		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			Personaje obj = enemigoRandom(enemigos);
			if (obj == null)
				return;
			if (mana >= 12)
				usarQuemadura(obj);
			else
				ataqueBasico(obj);
		}
	}

	/**
	 * Sanador: vida 100, ataque 10, defensa 4, mana 35. Arma: Blaster. Prioriza
	 * curar al aliado con menos vida antes de atacar.
	 * 
	 * @author Victor
	 */
	public static class Sanador extends Personaje {
		/**
		 * Crea un Sanador y equipa el Blaster.
		 * 
		 * @param nombre nombre del personaje
		 */
		public Sanador(String nombre) {
			super(nombre, 100, 10, 4, 35);
			this.arma = new Armas.Blaster();
		}

		/**
		 * Turno automatico: cura si hay aliados bajos, sino Renovar, sino ataque.
		 * 
		 * @param enemigos array del equipo enemigo
		 * @param aliados  array del equipo aliado
		 */
		@Override
		public void hacerTurno(Personaje[] enemigos, Personaje[] aliados) {
			Personaje min = null;
			for (Personaje p : aliados)
				if (p != null && p.estaVivo())
					if (min == null || p.vida < min.vida)
						min = p;
			if (min != null && min.vida < 40 && mana >= 10)
				usarCuracion(min);
			else if (min != null && mana >= 12)
				usarRenovar(min);
			else
				ataqueBasico(enemigoRandom(enemigos));
		}
	}
}