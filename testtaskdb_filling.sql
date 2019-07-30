CREATE TABLE doctor (
   id NUMERIC IDENTITY PRIMARY KEY,
   name VARCHAR(50) NOT NULL,
   surname VARCHAR(50) NOT NULL,
   patronymic VARCHAR(50) NOT NULL,
   specialization VARCHAR(50) NOT NULL
);

CREATE TABLE patient (
   id NUMERIC IDENTITY PRIMARY KEY,
   name VARCHAR(50) NOT NULL,
   surname VARCHAR(50) NOT NULL,
   patronymic VARCHAR(50) NOT NULL,
   phoneNumber VARCHAR(12)
);

CREATE TABLE recipe (
   id NUMERIC IDENTITY PRIMARY KEY,
   description VARCHAR(255) NOT NULL,
   patientId NUMERIC NOT NULL,
   doctorId NUMERIC NOT NULL,
   creationDate DATE NOT NULL,
   duration INTEGER,
   priority VARCHAR(50) NOT NULL,
   FOREIGN KEY (patientId) REFERENCES patient(id),
   FOREIGN KEY (doctorId) REFERENCES doctor(id)
);

INSERT INTO doctor (name, surname, patronymic, specialization) VALUES ('AdocN', 'AdocS', 'AdocP', 'Aspec');
INSERT INTO doctor (name, surname, patronymic, specialization) VALUES ('BdocN', 'BdocS', 'BdocP', 'Bspec');
INSERT INTO doctor (name, surname, patronymic, specialization) VALUES ('CdocN', 'CdocS', 'CdocP', 'Cspec');
INSERT INTO doctor (name, surname, patronymic, specialization) VALUES ('DdocN', 'DdocS', 'DdocP', 'Dspec');
INSERT INTO doctor (name, surname, patronymic, specialization) VALUES ('EdocN', 'EdocS', 'EdocP', 'Espec');

INSERT INTO patient (name, surname, patronymic, phoneNumber) VALUES ('ApatN','ApatS','ApatP','000000');
INSERT INTO patient (name, surname, patronymic, phoneNumber) VALUES ('BpatN','BpatS','BpatP','111111');
INSERT INTO patient (name, surname, patronymic, phoneNumber) VALUES ('CpatN','CpatS','CpatP','222222');
INSERT INTO patient (name, surname, patronymic, phoneNumber) VALUES ('DpatN','DpatS','DpatP','333333');
INSERT INTO patient (name, surname, patronymic, phoneNumber) VALUES ('EpatN','EpatS','EpatP','444444');

INSERT INTO recipe (description, patientId, doctorId, creationDate, duration, priority) VALUES ('descr0', 0, 0, '2019-07-20', 15, 'normal');
INSERT INTO recipe (description, patientId, doctorId, creationDate, duration, priority) VALUES ('descr1', 1, 1, '2019-07-21', 10, 'statium');
INSERT INTO recipe (description, patientId, doctorId, creationDate, duration, priority) VALUES ('descr2', 4, 1, '2019-07-20', 20, 'cito');
INSERT INTO recipe (description, patientId, doctorId, creationDate, duration, priority) VALUES ('descr3', 3, 2, '2019-07-22', 30, 'cito');
INSERT INTO recipe (description, patientId, doctorId, creationDate, duration, priority) VALUES ('descr4', 2, 4, '2019-07-26', 20, 'statium');
INSERT INTO recipe (description, patientId, doctorId, creationDate, duration, priority) VALUES ('descr5', 1, 0, '2019-07-22', 10, 'normal');
INSERT INTO recipe (description, patientId, doctorId, creationDate, duration, priority) VALUES ('descr6', 0, 3, '2019-07-24', 30, 'statium');
INSERT INTO recipe (description, patientId, doctorId, creationDate, duration, priority) VALUES ('descr7', 2, 3, '2019-07-26', 10, 'cito');
INSERT INTO recipe (description, patientId, doctorId, creationDate, duration, priority) VALUES ('descr8', 3, 4, '2019-07-26', 15, 'cito');
INSERT INTO recipe (description, patientId, doctorId, creationDate, duration, priority) VALUES ('descr9', 4, 2, '2019-07-25', 20, 'normal');
