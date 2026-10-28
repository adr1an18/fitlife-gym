-- =====================================================
-- Base de datos: bdgimnasio (proyecto FitLife Gym)
-- Motor: MySQL 8
-- Una sola sede (Av. Circunvalacion 1757, San Luis), por eso no hay tabla de sedes.
-- Tablas (8): tb_usuario, tb_cliente, tb_entrenador, tb_plan,
--             tb_matricula, tb_rutina, tb_asistencia, tb_clase
-- =====================================================

DROP DATABASE IF EXISTS bdgimnasio;
CREATE DATABASE bdgimnasio;
USE bdgimnasio;

-- ---------------------- TABLAS ----------------------

CREATE TABLE tb_usuario (
    id_usuario      INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario  VARCHAR(40)  NOT NULL UNIQUE,
    contrasena      VARCHAR(255) NOT NULL,              -- guardar cifrada (BCrypt)
    correo          VARCHAR(100) NOT NULL UNIQUE,
    rol             ENUM('ADMIN','CLIENTE','ENTRENADOR') NOT NULL,
    estado          TINYINT(1)   NOT NULL DEFAULT 1,    -- 1 = activo, 0 = inactivo
    fec_registro    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_cliente (
    id_cliente        INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario        INT UNIQUE,                       -- relacion 1:1 con usuario
    nombres           VARCHAR(60) NOT NULL,
    apellidos         VARCHAR(60) NOT NULL,
    dni               CHAR(8)     NOT NULL UNIQUE,
    telefono          VARCHAR(15),
    fecha_nacimiento  DATE,
    estado            TINYINT(1)  NOT NULL DEFAULT 1,
    fec_registro      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id_usuario)
);

CREATE TABLE tb_entrenador (
    id_entrenador  INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario     INT UNIQUE,                          -- relacion 1:1 con usuario
    nombres        VARCHAR(80) NOT NULL,
    especialidad   VARCHAR(80),
    experiencia    INT,                                 -- anos de experiencia
    foto           VARCHAR(100),                        -- archivo en static/img
    estado         TINYINT(1)  NOT NULL DEFAULT 1,
    fec_registro   DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id_usuario)
);

CREATE TABLE tb_plan (
    id_plan         INT AUTO_INCREMENT PRIMARY KEY,
    nombre_plan     VARCHAR(50)  NOT NULL,
    descripcion     VARCHAR(150),
    precio          DECIMAL(8,2) NOT NULL,
    duracion_meses  INT          NOT NULL,
    estado          TINYINT(1)   NOT NULL DEFAULT 1
);

CREATE TABLE tb_matricula (
    id_matricula  INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente    INT  NOT NULL,
    id_plan       INT  NOT NULL,
    fecha_inicio  DATE NOT NULL,
    fecha_fin     DATE NOT NULL,
    estado        TINYINT(1) NOT NULL DEFAULT 1,
    fec_registro  DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_cliente) REFERENCES tb_cliente(id_cliente),
    FOREIGN KEY (id_plan)    REFERENCES tb_plan(id_plan)
);

CREATE TABLE tb_rutina (
    id_rutina         INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente        INT          NOT NULL,
    id_entrenador     INT          NOT NULL,
    nombre_rutina     VARCHAR(80)  NOT NULL,
    descripcion       VARCHAR(200),
    fecha_asignacion  DATE         NOT NULL,
    estado            TINYINT(1)   NOT NULL DEFAULT 1,
    FOREIGN KEY (id_cliente)    REFERENCES tb_cliente(id_cliente),
    FOREIGN KEY (id_entrenador) REFERENCES tb_entrenador(id_entrenador)
);

CREATE TABLE tb_asistencia (
    id_asistencia  INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente     INT      NOT NULL,
    fecha          DATE     NOT NULL,
    hora_ingreso   TIME     NOT NULL,
    hora_salida    TIME     NULL,                       -- vacia mientras el cliente sigue dentro
    fec_registro   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_cliente) REFERENCES tb_cliente(id_cliente)
);

CREATE TABLE tb_clase (
    id_clase       INT AUTO_INCREMENT PRIMARY KEY,
    id_entrenador  INT         NOT NULL,
    nombre_clase   VARCHAR(60) NOT NULL,
    dia_semana     ENUM('Lunes','Martes','Miercoles','Jueves','Viernes','Sabado','Domingo') NOT NULL,
    hora_inicio    TIME        NOT NULL,
    hora_fin       TIME        NOT NULL,
    cupo_maximo    INT         NOT NULL,
    estado         TINYINT(1)  NOT NULL DEFAULT 1,
    FOREIGN KEY (id_entrenador) REFERENCES tb_entrenador(id_entrenador)
);

-- ---------------------- DATOS DE EJEMPLO ----------------------
-- Contrasena de todos los usuarios de ejemplo: 123456 (hash BCrypt)

INSERT INTO tb_usuario (nombre_usuario, contrasena, correo, rol) VALUES
('admin',    '$2a$10$cNnEWBOU/X0ozAz5lhnZ1u8mEKEnn0rrstaqCI9ZUgcDIgx.jEjYG', 'miuragym@hotmail.com',  'ADMIN'),
('luisp',    '$2a$10$cNnEWBOU/X0ozAz5lhnZ1u8mEKEnn0rrstaqCI9ZUgcDIgx.jEjYG', 'luis.perez@correo.com',  'CLIENTE'),
('mariag',   '$2a$10$cNnEWBOU/X0ozAz5lhnZ1u8mEKEnn0rrstaqCI9ZUgcDIgx.jEjYG', 'maria.gomez@correo.com', 'CLIENTE'),
('carlosr',  '$2a$10$cNnEWBOU/X0ozAz5lhnZ1u8mEKEnn0rrstaqCI9ZUgcDIgx.jEjYG', 'carlos.ramos@correo.com','CLIENTE'),
('entrenador1','$2a$10$cNnEWBOU/X0ozAz5lhnZ1u8mEKEnn0rrstaqCI9ZUgcDIgx.jEjYG','entrenador1@fitlife.com','ENTRENADOR'),
('entrenador2','$2a$10$cNnEWBOU/X0ozAz5lhnZ1u8mEKEnn0rrstaqCI9ZUgcDIgx.jEjYG','entrenador2@fitlife.com','ENTRENADOR'),
('entrenador3','$2a$10$cNnEWBOU/X0ozAz5lhnZ1u8mEKEnn0rrstaqCI9ZUgcDIgx.jEjYG','entrenador3@fitlife.com','ENTRENADOR'),
('entrenador4','$2a$10$cNnEWBOU/X0ozAz5lhnZ1u8mEKEnn0rrstaqCI9ZUgcDIgx.jEjYG','entrenador4@fitlife.com','ENTRENADOR');

-- Los mismos clientes del constructor de GimnasioServicio
INSERT INTO tb_cliente (id_usuario, nombres, apellidos, dni, telefono, fecha_nacimiento) VALUES
(2, 'Luis',   'Perez', '70000001', '999111222', '2000-05-12'),
(3, 'Maria',  'Gomez', '70000002', '999333444', '1998-11-03'),
(4, 'Carlos', 'Ramos', '70000003', '999555666', '2001-02-20');

-- Los 4 entrenadores de la pagina "Nosotros"
INSERT INTO tb_entrenador (id_usuario, nombres, especialidad, experiencia, foto) VALUES
(5, 'Adrian', 'Musculacion y levantamiento', 6, 'entrenador1.jpg'),
(6, 'Antoni', 'Baile y zumba',                4, 'entrenador2.jpg'),
(7, 'Jose',   'Boxeo y cardio',               5, 'entrenador3.jpg'),
(8, 'Alexis', 'Entrenamiento funcional',      3, 'entrenador4.jpg');

-- Los mismos 8 planes del constructor de GimnasioServicio
INSERT INTO tb_plan (nombre_plan, descripcion, precio, duracion_meses) VALUES
('Plan Basico',     'Acceso a zona de pesas y cardio',      79.00,  1),
('Plan Estandar',   'Basico + clases grupales',            129.00,  3),
('Plan Premium',    'Estandar + rutina con entrenador',    199.00,  6),
('Plan Anual',      'Acceso total durante todo el ano',    349.00, 12),
('Plan Estudiante', 'Descuento con carne universitario',    59.00,  1),
('Plan Pareja',     'Dos personas, un solo pago',          219.00,  3),
('Plan Semestral',  'Acceso total por seis meses',         289.00,  6),
('Plan Funcional',  'Clases funcionales y de baile',        99.00,  1);

-- Las mismas 4 matriculas del constructor de GimnasioServicio
INSERT INTO tb_matricula (id_cliente, id_plan, fecha_inicio, fecha_fin) VALUES
(1, 1, '2026-08-05', '2026-09-05'),
(2, 2, '2026-09-10', '2026-12-10'),
(3, 2, '2026-09-18', '2026-12-18'),
(1, 3, '2026-10-01', '2027-04-01');

-- Rutinas (el Plan Premium incluye rutina con entrenador)
INSERT INTO tb_rutina (id_cliente, id_entrenador, nombre_rutina, descripcion, fecha_asignacion) VALUES
(1, 1, 'Fuerza tren superior', 'Pecho, espalda y brazos, 3 veces por semana', '2026-10-02'),
(1, 4, 'Cardio y movilidad',   'Circuito funcional de 30 minutos',            '2026-10-09');

-- Clases grupales
INSERT INTO tb_clase (id_entrenador, nombre_clase, dia_semana, hora_inicio, hora_fin, cupo_maximo) VALUES
(2, 'Baile',        'Miercoles', '19:00:00', '20:00:00', 25),
(4, 'Funcional',    'Lunes',     '18:00:00', '19:00:00', 20),
(3, 'Boxeo',        'Viernes',   '19:00:00', '20:00:00', 15),
(1, 'Levantamiento','Sabado',    '10:00:00', '11:00:00', 12);

INSERT INTO tb_asistencia (id_cliente, fecha, hora_ingreso, hora_salida) VALUES
(1, '2026-10-05', '07:10:00', '08:30:00'),
(2, '2026-10-05', '18:05:00', '19:15:00'),
(3, '2026-10-06', '18:20:00', '19:40:00'),
(1, '2026-10-07', '07:00:00', '08:20:00'),
(2, '2026-10-08', '18:00:00', NULL);

-- ---------------------- CONSULTA DE PRUEBA ----------------------
-- SELECT c.nombres, c.apellidos, p.nombre_plan, m.fecha_inicio, m.fecha_fin
-- FROM tb_matricula m
-- JOIN tb_cliente c ON c.id_cliente = m.id_cliente
-- JOIN tb_plan p    ON p.id_plan    = m.id_plan;
