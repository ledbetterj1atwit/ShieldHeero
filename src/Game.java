import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Home of main, most GUI logic, and all the game logic.
 * @author ledbetterj1
 *
 */
public class Game extends Application {
	// TODO: GUI and game logic (oof).
	static long frameCount = 0; // How many frames have passed(and what frame your on)(level independent)
	static long currentTime = 1;
	
	public static void main(String[] args) {
		System.out.printf("Hello, world!");
		launch(args); // Don't forget to set vm arguments with "--module-path "\path\to\javafx-sdk-14\lib" --add-modules javafx.controls,javafx.fxml"
		// in run configurations
	}
	
	public void start(Stage mainStage) {
		final double FPS30 = (1000/30.0); // Constants for 30 and 60 fps respectively.
		final double FPS60 = (1000/60.0);
		
		Pane arrow = new Pane();
		Polygon arrowBase = new Polygon(0, 10, 30, 0, 30, 30);
		arrowBase.setFill(Color.YELLOW);
		Polygon arrowTip = new Polygon(0, 10, 5, 7, 5, 12);
		arrowTip.setFill(Color.RED);
		arrow.getChildren().addAll(arrowBase, arrowTip);
		
		Pane pane = new Pane(); // Set up a window with a piece of text.
		Text text = new Text(50, 50, "");
		pane.getChildren().add(text);
		pane.getChildren().add(arrow);
		
		Scene scene = new Scene(pane, 200, 200);
		mainStage.setTitle("ugh");
		mainStage.setScene(scene);
		mainStage.show();
		
		long initTime = System.currentTimeMillis();
		currentTime = initTime;
		
		// Game loop
		EventHandler<ActionEvent> frameAction = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {// Code in this overridden method runs every frame
				double fps = currentTime; // Separated the fps related math to fix a div by 0 error.
				fps = fps / 1000; // Yeah idk either... >_>
				fps = frameCount / fps;
				text.setText(String.format("FPS: %f", fps));
				arrow.setLayoutX(mainStage.getWidth()/2);
				currentTime = System.currentTimeMillis() - initTime;
				frameCount ++;
			}
		};
		
		Timeline a = new Timeline(new KeyFrame(Duration.millis(FPS60), frameAction)); // Thing the make frames go brrr.
		a.setCycleCount(Timeline.INDEFINITE);
		a.play();
		
		/*
		scene.requestFocus();
		scene.setOnKeyPressed(e->{
			// Keyboard control area.
		}
		*/
	}

}
