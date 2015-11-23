# --- !Ups

INSERT INTO uploads (name, mime) SELECT name, mime FROM horo_uploads;

INSERT INTO flowers (flower, upflower, icon_id, period, content, sort, locale)
  SELECT flower, upflower, icon_id, period, content, "order", locale FROM horo_flowers;

INSERT INTO druids (druid, updruid, icon_id, period, content, sort, locale)
  SELECT druid, updruid, icon_id, period, content, "order", locale FROM horo_druids;

INSERT INTO japans (japan, upjapan, icon_id, period, content, sort, locale)
  SELECT japan, upjapan, icon_id, period, content, "order", locale FROM horo_japans;

INSERT INTO chinas (china, upchina, icon_id, period, content, sort, locale)
  SELECT china, upchina, icon_id, period, content, "order", locale FROM horo_chinas_v2;

INSERT INTO amulets (amulet, upamulet, image_id, type, content, locale, rus_amulet)
  SELECT amulet, upamulet, image_id, type, content, locale, rus_amulet FROM horo_amulets_v2;

INSERT INTO names (name, upname, sex, content, locale)
  SELECT name, upname, sex, content, locale FROM horo_names_v2;

INSERT INTO dreams (dream, updream, type, content, locale, rus_dream)
  SELECT dream, updream, type, content, locale, rus_dream FROM horo_dreams_v2;

INSERT INTO horoscopes (type, kind, sign, period, content, locale)
  SELECT type, kind, sign, period, content, locale FROM horo_texts;

INSERT INTO periods (type, period, key, version)
  SELECT type, period, key, version FROM horo_periods;

# --- !Downs

TRUNCATE TABLE flowers RESTART IDENTITY CASCADE;

TRUNCATE TABLE druids RESTART IDENTITY CASCADE;

TRUNCATE TABLE japans RESTART IDENTITY CASCADE;

TRUNCATE TABLE chinas RESTART IDENTITY CASCADE;

TRUNCATE TABLE amulets RESTART IDENTITY CASCADE;

TRUNCATE TABLE uploads RESTART IDENTITY CASCADE;

TRUNCATE TABLE names RESTART IDENTITY CASCADE;

TRUNCATE TABLE dreams RESTART IDENTITY CASCADE;

TRUNCATE TABLE horoscopes RESTART IDENTITY CASCADE;

TRUNCATE TABLE periods RESTART IDENTITY CASCADE;