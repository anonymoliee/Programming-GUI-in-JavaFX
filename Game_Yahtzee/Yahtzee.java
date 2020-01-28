package yahtzee;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Yahtzee extends Application {

	Group root;
	Dice[] dices = new Dice[5];
	int[] count = new int[6];
	List<Score> scores = new ArrayList<>();
	Label prompt;
	boolean pressed = false;
	int rollTime = 0;
	int totals = 0;
	int bonuss = 0;
	int totalu = 0;
	int totall = 0;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		initUI(stage);
	}

	protected void initUI(Stage stage) {
		root = new Group();
		Scene scene = new Scene(root, 600, 600);
		stage.setScene(scene);
		stage.show();
		stage.setTitle("Yahtzee");

		// set up the column for dices
		VBox diceRow = new VBox(20);
		for (int i = 0; i < 5; i++) {
			Dice d = new Dice(50);
			dices[i] = d;
			d.getKeep().setOnAction((ActionEvent e) -> {
				if (rollTime != 0)
					// System.out.println("dice clicked");
					d.colorChange();
			});
			diceRow.getChildren().add(d);
		}

		prompt = new Label("Start by rolling");
		prompt.setLayoutX(130);
		prompt.setLayoutY(370);
		Button rollBtn = new Button("Roll Dices");
		rollBtn.setOnAction((ActionEvent e) -> {
			if (rollTime <= 2) {
				for (int i = 0; i < 5; i++)
					dices[i].rollDice();
				compute();
				rollTime++;
				prompt.setText("You now have " + Integer.toString(3 - rollTime) + " chances to roll");
				pressed = false;
			}
		});
		diceRow.getChildren().add(rollBtn);
		diceRow.setLayoutX(20);
		diceRow.setLayoutY(20);
		root.getChildren().add(diceRow);
		root.getChildren().add(prompt);

		// set up the column for scores
		VBox upper = new VBox(10);
		Label upperTitle = new Label("Upper");
		upper.getChildren().add(upperTitle);
		Score ones = new Score("Ones");
		Score twos = new Score("Twos");
		Score threes = new Score("Threes");
		Score fours = new Score("Fours");
		Score fives = new Score("Fives");
		Score sixes = new Score("Sixes");
		scores.add(ones);
		scores.add(twos);
		scores.add(threes);
		scores.add(fours);
		scores.add(fives);
		scores.add(sixes);
		Label totalS = new Label("Total Score: 0");
		Label bonus = new Label("Bonus: 0");
		Label totalU = new Label("Upper Total: 0");
		for (int i = 0; i < 6; i++) {
			Score s = scores.get(i);
			s.nameB().setOnAction((ActionEvent e) -> {
				if (!pressed) {
					if (!s.isChosen())
						s.scoreChosen();
					totals += s.getScore();
					totalS.setText("Total Score: " + Integer.toString(totals));
					if (totals >= 63)
						bonuss += 35;
					bonus.setText("Bonus: " + Integer.toString(bonuss));
					totalu = bonuss + totals;
					totalU.setText("Upper Total: " + Integer.toString(totalu));
					rollTime = 0;
					for (int j = 0; j < 5; j++)
						dices[j].changeBlack();
					prompt.setText("Your total is now: " + Integer.toString(totalu + totall));
					pressed = true;
				}
			});
		}
		upper.getChildren().addAll(ones, twos, threes, fours, fives, sixes);
		upper.getChildren().addAll(totalS, bonus, totalU);
		upper.setLayoutX(200);
		upper.setLayoutY(20);
		root.getChildren().add(upper);

		VBox lower = new VBox(10);
		Label lowerTitle = new Label("Lower");
		lower.getChildren().add(lowerTitle);
		Score threeKind = new Score("Three of a Kind");
		Score fourKind = new Score("Four of a Kind");
		Score fullHouse = new Score("Full House");
		Score small = new Score("Small Straight");
		Score large = new Score("Large Straight");
		Score yahtzee = new Score("Yahtzee");
		Score chance = new Score("Chance");
		scores.add(threeKind);
		scores.add(fourKind);
		scores.add(fullHouse);
		scores.add(small);
		scores.add(large);
		scores.add(yahtzee);
		scores.add(chance);
		Label totalL = new Label("Lower Total: 0");
		for (int i = 6; i < 13; i++) {
			Score s = scores.get(i);
			s.nameB().setOnAction((ActionEvent e) -> {
				if (!pressed) {
					if (!s.isChosen())
						s.scoreChosen();
					totall += s.getScore();
					totalL.setText("Lower Score: " + Integer.toString(totall));
					rollTime = 0;
					for (int j = 0; j < 5; j++)
						dices[j].changeBlack();
					prompt.setText("Your total is now: " + Integer.toString(totalu + totall));
					pressed = true;
				}
			});
		}
		lower.getChildren().addAll(threeKind, fourKind, fullHouse, small, large, yahtzee, chance);
		lower.getChildren().add(totalL);
		lower.setLayoutX(400);
		lower.setLayoutY(20);
		root.getChildren().add(lower);
	}

	public void compute() {
		for (Score s : scores)
			s.updateScore(0);
		int sum = 0;
		int fullHouse = -1;
		for (int i = 0; i < 6; i++)
			count[i] = 0;
		for (Dice d : dices) {
			count[d.getNum() - 1]++;
			sum += d.getNum();
		}
		// update scores
		for (int i = 0; i < 6; i++) {
			scores.get(i).updateScore(count[i] * (i + 1));
			if (count[i] == 2)
				if (fullHouse == -1)
					fullHouse = 2;
				else if (fullHouse == 3)
					scores.get(8).updateScore(25);
			if (count[i] == 3) {
				scores.get(6).updateScore(sum);
				if (fullHouse == -1)
					fullHouse = 3;
				else if (fullHouse == 2)
					scores.get(8).updateScore(25);
			}
			if (count[i] == 4)
				scores.get(7).updateScore(sum);
			if (count[i] == 5)
				scores.get(11).updateScore(50);
		}
		// small & large straights
		if (count[0] >= 1 && count[1] >= 1 && count[2] >= 1 && count[3] >= 1)
			scores.get(9).updateScore(30);
		if (count[1] >= 1 && count[2] >= 1 && count[3] >= 1 && count[4] >= 1)
			scores.get(9).updateScore(30);
		if (count[2] >= 1 && count[3] >= 1 && count[4] >= 1 && count[5] >= 1)
			scores.get(9).updateScore(30);
		if (count[0] >= 1 && count[1] >= 1 && count[2] >= 1 && count[3] >= 1 && count[4] >= 1)
			scores.get(10).updateScore(40);
		if (count[1] >= 1 && count[2] >= 1 && count[3] >= 1 && count[4] >= 1 && count[5] >= 1)
			scores.get(10).updateScore(40);
		scores.get(12).updateScore(sum);
	}
}