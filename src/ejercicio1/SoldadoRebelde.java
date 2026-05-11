package ejercicio1;

/**
 * SoldadoRebelde: vida 110, ataque 22, defensa 4, mana 20. Arma: Blaster.
 */
public class SoldadoRebelde extends persona.Personaje {

	/**
	 * Crea un Soldado Rebelde y equipa el Blaster.
	 *
	 * @param nombre nombre del personaje
	 */
	public SoldadoRebelde(String nombre) {
		super(nombre, 110, 22, 4, 20);
		this.arma = new Blaster();
	}

	/**
	 * Usa Veneno si tiene mana suficiente.
	 *
	 * @param obj personaje enemigo objetivo
	 */
	public void usarVeneno(persona.Personaje obj) {
		if (mana >= 10) {
			mana -= 10;
			new hechizos.LanzarVeneno().lanzar(this, new persona.Personaje[] { obj });
		} else {
			System.out.println("No tiene mana suficiente");
		}
	}

	/**
	 * Turno automatico: usa Veneno si tiene mana, si no ataque basico.
	 *
	 * @param enemigos array del equipo enemigo
	 * @param aliados  array del equipo aliado
	 */
	@Override
	public void hacerTurno(persona.Personaje[] enemigos, persona.Personaje[] aliados) {
		persona.Personaje obj = enemigoRandom(enemigos);
		if (obj == null)
			return;
		if (mana >= 10)
			usarVeneno(obj);
		else
			ataqueBasico(obj);
	}
}
