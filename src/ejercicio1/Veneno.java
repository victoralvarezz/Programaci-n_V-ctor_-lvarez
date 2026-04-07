package ejercicio1;

/**
 * Estado de daño por turnos que representa veneno. Aplica 4 puntos de daño
 * directo a la vida durante 3 turnos. Este daño no pasa por la defensa del
 * personaje. Se aplica al final de cada ronda en la fase de estados.
 *
 * @author Victor
 * @version 1.0
 */
public class Veneno extends Estado {

	/**
	 * Crea el estado Veneno con 4 de daño durante 3 turnos.
	 */
	public Veneno() {
		super("Veneno", 4, 3);
	}

	/**
	 * Aplica el daño del veneno directamente sobre la vida del objetivo. Si el
	 * objetivo ya esta muerto no hace nada. Decrementa turnosRestantes al final.
	 *
	 * @param objetivo el personaje que sufre el veneno
	 */
	@Override
	public void aplicarEfecto(persona.Personaje objetivo) {
		if (!objetivo.estaVivo())
			return;

		objetivo.vida -= potenciaPorTurno;

		if (objetivo.vida < 0)
			objetivo.vida = 0;

		System.out.println(
				"  [Veneno] " + objetivo.nombre + " sufre " + potenciaPorTurno + " de daño | Vida: " + objetivo.vida);

		turnosRestantes--;
	}
}