# --- !Ups

DELETE FROM periods WHERE type='weekly' AND key like '% - %';

DELETE FROM horoscopes WHERE type='weekly' AND period like '% - %';

# --- !Downs