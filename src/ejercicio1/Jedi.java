package ejercicio1;

import java.util.Scanner;

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
	private void usarEmpujon(persona.Personaje[] enemigos) {
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
	private void usarAplastamiento(persona.Personaje obj) {
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
	private void usarTelequinesis(persona.Personaje obj) {
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
		if (atacarSiNoHayMana(obj, 8))
			return;
		int op = opcionAleatoria(3);
		if (op == 1 && mana >= 10)
			usarEmpujon(enemigos);
		else if (op == 2 && mana >= 10)
			usarAplastamiento(obj);
		else if (op == 3 && mana >= 8)
			usarTelequinesis(obj);
		else
			ataqueBasico(obj);
	}

	/**
	 * Turno manual del Jedi.
	 *
	 * @param enemigos array del equipo enemigo
	 * @param aliados  array del equipo aliado
	 * @param sc       scanner para leer la opcion
	 */
	@Override
	public void turnoManual(persona.Personaje[] enemigos, persona.Personaje[] aliados, Scanner sc) {
		// Turno manual del Jedi
		// Mostramos solo sus habilidades
		System.out.println("1) Ataque basico");
		System.out.println("2) Empujon de la Fuerza");
		System.out.println("3) Aplastamiento");
		System.out.println("4) Telequinesis");
		int op = leerEntero(sc, 1, 4);

		// Usamos la habilidad elegida
		if (op == 1)
			ataqueBasico(elegirObjetivo(enemigos, sc));
		else if (op == 2)
			usarEmpujon(enemigos);
		else if (op == 3)
			usarAplastamiento(elegirObjetivo(enemigos, sc));
		else
			usarTelequinesis(elegirObjetivo(enemigos, sc));
	}

}
