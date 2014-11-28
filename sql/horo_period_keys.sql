CREATE TABLE horo_period_keys (
  period varchar(30) NOT NULL
);
CREATE INDEX horo_period_keys_period ON horo_period_keys (period);
 

INSERT INTO horo_period_keys VALUES ('cur');
INSERT INTO horo_period_keys VALUES ('prev');
INSERT INTO horo_period_keys VALUES ('today');
INSERT INTO horo_period_keys VALUES ('tomorrow');
INSERT INTO horo_period_keys VALUES ('tomorrow02');
INSERT INTO horo_period_keys VALUES ('yesterday');
