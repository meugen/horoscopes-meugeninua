# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table amulets (
  id                            serial not null,
  upamulet                      varchar(100) not null,
  amulet                        varchar(100) not null,
  image_id                      integer not null,
  type                          integer not null,
  content                       TEXT not null,
  locale                        varchar(10) not null,
  rus_amulet                    varchar(100) not null,
  guid                          uuid not null,
  constraint pk_amulets primary key (id)
);

create table chinas (
  id                            serial not null,
  china                         varchar(100) not null,
  upchina                       varchar(100) not null,
  icon_id                       integer not null,
  period                        varchar(100) not null,
  content                       TEXT not null,
  sort                          integer not null,
  locale                        varchar(10) not null,
  guid                          uuid not null,
  constraint pk_chinas primary key (id)
);

create table dreams (
  id                            serial not null,
  dream                         varchar(100) not null,
  updream                       varchar(100) not null,
  type                          integer not null,
  content                       TEXT not null,
  locale                        varchar(10) not null,
  rus_dream                     varchar(100) not null,
  guid                          uuid not null,
  constraint pk_dreams primary key (id)
);

create table druids (
  id                            serial not null,
  druid                         varchar(100) not null,
  updruid                       varchar(100) not null,
  icon_id                       integer not null,
  period                        varchar(100) not null,
  content                       TEXT not null,
  sort                          integer not null,
  locale                        varchar(10) not null,
  guid                          uuid not null,
  constraint pk_druids primary key (id)
);

create table flowers (
  id                            serial not null,
  flower                        varchar(100) not null,
  upflower                      varchar(100) not null,
  icon_id                       integer not null,
  period                        varchar(100) not null,
  content                       TEXT not null,
  sort                          integer not null,
  locale                        varchar(10) not null,
  guid                          uuid not null,
  constraint pk_flowers primary key (id)
);

create table horoscopes (
  id                            serial not null,
  type                          varchar(10) not null,
  kind                          varchar(30) not null,
  sign                          varchar(30) not null,
  period                        varchar(30) not null,
  content                       TEXT not null,
  locale                        varchar(10) not null,
  guid                          uuid not null,
  constraint pk_horoscopes primary key (id)
);

create table japans (
  id                            serial not null,
  japan                         varchar(100) not null,
  upjapan                       varchar(100) not null,
  icon_id                       integer not null,
  period                        varchar(100) not null,
  content                       TEXT not null,
  sort                          integer not null,
  locale                        varchar(10) not null,
  guid                          uuid not null,
  constraint pk_japans primary key (id)
);

create table names (
  id                            serial not null,
  name                          varchar(100) not null,
  upname                        varchar(100) not null,
  sex                           integer not null,
  content                       TEXT not null,
  locale                        varchar(10) not null,
  guid                          uuid not null,
  constraint pk_names primary key (id)
);

create table periods (
  id                            serial not null,
  type                          varchar(10) not null,
  name                          varchar(30) not null,
  key                           varchar(30) not null,
  guid                          uuid not null,
  constraint pk_periods primary key (id)
);

create table updates (
  id                            serial not null,
  update_date                   timestamptz not null,
  uri                           varchar(255) not null,
  respone                       TEXT not null,
  guid                          uuid not null,
  constraint pk_updates primary key (id)
);

create table uploads (
  id                            serial not null,
  name                          varchar(255) not null,
  mime                          varchar(255) not null,
  guid                          uuid not null,
  constraint pk_uploads primary key (id)
);

create index ix_amulets_image_id on amulets (image_id);
alter table amulets add constraint fk_amulets_image_id foreign key (image_id) references uploads (id) on delete restrict on update restrict;

create index ix_chinas_icon_id on chinas (icon_id);
alter table chinas add constraint fk_chinas_icon_id foreign key (icon_id) references uploads (id) on delete restrict on update restrict;

create index ix_druids_icon_id on druids (icon_id);
alter table druids add constraint fk_druids_icon_id foreign key (icon_id) references uploads (id) on delete restrict on update restrict;

create index ix_flowers_icon_id on flowers (icon_id);
alter table flowers add constraint fk_flowers_icon_id foreign key (icon_id) references uploads (id) on delete restrict on update restrict;

create index ix_japans_icon_id on japans (icon_id);
alter table japans add constraint fk_japans_icon_id foreign key (icon_id) references uploads (id) on delete restrict on update restrict;


# --- !Downs

alter table if exists amulets drop constraint if exists fk_amulets_image_id;
drop index if exists ix_amulets_image_id;

alter table if exists chinas drop constraint if exists fk_chinas_icon_id;
drop index if exists ix_chinas_icon_id;

alter table if exists druids drop constraint if exists fk_druids_icon_id;
drop index if exists ix_druids_icon_id;

alter table if exists flowers drop constraint if exists fk_flowers_icon_id;
drop index if exists ix_flowers_icon_id;

alter table if exists japans drop constraint if exists fk_japans_icon_id;
drop index if exists ix_japans_icon_id;

drop table if exists amulets cascade;

drop table if exists chinas cascade;

drop table if exists dreams cascade;

drop table if exists druids cascade;

drop table if exists flowers cascade;

drop table if exists horoscopes cascade;

drop table if exists japans cascade;

drop table if exists names cascade;

drop table if exists periods cascade;

drop table if exists updates cascade;

drop table if exists uploads cascade;

