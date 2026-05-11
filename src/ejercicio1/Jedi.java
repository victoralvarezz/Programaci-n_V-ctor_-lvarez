package ejercicio1;

/**
 * Jedi: vida 130, ataque 24, defensa 8, mana 20. Arma: SableLuz. Hechizos:
 * Empujon, Aplastamiento, Telequinesis.
 */
public class Jedi extends persona.Personaje {

	/**
	 * Crea un Jedi y equipa el Sable de Luz.
	 *
	 * @param nombre nombre del personaje
	 */
	public Jedi(String nombre) {
		super(nombre, 130, 24, 8, 20);
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
	 * Usa Telequinesis sobre un enemigo si tiene mana suficiente.
	 *
	 * @param obj personaje enemigo objetivo
	 */
	public void usarTelequinesis(persona.Personaje obj) {
		if (mana >= 8) {
			mana -= 8;
			new hechizos.Telequinesis().lanzar(this, new persona.Personaje[] { obj });
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
		if (mana < 8) {
			ataqueBasico(obj);
			return;
		}
		int op = (int) (Math.random() * 3) + 1;
		if (op == 1 && mana >= 10)
			usarEmpujon(enemigos);
		else if (op == 2 && mana >= 10)
			usarAplastamiento(obj);
		else if (op == 3 && mana >= 8)
			usarTelequinesis(obj);
		else
			ataqueBasico(obj);
	}

}
