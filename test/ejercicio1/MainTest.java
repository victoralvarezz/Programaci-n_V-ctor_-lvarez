package ejercicio1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MainTest {

	@Test
	void hayVivosDevuelveTrueSiHayUnPersonajeVivo() {
		persona.Personaje[] equipo = { new Sith("Darth Vader"), new SoldadoImperial("Stormtrooper") };

		// Dejamos muerto a un personaje y el otro sigue vivo.
		equipo[0].recibirDanioDirecto(999);

		assertTrue(main.hayVivos(equipo));
	}

	@Test
	void hayVivosDevuelveFalseSiTodosEstanMuertos() {
		persona.Personaje[] equipo = { new Sith("Darth Vader"), new SoldadoImperial("Stormtrooper") };

		// Matamos a todos los personajes del equipo.
		equipo[0].recibirDanioDirecto(999);
		equipo[1].recibirDanioDirecto(999);

		assertFalse(main.hayVivos(equipo));
	}

	@Test
	void aplicarDificultadFacilBajaVidaYAtaque() {
		Sith sith = new Sith("Darth Vader");
		SoldadoImperial soldado = new SoldadoImperial("Stormtrooper");
		persona.Personaje[] enemigos = { sith, soldado };

		// Guardamos la vida y el ataque antes de cambiar la dificultad.
		int vidaSith = sith.vida;
		int ataqueSith = sith.ataque;
		int vidaSoldado = soldado.vida;
		int ataqueSoldado = soldado.ataque;

		// Aplicamos la dificultad facil.
		main.aplicarDificultad(enemigos, 1);

		// Comprobamos que la vida y el ataque han bajado.
		assertTrue(sith.vida < vidaSith);
		assertTrue(sith.ataque < ataqueSith);
		assertTrue(soldado.vida < vidaSoldado);
		assertTrue(soldado.ataque < ataqueSoldado);
	}

	@Test
	void aplicarDificultadDificilSubeVidaYAtaque() {
		Sith sith = new Sith("Darth Vader");
		SoldadoImperial soldado = new SoldadoImperial("Stormtrooper");
		persona.Personaje[] enemigos = { sith, soldado };

		// Guardamos la vida y el ataque antes de cambiar la dificultad.
		int vidaSith = sith.vida;
		int ataqueSith = sith.ataque;
		int vidaSoldado = soldado.vida;
		int ataqueSoldado = soldado.ataque;

		// Aplicamos la dificultad dificil.
		main.aplicarDificultad(enemigos, 3);

		// Comprobamos que la vida y el ataque han subido.
		assertTrue(sith.vida > vidaSith);
		assertTrue(sith.ataque > ataqueSith);
		assertTrue(soldado.vida > vidaSoldado);
		assertTrue(soldado.ataque > ataqueSoldado);
	}

	@Test
	void ataqueBasicoBajaLaVidaDelEnemigo() {
		Jedi jedi = new Jedi("Yoda");
		Sith sith = new Sith("Darth Vader");

		// Guardamos la vida antes del ataque.
		int vidaAntes = sith.vida;

		// Yoda ataca a Darth Vader.
		jedi.ataqueBasico(sith);

		assertTrue(sith.vida < vidaAntes);
	}

	@Test
	void personajeConVidaCeroEstaMuerto() {
		Sith sith = new Sith("Darth Vader");

		// Matamos al personaje.
		sith.recibirDanioDirecto(999);

		// Comprobamos que la vida queda a 0 y ya no está vivo.
		assertEquals(0, sith.vida);
		assertFalse(sith.estaVivo());
	}

	@Test
	void sanadorCuraAliadoSiTieneManaSuficiente() {
		Sanador sanador = new Sanador("Leia");
		Jedi jedi = new Jedi("Yoda");

		// Primero hacemos daño al aliado.
		jedi.recibirDanioDirecto(40);
		int vidaAntes = jedi.vida;
		int manaAntes = sanador.mana;

		// Leia cura a Yoda.
		sanador.usarCuracion(jedi);

		// Comprobamos que Yoda se cura y Leia gasta mana.
		assertTrue(jedi.vida > vidaAntes);
		assertTrue(sanador.mana < manaAntes);
	}

	@Test
	void procesarEquipoRegeneraManaAPersonajesVivos() {
		Jedi jedi = new Jedi("Yoda");
		persona.Personaje[] equipo = { jedi };
		jedi.mana = 0;

		main.procesarEquipo(equipo);

		assertEquals(3, jedi.mana);
	}

	@Test
	void procesarEquipoNoProcesaPersonajesMuertos() {
		Sith sith = new Sith("Darth Vader");
		persona.Personaje[] equipo = { sith };
		sith.mana = 0;
		sith.recibirDanioDirecto(999);

		main.procesarEquipo(equipo);

		assertEquals(0, sith.mana);
	}

	@Test
	void jediConEmpujonBajaVidaALosEnemigos() {
		Jedi jedi = new Jedi("Yoda");
		Sith sith = new Sith("Darth Vader");
		SoldadoImperial soldado = new SoldadoImperial("Stormtrooper");
		persona.Personaje[] enemigos = { sith, soldado };

		// Guardamos la vida antes del ataque.
		int vidaSith = sith.vida;
		int vidaSoldado = soldado.vida;

		// Usamos la habilidad.
		jedi.usarEmpujon(enemigos);

		// Comprobamos que ha bajado la vida.
		assertTrue(sith.vida < vidaSith);
		assertTrue(soldado.vida < vidaSoldado);
	}

	@Test
	void sithConAplastamientoBajaVidaYGastaMana() {
		Sith sith = new Sith("Darth Vader");
		SoldadoImperial soldado = new SoldadoImperial("Stormtrooper");

		// Guardamos la vida antes del ataque.
		int vidaAntes = soldado.vida;
		int manaAntes = sith.mana;

		// Usamos la habilidad.
		sith.usarAplastamiento(soldado);

		// Comprobamos que ha bajado la vida.
		assertTrue(soldado.vida < vidaAntes);
		assertTrue(sith.mana < manaAntes);
	}

	@Test
	void soldadoRebeldeAplicaVenenoAUnEnemigo() {
		SoldadoRebelde soldado = new SoldadoRebelde("Han Solo");
		Sith sith = new Sith("Darth Vader");

		// Usamos la habilidad.
		soldado.usarVeneno(sith);

		// Comprobamos que se ha aplicado el estado.
		assertEquals(1, sith.numEstados);
		assertEquals("Veneno", sith.estados[0].getNombre());
	}

	@Test
	void cazarrecompensasAplicaQuemaduraAUnEnemigo() {
		Cazarrecompensas cazarrecompensas = new Cazarrecompensas("Boba Fett");
		SoldadoImperial soldado = new SoldadoImperial("Stormtrooper");

		// Usamos la habilidad.
		cazarrecompensas.usarQuemadura(soldado);

		// Comprobamos que se ha aplicado el estado.
		assertEquals(1, soldado.numEstados);
		assertEquals("Quemadura", soldado.estados[0].getNombre());
	}

	@Test
	void sanadorConRenovarAnadeEstadoAUnAliado() {
		Sanador sanador = new Sanador("Leia");
		Jedi jedi = new Jedi("Yoda");

		// Usamos la habilidad.
		sanador.usarRenovar(jedi);

		// Comprobamos que se ha aplicado el estado.
		assertEquals(1, jedi.numEstados);
		assertEquals("Renovar", jedi.estados[0].getNombre());
	}

	@Test
	void curacionNoPasaDeLaVidaMaximaDelAliado() {
		Sanador sanador = new Sanador("Leia");
		Jedi jedi = new Jedi("Yoda");
		jedi.recibirDanioDirecto(5);

		// Usamos la habilidad.
		sanador.usarCuracion(jedi);

		assertEquals(jedi.vidaMax, jedi.vida);
	}
}
