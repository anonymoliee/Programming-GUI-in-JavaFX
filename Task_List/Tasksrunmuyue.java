package Tasks;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Tasksrunmuyue extends Application {
	Font ff = Font.font("Verdana", FontWeight.EXTRA_BOLD, 18);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		initUI(stage);
	}

	private void initUI(Stage stage) {
		Group root = new Group();
		root.getChildren().add(doATilePane());
		Scene scene = new Scene(root, 1200, 500);
		stage.setTitle("Task List");
		stage.setScene(scene);
		stage.show();
	}

	public TilePane doATilePane() {
		TilePane pane = new TilePane();
		pane.setHgap(5);
		pane.setVgap(5);
		pane.setPrefColumns(6);
		pane.getChildren().add(label("Task Name"));

		return pane;
	}

	public Label label(String words) {
		Label lab = new Label();
		lab.setText(words);
		lab.setFont(ff);
		return lab;
	}
}