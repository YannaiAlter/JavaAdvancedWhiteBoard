import javafx.scene.canvas.Canvas;

import java.rmi.Remote;
import java.util.*;
import java.rmi.RemoteException;
/*
   RoomInterface is the interface of RoomManager and order to pass those functions on RMI.
 */
public interface RoomInterface extends Remote {

	//This function adds a new room to the room list in RMI
	boolean addRoom(String roomName,String adminUserName) throws RemoteException;

	//Useful in lobby - returns a String list of all room names.
	ArrayList<String> getRoomsAsString() throws RemoteException;

	//This function gets roomName and text and appending it to the chat of the room.
	void setRoomConversation(String roomName,String text) throws RemoteException;

	//Returns true if chat is updated to the latest version
	boolean isChatUpdated(String roomName,Date chatLastUpdateTime) throws  RemoteException;

	//Returns true if roomlist is updated to the latest version
	boolean isRoomListUpdated(String roomName,Date roomListLastUpdateTime) throws  RemoteException;

	//Returns the chat of roomName to the client
	Chat getChatOfRoom(String roomName) throws RemoteException;

	//This function gets roomName and a Shape and adds it to the stack of all shapes in RMI.
	void addShapeToRoom(String roomName, Shape shape) throws  RemoteException;

	//This function is useful when the client has drawn something, then the clients update the date of the last update in the rmi side.
	void updateGraphicsTime(String roomName) throws  RemoteException;

	//This function is useful when the client has sent something, then the clients update the date of the last update in the rmi side.
	void updateChatTime(String roomName) throws  RemoteException;

	//This function is useful when the client has joined chat, then the clients update the date of the last update in the rmi side.
	void updateRoomListTime(String roomName) throws  RemoteException;

	boolean isAdmin(String userName,String roomName) throws RemoteException;

	//This function returns true if a client needs an update, called from the interval
	boolean isBoardUpdated(String roomName,Date clientLastUpdateTime) throws RemoteException;

	//Returns the time of the newest update, this is useful in case a client needs update, to mark that he did the update.
	Date getWhiteBoardUpdateTimeOfRoom(String roomName) throws RemoteException;

	//Returns an ArrayList contains all the shapes in a room
	ArrayList<Shape> getAllShapesOfRoom(String roomName) throws RemoteException;

	//Deletes last shape from shapes' stack
	void undoShapeOfRoom(String roomName) throws RemoteException;

	//Returns last shape deleted to shapes' stack
	void redoShapeOfRoom(String roomName) throws RemoteException;

	void deleteUserFromRoom(String roomName,String userName) throws RemoteException;
	//Returns last update of room name on server
	Date getRoomListUpdateTimeOfRoom(String roomName) throws RemoteException;

	//Adds a client to clients room list
	void addClientToRoom(String roomName,String userName) throws RemoteException;

	void deleteRoom(String roomName) throws RemoteException;

	//Returns a string list will all rooms' clients.
	ArrayList<String>getAllClientsOfRoom(String roomName) throws RemoteException;
}
