import java.io.Serializable;
import java.util.ArrayList;

public class Shape implements Serializable {
	enum Type {LINE,RECTANGLE,CIRCLE,LINE_SECOND_CLICK,TEXT,CONTINUOUS_LINE}
	Type type;
	Shape () { type = Type.LINE; }//Default is LINE (If nothing clicked).
	static void draw(ArrayList<Shape> shapes)//static are ignored in serialization
	{
		if(shapes.size() == 0) return;
		for(Shape shape : shapes)
		{
			shape.draw();
		}
	}
	public void draw() //Must be overriden
	{

	}

	Shape(Type t)
	{
		type = t;
	}
	boolean isLine() { return this.type == Type.LINE; }
}
