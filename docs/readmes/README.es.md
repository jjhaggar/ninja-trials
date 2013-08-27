# Ninja Trials


Ninja Trials es un juego para Android al estilo de la vieja escuela, desarrollado específicamente para [Ouya](https://www.ouya.tv/ "Ouya console"), utilizando [AndEngine](https://github.com/nicolasgramlich/AndEngine " AndEngine por Nicolas Gramlich ").

El juego consta de varios minijuegos, todos ellos de manejo sencillo, como pulsar repetidamente un botón lo más rápido posible, o presionar un botón en un momento preciso.

Comenzamos a hacer este juego para poner a prueba las capacidades de AndEngine y para rendir homenaje a grandes juegos de antaño como [Track & Field](http://es.wikipedia.org/wiki/Track_%26_Field), [Combat School / Boot Camp ](http://es.wikipedia.org/wiki/Combat_School), y otros (no pretendemos infringir de ningún modo los derechos de autor , los títulos pertenecen a sus respectivos propietarios legales).

Pensamos que sería una buena idea hacer que fuera de código abierto, para que la gente pueda utilizarlo como ayuda para crear sus propios juegos. Y también sería estupendo que la gente pudiera ayudarnos a mejorar cualquier aspecto del juego ;)

---
**Este archivo "léeme" está disponible en más idiomas:**

[![GPL v3](flag_usa.png)](../../README.md)

---


## Contenido

* [Requisitos](#requisitos)
* [Descarga](#descarga)
* [Recopilación](#compilación)
* [Contribuir](#contribuir)
* [Bibliografía](#bibliografía)
* [Licencia](#licencia)
* [Contacto](#contacto)

## Requisitos
* [Kit de desarrollo de Java](http://www.oracle.com/technetwork/es/java/javase/downloads/index.html) (sólo JRE no es suficiente).
* [AndEngine](http://www.andengine.org/) ([rama GLES2-AnchorCenter](https://github.com/nicolasgramlich/AndEngine/tree/GLES2-AnchorCenter)).
* [Android SDK para Android 4.2.2 (API17)](http://developer.android.com/sdk/index.html). Se puede conseguir mediante la descarga del ADT Bundle (o sólo de las SDK Tools) y luego usando el Android SDK Manager.
* Dispositivo físico que use Android / dispositivo virtual, con Android 2.2.x o superior.

## Descarga

El código fuente está disponible en github, puedes clonar el árbol git haciendo:

    git clone https://github.com/jjhaggar/ninja-trials.git


## Compilación

Ninja Trials utiliza [javac](http://en.wikipedia.org/wiki/Javac) (incluido en [JDK](http://es.wikipedia.org/wiki/Java_Development_Kit)) como sistema de compilacion. El proceso de compilación completa depende del sistema operativo que estés utilizando (Linux, Windows o Mac OS X).

### Compilación en Linux (sin usar el IDE Eclipse):

Al instalar android-sdk, tendrás que añadir los directorios "tools" y "platform-tools" a tu PATH(sustituye $ANDROID\_HOME por la ruta en la que tengas el android-sdk):

    export PATH=$ANDROID\_HOME/tools:$ANDROID\_HOME/platform-tools:$PATH

Instalación de AndEngine:

    git clone https://github.com/nicolasgramlich/AndEngine.git
    cd AndEngine
    git checkout -t origin/GLES2-AnchorCenter
    android list targets (debes usar el ID que te devuelve para reemplazar target-id en el siguiente comando)
    android update project --target target-id --name project-name --path /path/to/project
    ant release

Configuración de adb (adb es una herramienta de línea de comandos que te permite comunicarte con un dispositivo Android conectado o con una instancia del emulador):

    sudo adb kill-server
    sudo adb start-server
    sudo adb devices (este comando devuelve una lista de los dispositivos/emuladores conectados)

Descarga del proyecto, compilación y carga en el dispositivo:

    git clone https://github.com/jjhaggar/ninja-trials.git
    android update project --target target-id --name ninjatrials --path /path/to/project --library /relative/path/AndEngine
    cd ninjatrials
    ant debug or ant realease
    ant debug install or ant release install (este comando instala el proyecto en el dispositivo)

### Compilation en Windows usando el ADT Bundle (Eclipse + ADT)

Daremos por hecho que usas Windows 7 y que ya has instalado en vuestro equipo: [Git](http://msysgit.github.io/), [Java JDK](http://www.oracle.com/technetwork/es/java/javase/downloads/index.html), [ADT Bundle](http://developer.android.com/sdk/index.html), SDK para Android 4.2.2 (API17), [AndEngine](https://github.com/nicolasgramlich/AndEngine/tree/GLES2-AnchorCenter) (rama GLES2-AnchorCenter) y [AndEngineSVGTextureRegionExtension](https://github.com/nicolasgramlich/AndEngineSVGTextureRegionExtension) (rama GLES2 branch), y que tienes un dispositivo Android conectado (vía USB) o una instancia abierta en el emulador, funcionando con Android 2.2.x (API 8, Froyo) o más alta. 

**1) Descarga del proyecto de GitHub a tu espacio de trabajo (workspace)**

Mientras presionas SHIFT, haz click sobre la carpeta de tu "workspace" de Eclipse y selecciona "Abrir ventana de comandos aquí".
Introduce la siguiente línea en la ventana de comandos: 

	git clone https://github.com/jjhaggar/ninja-trials.git

La ventana debería mostrar algo semejante a esto:

	C:\[...path_to_your_workspace...]\workspace>git clone https://github.com/jjhaggar/ninja-trials.git
	Cloning into 'ninjatrials'...
	remote: Counting objects: 600, done.
	remote: Compressing objects: 100% (286/286), done.
	Receiving objects:  98% (588/600), 32.55 MiB | 4.87 MiB/s
	Receiving objects: 100% (600/600), 34.09 MiB | 4.91 MiB/s, done.
	Resolving deltas: 100% (209/209), done.
	Checking out files: 100% (175/175), done.

Ahora ya deberías tener una copia del repositorio en tu espacio de trabajo.

**2) Importación del proyecto en Eclipse.**

Click derecho sobre el "Package Explorer", entonces *Import > Android > Existing Android code Into Workspace*
Ahora haz click en Browse, busca y selecciona tu carpeta "Ninja Trials" y haz click en OK.
Deselecciona la casilla "Copy projects into workspace" y haz click en Finish.

**3) Arreglo de la ruta de las bibliotecas**

Click derecho sobre el proyecto > Properties > Android.
Elimina los enlaces a las bibliotecas AndEngine y AndEngineSVGTextureRegionExtension, y vuelve a añadirlas seleccionándolas desde la lista desplegable.
Click en OK.

**4) Creando e instalando el .apk**

Click derecho sobre el proyecto > Run as > Run configuration.
Haz click en la pestaña "Target", selecciona "Always prompt to pick device" haz click en "Run".
Aparecerá una ventana, selecciona el dispositivo de la lista y pulsa OK.

Eso generará el .apk dentro del directorio "bin" del proyecto, y lo subirá e instalará en el dispositivo que elegiste.


## Contribuir

¡Puedes contribuir con Ninja Trials de muchas formas! :D ¡Agradeceremos cualquier ayuda! :D ¡Todos los colaboradores estarán en la sección de créditos del juego! :D ¡Os haréis famosos! (bueno, quizás XD).


### Contribuir en las traducciones

Todavía no hay nada que traducir, pero pronto escribiremos los archivos de idioma, así que por favor, ¡estad atentos! :)

#### Añade una traducción a tu lengua

* En primer lugar, haz una copia del archivo ***strings.xml***  situado en ***NinjaTrials/res/values/***, agregandole al final el sufijo de dos letras correspondiente a tu idioma. Utiliza el código de dos letras [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) (por ejemplo, si deseas hacer una traducción al ruso debes crear una copia llamada *strings_ru.xml*)
* En segundo lugar, traduce las palabras y frases del archivo.
A continuación mostramos algunos ejemplos para que sepas qué palabras debes traducir y cuáles no (no debes traducir los valores de *string name*).

Inglés (idioma predeterminado), **strings.xml**:

    <? Xml version = encoding "1.0" = "UTF-8">
    <Resources>
	    <string name="title"> My Application </ string>
	    <string name="hello_world"> Hello World </ string>
    </ Resources>

Español, **strings_es.xml**:

    <? Xml version = encoding "1.0" = "UTF-8">
    <Resources>
	    <String name="title"> Mi Aplicación </ string>
	    <string name="hello_world"> Hola Mundo </ string>
    </ Resources>

Francés, **strings_fr.xml**:

    <? Xml version = encoding "1.0" = "UTF-8">
    <Resources>
	    <string name="title"> lun Aplicación </ string>
	    <String name="hello_world"> Bonjour le monde! </ string>
    </ Resources>

* En tercer y último lugar, envíanos un [e-mail](mailto:madgeargames@gmail.com) con el archivo de traducción adjunta.

#### Corrige las traducciones actuales
* ¡Los "Nazis gramáticales" son bienvenidos! XD ¡De hecho, os necesitamos! :D
* Enviar un e-mail a [madgeargames@gmail.com](mailto:madgeargames@gmail.com) con el asunto "corrección traducción NinjaTrials (idioma)", y explicando ***en Español o en Inglés*** lo que crees que deberíamos corregir.

Ejemplo de e-mail que nos gustaría recibir:

> **Destinatario de correo electrónico (a)**: madgeargames@gmail.com
>
> **Asunto del correo electrónico**: NinjaTrials, corrección de la traducción (japonés)
>
> **Cuerpo del correo electrónico**:
> Hola, me llamo es Ryu, soy japonés y me gustaría ayudaros comprobando las traducciones al japonés.
>
> Parece que habéis confundido un kanji en la tercera línea de diálogo de la secuencia de ending de Sho.
>
> Línea original: 辛せ! -El primer kanji aquí significa picante o salado, pero en el contexto no tiene mucho sentido.
> 
Línea corregida: 幸せ! -Este kanji es similar al anterior en la forma, pero significa felicidad/fortuna/suerte/bendición, que creo que encaja perfectamente en el contexto.

> He descargado el archivo japonés y lo comprobé entero, y creo que lo demás está perfecto. ¡Buen trabajo! :D
>
> Que os vaya bien todo,
Ryu.


Ejemplo de un correo que no sería de gran ayuda:

> **Destinatario de correo electrónico (a)**: madgeargames@gmail.com
<br> **asunto del correo electrónico**: Mala traducción! : P
<br> **cuerpo del correo electrónico**:
<br> ¿Cómo habéis podido escribir mal esa palabra? Deberíais haber escrito コ al final y no ユ, ¡Baka! :P
 
Por favor, mandadnos correos como el primero :)

### Contribuciones en el código

Puedes ayudarnos a escribir y a mejorar nuestro código. Aquí están los pasos que debes seguir para colaborar puntualmente (si deseas ser un colaborador habitual, mándanos un e-mail).

#### Utilizando la interfaz de GitHub:

1. Haz un [Fork](http://help.github.com/forking/) del proyecto https://github.com/jjhaggar/ninja-trials/fork
2. Haz un commit limpio y bien comentado en tu repositorio. Puedes hacer una nueva rama si crees que es necesario.
3. Realiza una [pull request](http://help.github.com/pull-requests/) en la interfaz web de GitHub.

#### Enviando un parche por correo:

1. git clone https://github.com/jjhaggar/ninja-trials.git
2. Haz un parche limpio.
3. Envía el parche (bien comentado) a [madgeargames@gmail.com](mailto:madgeargames@gmail.com). Preferiríamos que el parche esté hecho con el comando *format-patch* en lugar de con *diff*, pero hacedlo como queráis, no somos tan quisquillosos ;)


## Bibliografía

    * http://incise.org/android-development-on-the-command-line.html
    * http://developer.android.com/tools/projects/projects-cmdline.html
    * http://stackoverflow.com/questions/2304863/how-to-write-a-good-readme


## Licencia

### Licencia del código

Derechos de autor 2013 Mad Gear Games.

Código autorizado en virtud de la Licencia de Apache, Versión 2.0 (la "Licencia"); se prohíbe utilizar este archivo excepto en cumplimiento de la Licencia. Podrá obtener una copia de la Licencia en:

[Http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

[![CC-BY-SA](http://www.apache.org/images/feather-small.gif)](http://www.apache.org/licenses/LICENSE-2.0)

A menos que lo exijan las leyes pertinentes o se haya establecido por escrito, el software distribuido en virtud de la Licencia se distribuye “TAL CUAL”, SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ya sean expresas o implícitas.
Véase la Licencia para consultar el texto específico relativo a los permisos y limitaciones establecidos en la Licencia.

---

### Licencia de los gráficos

Derechos de autor 2013 Mad Gear Games.

Gráficos autorizados bajo una licencia de Creative Commons Attribución-Compartir igual 3.0 no portada ("CC-BY-SA").

[http://creativecommons.org/licenses/by-sa/3.0/es/](http://creativecommons.org/licenses/by-sa/3.0/es/)

[![CC-BY-SA](http://i.creativecommons.org/l/by-sa/3.0/88x31.png)](http://creativecommons.org/licenses/by-sa/3.0/es/)

---

### Music license

Derechos de autor 2013 Estudio Evergreen - Daniel Pellicer García (Danpelgar) y Samuel Föger (Musamic) [http://estudioevergreen.wordpress.com/](http://estudioevergreen.wordpress.com/)

Música autorizada bajo una licencia de Creative Commons Attribución-Compartir igual 3.0 no portada ("CC-BY-SA").

[http://creativecommons.org/licenses/by-sa/3.0/es/](http://creativecommons.org/licenses/by-sa/3.0/es/)

[![CC-BY-SA](http://i.creativecommons.org/l/by-sa/3.0/88x31.png)](http://creativecommons.org/licenses/by-sa/3.0/es/)

## Contacto

* madgeargames@gmail.com
