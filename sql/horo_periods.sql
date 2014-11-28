CREATE TABLE horo_periods (
  id serial NOT NULL,
  "type" varchar(10) NOT NULL,
  period varchar(30) NOT NULL,
  "key" varchar(30) NOT NULL,
  PRIMARY KEY (id)
);
CREATE UNIQUE INDEX horo_periods_period ON horo_periods (period, type);
CREATE INDEX horo_periods_key ON horo_periods (key);
CREATE INDEX horo_periods_type ON horo_periods (type);


INSERT INTO horo_periods (type, period, key) VALUES ('yearly', '2014', '2014');
INSERT INTO horo_periods (type, period, key) VALUES ('yearly', '2015', '2015');
INSERT INTO horo_periods (type, period, key) VALUES ('monthly', 'Nov2014', 'Nov2014');
INSERT INTO horo_periods (type, period, key) VALUES ('monthly', 'Dec2014', 'Dec2014');
INSERT INTO horo_periods (type, period, key) VALUES ('weekly', 'cur', '24 - 30 ноября');
INSERT INTO horo_periods (type, period, key) VALUES ('weekly', 'prev', '17 - 23 ноября');
INSERT INTO horo_periods (type, period, key) VALUES ('daily', 'yesterday', '27.11.2014');
INSERT INTO horo_periods (type, period, key) VALUES ('daily', 'today', '28.11.2014');
INSERT INTO horo_periods (type, period, key) VALUES ('daily', 'tomorrow', '29.11.2014');
INSERT INTO horo_periods (type, period, key) VALUES ('daily', 'tomorrow02', '30.11.2014');
