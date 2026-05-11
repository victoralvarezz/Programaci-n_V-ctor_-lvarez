package ejercicio1;

/**
 * Clase abstracta que representa un estado aplicado a un personaje.
 */
public abstract class Estado {

	// Guarda el nombre del estado, por ejemplo Veneno o Quemadura.
	protected String nombre;

	// Guarda cuantos turnos le quedan al estado.
	protected int turnosRestantes;

	// Crea un estado con su nombre y los turnos que dura.
	public Estado(String nombre, int turnosRestantes) {
		this.nombre = nombre;
		this.turnosRestantes = turnosRestantes;
	}

	// Devuelve el nombre del estado.
	public String getNombre() {
		return nombre;
	}

	// Devuelve cuantos turnos quedan.
	public int getTurnosRestantes() {
		return turnosRestantes;
	}

	// Comprueba si el estado todavia tiene turnos.
	public boolean estaActivo() {
		return turnosRestantes > 0;
	}

	// Resta un turno de duracion al estado.
	public void reducirTurno() {
		turnosRestantes--;
	}

	// Cada estado hijo define aqui que efecto aplica al personaje.
	public abstract void aplicar(persona.Personaje objetivo);
}
