package ejercicio1;

public class Senadora extends Estado {

	// RENOVAR = curación por turnos (la usa el Sanador)
	public Senadora() {
		super("Renovar", 10, 2); // cura 10 durante 2 turnos
	}

	@Override
	public void aplicarEfecto(persona.Personaje objetivo) {
		if (!objetivo.estaVivo())
			return;
		objetivo.vida += potenciaPorTurno;
		if (objetivo.vida > objetivo.vidaMax)
			objetivo.vida = objetivo.vidaMax;
		System.out.println("  [Renovar] " + objetivo.nombre + " recupera " + potenciaPorTurno + " de vida | Vida: "
				+ objetivo.vida);
		turnosRestantes--;
	}
}