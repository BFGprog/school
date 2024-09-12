create table users(
user_id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
name text not null,
driver_license boolean,
car_id int not null
)

create table cars(
id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
brand varchar(100),
model varchar(100),
price integer
)

ALTER TABLE users add  foreign key (car_id) references cars(id)