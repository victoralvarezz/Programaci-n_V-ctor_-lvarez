package ejercicio1;

/**
 * Clase abstracta que representa cualquier hechizo o habilidad especial del
 * juego. Cada hechizo tiene un nombre y un coste de mana. Las subclases
 * implementan el metodo lanzar con su efecto concreto.
 *
 * @author Victor
 * @version 1.0
 */
public abstract class hechizos {

	/** Nombre del hechizo que se muestra en combate. */
	protected String nombre;

	/** Coste de mana necesario para lanzar el hechizo. */
	protected int costeMana;

	/**
	 * Constructor de hechizos.
	 *
	 * @param nombre    nombre del hechizo
	 * @param costeMana coste de mana para lanzarlo
	 */
	public hechizos(String nombre, int costeMana) {
		this.nombre = nombre;
		this.costeMana = costeMana;
	}

	/**
	 * Lanza el hechizo sobre los objetivos indicados. Cada subclase define el
	 * efecto concreto.
	 *
	 * @param lanzador  el personaje que lanza el hechizo
	 * @param objetivos array de personajes objetivo
	 */
	public abstract void lanzar(persona.Personaje lanzador, persona.Personaje[] objetivos);

	/**
	 * Hechizo que aplica 10 de daño a todos los enemigos vivos. Coste: 10 mana.
	 * 
	 * @author Victor
	 */
	public static class EmpujonFuerza extends hechizos {
		/** Crea EmpujonFuerza con coste 10 de mana. */
		public EmpujonFuerza() {
			super("Empujon de la Fuerza", 10);
		}

		/**
		 * Daña a todos los enemigos vivos con 10 de daño.
		 * 
		 * @param lanzador el personaje que usa el hechizo
		 * @param enemigos array de personajes enemigos
		 */
		@Override
		public void lanzar(persona.Personaje lanzador, persona.Personaje[] enemigos) {
			System.out.println(lanzador.nombre + " usa Empujon de la Fuerza (-10 mana)");
			for (int i = 0; i < enemigos.length; i++)
				if (enemigos[i] != null && enemigos[i].estaVivo()) {
					enemigos[i].recibirDanio(10);
					System.out.println("   -> " + enemigos[i].nombre + " queda con vida " + enemigos[i].vida);
				}
		}
	}

	/**
	 * Hechizo que aplica 18 de daño al primer enemigo vivo. Coste: 8 mana.
	 * 
	 * @author Victor
	 */
	public static class Telequinesis extends hechizos {
		/** Crea Telequinesis con coste 8 de mana. */
		public Telequinesis() {
			super("Telequinesis", 8);
		}

		/**
		 * Aplica 18 de daño al primer enemigo vivo encontrado.
		 * 
		 * @param lanzador el personaje que usa el hechizo
		 * @param enemigos array de personajes enemigos
		 */
		@Override
		public void lanzar(persona.Personaje lanzador, persona.Personaje[] enemigos) {
			persona.Personaje obj = null;
			for (int i = 0; i < enemigos.length; i++)
				if (enemigos[i] != null && enemigos[i].estaVivo()) {
					obj = enemigos[i];
					break;
				}
			if (obj == null)
				return;
			System.out.println(lanzador.nombre + " usa Telequinesis (-8 mana)");
			obj.recibirDanio(18);
			System.out.println("   -> " + obj.nombre + " queda con vida " + obj.vida);
		}
	}

	/**
	 * Hechizo que aplica 20 de daño al primer enemigo vivo. Coste: 10 mana.
	 * 
	 * @author Victor
	 */
	public static class Estrangulamiento extends hechizos {
		/** Crea Estrangulamiento con coste 10 de mana. */
		public Estrangulamiento() {
			super("Estrangulamiento", 10);
		}

		/**
		 * Aplica 20 de daño al primer enemigo vivo encontrado.
		 * 
		 * @param lanzador el personaje que usa el hechizo
		 * @param enemigos array de personajes enemigos
		 */
		@Override
		public void lanzar(persona.Personaje lanzador, persona.Personaje[] enemigos) {
			persona.Personaje obj = null;
			for (int i = 0; i < enemigos.length; i++)
				if (enemigos[i] != null && enemigos[i].estaVivo()) {
					obj = enemigos[i];
					break;
				}
			if (obj == null)
				return;
			System.out.println(lanzador.nombre + " usa Estrangulamiento (-10 mana)");
			obj.recibirDanio(20);
			System.out.println("   -> " + obj.nombre + " queda con vida " + obj.vida);
		}
	}

	/**
	 * Hechizo que aplica 15 de daño al primer enemigo vivo. Coste: 10 mana.
	 * 
	 * @author Victor
	 */
	public static class Aplastamiento extends hechizos {
		/** Crea Aplastamiento con coste 10 de mana. */
		public Aplastamiento() {
			super("Aplastamiento", 10);
		}

		/**
		 * Aplica 15 de daño al primer enemigo vivo encontrado.
		 * 
		 * @param lanzador el personaje que usa el hechizo
		 * @param enemigos array de personajes enemigos
		 */
		@Override
		public void lanzar(persona.Personaje lanzador, persona.Personaje[] enemigos) {
			persona.Personaje obj = null;
			for (int i = 0; i < enemigos.length; i++)
				if (enemigos[i] != null && enemigos[i].estaVivo()) {
					obj = enemigos[i];
					break;
				}
			if (obj == null)
				return;
			System.out.println(lanzador.nombre + " usa Aplastamiento (-10 mana)");
			obj.recibirDanio(15);
			System.out.println("   -> " + obj.nombre + " queda con vida " + obj.vida);
		}
	}

	/**
	 * Hechizo que restaura 25 de vida a un aliado sin superar el maximo. Coste: 10
	 * mana.
	 * 
	 * @author Victor
	 */
	public static class Curacion extends hechizos {
		/** Crea Curacion con coste 10 de mana. */
		public Curacion() {
			super("Curacion", 10);
		}

		/**
		 * Cura 25 de vida al primer aliado del array.
		 * 
		 * @param lanzador el personaje que usa el hechizo
		 * @param aliados  array de aliados
		 */
		@Override
		public void lanzar(persona.Personaje lanzador, persona.Personaje[] aliados) {
			persona.Personaje obj = aliados[0];
			if (obj == null || !obj.estaVivo())
				return;
			System.out.println(lanzador.nombre + " usa Curacion sobre " + obj.nombre + " (-10 mana)");
			obj.vida += 25;
			if (obj.vida > obj.vidaMax)
				obj.vida = obj.vidaMax;
			System.out.println("   -> " + obj.nombre + " queda con vida " + obj.vida);
		}
	}

	/**
	 * Hechizo que aplica 10 de daño inmediato y el estado Quemaduras (DoT). Coste:
	 * 12 mana. Si el objetivo ya tenia Quemadura, se renueva en lugar de
	 * acumularse.
	 * 
	 * @author Victor
	 */
	public static class LanzarQuemadura extends hechizos {
		/** Crea LanzarQuemadura con coste 12 de mana. */
		public LanzarQuemadura() {
			super("Quemadura", 12);
		}

		/**
		 * Aplica daño inmediato y el estado Quemaduras al primer enemigo.
		 * 
		 * @param lanzador el personaje que usa el hechizo
		 * @param enemigos array de personajes enemigos
		 */
		@Override
		public void lanzar(persona.Personaje lanzador, persona.Personaje[] enemigos) {
			persona.Personaje obj = enemigos[0];
			if (obj == null || !obj.estaVivo())
				return;
			System.out.println(lanzador.nombre + " lanza Quemadura a " + obj.nombre + " (-12 mana)");
			obj.recibirDanio(10);
			obj.aplicarEstado(new Quemaduras());
			System.out.println("   -> " + obj.nombre + " queda con vida " + obj.vida);
		}
	}

	/**
	 * Hechizo que aplica el estado Renovar (HoT) a un aliado. Coste: 12 mana. Cura
	 * 10 de vida durante 2 turnos. Si ya tenia Renovar se renueva.
	 * 
	 * @author Victor
	 */
	public static class LanzarRenovar extends hechizos {
		/** Crea LanzarRenovar con coste 12 de mana. */
		public LanzarRenovar() {
			super("Renovar", 12);
		}

		/**
		 * Aplica el estado Renovar al primer aliado del array.
		 * 
		 * @param lanzador el personaje que usa el hechizo
		 * @param aliados  array de aliados
		 */
		@Override
		public void lanzar(persona.Personaje lanzador, persona.Personaje[] aliados) {
			persona.Personaje obj = aliados[0];
			if (obj == null || !obj.estaVivo())
				return;
			System.out.println(lanzador.nombre + " aplica Renovar a " + obj.nombre + " (-12 mana)");
			obj.aplicarEstado(new Senadora());
		}
	}

	/**
	 * Hechizo que aplica 8 de daño inmediato y el estado Veneno (DoT). Coste: 10
	 * mana. Si el objetivo ya tenia Veneno, se renueva en lugar de acumularse.
	 * 
	 * @author Victor
	 */
	public static class LanzarVeneno extends hechizos {
		/** Crea LanzarVeneno con coste 10 de mana. */
		public LanzarVeneno() {
			super("Veneno", 10);
		}

		/**
		 * Aplica daño inmediato y el estado Veneno al primer enemigo.
		 * 
		 * @param lanzador el personaje que usa el hechizo
		 * @param enemigos array de personajes enemigos
		 */
		@Override
		public void lanzar(persona.Personaje lanzador, persona.Personaje[] enemigos) {
			persona.Personaje obj = enemigos[0];
			if (obj == null || !obj.estaVivo())
				return;
			System.out.println(lanzador.nombre + " lanza granada venenosa a " + obj.nombre + " (-10 mana)");
			obj.recibirDanio(8);
			obj.aplicarEstado(new Veneno());
			System.out.println("   -> " + obj.nombre + " queda con vida " + obj.vida);
		}
	}
}