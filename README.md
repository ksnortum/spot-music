# spot-music
Get links and suggestions of music to listen to from the popular music source the starts with "Spot"

## Introduction
This is a simple command line application that demonstrates how to use the API of Spotify

## Requirements
* Java 11
* Maven 3.6
* Git 2.25

(It may run on earlier version of Maven and Git, but this is what I've tested.)

## Platforms
Tested on:
* Windows 1909
* Ubuntu 20.04
* Should work fine in MacOS, but it has not been tested

## Execution
Get to a command line: Terminal on Linux  (and Macs?) or Command Prompt or PowerShell on Windows.
After downloading or cloning the application, `cd` into the directory with `pom.xml` in it.  This
should be just beneath the top directory.  Issue this Maven command:

    mvn clean package

You only need to do this once.  Then after that, issue the command:

    mvn exec:java
    
The application should startup.  Alternatively, you can run the jar file directly in Java.  `cd` to the
target directory and issue this command (replace the `x` with the correct version number:

    java -jar spot-music-0.0.x-jar-with-dependencies.jar

You can also run the application from your favorite IDE.

## Usage
### auth
The first thing you should do is issue the `auth` command.  This will display a link for you to copy and paste
(or maybe you have a "launch link" function from your terminal window).  It will wait 30 seconds for your
authorization from the web page (this should be improved).  If the application receives the authorization
correctly, the web page will be cleared, and a message will display that the code was received.  You can now 
close the window and return to the application.

If the authorization is cancelled or takes longer than 60 seconds, the application will display "Could not 
authorize user".  You will have to wait the full 60 seconds before control is passed back to the
command processing. 

### next and prev
There is basic pagination for the rest of the commands, which are all lists.  `next` will display the next
five items.  This number can be changed at the command line with the `--items-per-pages <number>` flag. 
You can currently only use the flag when launching the application via the jar file. 

### new
This command will display all the new releases on Spotify.

### featured
This will display all the featured music.

### categories
This will list all the categories on Spotify.

### playlists <category>
This command will list all the playlists from a certain category.  This category must appear in the categories
list.  Currently, you must list the categories before you use them, but this will change.

### help
Displays a list of commands and what they do.

### quit or exit
Exits the application.

## TODO
* add a GUI? or run in a web browser
* create `.sh` and `.bat` files to launch the JAR file, when it's ready
* start auth command automatically at startup
* load categories on startup?  or when the first `playlist` command is issued?
* add a way to change the number of items per page at the command prompt
