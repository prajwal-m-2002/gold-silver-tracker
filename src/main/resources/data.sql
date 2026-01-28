-- Initial metal prices
-- Using INSERT IGNORE or similar logic is tricky across DBs, 
-- but since we use ddl-auto=update, this file will help initialize data.

-- Note: In Spring Boot with Hibernate, data.sql runs AFTER Hibernate's ddl-auto.

-- Gold Price (Sample)
INSERT INTO metal_prices (metal_type, price_per_gram, updated_on) 
SELECT 'GOLD', 6000.00, CURRENT_DATE() 
WHERE NOT EXISTS (SELECT 1 FROM metal_prices WHERE metal_type = 'GOLD');

-- Silver Price (Sample)
INSERT INTO metal_prices (metal_type, price_per_gram, updated_on) 
SELECT 'SILVER', 75.00, CURRENT_DATE() 
WHERE NOT EXISTS (SELECT 1 FROM metal_prices WHERE metal_type = 'SILVER');

-- Initial Daily Prices for Charting
INSERT INTO daily_prices (metal_type, price_per_gram, price_date)
SELECT 'GOLD', 5900.00, CURRENT_DATE() - 2
WHERE NOT EXISTS (SELECT 1 FROM daily_prices WHERE metal_type = 'GOLD' AND price_date = CURRENT_DATE() - 2);

INSERT INTO daily_prices (metal_type, price_per_gram, price_date)
SELECT 'GOLD', 5950.00, CURRENT_DATE() - 1
WHERE NOT EXISTS (SELECT 1 FROM daily_prices WHERE metal_type = 'GOLD' AND price_date = CURRENT_DATE() - 1);

INSERT INTO daily_prices (metal_type, price_per_gram, price_date)
SELECT 'GOLD', 6000.00, CURRENT_DATE()
WHERE NOT EXISTS (SELECT 1 FROM daily_prices WHERE metal_type = 'GOLD' AND price_date = CURRENT_DATE());

INSERT INTO daily_prices (metal_type, price_per_gram, price_date)
SELECT 'SILVER', 72.00, CURRENT_DATE() - 2
WHERE NOT EXISTS (SELECT 1 FROM daily_prices WHERE metal_type = 'SILVER' AND price_date = CURRENT_DATE() - 2);

INSERT INTO daily_prices (metal_type, price_per_gram, price_date)
SELECT 'SILVER', 73.50, CURRENT_DATE() - 1
WHERE NOT EXISTS (SELECT 1 FROM daily_prices WHERE metal_type = 'SILVER' AND price_date = CURRENT_DATE() - 1);

INSERT INTO daily_prices (metal_type, price_per_gram, price_date)
SELECT 'SILVER', 75.00, CURRENT_DATE()
WHERE NOT EXISTS (SELECT 1 FROM daily_prices WHERE metal_type = 'SILVER' AND price_date = CURRENT_DATE());
