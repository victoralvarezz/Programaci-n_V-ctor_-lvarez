package BBDD;

import java.util.ArrayList;
import java.util.List;

public class GestorGraficos {

	public static List<List<Object>> datosVidaPersonajes() {
		String sql = "SELECT nombre, vidaMax FROM Personaje ORDER BY nombre";
		List<Object> parametros = new ArrayList<Object>();

		return Utils.selectData(sql, parametros);
	}

	public static List<List<Object>> datosAtaquePersonajes() {
		String sql = "SELECT nombre, ataque FROM Personaje ORDER BY nombre";
		List<Object> parametros = new ArrayList<Object>();

		return Utils.selectData(sql, parametros);
	}

	public static List<List<Object>> datosManaPersonajes() {
		String sql = "SELECT nombre, manaMax FROM Personaje ORDER BY nombre";
		List<Object> parametros = new ArrayList<Object>();

		return Utils.selectData(sql, parametros);
	}

	public static List<List<Object>> datosEnemigos() {
		String sql = "SELECT nombre, vidaMax FROM Personaje "
				+ "WHERE tipo IN ('Sith', 'SoldadoImperial', 'Cazarrecompensas') "
				+ "ORDER BY nombre";
		List<Object> parametros = new ArrayList<Object>();

		return Utils.selectData(sql, parametros);
	}

	public static List<List<Object>> datosHechizos() {
		String sql = "SELECT nombre, costeMana, danio FROM Hechizos ORDER BY id_hechizo";
		List<Object> parametros = new ArrayList<Object>();

		return Utils.selectData(sql, parametros);
	}

	public static List<List<Object>> datosEstados() {
		String sql = "SELECT nombre, turnosRestantes, potenciaPorTurno FROM Estado ORDER BY id_estado";
		List<Object> parametros = new ArrayList<Object>();

		return Utils.selectData(sql, parametros);
	}
}
