package paint1;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Box extends Item // extends Rectangle
{
	Rectangle r;

	public Box(int w, int h) {
		// super(w,h);
		r = new Rectangle(w, h);
	}

	@Override
	public boolean zatYou(double x, double y) {
		boolean hit = false;

		hit = true;
		hit = hit && r.getX() < x;
		hit = hit && r.getY() < y;
		hit = hit && x < r.getX() + r.getWidth();
		hit = hit && y < r.getY() + r.getHeight();

		return hit;
	}

	@Override
	public void setX(double x) {
		r.setX(x);
	}

	@Override
	public void setY(double y) {
		r.setY(y);
	}

	public void setHeight(double h) {
		r.setHeight(h);
	}

	public void setWidth(double w) {
		r.setWidth(w);
	}

	@Override
	public Item ghost(int which) {

		// 500 * 500

		Box ghost = new Box((int) r.getWidth(), (int) r.getHeight());
		switch (which) {
		case 1:
			// x-axis
			ghost.setY(500 - r.getY());
			ghost.setX(r.getX());
			break;
		case 2:
			// y-axis
			ghost.setX(500 - r.getX());
			ghost.setY(r.getY());
			break;
		case 3:
			// origin
			ghost.setX(500 - r.getX());
			ghost.setY(500 - r.getY());
			break;
		default:
			break;
		}

		return ghost;
	}

	@Override
	public Shape getContent() {
		return r;
	}
}
