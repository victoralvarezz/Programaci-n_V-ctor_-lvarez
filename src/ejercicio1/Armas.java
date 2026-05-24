package ejercicio1;

/**
 * Clase abstracta que representa cualquier arma equipable en el juego. Cada
 * arma tiene un nombre, un danio base y una formula propia para calcular el
 * danio.
 *
 * @author Victor
 * @version 1.0
 */
public abstract class Armas {

	// Esta clase sirve como base para todas las armas del juego.

	/** Nombre del arma que se muestra en combate. */
	protected String nombre;

	/** Danio base del arma antes de aplicar estadisticas del personaje. */
	protected int danioBase;

	/**
	 * Constructor de Armas.
	 *
	 * @param nombre    nombre del arma
	 * @param danioBase danio base del arma
	 */
	public Armas(String nombre, int danioBase) {
		this.nombre = nombre;
		this.danioBase = danioBase;
	}

	/**
	 * Calcula el danio que produce esta arma en un ataque. Cada subclase aplica su
	 * propia formula.
	 *
	 * @param atacante el personaje que ataca
	 * @param defensor el personaje que recibe el ataque
	 * @return cantidad de danio calculada antes de aplicar defensa
	 */
	public abstract int calcularDanio(persona.Personaje atacante, persona.Personaje defensor);
}
