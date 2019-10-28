package paint1;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class Triangle extends Item {

	Polygon p;
	double[] points = new double[6];
	double r, x, y;

	public Triangle(double radius) {
		r = radius;
		p = new Polygon(points);
	}

	@Override
	public boolean zatYou(double x, double y) {
		boolean hit = false;

		hit = true;
		hit = hit && x > this.x - r && x < this.x + r;
		hit = hit && y > this.x + this.y - x && y < this.y + r;
		return hit;
	}

	@Override
	public void setX(double x) {

		this.x = x;
		p.getPoints().set(0, x - r);
		p.getPoints().set(2, x + r);
		p.getPoints().set(4, x + r);

	}

	@Override
	public void setY(double y) {

		this.y = y;
		p.getPoints().set(1, y + r);
		p.getPoints().set(3, y - r);
		p.getPoints().set(5, y + r);
	}

	public void setXY(double x, double y) {
		r = x;
		setX(this.x);
		r = y;
		setY(this.y);
	}

	@Override
	public Item ghost(int which) {

		// 500 * 500

		Triangle ghost = new Triangle(r);
		switch (which) {
		case 1:
			// x-axis
			ghost.setY(500 - y);
			ghost.setX(x);
			break;
		case 2:
			// y-axis
			ghost.setX(500 - x);
			ghost.setY(y);
			break;
		case 3:
			// origin
			ghost.setX(500 - x);
			ghost.setY(500 - y);
			break;
		default:
			break;
		}

		System.out.println(which + ": (" + x + "," + y + ")->(" + (500 - x) + "," + (500 - y) + ")");

		return ghost;
	}

	@Override
	public Shape getContent() {
		return p;
	}
}
