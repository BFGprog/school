ALTER TABLE student
ADD CONSTRAINT age_constraint CHECK (age >= 16) ,
ADD CONSTRAINT name_unique unique (name),
alter column age set DEFAULT (20)

ALTER TABLE faculty
ADD CONSTRAINT color_name_unique unique (name, color)
