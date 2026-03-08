package ejercicio1;

public class main {

	public static void main(String[] args) {

		// equipos de momento fijos, luego lo hago con scanner
		persona.Personaje[] rebeldes = { new persona.Jedi("Yoda"), new persona.SoldadoRebelde("Han Solo"),
				new persona.Sanador("Leia") };

		persona.Personaje[] imperio = { new persona.Sith("Darth Vader"), new persona.SoldadoImperial("Stormtrooper"),
				new persona.Cazarrecompensas("Boba Fett") };

		int ronda = 1;
		System.out.println("=== COMBATE INICIADO ===\n");

		while (hayVivos(rebeldes) && hayVivos(imperio)) {
			System.out.println("\n===== RONDA " + ronda + " =====");

			// primero atacan los rebeldes
			System.out.println("\n-- Turno Rebeldes --");
			for (int i = 0; i < rebeldes.length; i++) {
				if (rebeldes[i] != null && rebeldes[i].estaVivo()) {
					rebeldes[i].hacerTurno(imperio, rebeldes);
				}
			}

			// luego el imperio
			System.out.println("\n-- Turno Imperio --");
			for (int i = 0; i < imperio.length; i++) {
				if (imperio[i] != null && imperio[i].estaVivo()) {
					imperio[i].hacerTurno(rebeldes, imperio);
				}
			}

			// al final de la ronda se procesan estados y se regenera mana
			System.out.println("\n-- Estados y mana --");
			procesarEquipo(rebeldes);
			procesarEquipo(imperio);

			ronda++;
		}

		// TODO falta que el jugador elija su equipo con scanner
		// TODO falta resumen final con stats de cada personaje
		System.out.println("\n=== FIN DEL COMBATE ===");
		if (hayVivos(rebeldes)) {
			System.out.println("Ganan los rebeldes en " + (ronda - 1) + " rondas.");
		} else {
			System.out.println("Gana el imperio en " + (ronda - 1) + " rondas.");
		}
	}

	// comprueba si queda alguien vivo en el equipo
	public static boolean hayVivos(persona.Personaje[] equipo) {
		for (int i = 0; i < equipo.length; i++) {
			if (equipo[i] != null && equipo[i].estaVivo())
				return true;
		}
		return false;
	}

	// procesa estados y regenera mana de todo el equipo
	public static void procesarEquipo(persona.Personaje[] equipo) {
		for (int i = 0; i < equipo.length; i++) {
			if (equipo[i] != null && equipo[i].estaVivo()) {
				equipo[i].procesarEstados();
				equipo[i].regenerarMana();
			}
		}
	}
}