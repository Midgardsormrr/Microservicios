INSERT INTO pricing (laps, max_time, total_duration, base_price) VALUES (10, 10, 30, 15000.0);
INSERT INTO pricing (laps, max_time, total_duration, base_price) VALUES (15, 15, 35, 20000.0);
INSERT INTO pricing (laps, max_time, total_duration, base_price) VALUES (20, 20, 40, 25000.0);

-- Actualiza automáticamente la secuencia del id si existe
SELECT setval('pricing_id_seq', (SELECT MAX(id) FROM pricing));
