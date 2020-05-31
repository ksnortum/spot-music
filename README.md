# spot-music
Get links and suggestions of music to listen to from the popular music source the starts with "Spot"

## Introduction
This is a simple command line application that demonstrates how to use the API of Spotify

## Requirements
* Java 11
* Maven 3.6

## Execution
After downloading or cloning the applications, `cd` into the directory with `pom.xml` in it.  This
should be just beneath the top directory.  Issue this Maven command:

    mvn exec:java
    
The application should startup.  You can also run this from your favorite IDE.

## Usage
### auth
The first thing you should do is issue the `auth` command.  This will display a link for you to copy and paste
(or maybe you have a "launch link" function from your terminal window).  It will wait 30 seconds for your
authorization from the web page (this should be improved).  If the application receives the authorization
correctly, the web page will be cleared and a message will display that the code was received.  You can now 
close the window and return to the application.

If the authorization is cancelled or takes longer than 30 seconds, the application will display "Program not 
responding" (change this).  You will have to wait the full 30 seconds before control is passed back to the
command processing. 

### next and prev
There is basic pagination for the rest of the commands, which are all lists.  `next` will display the next
five items.  This number can be changed at the command line with the `--pages <number>` flag (except that the 
Maven command doesn't accept flags that aren't Maven-releated.  Change this).

### new
This command will display all the new releases on Spotify.

### featured
This will display all the featured music.

### categories
This will list all the categories on Spotify.

### playlists <category>
This command will list all the playlists from a certain category.  This category must appear in the categoies
list.  Currently, you must list the categories before you use them, but this will change.

### exit
Exits the application.

## TODO
* add a GUI?
* a better way to wait for authorization
* create executable JAR
* create `.sh` and `.bat` files to launch the JAR file, when it's ready
* start auth command automatically at startup
* load categories on startup?  or when the first `playlist` command is issued?
* display a special caret when the app wants input.
* add a way to change the number of items per page at the command prompt