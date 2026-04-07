package ejercicio1;

/**
 * Clase abstracta que representa un estado persistente aplicado a un personaje.
 * Un estado tiene una duracion en turnos y aplica un efecto (daño o curacion)
 * cada ronda hasta que expira. Las subclases concretas son Quemaduras, Veneno y
 * Senadora (Renovar).
 *
 * @author Victor
 * @version 1.0
 */
public abstract class Estado {

	/** Nombre del estado que se muestra en combate. */
	protected String nombre;

	/** Turnos que le quedan al estado antes de expirar. */
	protected int turnosRestantes;

	/** Cantidad de daño o curacion que aplica por turno. */
	protected int potenciaPorTurno;

	/**
	 * Constructor de Estado.
	 *
	 * @param nombre           nombre del estado
	 * @param potenciaPorTurno cantidad de daño o curacion por turno
	 * @param turnosRestantes  duracion del estado en turnos
	 */
	public Estado(String nombre, int potenciaPorTurno, int turnosRestantes) {
		this.nombre = nombre;
		this.potenciaPorTurno = potenciaPorTurno;
		this.turnosRestantes = turnosRestantes;
	}

	/**
	 * Aplica el efecto del estado sobre el personaje objetivo. Cada subclase define
	 * si el efecto es daño o curacion. Tambien decrementa turnosRestantes en cada
	 * llamada.
	 *
	 * @param objetivo el personaje sobre el que se aplica el efecto
	 */
	public abstract void aplicarEfecto(persona.Personaje objetivo);
}