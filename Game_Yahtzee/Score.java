package yahtzee;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Score extends HBox {

	protected Button nameBtn;
	protected Label scoreLabel;
	protected String name;
	protected int score;
	protected boolean chosen = false;

	public Score(String name) {
		super(30);
		this.name = name;
		nameBtn = new Button(name);
		score = 0;
		scoreLabel = new Label(Integer.toString(score));
		getChildren().addAll(nameBtn, scoreLabel);
	}

	public void updateScore(int sum) {
		if (!isChosen()) {
			score = sum;
			scoreLabel.setText(Integer.toString(score));
		}
	}

	public Button nameB() {
		return nameBtn;
	}

	public boolean isChosen() {
		return chosen;
	}

	public void scoreChosen() {
		chosen = true;
		nameBtn.setStyle("-fx-background-color: grey;");
	}

	public int getScore() {
		return score;
	}
}
