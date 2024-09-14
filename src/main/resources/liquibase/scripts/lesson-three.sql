-- liquibase formatted sql

-- changeset bfgprog:1
create index student_name_idx on student (name);


-- changeset bfgprog:2
create index faculty_nc_idx on faculty (name, color);


