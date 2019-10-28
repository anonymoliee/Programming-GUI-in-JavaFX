package paint1;

import java.util.LinkedList;
import java.util.List;

//ButtonDetail.java
//2018 Barrett Koster
//demo to show how a button works

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Paint extends Application {
	Pane root; // the whole window
	FlowPane buttonPane; // just for buttons
	Pane drawPane; // all the stuff in the window attaches here
	Point[] corners = new Point[2]; // for drawing rectangles
	LinkedList<Item> shapes;
	Box b;
	Dot d;
	Triangle t;

	enum modeType {
		PICKER, RECTANGLES, DOTS, TRIANGLES, KALEIDESCOPE
	}

	modeType mode = modeType.PICKER;
	Item picked = null;

	boolean mouseIsPressed = false;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		initUI(stage);
	}

	private void initUI(Stage stage) {
		root = new Pane();
		Scene scene = new Scene(root, 500, 500);
		stage.setTitle("Paint1");
		stage.setScene(scene);
		stage.show();

		drawPane = new Pane();
		root.getChildren().add(drawPane);
		drawPane.setPrefSize(450, 450);
		buttonPane = new FlowPane();
		root.getChildren().add(buttonPane);

		shapes = new LinkedList<>();

		Button modeRectangle = new Button("Rectangles");
		buttonPane.getChildren().add(modeRectangle);
		modeRectangle.setOnAction((ActionEvent e) -> {
			mode = modeType.RECTANGLES;
			System.out.println("Rectangle button clicked");
		});

		Button modeDots = new Button("Dots");
		buttonPane.getChildren().add(modeDots);
		modeDots.setOnAction((ActionEvent e) -> {
			mode = modeType.DOTS;
			System.out.println("Dots button clicked");
		});

		Button modeTris = new Button("Triangles");
		buttonPane.getChildren().add(modeTris);
		modeTris.setOnAction((ActionEvent e) -> {
			mode = modeType.TRIANGLES;
			System.out.println("Triangles button clicked");
		});

		Button modePicker = new Button("Picker");
		buttonPane.getChildren().add(modePicker);
		modePicker.setOnAction((ActionEvent e) -> {
			mode = modeType.PICKER;
			System.out.println("picker button clicked");
		});

		Button modeK = new Button("Kaleidescope");
		buttonPane.getChildren().add(modeK);
		modeK.setOnAction((ActionEvent e) -> {
			mode = modeType.KALEIDESCOPE;
			System.out.println("Kaleidescope button clicked");
			List<Item> ghosts = new LinkedList<>();
			for (Item b : shapes)
				for (int i = 0; i < 4; i++) {
					Item ghost = b.ghost(i);
					drawPane.getChildren().add(ghost.getContent());
					ghosts.add(ghost);
				}
			shapes.addAll(ghosts);
		});

		drawPane.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent m) -> {
			System.out.println("pressed at " + m.getX() + " " + m.getY());
			if (mode == modeType.RECTANGLES) {
				corners[0] = new Point(m);
				b = new Box(10, 10);
				b.setX(corners[0].getX());
				b.setY(corners[0].getY());
				drawPane.getChildren().add(b.getContent());
				shapes.add(b);
			} else if (mode == modeType.DOTS) {
				corners[0] = new Point(m);
				d = new Dot(10);
				d.setX(corners[0].getX());
				d.setY(corners[0].getY());
				drawPane.getChildren().add(d.getContent());
				shapes.add(d);
			} else if (mode == modeType.TRIANGLES) {
				corners[0] = new Point(m);
				t = new Triangle(10);
				t.setX(corners[0].getX());
				t.setY(corners[0].getY());
				drawPane.getChildren().add(t.getContent());
				shapes.add(t);
			} else if (mode == modeType.PICKER) {
				picked = null;
				for (Item b : shapes)
					if (b.zatYou(m.getX(), m.getY()))
						picked = b;
			}
			mouseIsPressed = true;

		});
		drawPane.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent m) -> {
			mouseIsPressed = false;
			picked = null;
		});

		drawPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent m) -> {
			if (mode == modeType.RECTANGLES) {
				corners[1] = new Point(m);
				double xdif = corners[0].xdif(corners[1]);
				double ydif = corners[0].ydif(corners[1]);
				b.setWidth(xdif);
				b.setHeight(ydif);
			} else if (mode == modeType.DOTS) {
				corners[1] = new Point(m);
				double xdif = corners[0].xdif(corners[1]);
				// double ydif = corners[0].ydif(corners[1]);
				d.setRadius(xdif);
			} else if (mode == modeType.TRIANGLES) {
				corners[1] = new Point(m);
				double xdif = corners[0].xdif(corners[1]);
				double ydif = corners[0].ydif(corners[1]);
				t.setXY(xdif, ydif);
			} else if (mode == modeType.PICKER)
				if (picked != null) {
					picked.setX(m.getX());
					picked.setY(m.getY());
				}
		});
	}
}
