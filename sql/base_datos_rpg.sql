CREATE DATABASE IF NOT EXISTS starwars_rpg;
USE starwars_rpg;

-- Borramos las tablas si ya existen para poder ejecutar el script varias veces sin errores
DROP TABLE IF EXISTS Partida_Personaje_Estado;
DROP TABLE IF EXISTS Partida_Logro;
DROP TABLE IF EXISTS Partida_Personaje;
DROP TABLE IF EXISTS Partida;
DROP TABLE IF EXISTS Combate;
DROP TABLE IF EXISTS Personaje_Estado;
DROP TABLE IF EXISTS Aplica_Estado;
DROP TABLE IF EXISTS Lanza;
DROP TABLE IF EXISTS Personaje;
DROP TABLE IF EXISTS Dificultad;
DROP TABLE IF EXISTS Estado;
DROP TABLE IF EXISTS Hechizos;
DROP TABLE IF EXISTS Armas;
DROP TABLE IF EXISTS Logro;

-- Tabla de armas
CREATE TABLE Armas (
  id_arma INT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  danioBase INT NOT NULL CHECK (danioBase >= 0),
  tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('SableLuz', 'Blaster'))
);

INSERT INTO Armas VALUES
  (1, 'Sable de Luz', 10, 'SableLuz'),
  (2, 'Blaster', 8, 'Blaster');

-- Tabla de hechizos directos
CREATE TABLE Hechizos (
  id_hechizo INT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  costeMana INT NOT NULL CHECK (costeMana >= 0),
  tipo VARCHAR(30) NOT NULL CHECK (tipo IN ('EmpujonFuerza', 'Telequinesis', 'Estrangulamiento', 'Aplastamiento', 'Curacion')),
  danio INT DEFAULT 0
);

INSERT INTO Hechizos VALUES
  (1, 'Empujon Fuerza', 10, 'EmpujonFuerza', 15),
  (2, 'Telequinesis', 8, 'Telequinesis', 10),
  (3, 'Estrangulamiento', 12, 'Estrangulamiento', 20),
  (4, 'Aplastamiento', 10, 'Aplastamiento', 18),
  (5, 'Curacion', 10, 'Curacion', 0);

-- Tabla de estados por turnos
CREATE TABLE Estado (
  id_estado INT PRIMARY KEY,
  nombre VARCHAR(30) NOT NULL,
  turnosRestantes INT NOT NULL CHECK (turnosRestantes >= 0),
  potenciaPorTurno INT NOT NULL,
  tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('Veneno', 'Quemadura', 'Renovar'))
);

INSERT INTO Estado VALUES
  (1, 'Veneno', 3, 4, 'Veneno'),
  (2, 'Quemadura', 3, 5, 'Quemadura'),
  (3, 'Renovar', 2, 10, 'Renovar');

-- Tabla de personajes
CREATE TABLE Personaje (
  id_personaje INT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  estaVivo BOOLEAN DEFAULT TRUE,
  vida INT NOT NULL CHECK (vida >= 0),
  vidaMax INT NOT NULL CHECK (vidaMax > 0),
  ataque INT NOT NULL,
  defensa INT NOT NULL DEFAULT 0,
  mana INT NOT NULL DEFAULT 0,
  manaMax INT NOT NULL DEFAULT 0,
  nivel INT NOT NULL DEFAULT 1,
  experiencia INT NOT NULL DEFAULT 0,
  victorias INT NOT NULL DEFAULT 0,
  derrotas INT NOT NULL DEFAULT 0,
  tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('Jedi', 'Sith', 'SoldadoImperial', 'SoldadoRebelde', 'Sanador', 'Cazarrecompensas')),
  id_arma INT NOT NULL,
  FOREIGN KEY (id_arma) REFERENCES Armas(id_arma)
);

INSERT INTO Personaje VALUES
  (1, 'Yoda', TRUE, 130, 130, 24, 8, 20, 20, 1, 0, 0, 0, 'Jedi', 1),
  (2, 'Darth Vader', TRUE, 130, 130, 26, 7, 25, 25, 1, 0, 0, 0, 'Sith', 1),
  (3, 'Leia', TRUE, 100, 100, 10, 4, 35, 35, 1, 0, 0, 0, 'Sanador', 2),
  (4, 'Han Solo', TRUE, 110, 110, 22, 4, 20, 20, 1, 0, 0, 0, 'SoldadoRebelde', 2),
  (5, 'Stormtrooper', TRUE, 110, 110, 21, 5, 20, 20, 1, 0, 0, 0, 'SoldadoImperial', 2),
  (6, 'Boba Fett', TRUE, 105, 105, 21, 4, 30, 30, 1, 0, 0, 0, 'Cazarrecompensas', 2);

-- Tablas de relaciones
CREATE TABLE Lanza (
  id_personaje INT NOT NULL,
  id_hechizo INT NOT NULL,
  PRIMARY KEY (id_personaje, id_hechizo),
  FOREIGN KEY (id_personaje) REFERENCES Personaje(id_personaje) ON DELETE CASCADE,
  FOREIGN KEY (id_hechizo) REFERENCES Hechizos(id_hechizo) ON DELETE CASCADE
);

INSERT INTO Lanza VALUES
  (1, 1), (1, 2), (1, 4),
  (2, 1), (2, 3), (2, 4),
  (3, 5);

CREATE TABLE Aplica_Estado (
  id_personaje INT NOT NULL,
  id_estado INT NOT NULL,
  costeMana INT NOT NULL CHECK (costeMana >= 0),
  PRIMARY KEY (id_personaje, id_estado),
  FOREIGN KEY (id_personaje) REFERENCES Personaje(id_personaje) ON DELETE CASCADE,
  FOREIGN KEY (id_estado) REFERENCES Estado(id_estado) ON DELETE CASCADE
);

INSERT INTO Aplica_Estado VALUES
  (3, 3, 12),
  (4, 1, 10),
  (5, 1, 10),
  (6, 2, 12);

CREATE TABLE Personaje_Estado (
  id_personaje INT NOT NULL,
  id_estado INT NOT NULL,
  PRIMARY KEY (id_personaje, id_estado),
  FOREIGN KEY (id_personaje) REFERENCES Personaje(id_personaje) ON DELETE CASCADE,
  FOREIGN KEY (id_estado) REFERENCES Estado(id_estado) ON DELETE CASCADE
);

CREATE TABLE Combate (
  id_combate INT PRIMARY KEY,
  id_personajeA INT NOT NULL,
  id_personajeB INT NOT NULL,
  CONSTRAINT chk_diferentes CHECK (id_personajeA <> id_personajeB),
  FOREIGN KEY (id_personajeA) REFERENCES Personaje(id_personaje),
  FOREIGN KEY (id_personajeB) REFERENCES Personaje(id_personaje)
);

INSERT INTO Combate VALUES
  (1, 1, 2),
  (2, 4, 5);

-- Tabla de dificultades
CREATE TABLE Dificultad (
  id_dificultad INT PRIMARY KEY,
  nombre VARCHAR(20) NOT NULL,
  modificadorVida INT NOT NULL,
  modificadorAtaque INT NOT NULL,
  experienciaVictoria INT NOT NULL
);

INSERT INTO Dificultad VALUES
  (1, 'Facil', -20, -3, 30),
  (2, 'Normal', 0, 0, 50),
  (3, 'Dificil', 30, 5, 80);

-- Tablas de logros
CREATE TABLE Logro (
  id_logro INT PRIMARY KEY,
  nombre VARCHAR(80) NOT NULL,
  descripcion VARCHAR(255) NOT NULL
);

INSERT INTO Logro VALUES
  (1, 'Primera partida', 'Has empezado una partida.'),
  (2, 'Primera victoria', 'Has ganado una partida.'),
  (3, 'Victoria del jugador', 'El equipo del jugador ha ganado.'),
  (4, 'Primer enemigo derrotado', 'Has derrotado al menos un enemigo.'),
  (5, 'Victoria facil', 'Has ganado en dificultad facil.'),
  (6, 'Victoria normal', 'Has ganado en dificultad normal.'),
  (7, 'Victoria dificil', 'Has ganado en dificultad dificil.');

-- Tablas para guardar partidas
CREATE TABLE Partida (
  id_partida INT PRIMARY KEY,
  rondas_guardadas INT DEFAULT 0,
  id_ronda_actual INT DEFAULT 1,
  final_del_turno BOOLEAN DEFAULT FALSE,
  id_dificultad INT,
  FOREIGN KEY (id_dificultad) REFERENCES Dificultad(id_dificultad)
);

CREATE TABLE Partida_Logro (
  id_partida INT NOT NULL,
  id_logro INT NOT NULL,
  fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_partida, id_logro),
  FOREIGN KEY (id_partida) REFERENCES Partida(id_partida) ON DELETE CASCADE,
  FOREIGN KEY (id_logro) REFERENCES Logro(id_logro) ON DELETE CASCADE
);

CREATE TABLE Partida_Personaje (
  id_partida INT NOT NULL,
  id_personaje INT NOT NULL,
  turno INT NOT NULL,
  vida_actual INT NOT NULL CHECK (vida_actual >= 0),
  mana_actual INT NOT NULL CHECK (mana_actual >= 0),
  estaVivo BOOLEAN DEFAULT TRUE,
  equipo VARCHAR(20) NOT NULL CHECK (equipo IN ('Jugador', 'Enemigo')),
  PRIMARY KEY (id_partida, id_personaje, turno),
  FOREIGN KEY (id_partida) REFERENCES Partida(id_partida) ON DELETE CASCADE,
  FOREIGN KEY (id_personaje) REFERENCES Personaje(id_personaje) ON DELETE CASCADE
);

CREATE TABLE Partida_Personaje_Estado (
  id_partida INT NOT NULL,
  id_personaje INT NOT NULL,
  id_estado INT NOT NULL,
  turno INT NOT NULL,
  turnosRestantes INT NOT NULL CHECK (turnosRestantes >= 0),
  PRIMARY KEY (id_partida, id_personaje, id_estado, turno),
  FOREIGN KEY (id_partida) REFERENCES Partida(id_partida) ON DELETE CASCADE,
  FOREIGN KEY (id_personaje) REFERENCES Personaje(id_personaje) ON DELETE CASCADE,
  FOREIGN KEY (id_estado) REFERENCES Estado(id_estado) ON DELETE CASCADE
);

-- CONSULTAS PARA LA DEMOSTRACION

-- Enseñar personajes principales
SELECT * FROM Personaje;

-- Enseñar dificultades
SELECT * FROM Dificultad;

-- Enseñar logros disponibles
SELECT * FROM Logro;

-- Enseñar partidas guardadas
SELECT * FROM Partida;

-- Enseñar todos los personajes guardados de una partida
-- Cambiar el id 44 por el id de la partida que se quiera enseñar
SELECT p.nombre, pp.turno, pp.vida_actual, pp.mana_actual, pp.estaVivo, pp.equipo
FROM Partida_Personaje pp
JOIN Personaje p ON pp.id_personaje = p.id_personaje
WHERE pp.id_partida = 44
ORDER BY pp.turno, pp.equipo, p.nombre;

-- Enseñar solo el ultimo turno guardado de una partida
-- Cambiar el id 44 por el id de la partida que se quiera enseñar
SELECT p.nombre, pp.turno, pp.vida_actual, pp.mana_actual, pp.estaVivo, pp.equipo
FROM Partida_Personaje pp
JOIN Personaje p ON pp.id_personaje = p.id_personaje
WHERE pp.id_partida = 44
AND pp.turno = (
  SELECT MAX(turno)
  FROM Partida_Personaje
  WHERE id_partida = 44
)
ORDER BY pp.equipo, p.nombre;

-- Enseñar logros desbloqueados de una partida
-- Cambiar el id 44 por el id de la partida que se quiera enseñar
SELECT l.nombre, l.descripcion, pl.fecha
FROM Partida_Logro pl
JOIN Logro l ON pl.id_logro = l.id_logro
WHERE pl.id_partida = 44
ORDER BY pl.fecha;

-- Enseñar ranking de personajes
SELECT nombre, tipo, nivel, experiencia, victorias, derrotas
FROM Personaje
ORDER BY victorias DESC, experiencia DESC, nivel DESC;
