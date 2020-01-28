package yahtzee;

import java.util.Random;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Dice extends Pane {

	private Button keep = new Button();
	private Rectangle dice;
	private Circle[] circs;
	private Color color = Color.BLACK;
	private boolean shouldRoll = true;
	private int num, size;

	public Dice(int size) {
		this.size = size;
		keep.setPrefHeight(size);
		keep.setPrefWidth(size);
		keep.setStyle("-fx-background-color: transparent;");
		dice = new Rectangle(size, size);
		getChildren().addAll(dice, keep);
		rollDice();
	}

	public Button getKeep() {
		return keep;
	}

	public int getNum() {
		return num;
	}

	public void rollDice() {
		if (shouldRoll) {
			circs = new Circle[6];
			Random r = new Random();
			num = r.nextInt(6) + 1;
			getChildren().clear();
			getChildren().addAll(dice, keep);
			for (int i = 0; i < num; i++) {
				Circle c = new Circle(size / 12);
				c.setFill(Color.WHITE);
				circs[i] = c;
				getChildren().add(c);
			}
			drawDice();
		}
	}

	public void drawDice() {
		// System.out.println("num: " + Integer.toString(num));
		if (num == 1) {
			circs[0].setCenterX(size / 2);
			circs[0].setCenterY(size / 2);
		} else if (num == 2) {
			circs[0].setCenterX(size / 4);
			circs[0].setCenterY(size / 4 * 3);
			circs[1].setCenterX(size / 4 * 3);
			circs[1].setCenterY(size / 4);
		} else if (num == 3) {
			circs[0].setCenterX(size / 4);
			circs[0].setCenterY(size / 4 * 3);
			circs[1].setCenterX(size / 4 * 3);
			circs[1].setCenterY(size / 4);
			circs[2].setCenterX(size / 2);
			circs[2].setCenterY(size / 2);
		} else if (num == 4) {
			circs[0].setCenterX(size / 4);
			circs[0].setCenterY(size / 4 * 3);
			circs[1].setCenterX(size / 4 * 3);
			circs[1].setCenterY(size / 4);
			circs[2].setCenterX(size / 4);
			circs[2].setCenterY(size / 4);
			circs[3].setCenterX(size / 4 * 3);
			circs[3].setCenterY(size / 4 * 3);
		} else if (num == 5) {
			circs[0].setCenterX(size / 4);
			circs[0].setCenterY(size / 4 * 3);
			circs[1].setCenterX(size / 4 * 3);
			circs[1].setCenterY(size / 4);
			circs[2].setCenterX(size / 4);
			circs[2].setCenterY(size / 4);
			circs[3].setCenterX(size / 4 * 3);
			circs[3].setCenterY(size / 4 * 3);
			circs[4].setCenterX(size / 2);
			circs[4].setCenterY(size / 2);
		} else if (num == 6) {
			circs[0].setCenterX(size / 4);
			circs[0].setCenterY(size / 4 * 3);
			circs[1].setCenterX(size / 4 * 3);
			circs[1].setCenterY(size / 4);
			circs[2].setCenterX(size / 4);
			circs[2].setCenterY(size / 4);
			circs[3].setCenterX(size / 4 * 3);
			circs[3].setCenterY(size / 4 * 3);
			circs[4].setCenterX(size / 4);
			circs[4].setCenterY(size / 2);
			circs[5].setCenterX(size / 4 * 3);
			circs[5].setCenterY(size / 2);
		}
	}

	public void colorChange() {
		if (color == Color.BLACK) {
			color = Color.DARKRED;
			dice.setFill(Color.DARKRED);
			shouldRoll = false;
		} else {
			color = Color.BLACK;
			dice.setFill(Color.BLACK);
			shouldRoll = true;
		}
	}

	public void changeBlack() {
		color = Color.BLACK;
		dice.setFill(Color.BLACK);
		shouldRoll = true;
	}
}
