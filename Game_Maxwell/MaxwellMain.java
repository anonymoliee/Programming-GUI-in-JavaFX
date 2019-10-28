package maxwell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MaxwellMain extends Application {
	Stage stage;
	// Group root;
	Font ff = Font.font("Verdana", FontWeight.EXTRA_BOLD, 18);
	Random r = new Random();
	Rectangle door;
	int level = 1;
	int defaultBall = 6;
	int currentBall = defaultBall;
	boolean animePause = false;
	boolean doorOpen = false;
	boolean atRightSide;
	List<MaxwellBall> ballList = new ArrayList<>();
	List<Boolean> boolList = new ArrayList<>();
	Button addBall, pause, resume, restart, continueGame;
	Label popWindow;
	Rectangle background;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		initUI(stage);
	}

	private void initUI(Stage stage) {
		this.stage = stage;
		// Group root = new Group();
		Scene scene = gameScene(defaultBall);
		stage.setScene(scene);
		stage.setTitle("Maxwell's Demon");
		stage.show();
	}

	public Scene gameScene(int numBall) {
		boolList.clear();
		ballList.clear();
		Group root = new Group();
		Scene scene = new Scene(root, 500, 500);

		// draw the wall
		Rectangle wall = new Rectangle(10, 500);
		wall.setX(245);
		wall.setY(0);
		wall.setFill(Color.BLACK);
		root.getChildren().add(wall);

		// draw the door
		door = new Rectangle(10, 80);
		door.setX(245);
		door.setY(0);
		door.setFill(Color.RED);
		root.getChildren().add(door);

		// move the door
		scene.setOnMouseMoved((MouseEvent m) -> {
			if (!animePause)
				door.setY(m.getY() - 20);
		});

		// open and close the door
		scene.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent m) -> {
			if (!animePause) {
				door.setY(m.getY() - 20);
				door.setFill(Color.WHITE);
				doorOpen = true;
			}
		});
		scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent m) -> {
			if (!animePause) {
				door.setY(m.getY() - 20);
				door.setFill(Color.WHITE);
				doorOpen = true;
			}
		});
		scene.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent m) -> {
			if (!animePause) {
				door.setY(m.getY() - 20);
				door.setFill(Color.RED);
				doorOpen = false;
			}
		});

		// set up indicating labels
		Label leftLabel = new Label("Fast(Blue)");
		leftLabel.setLayoutX(70);
		leftLabel.setLayoutY(30);
		root.getChildren().add(leftLabel);
		Label rightLabel = new Label("Slow(Green)");
		rightLabel.setLayoutX(320);
		rightLabel.setLayoutY(30);
		root.getChildren().add(rightLabel);
		Label levelLabel = new Label("Level       " + Integer.toString(level));
		levelLabel.setLayoutX(180);
		levelLabel.setLayoutY(10);
		levelLabel.setFont(ff);
		root.getChildren().add(levelLabel);

		// draw balls
		boolList.add(true);
		boolList.add(false);
		for (int i = 0; i < numBall; i++) {
			int side = r.nextInt(2);
			int speed = r.nextInt(2);
			MaxwellBall mb = new MaxwellBall(boolList.get(side), boolList.get(speed));
			ballList.add(mb);
			root.getChildren().add(mb);
		}

		// button to add balls
		addBall = new Button("Add Another Ball");
		root.getChildren().add(addBall);
		addBall.setOnAction(ae -> {
			currentBall += 1;
			int side = r.nextInt(2);
			int speed = r.nextInt(2);
			MaxwellBall mb = new MaxwellBall(boolList.get(side), boolList.get(speed));
			ballList.add(mb);
			root.getChildren().add(mb);
		});

		// animation
		Driver d = new Driver();
		d.start();

		// button to pause
		background = new Rectangle(200, 100);
		background.setLayoutX(150);
		background.setLayoutY(200);
		background.setFill(Color.LIGHTGREY);
		pause = new Button("Pause");
		pause.setLayoutX(120);
		resume = new Button("Resume");
		resume.setLayoutX(220);
		resume.setLayoutY(230);
		resume.setVisible(false);
		root.getChildren().addAll(background, pause, resume);
		pause.setOnAction(ae -> {
			background.setVisible(true);
			animePause = true;
			d.stop();
			resume.setVisible(true);
		});
		resume.setOnAction(ae -> {
			background.setVisible(false);
			animePause = false;
			d.start();
			resume.setVisible(false);
		});

		// end of game indicator
		popWindow = new Label("You Win!");
		popWindow.setLayoutX(230);
		popWindow.setLayoutY(220);
		restart = new Button("Restart");
		restart.setLayoutX(180);
		restart.setLayoutY(250);
		restart.setOnAction(ae -> {
			d.stop();
			level = 1;
			currentBall = defaultBall;
			stage.setScene(gameScene(defaultBall));
		});
		continueGame = new Button("Continue");
		continueGame.setLayoutX(260);
		continueGame.setLayoutY(250);
		continueGame.setOnAction(ae -> {
			d.stop();
			level++;
			currentBall += 2;
			stage.setScene(gameScene(currentBall));
		});
		root.getChildren().addAll(popWindow, restart, continueGame);

		return scene;
	}

	public class Driver extends AnimationTimer {
		long lasttime;
		boolean firstTime = true;

		@Override
		public void handle(long now) {
			if (firstTime) {
				lasttime = now;
				firstTime = false;
			} else {
				double deltat = (now - lasttime) * 1.0e-9;
				lasttime = now;
				// judge the winning condition
				atRightSide = true;
				background.setVisible(false);
				popWindow.setVisible(false);
				restart.setVisible(false);
				continueGame.setVisible(false);
				for (MaxwellBall mb : ballList) {
					mb.move(deltat, doorOpen, door.getY());
					if (mb.getSide() == true && mb.getSpeed() == false)
						atRightSide = false;
					if (mb.getSide() == false && mb.getSpeed() == true)
						atRightSide = false;
				}
				if (atRightSide) {
					background.setVisible(true);
					popWindow.setVisible(true);
					restart.setVisible(true);
					continueGame.setVisible(true);
				}
			}
		}
	}
}
