-- FITLIFE GYM - PASO 1: BASE DE DATOS
-- MySQL 8.0.16 o posterior.
-- Ejecutar una sola vez sobre una base nueva o vacia.
-- Este archivo no elimina bases ni tablas existentes.
-- Los datos de ejemplo corresponden a los del proyecto Java original.

-- 1. Crear y seleccionar la base.
CREATE DATABASE IF NOT EXISTS fitlife_gym;

USE fitlife_gym;

-- 2. Planes disponibles en el gimnasio.
-- AUTO_INCREMENT genera el ID de cada nuevo registro.
CREATE TABLE planes (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    duracion_meses INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT ck_plan_precio CHECK (precio >= 0),
    CONSTRAINT ck_plan_duracion CHECK (duracion_meses > 0)
) ENGINE=InnoDB;

-- 3. Clientes. Un mismo DNI no puede registrarse dos veces.
CREATE TABLE clientes (
    id INT NOT NULL AUTO_INCREMENT,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni VARCHAR(8) NOT NULL,
    telefono VARCHAR(30) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_cliente_dni UNIQUE (dni)
) ENGINE=InnoDB;

-- 4. Cada matricula pertenece a un cliente y a un plan.
-- RESTRICT impide borrar un cliente o plan que tenga matriculas.
CREATE TABLE matriculas (
    id INT NOT NULL AUTO_INCREMENT,
    id_cliente INT NOT NULL,
    id_plan INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_matricula_cliente
        FOREIGN KEY (id_cliente) REFERENCES clientes(id)
        ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_matricula_plan
        FOREIGN KEY (id_plan) REFERENCES planes(id)
        ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT ck_matricula_fechas CHECK (fecha_fin >= fecha_inicio)
) ENGINE=InnoDB;

-- 5. Cargar los datos de ejemplo.
START TRANSACTION;

INSERT INTO planes (id, nombre, descripcion, precio, duracion_meses) VALUES
(1, 'Plan Basico', 'Acceso a zona de pesas y cardio', 79.00, 1),
(2, 'Plan Estandar', 'Basico + clases grupales', 129.00, 3),
(3, 'Plan Premium', 'Estandar + rutina con entrenador', 199.00, 6),
(4, 'Plan Anual', 'Acceso total durante todo el ano', 349.00, 12),
(5, 'Plan Estudiante', 'Descuento con carne universitario', 59.00, 1),
(6, 'Plan Pareja', 'Dos personas, un solo pago', 219.00, 3),
(7, 'Plan Semestral', 'Acceso total por seis meses', 289.00, 6),
(8, 'Plan Funcional', 'Clases funcionales y de baile', 99.00, 1);

INSERT INTO clientes (id, nombres, apellidos, dni, telefono, fecha_nacimiento) VALUES
(1, 'Luis', 'Perez', '70000001', '999111222', '2000-05-12'),
(2, 'Maria', 'Gomez', '70000002', '999333444', '1998-11-03'),
(3, 'Carlos', 'Ramos', '70000003', '999555666', '2001-02-20');

INSERT INTO matriculas (id, id_cliente, id_plan, fecha_inicio, fecha_fin) VALUES
(1, 1, 1, '2026-08-05', '2026-09-05'),
(2, 2, 2, '2026-09-10', '2026-12-10'),
(3, 3, 2, '2026-09-18', '2026-12-18'),
(4, 1, 3, '2026-10-01', '2027-04-01');

COMMIT;

-- 6. Verificar el resultado: 8 planes, 3 clientes y 4 matriculas.
SHOW TABLES;

SELECT 'planes' AS tabla, COUNT(*) AS registros FROM planes
UNION ALL
SELECT 'clientes', COUNT(*) FROM clientes
UNION ALL
SELECT 'matriculas', COUNT(*) FROM matriculas;

-- Ver las matriculas con los nombres del cliente y del plan.
SELECT m.id,
       CONCAT(c.nombres, ' ', c.apellidos) AS cliente,
       p.nombre AS plan,
       m.fecha_inicio,
       m.fecha_fin
FROM matriculas AS m
INNER JOIN clientes AS c ON c.id = m.id_cliente
INNER JOIN planes AS p ON p.id = m.id_plan
ORDER BY m.id;



USE bdgimnasio;
SELECT p.id_plan, p.nombre_plan, COUNT(m.id_matricula) AS matriculas
FROM tb_plan p
LEFT JOIN tb_matricula m ON m.id_plan = p.id_plan
GROUP BY p.id_plan, p.nombre_plan
ORDER BY p.id_plan;

USE bdgimnasio;

SHOW TABLES;

SELECT
    e.id_entrenador,
    e.nombres AS entrenador,
    c.id_clase,
    c.nombre_clase,
    c.dia_semana,
    c.hora_inicio,
    c.hora_fin,
    c.cupo_maximo
FROM tb_entrenador e
LEFT JOIN tb_clase c
    ON c.id_entrenador = e.id_entrenador
ORDER BY e.id_entrenador, c.id_clase;
