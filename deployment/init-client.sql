-- Conectar a la base de datos `postgres`
\c postgres;

-- Eliminar la base de datos si ya existe
DROP DATABASE IF EXISTS "client";

-- Crear la nueva base de datos
CREATE DATABASE "client";

-- Conectar a la nueva base de datos
\c client;

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

--
-- Name: client; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client (
                               id BIGINT NOT NULL,
                               rut VARCHAR(255),
                               name VARCHAR(255),
                               email VARCHAR(255),
                               birth_date DATE,
                               monthly_visit_count INTEGER
);

ALTER TABLE public.client OWNER TO postgres;

--
-- Name: client_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.client_id_seq
    AS BIGINT
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.client_id_seq OWNER TO postgres;

--
-- Name: client_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.client_id_seq OWNED BY public.client.id;

--
-- Name: client id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client ALTER COLUMN id SET DEFAULT nextval('public.client_id_seq'::regclass);

--
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client (id, rut, name, email, birth_date, monthly_visit_count) FROM stdin;
1	12.345.678-5	Juan Perez	juan.perez@example.com	1990-05-10	3
2	20.123.456-7	María López	maria.lopez@example.com	1985-12-01	7
3	11.222.333-0	Pedro García	pedro.garcia@example.com	2000-03-15	1
4	15.678.912-3	Ana Torres	ana.torres@example.com	1995-07-22	5
5	17.345.987-1	Carlos Díaz	carlos.diaz@example.com	1988-10-03	2
6	16.890.321-4	Sofía Rojas	sofia.rojas@example.com	1992-01-30	4
7	13.654.789-6	Luis Fuentes	luis.fuentes@example.com	1983-06-12	6
8	14.321.678-2	Valentina Soto	valentina.soto@example.com	1996-11-17	3
9	18.765.432-1	Javier Morales	javier.morales@example.com	1999-09-25	1
10	19.543.210-9	Fernanda Ruiz	fernanda.ruiz@example.com	2001-04-05	2
11	21.987.654-3	Ricardo Vega	ricardo.vega@example.com	1991-03-19	7
12	22.345.678-4	Marcela Bravo	marcela.bravo@example.com	1987-08-08	5
\.

--
-- Name: client_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.client_id_seq', 12, true);

--
-- Name: client client_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);

--
-- PostgreSQL database dump complete
--
