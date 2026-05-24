package ejercicio1;

import BBDD.GestorGraficos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;

/**
 * Menu para consultar graficos con XChart.
 */
public class MenuGraficos {

	/**
	 * Muestra el menu de graficos.
	 *
	 * @param sc scanner de la aplicacion
	 */
	public static void verGraficos(Scanner sc) {
		// Mostramos el menu de graficos
		System.out.println("=== GRAFICOS ===");
		System.out.println("1) Vida maxima de personajes");
		System.out.println("2) Ataque de personajes");
		System.out.println("3) Mana maximo de personajes");
		System.out.println("4) Vida de enemigos por dificultad");
		System.out.println("5) Hechizos");
		System.out.println("6) Estados");
		System.out.println("7) Volver");

		int opcion = leerEntero(sc, 1, 7);

		if (opcion == 1)
			mostrarGraficoVidaPersonajes();
		else if (opcion == 2)
			mostrarGraficoAtaquePersonajes();
		else if (opcion == 3)
			mostrarGraficoManaPersonajes();
		else if (opcion == 4)
			mostrarGraficoEnemigos();
		else if (opcion == 5)
			mostrarGraficoHechizos();
		else if (opcion == 6)
			mostrarGraficoEstados();
	}

	/**
	 * Muestra el grafico de vida maxima.
	 */
	private static void mostrarGraficoVidaPersonajes() {
		// Cargamos los datos desde MySQL
		List<List<Object>> datos = GestorGraficos.datosVidaPersonajes();
		mostrarGraficoSimple(datos, "Vida maxima de personajes", "Personajes", "Vida maxima", "Vida maxima", 1);
	}

	/**
	 * Muestra el grafico de ataque.
	 */
	private static void mostrarGraficoAtaquePersonajes() {
		// Cargamos los datos desde MySQL
		List<List<Object>> datos = GestorGraficos.datosAtaquePersonajes();
		mostrarGraficoSimple(datos, "Ataque de personajes", "Personajes", "Ataque", "Ataque", 1);
	}

	/**
	 * Muestra el grafico de mana maximo.
	 */
	private static void mostrarGraficoManaPersonajes() {
		// Cargamos los datos desde MySQL
		List<List<Object>> datos = GestorGraficos.datosManaPersonajes();
		mostrarGraficoSimple(datos, "Mana maximo de personajes", "Personajes", "Mana maximo", "Mana maximo", 1);
	}

	/**
	 * Muestra el grafico de enemigos por dificultad.
	 */
	private static void mostrarGraficoEnemigos() {
		// Cargamos los datos desde MySQL
		List<List<Object>> datos = GestorGraficos.datosEnemigos();
		List<String> nombres = new ArrayList<String>();
		List<Integer> facil = new ArrayList<Integer>();
		List<Integer> normal = new ArrayList<Integer>();
		List<Integer> dificil = new ArrayList<Integer>();

		agregarVidaEnemigo(datos, nombres, facil, normal, dificil, "Darth Vader");
		agregarVidaEnemigo(datos, nombres, facil, normal, dificil, "Stormtrooper");
		agregarVidaEnemigo(datos, nombres, facil, normal, dificil, "Boba Fett");

		// Creamos el grafico con XChart
		CategoryChart chart = new CategoryChartBuilder().width(800).height(600)
				.title("Vida de enemigos por dificultad").xAxisTitle("Enemigos").yAxisTitle("Vida").build();
		chart.addSeries("Facil", nombres, facil);
		chart.addSeries("Normal", nombres, normal);
		chart.addSeries("Dificil", nombres, dificil);

		// Mostramos el grafico
		new SwingWrapper<CategoryChart>(chart).displayChart();
	}

	/**
	 * Muestra el grafico de hechizos.
	 */
	private static void mostrarGraficoHechizos() {
		// Cargamos los datos desde MySQL
		List<List<Object>> datos = GestorGraficos.datosHechizos();
		List<String> nombres = new ArrayList<String>();
		List<Integer> costeMana = new ArrayList<Integer>();
		List<Integer> danio = new ArrayList<Integer>();

		for (int i = 0; i < datos.size(); i++) {
			List<Object> fila = datos.get(i);
			nombres.add(String.valueOf(fila.get(0)));
			costeMana.add(Integer.parseInt(String.valueOf(fila.get(1))));
			danio.add(Integer.parseInt(String.valueOf(fila.get(2))));
		}

		// Creamos el grafico con XChart
		CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Hechizos")
				.xAxisTitle("Hechizos").yAxisTitle("Valor").build();
		chart.addSeries("Coste mana", nombres, costeMana);
		chart.addSeries("Danio", nombres, danio);

		// Mostramos el grafico
		new SwingWrapper<CategoryChart>(chart).displayChart();
	}

	/**
	 * Muestra el grafico de estados.
	 */
	private static void mostrarGraficoEstados() {
		// Cargamos los datos desde MySQL
		List<List<Object>> datos = GestorGraficos.datosEstados();
		List<String> nombres = new ArrayList<String>();
		List<Integer> turnos = new ArrayList<Integer>();
		List<Integer> potencia = new ArrayList<Integer>();

		for (int i = 0; i < datos.size(); i++) {
			List<Object> fila = datos.get(i);
			nombres.add(String.valueOf(fila.get(0)));
			turnos.add(Integer.parseInt(String.valueOf(fila.get(1))));
			potencia.add(Integer.parseInt(String.valueOf(fila.get(2))));
		}

		// Creamos el grafico con XChart
		CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Estados")
				.xAxisTitle("Estados").yAxisTitle("Valor").build();
		chart.addSeries("Turnos", nombres, turnos);
		chart.addSeries("Potencia", nombres, potencia);

		// Mostramos el grafico
		new SwingWrapper<CategoryChart>(chart).displayChart();
	}

	/**
	 * Muestra un grafico sencillo con una serie.
	 *
	 * @param datos       datos de MySQL
	 * @param titulo      titulo del grafico
	 * @param ejeX        texto del eje X
	 * @param ejeY        texto del eje Y
	 * @param nombreSerie nombre de la serie
	 * @param columna     columna del valor
	 */
	private static void mostrarGraficoSimple(List<List<Object>> datos, String titulo, String ejeX, String ejeY,
			String nombreSerie, int columna) {
		List<String> nombres = new ArrayList<String>();
		List<Integer> valores = new ArrayList<Integer>();

		for (int i = 0; i < datos.size(); i++) {
			List<Object> fila = datos.get(i);
			nombres.add(String.valueOf(fila.get(0)));
			valores.add(Integer.parseInt(String.valueOf(fila.get(columna))));
		}

		// Creamos el grafico con XChart
		CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title(titulo).xAxisTitle(ejeX)
				.yAxisTitle(ejeY).build();
		chart.addSeries(nombreSerie, nombres, valores);

		// Mostramos el grafico
		new SwingWrapper<CategoryChart>(chart).displayChart();
	}

	/**
	 * Agrega la vida de un enemigo a las listas del grafico.
	 *
	 * @param datos   datos de enemigos
	 * @param nombres nombres del grafico
	 * @param facil   valores de facil
	 * @param normal  valores de normal
	 * @param dificil valores de dificil
	 * @param enemigo nombre del enemigo
	 */
	private static void agregarVidaEnemigo(List<List<Object>> datos, List<String> nombres, List<Integer> facil,
			List<Integer> normal, List<Integer> dificil, String enemigo) {
		int vida = buscarVidaEnemigo(datos, enemigo);
		int vidaFacil = vida - 20;

		if (vidaFacil < 1)
			vidaFacil = 1;

		nombres.add(enemigo);
		facil.add(vidaFacil);
		normal.add(vida);
		dificil.add(vida + 30);
	}

	/**
	 * Busca la vida maxima de un enemigo.
	 *
	 * @param datos   datos de enemigos
	 * @param enemigo nombre del enemigo
	 * @return vida maxima
	 */
	private static int buscarVidaEnemigo(List<List<Object>> datos, String enemigo) {
		for (int i = 0; i < datos.size(); i++) {
			List<Object> fila = datos.get(i);
			String nombre = String.valueOf(fila.get(0));

			if (nombre.equals(enemigo))
				return Integer.parseInt(String.valueOf(fila.get(1)));
		}

		return 0;
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
