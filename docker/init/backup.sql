--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

-- Started on 2025-06-16 16:10:56

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- DROP DATABASE IF EXISTS "TresoraBBDD";
--
-- TOC entry 5057 (class 1262 OID 32880)
-- Name: TresoraBBDD; Type: DATABASE; Schema: -; Owner: postgres
--

-- CREATE DATABASE "TresoraBBDD" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'es_ES.UTF-8';


-- ALTER DATABASE "TresoraBBDD" OWNER TO postgres;

\connect "TresoraBBDD"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 228 (class 1259 OID 41429)
-- Name: categoria_producto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria_producto (
    id_categoria integer NOT NULL,
    nombre character varying(30) NOT NULL,
    descripcion character varying(80)
);


ALTER TABLE public.categoria_producto OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 41428)
-- Name: categoria_producto_id_categoria_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categoria_producto_id_categoria_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categoria_producto_id_categoria_seq OWNER TO postgres;

--
-- TOC entry 5058 (class 0 OID 0)
-- Dependencies: 227
-- Name: categoria_producto_id_categoria_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoria_producto_id_categoria_seq OWNED BY public.categoria_producto.id_categoria;


--
-- TOC entry 243 (class 1259 OID 41558)
-- Name: contactos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contactos (
    id integer NOT NULL,
    nombre character varying(100) NOT NULL,
    correo character varying(70) NOT NULL,
    telefono character varying(20),
    departamento character varying(20) NOT NULL,
    mensaje text NOT NULL,
    fecha_envio timestamp without time zone,
    id_usuario integer
);


ALTER TABLE public.contactos OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 41557)
-- Name: contactos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.contactos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.contactos_id_seq OWNER TO postgres;

--
-- TOC entry 5059 (class 0 OID 0)
-- Dependencies: 242
-- Name: contactos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.contactos_id_seq OWNED BY public.contactos.id;


--
-- TOC entry 240 (class 1259 OID 41526)
-- Name: detalle_factura; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.detalle_factura (
    id integer NOT NULL,
    id_detalle integer NOT NULL,
    id_factura integer NOT NULL,
    id_producto_usuario integer NOT NULL
);


ALTER TABLE public.detalle_factura OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 41525)
-- Name: detalle_factura_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.detalle_factura_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.detalle_factura_id_seq OWNER TO postgres;

--
-- TOC entry 5060 (class 0 OID 0)
-- Dependencies: 239
-- Name: detalle_factura_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_factura_id_seq OWNED BY public.detalle_factura.id;


--
-- TOC entry 236 (class 1259 OID 41487)
-- Name: detalle_reserva; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.detalle_reserva (
    id integer NOT NULL,
    id_reserva integer,
    id_habitacion integer
);


ALTER TABLE public.detalle_reserva OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 41486)
-- Name: detalle_reserva_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.detalle_reserva_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.detalle_reserva_id_seq OWNER TO postgres;

--
-- TOC entry 5061 (class 0 OID 0)
-- Dependencies: 235
-- Name: detalle_reserva_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_reserva_id_seq OWNED BY public.detalle_reserva.id;


--
-- TOC entry 226 (class 1259 OID 41405)
-- Name: facturas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.facturas (
    id_factura integer NOT NULL,
    id_usuario integer NOT NULL,
    id_metodo_pago integer NOT NULL,
    id_detalle integer NOT NULL,
    id_hotel integer NOT NULL,
    fecha_emision date NOT NULL,
    estado character varying NOT NULL,
    observaciones character varying(80)
);


ALTER TABLE public.facturas OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 41404)
-- Name: facturas_id_factura_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.facturas_id_factura_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.facturas_id_factura_seq OWNER TO postgres;

--
-- TOC entry 5062 (class 0 OID 0)
-- Dependencies: 225
-- Name: facturas_id_factura_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.facturas_id_factura_seq OWNED BY public.facturas.id_factura;


--
-- TOC entry 232 (class 1259 OID 41453)
-- Name: habitaciones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.habitaciones (
    id_habitacion integer NOT NULL,
    id_hotel integer NOT NULL,
    id_producto integer NOT NULL,
    numero_habitacion integer NOT NULL,
    piso integer NOT NULL,
    tipo character varying(25) NOT NULL,
    capacidad integer NOT NULL,
    estado_ocupacion character varying(30) NOT NULL
);


ALTER TABLE public.habitaciones OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 41452)
-- Name: habitaciones_id_habitacion_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.habitaciones_id_habitacion_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.habitaciones_id_habitacion_seq OWNER TO postgres;

--
-- TOC entry 5063 (class 0 OID 0)
-- Dependencies: 231
-- Name: habitaciones_id_habitacion_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.habitaciones_id_habitacion_seq OWNED BY public.habitaciones.id_habitacion;


--
-- TOC entry 220 (class 1259 OID 41370)
-- Name: hoteles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.hoteles (
    id_hotel integer NOT NULL,
    nombre character varying(30) NOT NULL,
    ciudad character varying(50) NOT NULL,
    direccion character varying(70) NOT NULL,
    telefono character varying(20) NOT NULL,
    email character varying(30) NOT NULL
);


ALTER TABLE public.hoteles OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 41369)
-- Name: hoteles_id_hotel_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hoteles_id_hotel_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.hoteles_id_hotel_seq OWNER TO postgres;

--
-- TOC entry 5064 (class 0 OID 0)
-- Dependencies: 219
-- Name: hoteles_id_hotel_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.hoteles_id_hotel_seq OWNED BY public.hoteles.id_hotel;


--
-- TOC entry 245 (class 1259 OID 41572)
-- Name: limpieza_habitaciones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.limpieza_habitaciones (
    id integer NOT NULL,
    id_usuario integer NOT NULL,
    id_habitacion integer NOT NULL,
    fecha_limpieza date NOT NULL,
    hora_limpieza time without time zone NOT NULL,
    foto1 character varying(255),
    foto2 character varying(255)
);


ALTER TABLE public.limpieza_habitaciones OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 41571)
-- Name: limpieza_habitaciones_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.limpieza_habitaciones_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.limpieza_habitaciones_id_seq OWNER TO postgres;

--
-- TOC entry 5065 (class 0 OID 0)
-- Dependencies: 244
-- Name: limpieza_habitaciones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.limpieza_habitaciones_id_seq OWNED BY public.limpieza_habitaciones.id;


--
-- TOC entry 224 (class 1259 OID 41396)
-- Name: metodo_pago; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.metodo_pago (
    id_metodo_pago integer NOT NULL,
    nombre character varying(30) NOT NULL,
    descripcion character varying
);


ALTER TABLE public.metodo_pago OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 41395)
-- Name: metodo_pago_id_metodo_pago_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.metodo_pago_id_metodo_pago_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.metodo_pago_id_metodo_pago_seq OWNER TO postgres;

--
-- TOC entry 5066 (class 0 OID 0)
-- Dependencies: 223
-- Name: metodo_pago_id_metodo_pago_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.metodo_pago_id_metodo_pago_seq OWNED BY public.metodo_pago.id_metodo_pago;


--
-- TOC entry 241 (class 1259 OID 41542)
-- Name: producto_habitacion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.producto_habitacion (
    id_producto integer NOT NULL,
    id_habitacion integer NOT NULL
);


ALTER TABLE public.producto_habitacion OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 41504)
-- Name: producto_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.producto_usuario (
    id integer NOT NULL,
    id_producto integer NOT NULL,
    id_usuario integer NOT NULL,
    fecha date,
    cantidad integer,
    descuento integer,
    id_reserva integer,
    facturado boolean
);


ALTER TABLE public.producto_usuario OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 41503)
-- Name: producto_usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.producto_usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.producto_usuario_id_seq OWNER TO postgres;

--
-- TOC entry 5067 (class 0 OID 0)
-- Dependencies: 237
-- Name: producto_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.producto_usuario_id_seq OWNED BY public.producto_usuario.id;


--
-- TOC entry 230 (class 1259 OID 41436)
-- Name: productos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.productos (
    id_producto integer NOT NULL,
    id_categoria integer NOT NULL,
    id_hotel integer NOT NULL,
    nombre character varying(30) NOT NULL,
    descripcion character varying(80) NOT NULL,
    precio_base numeric(15,2) NOT NULL,
    activo boolean,
    fecha_desde date,
    fecha_hasta date
);


ALTER TABLE public.productos OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 41435)
-- Name: productos_id_producto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.productos_id_producto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.productos_id_producto_seq OWNER TO postgres;

--
-- TOC entry 5068 (class 0 OID 0)
-- Dependencies: 229
-- Name: productos_id_producto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.productos_id_producto_seq OWNED BY public.productos.id_producto;


--
-- TOC entry 234 (class 1259 OID 41470)
-- Name: reservas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reservas (
    id_reserva integer NOT NULL,
    id_usuario integer NOT NULL,
    id_habitacion integer NOT NULL,
    fecha_entrada date NOT NULL,
    fecha_salida date NOT NULL,
    estado character varying(30) NOT NULL,
    pax integer NOT NULL,
    fecha_reserva time without time zone NOT NULL,
    comentarios character varying(80)
);


ALTER TABLE public.reservas OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 41469)
-- Name: reservas_id_reserva_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reservas_id_reserva_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reservas_id_reserva_seq OWNER TO postgres;

--
-- TOC entry 5069 (class 0 OID 0)
-- Dependencies: 233
-- Name: reservas_id_reserva_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reservas_id_reserva_seq OWNED BY public.reservas.id_reserva;


--
-- TOC entry 218 (class 1259 OID 41363)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id_rol integer NOT NULL,
    nombre_rol character varying(20) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 41362)
-- Name: roles_id_rol_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_id_rol_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.roles_id_rol_seq OWNER TO postgres;

--
-- TOC entry 5070 (class 0 OID 0)
-- Dependencies: 217
-- Name: roles_id_rol_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_id_rol_seq OWNED BY public.roles.id_rol;


--
-- TOC entry 222 (class 1259 OID 41377)
-- Name: usuarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuarios (
    id_usuario integer NOT NULL,
    id_rol integer NOT NULL,
    id_hotel integer,
    nombre character varying(40) NOT NULL,
    apellidos character varying(70) NOT NULL,
    email character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    direccion character varying,
    telefono character varying(15) NOT NULL,
    fecha_nacimiento date NOT NULL,
    fecha_alta date NOT NULL,
    activo boolean,
    dni character varying(15)
);


ALTER TABLE public.usuarios OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 41376)
-- Name: usuarios_id_usuario_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuarios_id_usuario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.usuarios_id_usuario_seq OWNER TO postgres;

--
-- TOC entry 5071 (class 0 OID 0)
-- Dependencies: 221
-- Name: usuarios_id_usuario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuarios_id_usuario_seq OWNED BY public.usuarios.id_usuario;


--
-- TOC entry 4816 (class 2604 OID 41432)
-- Name: categoria_producto id_categoria; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria_producto ALTER COLUMN id_categoria SET DEFAULT nextval('public.categoria_producto_id_categoria_seq'::regclass);


--
-- TOC entry 4823 (class 2604 OID 41561)
-- Name: contactos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contactos ALTER COLUMN id SET DEFAULT nextval('public.contactos_id_seq'::regclass);


--
-- TOC entry 4822 (class 2604 OID 41529)
-- Name: detalle_factura id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_factura ALTER COLUMN id SET DEFAULT nextval('public.detalle_factura_id_seq'::regclass);


--
-- TOC entry 4820 (class 2604 OID 41490)
-- Name: detalle_reserva id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_reserva ALTER COLUMN id SET DEFAULT nextval('public.detalle_reserva_id_seq'::regclass);


--
-- TOC entry 4815 (class 2604 OID 41408)
-- Name: facturas id_factura; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.facturas ALTER COLUMN id_factura SET DEFAULT nextval('public.facturas_id_factura_seq'::regclass);


--
-- TOC entry 4818 (class 2604 OID 41456)
-- Name: habitaciones id_habitacion; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.habitaciones ALTER COLUMN id_habitacion SET DEFAULT nextval('public.habitaciones_id_habitacion_seq'::regclass);


--
-- TOC entry 4812 (class 2604 OID 41373)
-- Name: hoteles id_hotel; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hoteles ALTER COLUMN id_hotel SET DEFAULT nextval('public.hoteles_id_hotel_seq'::regclass);


--
-- TOC entry 4824 (class 2604 OID 41575)
-- Name: limpieza_habitaciones id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.limpieza_habitaciones ALTER COLUMN id SET DEFAULT nextval('public.limpieza_habitaciones_id_seq'::regclass);


--
-- TOC entry 4814 (class 2604 OID 41399)
-- Name: metodo_pago id_metodo_pago; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.metodo_pago ALTER COLUMN id_metodo_pago SET DEFAULT nextval('public.metodo_pago_id_metodo_pago_seq'::regclass);


--
-- TOC entry 4821 (class 2604 OID 41507)
-- Name: producto_usuario id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto_usuario ALTER COLUMN id SET DEFAULT nextval('public.producto_usuario_id_seq'::regclass);


--
-- TOC entry 4817 (class 2604 OID 41439)
-- Name: productos id_producto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.productos ALTER COLUMN id_producto SET DEFAULT nextval('public.productos_id_producto_seq'::regclass);


--
-- TOC entry 4819 (class 2604 OID 41473)
-- Name: reservas id_reserva; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservas ALTER COLUMN id_reserva SET DEFAULT nextval('public.reservas_id_reserva_seq'::regclass);


--
-- TOC entry 4811 (class 2604 OID 41366)
-- Name: roles id_rol; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN id_rol SET DEFAULT nextval('public.roles_id_rol_seq'::regclass);


--
-- TOC entry 4813 (class 2604 OID 41380)
-- Name: usuarios id_usuario; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios ALTER COLUMN id_usuario SET DEFAULT nextval('public.usuarios_id_usuario_seq'::regclass);


--
-- TOC entry 5034 (class 0 OID 41429)
-- Dependencies: 228
-- Data for Name: categoria_producto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5049 (class 0 OID 41558)
-- Dependencies: 243
-- Data for Name: contactos; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5046 (class 0 OID 41526)
-- Dependencies: 240
-- Data for Name: detalle_factura; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5042 (class 0 OID 41487)
-- Dependencies: 236
-- Data for Name: detalle_reserva; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5032 (class 0 OID 41405)
-- Dependencies: 226
-- Data for Name: facturas; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5038 (class 0 OID 41453)
-- Dependencies: 232
-- Data for Name: habitaciones; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5026 (class 0 OID 41370)
-- Dependencies: 220
-- Data for Name: hoteles; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5051 (class 0 OID 41572)
-- Dependencies: 245
-- Data for Name: limpieza_habitaciones; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5030 (class 0 OID 41396)
-- Dependencies: 224
-- Data for Name: metodo_pago; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5047 (class 0 OID 41542)
-- Dependencies: 241
-- Data for Name: producto_habitacion; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5044 (class 0 OID 41504)
-- Dependencies: 238
-- Data for Name: producto_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5036 (class 0 OID 41436)
-- Dependencies: 230
-- Data for Name: productos; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5040 (class 0 OID 41470)
-- Dependencies: 234
-- Data for Name: reservas; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5024 (class 0 OID 41363)
-- Dependencies: 218
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5028 (class 0 OID 41377)
-- Dependencies: 222
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5072 (class 0 OID 0)
-- Dependencies: 227
-- Name: categoria_producto_id_categoria_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoria_producto_id_categoria_seq', 1, false);


--
-- TOC entry 5073 (class 0 OID 0)
-- Dependencies: 242
-- Name: contactos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.contactos_id_seq', 1, false);


--
-- TOC entry 5074 (class 0 OID 0)
-- Dependencies: 239
-- Name: detalle_factura_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_factura_id_seq', 1, false);


--
-- TOC entry 5075 (class 0 OID 0)
-- Dependencies: 235
-- Name: detalle_reserva_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.detalle_reserva_id_seq', 1, false);


--
-- TOC entry 5076 (class 0 OID 0)
-- Dependencies: 225
-- Name: facturas_id_factura_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.facturas_id_factura_seq', 1, false);


--
-- TOC entry 5077 (class 0 OID 0)
-- Dependencies: 231
-- Name: habitaciones_id_habitacion_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.habitaciones_id_habitacion_seq', 1, false);


--
-- TOC entry 5078 (class 0 OID 0)
-- Dependencies: 219
-- Name: hoteles_id_hotel_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hoteles_id_hotel_seq', 1, false);


--
-- TOC entry 5079 (class 0 OID 0)
-- Dependencies: 244
-- Name: limpieza_habitaciones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.limpieza_habitaciones_id_seq', 1, false);


--
-- TOC entry 5080 (class 0 OID 0)
-- Dependencies: 223
-- Name: metodo_pago_id_metodo_pago_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.metodo_pago_id_metodo_pago_seq', 1, false);


--
-- TOC entry 5081 (class 0 OID 0)
-- Dependencies: 237
-- Name: producto_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.producto_usuario_id_seq', 1, false);


--
-- TOC entry 5082 (class 0 OID 0)
-- Dependencies: 229
-- Name: productos_id_producto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.productos_id_producto_seq', 1, false);


--
-- TOC entry 5083 (class 0 OID 0)
-- Dependencies: 233
-- Name: reservas_id_reserva_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reservas_id_reserva_seq', 1, false);


--
-- TOC entry 5084 (class 0 OID 0)
-- Dependencies: 217
-- Name: roles_id_rol_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_rol_seq', 1, false);


--
-- TOC entry 5085 (class 0 OID 0)
-- Dependencies: 221
-- Name: usuarios_id_usuario_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuarios_id_usuario_seq', 1, false);


--
-- TOC entry 4836 (class 2606 OID 41434)
-- Name: categoria_producto categoria_producto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria_producto
    ADD CONSTRAINT categoria_producto_pkey PRIMARY KEY (id_categoria);


--
-- TOC entry 4852 (class 2606 OID 41565)
-- Name: contactos contactos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contactos
    ADD CONSTRAINT contactos_pkey PRIMARY KEY (id);


--
-- TOC entry 4848 (class 2606 OID 41531)
-- Name: detalle_factura detalle_factura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_factura
    ADD CONSTRAINT detalle_factura_pkey PRIMARY KEY (id_detalle, id);


--
-- TOC entry 4844 (class 2606 OID 41492)
-- Name: detalle_reserva detalle_reserva_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_reserva
    ADD CONSTRAINT detalle_reserva_pkey PRIMARY KEY (id);


--
-- TOC entry 4834 (class 2606 OID 41412)
-- Name: facturas facturas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.facturas
    ADD CONSTRAINT facturas_pkey PRIMARY KEY (id_factura);


--
-- TOC entry 4840 (class 2606 OID 41458)
-- Name: habitaciones habitaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.habitaciones
    ADD CONSTRAINT habitaciones_pkey PRIMARY KEY (id_habitacion);


--
-- TOC entry 4828 (class 2606 OID 41375)
-- Name: hoteles hoteles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hoteles
    ADD CONSTRAINT hoteles_pkey PRIMARY KEY (id_hotel);


--
-- TOC entry 4854 (class 2606 OID 41579)
-- Name: limpieza_habitaciones limpieza_habitaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.limpieza_habitaciones
    ADD CONSTRAINT limpieza_habitaciones_pkey PRIMARY KEY (id);


--
-- TOC entry 4832 (class 2606 OID 41403)
-- Name: metodo_pago metodo_pago_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.metodo_pago
    ADD CONSTRAINT metodo_pago_pkey PRIMARY KEY (id_metodo_pago);


--
-- TOC entry 4850 (class 2606 OID 41546)
-- Name: producto_habitacion producto_habitacion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto_habitacion
    ADD CONSTRAINT producto_habitacion_pkey PRIMARY KEY (id_producto, id_habitacion);


--
-- TOC entry 4846 (class 2606 OID 41509)
-- Name: producto_usuario producto_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto_usuario
    ADD CONSTRAINT producto_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 4838 (class 2606 OID 41441)
-- Name: productos productos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.productos
    ADD CONSTRAINT productos_pkey PRIMARY KEY (id_producto);


--
-- TOC entry 4842 (class 2606 OID 41475)
-- Name: reservas reservas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservas
    ADD CONSTRAINT reservas_pkey PRIMARY KEY (id_reserva);


--
-- TOC entry 4826 (class 2606 OID 41368)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id_rol);


--
-- TOC entry 4830 (class 2606 OID 41384)
-- Name: usuarios usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id_usuario);


--
-- TOC entry 4875 (class 2606 OID 41566)
-- Name: contactos contactos_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contactos
    ADD CONSTRAINT contactos_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuarios(id_usuario);


--
-- TOC entry 4871 (class 2606 OID 41532)
-- Name: detalle_factura detalle_factura_id_factura_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_factura
    ADD CONSTRAINT detalle_factura_id_factura_fkey FOREIGN KEY (id_factura) REFERENCES public.facturas(id_factura);


--
-- TOC entry 4872 (class 2606 OID 41537)
-- Name: detalle_factura detalle_factura_id_producto_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_factura
    ADD CONSTRAINT detalle_factura_id_producto_usuario_fkey FOREIGN KEY (id_producto_usuario) REFERENCES public.producto_usuario(id);


--
-- TOC entry 4866 (class 2606 OID 41498)
-- Name: detalle_reserva detalle_reserva_id_habitacion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_reserva
    ADD CONSTRAINT detalle_reserva_id_habitacion_fkey FOREIGN KEY (id_habitacion) REFERENCES public.habitaciones(id_habitacion);


--
-- TOC entry 4867 (class 2606 OID 41493)
-- Name: detalle_reserva detalle_reserva_id_reserva_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_reserva
    ADD CONSTRAINT detalle_reserva_id_reserva_fkey FOREIGN KEY (id_reserva) REFERENCES public.reservas(id_reserva);


--
-- TOC entry 4857 (class 2606 OID 41423)
-- Name: facturas facturas_id_hotel_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.facturas
    ADD CONSTRAINT facturas_id_hotel_fkey FOREIGN KEY (id_hotel) REFERENCES public.hoteles(id_hotel);


--
-- TOC entry 4858 (class 2606 OID 41418)
-- Name: facturas facturas_id_metodo_pago_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.facturas
    ADD CONSTRAINT facturas_id_metodo_pago_fkey FOREIGN KEY (id_metodo_pago) REFERENCES public.metodo_pago(id_metodo_pago);


--
-- TOC entry 4859 (class 2606 OID 41413)
-- Name: facturas facturas_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.facturas
    ADD CONSTRAINT facturas_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuarios(id_usuario);


--
-- TOC entry 4862 (class 2606 OID 41459)
-- Name: habitaciones habitaciones_id_hotel_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.habitaciones
    ADD CONSTRAINT habitaciones_id_hotel_fkey FOREIGN KEY (id_hotel) REFERENCES public.hoteles(id_hotel);


--
-- TOC entry 4863 (class 2606 OID 41464)
-- Name: habitaciones habitaciones_id_producto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.habitaciones
    ADD CONSTRAINT habitaciones_id_producto_fkey FOREIGN KEY (id_producto) REFERENCES public.productos(id_producto);


--
-- TOC entry 4876 (class 2606 OID 41585)
-- Name: limpieza_habitaciones limpieza_habitaciones_id_habitacion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.limpieza_habitaciones
    ADD CONSTRAINT limpieza_habitaciones_id_habitacion_fkey FOREIGN KEY (id_habitacion) REFERENCES public.habitaciones(id_habitacion);


--
-- TOC entry 4877 (class 2606 OID 41580)
-- Name: limpieza_habitaciones limpieza_habitaciones_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.limpieza_habitaciones
    ADD CONSTRAINT limpieza_habitaciones_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuarios(id_usuario);


--
-- TOC entry 4873 (class 2606 OID 41552)
-- Name: producto_habitacion producto_habitacion_id_habitacion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto_habitacion
    ADD CONSTRAINT producto_habitacion_id_habitacion_fkey FOREIGN KEY (id_habitacion) REFERENCES public.habitaciones(id_habitacion);


--
-- TOC entry 4874 (class 2606 OID 41547)
-- Name: producto_habitacion producto_habitacion_id_producto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto_habitacion
    ADD CONSTRAINT producto_habitacion_id_producto_fkey FOREIGN KEY (id_producto) REFERENCES public.productos(id_producto);


--
-- TOC entry 4868 (class 2606 OID 41510)
-- Name: producto_usuario producto_usuario_id_producto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto_usuario
    ADD CONSTRAINT producto_usuario_id_producto_fkey FOREIGN KEY (id_producto) REFERENCES public.productos(id_producto);


--
-- TOC entry 4869 (class 2606 OID 41520)
-- Name: producto_usuario producto_usuario_id_reserva_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto_usuario
    ADD CONSTRAINT producto_usuario_id_reserva_fkey FOREIGN KEY (id_reserva) REFERENCES public.reservas(id_reserva);


--
-- TOC entry 4870 (class 2606 OID 41515)
-- Name: producto_usuario producto_usuario_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto_usuario
    ADD CONSTRAINT producto_usuario_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuarios(id_usuario);


--
-- TOC entry 4860 (class 2606 OID 41442)
-- Name: productos productos_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.productos
    ADD CONSTRAINT productos_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoria_producto(id_categoria);


--
-- TOC entry 4861 (class 2606 OID 41447)
-- Name: productos productos_id_hotel_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.productos
    ADD CONSTRAINT productos_id_hotel_fkey FOREIGN KEY (id_hotel) REFERENCES public.hoteles(id_hotel);


--
-- TOC entry 4864 (class 2606 OID 41481)
-- Name: reservas reservas_id_habitacion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservas
    ADD CONSTRAINT reservas_id_habitacion_fkey FOREIGN KEY (id_habitacion) REFERENCES public.habitaciones(id_habitacion);


--
-- TOC entry 4865 (class 2606 OID 41476)
-- Name: reservas reservas_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservas
    ADD CONSTRAINT reservas_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuarios(id_usuario);


--
-- TOC entry 4855 (class 2606 OID 41390)
-- Name: usuarios usuarios_id_hotel_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_id_hotel_fkey FOREIGN KEY (id_hotel) REFERENCES public.hoteles(id_hotel);


--
-- TOC entry 4856 (class 2606 OID 41385)
-- Name: usuarios usuarios_id_rol_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_id_rol_fkey FOREIGN KEY (id_rol) REFERENCES public.roles(id_rol);


-- Completed on 2025-06-16 16:10:56

--
-- PostgreSQL database dump complete
--

