-- Conectar a la base de datos `postgres`
\c postgres;

-- Eliminar la base de datos si ya existe
DROP DATABASE IF EXISTS "paymentdetail";

-- Crear la nueva base de datos
CREATE DATABASE "paymentdetail";

-- Conectar a la nueva base de datos
\c paymentdetail;

-- Configuraciones generales del dump
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

-- Tabla payment_detail
CREATE TABLE public.payment_detail (
                                       id BIGSERIAL PRIMARY KEY,
                                       client_name VARCHAR(255),
                                       amount FLOAT,
                                       payment_receipt_id BIGINT
);

-- Insertar datos en payment_detail
COPY public.payment_detail (id, client_name, amount, payment_receipt_id) FROM stdin;
1	Juan Perez	7500.0	1
2	Maria Lopez	5000.0	2
3	Jose Muñoz	5000.0	2
4	Pedro Garcia	10000.0	3
5	Camila Soto	7000.0	4
6	Laura Ruiz	6500.0	4
7	Luis Martinez	8500.0	5
8	Marta Salinas	9000.0	5
9	Ana Diaz	6000.0	6
10	Jorge Fuentes	7000.0	7
11	Paula Herrera	5000.0	8
12	Diego Reyes	7500.0	9
13	Felipe Gómez	8000.0	9
14	Daniela Morales	7000.0	10
15	Claudio Nuñez	7200.0	10
\.

ALTER TABLE public.payment_detail OWNER TO postgres;
