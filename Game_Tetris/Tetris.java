package project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tetris extends Application {
	int score = 0;
	int level = 1;
	Stage stage;
	Group gameRoot;
	Scene gameScene;
	Random rand = new Random();
	Label levelLabel;
	Label levelKeeper;
	Label scoreLabel;
	Label scoreKeeper;
	List<Block> blocks = new ArrayList<>();
	List<String> scores = new ArrayList<>();
	List<String> names = new ArrayList<>();
	ArrayList<User> users = new ArrayList<>();
	boolean occupation[][];
	boolean fail = false;
	boolean nextBlock = true;
	static final Rectangle[] EMPTY_ARRAY = new Rectangle[5];
	Font ff;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		initUI(stage);
	}

	protected void initUI(Stage stage) {
		this.stage = stage;
		stage.setTitle("Tetris");
		stage.setScene(startScene());
		stage.show();
	}

	public Scene startScene() {
		Group root = new Group();
		Scene scene = new Scene(root, 300, 400);
		scene.setFill(Color.DARKBLUE);

		try {
			ff = Font.loadFont(new FileInputStream(new File("./arcade_ya/ARCADE_I.TTF")), 30);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Label title = new Label("Tetris");
		title.setLayoutX(60);
		title.setLayoutY(80);
		title.setFont(ff);
		title.setStyle("-fx-text-fill: white");

		try {
			ff = Font.loadFont(new FileInputStream(new File("./arcade_ya/ARCADE_I.TTF")), 15);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Button startBtn = new Button("Start New Game");
		startBtn.setOnAction(ae -> {
			stage.setScene(newGame());
		});
		startBtn.setLayoutX(30);
		startBtn.setLayoutY(180);
		startBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white");
		startBtn.setFont(ff);
		Button ruleBtn = new Button("Instruction");
		ruleBtn.setOnAction(ae -> {
			stage.setScene(ruleScene());
		});
		ruleBtn.setLayoutX(50);
		ruleBtn.setLayoutY(210);
		ruleBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white");
		ruleBtn.setFont(ff);
		Button boardBtn = new Button("Leader Board");
		boardBtn.setOnAction(ae -> {
			stage.setScene(leaderScene());
		});
		boardBtn.setLayoutX(40);
		boardBtn.setLayoutY(240);
		boardBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white");
		boardBtn.setFont(ff);
		boardBtn.setOnAction(ae -> {
			stage.setScene(leaderScene());
		});
		root.getChildren().addAll(title, startBtn, boardBtn, ruleBtn);

		return scene;
	}

	public Scene leaderScene() {
		GridPane leaderGP = new GridPane();
		leaderGP.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

		users.clear();
		scores.clear();
		names.clear();

		Scanner sc = null;
		try {
			sc = new Scanner(new File("leaderboard.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find the file to open. Try again!");
		} finally {
			if (sc != null) {
				String line;
				boolean num = true;
				while (sc.hasNextLine()) {
					line = sc.nextLine();
					if (num) {
						scores.add(line);
						num = false;
					} else {
						names.add(line);
						num = true;
					}
				}
				sc.close();
			}
		}
		for (int i = 0; i < scores.size(); i++)
			users.add(new User(Integer.parseInt(scores.get(i)), names.get(i)));
		Collections.sort(users, Collections.reverseOrder());

		TilePane order = new TilePane();
		order.setPadding(new Insets(30, 30, 30, 30));
		order.setHgap(5);
		order.setVgap(20);
		order.setPrefColumns(1);
		order.setTileAlignment(Pos.CENTER_LEFT);
		Label orderLabel = new Label("Top");
		orderLabel.setStyle("-fx-text-fill: white");
		orderLabel.setFont(ff);
		order.getChildren().add(orderLabel);
		int size;
		if (users.size() > 8)
			size = 8;
		else
			size = users.size();
		for (int i = 0; i < size; i++) {
			int number = i + 1;
			Label num = new Label(Integer.toString(number));
			num.setStyle("-fx-text-fill: white");
			num.setFont(ff);
			order.getChildren().add(num);
		}
		leaderGP.add(order, 0, 0);

		TilePane showName = new TilePane();
		showName.setPadding(new Insets(30, 30, 30, 30));
		showName.setHgap(5);
		showName.setVgap(20);
		showName.setPrefColumns(1);
		showName.setTileAlignment(Pos.CENTER_LEFT);
		Label nameLabel = new Label("Name");
		nameLabel.setStyle("-fx-text-fill: white");
		nameLabel.setFont(ff);
		showName.getChildren().add(nameLabel);
		for (int i = 0; i < size; i++) {
			Label name = new Label(users.get(i).getName());
			name.setStyle("-fx-text-fill: white");
			name.setFont(ff);
			showName.getChildren().add(name);
		}
		leaderGP.add(showName, 1, 0);

		TilePane showScore = new TilePane();
		showScore.setPadding(new Insets(30, 30, 30, 30));
		showScore.setHgap(5);
		showScore.setVgap(20);
		showScore.setPrefColumns(1);
		showScore.setTileAlignment(Pos.CENTER_LEFT);
		Label scoreLabel = new Label("Score");
		scoreLabel.setStyle("-fx-text-fill: white");
		scoreLabel.setFont(ff);
		showScore.getChildren().add(scoreLabel);
		for (int i = 0; i < size; i++) {
			Label score = new Label(Integer.toString(users.get(i).getScore()));
			score.setStyle("-fx-text-fill: white");
			score.setFont(ff);
			showScore.getChildren().add(score);
		}
		leaderGP.add(showScore, 2, 0);

		Button backBtn = new Button("back");
		backBtn.setOnAction(ae -> {
			stage.setScene(startScene());
		});
		backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white");
		backBtn.setFont(ff);
		leaderGP.add(backBtn, 0, 1);

		Scene scene = new Scene(leaderGP, 450, 400);
		scene.setFill(Color.DARKBLUE);
		return scene;
	}

	public Scene newGame() {
		gameRoot = new Group();
		gameScene = new Scene(gameRoot, 400, 300);

		GridPane gp = new GridPane();
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 15; j++) {
				Region r = new Region();
				r.setStyle(
						"-fx-background-color: darkblue; -fx-border-style: solid; -fx-border-width: 0.5; -fx-border-color: blue; -fx-min-width: 20; -fx-min-height:20; -fx-max-width:20; -fx-max-height: 20;");
				gp.add(r, i, j);
			}
		gp.setLayoutX(0);
		gp.setLayoutY(0);
		gameRoot.getChildren().add(gp);

		occupation = new boolean[16][11];
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 11; j++)
				occupation[i][j] = false;

		try {
			ff = Font.loadFont(new FileInputStream(new File("./arcade_ya/ARCADE_I.TTF")), 12);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		levelLabel = new Label("Level: ");
		levelKeeper = new Label(Integer.toString(level));
		levelLabel.setLayoutX(220);
		levelLabel.setLayoutY(50);
		levelKeeper.setLayoutX(220);
		levelKeeper.setLayoutY(70);
		levelLabel.setFont(ff);
		levelKeeper.setFont(ff);
		levelLabel.setStyle("-fx-text-fill: darkblue");
		levelKeeper.setStyle("-fx-text-fill: darkblue");

		scoreLabel = new Label("Your Score: ");
		scoreKeeper = new Label(Integer.toString(score));
		scoreLabel.setLayoutX(220);
		scoreLabel.setLayoutY(90);
		scoreKeeper.setLayoutX(220);
		scoreKeeper.setLayoutY(110);
		scoreLabel.setFont(ff);
		scoreKeeper.setFont(ff);
		scoreLabel.setStyle("-fx-text-fill: darkblue");
		scoreKeeper.setStyle("-fx-text-fill: darkblue");

		gameRoot.getChildren().addAll(levelLabel, levelKeeper, scoreLabel, scoreKeeper);
		nextBlock = true;
		Driver dr = new Driver();
		dr.start();

		return gameScene;
	}

	public Scene ruleScene() {
		Group ruleRoot = new Group();
		Scene ruleScene = new Scene(ruleRoot, 300, 200);
		ruleScene.setFill(Color.DARKBLUE);

		Text rule = new Text();
		rule.setText("←→: move the dropping block\nspace: rotate the dropping block\n↑: pause\n↓: resume️️");
		rule.setLayoutX(20);
		rule.setLayoutY(50);
		rule.setFill(Color.WHITE);

		Button backBtn = new Button("back");
		backBtn.setOnAction(ae -> {
			stage.setScene(startScene());
		});
		backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white");
		backBtn.setFont(ff);
		backBtn.setLayoutX(20);
		backBtn.setLayoutY(150);

		ruleRoot.getChildren().addAll(rule, backBtn);

		return ruleScene;
	}

	public boolean isOccupied(int x, int y, Block block) {
		if (x < 0 || y < 0 || x >= 15 || y >= 10)
			return true;
		for (Block b : blocks) {
			if (b == block)
				continue;
			if (occupation[x][y])
				return true;
		}
		return false;
	}

	public class Driver extends AnimationTimer {
		long lasttime;
		boolean firstTime = true;

		@Override
		public void handle(long now) {
			if (firstTime) {
				lasttime = now;
				firstTime = false;
			} else if (fail) {
				fail = false;
				stop();
				finalStage();
			} else if (nextBlock) {
				for (int i = 0; i < 11; i++)
					if (occupation[0][i]) {
						fail = true;
						continue;
					}
				nextBlock = false;
				int num = rand.nextInt(4) + 1;
				Block b = new Block(num, 19.9);
				blocks.add(b);
				b.setLayoutX(80);
				b.setLayoutY(0);
				gameRoot.getChildren().add(b);
			} else {
				Block b = blocks.get(blocks.size() - 1);
				double time = (now - lasttime) * 1.0e-9 + (level - 1) * 0.03;
				if (time >= 0.18) {
					b.setLayoutY(b.getLayoutY() + 20);
					lasttime = now;
				}
				for (Rectangle r : b.recs) {
					int col = (int) (r.getLayoutX() / 19.9 + b.getLayoutX() / 20);
					int row = (int) (r.getLayoutY() / 19.9 + b.getLayoutY() / 20);
					if (isOccupied(row + 1, col, b) || row + 1 >= 15) {
						nextBlock = true;
						for (Rectangle rec : b.recs) {
							col = (int) (rec.getLayoutX() / 19.9 + b.getLayoutX() / 20);
							row = (int) (rec.getLayoutY() / 19.9 + b.getLayoutY() / 20);
							occupation[row][col] = true;
						}
						erase();
						break;
					}
				}
				gameScene.setOnKeyPressed(ke -> {
					boolean move = true;
					KeyCode keyCode = ke.getCode();
					if (keyCode == KeyCode.LEFT) {
						for (Rectangle r : b.recs) {
							int col = (int) (r.getLayoutX() / 19.9 + b.getLayoutX() / 20);
							int row = (int) (r.getLayoutY() / 19.9 + b.getLayoutY() / 20);
							if (isOccupied(row, col - 1, b) || col - 1 < 0)
								move = false;
						}
						if (move)
							b.setLayoutX(b.getLayoutX() - 20);
					}
					if (keyCode == KeyCode.RIGHT) {
						for (Rectangle r : b.recs) {
							int col = (int) (r.getLayoutX() / 19.9 + b.getLayoutX() / 20);
							int row = (int) (r.getLayoutY() / 19.9 + b.getLayoutY() / 20);
							if (isOccupied(row, col + 1, b) || col + 1 >= 10)
								move = false;
						}
						if (move)
							b.setLayoutX(b.getLayoutX() + 20);
					}
					if (keyCode == KeyCode.SPACE) {
						b.rotate();
						for (Rectangle r : b.recs) {
							int col = (int) (r.getLayoutX() / 19.9 + b.getLayoutX() / 20);
							int row = (int) (r.getLayoutY() / 19.9 + b.getLayoutY() / 20);
							if (isOccupied(row, col, b))
								b.rotate();
						}
					}
					if (keyCode == KeyCode.UP)
						stop();
					if (keyCode == KeyCode.DOWN)
						start();
				});

			}
		}

	}

	public void finalStage() {
		Stage endStage = new Stage();
		Group endGroup = new Group();
		Scene endScene = new Scene(endGroup, 230, 150);
		endScene.setFill(Color.DARKBLUE);
		endStage.setScene(endScene);

		Label prompt = new Label("Enter a username:");
		prompt.setLayoutX(20);
		prompt.setLayoutY(30);
		prompt.setStyle("-fx-text-fill: white");
		prompt.setFont(ff);
		TextField tx = new TextField();
		tx.setLayoutX(20);
		tx.setLayoutY(60);
		Button enterBtn = new Button("Enter");
		enterBtn.setStyle("-fx-background-color: white; ");
		enterBtn.setLayoutX(20);
		enterBtn.setLayoutY(90);
		enterBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white");
		enterBtn.setFont(ff);
		enterBtn.setOnAction(ae -> {
			File f = new File("leaderboard.txt");
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
				bw.append(scoreKeeper.getText());
				bw.append(System.getProperty("line.separator"));
				bw.append(tx.getText());
				bw.append(System.getProperty("line.separator"));
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			score = 0;
			level = 1;
			blocks.clear();
			stage.setScene(startScene());
			endStage.close();
		});

		endGroup.getChildren().addAll(prompt, tx, enterBtn);
		endStage.show();
	}

	public void erase() {
		int count = 0;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 11; j++) {
				if (occupation[i][j])
					count++;
				if (count >= 10) {
					score++;
					scoreKeeper.setText(Integer.toString(score));
					if (score % 3 == 0) {
						level++;
						levelKeeper.setText(Integer.toString(level));
					}
					for (int m = 0; m < blocks.size(); m++) {
						Block b = blocks.get(m);
						for (int n = b.num - 1; n >= 0; n--) {
							int row = (int) (b.recs.get(n).getLayoutY() / 19.9 + b.getLayoutY() / 20);
							if (row == i) {
								b.getChildren().remove(b.recs.get(n));
								b.recs.remove(n);
								b.num--;
							}
						}
					}
					for (int m = 0; m < blocks.size(); m++) {
						Block b = blocks.get(m);
						if (b.num <= 0) {
							blocks.remove(b);
							gameRoot.getChildren().remove(b);
						}
					}
					for (int m = 0; m < blocks.size(); m++) {
						Block b = blocks.get(m);
						if (b.getLayoutY() / 20 <= i)
							b.setLayoutY(b.getLayoutY() + 20);
						// System.out.println("entire drop!");
						for (int n = b.num - 1; n >= 0; n--) {
							int row = (int) (b.recs.get(n).getLayoutY() / 19.9 + b.getLayoutY() / 20);
							if (row + 1 > 15)
								b.recs.get(n).setLayoutY(b.recs.get(n).getLayoutY() - 19.9);
						}
					}
					count = 0;
					continue;
				}
				if (j >= 10)
					count = 0;
			}
			occupationCheck();
		}
	}

	void occupationCheck() {
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 11; j++)
				occupation[i][j] = false;
		for (int i = 0; i < blocks.size(); i++) {
			Block b = blocks.get(i);
			for (int j = 0; j < b.num; j++) {
				int col = (int) (b.recs.get(j).getLayoutX() / 19.9 + b.getLayoutX() / 20);
				int row = (int) (b.recs.get(j).getLayoutY() / 19.9 + b.getLayoutY() / 20);
				occupation[row][col] = true;
			}
		}
	}
}
