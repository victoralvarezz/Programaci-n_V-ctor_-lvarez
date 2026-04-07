package ejercicio1;

/**
 * Clase abstracta que representa cualquier arma equipable en el juego. Cada
 * arma tiene un nombre, un daño base y una formula propia para calcular el
 * daño. Las subclases implementan calcularDanio segun su tipo (cuerpo a cuerpo
 * o distancia).
 *
 * @author Victor
 * @version 1.0
 */
public abstract class Armas {

	/** Nombre del arma que se muestra en combate. */
	protected String nombre;

	/** Daño base del arma antes de aplicar estadisticas del personaje. */
	protected int danioBase;

	/**
	 * Constructor de Armas.
	 *
	 * @param nombre    nombre del arma
	 * @param danioBase daño base del arma
	 */
	public Armas(String nombre, int danioBase) {
		this.nombre = nombre;
		this.danioBase = danioBase;
	}

	/**
	 * Calcula el daño que produce esta arma en un ataque. Cada subclase aplica su
	 * propia formula.
	 *
	 * @param atacante el personaje que ataca
	 * @param defensor el personaje que recibe el ataque
	 * @return cantidad de daño calculada antes de aplicar defensa
	 */
	public abstract int calcularDanio(persona.Personaje atacante, persona.Personaje defensor);

	// —————————————————————————————————————————————————————————————
	// SUBCLASE: SableLuz (arma cuerpo a cuerpo)
	//——————————————————————————————————————————————————————————————

	/**
	 * Arma cuerpo a cuerpo. Usa el ataque completo del personaje en el calculo.
	 * Formula: danioBase + ataque del atacante.
	 *
	 * @author Victor
	 * @version 1.0
	 */
	public static class SableLuz extends Armas {

		/**
		 * Crea un Sable de Luz con daño base 10.
		 */
		public SableLuz() {
			super("Sable de Luz", 10);
		}

		/**
		 * Calcula el daño del Sable de Luz. Usa el ataque completo del atacante.
		 *
		 * @param atacante el personaje que ataca
		 * @param defensor el personaje que recibe el ataque
		 * @return danioBase + ataque del atacante
		 */
		@Override
		public int calcularDanio(persona.Personaje atacante, persona.Personaje defensor) {
			return danioBase + atacante.ataque;
		}
	}

	// —————————————————————————————————————————————————————————————
	// SUBCLASE: Blaster (arma a distancia)
	// —————————————————————————————————————————————————————————————

	/**
	 * Arma a distancia. Usa la mitad del ataque del personaje en el calculo.
	 * Formula: danioBase + (ataque del atacante / 2).
	 *
	 * @author Victor
	 * @version 1.0
	 */
	public static class Blaster extends Armas {

		/**
		 * Crea un Blaster con daño base 8.
		 */
		public Blaster() {
			super("Blaster", 8);
		}

		/**
		 * Calcula el daño del Blaster. Usa la mitad del ataque del atacante.
		 *
		 * @param atacante el personaje que ataca
		 * @param defensor el personaje que recibe el ataque
		 * @return danioBase + (ataque del atacante / 2)
		 */
		@Override
		public int calcularDanio(persona.Personaje atacante, persona.Personaje defensor) {
			return danioBase + (atacante.ataque / 2);
		}
	}
}