package ejercicio1;

public abstract class Estado {
	protected String nombre;
	protected int turnosRestantes;
	protected int potenciaPorTurno;

	public Estado(String nombre, int potenciaPorTurno, int turnosRestantes) {
		this.nombre = nombre;
		this.potenciaPorTurno = potenciaPorTurno;
		this.turnosRestantes = turnosRestantes;
	}

	// cada estado aplica su efecto cada una el suyo
	public abstract void aplicarEfecto(persona.Personaje objetivo);
} 