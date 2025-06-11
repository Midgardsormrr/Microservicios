-- Conectar a la base de datos `postgres`
\c postgres;

-- Eliminar la base de datos si ya existe
DROP DATABASE IF EXISTS "reservation";

-- Crear la nueva base de datos
CREATE DATABASE "reservation";

-- Conectar a la nueva base de datos
\c reservation;

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.2 (Debian 16.2-1.pgdg120+2)

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

SET default_tablespace = '';
SET default_table_access_method = heap;

-- Tabla principal
CREATE TABLE public.reservation (
                                    id BIGINT NOT NULL,
                                    reservation_code VARCHAR(255),
                                    start_date_time TIMESTAMP,
                                    end_date_time TIMESTAMP,
                                    laps INTEGER,
                                    number_of_people INTEGER,
                                    status VARCHAR(255)
);

ALTER TABLE public.reservation OWNER TO postgres;

-- Secuencia
CREATE SEQUENCE public.reservation_id_seq
    AS BIGINT
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.reservation_id_seq OWNER TO postgres;
ALTER SEQUENCE public.reservation_id_seq OWNED BY public.reservation.id;
ALTER TABLE ONLY public.reservation ALTER COLUMN id SET DEFAULT nextval('public.reservation_id_seq'::regclass);

-- Clave primaria
ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (id);

-- Tabla de relación con clientes
CREATE TABLE public.reservation_client (
                                           reservation_id BIGINT REFERENCES public.reservation(id),
                                           client_rut VARCHAR(255)
);

ALTER TABLE public.reservation_client OWNER TO postgres;

-- Tabla de relación con karts
CREATE TABLE public.reservation_kart (
                                         reservation_id BIGINT REFERENCES public.reservation(id),
                                         kart_code VARCHAR(255)
);

ALTER TABLE public.reservation_kart OWNER TO postgres;

-- Datos
COPY public.reservation (id, reservation_code, start_date_time, end_date_time, laps, number_of_people, status) FROM stdin;
1	RES-20231025-K001	2023-10-25 09:00:00	2023-10-25 09:30:00	3	2	CONFIRMED
\.

COPY public.reservation_client (reservation_id, client_rut) FROM stdin;
1	12.345.678-5
1	20.123.456-7
\.

COPY public.reservation_kart (reservation_id, kart_code) FROM stdin;
1	K001
\.

-- Actualizar secuencia
SELECT pg_catalog.setval('public.reservation_id_seq', 1, true);
