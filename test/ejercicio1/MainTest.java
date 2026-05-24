package ejercicio1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MainTest {

	@Test
	void hayVivosDevuelveTrueSiQuedaUnPersonajeVivo() {
		// Creamos personajes
		persona.Personaje[] equipo = { new Sith("Darth Vader"), new SoldadoImperial("Stormtrooper") };

		// Matamos un personaje
		equipo[0].recibirDanioDirecto(999);

		// Comprobamos el resultado
		assertTrue(main.hayVivos(equipo));
	}

	@Test
	void hayVivosDevuelveFalseSiTodosMueren() {
		// Creamos personajes
		persona.Personaje[] equipo = { new Sith("Darth Vader"), new SoldadoImperial("Stormtrooper") };

		// Matamos personajes
		equipo[0].recibirDanioDirecto(999);
		equipo[1].recibirDanioDirecto(999);

		// Comprobamos el resultado
		assertFalse(main.hayVivos(equipo));
	}

	@Test
	void aplicarDificultadFacilBajaVidaYAtaque() {
		// Creamos personajes
		Sith sith = new Sith("Darth Vader");
		SoldadoImperial soldado = new SoldadoImperial("Stormtrooper");
		persona.Personaje[] enemigos = { sith, soldado };

		int vidaSith = sith.vida;
		int ataqueSith = sith.ataque;
		int vidaSoldado = soldado.vida;
		int ataqueSoldado = soldado.ataque;

		// Aplicamos dificultad facil
		main.aplicarDificultad(enemigos, 1);

		// Comprobamos el resultado
		assertTrue(sith.vida < vidaSith);
		assertTrue(sith.ataque < ataqueSith);
		assertTrue(soldado.vida < vidaSoldado);
		assertTrue(soldado.ataque < ataqueSoldado);
	}

	@Test
	void aplicarDificultadDificilSubeVidaYAtaque() {
		// Creamos personajes
		Sith sith = new Sith("Darth Vader");
		SoldadoImperial soldado = new SoldadoImperial("Stormtrooper");
		persona.Personaje[] enemigos = { sith, soldado };

		int vidaSith = sith.vida;
		int ataqueSith = sith.ataque;
		int vidaSoldado = soldado.vida;
		int ataqueSoldado = soldado.ataque;

		// Aplicamos dificultad dificil
		main.aplicarDificultad(enemigos, 3);

		// Comprobamos el resultado
		assertTrue(sith.vida > vidaSith);
		assertTrue(sith.ataque > ataqueSith);
		assertTrue(soldado.vida > vidaSoldado);
		assertTrue(soldado.ataque > ataqueSoldado);
	}

	@Test
	void ataqueBasicoBajaLaVidaDelEnemigo() {
		// Creamos personajes
		Jedi jedi = new Jedi("Yoda");
		Sith sith = new Sith("Darth Vader");
		int vidaAntes = sith.vida;

		// Atacamos
		jedi.ataqueBasico(sith);

		// Comprobamos el resultado
		assertTrue(sith.vida < vidaAntes);
	}

	@Test
	void recibirDanioDirectoDejaLaVidaACeroSiElDanioEsMayor() {
		// Creamos personaje
		Sith sith = new Sith("Darth Vader");

		// Hacemos mucho danio
		sith.recibirDanioDirecto(999);

		// Comprobamos el resultado
		assertEquals(0, sith.vida);
	}
// comprueba que cuando la vida queda en 0, estaVivo() devuelve false.
	@Test
	void estaVivoDevuelveFalseCuandoElPersonajeMuere() {
		// Creamos personaje
		Sith sith = new Sith("Darth Vader");

		// Matamos personaje
		sith.recibirDanioDirecto(999);

		// Comprobamos el resultado
		assertFalse(sith.estaVivo());
	}

	@Test
	void hacerTurnoAutomaticoNoRompe() {
		// Creamos personajes
		Jedi jedi = new Jedi("Yoda");
		Sith sith = new Sith("Darth Vader");
		persona.Personaje[] enemigos = { sith };
		persona.Personaje[] aliados = { jedi };

		// Ejecutamos turno automatico
		jedi.hacerTurno(enemigos, aliados);

		// Comprobamos el resultado
		assertTrue(jedi.estaVivo());
	}

	@Test
	void curarNoSuperaVidaMaxima() {
		// Creamos personaje
		Jedi jedi = new Jedi("Yoda");

		// Hacemos danio y curamos mucho
		jedi.recibirDanioDirecto(20);
		jedi.curar(999);

		// Comprobamos el resultado
		assertEquals(jedi.vidaMax, jedi.vida);
	}

	@Test
	void regenerarManaNoSuperaManaMaximo() {
		// Creamos personaje
		Jedi jedi = new Jedi("Yoda");

		// Ponemos el mana cerca del maximo
		jedi.mana = jedi.manaMax - 1;
		jedi.regenerarMana();

		// Comprobamos el resultado
		assertEquals(jedi.manaMax, jedi.mana);
	}

	@Test
	void aplicarEstadoAnadeUnEstado() {
		// Creamos personaje
		Jedi jedi = new Jedi("Yoda");

		// Aplicamos un estado
		jedi.aplicarEstado(new Veneno());

		// Comprobamos el resultado
		assertEquals(1, jedi.numEstados);
		assertTrue(jedi.estados[0] != null);
	}

	@Test
	void procesarEstadosReduceTurnosDelEstado() {
		// Creamos personaje
		Jedi jedi = new Jedi("Yoda");

		// Aplicamos un estado
		jedi.aplicarEstado(new Veneno());
		int turnosAntes = jedi.estados[0].getTurnosRestantes();

		// Procesamos estados
		jedi.procesarEstados();

		// Comprobamos el resultado
		assertTrue(jedi.numEstados == 0 || jedi.estados[0].getTurnosRestantes() < turnosAntes);
	}
}
