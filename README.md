# Nisum - Microservicio de Gestión de Usuarios

Este microservicio proporciona una API para gestionar la información de los usuarios. Está construido con Spring Boot y utiliza Maven para la gestión de dependencias.

## Instalación y Configuración

Para ejecutar este microservicio en un entorno local, tenga en cuenta los siguientes pasos:

1. Clonar este repositorio.
2. Abrir el proyecto en el IDE de preferencia (por ejemplo, IntelliJ IDEA o Eclipse).
3. Configurar las variables de entorno, como la expresión regular de la contraseña, el tiempo de expiración del JWT y la clave secreta JWT, en el archivo de propiedades de la aplicación (application.yml).
4. Ejecuta la aplicación Spring Boot.
5. La API estará disponible en `http://localhost:8080/api/v1`.

## Postman

A continuación, podra descargar la colección Postman del API.

## Uso de la API

## Registro de Usuarios

Este endpoint permite crear un nuevo usuario en el sistema proporcionando la información del mismo en formato JSON.
El servicio retornará los datos relacionados al usuario que se creó, dentro de estos datos se retornará un token JWT 
válido que puede ser usado para acceder a otros endpoints protegidos.

- **URL**: `/api/v1/users`
- **Método**: `POST`
- **Headers**:
    - `Content-Type: application/json`
    - **Autorización**: No se requiere autorización para crear un nuevo usuario.

### Ejemplo de Solicitud en Postman

![img.png](src/main/resources/documentation/images/createUser.png)

## Autenticación de Usuario

Este endpoint permite autenticar un usuario proporcionando su nombre de usuario (correo electrónico) y contraseña. 
Si la autenticación es exitosa, se retornará un token JWT válido que puede ser usado para acceder a otros endpoints protegidos.

- **URL**: `/api/v1/auth/login`
- **Método**: `POST`
- **Headers**:
  - `Content-Type: application/x-www-form-urlencoded`
  - **Autorización**: No se requiere autorización para autenticar un usuario.

### Ejemplo de Solicitud en Postman

![img.png](src/main/resources/documentation/images/login.png)

## Obtener Usuario por ID

Este endpoint permite obtener el perfil de un usuario específico proporcionando su ID único.

- **URL**: `/api/v1/users/{user-id}`
- **Método**: `GET`
- **Headers**:
  - **Autorización**: Se requiere un token JWT válido para acceder a este endpoint.

### Ejemplo de Solicitud en Postman

Reemplazar `{user-id}` con el ID real del usuario que requiere obtener.

![img.png](src/main/resources/documentation/images/getUserById.png)

## Obtener Lista de Usuarios

Este endpoint permite obtener una lista de todos los perfiles de usuario en el 
sistema. Puede hacer uso de la paginación para controlar la cantidad de resultados 
que se muestran en cada página.

- **URL**: `/api/v1/users`
- **Método**: `GET`
- **Headers**:
  - **Autorización**: Se requiere un token JWT válido para acceder a este endpoint.

### Ejemplo de Solicitud en Postman

![img.png](src/main/resources/documentation/images/getAllUsers.png)

## Actualizar Usuario por ID

Este endpoint permite actualizar el perfil de un usuario específico proporcionando
su ID único. Los datos que son posibles modificar son: el nombre, la contraseña y
los números de teléfono de contacto del usuario.

- **URL**: `/api/v1/users/{user-id}`
- **Método**: `PUT`
- **Headers**:
  - **Autorización**: Se requiere un token JWT válido para acceder a este endpoint.

### Ejemplo de Solicitud en Postman

Reemplazar `{user-id}` con el ID real del usuario que requiere actualizar. En el cuerpo de la solicitud, proporciona los datos a actualizar en formato JSON como se muestra en el ejemplo a continuación.

Tener en cuenta lo siguientes casos:

1.	Modificar teléfono existente: En caso de requerir modificar datos asociados a un teléfono de contacto especifico (existente) se debe proporcionar el id del mismo generado en la creación y actualizar los demás campos según aplique.
2.	Eliminar teléfono: En caso de requerir eliminar un teléfono de contacto solo es necesario remover completamente el registro de la lista.
3.	Agregar nuevo teléfono: En caso de requerir agregar un nuevo teléfono de contacto, es necesario agregar un nuevo registro a la lista estableciendo el campo “id” como null.

![img.png](src/main/resources/documentation/images/updateUser.png)

## Desactivar Usuario por ID

Este endpoint permite desactivar (realizar una eliminación lógica) un usuario específico proporcionando su ID único. La desactivación implica que el usuario ya no estará activo en el sistema, pero su perfil se mantendrá para futuras referencias.

- **URL**: `/api/v1/users/{user-id}`
- **Método**: `DELETE`
- **Headers**:
  - **Autorización**: Se requiere un token JWT válido para acceder a este endpoint.

### Ejemplo de Solicitud en Postman

Reemplazar `{user-id}` con el ID real del usuario que requiere desactivar.

![img.png](src/main/resources/documentation/images/deleteUserById.png)