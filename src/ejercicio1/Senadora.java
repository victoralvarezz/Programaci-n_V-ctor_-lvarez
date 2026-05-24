package ejercicio1;

/**
 * Estado de curacion por turnos que representa el hechizo Renovar. Cura 10
 * puntos de vida durante 2 turnos sin superar la vida maxima. Se aplica al
 * final de cada ronda en la fase de estados.
 *
 * @author Victor
 * @version 1.0
 */
public class Senadora extends Estado {

	// Guarda cuanta vida recupera el estado en cada turno.
	private int curacionPorTurno;

	/**
	 * Crea el estado Renovar con 10 de curacion durante 2 turnos.
	 */
	public Senadora() {
		// Llama al constructor de Estado para guardar el nombre y la duracion.
		super("Renovar", 2);
		this.curacionPorTurno = 10;
	}

	/**
	 * Aplica la curacion del estado Renovar sobre la vida del objetivo. No supera
	 * la vida maxima del personaje. Si el objetivo ya esta muerto no hace nada.
	 * Decrementa turnosRestantes al final.
	 *
	 * @param objetivo el personaje que recibe la curacion
	 */
	@Override
	public void aplicar(persona.Personaje objetivo) {
		// Si el estado ya no esta activo o el personaje esta muerto, no hace nada.
		if (!estaActivo() || !objetivo.estaVivo())
			return;

		// Aplica la curacion sin superar la vida maxima del personaje.
		objetivo.curar(curacionPorTurno);

		System.out.println("  [Renovar] " + objetivo.getNombre() + " recupera " + curacionPorTurno + " de vida");

		// Despues de aplicarse, se reduce un turno de duracion.
		reducirTurno();
	}
}
