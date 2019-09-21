import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
/*
This class contains the Room properties. RoomManager instance which is common-instance for all clients handles a list of all Rooms,
every room is passed in the RMI and this is why the class is serialized.
 */
class Room implements Serializable
{
    String name;
    Chat chat;

    public Room(String roomName) {
        this.name=roomName;
        chat = new Chat("");

    }
    public Chat getChat() { return this.chat; }
    public String getName(){
        return this.name;
    }
    public static void main(String[]args) {

    }
}
