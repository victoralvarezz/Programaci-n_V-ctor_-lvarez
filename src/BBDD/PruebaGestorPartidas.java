package BBDD;

import java.util.List;

public class PruebaGestorPartidas {

	public static void main(String[] args) {
		// Creamos una partida
		GestorPartidas.crearPartida(102, 2);

		// Guardamos personajes
		GestorPartidas.guardarPersonajePartida(102, 1, 0, 130, 20, true, "Jugador");
		GestorPartidas.guardarPersonajePartida(102, 2, 0, 130, 25, true, "Enemigo");

		// Actualizamos la partida
		GestorPartidas.actualizarPartida(102, 1, 0, true);

		// Listamos partidas
		System.out.println("=== PARTIDAS ===");
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

		// Cargamos el turno
		System.out.println("=== TURNO 0 ===");
		List<List<Object>> personajes = GestorPartidas.cargarTurno(102, 0);

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

		// Borramos la partida de prueba
		GestorPartidas.borrarPartida(102);
	}
}
