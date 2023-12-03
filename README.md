# AirPreheating
This project serves as tool for research on identification and control of air preheating pipe.

## Installation
Please open the project in Intellij and add the following references to the project:
1. [JGAP library](https://sourceforge.net/projects/jgap/files/jgap/JGAP%203.6.3/jgap_3.6.3_jar.zip/download)
Download the zip file, unzip it in your libraries folder (or anywhere), and add jgap.jar as an external library to the project
2. [Fuzzy library](https://github.com/AttilaOrs/FuzzP/blob/master/fatJar/FuzzPVizual-all-v0.00005.jar)
Download the .jar file, and add it as an external library to the project

## Project contents
There are several packages:
1. utils - deals with all common operations
2. simulation - contains all the files necessary to simulate the system
3. GA - contains all GA-related code necessary to run the identification. This code is organised in sub-packages for each scenario.
