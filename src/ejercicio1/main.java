package ejercicio1;

import BBDD.GestorPartidas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Clase principal que controla el flujo del juego RPG por turnos. Gestiona el
 * menu inicial, la creacion de equipos, el bucle de combate y el resumen final
 * de la partida. Usa un HashMap para el catalogo de personajes disponibles.
 *
 * @author Victor
 * @version 1.0
 */
public class main {

	/** Scanner para leer opciones del jugador por consola. */
	static Scanner sc = new Scanner(System.in);

	/** Mapa que relaciona un numero con el nombre del tipo de personaje. */
	static Map<Integer, String> menuPersonajes = new HashMap<Integer, String>();

	static {
		menuPersonajes.put(1, "Jedi");
		menuPersonajes.put(2, "Sith");
		menuPersonajes.put(3, "Soldado Rebelde");
		menuPersonajes.put(4, "Soldado Imperial");
		menuPersonajes.put(5, "Cazarrecompensas");
		menuPersonajes.put(6, "Sanador");
	}

	/**
	 * Punto de entrada del programa. Muestra el menu, configura los equipos y
	 * ejecuta el bucle de combate.
	 *
	 * @param args argumentos de la linea de comandos (no se usan)
	 */
	public static void main(String[] args) {

		System.out.println("=== RPG POR TURNOS ===");
		System.out.println("1) Nueva partida");
		System.out.println("2) Cargar partida guardada");
		System.out.println("3) Listar partidas guardadas");
		System.out.println("4) Borrar partida guardada");
		System.out.println("5) Salir");

		int opcionInicio = leerEntero(1, 5);

		if (opcionInicio == 2) {
			cargarPartidaGuardada();
			sc.close();
			return;
		}

		if (opcionInicio == 3) {
			listarPartidasGuardadas();
			sc.close();
			return;
		}

		if (opcionInicio == 4) {
			borrarPartidaGuardada();
			sc.close();
			return;
		}

		if (opcionInicio == 5) {
			System.out.println("Hasta luego.");
			sc.close();
			return;
		}

		System.out.println("\nElige dificultad:");
		System.out.println("1) Facil");
		System.out.println("2) Normal");
		System.out.println("3) Dificil");

		int dificultad = leerEntero(1, 3);

		System.out.println("\nElige modo:");
		System.out.println("1) Combate automatico");
		System.out.println("2) Elegir mi equipo y jugar manual");

		int modo = leerEntero(1, 2);

		System.out.println("\nIntroduce id para guardar esta partida:");
		// Pedimos el id de la partida
		int idPartida = leerEntero(1, 9999);

		persona.Personaje[] equipoA;
		persona.Personaje[] equipoB;

		if (modo == 1) {
			equipoA = new persona.Personaje[] { new Jedi("Yoda"), new SoldadoRebelde("Han Solo"),
					new Sanador("Leia") };
			equipoB = new persona.Personaje[] { new Sith("Darth Vader"),
					new SoldadoImperial("Stormtrooper"), new Cazarrecompensas("Boba Fett") };
			System.out.println("\nEquipo A: Yoda / Han Solo / Leia");
			System.out.println("Equipo B: Darth Vader / Stormtrooper / Boba Fett");
		} else {
			System.out.println("\n=== ELIGE TU EQUIPO ===");
			equipoA = elegirEquipo("Tu equipo");
			equipoB = new persona.Personaje[] { new Sith("Darth Vader"),
					new SoldadoImperial("Stormtrooper"), new Cazarrecompensas("Boba Fett") };
			System.out.println("\nEquipo enemigo: Darth Vader / Stormtrooper / Boba Fett");
		}

		aplicarDificultad(equipoB, dificultad);

		int ronda = 1;

		// Creamos la partida en la base de datos
		GestorPartidas.borrarPartida(idPartida);
		GestorPartidas.crearPartida(idPartida, dificultad);

		// Guardamos los equipos al empezar
		guardarEquipoPartida(equipoA, idPartida, 0, "Jugador");
		guardarEquipoPartida(equipoB, idPartida, 0, "Enemigo");
		GestorPartidas.actualizarPartida(idPartida, 0, 0, true);

		System.out.println("\n=== COMBATE INICIADO ===");

		while (hayVivos(equipoA) && hayVivos(equipoB)) {

			System.out.println("\n===== RONDA " + ronda + " =====");
			sleep(1700);

			System.out.println("\n-- Equipo A --");
			for (persona.Personaje p : equipoA)
				if (p != null)
					mostrarPersonaje(p);

			System.out.println("\n-- Equipo B --");
			for (persona.Personaje p : equipoB)
				if (p != null)
					mostrarPersonaje(p);

			System.out.println("\n-- Turno Equipo A --");
			for (persona.Personaje p : equipoA) {
				if (p != null && p.estaVivo()) {
					if (modo == 1)
						p.hacerTurno(equipoB, equipoA);
					else
						turnoJugador(p, equipoB, equipoA);
					sleep(1700);
				}
			}

			if (!hayVivos(equipoB)) {
				// Guardamos la ronda actual
				guardarEquipoPartida(equipoA, idPartida, ronda, "Jugador");
				guardarEquipoPartida(equipoB, idPartida, ronda, "Enemigo");
				GestorPartidas.actualizarPartida(idPartida, ronda, ronda, true);
				break;
			}

			System.out.println("\n-- Turno Equipo B --");
			for (persona.Personaje p : equipoB) {
				if (p != null && p.estaVivo()) {
					p.hacerTurno(equipoA, equipoB);
					sleep(1700);
				}
			}

			System.out.println("\n-- Estados y mana --");
			procesarEquipo(equipoA);
			procesarEquipo(equipoB);
			sleep(1700);

			// Guardamos la ronda actual
			guardarEquipoPartida(equipoA, idPartida, ronda, "Jugador");
			guardarEquipoPartida(equipoB, idPartida, ronda, "Enemigo");
			GestorPartidas.actualizarPartida(idPartida, ronda, ronda, true);

			ronda++;
		}

		System.out.println("\n=== FIN DEL COMBATE ===");

		if (!hayVivos(equipoB) && hayVivos(equipoA))
			System.out.println("Gana el Equipo A");
		else if (!hayVivos(equipoA) && hayVivos(equipoB))
			System.out.println("Gana el Equipo B");
		else
			System.out.println("Empate");

		System.out.println("\n--- Equipo A ---");
		for (persona.Personaje p : equipoA)
			if (p != null)
				System.out.println("  " + p.nombre + " | Vida: " + p.vida + "/" + p.vidaMax + " | "
						+ (p.estaVivo() ? "VIVO" : "ELIMINADO"));

		System.out.println("\n--- Equipo B ---");
		for (persona.Personaje p : equipoB)
			if (p != null)
				System.out.println("  " + p.nombre + " | Vida: " + p.vida + "/" + p.vidaMax + " | "
						+ (p.estaVivo() ? "VIVO" : "ELIMINADO"));

		sc.close();
	}

	/**
	 * Lista las partidas guardadas.
	 *
	 * @return lista de partidas guardadas
	 */
	private static List<List<Object>> listarPartidasGuardadas() {
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
	 * Carga una partida guardada y muestra sus personajes.
	 */
	private static void cargarPartidaGuardada() {
		// Cargamos una partida guardada
		List<List<Object>> partidas = listarPartidasGuardadas();

		if (partidas.size() == 0) {
			System.out.println("No hay partidas guardadas.");
			return;
		}

		System.out.println("Introduce id de partida a cargar:");
		int idPartida = leerEntero(1, 9999);
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
	 * Borra una partida guardada.
	 */
	private static void borrarPartidaGuardada() {
		// Borramos una partida guardada
		List<List<Object>> partidas = listarPartidasGuardadas();

		if (partidas.size() == 0) {
			System.out.println("No hay partidas guardadas.");
			return;
		}

		System.out.println("Introduce id de partida a borrar:");
		int idPartida = leerEntero(1, 9999);

		GestorPartidas.borrarPartida(idPartida);
		System.out.println("Partida borrada.");
	}

	/**
	 * Guarda los personajes de un equipo en la partida.
	 *
	 * @param equipo    equipo que se va a guardar
	 * @param idPartida id de la partida
	 * @param turno     ronda que se esta guardando
	 * @param nombreEquipo nombre del equipo
	 */
	private static void guardarEquipoPartida(persona.Personaje[] equipo, int idPartida, int turno, String nombreEquipo) {
		for (persona.Personaje p : equipo) {
			if (p != null) {
				GestorPartidas.guardarPersonajePartida(idPartida, obtenerIdPersonaje(p), turno, p.vida, p.mana,
						p.estaVivo(), nombreEquipo);
			}
		}
	}

	/**
	 * Devuelve el id del personaje segun su nombre.
	 *
	 * @param p personaje del juego
	 * @return id del personaje en la base de datos
	 */
	private static int obtenerIdPersonaje(persona.Personaje p) {
		if (p.nombre.startsWith("Yoda"))
			return 1;
		if (p.nombre.startsWith("Darth Vader"))
			return 2;
		if (p.nombre.startsWith("Leia"))
			return 3;
		if (p.nombre.startsWith("Han Solo"))
			return 4;
		if (p.nombre.startsWith("Stormtrooper"))
			return 5;
		if (p.nombre.startsWith("Boba Fett"))
			return 6;
		return 0;
	}

	/**
	 * Gestiona el turno manual del jugador mostrando las acciones disponibles segun
	 * el tipo de personaje y pidiendo objetivo si es necesario.
	 *
	 * @param p        el personaje que actua
	 * @param enemigos array del equipo enemigo
	 * @param aliados  array del equipo aliado
	 */
	private static void turnoJugador(persona.Personaje p, persona.Personaje[] enemigos, persona.Personaje[] aliados) {

		System.out.println("\nTurno de " + p.nombre);

		if (p instanceof Jedi) {
			Jedi jedi = (Jedi) p;
			System.out.println("1) Ataque basico");
			System.out.println("2) Empujon Fuerza");
			System.out.println("3) Aplastamiento");
			System.out.println("4) Telequinesis");
			int op = leerEntero(1, 4);
			if (op == 1)
				p.ataqueBasico(elegirObjetivo(enemigos));
			else if (op == 2)
				jedi.usarEmpujon(enemigos);
			else if (op == 3)
				jedi.usarAplastamiento(elegirObjetivo(enemigos));
			else
				jedi.usarTelequinesis(elegirObjetivo(enemigos));

		} else if (p instanceof Sith) {
			Sith sith = (Sith) p;
			System.out.println("1) Ataque basico");
			System.out.println("2) Empujon Fuerza");
			System.out.println("3) Aplastamiento");
			System.out.println("4) Estrangulamiento");
			int op = leerEntero(1, 4);
			if (op == 1) {
				persona.Personaje obj = elegirObjetivo(enemigos);
				if (obj != null)
					p.ataqueBasico(obj);
			} else if (op == 2)
				sith.usarEmpujon(enemigos);
			else if (op == 3) {
				persona.Personaje obj = elegirObjetivo(enemigos);
				if (obj != null)
					sith.usarAplastamiento(obj);
			} else {
				persona.Personaje obj = elegirObjetivo(enemigos);
				if (obj != null)
					sith.usarEstrangulamiento(obj);
			}

		} else if (p instanceof Cazarrecompensas) {
			Cazarrecompensas cazarrecompensas = (Cazarrecompensas) p;
			System.out.println("1) Ataque basico");
			System.out.println("2) Quemadura");
			int op = leerEntero(1, 2);
			if (op == 1)
				p.ataqueBasico(elegirObjetivo(enemigos));
			else
				cazarrecompensas.usarQuemadura(elegirObjetivo(enemigos));

		} else if (p instanceof Sanador) {
			Sanador sanador = (Sanador) p;
			System.out.println("1) Ataque basico");
			System.out.println("2) Curacion");
			System.out.println("3) Renovar");
			int op = leerEntero(1, 3);
			if (op == 1)
				p.ataqueBasico(elegirObjetivo(enemigos));
			else if (op == 2)
				sanador.usarCuracion(elegirObjetivo(aliados));
			else
				sanador.usarRenovar(elegirObjetivo(aliados));

		} else {
			System.out.println("1) Ataque basico");
			System.out.println("2) Granada venenosa");
			int op = leerEntero(1, 2);
			if (op == 1)
				p.ataqueBasico(elegirObjetivo(enemigos));
			else if (p instanceof SoldadoRebelde) {
				SoldadoRebelde soldado = (SoldadoRebelde) p;
				soldado.usarVeneno(elegirObjetivo(enemigos));
			} else if (p instanceof SoldadoImperial) {
				SoldadoImperial soldado = (SoldadoImperial) p;
				soldado.usarVeneno(elegirObjetivo(enemigos));
			}
		}
	}

	/**
	 * Muestra la lista de personajes vivos del equipo y devuelve el elegido. No
	 * permite seleccionar personajes muertos.
	 *
	 * @param equipo array del equipo del que se elige objetivo
	 * @return el personaje vivo seleccionado por el jugador
	 */
	private static persona.Personaje elegirObjetivo(persona.Personaje[] equipo) {
		System.out.println("Elige objetivo:");
		for (int i = 0; i < equipo.length; i++) {
			if (equipo[i] != null && equipo[i].estaVivo())
				System.out.println((i + 1) + ") " + equipo[i].nombre + " (" + equipo[i].vida + " vida)");
			else
				System.out.println((i + 1) + ") [ELIMINADO]");
		}
		while (true) {
			int op = leerEntero(1, equipo.length);
			if (equipo[op - 1] != null && equipo[op - 1].estaVivo())
				return equipo[op - 1];
			System.out.println("Ese objetivo no vale.");
		}
	}

	/**
	 * Permite al jugador elegir 3 personajes para su equipo con repeticiones
	 * permitidas.
	 *
	 * @param etiqueta nombre del equipo que se muestra en pantalla
	 * @return array de 3 personajes creados segun la eleccion del jugador
	 */
	private static persona.Personaje[] elegirEquipo(String etiqueta) {
		persona.Personaje[] equipo = new persona.Personaje[3];
		int[] repetidos = new int[7];
		for (int i = 0; i < 3; i++) {
			System.out.println("\n[" + etiqueta + "] Personaje " + (i + 1));
			for (int j = 1; j <= 6; j++)
				System.out.println(j + ") " + menuPersonajes.get(j));
			int tipo = leerEntero(1, 6);
			repetidos[tipo]++;
			String nombre = nombreAutomatico(tipo, repetidos[tipo]);
			equipo[i] = crearPersonaje(tipo, nombre);
			System.out.println("Has elegido a " + equipo[i].nombre);
		}
		return equipo;
	}

	/**
	 * Devuelve el nombre automatico de un personaje segun su tipo y numero de
	 * repeticion.
	 *
	 * @param tipo   tipo de personaje (1-6)
	 * @param numero numero de veces que se ha repetido ese tipo
	 * @return nombre asignado al personaje
	 */
	private static String nombreAutomatico(int tipo, int numero) {
		if (tipo == 1)
			return numero == 1 ? "Yoda" : "Yoda " + numero;
		if (tipo == 2)
			return numero == 1 ? "Darth Vader" : "Darth Vader " + numero;
		if (tipo == 3)
			return numero == 1 ? "Han Solo" : "Han Solo " + numero;
		if (tipo == 4)
			return numero == 1 ? "Stormtrooper" : "Stormtrooper " + numero;
		if (tipo == 5)
			return numero == 1 ? "Boba Fett" : "Boba Fett " + numero;
		if (tipo == 6)
			return numero == 1 ? "Leia" : "Leia " + numero;
		return "Personaje";
	}

	/**
	 * Crea un personaje del tipo indicado con el nombre dado.
	 *
	 * @param tipo   tipo de personaje (1=Jedi, 2=Sith, 3=SoldadoRebelde,
	 *               4=SoldadoImperial, 5=Cazarrecompensas, 6=Sanador)
	 * @param nombre nombre del personaje
	 * @return instancia del personaje creado
	 */
	private static persona.Personaje crearPersonaje(int tipo, String nombre) {
		if (tipo == 1)
			return new Jedi(nombre);
		if (tipo == 2)
			return new Sith(nombre);
		if (tipo == 3)
			return new SoldadoRebelde(nombre);
		if (tipo == 4)
			return new SoldadoImperial(nombre);
		if (tipo == 5)
			return new Cazarrecompensas(nombre);
		return new Sanador(nombre);
	}

	/**
	 * Muestra en consola el estado actual de un personaje. Incluye vida, mana y
	 * estados activos con sus turnos restantes.
	 *
	 * @param p el personaje a mostrar
	 */
	private static void mostrarPersonaje(persona.Personaje p) {
		if (!p.estaVivo()) {
			System.out.println("  [ELIMINADO] " + p.nombre);
			return;
		}
		System.out.print(
				"  " + p.nombre + " | Vida: " + p.vida + "/" + p.vidaMax + " | Mana: " + p.mana + "/" + p.manaMax);
		if (p.numEstados > 0) {
			System.out.print(" | Estados: ");
			for (int i = 0; i < p.numEstados; i++) {
				System.out.print(p.estados[i].nombre + "(" + p.estados[i].turnosRestantes + ")");
				if (i < p.numEstados - 1)
					System.out.print(", ");
			}
		} else {
			System.out.print(" | Estados: ninguno");
		}
		System.out.println();
	}

	/**
	 * Cambia las estadisticas del equipo enemigo segun la dificultad elegida.
	 *
	 * @param enemigos   equipo enemigo que se va a modificar
	 * @param dificultad dificultad elegida por el jugador
	 */
	private static void aplicarDificultad(persona.Personaje[] enemigos, int dificultad) {
		for (persona.Personaje enemigo : enemigos) {
			if (enemigo != null) {
				// Solo se cambian los enemigos para hacer el combate mas facil o dificil.
				if (dificultad == 1) {
					enemigo.vidaMax = Math.max(1, enemigo.vidaMax - 20);
					enemigo.vida = Math.max(1, enemigo.vida - 20);
					enemigo.ataque = Math.max(1, enemigo.ataque - 3);
				} else if (dificultad == 3) {
					enemigo.vidaMax = enemigo.vidaMax + 30;
					enemigo.vida = enemigo.vida + 30;
					enemigo.ataque = enemigo.ataque + 5;
				}
			}
		}
	}

	/**
	 * Comprueba si algun personaje del equipo sigue vivo.
	 *
	 * @param equipo array del equipo a comprobar
	 * @return true si hay al menos un personaje vivo, false si todos estan
	 *         eliminados
	 */
	public static boolean hayVivos(persona.Personaje[] equipo) {
		for (persona.Personaje p : equipo)
			if (p != null && p.estaVivo())
				return true;
		return false;
	}

	/**
	 * Procesa los estados activos y regenera el mana de todos los personajes vivos
	 * del equipo.
	 *
	 * @param equipo array del equipo a procesar
	 */
	public static void procesarEquipo(persona.Personaje[] equipo) {
		for (persona.Personaje p : equipo)
			if (p != null && p.estaVivo()) {
				p.procesarEstados();
				p.regenerarMana();
			}
	}

	/**
	 * Lee un entero por consola dentro del rango indicado. Repite la lectura si el
	 * valor no es valido.
	 *
	 * @param min valor minimo aceptado
	 * @param max valor maximo aceptado
	 * @return entero valido introducido por el jugador
	 */
	private static int leerEntero(int min, int max) {
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

	/**
	 * Pausa la ejecucion el numero de milisegundos indicado. Se usa para que el
	 * combate sea mas facil de seguir en consola.
	 *
	 * @param ms milisegundos a esperar
	 */
	public static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
