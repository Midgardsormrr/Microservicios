-- Conectar a la base de datos `postgres`
\c postgres;

-- Eliminar la base de datos si ya existe
DROP DATABASE IF EXISTS "kart";

-- Crear la nueva base de datos
CREATE DATABASE "kart";

-- Conectar a la nueva base de datos
\c kart;

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
-- Name: kart; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.kart (
                             id BIGINT NOT NULL,
                             code VARCHAR(255),
                             status VARCHAR(255)
);

ALTER TABLE public.kart OWNER TO postgres;

--
-- Name: kart_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.kart_id_seq
    AS BIGINT
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.kart_id_seq OWNER TO postgres;

--
-- Name: kart_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.kart_id_seq OWNED BY public.kart.id;

--
-- Name: kart id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.kart ALTER COLUMN id SET DEFAULT nextval('public.kart_id_seq'::regclass);

--
-- Data for Name: kart; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.kart (id, code, status) FROM stdin;
1	K001	AVAILABLE
2	K002	AVAILABLE
3	K003	UNDER_MAINTENANCE
4	K004	AVAILABLE
5	K005	AVAILABLE
6	K006	UNDER_MAINTENANCE
7	K007	AVAILABLE
8	K008	AVAILABLE
9	K009	UNDER_MAINTENANCE
10	K010	AVAILABLE
11	K011	AVAILABLE
12	K012	UNDER_MAINTENANCE
13	K013	AVAILABLE
14	K014	AVAILABLE
15	K015	UNDER_MAINTENANCE
\.

--
-- Name: kart_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.kart_id_seq', 15, true);

--
-- Name: kart kart_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.kart
    ADD CONSTRAINT kart_pkey PRIMARY KEY (id);

--
-- PostgreSQL database dump complete
