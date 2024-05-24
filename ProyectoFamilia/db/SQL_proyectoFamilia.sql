DROP DATABASE ProyectoFamilia; -- se puede borrar la base de datos completa o tabla por tabla como lo comentado en la siguiente linea.
CREATE DATABASE IF NOT EXISTS ProyectoFamilia;
USE ProyectoFamilia;
-- DROP TABLE IF EXISTS ProyectoFamilia.Miembro_agrega_evento;
-- DROP TABLE IF EXISTS ProyectoFamilia.Recompensa;
-- DROP TABLE IF EXISTS ProyectoFamilia.Tarea;
-- DROP TABLE IF EXISTS ProyectoFamilia.Evento;
-- DROP TABLE IF EXISTS ProyectoFamilia.Miembro;
-- DROP TABLE IF EXISTS ProyectoFamilia.Familia;


-- -----------------------------------------------------
-- Table ProyectoFamilia.familia
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS ProyectoFamilia.Familia (
	Id INT NOT NULL AUTO_INCREMENT,
    Codigo INT UNIQUE NOT NULL ,
    Nombre VARCHAR(100) NOT NULL,
    Descripcion VARCHAR(245),
    PRIMARY KEY (Id),
	INDEX idx_codigo (Codigo ASC)
	)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table ProyectoFamilia.miembro
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS ProyectoFamilia.Miembro(
	Id INT NOT NULL AUTO_INCREMENT, 
    Nombre VARCHAR(50) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Contraseña VARCHAR(45) NOT NULL,
    Rol VARCHAR(45) NOT NULL,
    Puntos INT,
    Familia_id INT NOT NULL,
    PRIMARY KEY (Id),
    INDEX idx_email (Email ASC),
    
    CONSTRAINT fk_miembro_familia -- la condicion nos dice que la columna familia_id debe existir en la tabla familia para ser creada esta nueva tabla
    FOREIGN KEY (Familia_id)
    REFERENCES ProyectoFamilia.Familia(Id)
)
ENGINE = INNODB;

-- -----------------------------------------------------
-- Table ProyectoFamilia.evento
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS ProyectoFamilia.Evento (
	Id INT NOT NULL AUTO_INCREMENT, 
    Nombre VARCHAR(100) NOT NULL,
    Fecha DATE NOT NULL,
    Descripcion VARCHAR(245),
    Familia_id INT NOT NULL,
    Miembro_id INT,
    PRIMARY KEY (Id),
    
    CONSTRAINT fk_evento_familia_id -- nombre de la condicion
    FOREIGN KEY(Familia_id) -- clave foranea
    REFERENCES ProyectoFamilia.Familia(Id), -- la referencia, donde encontrar esa clave
    
	CONSTRAINT fk_evento_miembro_id 
    FOREIGN KEY(Miembro_id) 
    REFERENCES ProyectoFamilia.Miembro(Id)
    )
ENGINE = INNODB;

-- -----------------------------------------------------
-- Table ProyectoFamilia.tarea
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS ProyectoFamilia.Tarea (
	Id INT NOT NULL AUTO_INCREMENT,
    Nombre VARCHAR(100) NOT NULL,
    Descripcion VARCHAR(245) NOT NULL,
    Fecha DATE,
    Puntos INT,
    Familia_id INT NOT NULL,
    Miembro_id INT,
    PRIMARY KEY (Id),
    
    CONSTRAINT fk_tarea_familia_id -- nombre de la condicion
    FOREIGN KEY(Familia_id) -- clave foranea
    REFERENCES ProyectoFamilia.Familia(Id), -- la referencia, donde encontrar esa clave
    
	CONSTRAINT fk_tarea_miembro_id 
    FOREIGN KEY(Miembro_id) 
    REFERENCES ProyectoFamilia.Miembro(Id)
    
)
ENGINE = INNODB;

-- -----------------------------------------------------
-- Table ProyectoFamilia.recompensa
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS ProyectoFamilia.Recompensa (
	Id INT NOT NULL AUTO_INCREMENT, 
    Nombre VARCHAR(100) NOT NULL,
    Descripcion VARCHAR(245),
    Puntos INT NOT NULL,
    Miembro_id INT NOT NULL,
    PRIMARY KEY(Id),
    
    CONSTRAINT fk_recompensa_miembro
    FOREIGN KEY (Miembro_id)
    REFERENCES ProyectoFamilia.Miembro(Id)
)
ENGINE = INNODB;


