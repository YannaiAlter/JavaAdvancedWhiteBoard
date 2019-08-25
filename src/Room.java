import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;

class Room
{
    String name;
<<<<<<< HEAD
    //Chat roomChat;
    //ArrayList<Shape> shapes;
    public Room(String roomName) throws java.rmi.RemoteException
    {
=======
    public Room(String roomName) throws java.rmi.RemoteException {
>>>>>>> d495a97f6a23bb13378432a8ff0331bf535b2605
        this.name=roomName;
    }
    public String getName() throws java.rmi.RemoteException {
        return this.name;
    }
    public static void main(String[]args) {

    }
}
