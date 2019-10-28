package paint1;

import javafx.scene.input.MouseEvent;

public class Point {
	private double x, y; // in screen coords

	/**
	 * Constructor for objects of class Point
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point(MouseEvent me) {
		x = me.getX();
		y = me.getY();
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double xdif(Point p) {
		return Math.abs(p.x - x);
	}

	public double ydif(Point p) {
		return Math.abs(p.y - y);
	}

	@Override
	public String toString() {
		return "" + x + ", " + y;
	}
}
