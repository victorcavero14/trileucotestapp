# Backend

## Estructura de directorios

El proyecto se divide en dos partes principales: `main` y `test`.

### `main`

Esta es la estructura principal del backend de nuestra aplicación y se divide en las siguientes carpetas:

- `controller`: Aquí se alojan los controladores de tu API REST, incluyendo el archivo `SwapiProxyController.java`, que se encarga de administrar las peticiones y respuestas.
- `service`: Esta carpeta contiene la lógica central del servicio, específicamente en el archivo `SwapiProxyService.java`. Aquí es donde se procesan las solicitudes y se generan las respuestas.
- `model`: Este directorio alberga las clases de modelos que se encargan de mapear las respuestas de la API de Swapi.
- `dto`: Aquí se encuentran las clases de modelos que se utilizan para estructurar la respuesta del endpoint `/swapi-proxy/person-info`.
- `error`: Se encarga de la gestión de errores y excepciones. Todo lo relacionado con la captura y manejo de excepciones se hace aquí.

### `test`

En esta sección, encontramos las pruebas del proyecto. Cada capa del backend tiene dos tipos de pruebas: una para cuando se encuentra el nombre del personaje y otra para cuando no se encuentra.

- `controller`: Contiene las pruebas que verifican la respuesta HTTP y la gestión correcta del controlador.
- `service`: Aloja las pruebas que se encargan de comprobar que los resultados devueltos por los servicios son los esperados.
