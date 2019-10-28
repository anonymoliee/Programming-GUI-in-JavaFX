package maxwell;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MaxwellBall extends Circle {

	double vx = 100; // pix per second
	double vy = -100;
	double x; // position of the ball
	double y;

	boolean left; // if the ball is on the left side of the scene
	boolean fast; // if the ball moves fast
	boolean through = false;

	Random r = new Random();

	public MaxwellBall(boolean leftSide, boolean fastSpeed) {
		super(5);
		// decide where the ball should start
		if (leftSide) {
			left = true;
			setCenterX(r.nextInt(240));
			setCenterY(r.nextInt(500));
		} else {
			left = false;
			setCenterX(r.nextInt(240) + 260);
			setCenterY(r.nextInt(500));
		}
		// decide the speed of the ball
		if (fastSpeed) {
			fast = true;
			setFill(Color.BLUE);
		} else {
			fast = false;
			setFill(Color.GREEN);
		}
	}

	public boolean getSide() {
		return left;
	}

	public boolean getSpeed() {
		return fast;
	}

	public void move(double deltat, boolean doorOpen, double doorY) {
		x = getCenterX();
		y = getCenterY();
		if (fast) {
			int speed = r.nextInt(2) + 2;
			x += vx * speed * deltat;
			y += vy * speed * deltat;
		} else {
			x += vx * deltat;
			y += vy * deltat;
		}
		setCenterX(x);
		setCenterY(y);
		boolean insideDoorX = getCenterX() >= 240 && getCenterX() <= 260;
		boolean insideDoorY = getCenterY() >= doorY && getCenterY() <= doorY + 80;
		if (doorOpen && insideDoorX && insideDoorY) {
			// System.out.println("pass through door");
			through = true;
			return;
		}
		if (through) {
			switchSide();
			through = false;
		}
		setBoundary();
		bounce();
	}

	public void switchSide() {
		if (left && getCenterX() > 240)
			left = false;
		if (!left && getCenterX() < 260)
			left = true;
	}

	public void bounce() {
		if (left) {
			if (x <= 0 || x >= 240)
				vx = -vx;
			// System.out.println("bounce left");
		} else if (!left)
			if (x <= 260 || x >= 500)
				vx = -vx;
		// System.out.println("bounce right");
		if (y <= 0 || y >= 500)
			vy = -vy;
		// System.out.println("bounce up/down");
	}

	public void setBoundary() {
		x = getCenterX();
		y = getCenterY();
		if (x <= 0)
			x = 0;
		if (x >= 500)
			x = 500;
		if (y <= 0)
			y = 0;
		if (y >= 500)
			y = 500;
		if (left) {
			if (x >= 240)
				x = 240;
		} else if (!left)
			if (x <= 260)
				x = 260;
		setCenterX(x);
		setCenterY(y);
	}

}
