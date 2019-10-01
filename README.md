# JavaAdvancedWhiteBoard
This is a project i'm working on course: 20503 - https://www.openu.ac.il/courses/20503.htm

## Developers
  * Open the project with an IDE (Intellij recommended) and include all files at lib folder
  * JavaFX library requires some more steps in addition to the including, make sure you follow those steps here:
  https://openjfx.io/
## Configuration
 * You can configure the server host and port and some more properties by editing the file config.json under the config directory. make sure you are loading this file when running the server.
 * The same is for the client - you need to edit a config file with the information about the sql and rmi host, and then load this configuration on the login window.
 * using "default" in configuration path as shown in the last picture here will use the localhost as your sql,rmi server. for making this working, you need to install mysql server on your computer and make sure it's under port 3306. rmi will automatically create the server under the port 1099
 
## Some pictures: 

![alt text](https://github.com/YannaiAlter/JavaAdvancedWhiteBoard/blob/master/guides/images/login.png)
![alt text](https://github.com/YannaiAlter/JavaAdvancedWhiteBoard/blob/master/guides/images/lobby.png)
![alt text](https://github.com/YannaiAlter/JavaAdvancedWhiteBoard/blob/master/guides/images/chat_room.png)
![alt text](https://github.com/YannaiAlter/JavaAdvancedWhiteBoard/blob/master/guides/images/server_running.png)
