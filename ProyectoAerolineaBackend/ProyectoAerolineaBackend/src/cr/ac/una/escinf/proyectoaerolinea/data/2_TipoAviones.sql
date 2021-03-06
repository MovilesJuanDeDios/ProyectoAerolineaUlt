/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  slon
 * Created: 24/05/2018
 */

-- ----------------- TABLA DE TIPOAVIONES -----------------
CREATE TABLE TIPO_AVIONES(
    TIPO_AVION INT NOT NULL,
    CAPACIDAD INT NOT NULL,
    ANNO VARCHAR(4) NOT NULL,
    MODELO VARCHAR(15) NOT NULL,
    MARCA VARCHAR(15) NOT NULL,
    FILAS INT NOT NULL,
    ASIENTOS_POR_FILA INT NOT NULL,
    CONSTRAINT PK_TIPO_AVION PRIMARY KEY (TIPO_AVION)
);

-- ----------------- INSERTAR TIPOAVION ----------------- 
CREATE OR REPLACE PROCEDURE INSERTAR_TIPOAVION(
TIPO_AVION IN TIPO_AVIONES.TIPO_AVION%TYPE, 
CAPACIDAD IN TIPO_AVIONES.CAPACIDAD%TYPE, 
ANNO IN TIPO_AVIONES.ANNO%TYPE, 
MODELO IN TIPO_AVIONES.MODELO%TYPE, 
MARCA IN TIPO_AVIONES.MARCA%TYPE,
FILAS IN TIPO_AVIONES.FILAS%TYPE,
ASIENTOS_POR_FILA IN TIPO_AVIONES.ASIENTOS_POR_FILA%TYPE)
AS
BEGIN
    INSERT INTO TIPO_AVIONES VALUES(TIPO_AVION, CAPACIDAD, ANNO, MODELO, MARCA, 
FILAS, ASIENTOS_POR_FILA);
END;
/

-- ----------------- ACTUALIZAR TIPOAVION ----------------- 
CREATE OR REPLACE PROCEDURE ACTUALIZAR_TIPOAVION(
TIPO_AVIONIN IN TIPO_AVIONES.TIPO_AVION%TYPE, 
CAPACIDADIN IN TIPO_AVIONES.CAPACIDAD%TYPE, 
ANNOIN IN TIPO_AVIONES.ANNO%TYPE, 
MODELOIN IN TIPO_AVIONES.MODELO%TYPE, 
MARCAIN IN TIPO_AVIONES.MARCA%TYPE,
FILASIN IN TIPO_AVIONES.FILAS%TYPE,
ASIENTOS_POR_FILAIN IN TIPO_AVIONES.ASIENTOS_POR_FILA%TYPE)
AS
BEGIN
    UPDATE TIPO_AVIONES SET TIPO_AVION=TIPO_AVIONIN, CAPACIDAD=CAPACIDADIN,
ANNO=ANNOIN, MODELO=MODELOIN, MARCA=MARCAIN, FILAS=FILASIN, 
ASIENTOS_POR_FILA=ASIENTOS_POR_FILAIN WHERE TIPO_AVION=TIPO_AVIONIN;
END;
/

-- ----------------- BUSCAR TIPOAVION ----------------- 
CREATE OR REPLACE FUNCTION BUSCAR_TIPOAVION(TIPOAVIONIN IN TIPO_AVIONES.TIPO_AVION%TYPE)
RETURN TYPES.REF_CURSOR 
AS 
        TIPOAVION_CURSOR TYPES.REF_CURSOR; 
BEGIN 
  OPEN TIPOAVION_CURSOR FOR 
       SELECT * FROM TIPO_AVIONES WHERE TIPO_AVION=TIPOAVIONIN; 
RETURN TIPOAVION_CURSOR; 
END;
/

-- ----------------- LISTAR TIPOAVIONES ----------------- 
CREATE OR REPLACE FUNCTION LISTAR_TIPOAVIONES
RETURN TYPES.REF_CURSOR  
AS 
       TIPOAVION_CURSOR TYPES.REF_CURSOR; 
BEGIN 
  OPEN TIPOAVION_CURSOR FOR 
       SELECT * FROM TIPO_AVIONES; 
RETURN TIPOAVION_CURSOR; 
END;
/

Execute INSERTAR_TIPOAVION(1, 300, '2005', '777', 'Boeing', 10, 30);
Execute INSERTAR_TIPOAVION(2, 220, '2004', 'A320', 'Airbus', 10, 22);