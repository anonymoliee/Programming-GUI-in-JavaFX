package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Pane {
	public List<Rectangle> recs;
	public int num;
	private Color[] colors = { Color.PINK, Color.YELLOW, Color.GREEN, Color.RED, Color.WHITE };
	private Color color;
	private Random rand;
	private double size;

	Block(int n, double size) {
		this.size = size;
		num = n;
		recs = new ArrayList<>();
		rand = new Random();
		color = colors[rand.nextInt(5)];
		for (int i = 0; i < n; i++) {
			Rectangle r = new Rectangle(size, size);
			r.setFill(color);
			recs.add(r);
			getChildren().add(r);
		}
		// System.out.println("rec created with num " + Integer.toString(num));
		setShape();
	}

	void setShape() {
		if (num == 1) {
			recs.get(0).setLayoutX(0);
			recs.get(0).setLayoutY(0);
		} else if (num == 2) {
			recs.get(0).setLayoutX(0);
			recs.get(0).setLayoutY(0);
			recs.get(1).setLayoutX(size);
			recs.get(1).setLayoutY(0);
		} else if (num == 3) {
			recs.get(0).setLayoutX(0);
			recs.get(0).setLayoutY(0);
			recs.get(1).setLayoutX(0);
			recs.get(1).setLayoutY(size);
			recs.get(2).setLayoutX(0);
			recs.get(2).setLayoutY(size * 2);
		} else if (num == 4)
			if (rand.nextInt(3) == 0) {
				recs.get(0).setLayoutX(0);
				recs.get(0).setLayoutY(0);
				recs.get(1).setLayoutX(0);
				recs.get(1).setLayoutY(size);
				recs.get(2).setLayoutX(size);
				recs.get(2).setLayoutY(0);
				recs.get(3).setLayoutX(size);
				recs.get(3).setLayoutY(size);
			} else if (rand.nextInt(2) == 1) {
				recs.get(0).setLayoutX(0);
				recs.get(0).setLayoutY(0);
				recs.get(1).setLayoutX(size);
				recs.get(1).setLayoutY(0);
				recs.get(2).setLayoutX(size * 2);
				recs.get(2).setLayoutY(0);
				recs.get(3).setLayoutX(size * 2);
				recs.get(3).setLayoutY(size);
			} else {
				recs.get(0).setLayoutX(size);
				recs.get(0).setLayoutY(0);
				recs.get(1).setLayoutX(0);
				recs.get(1).setLayoutY(size);
				recs.get(2).setLayoutX(size);
				recs.get(2).setLayoutY(size);
				recs.get(3).setLayoutX(size * 2);
				recs.get(3).setLayoutY(size);
			}
	}

	void rotate() {
		for (Rectangle r : recs) {
			double x = r.getLayoutX();
			double y = r.getLayoutY();
			r.setLayoutX(y);
			r.setLayoutY(x);
		}
	}
}
