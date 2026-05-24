package ejercicio1;

/**
 * Estado de danio por turnos que representa fuego.
 */
public class Quemaduras extends Estado {

	// Guarda cuanto danio hace la quemadura en cada turno.
	private int danioPorTurno;

	// Crea un estado de quemadura que dura 3 turnos.
	public Quemaduras() {
		// Llama al constructor de Estado para guardar el nombre y la duracion.
		super("Quemadura", 3);
		this.danioPorTurno = 5;
	}

	// Aplica el efecto de la quemadura sobre el personaje.
	@Override
	public void aplicar(persona.Personaje objetivo) {
		// Si el estado ya no esta activo o el personaje esta muerto, no hace nada.
		if (!estaActivo() || !objetivo.estaVivo())
			return;

		// Aplica el danio directo, sin tener en cuenta la defensa.
		objetivo.recibirDanioDirecto(danioPorTurno);

		System.out.println("  [Quemadura] " + objetivo.getNombre() + " sufre " + danioPorTurno + " de danio");

		// Despues de aplicarse, se reduce un turno de duracion.
		reducirTurno();
	}
}
