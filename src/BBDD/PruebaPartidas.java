package BBDD;

import java.util.ArrayList;
import java.util.List;

public class PruebaPartidas {

	public static void main(String[] args) {
		String sqlInsert = "INSERT INTO Partida (id_partida, rondas_guardadas, id_ronda_actual, final_del_turno, id_dificultad) "
				+ "VALUES (?, ?, ?, ?, ?)";
		List<Object> parametros = new ArrayList<Object>();

		// Insertamos una partida de prueba
		parametros.add(99);
		parametros.add(0);
		parametros.add(1);
		parametros.add(false);
		parametros.add(2);
		Utils.insertData(sqlInsert, parametros);

		String sqlSelect = "SELECT p.id_partida, p.rondas_guardadas, p.id_ronda_actual, d.nombre "
				+ "FROM Partida p "
				+ "JOIN Dificultad d ON p.id_dificultad = d.id_dificultad";
		parametros = new ArrayList<Object>();

		// Listamos antes de borrar
		System.out.println("=== PARTIDAS ANTES DE BORRAR ===");
		List<List<Object>> partidas = Utils.selectData(sqlSelect, parametros);

		// Mostramos los datos
		for (int i = 0; i < partidas.size(); i++) {
			List<Object> partida = partidas.get(i);
			System.out.println("Partida: " + partida.get(0));
			System.out.println("Rondas guardadas: " + partida.get(1));
			System.out.println("Ronda actual: " + partida.get(2));
			System.out.println("Dificultad: " + partida.get(3));
			System.out.println("--------------------");
		}

		String sqlDelete = "DELETE FROM Partida WHERE id_partida = ?";
		parametros = new ArrayList<Object>();

		// Borramos la partida de prueba
		parametros.add(99);
		Utils.deleteData(sqlDelete, parametros);

		parametros = new ArrayList<Object>();

		// Listamos despues de borrar
		System.out.println("=== PARTIDAS DESPUES DE BORRAR ===");
		partidas = Utils.selectData(sqlSelect, parametros);

		// Mostramos los datos
		for (int i = 0; i < partidas.size(); i++) {
			List<Object> partida = partidas.get(i);
			System.out.println("Partida: " + partida.get(0));
			System.out.println("Rondas guardadas: " + partida.get(1));
			System.out.println("Ronda actual: " + partida.get(2));
			System.out.println("Dificultad: " + partida.get(3));
			System.out.println("--------------------");
		}
	}
}
