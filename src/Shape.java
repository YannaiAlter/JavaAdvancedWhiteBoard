import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class Shape implements Serializable {
	enum Type {LINE,RECTANGLE,CIRCLE,LINE_SECOND_CLICK,TEXT,CONTINUOUS_LINE}
	Type type;
	java.awt.Color color;
	Shape (Color color,Type type) {
		this.type = type;
		this.color = fxToAwt(color);
	}//Default is LINE (If nothing clicked).
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

	public static java.awt.Color fxToAwt(javafx.scene.paint.Color color){
		return new java.awt.Color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getOpacity());
	}

	public static javafx.scene.paint.Color awtToFx(java.awt.Color color){
		return new javafx.scene.paint.Color(color.getRed()/255.0, color.getGreen()/255.0, color.getBlue()/255.0, color.getAlpha()/255.0);
	}

	Shape(Type t)
	{
		type = t;
	}
	boolean isLine() { return this.type == Type.LINE; }
}
