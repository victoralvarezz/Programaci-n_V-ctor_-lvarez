package ejercicio1;

/**
 * Sanador: vida 100, ataque 10, defensa 4, mana 35. Arma: Blaster. Prioriza
 * curar al aliado con menos vida antes de atacar.
 */
public class Sanador extends persona.Personaje {

	/**
	 * Crea un Sanador y equipa el Blaster.
	 *
	 * @param nombre nombre del personaje
	 */
	public Sanador(String nombre) {
		super(nombre, 100, 10, 4, 35);
		this.arma = new Blaster();
	}

	/**
	 * Usa Curacion si tiene mana suficiente.
	 *
	 * @param aliado personaje aliado a curar
	 */
	public void usarCuracion(persona.Personaje aliado) {
		if (mana >= 10) {
			mana -= 10;
			new hechizos.Curacion().lanzar(this, new persona.Personaje[] { aliado });
		} else {
			System.out.println("No tiene mana suficiente");
		}
	}

	/**
	 * Usa Renovar si tiene mana suficiente.
	 *
	 * @param aliado personaje aliado a curar
	 */
	public void usarRenovar(persona.Personaje aliado) {
		if (mana >= 12) {
			mana -= 12;
			new hechizos.LanzarRenovar().lanzar(this, new persona.Personaje[] { aliado });
		} else {
			System.out.println("No tiene mana suficiente");
		}
	}

	/**
	 * Turno automatico: cura si hay aliados bajos, sino Renovar, sino ataque.
	 *
	 * @param enemigos array del equipo enemigo
	 * @param aliados  array del equipo aliado
	 */
	@Override
	public void hacerTurno(persona.Personaje[] enemigos, persona.Personaje[] aliados) {
		persona.Personaje min = null;
		for (persona.Personaje p : aliados)
			if (p != null && p.estaVivo())
				if (min == null || p.vida < min.vida)
					min = p;
		if (min != null && min.vida < 40) {
			if (atacarSiNoHayMana(enemigoRandom(enemigos), 10))
				return;
			usarCuracion(min);
		} else if (min != null) {
			if (atacarSiNoHayMana(enemigoRandom(enemigos), 12))
				return;
			usarRenovar(min);
		} else {
			ataqueBasico(enemigoRandom(enemigos));
		}
	}
}
