package ejercicio1;

public abstract class hechizos {
	protected String nombre;
	protected int costeMana;

	public hechizos(String nombre, int costeMana) {
		this.nombre = nombre;
		this.costeMana = costeMana;
	}

	// cada hechizo lanza su efecto a su manera
	public abstract void lanzar(persona.Personaje lanzador, persona.Personaje[] enemigos);

	// EMPUJON = daño área (lo usan Jedi y Sith)
	public static class EmpujonFuerza extends hechizos {

		public EmpujonFuerza() {
			super("Empujón de la Fuerza", 10); // cuesta 10 mana
		}

		@Override
		public void lanzar(persona.Personaje lanzador, persona.Personaje[] enemigos) {
			System.out.println(lanzador.nombre + " usa Empujón de la Fuerza (área) (-10 mana)");
			for (int i = 0; i < enemigos.length; i++) {
				if (enemigos[i] != null && enemigos[i].estaVivo()) {
					enemigos[i].recibirDanio(10);
					System.out.println("   -> " + enemigos[i].nombre + " queda con vida " + enemigos[i].vida);
				}
			}
		}
	}

	// TELEQUINESIS = daño directo a 1 enemigo (solo Jedi)
	public static class Telequinesis extends hechizos {

		public Telequinesis() {
			super("Telequinesis", 8); // cuesta 8 mana
		}

		@Override
		public void lanzar(persona.Personaje lanzador, persona.Personaje[] enemigos) {
			persona.Personaje obj = null;
			for (int i = 0; i < enemigos.length; i++) {
				if (enemigos[i] != null && enemigos[i].estaVivo()) {
					obj = enemigos[i];
					break;
				}
			}
			if (obj == null)
				return;
			System.out.println(lanzador.nombre + " usa Telequinesis (-8 mana)");
			obj.recibirDanio(18);
			System.out.println("   -> " + obj.nombre + " queda con vida " + obj.vida);
		}
	}

	// ESTRANGULAMIENTO = daño directo a 1 enemigo (solo Sith)
	public static class Estrangulamiento extends hechizos {

		public Estrangulamiento() {
			super("Estrangulamiento", 10); // cuesta 10 mana
		}

		@Override
		public void lanzar(persona.Personaje lanzador, persona.Personaje[] enemigos) {
			persona.Personaje obj = null;
			for (int i = 0; i < enemigos.length; i++) {
				if (enemigos[i] != null && enemigos[i].estaVivo()) {
					obj = enemigos[i];
					break;
				}
			}
			if (obj == null)
				return;
			System.out.println(lanzador.nombre + " usa Estrangulamiento (-10 mana)");
			obj.recibirDanio(20);
			System.out.println("   -> " + obj.nombre + " queda con vida " + obj.vida);
		}
	}

	// APLASTAMIENTO = jedi y sith
	public static class Aplastamiento extends hechizos {

		public Aplastamiento() {
			super("Aplastamiento", 10); // cuesta 10 mana
		}

		@Override
		public void lanzar(persona.Personaje lanzador, persona.Personaje[] enemigos) {
			persona.Personaje obj = null;
			for (int i = 0; i < enemigos.length; i++) {
				if (enemigos[i] != null && enemigos[i].estaVivo()) {
					obj = enemigos[i];
					break;
				}
			}
			if (obj == null)
				return;
			System.out.println(lanzador.nombre + " usa Aplastamiento (-10 mana)");
			obj.recibirDanio(15); // daño entre Telequinesis y Estrangulamiento
			System.out.println("   -> " + obj.nombre + " queda con vida " + obj.vida);
		}
	}
}