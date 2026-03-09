package ejercicio1;

public class Quemaduras extends Estado {

	// QUEMADURA = daño por turnos (la usa Cazarrecompensas)
	public Quemaduras() {
		super("Quemadura", 5, 3); // 5 daño durante 3 turnos
	}

	@Override
	public void aplicarEfecto(persona.Personaje objetivo) {
		if (!objetivo.estaVivo())
			return;
		objetivo.vida -= potenciaPorTurno;
		if (objetivo.vida < 0)
			objetivo.vida = 0;
		System.out.println("  [Quemadura] " + objetivo.nombre + " sufre " + potenciaPorTurno + " de daño | Vida: "
				+ objetivo.vida);
		turnosRestantes--;
	}
}
