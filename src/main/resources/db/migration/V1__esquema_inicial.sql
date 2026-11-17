-- V1: esquema inicial de Ferretería Los Maestros.
-- Nombres de tabla en minúsculas (Hibernate/Spring Boot los consulta en
-- minúsculas; así funciona igual en Linux, Windows y Docker).
-- Flyway se conecta a la BD `maestros` de la URL: aquí no se crea ni se borra nada.

-- ------------------------------------------------------------
-- Tablas de apoyo
-- ------------------------------------------------------------
CREATE TABLE rol (
  id          INT NOT NULL AUTO_INCREMENT,
  nombre      VARCHAR(30) DEFAULT NULL,
  descripcion VARCHAR(60) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE cargo (
  id          INT NOT NULL AUTO_INCREMENT,
  nombre      VARCHAR(30) DEFAULT NULL,
  descripcion VARCHAR(60) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE sucursal (
  id          INT NOT NULL AUTO_INCREMENT,
  nombre      VARCHAR(30) DEFAULT NULL,
  descripcion VARCHAR(60) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE categoria (
  id          INT NOT NULL AUTO_INCREMENT,
  nombre      VARCHAR(30) DEFAULT NULL,
  descripcion VARCHAR(60) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE tipo_cliente (
  id          INT NOT NULL AUTO_INCREMENT,
  nombre      VARCHAR(30) DEFAULT NULL,
  descripcion VARCHAR(60) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ------------------------------------------------------------
-- Usuarios (admin y cliente comparten el id de USUARIO)
-- ------------------------------------------------------------
CREATE TABLE usuario (
  id             INT NOT NULL AUTO_INCREMENT,
  nombre_usuario VARCHAR(30)  DEFAULT NULL,
  rut            VARCHAR(11)  DEFAULT NULL,
  primer_nombre  VARCHAR(50)  NOT NULL,
  segundo_nombre VARCHAR(50)  DEFAULT NULL,
  apa_paterno    VARCHAR(50)  NOT NULL,
  apa_materno    VARCHAR(50)  DEFAULT NULL,
  telefono       VARCHAR(12)  DEFAULT NULL,
  correo         VARCHAR(50)  DEFAULT NULL,
  contrasenia    VARCHAR(255) NOT NULL,
  f_nacimiento   DATE         DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UQ_USUARIO_NOMBRE (nombre_usuario),
  UNIQUE KEY UQ_USUARIO_CORREO (correo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE admin (
  ID          INT NOT NULL,
  id_ROL      INT NOT NULL,
  id_CARGO    INT NOT NULL,
  id_SUCURSAL INT NOT NULL,
  PRIMARY KEY (ID),
  KEY IDX_ADMIN_ROL (id_ROL),
  KEY IDX_ADMIN_CARGO (id_CARGO),
  KEY IDX_ADMIN_SUCURSAL (id_SUCURSAL),
  CONSTRAINT FK_ADMIN_USUARIO  FOREIGN KEY (ID)          REFERENCES usuario (id) ON DELETE CASCADE,
  CONSTRAINT FK_ADMIN_ROL      FOREIGN KEY (id_ROL)      REFERENCES rol (id),
  CONSTRAINT FK_ADMIN_CARGO    FOREIGN KEY (id_CARGO)    REFERENCES cargo (id),
  CONSTRAINT FK_ADMIN_SUCURSAL FOREIGN KEY (id_SUCURSAL) REFERENCES sucursal (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE cliente (
  id              INT NOT NULL,
  id_tipo_cliente INT NOT NULL,
  region          VARCHAR(50) NOT NULL,
  comuna          VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  KEY IDX_CLIENTE_TIPO (id_tipo_cliente),
  CONSTRAINT FK_CLIENTE_USUARIO FOREIGN KEY (id)              REFERENCES usuario (id) ON DELETE CASCADE,
  CONSTRAINT FK_CLIENTE_TIPO    FOREIGN KEY (id_tipo_cliente) REFERENCES tipo_cliente (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ------------------------------------------------------------
-- Productos
-- ------------------------------------------------------------
CREATE TABLE producto (
  id           INT NOT NULL AUTO_INCREMENT,
  url_imagen   VARCHAR(100) NOT NULL,
  nombre       VARCHAR(80)  NOT NULL,
  descripcion  VARCHAR(200) NOT NULL,
  detalle      VARCHAR(300) NOT NULL,
  precio       INT NOT NULL,
  id_CATEGORIA INT NOT NULL,
  sku          VARCHAR(7)   NOT NULL,
  marca        VARCHAR(40)  NOT NULL,
  unidad       VARCHAR(10)  NOT NULL,
  stock        INT UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  UNIQUE KEY UQ_PRODUCTO_SKU (sku),
  KEY IDX_PRODUCTO_CATEGORIA (id_CATEGORIA),
  CONSTRAINT FK_PRODUCTO_CATEGORIA FOREIGN KEY (id_CATEGORIA) REFERENCES categoria (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ------------------------------------------------------------
-- Pedidos
-- ------------------------------------------------------------
CREATE TABLE pedido (
  ID         INT NOT NULL AUTO_INCREMENT,
  id_CLIENTE INT NOT NULL,
  TOTAL      INT DEFAULT 0,
  FECHA      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ID),
  KEY IDX_PEDIDO_CLIENTE (id_CLIENTE),
  CONSTRAINT FK_PEDIDO_CLIENTE FOREIGN KEY (id_CLIENTE) REFERENCES cliente (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE detalle_pedido (
  ID_PEDIDO       INT NOT NULL,
  ID_PRODUCTO     INT NOT NULL,
  CANTIDAD        INT UNSIGNED DEFAULT 0,
  PRECIO_UNITARIO INT UNSIGNED DEFAULT 0,
  PRIMARY KEY (ID_PEDIDO, ID_PRODUCTO),
  KEY IDX_DETALLE_PRODUCTO (ID_PRODUCTO),
  CONSTRAINT FK_DETALLE_PEDIDO   FOREIGN KEY (ID_PEDIDO)   REFERENCES pedido (ID),
  CONSTRAINT FK_DETALLE_PRODUCTO FOREIGN KEY (ID_PRODUCTO) REFERENCES producto (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
