package ejercicio1;

import BBDD.GestorLogros;

import java.util.List;
import java.util.Scanner;

/**
 * Menu para consultar los logros de una partida.
 */
public class MenuLogros {

	/**
	 * Muestra los logros disponibles y los logros desbloqueados de una partida.
	 *
	 * @param sc scanner de la aplicacion
	 */
	public static void verLogros(Scanner sc) {
		System.out.println("=== LOGROS DISPONIBLES ===");
		List<List<Object>> todosLogros = GestorLogros.listarTodosLogros();

		// Mostramos todos los logros disponibles
		for (int i = 0; i < todosLogros.size(); i++) {
			List<Object> logro = todosLogros.get(i);
			System.out.println("Logro: " + logro.get(1));
			System.out.println("Descripcion: " + logro.get(2));
			System.out.println("--------------------");
		}

		MenuPartidas.listarPartidasGuardadas();

		System.out.println("Introduce id de partida para ver sus logros desbloqueados:");
		int idPartida = leerEntero(sc, 1, 9999);

		List<List<Object>> logros = GestorLogros.listarLogrosPartida(idPartida);

		if (logros.size() == 0) {
			System.out.println("No hay logros desbloqueados para esta partida.");
			return;
		}

		// Mostramos los logros desbloqueados de la partida
		for (int i = 0; i < logros.size(); i++) {
			List<Object> logro = logros.get(i);
			System.out.println("Logro: " + logro.get(0));
			System.out.println("Descripcion: " + logro.get(1));
			System.out.println("Fecha: " + logro.get(2));
			System.out.println("--------------------");
		}
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
