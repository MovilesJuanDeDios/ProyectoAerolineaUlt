/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  slon
 * Created: 24/05/2018
 */

-- ----------------- TABLA DE LUGARES -----------------
CREATE TABLE LUGARES(
  NOMBRE VARCHAR(30) NOT NULL,
  CONSTRAINT PK_NOMBRE PRIMARY KEY (NOMBRE)
);

-- ----------------- INSERTAR LUGAR ----------------- 
CREATE OR REPLACE PROCEDURE INSERTAR_LUGAR(
NOMBRE IN LUGARES.NOMBRE%TYPE)
AS
BEGIN
    INSERT INTO LUGARES VALUES(NOMBRE);
END;
/

-- ----------------- BUSCAR LUGAR ----------------- 
CREATE OR REPLACE FUNCTION BUSCAR_LUGAR(NOMBREIN IN LUGARES.NOMBRE%TYPE)
RETURN TYPES.REF_CURSOR 
AS 
        LUGAR_CURSOR TYPES.REF_CURSOR; 
BEGIN 
  OPEN LUGAR_CURSOR FOR 
       SELECT * FROM LUGARES WHERE NOMBRE=NOMBREIN; 
RETURN LUGAR_CURSOR; 
END;
/

-- ----------------- LISTAR LUGARES ----------------- 
CREATE OR REPLACE FUNCTION LISTAR_LUGARES
RETURN TYPES.REF_CURSOR  
AS 
       LUGAR_CURSOR TYPES.REF_CURSOR; 
BEGIN 
  OPEN LUGAR_CURSOR FOR 
       SELECT * FROM LUGARES; 
RETURN LUGAR_CURSOR; 
END;
/

Execute INSERTAR_LUGAR('Rio de Janeiro');
Execute INSERTAR_LUGAR('Santiago');
Execute INSERTAR_LUGAR('Ciudad de Mexico');
Execute INSERTAR_LUGAR('Bogota');
Execute INSERTAR_LUGAR('Nueva York');
Execute INSERTAR_LUGAR('Ottawa');