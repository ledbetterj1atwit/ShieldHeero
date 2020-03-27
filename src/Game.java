import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
	
	public static void main(String[] args) {
		System.out.printf("Hello, world!");
		launch(args); // Don't forget to set vm arguments with "--module-path "\path\to\javafx-sdk-14\lib" --add-modules javafx.controls,javafx.fxml"
		// in run configurations
	}
	
	public void start(Stage mainStage) {
		final double FPS30 = (1000/30.0); // Constants for 30 and 60 fps respectively.
		final double FPS60 = (1000/60.0);
		
		BorderPane pane = new BorderPane(); // Set up a window with a piece of text.
		Text text = new Text(50, 50, "");
		pane.setCenter(text);
		Scene scene = new Scene(pane, 200, 200);
		mainStage.setTitle("ugh");
		mainStage.setScene(scene);
		mainStage.show();
		
		// Game loop
		EventHandler<ActionEvent> frameAction = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {// Code in this overridden method runs every frame
				text.setText(String.format("FPS: %d", frameCount));
				frameCount ++;
			}
		};
		
		Timeline a = new Timeline(new KeyFrame(Duration.millis(FPS30), frameAction)); // Thing the make frames go brrr.
		a.setCycleCount(Timeline.INDEFINITE);
		a.play();
	}

}
