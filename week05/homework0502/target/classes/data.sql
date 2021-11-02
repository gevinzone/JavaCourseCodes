INSERT INTO `gevin-db`.school (name)
VALUES ('school1');

INSERT INTO `gevin-db`.school (name)
VALUES ('school2');

#########

INSERT INTO `gevin-db`.Klass (name, school, create_time, update_time)
VALUES ('class1', 1, DEFAULT, DEFAULT);

INSERT INTO `gevin-db`.Klass (name, school, create_time, update_time)
VALUES ('class2', 1, DEFAULT, DEFAULT);

INSERT INTO `gevin-db`.Klass (name, school, create_time, update_time)
VALUES ('classA', 2, DEFAULT, DEFAULT);

INSERT INTO `gevin-db`.Klass (name, school, create_time, update_time)
VALUES ('classB', 2, DEFAULT, DEFAULT);

##########

INSERT INTO `gevin-db`.student (name, klass, create_time, update_time)
VALUES ('student1', 1, DEFAULT, DEFAULT);

INSERT INTO `gevin-db`.student (name, klass, create_time, update_time)
VALUES ('student2', 1, DEFAULT, DEFAULT);

INSERT INTO `gevin-db`.student (name, klass, create_time, update_time)
VALUES ('studentA', 3, DEFAULT, DEFAULT);

INSERT INTO `gevin-db`.student (name, klass, create_time, update_time)
VALUES ('studentB', 3, DEFAULT, DEFAULT);

