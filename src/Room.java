import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;

class Room extends UnicastRemoteObject implements RoomInterface
{
    String name;
    public Room(String roomName) throws java.rmi.RemoteException
    {
        this.name=roomName;
    }
    public String getName() throws java.rmi.RemoteException
    {
        return this.name;
    }
    public static void main(String[]args)
    {

    }
}
