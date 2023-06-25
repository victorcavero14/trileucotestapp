- Mejor openapi que forefox ya que se sigue desarrollando

src/main/java: Contiene el código fuente principal de tu aplicación.
com.example.trileucotestapp: Paquete base para tus clases Java.
TrileucotestappApplication.java: Clase principal que inicia tu aplicación Spring Boot.
controller: Carpeta para los controladores de tu API REST, donde puedes colocar el SwapiProxyController.java.
model: Carpeta para las clases de modelos que mapean las respuestas de la API de Swapi.
dto: Carpeta para las clases de modelos utilizadas para la respuesta del endpoint /swapi-proxy/person-info.
src/main/resources: Contiene los recursos y archivos de configuración de tu aplicación.
application.properties o application.yml: Archivo de configuración de Spring Boot.
Otros recursos, como archivos de propiedades, archivos de configuración, plantillas, etc.
src/test/java: Contiene los archivos de prueba de tu aplicación.
com.example.trileucotestapp: Paquete base para las clases de prueba.
controller: Carpeta para las pruebas del controlador, donde puedes colocar SwapiProxyControllerTest.java.
Otros directorios: Puedes tener otros directorios y paquetes según tus necesidades, como servicios, repositorios, configuraciones adicionales, etc

# How to run backend
./gradlew build --stacktrace
./gradlew bootRun

docker compose build
docker compose up