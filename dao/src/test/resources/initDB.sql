CREATE TABLE accounts
(
  id             INT PRIMARY KEY AUTO_INCREMENT,
  firstName      VARCHAR(45) NOT NULL,
  secondName     VARCHAR(45) NOT NULL,
  middleName     VARCHAR(45) NULL,
  gender         CHAR(7)     NULL,
  birthDate      DATE        NOT NULL,
  homeAddress    VARCHAR(45) NULL,
  workAddress    VARCHAR(45) NULL,
  email          VARCHAR(45) NOT NULL UNIQUE,
  icq            INT         NULL,
  skype          VARCHAR(45) NULL,
  additionalInfo VARCHAR(45) NULL,
  password       VARCHAR(15) NOT NULL
);