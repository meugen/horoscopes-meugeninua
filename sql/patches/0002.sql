--DROP SEQUENCE horo_amulets_id_seq;
ALTER SEQUENCE horo_amulets_v2_id_seq RENAME TO horo_amulets_id_seq;
ALTER TABLE horo_amulets ALTER COLUMN id SET DEFAULT nextval('horo_amulets_id_seq'); 

--DROP SEQUENCE horo_chinas_id_seq;
ALTER SEQUENCE horo_chinas_v2_id_seq RENAME TO horo_chinas_id_seq;
ALTER TABLE horo_chinas ALTER COLUMN id SET DEFAULT nextval('horo_chinas_id_seq');

--DROP SEQUENCE horo_dreams_id_seq;
ALTER SEQUENCE horo_dreams_v2_id_seq RENAME TO horo_dreams_id_seq;
ALTER TABLE horo_dreams ALTER COLUMN id SET DEFAULT nextval('horo_dreams_id_seq');

--DROP SEQUENCE horo_names_id_seq;
ALTER SEQUENCE horo_names_v2_id_seq RENAME TO horo_names_id_seq;
ALTER TABLE horo_names ALTER COLUMN id SET DEFAULT nextval('horo_names_id_seq');