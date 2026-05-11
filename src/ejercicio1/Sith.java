package ejercicio1;

/**
 * Sith: vida 130, ataque 26, defensa 7, mana 25. Arma: SableLuz. Hechizos:
 * Empujon, Aplastamiento, Estrangulamiento.
 */
public class Sith extends persona.Personaje {

	/**
	 * Crea un Sith y equipa el Sable de Luz.
	 *
	 * @param nombre nombre del personaje
	 */
	public Sith(String nombre) {
		super(nombre, 130, 26, 7, 25);
		this.arma = new SableLuz();
	}

	/**
	 * Usa Empujon de la Fuerza si tiene mana suficiente.
	 *
	 * @param enemigos array de personajes enemigos
	 */
	public void usarEmpujon(persona.Personaje[] enemigos) {
		if (mana >= 10) {
			mana -= 10;
			new hechizos.EmpujonFuerza().lanzar(this, enemigos);
		} else {
			System.out.println("No tiene mana suficiente");
		}
	}

	/**
	 * Usa Aplastamiento sobre un enemigo si tiene mana suficiente.
	 *
	 * @param obj personaje enemigo objetivo
	 */
	public void usarAplastamiento(persona.Personaje obj) {
		if (mana >= 10) {
			mana -= 10;
			new hechizos.Aplastamiento().lanzar(this, new persona.Personaje[] { obj });
		} else {
			System.out.println("No tiene mana suficiente");
		}
	}

	/**
	 * Usa Estrangulamiento sobre un enemigo si tiene mana suficiente.
	 *
	 * @param obj personaje enemigo objetivo
	 */
	public void usarEstrangulamiento(persona.Personaje obj) {
		if (mana >= 12) {
			mana -= 12;
			new hechizos.Estrangulamiento().lanzar(this, new persona.Personaje[] { obj });
		} else {
			System.out.println("No tiene mana suficiente");
		}
	}

	/**
	 * Turno automatico: elige hechizo al azar o ataque basico si no hay mana.
	 *
	 * @param enemigos array del equipo enemigo
	 * @param aliados  array del equipo aliado
	 */
	@Override
	public void hacerTurno(persona.Personaje[] enemigos, persona.Personaje[] aliados) {
		persona.Personaje obj = enemigoRandom(enemigos);
		if (obj == null)
			return;
		if (atacarSiNoHayMana(obj, 10))
			return;
		int op = opcionAleatoria(3);
		if (op == 1 && mana >= 10)
			usarEmpujon(enemigos);
		else if (op == 2 && mana >= 10)
			usarAplastamiento(obj);
		else if (op == 3 && mana >= 12)
			usarEstrangulamiento(obj);
		else
			ataqueBasico(obj);
	}
}
