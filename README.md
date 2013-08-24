# Ninja Trials

Ninja Trials is an old school style Android Game, developed specifically for [OUYA](https://www.ouya.tv/ "Ouya console"), using [AndEngine](https://github.com/nicolasgramlich/AndEngine "AndEngine by Nicolas Gramlich").

The game features several minigames, they all have simple gameplay, like quick repeating button presses or accurate timing button presses.

We started making this game to test the capabilities of AndEngine and to pay homage to great old games like [Track & Field](http://en.wikipedia.org/wiki/Track_&_Field_(video_game)), [Combat School / Boot Camp](http://en.wikipedia.org/wiki/Combat_School), and others (no copyright infringement intended, all titles belong to their respective legal owners).

We thought it would be a good idea to make it Open Source, so people could use it as a help to create their own games. And it also would be great that people could help us to improve any aspect of the game ;)

---
**This readme is also available in other languages:**

[![GPL v3](/docs/readmes/flag_spain.png)](/docs/readmes/README.es.md)

---

## Contents

* [Requirements](#requirements)
* [Download](#download)
* [Compilation](#compilation)
* [Contribute](#contribute)
* [Bibliography](#bibliography)
* [License](#license)
* [Contact](#contact)

## Requirements
* [Java Development Kit](http://www.oracle.com/technetwork/es/java/javase/downloads/index.html)  (JRE alone is not sufficient).
* [AndEngine](http://www.andengine.org/) ([branch GLES2-AnchorCenter](https://github.com/nicolasgramlich/AndEngine/tree/GLES2-AnchorCenter)).
* [Android SDK for Android 4.2.2 (API17)](http://developer.android.com/sdk/index.html). You can get it by downloading the ADT Bundle (or just the SDK Tools) and then using the Android SDK Manager.
* Android powered device / Android virtual device with Android 2.2.x or higher.

## Download

The source code is available from github, you can clone the git tree by doing:

    git clone https://github.com/jjhaggar/ninja-trials.git


## Compilation

Ninja Trials uses [javac](http://en.wikipedia.org/wiki/Javac) (included in [JDK](http://en.wikipedia.org/wiki/Java_Development_Kit)) as build system. The complete compilation process depends on the OS you are using (Linux, Windows or Mac OS X).

### Compilation in Linux (without using Eclipse IDE):

When you install android-sdk, you will need add "tools" and "platform-tools" directories to the PATH of the user (replace $ANDROID\_HOME with the path of your android-sdk):

    export PATH=$ANDROID\_HOME/tools:$ANDROID\_HOME/platform-tools:$PATH

Install AndEngine:

    git clone https://github.com/nicolasgramlich/AndEngine.git
    cd AndEngine
    git checkout -t origin/GLES2-AnchorCenter
    android list targets (you must use the ID returned to replace target-id in the next command)
    android update project --target target-id --name project-name --path /path/to/project
    ant release

Configure adb (adb is a command line tool that lets you communicate with a connected Android-powered device or emulator instance):

    sudo adb kill-server
    sudo adb start-server
    sudo adb devices (this command returns a list of attached devices/emulators)

Download project, compile and upload to device:

    git clone https://github.com/jjhaggar/ninja-trials.git
    android update project --target target-id --name ninjatrials --path /path/to/project --library /relative/path/AndEngine
    cd ninjatrials
    ant debug or ant realease
    ant debug install or ant release install (this command installs the project to device)


### Compilation in Windows using ADT Bundle (Eclipse + ADT)

We will assume you are using Windows 7 and you already have installed in your system: [Git](http://msysgit.github.io/), [Java JDK](http://www.oracle.com/technetwork/es/java/javase/downloads/index.html), [ADT Bundle](http://developer.android.com/sdk/index.html), SDK for Android 4.2.2 (API17), [AndEngine](https://github.com/nicolasgramlich/AndEngine/tree/GLES2-AnchorCenter) (GLES2-AnchorCenter branch) and [AndEngineSVGTextureRegionExtension](https://github.com/nicolasgramlich/AndEngineSVGTextureRegionExtension) (GLES2 branch), and have a connected Android-powered device (via USB) or an emulator instance, running with Android 2.2.x (API 8, Froyo) or higher. 

**1) Downloading the project from GitHub into your workspace**

While pressing SHIFT, click on the folder of your Eclipse workspace and select "Open Command Window Here".
Introduce the next command in the command line: 

	git clone https://github.com/jjhaggar/ninja-trials.git

The window should show something similar to this:

	C:\[...path_to_your_workspace...]\workspace>git clone https://github.com/jjhaggar/ninja-trials.git
	Cloning into 'ninjatrials'...
	remote: Counting objects: 600, done.
	remote: Compressing objects: 100% (286/286), done.
	Receiving objects:  98% (588/600), 32.55 MiB | 4.87 MiB/s
	Receiving objects: 100% (600/600), 34.09 MiB | 4.91 MiB/s, done.
	Resolving deltas: 100% (209/209), done.
	Checking out files: 100% (175/175), done.

Now you should have a copy of the repository in your workspace.

**2) Importing the project into Eclipse.**

Right click on Package Explorer, then *Import > Android > Existing Android code Into Workspace*.
Now click on Browse, find and select your "Ninja Trials" folder and click OK.
Uncheck the box "Copy projects into workspace" and click on Finish.

**3) Fixing path for libraries**

Right click on the project > Properties > Android.
Remove the links for libraries AndEngine and AndEngineSVGTextureRegionExtension, then add them again selecting them from the drop-down list.
Click OK.

**4) Building and installing the .apk**

Right click on the project > Run as > Run configuration.
Click on Target tab and select "Always prompt to pick device" and click on Run.
A window will appear, select the device from the list and click OK.

That will generate the .apk into the bin folder of the project, and will upload & install it to the device you chose.


## Contribute

You can contribute with Ninja Trials in several ways!! :D We'll appreciate any help!! :D All contributors will be in the credits section of the game!! :D You will be famous!! (well, maybe XD).


### Translation contributions

There is still nothing to translate now, but soon we'll write the language files, so please stay tuned! :) 

#### Add a translation to your language

* First, make a copy of the file ***strings.xml*** placed in ***NinjaTrials/res/values/***, adding to the end the two letter suffix pertaining to your language. Please use the [ISO 639-1](http://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) two letter codes (for example, if you want to make a Russian translation you should create a copy called *strings_ru.xml*)
* Second, translate the words and sentences in the file.
The following are some different examples of which words you should translate and which ones you shouldn't (you mustn't translate the *string name* values).

English (default locale), **strings.xml**:

	<?xml version="1.0" encoding="utf-8"?>
	<resources>
	    <string name="title">My Application</string>
	    <string name="hello_world">Hello World!</string>
	</resources>

Spanish, **strings_es.xml**:

    <?xml version="1.0" encoding="utf-8"?>
    <resources>
    	<string name="title">Mi Aplicación</string>
    	<string name="hello_world">Hola Mundo!</string>
    </resources>

French, **strings_fr.xml**:

    <?xml version="1.0" encoding="utf-8"?>
    <resources>
    	<string name="title">Mon Application</string>
    	<string name="hello_world">Bonjour le monde !</string>
    </resources>

* Third and last, send us an [e-mail](mailto:madgeargames@gmail.com) with the attached translation file.

#### Correct existing translations
* Grammar nazis are welcome! XD In fact we need them! :D
* Send an e-mail to [madgeargames@gmail.com](mailto:madgeargames@gmail.com) with the subject "NinjaTrials translation correction (language)", and explaining ***in English or Spanish*** what you think that we should correct. 

Example of e-mail that we'd be delighted to receive:


> **e-mail recipient (to)**: madgeargames@gmail.com
>  
> **e-mail subject**: NinjaTrials translation correction (japanese)
> 
> **e-mail body**:
> Hi,my name is Ryu, I am Japanese and would like to help you by checking the japanese translations.
> 
>It seems that you confused a kanji in the third line of dialogue from Shô's ending.
>
>Original line: 辛せ! -The first kanji here means hot (spicy) or salty, but in the context it doesn't have much sense. 
>
Corrected line: 幸せ! -This kanji is similar in shape, but it means happiness / good fortune / luck / blessing, which I think fits perfectly in the context.

>I downloaded the japanese file and checked over it, and I think the rest is perfect. Great job! :D
>
>Best wishes,
Ryu.


Example of what wouldn't be of great help:

>**e-mail recipient (to)**: madgeargames@gmail.com
<br>**e-mail subject**: Bad translation! :P
<br>**e-mail body**:
<br>How could you misspell that word? You should have written コ at the end and no ユ, Baka!! :P
 
Please, stick to the first kind of e-mail :)

### Code contributions

You can help us to write / improve our code. Here you are the steps you should follow for punctual collaborations (if you'd like to be a regular collaborator, e-mail us).

1.  [Fork](http://help.github.com/forking/) the project https://github.com/jjhaggar/ninja-trials/fork
2.  Make one well commented and clean commit to the repository. You can make a new branch if you if you feel it necessary.
3.  Perform a [pull request](http://help.github.com/pull-requests/) in github's web interface.



## Bibliography

    * http://incise.org/android-development-on-the-command-line.html
    * http://developer.android.com/tools/projects/projects-cmdline.html
    * http://stackoverflow.com/questions/2304863/how-to-write-a-good-readme

## License

Copyright 2013 Mad Gear Games.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at 

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

## Contact

* madgeargames@gmail.com
