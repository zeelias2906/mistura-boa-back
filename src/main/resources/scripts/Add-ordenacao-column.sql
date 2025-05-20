alter table categoria add column ordenacao bigint;
update categoria set ordenacao = id_categoria where ordenacao is null;
alter table categoria modify column ordenacao bigint not null;
