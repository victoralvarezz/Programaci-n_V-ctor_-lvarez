package BBDD;

import java.util.ArrayList;
import java.util.List;

public class PruebaConexion {

	public static void main(String[] args) {

		// Probamos personajes
		String sqlPersonajes = "SELECT nombre, tipo, nivel, experiencia FROM Personaje";
		List<Object> parametros = new ArrayList<Object>();

		List<List<Object>> personajes = Utils.selectData(sqlPersonajes, parametros);

		System.out.println("PERSONAJES");

		for (List<Object> fila : personajes) {
			System.out.println("Nombre: " + fila.get(0)
					+ " | Tipo: " + fila.get(1)
					+ " | Nivel: " + fila.get(2)
					+ " | Exp: " + fila.get(3));
		}

		// Probamos armas de personajes
		String sqlArmas = "SELECT p.nombre, a.nombre, a.danioBase "
				+ "FROM Personaje p "
				+ "JOIN Armas a ON p.id_arma = a.id_arma";

		List<List<Object>> armas = Utils.selectData(sqlArmas, parametros);

		System.out.println();
		System.out.println("ARMAS DE PERSONAJES");

		for (List<Object> fila : armas) {
			System.out.println("Personaje: " + fila.get(0)
					+ " | Arma: " + fila.get(1)
					+ " | Daño: " + fila.get(2));
		}

		// Probamos hechizos
		String sqlHechizos = "SELECT p.nombre, h.nombre, h.costeMana, h.danio "
				+ "FROM Lanza l "
				+ "JOIN Personaje p ON l.id_personaje = p.id_personaje "
				+ "JOIN Hechizos h ON l.id_hechizo = h.id_hechizo";

		List<List<Object>> hechizos = Utils.selectData(sqlHechizos, parametros);

		System.out.println();
		System.out.println("HECHIZOS");

		for (List<Object> fila : hechizos) {
			System.out.println("Personaje: " + fila.get(0)
					+ " | Hechizo: " + fila.get(1)
					+ " | Mana: " + fila.get(2)
					+ " | Daño: " + fila.get(3));
		}

		// Probamos estados
		String sqlEstados = "SELECT p.nombre, e.nombre, ae.costeMana "
				+ "FROM Aplica_Estado ae "
				+ "JOIN Personaje p ON ae.id_personaje = p.id_personaje "
				+ "JOIN Estado e ON ae.id_estado = e.id_estado";

		List<List<Object>> estados = Utils.selectData(sqlEstados, parametros);

		System.out.println();
		System.out.println("ESTADOS QUE PUEDEN APLICAR ");

		for (List<Object> fila : estados) {
			System.out.println("Personaje: " + fila.get(0)
					+ " | Estado: " + fila.get(1)
					+ " | Mana: " + fila.get(2));
		}

		// Probamos dificultad
		String sqlDificultad = "SELECT nombre, modificadorVida, modificadorAtaque, experienciaVictoria FROM Dificultad";

		List<List<Object>> dificultades = Utils.selectData(sqlDificultad, parametros);

		System.out.println();
		System.out.println("DIFICULTADES");

		for (List<Object> fila : dificultades) {
			System.out.println("Dificultad: " + fila.get(0)
					+ " | Vida: " + fila.get(1)
					+ " | Ataque: " + fila.get(2)
					+ " | Exp victoria: " + fila.get(3));
		}
	}
}