/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  slon
 * Created: 24/05/2018
 */

-- ----------------- TABLA DE HORARIOS -----------------
CREATE TABLE HORARIOS(
  HORARIO INT NOT NULL,
  RUTA INT NULL,
  PRECIO DECIMAL(6) NOT NULL,
  DIA_SALIDA VARCHAR(10) NOT NULL,
  HORA_SALIDA VARCHAR(6) NOT NULL,
  DIA_LLEGADA VARCHAR(10) NOT NULL,
  HORA_LLEGADA VARCHAR(6) NOT NULL,
  CONSTRAINT PK_HORARIO PRIMARY KEY (HORARIO),
  CONSTRAINT FK_HORARIO_RUTA FOREIGN KEY (RUTA)
  REFERENCES RUTAS (RUTA)
);

CREATE SEQUENCE HORARIOS_SEQ START WITH 1;

CREATE OR REPLACE TRIGGER HORARIOS_AI 
BEFORE INSERT ON HORARIOS 
FOR EACH ROW
BEGIN
  SELECT HORARIOS_SEQ.NEXTVAL
  INTO   :new.HORARIO
  FROM   dual;
END;
/

-- ----------------- INSERTAR HORARIO ----------------- 
CREATE OR REPLACE PROCEDURE INSERTAR_HORARIO(
HORARIO IN HORARIOS.HORARIO%TYPE, 
RUTA IN HORARIOS.RUTA%TYPE,
PRECIO IN HORARIOS.PRECIO%TYPE,
DIA_SALIDA IN HORARIOS.DIA_SALIDA%TYPE,
HORA_SALIDA IN HORARIOS.HORA_SALIDA%TYPE,
DIA_LLEGADA IN HORARIOS.DIA_LLEGADA%TYPE,
HORA_LLEGADA IN HORARIOS.HORA_LLEGADA%TYPE)
AS
BEGIN
    INSERT INTO HORARIOS VALUES(HORARIO, RUTA, PRECIO, DIA_SALIDA, HORA_SALIDA,
DIA_LLEGADA, HORA_LLEGADA);
END;
/

-- ----------------- ACTUALIZAR HORARIO ----------------- 
CREATE OR REPLACE PROCEDURE ACTUALIZAR_HORARIO(
HORARIOIN IN HORARIOS.HORARIO%TYPE, 
RUTAIN IN HORARIOS.RUTA%TYPE,
PRECIOIN IN HORARIOS.PRECIO%TYPE,
DIA_SALIDAIN IN HORARIOS.DIA_SALIDA%TYPE,
HORA_SALIDAIN IN HORARIOS.HORA_SALIDA%TYPE,
DIA_LLEGADAIN IN HORARIOS.DIA_LLEGADA%TYPE,
HORA_LLEGADAIN IN HORARIOS.HORA_LLEGADA%TYPE)
AS
BEGIN
    UPDATE HORARIOS SET HORARIO=HORARIOIN, RUTA=RUTAIN, PRECIO=PRECIOIN,
DIA_SALIDA=DIA_SALIDAIN, HORA_SALIDA=HORA_SALIDAIN, DIA_LLEGADA=DIA_LLEGADAIN, 
HORA_LLEGADA=HORA_LLEGADAIN
WHERE HORARIO=HORARIOIN;
END;
/

-- ----------------- BUSCAR HORARIO ----------------- 
CREATE OR REPLACE FUNCTION BUSCAR_HORARIO(HORARIOIN IN HORARIOS.HORARIO%TYPE)
RETURN TYPES.REF_CURSOR 
AS 
       HORARIO_CURSOR TYPES.REF_CURSOR; 
BEGIN 
  OPEN HORARIO_CURSOR FOR 
       SELECT * FROM HORARIOS WHERE HORARIO=HORARIOIN; 
RETURN HORARIO_CURSOR; 
END;
/

-- ----------------- LISTAR HORARIOS ----------------- 
CREATE OR REPLACE FUNCTION LISTAR_HORARIOS
RETURN TYPES.REF_CURSOR  
AS 
       HORARIO_CURSOR TYPES.REF_CURSOR; 
BEGIN 
  OPEN HORARIO_CURSOR FOR 
       SELECT * FROM HORARIOS; 
RETURN HORARIO_CURSOR; 
END;
/