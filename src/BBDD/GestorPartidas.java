package BBDD;

import java.util.ArrayList;
import java.util.List;

public class GestorPartidas {

	public static void crearPartida(int idPartida, int idDificultad) {
		String sql = "INSERT INTO Partida (id_partida, rondas_guardadas, id_ronda_actual, final_del_turno, id_dificultad) "
				+ "VALUES (?, ?, ?, ?, ?)";
		List<Object> parametros = new ArrayList<Object>();

		// Creamos la partida
		parametros.add(idPartida);
		parametros.add(0);
		parametros.add(0);
		parametros.add(false);
		parametros.add(idDificultad);
		Utils.insertData(sql, parametros);
	}

	public static void guardarPersonajePartida(int idPartida, int idPersonaje, int turno, int vida, int mana,
			boolean estaVivo, String equipo) {
		String sql = "INSERT INTO Partida_Personaje (id_partida, id_personaje, turno, vida_actual, mana_actual, estaVivo, equipo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		List<Object> parametros = new ArrayList<Object>();

		// Guardamos el personaje
		parametros.add(idPartida);
		parametros.add(idPersonaje);
		parametros.add(turno);
		parametros.add(vida);
		parametros.add(mana);
		parametros.add(estaVivo);
		parametros.add(equipo);
		Utils.insertData(sql, parametros);
	}

	public static void actualizarPartida(int idPartida, int rondasGuardadas, int rondaActual, boolean finalDelTurno) {
		String sql = "UPDATE Partida "
				+ "SET rondas_guardadas = ?, id_ronda_actual = ?, final_del_turno = ? "
				+ "WHERE id_partida = ?";
		List<Object> parametros = new ArrayList<Object>();

		// Actualizamos la partida
		parametros.add(rondasGuardadas);
		parametros.add(rondaActual);
		parametros.add(finalDelTurno);
		parametros.add(idPartida);
		Utils.updateData(sql, parametros);
	}

	public static List<List<Object>> listarPartidas() {
		String sql = "SELECT p.id_partida, p.rondas_guardadas, p.id_ronda_actual, p.final_del_turno, d.nombre "
				+ "FROM Partida p "
				+ "JOIN Dificultad d ON p.id_dificultad = d.id_dificultad";
		List<Object> parametros = new ArrayList<Object>();

		// Listamos las partidas
		return Utils.selectData(sql, parametros);
	}

	public static List<List<Object>> cargarTurno(int idPartida, int turno) {
		String sql = "SELECT p.nombre, p.tipo, pp.turno, pp.vida_actual, pp.mana_actual, pp.estaVivo, pp.equipo "
				+ "FROM Partida_Personaje pp "
				+ "JOIN Personaje p ON pp.id_personaje = p.id_personaje "
				+ "WHERE pp.id_partida = ? "
				+ "AND pp.turno = ? "
				+ "ORDER BY pp.equipo, p.nombre";
		List<Object> parametros = new ArrayList<Object>();

		// Cargamos un turno
		parametros.add(idPartida);
		parametros.add(turno);
		return Utils.selectData(sql, parametros);
	}

	public static void borrarPartida(int idPartida) {
		String sql = "DELETE FROM Partida WHERE id_partida = ?";
		List<Object> parametros = new ArrayList<Object>();

		// Borramos la partida
		parametros.add(idPartida);
		Utils.deleteData(sql, parametros);
	}
}
