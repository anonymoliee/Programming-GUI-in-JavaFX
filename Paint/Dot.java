package paint1;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Dot extends Item {
	Circle circ;

	public Dot(double radius) {
		// super(w,h);
		circ = new Circle(radius);
	}

	@Override
	public boolean zatYou(double x, double y) {
		boolean hit = false;

		hit = true;
		hit = hit && x < circ.getCenterX() + circ.getRadius() && x > circ.getCenterX() - circ.getRadius();
		hit = hit && y < circ.getCenterY() + circ.getRadius() && y > circ.getCenterY() - circ.getRadius();
		return hit;
	}

	@Override
	public void setX(double x) {
		circ.setCenterX(x);
	}

	@Override
	public void setY(double y) {
		circ.setCenterY(y);
	}

	public void setRadius(double r) {
		circ.setRadius(r);
	}

	@Override
	public Item ghost(int which) {

		// 500 * 500

		Dot ghost = new Dot(circ.getRadius());
		switch (which) {
		case 1:
			// x-axis
			ghost.setY(500 - circ.getCenterY());
			ghost.setX(circ.getCenterX());
			break;
		case 2:
			// y-axis
			ghost.setX(500 - circ.getCenterX());
			ghost.setY(circ.getCenterY());
			break;
		case 3:
			// origin
			ghost.setX(500 - circ.getCenterX());
			ghost.setY(500 - circ.getCenterY());
			break;
		default:
			break;
		}

		return ghost;
	}

	@Override
	public Shape getContent() {
		return circ;
	}
}
