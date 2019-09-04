import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;

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
