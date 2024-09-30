Parcial #2 Felipe Mendoza y Diego Avila

1. a) Escriba explícitamente en eI readme del aplicativo como hacer correr eI aplicativo de manera local usando una base de datos mysql (con docker) con las conexiones especificadas en el archivo application.yml

  Como primer paso, se levantar una instancia de MySQL usando docker para que la aplicación se conecte a ella.
   Para ello, se ejecuta el siguiente comando en el bash:

   docker run --name yms-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=yms -e MYSQL_USER=yms_user -e MYSQL_PASSWORD=yms_clave -p 3306:3306 -d mysql:8

La composición del comando es la siguiente:

- Iniciará un contenedor de MySQL 8.
- Creará una base de datos llamada yms.
- Creará un usuario llamado yms_user con la contraseña yms_clave.
- Expondrá el puerto 3306 para que la aplicación pueda conectarse a MySQL localmente.

![image](https://github.com/user-attachments/assets/6c9c0c1c-6b2d-4ae7-bf37-5acc04e2667c)

Posterior a eso, se realiza la verificación con el comando "docker ps"

![image](https://github.com/user-attachments/assets/7ddff8e5-9127-4fdc-bd69-dfe840a736e5)

Teniendo en cuenta que el archivo "application.properties" esta configurado para conectarse a un servidor MySQL en local host en el puerto 3306 con sus credenciales especificadas.
Para ejecutar la aplicación se utiliza el comando "./gradlew bootRun", esto utilizara Spring e iniciar la app en el puerto del archivo de configuracion, el puerto 8080, desde el cual podemos acceder desde el naavegador por medio del localhost.

![image](https://github.com/user-attachments/assets/ea27ee47-bc91-4f63-b03d-912edad5987e)

Y finalmente podemos realizar la verficación usando el comando :

  "docker exec -it yms-mysql mysql -u yms_user -p2-arquitectura>" e ingresando la constraseña yms_clave.

  ![image](https://github.com/user-attachments/assets/8889b6c9-23c9-41df-bcbe-94a00d879e72)

Y usando el MySQL

  ![image](https://github.com/user-attachments/assets/1ed00e56-e985-4997-b302-54627c9b4c1d)

5. Especifique en el archivo README.MD el comando para crear la imagen de docker del proyecto actual y ejecutar dicha imagen

Para crear la imagen, se debe utilizar el comando:

"docker build -t dockerparcial ."

El docker build que se encargará de crear la imagen y -t mas el nombre que asignara el tag.

y para ejecutar la imagen con el siguiente comando: 

"docker run -p 8080:8080 dockerparcial"

Docker run que ejecuta el contenedor a partir de una imagen, -p mapea el puerto 8080 para acceder a la app.

## JACOCO:
<img width="1208" alt="Screenshot 2024-09-30 at 9 14 56 AM" src="https://github.com/user-attachments/assets/2b3ad12e-efa8-4803-9dd1-ae23f4996229">

