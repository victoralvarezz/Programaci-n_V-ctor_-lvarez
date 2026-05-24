package ejercicio1;

import java.util.Scanner;

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
	private void usarVeneno(persona.Personaje obj) {
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
		if (atacarSiNoHayMana(obj, 10))
			return;
		usarVeneno(obj);
	}

	/**
	 * Turno manual del Soldado Rebelde.
	 *
	 * @param enemigos array del equipo enemigo
	 * @param aliados  array del equipo aliado
	 * @param sc       scanner para leer la opcion
	 */
	@Override
	public void turnoManual(persona.Personaje[] enemigos, persona.Personaje[] aliados, Scanner sc) {
		// Turno manual del Soldado Rebelde
		// Mostramos solo sus habilidades
		System.out.println("1) Ataque basico");
		System.out.println("2) Veneno");
		int op = leerEntero(sc, 1, 2);

		// Usamos la habilidad elegida
		if (op == 1)
			ataqueBasico(elegirObjetivo(enemigos, sc));
		else
			usarVeneno(elegirObjetivo(enemigos, sc));
	}
}
