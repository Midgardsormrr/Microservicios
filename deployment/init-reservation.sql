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
2	RES-20250611-K001	2025-06-11 10:00:00	2025-06-11 10:30:00	4	3	CONFIRMED
3	RES-20250611-K002	2025-06-11 11:00:00	2025-06-11 11:30:00	3	2	CANCELLED
4	RES-20250611-K003	2025-06-11 12:00:00	2025-06-11 12:30:00	5	4	CONFIRMED
5	RES-20250611-K004	2025-06-11 13:00:00	2025-06-11 13:30:00	2	1	CONFIRMED
6	RES-20250611-K005	2025-06-11 14:00:00	2025-06-11 14:30:00	3	2	CONFIRMED
7	RES-20250611-K006	2025-06-11 15:00:00	2025-06-11 15:30:00	4	3	CONFIRMED
8	RES-20250611-K007	2025-06-11 16:00:00	2025-06-11 16:30:00	2	1	CANCELLED
9	RES-20250612-K001	2025-06-12 09:00:00	2025-06-12 09:30:00	3	2	CONFIRMED
10	RES-20250612-K002	2025-06-12 10:00:00	2025-06-12 10:30:00	4	4	CONFIRMED
11	RES-20250612-K003	2025-06-12 11:00:00	2025-06-12 11:30:00	5	5	CONFIRMED
12	RES-20250612-K004	2025-06-12 12:00:00	2025-06-12 12:30:00	2	2	CONFIRMED
13	RES-20250612-K005	2025-06-12 13:00:00	2025-06-12 13:30:00	3	2	CONFIRMED
14	RES-20250612-K006	2025-06-12 14:00:00	2025-06-12 14:30:00	4	3	CANCELLED
15	RES-20250613-K001	2025-06-13 10:00:00	2025-06-13 10:30:00	3	2	CONFIRMED
16	RES-20250613-K002	2025-06-13 11:00:00	2025-06-13 11:30:00	5	4	CONFIRMED
17	RES-20250613-K003	2025-06-13 12:00:00	2025-06-13 12:30:00	2	1	CONFIRMED
18	RES-20250613-K004	2025-06-13 13:00:00	2025-06-13 13:30:00	4	3	CONFIRMED
19	RES-20250613-K005	2025-06-13 14:00:00	2025-06-13 14:30:00	3	2	CANCELLED
20	RES-20250614-K001	2025-06-14 09:00:00	2025-06-14 09:30:00	4	3	CONFIRMED
21	RES-20250614-K002	2025-06-14 10:00:00	2025-06-14 10:30:00	5	4	CONFIRMED
22	RES-20250614-K003	2025-06-14 11:00:00	2025-06-14 11:30:00	3	2	CONFIRMED
23	RES-20250614-K004	2025-06-14 12:00:00	2025-06-14 12:30:00	2	1	CONFIRMED
24	RES-20250615-K001	2025-06-15 09:00:00	2025-06-15 09:30:00	3	2	CONFIRMED
25	RES-20250615-K002	2025-06-15 10:00:00	2025-06-15 10:30:00	4	3	CONFIRMED
26	RES-20250615-K003	2025-06-15 11:00:00	2025-06-15 11:30:00	5	4	CANCELLED
27	RES-20250615-K004	2025-06-15 12:00:00	2025-06-15 12:30:00	2	1	CONFIRMED
28	RES-20250616-K001	2025-06-16 10:00:00	2025-06-16 10:30:00	3	2	CONFIRMED
29	RES-20250616-K002	2025-06-16 11:00:00	2025-06-16 11:30:00	4	4	CONFIRMED
30	RES-20250616-K003	2025-06-16 12:00:00	2025-06-16 12:30:00	2	2	CONFIRMED
31	RES-20250617-K001	2025-06-17 09:00:00	2025-06-17 09:30:00	3	2	CONFIRMED
\.

COPY public.reservation_client (reservation_id, client_rut) FROM stdin;
1	12.345.678-5
1	20.123.456-7
2	10.111.222-3
3	11.222.333-4
4	12.333.444-5
5	13.444.555-6
6	14.555.666-7
7	15.666.777-8
8	16.777.888-9
9	17.888.999-0
10	18.999.000-1
11	19.000.111-2
12	20.111.222-3
13	21.222.333-4
14	22.333.444-5
15	23.444.555-6
16	24.555.666-7
17	25.666.777-8
18	26.777.888-9
19	27.888.999-0
20	28.999.000-1
21	29.000.111-2
22	30.111.222-3
23	31.222.333-4
24	32.333.444-5
25	33.444.555-6
26	34.555.666-7
27	35.666.777-8
28	36.777.888-9
29	37.888.999-0
30	38.999.000-1
31	39.000.111-2
\.

COPY public.reservation_kart (reservation_id, kart_code) FROM stdin;
1	K001
2	K002
3	K003
4	K004
5	K005
6	K006
7	K007
8	K008
9	K009
10	K010
11	K011
12	K012
13	K013
14	K014
15	K015
16	K001
17	K002
18	K003
19	K004
20	K005
21	K006
22	K007
23	K008
24	K009
25	K010
26	K011
27	K012
28	K013
29	K014
30	K015
31	K001
\.

-- Actualizar secuencia
SELECT pg_catalog.setval('public.reservation_id_seq', 31, true);
