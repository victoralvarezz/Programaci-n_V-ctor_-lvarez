package ejercicio1;

import java.util.Scanner;

/**
 * SoldadoImperial: vida 110, ataque 21, defensa 5, mana 20. Arma: Blaster.
 */
public class SoldadoImperial extends persona.Personaje {

	/**
	 * Crea un Soldado Imperial y equipa el Blaster.
	 *
	 * @param nombre nombre del personaje
	 */
	public SoldadoImperial(String nombre) {
		super(nombre, 110, 21, 5, 20);
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
	 * Turno manual del Soldado Imperial.
	 *
	 * @param enemigos array del equipo enemigo
	 * @param aliados  array del equipo aliado
	 * @param sc       scanner para leer la opcion
	 */
	@Override
	public void turnoManual(persona.Personaje[] enemigos, persona.Personaje[] aliados, Scanner sc) {
		// Turno manual del Soldado Imperial
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
