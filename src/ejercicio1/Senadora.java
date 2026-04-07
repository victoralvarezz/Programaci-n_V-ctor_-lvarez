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

	/**
	 * Crea el estado Renovar con 10 de curacion durante 2 turnos.
	 */
	public Senadora() {
		super("Renovar", 10, 2);
	}

	/**
	 * Aplica la curacion del estado Renovar sobre la vida del objetivo. No supera
	 * la vida maxima del personaje. Si el objetivo ya esta muerto no hace nada.
	 * Decrementa turnosRestantes al final.
	 *
	 * @param objetivo el personaje que recibe la curacion
	 */
	@Override
	public void aplicarEfecto(persona.Personaje objetivo) {
		if (!objetivo.estaVivo())
			return;

		objetivo.vida += potenciaPorTurno;

		if (objetivo.vida > objetivo.vidaMax)
			objetivo.vida = objetivo.vidaMax;

		System.out.println("  [Renovar] " + objetivo.nombre + " recupera " + potenciaPorTurno + " de vida | Vida: "
				+ objetivo.vida);

		turnosRestantes--;
	}
}