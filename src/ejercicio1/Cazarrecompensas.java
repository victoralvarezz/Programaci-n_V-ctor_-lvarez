package ejercicio1;

/**
 * Cazarrecompensas: vida 105, ataque 21, defensa 4, mana 30. Arma: Blaster.
 */
public class Cazarrecompensas extends persona.Personaje {

	/**
	 * Crea un Cazarrecompensas y equipa el Blaster.
	 *
	 * @param nombre nombre del personaje
	 */
	public Cazarrecompensas(String nombre) {
		super(nombre, 105, 21, 4, 30);
		this.arma = new Blaster();
	}

	/**
	 * Usa Quemadura si tiene mana suficiente.
	 *
	 * @param obj personaje enemigo objetivo
	 */
	public void usarQuemadura(persona.Personaje obj) {
		if (mana >= 12) {
			mana -= 12;
			new hechizos.LanzarQuemadura().lanzar(this, new persona.Personaje[] { obj });
		} else {
			System.out.println("No tiene mana suficiente");
		}
	}

	/**
	 * Turno automatico: usa Quemadura si tiene mana, si no ataque basico.
	 *
	 * @param enemigos array del equipo enemigo
	 * @param aliados  array del equipo aliado
	 */
	@Override
	public void hacerTurno(persona.Personaje[] enemigos, persona.Personaje[] aliados) {
		persona.Personaje obj = enemigoRandom(enemigos);
		if (obj == null)
			return;
		if (mana >= 12)
			usarQuemadura(obj);
		else
			ataqueBasico(obj);
	}
}
