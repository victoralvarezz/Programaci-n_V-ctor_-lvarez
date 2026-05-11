package ejercicio1;

/**
 * Arma a distancia. Usa la mitad del ataque del personaje para calcular el
 * danio.
 */
public class Blaster extends Armas {

	/**
	 * Crea un Blaster con danio base 8.
	 */
	public Blaster() {
		super("Blaster", 8);
	}

	/**
	 * Calcula el danio del Blaster.
	 */
	@Override
	public int calcularDanio(persona.Personaje atacante, persona.Personaje defensor) {
		return danioBase + (atacante.ataque / 2);
	}
}
