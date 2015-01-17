ALTER TABLE horo_amulets_v2 ADD COLUMN locale VARCHAR(10) NOT NULL DEFAULT 'ru';
DROP INDEX horo_amulets_v2_upamulet;
CREATE UNIQUE INDEX horo_amulets_v2_upamulet ON horo_amulets_v2 (upamulet, locale);

ALTER TABLE horo_chinas_v2 ADD COLUMN locale VARCHAR(10) NOT NULL DEFAULT 'ru';
DROP INDEX horo_chinas_v2_upchina;
CREATE UNIQUE INDEX horo_chinas_v2_upchina ON horo_chinas_v2 (upchina, locale);

ALTER TABLE horo_dreams_v2 ADD COLUMN locale VARCHAR(10) NOT NULL DEFAULT 'ru';
DROP INDEX horo_dreams_v2_updream;
CREATE UNIQUE INDEX horo_dreams_v2_updream ON horo_dreams_v2 (updream, locale);

ALTER TABLE horo_druids ADD COLUMN locale VARCHAR(10) NOT NULL DEFAULT 'ru';
DROP INDEX horo_druids_updruid;
CREATE UNIQUE INDEX horo_druids_updruid ON horo_druids (updruid, locale);

ALTER TABLE horo_flowers ADD COLUMN locale VARCHAR(10) NOT NULL DEFAULT 'ru';
DROP INDEX horo_flowers_upflower;
CREATE UNIQUE INDEX horo_flowers_upflower ON horo_flowers (upflower, locale);

ALTER TABLE horo_japans ADD COLUMN locale VARCHAR(10) NOT NULL DEFAULT 'ru';
DROP INDEX horo_japans_upjapan;
CREATE UNIQUE INDEX horo_japans_upjapan ON horo_japans (upjapan, locale);

ALTER TABLE horo_names_v2 ADD COLUMN locale VARCHAR(10) NOT NULL DEFAULT 'ru';
DROP INDEX horo_names_v2_upname;
CREATE UNIQUE INDEX horo_names_v2_upname ON horo_names_v2 (upname, locale);