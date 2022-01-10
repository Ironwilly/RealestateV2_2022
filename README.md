# RealstateV2
Proyecto Realstatev2 - 2022




          .-'-.
        /`     |__
      /`  _.--`-,-`
      '-|`   a '<-.   []
        \     _\__) \=`
         C_  `    ,_/
           | ;----'
      _.---| |--._
    .'  _./' '\._ '.
 


## Desarrollada por :
#### - Guillermo Ferrari Ferrari

Esta API REST permite controlar el funcionamiento de la app REAL ESTATEv2-2022



## Entidades

Las entidades que componen esta API son:

- Vivienda
- Inmobiliaria
- User
- Interesa 

Para el correcto manejo de cada entidad contamos con las siguientes clases:

### Vivienda

- **ViviendaController**   

- **ViviendaRepository**
  
- **ViviendaService**
 

### Inmobiliaria

- **InmobiliariaController**

- **InmobiliariaRepository**
  
- **InmobiliariaService**


### Interesa

- **InteresaController**

- **InteresaRepository**
  
- **InteresaService**



Por otro lado, para el correcto manejo de las asociaciones entre entidades, se han creado diferentes DTOs (Data Transfer Object); estos nos permiten crear nuevos objetos con los atributos de las entidades que más nos interesen.

En nuestra API, se han creado los siguientes DTOs:

### Vivienda

- **CreateViviendaDto**
- **GetViviendaDto**
- **GetViviendaPropietarioDto**
- **ViviendaDtoConverter**


### Inmobiliaria

- **CreateGestorInmobiliariaDto**
- **CreateInmobiliariaDto**
- **GestorConverterDto**
- **GetInmobiliariaDto**
- **InmobiliariaDtoConverter**

### User

- **CreateUserDto**
- **CreateUserGestorDto**
- **GetUserDto**
- **GetUserPropietarioDto**
- **UserDtoConverter**

### Interesa

- **CreateInteresaDto**
- **GetInteresaDto**
- **InteresaDtoConverter**






## Seguridad

Se implementan roles de ADMIN,PROPIETARIO Y GESTOR

## JWT

La biblioteca implementa la verificación y firma de JWT utilizando los siguientes algoritmos:

JWS	    Algoritmo	    Descripción
HS256	HMAC256	    HMAC con SHA-256
HS384	HMAC384	    HMAC con SHA-384
HS512	HMAC512 	HMAC con SHA-512
RS256	RSA256	    RSASSA-PKCS1-v1_5 con SHA-256
RS384	RSA384	    RSASSA-PKCS1-v1_5 con SHA-384
RS512	RSA512	    RSASSA-PKCS1-v1_5 con SHA-512
ES256	ECDSA256	ECDSA con curva P-256 y SHA-256
ES256K	ECDSA256	ECDSA con curva secp256k1 y SHA-256
ES384	ECDSA384	ECDSA con curva P-384 y SHA-384
ES512	ECDSA512	ECDSA con curva P-521 y SHA-512


-Se elije el algoritmo
-Creamos y firmamos un token
-Verificamos token
-Validamos el tiempo de validez del token





