-- Conectar a la base de datos `postgres`
\c postgres;

-- Eliminar la base de datos si ya existe
DROP DATABASE IF EXISTS "paymentreceipt";

-- Crear la nueva base de datos
CREATE DATABASE "paymentreceipt";

-- Conectar a la nueva base de datos
\c paymentreceipt;

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

-- Tabla payment_receipts
CREATE TABLE public.payment_receipts (
                                         id BIGSERIAL PRIMARY KEY,
                                         reservation_code VARCHAR(255),
                                         reservation_date_time TIMESTAMP,
                                         laps INTEGER,
                                         number_of_people INTEGER,
                                         reserved_by VARCHAR(255)
);

-- Tabla payment_detail
CREATE TABLE public.payment_detail (
                                       id BIGSERIAL PRIMARY KEY,
                                       client_name VARCHAR(255),
                                       amount FLOAT,
                                       payment_receipt_id BIGINT
);

-- Insertar datos en payment_receipts
COPY public.payment_receipts (id, reservation_code, reservation_date_time, laps, number_of_people, reserved_by) FROM stdin;
1	RES-001	2024-06-01 09:00:00	3	2	Juan Perez
2	RES-002	2024-06-02 10:00:00	5	4	Maria Lopez
3	RES-003	2024-06-03 11:00:00	2	1	Pedro Garcia
4	RES-004	2024-06-04 12:00:00	4	3	Camila Soto
5	RES-005	2024-06-05 13:00:00	6	2	Laura Ruiz
6	RES-006	2024-06-06 14:00:00	3	1	Luis Martinez
7	RES-007	2024-06-07 15:00:00	5	5	Marta Salinas
8	RES-008	2024-06-08 16:00:00	4	2	Ana Diaz
9	RES-009	2024-06-09 17:00:00	3	3	Jorge Fuentes
10	RES-010	2024-06-10 18:00:00	2	1	Paula Herrera
\.

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


ALTER TABLE public.payment_receipts OWNER TO postgres;
ALTER TABLE public.payment_detail OWNER TO postgres;
