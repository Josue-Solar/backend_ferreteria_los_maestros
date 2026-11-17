-- V2: poblado inicial (catálogos, administrador y 12 productos del frontend).
-- sku, marca, unidad y stock son valores de ejemplo; cámbialos por los reales
-- (con una migración nueva, p. ej. V3, nunca editando esta una vez aplicada).

-- ------------------------------------------------------------
-- 1. Tablas de apoyo (los ids se generan solos)
-- ------------------------------------------------------------
INSERT INTO rol (nombre, descripcion) VALUES
  ('Administrador', 'Gestiona productos y la tienda'),
  ('Cliente',       'Compra productos en la tienda');

INSERT INTO cargo (nombre, descripcion) VALUES
  ('Encargado de inventario', 'Administra el stock y los productos');

INSERT INTO sucursal (nombre, descripcion) VALUES
  ('La Serena', 'Sucursal principal');

INSERT INTO categoria (nombre, descripcion) VALUES
  ('Herramientas eléctricas',    'Sierras, taladros y equipos con motor'),
  ('Herramientas manuales',      'Cinceles, serruchos, destornilladores y medición'),
  ('Gasfitería',                 'Llaves y accesorios para agua potable'),
  ('Electricidad',               'Insumos para instalaciones eléctricas'),
  ('Materiales de construcción', 'Cemento, barnices y obra gruesa');

INSERT INTO tipo_cliente (nombre, descripcion) VALUES
  ('Particular',            'Compra para uso personal o del hogar'),
  ('Maestro o contratista', 'Compra para trabajos a terceros'),
  ('Empresa constructora',  'Compra para obras de una empresa');

-- ------------------------------------------------------------
-- 2. Administrador (usuario + su fila en admin)
--    En la app: usuario "admin", clave "maestros2026" (se guarda como hash BCrypt).
-- ------------------------------------------------------------
INSERT INTO usuario (nombre_usuario, rut, primer_nombre, segundo_nombre, apa_paterno, apa_materno, telefono, correo, contrasenia, f_nacimiento)
VALUES ('admin', NULL, 'Administrador', NULL, 'Los Maestros', NULL, '+56954323442',
        'losmaestrosferreteria@gmail.com', '$2a$10$xYWb8WXI711crb6m0bgJEuvN14aloowvlDac82G9TJ9phPhUnAVk2', NULL);

INSERT INTO admin (ID, id_ROL, id_CARGO, id_SUCURSAL) VALUES (
  (SELECT id FROM usuario  WHERE correo = 'losmaestrosferreteria@gmail.com'),
  (SELECT id FROM rol      WHERE nombre = 'Administrador'),
  (SELECT id FROM cargo    WHERE nombre = 'Encargado de inventario'),
  (SELECT id FROM sucursal WHERE nombre = 'La Serena')
);

-- ------------------------------------------------------------
-- 3. Productos (12 del catálogo)
--    sku, marca, unidad y stock NO existen en productos.js:
--    son valores de ejemplo, cámbialos por los reales.
--    url_imagen guarda solo el nombre del archivo (carpeta assets/img).
-- ------------------------------------------------------------
INSERT INTO producto (url_imagen, nombre, descripcion, detalle, precio, id_CATEGORIA, sku, marca, unidad, stock) VALUES
(
  '402-2.jpg',
  'Sierra Circular Eléctrica 1400W Disco 185mm',
  'Motor potente para cortes rápidos y precisos en madera.',
  'Motor potente de 1400W para cortes rápidos y precisos en madera. Profundidad y ángulo de corte ajustables, disco de 185mm incluido, 4800 rpm sin carga.',
  49990,
  (SELECT id FROM categoria WHERE nombre = 'Herramientas eléctricas'),
  'LM-0001', 'Los Maestros', 'Unidad', 15
),
(
  'total-tools-sierras-circulares.jpg',
  'Sierra Circular Profesional Total Tools',
  'Ideal para trabajos de uso intensivo en obra.',
  'Diseñada para uso intensivo en obra, con carcasa reforzada y buena disipación de calor.',
  54990,
  (SELECT id FROM categoria WHERE nombre = 'Herramientas eléctricas'),
  'LM-0002', 'Total Tools', 'Unidad', 10
),
(
  'img1.jpg',
  'Set de Cinceles para Madera (3 piezas)',
  'Acero templado para tallado y ajuste fino en carpintería.',
  'Acero templado para tallado y ajuste fino en carpintería. Incluye 3 anchos distintos.',
  12990,
  (SELECT id FROM categoria WHERE nombre = 'Herramientas manuales'),
  'LM-0003', 'Los Maestros', 'Pack', 20
),
(
  'img2.jpg',
  'Cincel Biselado para Carpintería',
  'Filo de precisión, mango ergonómico antideslizante.',
  'Filo de precisión con mango ergonómico antideslizante.',
  6990,
  (SELECT id FROM categoria WHERE nombre = 'Herramientas manuales'),
  'LM-0004', 'Los Maestros', 'Unidad', 30
),
(
  'Sierra_carpinteria.jpg',
  'Serrucho de Carpintero Tradicional',
  'Herramienta manual clásica, ideal para cortes rectos.',
  'Herramienta manual clásica, ideal para cortes rectos en madera blanda y dura.',
  9990,
  (SELECT id FROM categoria WHERE nombre = 'Herramientas manuales'),
  'LM-0005', 'Los Maestros', 'Unidad', 25
),
(
  'Taladro_percutor.jpg',
  'Taladro Percutor 750W',
  'Perfora madera, metal y hormigón con velocidad variable.',
  'Perfora madera, metal y hormigón con velocidad variable. Incluye mango auxiliar y tope de profundidad.',
  39990,
  (SELECT id FROM categoria WHERE nombre = 'Herramientas eléctricas'),
  'LM-0006', 'Los Maestros', 'Unidad', 12
),
(
  'llave_paso1,,2.jpg',
  'Llave de Paso PVC 1/2"',
  'Para instalaciones de agua potable, cierre hermético.',
  'Para instalaciones de agua potable, cierre hermético. Resistente a la corrosión.',
  2990,
  (SELECT id FROM categoria WHERE nombre = 'Gasfitería'),
  'LM-0007', 'Los Maestros', 'Unidad', 60
),
(
  'CintaAislante_Electrica5unidades.webp',
  'Cinta Aislante Eléctrica (Pack 5un)',
  'Aislación segura para conexiones e instalaciones eléctricas.',
  'Aislación segura para conexiones e instalaciones eléctricas. Resistente a la humedad.',
  3490,
  (SELECT id FROM categoria WHERE nombre = 'Electricidad'),
  'LM-0008', 'Los Maestros', 'Pack', 40
),
(
  'SacoCemento25kg.webp',
  'Saco de Cemento 25kg',
  'Ideal para obras menores, reparaciones y trabajos de albañilería.',
  'Ideal para obras menores, reparaciones y trabajos de albañilería.',
  5490,
  (SELECT id FROM categoria WHERE nombre = 'Materiales de construcción'),
  'LM-0009', 'Los Maestros', 'Saco', 100
),
(
  'BarnizProtector.webp',
  'Barniz Protector para Madera 1L',
  'Protege y realza el color natural de la madera.',
  'Protege y realza el color natural de la madera. Secado rápido.',
  8990,
  (SELECT id FROM categoria WHERE nombre = 'Materiales de construcción'),
  'LM-0010', 'Los Maestros', 'Litro', 35
),
(
  'Desatornilladoresphillips.webp',
  'Set de Destornilladores Phillips y Plano (6 piezas)',
  'Puntas magnetizadas y mangos ergonómicos para uso general.',
  'Puntas magnetizadas y mangos ergonómicos para uso general.',
  5990,
  (SELECT id FROM categoria WHERE nombre = 'Herramientas manuales'),
  'LM-0011', 'Los Maestros', 'Pack', 45
),
(
  'Huincha_medir5M.webp',
  'Huincha de Medir 5m',
  'Cinta métrica con freno automático y carcasa resistente a caídas.',
  'Cinta métrica de 5 metros con freno automático y carcasa resistente a caídas.',
  3990,
  (SELECT id FROM categoria WHERE nombre = 'Herramientas manuales'),
  'LM-0012', 'Los Maestros', 'Unidad', 50
);
