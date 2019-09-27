import java.io.Serializable;

public class Shape implements Serializable {
    enum Type {LINE,RECTANGLE,CIRCLE,LINE_SECOND_CLICK}
    Type type;
    Shape () { type = Type.LINE; }//Default is LINE (If nothing clicked).
    Shape(Type t)
    {
        type = t;
    }
    boolean isLine() { return this.type == Type.LINE; }
}
