BEGIN;

-- === ROLES ===
CREATE TABLE IF NOT EXISTS public.roles (
    id_rol SERIAL PRIMARY KEY,
    nombre_rol VARCHAR(20) NOT NULL
);

-- === HOTELES ===
CREATE TABLE IF NOT EXISTS public.hoteles (
    id_hotel SERIAL PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    ciudad VARCHAR(50) NOT NULL,
    direccion VARCHAR(70) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(30) NOT NULL
);

-- === USUARIOS ===
CREATE TABLE IF NOT EXISTS public.usuarios (
    id_usuario SERIAL PRIMARY KEY,
    id_rol INTEGER NOT NULL,
    id_hotel INTEGER,
    nombre VARCHAR(40) NOT NULL,
    apellidos VARCHAR(70) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    direccion VARCHAR,
    telefono VARCHAR(15) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    fecha_alta DATE NOT NULL,
    activo BOOLEAN,
    dni VARCHAR(15),
    FOREIGN KEY (id_rol) REFERENCES public.roles(id_rol),
    FOREIGN KEY (id_hotel) REFERENCES public.hoteles(id_hotel)
);

-- === MÉTODO DE PAGO ===
CREATE TABLE IF NOT EXISTS public.metodo_pago (
    id_metodo_pago SERIAL PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    descripcion VARCHAR
);

-- === FACTURAS ===
CREATE TABLE IF NOT EXISTS public.facturas (
    id_factura SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    id_metodo_pago INTEGER NOT NULL,
    id_detalle INTEGER NOT NULL,
    id_hotel INTEGER NOT NULL,
    fecha_emision DATE NOT NULL,
    estado VARCHAR NOT NULL,
    observaciones VARCHAR(80),
    FOREIGN KEY (id_usuario) REFERENCES public.usuarios(id_usuario),
    FOREIGN KEY (id_metodo_pago) REFERENCES public.metodo_pago(id_metodo_pago),
    FOREIGN KEY (id_hotel) REFERENCES public.hoteles(id_hotel)
);

-- === CATEGORÍA PRODUCTO ===
CREATE TABLE IF NOT EXISTS public.categoria_producto (
    id_categoria SERIAL PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    descripcion VARCHAR(80)
);

-- === PRODUCTOS ===
CREATE TABLE IF NOT EXISTS public.productos (
    id_producto SERIAL PRIMARY KEY,
    id_categoria INTEGER NOT NULL,
    id_hotel INTEGER NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    descripcion VARCHAR(80) NOT NULL,
    precio_base NUMERIC(15, 2) NOT NULL,
    activo BOOLEAN,
    fecha_desde DATE,
    fecha_hasta DATE,
    FOREIGN KEY (id_categoria) REFERENCES public.categoria_producto(id_categoria),
    FOREIGN KEY (id_hotel) REFERENCES public.hoteles(id_hotel)
);

-- === HABITACIONES ===
CREATE TABLE IF NOT EXISTS public.habitaciones (
    id_habitacion SERIAL PRIMARY KEY,
    id_hotel INTEGER NOT NULL,
    id_producto INTEGER NOT NULL,
    numero_habitacion INTEGER NOT NULL,
    piso INTEGER NOT NULL,
    tipo VARCHAR(25) NOT NULL,
    capacidad INTEGER NOT NULL,
    estado_ocupacion VARCHAR(30) NOT NULL,
    FOREIGN KEY (id_hotel) REFERENCES public.hoteles(id_hotel),
    FOREIGN KEY (id_producto) REFERENCES public.productos(id_producto)
);

-- === RESERVAS ===
CREATE TABLE IF NOT EXISTS public.reservas (
    id_reserva SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    id_habitacion INTEGER NOT NULL,
    fecha_entrada DATE NOT NULL,
    fecha_salida DATE NOT NULL,
    estado VARCHAR(30) NOT NULL,
    pax INTEGER NOT NULL,
    fecha_reserva TIME NOT NULL,
    comentarios VARCHAR(80),
    FOREIGN KEY (id_usuario) REFERENCES public.usuarios(id_usuario),
    FOREIGN KEY (id_habitacion) REFERENCES public.habitaciones(id_habitacion)
);

-- === DETALLE RESERVA ===
CREATE TABLE IF NOT EXISTS public.detalle_reserva (
    id SERIAL PRIMARY KEY,
    id_reserva INTEGER,
    id_habitacion INTEGER,
    FOREIGN KEY (id_reserva) REFERENCES public.reservas(id_reserva),
    FOREIGN KEY (id_habitacion) REFERENCES public.habitaciones(id_habitacion)
);

-- === PRODUCTO-USUARIO ===
CREATE TABLE IF NOT EXISTS public.producto_usuario (
    id SERIAL PRIMARY KEY,
    id_producto INTEGER NOT NULL,
    id_usuario INTEGER NOT NULL,
    fecha DATE,
    cantidad INTEGER,
    descuento INTEGER,
    id_reserva INTEGER,
    facturado BOOLEAN,
    FOREIGN KEY (id_producto) REFERENCES public.productos(id_producto),
    FOREIGN KEY (id_usuario) REFERENCES public.usuarios(id_usuario),
    FOREIGN KEY (id_reserva) REFERENCES public.reservas(id_reserva)
);

-- === DETALLE FACTURA ===
CREATE TABLE IF NOT EXISTS public.detalle_factura (
    id SERIAL,
    id_detalle INTEGER NOT NULL,
    id_factura INTEGER NOT NULL,
    id_producto_usuario INTEGER NOT NULL,
    PRIMARY KEY (id_detalle, id),
    FOREIGN KEY (id_factura) REFERENCES public.facturas(id_factura),
    FOREIGN KEY (id_producto_usuario) REFERENCES public.producto_usuario(id)
);

-- === PRODUCTO-HABITACIÓN ===
CREATE TABLE IF NOT EXISTS public.producto_habitacion (
    id_producto INTEGER NOT NULL,
    id_habitacion INTEGER NOT NULL,
    PRIMARY KEY (id_producto, id_habitacion),
    FOREIGN KEY (id_producto) REFERENCES public.productos(id_producto),
    FOREIGN KEY (id_habitacion) REFERENCES public.habitaciones(id_habitacion)
);

-- === CONTACTOS ===
CREATE TABLE IF NOT EXISTS public.contactos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(70) NOT NULL,
    telefono VARCHAR(20),
    departamento VARCHAR(20) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_envio TIMESTAMP,
    id_usuario INTEGER,
    FOREIGN KEY (id_usuario) REFERENCES public.usuarios(id_usuario)
);

-- === LIMPIEZA HABITACIONES ===
CREATE TABLE IF NOT EXISTS public.limpieza_habitaciones (
    id SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    id_habitacion INTEGER NOT NULL,
    fecha_limpieza DATE NOT NULL,
    hora_limpieza TIME NOT NULL,
    foto1 VARCHAR(255),
    foto2 VARCHAR(255),
    FOREIGN KEY (id_usuario) REFERENCES public.usuarios(id_usuario),
    FOREIGN KEY (id_habitacion) REFERENCES public.habitaciones(id_habitacion)
);

END;
