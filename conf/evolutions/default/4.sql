# --- !Ups

ALTER TABLE horo_amulets RENAME TO __horo_amulets__;

ALTER TABLE horo_amulets_v2 RENAME TO __horo_amulets_v2__;

ALTER TABLE horo_chinas RENAME TO __horo_chinas__;

ALTER TABLE horo_chinas_v2 RENAME TO __horo_chinas_v2__;

ALTER TABLE horo_dreams RENAME TO __horo_dreams__;

ALTER TABLE horo_dreams_v2 RENAME TO __horo_dreams_v2__;

ALTER TABLE horo_druids RENAME TO __horo_druids__;

ALTER TABLE horo_flowers RENAME TO __horo_flowers__;

ALTER TABLE horo_japans RENAME TO __horo_japans__;

ALTER TABLE horo_messages RENAME TO __horo_messages__;

ALTER TABLE horo_names RENAME TO __horo_names__;

ALTER TABLE horo_names_v2 RENAME TO __horo_names_v2__;

ALTER TABLE horo_texts RENAME TO __horo_texts__;

ALTER TABLE horo_updates RENAME TO __horo_updates__;

ALTER TABLE horo_uploads RENAME TO __horo_uploads__;

ALTER TABLE horo_period_keys RENAME TO __horo_period_keys__;

ALTER TABLE horo_periods RENAME TO __horo_periods__;

# --- !Downs

ALTER TABLE __horo_amulets__ RENAME TO horo_amulets;

ALTER TABLE __horo_amulets_v2__ RENAME TO horo_amulets_v2;

ALTER TABLE __horo_chinas__ RENAME TO horo_chinas;

ALTER TABLE __horo_chinas_v2__ RENAME TO horo_chinas_v2;

ALTER TABLE __horo_dreams__ RENAME TO horo_dreams;

ALTER TABLE __horo_dreams_v2__ RENAME TO horo_dreams_v2;

ALTER TABLE __horo_druids__ RENAME TO horo_druids;

ALTER TABLE __horo_flowers__ RENAME TO horo_flowers;

ALTER TABLE __horo_japans__ RENAME TO horo_japans;

ALTER TABLE __horo_messages__ RENAME TO horo_messages;

ALTER TABLE __horo_names__ RENAME TO horo_names;

ALTER TABLE __horo_names_v2__ RENAME TO horo_names_v2;

ALTER TABLE __horo_texts__ RENAME TO horo_texts;

ALTER TABLE __horo_updates__ RENAME TO horo_updates;

ALTER TABLE __horo_uploads__ RENAME TO horo_uploads;

ALTER TABLE __horo_period_keys__ RENAME TO horo_period_keys;

ALTER TABLE __horo_periods__ RENAME TO horo_periods;