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
}
