ALTER table accounts add active_sw boolean default false;
UPDATE accounts set active_sw = false;

ALTER TABLE customer ADD COLUMN active_sw boolean default false;
UPDATE customer set active_sw = false;