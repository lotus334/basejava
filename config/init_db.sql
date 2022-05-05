create table resume
(
    uuid char(36) not null primary key,
    full_name text not null
);

create table contact
(
    id serial not null primary key,
    resume_uuid char(36) not null references resume on delete cascade,
    type text not null,
    value integer not null
);

create unique index contact_uuid_type_index
    on contact (resume_uuid, type);

