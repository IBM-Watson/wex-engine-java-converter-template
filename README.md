Java Converter Template
=======================

## What is this project?

The goal of this project is to provide as simple of an example as possible to
create, and build a custom Java converter for Watson Explorer Engine.

It is my hope that a new user could do a few string replacements
in key files and have a working converter that they could then build out to
suit their needs.

## Getting Started

### Project Structure
  The project structure is as follows:
```
├── build.xml  -- Ant Build File.
├── lib  -- Copy your engine lib/java/plugin dir here.
│   └── readme.txt
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── com -- Use your own package structure. This one is provided as an example
    │   │       └── ibm
    │   │           └── dataexplorer
    │   │               └── converters
    │   │                   └── bootstrap
    │   │                       ├── BootstrapByteArrayConverterExample.java
    │   │                       └── BootstrapFileConverterExample.java
    │   └── nodes
    │       ├── function.vse-converter-bootstrap-converter.xml -- Defines a byte array converter node in the Admin Interface
    │       └── function.vse-converter-bootstrap-file-converter.xml -- Defines a file converter node in the Admin Interface
    ├── plugin
    │   ├── plugin.ant
            A template file used to generate plugin.xml.  Plugin.xml defines
            converter plugins that are registered with our plugin framework and
            called as defined by the aforementioned converter 'nodes'
    └── test
        ├── java
        │   └── com
        │       └── ibm
        │           └── dataexplorer
        │               └── converters
        │                   └── bootstrap  -- Tests, you should write tests too!
        │                       ├── AddLoggingCategoryToDefaultValue.java
        │                       ├── BootstrapByteArrayConverterExampleTest.java
        │                       └── BootstrapFileConverterExampleTest.java
        └── resources  -- Where you should put your test data.
            ├── data
            │   └── BootstrapFileConverterExampleTest
            │       ├── expectedoutput
            │       │   └── expected.txt
            │       └── input
            │           └── testInput.txt
            └── log4j.properties
```

### How to use this project

This project is provided as a functioning template. It is assumed that the developer
will use this project to create their own customized converter project. The following
covers the needed steps to customize and get started.

1.   Select which Converter Interface you will implement
   - There are `FileConverter` and `ByteArrayConverter` interfaces. You do not need to implement both.
        - `FileConverter` will read the data from a file on disk, perform conversions,
        and write the output back disk. This could be beneficial when dealing with large files helping to keep memory utilization down.

        - `ByteArrayConverter` is a converter that will be handed the entire
        data set as a byte array to process. It will return the converted data
        as a byte array and will keep everything in memory.

1. Select a name more appropriate than 'Bootstrap Converter' for your project. You will need to change references to 'bootstrap converter' in:
      - build.xml
      - plugin.ant

  You will also need to rename and make the appropriate changes to the converter node file that your using either:
     - function.vse-converter-bootstrap-file-converter.xml
     - function.vse-converter-bootstrap-converter.xml
       (Delete the one you are not using. bootstrap-converter is the Byte Array
         converter where bootstrap-file-converter is the file converter).

       Additionally, you will need to rename the following source package and its corresponding test package:
       - com.ibm.dataexplorer.converters.bootstrap

      And finally, you will need to rename which ever class you choose to keep along with
      any test files that relate to it.

       - BootstrapFileConverterExample.java

1. Search for any `TODO` statements in the code and remove them once you understand them. They are mostly reminders about renaming.

1. Start writing the first tests to test your code.

1. In your chosen converter file (your renamed BootstrapByteArrayConverterExample or BootstrapFileConverterExample) implement the conversion code inside the "convert" function.

1. Reminder, the final product will be running inside IBM's JVM. You can obtain the IBM JDK From [http://www.ibm.com/developerworks/java/jdk/] for Linux and Windows. As of 2015, IBM does not offer an OS X version of its JDK. Regardless, many IBMers who have used this template internally are using Macs and have not encountered unresolvable issues.



## How to build this project.

### ANT

ANT is a widely used build tool developed by Apache. We provide a build.xml
file which has targets defined to allow you to properly bulid and package this
example.  To properly build the project you will need to:

1. Copy the jar files from your WEX 10+ `lib/java` directory into the `lib` directory of your project.
*Alternately, you can update the build.xml file to reflect the location of your engine lib directory.*
1. Run 'ant package' from the root of the project directory. There are other ant targets
as well such as "test" and "clean" which you can use. Read the file

### To Deploy
The following steps will walk you through the proper steps to follow
in order to get the packaged dstributable that results from the bulid copied
out to and properly installed in your WEX instance.

If you have succesfull built your project a 'target' directory will have been
created and within it packaged zip files for distribution.  These are the
deployable artifacts for use with engine. To deploy them:

1. Copy target/<project-name-and-verson>-distrib.zip to the root of your Engine
   install dir
1. Unzip that archive
1. In the Engine Admin Tool, navigate to Management -> Installation ->
   Repository and click 'unpack' to add your java converters's xml node to the
   repository.
1. Add that node like any other converter to the collection of your choice.


# Licensing
All sample code contained within this project repository or any subdirectories is licensed according to the terms of the MIT license, which can be viewed in the [license file](/License.txt)

# Open Source @ IBM
[Find more open source projects on the IBM Github Page](http://ibm.github.io/)
