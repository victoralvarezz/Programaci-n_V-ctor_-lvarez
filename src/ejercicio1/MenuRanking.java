package ejercicio1;

import BBDD.GestorPartidas;

import java.util.List;

/**
 * Menu para consultar el ranking de personajes.
 */
public class MenuRanking {

	/**
	 * Muestra el ranking de personajes.
	 */
	public static void verRanking() {
		System.out.println("=== RANKING DE PERSONAJES ===");
		List<List<Object>> ranking = GestorPartidas.listarRanking();

		// Mostramos el ranking de personajes
		for (int i = 0; i < ranking.size(); i++) {
			List<Object> personaje = ranking.get(i);
			System.out.println("Nombre: " + personaje.get(0));
			System.out.println("Tipo: " + personaje.get(1));
			System.out.println("Nivel: " + personaje.get(2));
			System.out.println("Experiencia: " + personaje.get(3));
			System.out.println("Victorias: " + personaje.get(4));
			System.out.println("Derrotas: " + personaje.get(5));
			System.out.println("--------------------");
		}
	}
}
