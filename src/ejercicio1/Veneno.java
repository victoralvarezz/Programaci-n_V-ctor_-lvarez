package ejercicio1;

/**
 * Estado de danio por turnos que representa veneno
 */
public class Veneno extends Estado {

	// Guarda cuanto danio hace el veneno en cada turno
	private int danioPorTurno;

	// Crea un estado de veneno que dura 3 turnos.
	public Veneno() {
		// Llama al constructor de Estado para guardar el nombre y la duracion
		super("Veneno", 3);
		this.danioPorTurno = 4;
	}

	// Aplica el efecto del veneno sobre el personaje
	@Override
	public void aplicar(persona.Personaje objetivo) {
		// Si el estado ya no esta activo o el personaje esta muerto, no hace nada
		if (!estaActivo() || !objetivo.estaVivo())
			return;

		// Aplica el danio directo, sin tener en cuenta la defensa
		objetivo.recibirDanioDirecto(danioPorTurno);

		System.out.println("  [Veneno] " + objetivo.getNombre() + " sufre " + danioPorTurno + " de danio");

		// Despues de aplicarse, se reduce un turno de duracion
		reducirTurno();
	}
}
