// Runmu Yue
// USC ID: 8918706466

package deal;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Dealrunmuyue extends Application {

	Stage stage;
	Font ff = Font.font("Verdana", FontWeight.EXTRA_BOLD, 18);
	ArrayList<Integer> contents = new ArrayList<>(10);
	Random rand = new Random();
	GridPane pane = new GridPane();
	Boolean firstCase = false;
	String chosen;
	Label prompt = new Label();
	Label prompt2 = new Label();
	Button deal, noDeal;
	int offer = 0;
	int itemCount = 10;
	int colCount = 1;

	public static void main(String[] args) {
		launch(args);
	} // Application.launch()

	@Override
	public void start(Stage stage) {
		initUI(stage);
	}

	private void initUI(Stage stage) {
		arrayInit();
		this.stage = stage;
		GridPane root = new GridPane();
		root.setPadding(new Insets(10, 10, 10, 10));
		root.add(doAGridPane(), 0, 0);
		Scene scene = new Scene(root, 1200, 500);
		stage.setTitle("Deal or No Deal");
		stage.setScene(scene);
		stage.show();
	}

	public GridPane doAGridPane() {
		pane.add(label("Cases"), 0, 0);
		for (int i = 0; i < 10; i++)
			pane.add(boxBtn(Integer.toString(i + 1)), 0, i + 1);
		pane.add(label("Contents"), 1, 0);
		for (int i = 0; i < 10; i++)
			pane.add(label("$" + Integer.toString(contents.get(i))), 1, i + 1);
		pane.add(label("Offers"), 2, 0);
		prompt = label("Choose your own case");
		pane.add(prompt, 3, 3);
		deal = dealBtn("Deal");
		noDeal = dealBtn("No Deal");
		deal.setVisible(false);
		noDeal.setVisible(false);
		pane.add(deal, 3, 6);
		pane.add(noDeal, 3, 7);
		return pane;
	}

	public Scene setNewScene(Boolean reveal) {
		GridPane change = new GridPane();
		change.setPadding(new Insets(200, 200, 200, 200));
		int n = rand.nextInt(itemCount);
		String string = "$" + Integer.toString(contents.get(n));
		Label end = label("Your case contains " + string);
		change.add(end, 5, 5);
		Label result;
		if (reveal == false) {
			if (offer > contents.get(n)) {
				int difference = offer - contents.get(n);
				result = label("You win $" + Integer.toString(difference) + "\n" + "from the Dealer");
			} else {
				int difference = contents.get(n) - offer;
				result = label("You lose $" + Integer.toString(difference));
			}
		} else if (offer < contents.get(n)) {
			int difference = contents.get(n) - offer;
			result = label("You win $" + Integer.toString(difference) + "\n" + "from the Dealer");
		} else {
			int difference = offer - contents.get(n);
			result = label("You lose $" + Integer.toString(difference));
		}
		change.add(result, 5, 6);
		Scene scene = new Scene(change, 1200, 500);
		return scene;
	}

	public Button dealBtn(String message) {
		Button btn = new Button();
		btn.setText(message);
		btn.setFont(ff);
		btn.setPrefSize(200, 50);
		btn.setOnAction(ae -> {
			if (btn.getText().equals("Deal"))
				stage.setScene(setNewScene(false));
			else if (btn.getText().equals("No Deal")) {
				prompt.setText("You have chosen case " + chosen + "\n" + "Choose a case to reveal");
				deal.setVisible(false);
				noDeal.setVisible(false);
			} else if (btn.getText().equals("Reveal Your Case"))
				stage.setScene(setNewScene(true));
		});
		return btn;
	}

	public Button boxBtn(String count) {
		Button btn = new Button();
		btn.setText(count);
		btn.setFont(ff);
		btn.setPrefSize(200, 50);
		btn.setOnAction(ae -> {
			if (firstCase == false) {
				chosen = btn.getText();
				prompt.setText("You have chosen case " + chosen + "\n" + "Choose a case to reveal");
				btn.setStyle("-fx-background-color:Red");
				firstCase = true;
			} else if (contents.size() <= 2)
				return;
			else {
				if (btn.getText().equals(chosen))
					return;
				// Reveal the money chosen and set background to grey
				int n = rand.nextInt(itemCount);
				String string = "$" + Integer.toString(contents.get(n));
				Set<Node> allLabels = pane.lookupAll(".label");
				for (Node node : allLabels) {
					Label label = (Label) node;
					if (label.getText().equals(string))
						node.setStyle("-fx-background-color:GREY");
				}
				contents.remove(n);
				itemCount--;
				btn.setVisible(false);
				// Show what the Dealer now offers
				offer = 0;
				for (int i = 0; i < itemCount; i++)
					offer += contents.get(i);
				offer = offer / itemCount * 9 / 10;
				prompt.setText("The Dealer now offers" + "\n" + " $" + Integer.toString(offer));
				deal.setVisible(true);
				noDeal.setVisible(true);
				Label reveal = label("$" + Integer.toString(offer));
				pane.add(reveal, 2, colCount);
				colCount++;
				if (contents.size() <= 2) {
					prompt.setText("Only 2 cases left" + "\n" + "The Dealer now offers");
					prompt2 = label("$" + Integer.toString(offer) + "\n" + "Deal or Reveal your case");
					pane.add(prompt2, 3, 4);
					noDeal.setText("Reveal Your Case");
				}
			}
		});
		return btn;
	}

	public Label label(String amount) {
		Label lab = new Label();
		lab.setText(amount);
		lab.setFont(ff);
		lab.setPrefSize(300, 50);
		return lab;
	}

	public void arrayInit() {
		contents.add(1);
		contents.add(5);
		contents.add(10);
		contents.add(100);
		contents.add(1000);
		contents.add(5000);
		contents.add(10000);
		contents.add(100000);
		contents.add(500000);
		contents.add(1000000);
	}
}