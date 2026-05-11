package ejercicio1;

/**
 * Clase contenedora de todas las clases de personaje del juego. Contiene la
 * clase abstracta base Personaje y varias subclases: Sith, SoldadoRebelde,
 * SoldadoImperial, Cazarrecompensas y Sanador.
 *
 * @author Victor
 * @version 1.0
 */
public class persona {

	/**
	 * Clase abstracta que representa cualquier personaje jugable o enemigo. Define
	 * los atributos comunes y los metodos que el motor de combate necesita invocar.
	 * Cada subclase define su propio comportamiento en hacerTurno.
	 *
	 * @author Victor
	 * @version 1.0
	 */
	public static abstract class Personaje {

		/** Nombre del personaje que se muestra en combate. */
		protected String nombre;

		/** Vida actual del personaje. Nunca baja de 0. */
		protected int vida;

		/** Vida maxima del personaje. */
		protected int vidaMax;

		/** Estadistica de ataque, usada en el calculo de daño del arma. */
		protected int ataque;

		/** Estadistica de defensa, reduce el daño recibido en ataques fisicos. */
		protected int defensa;

		/** Mana actual del personaje. */
		protected int mana;

		/** Mana maximo del personaje. */
		protected int manaMax;

		/** Arma equipada por el personaje. */
		// Aqui se guarda el arma que tiene este personaje.
		protected Armas arma;

		/** Array de estados activos. Maximo 5 simultaneos. */
		protected Estado[] estados = new Estado[5];

		/** Numero de estados activos actualmente. */
		protected int numEstados = 0;

		/**
		 * Constructor de Personaje.
		 *
		 * @param nombre  nombre del personaje
		 * @param vida    vida maxima inicial
		 * @param ataque  estadistica de ataque
		 * @param defensa estadistica de defensa
		 * @param mana    mana maximo inicial
		 */
		public Personaje(String nombre, int vida, int ataque, int defensa, int mana) {
			this.nombre = nombre;
			this.vida = vida;
			this.vidaMax = vida;
			this.ataque = ataque;
			this.defensa = defensa;
			this.mana = mana;
			this.manaMax = mana;
		}

		/**
		 * Comprueba si el personaje sigue vivo en combate.
		 *
		 * @return true si vida mayor que 0, false si esta eliminado
		 */
		public boolean estaVivo() {
			return vida > 0;
		}

		public String getNombre() {
			return nombre;
		}

		/**
		 * Aplica daño al personaje restando primero la defensa. El daño minimo aplicado
		 * es 1. La vida nunca baja de 0.
		 *
		 * @param danio cantidad de daño bruto recibido
		 */
		public void recibirDanio(int danio) {
			int real = danio - defensa;
			if (real < 1)
				real = 1;
			vida -= real;
			if (vida < 0)
				vida = 0;
		}

		public void recibirDanioDirecto(int cantidad) {
			vida -= cantidad;
			if (vida < 0)
				vida = 0;
		}

		public void curar(int cantidad) {
			vida += cantidad;
			if (vida > vidaMax)
				vida = vidaMax;
		}

		/**
		 * Regenera 3 puntos de mana al final de cada ronda. No supera el mana maximo
		 * del personaje.
		 */
		public void regenerarMana() {
			mana += 3;
			if (mana > manaMax)
				mana = manaMax;
		}

		/**
		 * Devuelve una opcion aleatoria entre 1 y max.
		 *
		 * @param max numero maximo de opciones
		 * @return opcion aleatoria
		 */
		protected int opcionAleatoria(int max) {
			return (int) (Math.random() * max) + 1;
		}

		/**
		 * Ataca si no hay mana suficiente.
		 *
		 * @param obj        personaje objetivo
		 * @param manaMinimo mana minimo necesario
		 * @return true si ha atacado, false si hay mana suficiente
		 */
		protected boolean atacarSiNoHayMana(Personaje obj, int manaMinimo) {
			if (mana < manaMinimo) {
				ataqueBasico(obj);
				return true;
			}
			return false;
		}

		/**
		 * Aplica un nuevo estado respetando la politica de stacking. Si ya existe un
		 * estado con el mismo nombre, se renueva su duracion. Si no existe y hay hueco,
		 * se añade al array.
		 *
		 * @param nuevoEstado el estado a aplicar
		 */
		public void aplicarEstado(Estado nuevoEstado) {
			for (int i = 0; i < numEstados; i++) {
				if (estados[i].getNombre().equals(nuevoEstado.getNombre())) {
					estados[i].turnosRestantes = nuevoEstado.getTurnosRestantes();
					System.out.println("  [" + nuevoEstado.getNombre() + "] renovado en " + nombre + " ("
							+ estados[i].getTurnosRestantes() + " turnos)");
					return;
				}
			}
			if (numEstados < estados.length) {
				estados[numEstados] = nuevoEstado;
				numEstados++;
				System.out.println("  [" + nuevoEstado.getNombre() + "] aplicado a " + nombre + " ("
						+ nuevoEstado.getTurnosRestantes() + " turnos)");
			}
		}

		/**
		 * Procesa todos los estados activos al final de la ronda. Aplica el efecto de
		 * cada estado, reduce su duracion y elimina los expirados.
		 */
		public void procesarEstados() {
			int i = 0;
			while (i < numEstados) {
				estados[i].aplicar(this);
				if (!estados[i].estaActivo()) {
					System.out.println("  [" + estados[i].getNombre() + "] ha expirado en " + nombre);
					estados[i] = estados[numEstados - 1];
					estados[numEstados - 1] = null;
					numEstados--;
				} else {
					i++;
				}
			}
		}

		public void aplicarEstados() {
			procesarEstados();
		}

		/**
		 * Selecciona un enemigo vivo al azar del equipo dado.
		 *
		 * @param eq array del equipo enemigo
		 * @return un Personaje vivo aleatorio, o null si no hay ninguno
		 */
		protected Personaje enemigoRandom(Personaje[] eq) {
			int vivos = 0;
			for (Personaje p : eq)
				if (p != null && p.estaVivo())
					vivos++;
			if (vivos == 0)
				return null;
			int elegido = (int) (Math.random() * vivos);
			int cont = 0;
			for (Personaje p : eq) {
				if (p != null && p.estaVivo()) {
					if (cont == elegido)
						return p;
					cont++;
				}
			}
			return null;
		}

		/**
		 * Cuenta cuantos personajes siguen vivos en un equipo.
		 *
		 * @param eq array del equipo a contar
		 * @return numero de personajes vivos
		 */
		protected int contarVivos(Personaje[] eq) {
			int c = 0;
			for (Personaje p : eq)
				if (p != null && p.estaVivo())
					c++;
			return c;
		}

		/**
		 * Ataque basico usando el arma equipada.
		 *
		 * @param obj personaje objetivo del ataque
		 */
		public void ataqueBasico(Personaje obj) {
			if (obj == null || !obj.estaVivo())
				return;
			// Para atacar se pide al arma que calcule el danio.
			int danio = arma.calcularDanio(this, obj);
			System.out.println(nombre + " ataca a " + obj.nombre);
			obj.recibirDanio(danio);
			System.out.println("   -> " + obj.nombre + " queda con vida " + obj.vida);
		}

		/**
		 * Define el comportamiento del personaje en su turno. Cada subclase implementa
		 * su propia logica.
		 *
		 * @param enemigos array del equipo enemigo
		 * @param aliados  array del equipo aliado
		 */
		public abstract void hacerTurno(Personaje[] enemigos, Personaje[] aliados);
	}

}
