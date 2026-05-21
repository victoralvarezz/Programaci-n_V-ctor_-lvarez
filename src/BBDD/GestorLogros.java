package BBDD;

import java.util.ArrayList;
import java.util.List;

public class GestorLogros {

	public static boolean tieneLogro(int idPartida, int idLogro) {
		String sql = "SELECT id_logro FROM Partida_Logro WHERE id_partida = ? AND id_logro = ?";
		List<Object> parametros = new ArrayList<Object>();

		// Buscamos si ya tiene el logro.
		parametros.add(idPartida);
		parametros.add(idLogro);

		List<List<Object>> datos = Utils.selectData(sql, parametros);
		return datos.size() > 0;
	}

	public static void desbloquearLogro(int idPartida, int idLogro) {
		if (tieneLogro(idPartida, idLogro)) {
			return;
		}

		String sqlLogro = "SELECT nombre, descripcion FROM Logro WHERE id_logro = ?";
		List<Object> parametrosLogro = new ArrayList<Object>();

		// Cogemos los datos del logro.
		parametrosLogro.add(idLogro);
		List<List<Object>> datosLogro = Utils.selectData(sqlLogro, parametrosLogro);

		if (datosLogro.size() == 0) {
			return;
		}

		String sql = "INSERT INTO Partida_Logro (id_partida, id_logro) VALUES (?, ?)";
		List<Object> parametros = new ArrayList<Object>();

		// Guardamos el logro de la partida.
		parametros.add(idPartida);
		parametros.add(idLogro);
		Utils.insertData(sql, parametros);

		List<Object> logro = datosLogro.get(0);
		System.out.println("*** LOGRO DESBLOQUEADO: " + logro.get(0) + " ***");
		System.out.println(logro.get(1));
	}

	public static List<List<Object>> listarTodosLogros() {
		String sql = "SELECT id_logro, nombre, descripcion FROM Logro ORDER BY id_logro";
		List<Object> parametros = new ArrayList<Object>();

		return Utils.selectData(sql, parametros);
	}

	public static List<List<Object>> listarLogrosPartida(int idPartida) {
		String sql = "SELECT l.nombre, l.descripcion, pl.fecha " + "FROM Partida_Logro pl "
				+ "JOIN Logro l ON pl.id_logro = l.id_logro " + "WHERE pl.id_partida = ? " + "ORDER BY pl.fecha";
		List<Object> parametros = new ArrayList<Object>();

		// Listamos los logros de la partida.
		parametros.add(idPartida);
		return Utils.selectData(sql, parametros);
	}
}
