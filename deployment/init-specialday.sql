-- Conectar a la base de datos `postgres`
\c postgres;

-- Eliminar la base de datos si ya existe
DROP DATABASE IF EXISTS "specialday";

-- Crear la nueva base de datos
CREATE DATABASE "specialday";

-- Conectar a la nueva base de datos
\c specialday;

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
-- Name: special_day; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.special_day (
                                    id BIGINT NOT NULL,
                                    date DATE,
                                    type VARCHAR(255),
                                    price_multiplier DOUBLE PRECISION
);

ALTER TABLE public.special_day OWNER TO postgres;

--
-- Name: special_day_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.special_day_id_seq
    AS BIGINT
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.special_day_id_seq OWNER TO postgres;

--
-- Name: special_day_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.special_day_id_seq OWNED BY public.special_day.id;

--
-- Name: special_day id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.special_day ALTER COLUMN id SET DEFAULT nextval('public.special_day_id_seq'::regclass);

--
-- Data for Name: special_day; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.special_day (id, date, type, price_multiplier) FROM stdin;
1	2023-12-25	HOLIDAY	1.5
2	2023-12-31	WEEKEND	1.2
\.

--
-- Name: special_day_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.special_day_id_seq', 2, true);

--
-- Name: special_day special_day_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.special_day
    ADD CONSTRAINT special_day_pkey PRIMARY KEY (id);

--
-- PostgreSQL database dump complete
--
