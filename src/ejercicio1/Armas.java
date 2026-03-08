package ejercicio1;

public abstract class Armas {
	protected String nombre;
	protected int danioBase;

	public Armas(String nombre, int danioBase) {
		this.nombre = nombre;
		this.danioBase = danioBase;
	}

	// cada arma calcula el daño a su manera
	public abstract int calcularDanio(persona.Personaje atacante, persona.Personaje defensor);

	// SABLE DE LUZ = cuerpo a cuerpo (Jedi y Sith)
	public static class SableLuz extends Armas {

		public SableLuz() {
			super("Sable de Luz", 10); // daño base 10
		}

		@Override
		public int calcularDanio(persona.Personaje atacante, persona.Personaje defensor) {
			// daño base + ataque del personaje
			return danioBase + atacante.ataque;
		}
	}

	// BLASTER = a distancia (SoldadoRebelde, SoldadoImperial, Cazarrecompensas)
	public static class Blaster extends Armas {

		public Blaster() {
			super("Blaster", 8); // daño base menor
		}

		@Override
		public int calcularDanio(persona.Personaje atacante, persona.Personaje defensor) {
			// daño base + mitad del ataque del personaje
			return danioBase + (atacante.ataque / 2);
		}
	}
}