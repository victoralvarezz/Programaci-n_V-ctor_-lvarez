package ejercicio1;

import BBDD.GestorPartidas;

import java.util.List;
import java.util.Scanner;

/**
 * Menu para gestionar partidas guardadas.
 */
public class MenuPartidas {

	/**
	 * Carga una partida guardada y muestra sus personajes.
	 *
	 * @param sc scanner de la aplicacion
	 */
	public static void cargarPartida(Scanner sc) {
		// Cargamos una partida guardada
		List<List<Object>> partidas = listarPartidasGuardadas();

		if (partidas.size() == 0) {
			System.out.println("No hay partidas guardadas.");
			return;
		}

		System.out.println("Introduce id de partida a cargar:");
		int idPartida = leerEntero(sc, 1, 9999);
		int turno = -1;

		for (int i = 0; i < partidas.size(); i++) {
			List<Object> partida = partidas.get(i);
			int idGuardado = Integer.parseInt(String.valueOf(partida.get(0)));

			if (idGuardado == idPartida) {
				turno = Integer.parseInt(String.valueOf(partida.get(2)));
			}
		}

		if (turno == -1) {
			System.out.println("No existe esa partida.");
			return;
		}

		List<List<Object>> personajes = GestorPartidas.cargarTurno(idPartida, turno);

		// Mostramos los personajes cargados
		for (int i = 0; i < personajes.size(); i++) {
			List<Object> personaje = personajes.get(i);
			System.out.println("Nombre: " + personaje.get(0));
			System.out.println("Tipo: " + personaje.get(1));
			System.out.println("Turno: " + personaje.get(2));
			System.out.println("Vida actual: " + personaje.get(3));
			System.out.println("Mana actual: " + personaje.get(4));
			System.out.println("Esta vivo: " + personaje.get(5));
			System.out.println("Equipo: " + personaje.get(6));
			System.out.println("--------------------");
		}
	}

	/**
	 * Lista las partidas guardadas.
	 */
	public static void listarPartidas() {
		listarPartidasGuardadas();
	}

	/**
	 * Borra una partida guardada.
	 *
	 * @param sc scanner de la aplicacion
	 */
	public static void borrarPartida(Scanner sc) {
		// Borramos una partida guardada
		List<List<Object>> partidas = listarPartidasGuardadas();

		if (partidas.size() == 0) {
			System.out.println("No hay partidas guardadas.");
			return;
		}

		System.out.println("Introduce id de partida a borrar:");
		int idPartida = leerEntero(sc, 1, 9999);

		GestorPartidas.borrarPartida(idPartida);
		System.out.println("Partida borrada.");
	}

	/**
	 * Lista las partidas guardadas y devuelve la lista.
	 *
	 * @return lista de partidas guardadas
	 */
	public static List<List<Object>> listarPartidasGuardadas() {
		// Listamos las partidas guardadas
		List<List<Object>> partidas = GestorPartidas.listarPartidas();

		for (int i = 0; i < partidas.size(); i++) {
			List<Object> partida = partidas.get(i);
			System.out.println("Partida: " + partida.get(0));
			System.out.println("Rondas guardadas: " + partida.get(1));
			System.out.println("Ronda actual: " + partida.get(2));
			System.out.println("Final del turno: " + partida.get(3));
			System.out.println("Dificultad: " + partida.get(4));
			System.out.println("--------------------");
		}

		return partidas;
	}

	/**
	 * Lee un entero por consola dentro del rango indicado.
	 *
	 * @param sc  scanner de la aplicacion
	 * @param min valor minimo
	 * @param max valor maximo
	 * @return numero valido
	 */
	private static int leerEntero(Scanner sc, int min, int max) {
		while (true) {
			try {
				int n = Integer.parseInt(sc.nextLine());
				if (n >= min && n <= max)
					return n;
			} catch (Exception e) {
			}
			System.out.print("Numero incorrecto: ");
		}
	}
}
