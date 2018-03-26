# biscada
Automatically exported from code.google.com/p/biscada

Colectar datos sueltos, conectarlos para convertirlos en información; predicados con sentido semántico que nos hablan del pasado, cruzar cada información con otras de igual tenor y obtener matrices de información, captar una de ellas y observar las gráficas, los nuevos datos, que nos dan conocimiento de la actualidad e impulsan un trabajo predictivo, mirando al futuro, en busca de aquel dato inexistente que debe ser colectado, cerrando el circulo y comenzando todo otra vez.

Así podrían resumirse las etapas de un software de inteligencia de negocio (BI) formal. Lo nuestro, bueno, lo hicimos nosotros.

## Requisitos del sistema
* Debe estar instalado el entorno de ejecución (JRE) de Java (https://www.java.com/es/download/manual.jsp). Este proyecto fue desarrollado con v1.8.
* Debe estar instalado un interprete de linea de comando Maven, para conectarse al repositorio de bibliotecas y descargar las requeridas (https://maven.apache.org/plugins/maven-clean-plugin/download.cgi). Este proyecto fue desarrollado con v3.5.3.
* Agregar al path de variables de entorno, la carpeta destino donde se instalo Maven. Por ej. entrada "maven.home", direccion "C:\apache-maven-x".
* Debe estar instalado MySQL, SGBD SQL relacional (https://dev.mysql.com/downloads/mysql/). Este proyecto fue desarrollado con v5.7.

## Paquetes necesarios
Visto que es un proyecto Java, la gestion de paquetes es mediante Maven (https://mvnrepository.com/).
Los requerimientos de este proyecto estan descritos en pom.xml. Una vez descargado ejecutar...
```
mvn clean
```
... para remover los archivos generados en build-time

Luego es necesario obtener las bibliotecas desde el repositorio local (o remoto)...
```
mvn package
```

## Configuracion inicial
En /cfg.properties debe especificarse:
* Los filtros de ruido para alarmas que se consideran falsos positivos.
* La url de conexion a Base de Datos con usuario/contraseña.
* La ubicacion de los archivos dbf candidatos a dar de alta en Base de Datos.

## Ejecutar el sistema
Clonar el proyecto https://github.com/poximan/biscada.git o descargarlo desde https://github.com/poximan/biscada/archive/master.zip.

Superadas las operaciones descritas en la seccion "Paquetes necesarios", puede ejecutarse la aplicacion escribiendo...
```
mvn exec:java
```

## Modos de operacion del sistema
* BI: Funcionalidad principal. Software de inteligencia de negocio, obtiene los datos almacenados en Base de Datos segun los criterios de filtrado impuestos por el usuario.
Luego los interpreta segun un punto de vista elegido por el usuario, y presenta los resultados en tabla+graficas.

* ETL: Funcionalidad de soporte. Software tipo batch para consumir archivos dbf estandar (Extract), interpretarlos y realizar las adaptaciones necesarias (Transform) y cargarlos en la Base de Datos (Load).
