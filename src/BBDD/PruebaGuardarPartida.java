package BBDD;

import java.util.ArrayList;
import java.util.List;

public class PruebaGuardarPartida {

	public static void main(String[] args) {
		String sqlInsertPartida = "INSERT INTO Partida (id_partida, rondas_guardadas, id_ronda_actual, final_del_turno, id_dificultad) "
				+ "VALUES (?, ?, ?, ?, ?)";
		List<Object> parametros = new ArrayList<Object>();

		// Creamos una partida de prueba
		parametros.add(100);
		parametros.add(0);
		parametros.add(0);
		parametros.add(false);
		parametros.add(2);
		Utils.insertData(sqlInsertPartida, parametros);

		String sqlInsertPersonaje = "INSERT INTO Partida_Personaje (id_partida, id_personaje, turno, vida_actual, mana_actual, estaVivo, equipo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		// Guardamos los personajes de la partida
		parametros = new ArrayList<Object>();
		parametros.add(100);
		parametros.add(1);
		parametros.add(0);
		parametros.add(130);
		parametros.add(20);
		parametros.add(true);
		parametros.add("Jugador");
		Utils.insertData(sqlInsertPersonaje, parametros);

		parametros = new ArrayList<Object>();
		parametros.add(100);
		parametros.add(4);
		parametros.add(0);
		parametros.add(110);
		parametros.add(20);
		parametros.add(true);
		parametros.add("Jugador");
		Utils.insertData(sqlInsertPersonaje, parametros);

		parametros = new ArrayList<Object>();
		parametros.add(100);
		parametros.add(3);
		parametros.add(0);
		parametros.add(100);
		parametros.add(35);
		parametros.add(true);
		parametros.add("Jugador");
		Utils.insertData(sqlInsertPersonaje, parametros);

		parametros = new ArrayList<Object>();
		parametros.add(100);
		parametros.add(2);
		parametros.add(0);
		parametros.add(130);
		parametros.add(25);
		parametros.add(true);
		parametros.add("Enemigo");
		Utils.insertData(sqlInsertPersonaje, parametros);

		parametros = new ArrayList<Object>();
		parametros.add(100);
		parametros.add(5);
		parametros.add(0);
		parametros.add(110);
		parametros.add(20);
		parametros.add(true);
		parametros.add("Enemigo");
		Utils.insertData(sqlInsertPersonaje, parametros);

		parametros = new ArrayList<Object>();
		parametros.add(100);
		parametros.add(6);
		parametros.add(0);
		parametros.add(105);
		parametros.add(30);
		parametros.add(true);
		parametros.add("Enemigo");
		Utils.insertData(sqlInsertPersonaje, parametros);

		String sqlSelect = "SELECT p.nombre, p.tipo, pp.vida_actual, pp.mana_actual, pp.estaVivo, pp.equipo "
				+ "FROM Partida_Personaje pp "
				+ "JOIN Personaje p ON pp.id_personaje = p.id_personaje "
				+ "WHERE pp.id_partida = ? "
				+ "ORDER BY pp.equipo, p.nombre";
		parametros = new ArrayList<Object>();
		parametros.add(100);

		// Listamos los personajes guardados
		List<List<Object>> personajes = Utils.selectData(sqlSelect, parametros);

		for (int i = 0; i < personajes.size(); i++) {
			List<Object> personaje = personajes.get(i);
			System.out.println("Nombre: " + personaje.get(0));
			System.out.println("Tipo: " + personaje.get(1));
			System.out.println("Vida actual: " + personaje.get(2));
			System.out.println("Mana actual: " + personaje.get(3));
			System.out.println("Esta vivo: " + personaje.get(4));
			System.out.println("Equipo: " + personaje.get(5));
			System.out.println("--------------------");
		}

		String sqlDelete = "DELETE FROM Partida WHERE id_partida = ?";
		parametros = new ArrayList<Object>();

		// Borramos la partida de prueba
		parametros.add(100);
		Utils.deleteData(sqlDelete, parametros);
	}
}
