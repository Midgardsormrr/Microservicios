INSERT INTO special_day (date, type, price_multiplier) VALUES ('2025-01-01', 'HOLIDAY', 1.2);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('2025-03-28', 'HOLIDAY', 1.1);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('2025-03-29', 'HOLIDAY', 1.1);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('2025-05-01', 'HOLIDAY', 1.2);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('2025-05-21', 'HOLIDAY', 1.1);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('2025-06-20', 'HOLIDAY', 1.1);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('2025-06-29', 'HOLIDAY', 1.1);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('2025-07-16', 'HOLIDAY', 1.1);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('2025-08-15', 'HOLIDAY', 1.1);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('2025-09-18', 'HOLIDAY', 1.3);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('2025-09-19', 'HOLIDAY', 1.3);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('2025-10-12', 'HOLIDAY', 1.1);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('2025-10-31', 'HOLIDAY', 1.1);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('2025-11-01', 'HOLIDAY', 1.1);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('2025-12-08', 'HOLIDAY', 1.1);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('2025-12-25', 'HOLIDAY', 1.2);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('1900-01-01', 'WEEKEND', 1.4);
INSERT INTO special_day (date, type, price_multiplier) VALUES ('1900-01-02', 'BIRTHDAY', 0.5);

-- Ajuste de la secuencia (si existe una columna id autogenerada)
SELECT setval('special_day_id_seq', (SELECT MAX(id) FROM special_day));
