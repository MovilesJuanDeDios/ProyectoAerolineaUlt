/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  slon
 * Created: 24/05/2018
 */

-- ----------------- CREACION DE UN CURSOR -----------------
CREATE OR REPLACE PACKAGE TYPES
AS
     TYPE REF_CURSOR IS REF CURSOR;
END;
/

CREATE TABLE USUARIOS(
        NOMBRE VARCHAR(15) NOT NULL,
	APELLIDOS VARCHAR(30) NOT NULL,
	CORREO VARCHAR(30) NOT NULL,
	FECHA_NAC VARCHAR(30) NOT NULL,
	DIRECCION VARCHAR(90) NOT NULL,
        CELULAR VARCHAR(15) NOT NULL,
        TELEFONO VARCHAR(15) NOT NULL,
        USUARIO VARCHAR(16) NOT NULL,
        CONTRASENA VARCHAR(16) NOT NULL,
	CONSTRAINT PK_USUARIO PRIMARY KEY (USUARIO)
);

-- ----------------- INSERTAR USUARIO ----------------- 
CREATE OR REPLACE PROCEDURE INSERTAR_USUARIO(
NOMBRE IN USUARIOS.NOMBRE%TYPE, 
APELLIDOS IN USUARIOS.APELLIDOS%TYPE, 
CORREO IN USUARIOS.CORREO%TYPE, 
FECHA_NAC IN USUARIOS.FECHA_NAC%TYPE, 
DIRECCION IN USUARIOS.DIRECCION%TYPE,
CELULAR IN USUARIOS.CELULAR%TYPE,
TELEFONO IN USUARIOS.TELEFONO%TYPE,
USUARIO IN USUARIOS.USUARIO%TYPE,
CONTRASENA IN USUARIOS.CONTRASENA%TYPE)
AS
BEGIN
    INSERT INTO USUARIOS VALUES(NOMBRE, APELLIDOS, CORREO, FECHA_NAC, DIRECCION, 
CELULAR, TELEFONO, USUARIO, CONTRASENA);
END;
/

-- ----------------- ACTUALIZAR USUARIO ----------------- 
CREATE OR REPLACE PROCEDURE ACTUALIZAR_USUARIO(
NOMBREIN IN USUARIOS.NOMBRE%TYPE, 
APELLIDOSIN IN USUARIOS.APELLIDOS%TYPE, 
CORREOIN IN USUARIOS.CORREO%TYPE, 
FECHA_NACIN IN USUARIOS.FECHA_NAC%TYPE, 
DIRECCIONIN IN USUARIOS.DIRECCION%TYPE,
CELULARIN IN USUARIOS.CELULAR%TYPE,
TELEFONOIN IN USUARIOS.TELEFONO%TYPE,
USUARIOIN IN USUARIOS.USUARIO%TYPE, 
CONTRASENAIN IN USUARIOS.CONTRASENA%TYPE)
AS
BEGIN
    UPDATE USUARIOS SET NOMBRE=NOMBREIN, APELLIDOS=APELLIDOSIN,
CORREO=CORREOIN, FECHA_NAC=FECHA_NACIN, DIRECCION=DIRECCIONIN, 
CELULAR=CELULARIN, TELEFONO=TELEFONOIN, CONTRASENA=CONTRASENAIN  
WHERE USUARIO=USUARIOIN;
END;
/

-- ----------------- BUSCAR USUARIO ----------------- 
CREATE OR REPLACE FUNCTION BUSCAR_USUARIO(USUARIOIN IN USUARIOS.USUARIO%TYPE)
RETURN TYPES.REF_CURSOR 
AS 
        USUARIO_CURSOR TYPES.REF_CURSOR; 
BEGIN 
  OPEN USUARIO_CURSOR FOR 
       SELECT * FROM USUARIOS WHERE USUARIO=USUARIOIN; 
RETURN USUARIO_CURSOR; 
END;
/

Execute INSERTAR_USUARIO('Jose', 'Slon Conejo', 'joseslon2407@gmail.com', 'abr. 4, 2018', 'Heredia', '84348567', '23456709', 'nejos', '12345');
Execute INSERTAR_USUARIO('Andres', 'Cascante', 'casca@gmail.com', 'abr. 17, 2018', 'Heredia', '89003122', '28960111', 'calcuta', '12345');