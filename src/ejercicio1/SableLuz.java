package ejercicio1;

/**
 * Arma cuerpo a cuerpo. Usa el ataque completo del personaje para calcular el
 * danio.
 */
public class SableLuz extends Armas {

	/**
	 * Crea un Sable de Luz con danio base 10.
	 */
	public SableLuz() {
		super("Sable de Luz", 10);
	}

	/**
	 * Calcula el danio del Sable de Luz.
	 */
	@Override
	public int calcularDanio(persona.Personaje atacante, persona.Personaje defensor) {
		return danioBase + atacante.ataque;
	}
}
