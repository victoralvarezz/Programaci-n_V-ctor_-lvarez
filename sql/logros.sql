USE starwars_rpg;

CREATE TABLE IF NOT EXISTS Logro (
  id_logro INT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  descripcion VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS Partida_Logro (
  id_partida INT NOT NULL,
  id_logro INT NOT NULL,
  fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_partida, id_logro),
  FOREIGN KEY (id_partida) REFERENCES Partida(id_partida) ON DELETE CASCADE,
  FOREIGN KEY (id_logro) REFERENCES Logro(id_logro) ON DELETE CASCADE
);

INSERT INTO Logro (id_logro, nombre, descripcion) VALUES
  (1, 'Partida guardada', 'Guardar una partida por primera vez'),
  (2, 'Primera victoria', 'Ganar un combate'),
  (3, 'Superviviente', 'Ganar con algun personaje vivo'),
  (4, 'Primer enemigo derrotado', 'Derrotar al primer enemigo de la partida'),
  (5, 'Victoria facil', 'Ganar una partida en dificultad facil'),
  (6, 'Victoria normal', 'Ganar una partida en dificultad normal'),
  (7, 'Victoria dificil', 'Ganar una partida en dificultad dificil')
ON DUPLICATE KEY UPDATE
  nombre = VALUES(nombre),
  descripcion = VALUES(descripcion);

SELECT *
FROM Logro;
