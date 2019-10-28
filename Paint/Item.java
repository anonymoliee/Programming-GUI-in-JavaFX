package paint1;

import javafx.scene.shape.Shape;

public abstract class Item extends Shape implements Kaleidescope {

	public abstract boolean zatYou(double x, double y);

	@Override
	public abstract Item ghost(int which);

	public abstract void setX(double x);

	public abstract void setY(double y);

	public abstract Shape getContent();
}
