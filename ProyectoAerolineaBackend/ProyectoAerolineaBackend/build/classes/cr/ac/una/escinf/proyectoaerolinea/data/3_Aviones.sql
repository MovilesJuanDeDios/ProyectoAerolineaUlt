/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  slon
 * Created: 24/05/2018
 */

-- ----------------- TABLA DE AVIONES -----------------
CREATE TABLE AVIONES(
    AVION INT NOT NULL,
    TIPO_AVION INT NOT NULL,
    CONSTRAINT PK_AVION PRIMARY KEY (AVION),
    CONSTRAINT FK_AVION_TIPOAVION 
    FOREIGN KEY (TIPO_AVION)
    REFERENCES TIPO_AVIONES (TIPO_AVION)
);

-- ----------------- INSERTAR AVION ----------------- 
CREATE OR REPLACE PROCEDURE INSERTAR_AVION(
AVION IN AVIONES.AVION%TYPE, 
TIPO_AVION IN AVIONES.TIPO_AVION%TYPE)
AS
BEGIN
    INSERT INTO AVIONES VALUES(AVION, TIPO_AVION);
END;
/

-- ----------------- ACTUALIZAR AVION ----------------- 
CREATE OR REPLACE PROCEDURE ACTUALIZAR_AVION(
AVIONIN IN AVIONES.AVION%TYPE, 
TIPO_AVIONIN IN AVIONES.TIPO_AVION%TYPE)
AS
BEGIN
    UPDATE AVIONES SET AVION=AVIONIN, TIPO_AVION=TIPO_AVIONIN 
WHERE AVION=AVIONIN;
END;
/

-- ----------------- BUSCAR AVION ----------------- 
CREATE OR REPLACE FUNCTION BUSCAR_AVION(AVIONIN IN AVIONES.AVION%TYPE)
RETURN TYPES.REF_CURSOR 
AS 
        AVION_CURSOR TYPES.REF_CURSOR; 
BEGIN 
  OPEN AVION_CURSOR FOR 
       SELECT * FROM AVIONES WHERE AVION=AVIONIN; 
RETURN AVION_CURSOR; 
END;
/

-- ----------------- LISTAR AVIONES ----------------- 
CREATE OR REPLACE FUNCTION LISTAR_AVIONES
RETURN TYPES.REF_CURSOR  
AS 
       AVION_CURSOR TYPES.REF_CURSOR; 
BEGIN 
  OPEN AVION_CURSOR FOR 
       SELECT * FROM AVIONES; 
RETURN AVION_CURSOR; 
END;
/
