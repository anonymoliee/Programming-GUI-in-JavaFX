package quizzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class Quizzlerunmuyue extends Application {

	Stage stage;
	List<String> data;
	Boolean clicked = false;
	int questionAnswered = 0;
	int questionRight = 0;
	int questionTotal = 5;
	String questionType;
	String answerType;
	int count = 0;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		initUI(stage);
	}

	private void initUI(Stage stage) {
		this.stage = stage;
		stage.setTitle("Quizzle");
		stage.setScene(startScene());
		stage.show();
	}

	public Scene startScene() {
		Group root = new Group();
		questionAnswered = 0;
		questionRight = 0;
		data = new ArrayList<>();

		Label prompt = new Label("Enter a file to load:");
		prompt.setLayoutX(30);
		prompt.setLayoutY(30);
		TextField fileEnter = new TextField();
		fileEnter.setLayoutX(30);
		fileEnter.setLayoutY(50);
		Button enterBtn = new Button("Enter");
		enterBtn.setStyle("-fx-background-color: #ADD8E6; ");
		enterBtn.setLayoutX(30);
		enterBtn.setLayoutY(80);
		enterBtn.setOnAction(ae -> {
			Scanner sc = null;
			try {
				String fileName = fileEnter.getText();
				sc = new Scanner(new File(fileName));
			} catch (FileNotFoundException e) {
				System.out.println("Cannot find the file to open. Try again!");
			} finally {
				if (sc != null) {
					while (sc.hasNextLine()) {
						String[] read = sc.nextLine().split(",");
						data.add(read[0]);
						data.add(read[1]);
					}
					// store the type of questions and answers
					questionType = data.get(0);
					answerType = data.get(1);
					data.remove(0);
					data.remove(0);
					stage.setScene(quizScene());
					sc.close();
				}
			}
		});
		root.getChildren().addAll(prompt, fileEnter, enterBtn);
		Scene scene = new Scene(root, 230, 150);
		return scene;
	}

	public Scene quizScene() {
		BorderPane root = new BorderPane();
		Random r = new Random();
		clicked = false;

		// "index" is the index of the question in array (e.g. state name)
		int index = r.nextInt(data.size() / 2) * 2;
		String question = data.get(index);
		TilePane tp = new TilePane();
		tp.setVgap(10);
		tp.setPrefTileWidth(230);
		tp.setPrefColumns(1);
		Label ask = new Label("");
		tp.getChildren().add(ask);

		// 4 random and 1 right answers generated and stored here
		List<String> answerList = new ArrayList<>();
		answerList.add(data.get(index + 1));

		// store the indexes of the answers
		int[] indexArray = new int[5];
		int indexNum = 0;
		indexArray[0] = index + 1;

		while (answerList.size() < 5) {
			int ansIndex = r.nextInt(data.size() / 2) * 2 + 1;
			Boolean isInArray = false;
			for (int i : indexArray)
				if (ansIndex == i)
					isInArray = true;
			if (isInArray == false) {
				answerList.add(data.get(ansIndex));
				indexNum++;
				indexArray[indexNum] = ansIndex;
			}
		}

		// shuffle the answers
		Collections.shuffle(answerList);
		Label scoreKeeper = new Label();
		count++;
		ask.setText(Integer.toString(count) + ". The " + answerType + " of " + question + " is: ");

		for (String s : answerList) {
			Button answerBtn = new Button(s);
			answerBtn.setStyle("-fx-background-radius: 30");
			answerBtn.setStyle("-fx-background-insets: 0;");
			answerBtn.setMaxWidth(Double.MAX_VALUE);
			tp.getChildren().add(answerBtn);
			answerBtn.setOnAction(ae -> {
				if (clicked == false) {
					if (answerBtn.getText().equals(data.get(index + 1))) {
						answerBtn.setStyle("-fx-background-color: #00ff00");
						questionRight++;
					} else {
						answerBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
						Set<Node> allButtons = root.lookupAll(".button");
						for (Node node : allButtons) {
							Button btn = (Button) node;
							if (btn.getText().equals(data.get(index + 1)))
								node.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
						}
					}
					scoreKeeper.setText(
							"Your score: " + Integer.toString(questionRight) + "/" + Integer.toString(questionTotal));
					data.remove(index);
					data.remove(index);
					questionAnswered++;
					clicked = true;
				}
			});
		}

		// button for going to the next question
		Button nextBtn = new Button("next");
		nextBtn.setStyle("-fx-background-color: #ADD8E6");
		nextBtn.setMaxWidth(Double.MAX_VALUE);
		tp.getChildren().add(nextBtn);
		nextBtn.setOnAction(ae -> {
			if (clicked == true)
				if (questionAnswered < 5)
					stage.setScene(quizScene());
				else {
					stage.setScene(finalScene());
					count = 0;
				}
		});
		tp.getChildren().add(scoreKeeper);
		tp.setLayoutX(50);
		tp.setLayoutY(20);
		BorderPane.setAlignment(tp, Pos.CENTER);
		BorderPane.setMargin(tp, new Insets(20, 20, 20, 20));
		root.setCenter(tp);
		Scene scene = new Scene(root, 280, 330);
		return scene;
	}

	public Scene finalScene() {
		Group root = new Group();

		Label scoreKeeper = new Label();
		scoreKeeper.setLayoutX(50);
		scoreKeeper.setLayoutY(30);
		scoreKeeper.setText("Your score: " + Integer.toString(questionRight) + "/" + Integer.toString(questionTotal));

		Button restart = new Button("Start a new quiz");
		restart.setStyle("-fx-background-color: #ADD8E6");
		restart.setOnAction(ae -> {
			stage.setScene(startScene());
		});
		restart.setLayoutX(50);
		restart.setLayoutY(60);

		root.getChildren().addAll(scoreKeeper, restart);
		Scene scene = new Scene(root, 230, 150);
		return scene;
	}
}