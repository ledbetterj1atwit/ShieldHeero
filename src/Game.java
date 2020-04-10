import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.Pos;

/**
 * Home of main, most GUI logic, and all the game logic.
 * @author ledbetterj1
 *
 */
public class Game extends Application {
	// TODO: GUI and game logic (oof).
	static long frameCount = 0; // How many frames have passed(and what frame your on)(level independent)
	static long currentTime = 1;
	static int screenWidth = 750;
	static int screenHeight = 500;
	static int levelSelected = 0;
	static ArrayList<Level> levels = new ArrayList<Level>();
	
	public void setupLevelSelcetScene(Scene s, Scene startScene, Scene gameScene, Stage mainStage) {
		BorderPane basePane = new BorderPane();
		VBox centerPane = new VBox();
		VBox rightPane = new VBox();
		
		Text prevLev = new Text("Unset");
		prevLev.setStyle("-fx-font: 20 arial;");
		Text currLev = new Text("Unset");
		currLev.setFill(Color.RED);
		currLev.setStroke(Color.BLACK);
		currLev.setStyle("-fx-font: 40 arial;");
		Text nextLev = new Text("Unset");
		nextLev.setStyle("-fx-font: 20 arial;");
		
		currLev.setText(String.format("%s: D %s", levels.get(levelSelected).getName(), levels.get(levelSelected).getDifficulty())); // Set the text.
		
		if(levelSelected+1 == levels.size()) {
			nextLev.setText(String.format("%s: D %s", levels.get(0).getName(), levels.get(0).getDifficulty()));
		}
		else {
			nextLev.setText(String.format("%s: D %s", levels.get(levelSelected+1).getName(), levels.get(levelSelected+1).getDifficulty()));
		}
		
		if(levelSelected-1 == -1) {
			prevLev.setText(String.format("%s: D %s", levels.get(levels.size()-1).getName(), levels.get(levels.size()-1).getDifficulty()));
		}
		else {
			prevLev.setText(String.format("%s: D %s", levels.get(levelSelected-1).getName(), levels.get(levelSelected-1).getDifficulty()));
		}
		
		Button prev = new Button("Previous");
		Button next = new Button("Next");
		Button startGame = new Button("Start");
		Button back = new Button("Back");
		
		Circle indicator = new Circle(7.0);
		indicator.setFill(Color.AQUA);
		indicator.setStroke(Color.BLACK);
		
		basePane.setCenter(centerPane);
		basePane.setTop(back);
		basePane.setRight(rightPane);
		basePane.setBottom(startGame);
		basePane.setLeft(indicator);
		BorderPane.setAlignment(centerPane, Pos.CENTER);
		BorderPane.setAlignment(startGame, Pos.CENTER);
		BorderPane.setAlignment(rightPane, Pos.CENTER);
		BorderPane.setAlignment(indicator, Pos.CENTER_RIGHT);
		
		centerPane.getChildren().add(prevLev);
		centerPane.getChildren().add(currLev);
		centerPane.getChildren().add(nextLev);
		centerPane.setAlignment(Pos.CENTER);
		
		rightPane.getChildren().add(prev);
		rightPane.getChildren().add(next);
		rightPane.setAlignment(Pos.CENTER);
		
		EventHandler<ActionEvent> backAction = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainStage.setScene(startScene);
				mainStage.setTitle("Main Menu");
			}
		};
		
		EventHandler<ActionEvent> startAction = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainStage.setScene(gameScene);
				mainStage.setTitle("");
			}
		};
		
		EventHandler<ActionEvent> prevAction = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(levelSelected == 0) levelSelected = levels.size()-1; // set the selected level.
				else levelSelected --;
				
				currLev.setText(String.format("%s: D %s", levels.get(levelSelected).getName(), levels.get(levelSelected).getDifficulty())); // Set the text.
				
				if(levelSelected+1 == levels.size()) {
					nextLev.setText(String.format("%s: D %s", levels.get(0).getName(), levels.get(0).getDifficulty()));
				}
				else {
					nextLev.setText(String.format("%s: D %s", levels.get(levelSelected+1).getName(), levels.get(levelSelected+1).getDifficulty()));
				}
				
				if(levelSelected-1 == -1) {
					prevLev.setText(String.format("%s: D %s", levels.get(levels.size()-1).getName(), levels.get(levels.size()-1).getDifficulty()));
				}
				else {
					prevLev.setText(String.format("%s: D %s", levels.get(levelSelected-1).getName(), levels.get(levelSelected-1).getDifficulty()));
				}
			}
		};
		
		EventHandler<ActionEvent> nextAction = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(levelSelected == levels.size()-1) levelSelected = 0; // set the selected level.
				else levelSelected ++;
				
				
				currLev.setText(String.format("%s: D %s", levels.get(levelSelected).getName(), levels.get(levelSelected).getDifficulty())); // Set the text.
				
				if(levelSelected+1 == levels.size()) {
					nextLev.setText(String.format("%s: D %s", levels.get(0).getName(), levels.get(0).getDifficulty()));
				}
				else {
					nextLev.setText(String.format("%s: D %s", levels.get(levelSelected+1).getName(), levels.get(levelSelected+1).getDifficulty()));
				}
				
				if(levelSelected-1 == -1) {
					prevLev.setText(String.format("%s: D %s", levels.get(levels.size()-1).getName(), levels.get(levels.size()-1).getDifficulty()));
				}
				else {
					prevLev.setText(String.format("%s: D %s", levels.get(levelSelected-1).getName(), levels.get(levelSelected-1).getDifficulty()));
				}
			}
		};
		
		startGame.setOnAction(startAction);
		back.setOnAction(backAction);
		prev.setOnAction(prevAction);
		next.setOnAction(nextAction);
		
		s.setRoot(basePane);
	}
	
	public void setupStartScene(Scene s, Scene levelSelectScene, Stage mainStage) {
		Pane basePane = new Pane();
		VBox centerPane = new VBox();
		Text title = new Text("Sheild Heero");
		title.setStyle("-fx-font: 40 arial;");
		Button start = new Button("Start");
		Button exit = new Button("Quit");
		
		basePane.getChildren().add(exit);
		basePane.getChildren().add(centerPane);
		
		centerPane.getChildren().add(title);
		centerPane.getChildren().add(start);
		centerPane.setLayoutX((s.getWidth()/2)-130);
		centerPane.setLayoutY((s.getHeight()/2)-30);
		centerPane.setAlignment(Pos.CENTER);
		
		EventHandler<ActionEvent> exitAction = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		};
		
		EventHandler<ActionEvent> startAction = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainStage.setScene(levelSelectScene);
				mainStage.setTitle("Level Select");
			}
		};
		
		exit.setOnAction(exitAction);
		start.setOnAction(startAction);
		
		s.setRoot(basePane);
		
	}
	
	public static void main(String[] args) {
		System.out.printf("Hello, world!");
		launch(args); // Don't forget to set vm arguments with "--module-path "\path\to\javafx-sdk-14\lib" --add-modules javafx.controls,javafx.fxml"
		// in run configurations
	}
	
	public void start(Stage mainStage) throws FileNotFoundException {
		final double FPS30 = (1000/30.0); // Constants for 30 and 60 fps respectively.
		final double FPS60 = (1000/60.0);
		
		Player player = new Player(3);
		Arrow testArrow = new Arrow('N', (long)99999);
		
		levels.add(new Level(new ArrayList<Arrow>(), "Lorem ipsum dolor sit", 0));
		levels.add(new Level(new ArrayList<Arrow>(), "Lorem ipsum dolor sit amet", -1));
		levels.add(new Level(new ArrayList<Arrow>(), "Lorem ipsum dolor sit amet", 2));
		levels.add(new Level(new ArrayList<Arrow>(), "Lorem ipsum dolor sit amet", 999));
		
		
		
		
		
		Pane basePane = new Pane(); // Set up a window with a piece of text.
		Text fpsCount = new Text();
		Text frameCountDisplay = new Text(String.format("Frame: %d", frameCount));
		Text lives = new Text(String.format("Lives: $d", player.getLives()));
		Text score = new Text(String.format("Score: $d", player.getScore()));
		Text multiplyer = new Text(String.format("Multiplyer: $d", player.getMultiplyer()));
		basePane.getChildren().add(lives);
		lives.setLayoutY(20);
		basePane.getChildren().add(fpsCount);
		fpsCount.setLayoutY(40);
		basePane.getChildren().add(frameCountDisplay);
		frameCountDisplay.setLayoutY(60);
		basePane.getChildren().add(score);
		score.setLayoutX(screenWidth-120);
		score.setLayoutY(20);
		basePane.getChildren().add(multiplyer);
		multiplyer.setLayoutX(screenWidth-80);
		multiplyer.setLayoutY(40);
		
		basePane.getChildren().add(player.getShape());
		player.setShapePos((screenWidth/2)-50, (screenHeight/2)-50); // Put the player in the center of the screen.
		testArrow.setShapePos(0,0);
		
		Scene gameScene = new Scene(basePane, screenWidth, screenHeight);
		Scene startScene = new Scene(new Pane(), screenWidth, screenHeight);
		Scene levelScene = new Scene(new Pane(), screenWidth, screenHeight);
		
		setupStartScene(startScene, levelScene, mainStage);
		setupLevelSelcetScene(levelScene, startScene, gameScene, mainStage);
		
		mainStage.setTitle("Main Menu");
		mainStage.setScene(startScene);
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
				fpsCount.setText(String.format("FPS: %f", fps));
				frameCountDisplay.setText(String.format("Frame: %d", frameCount));
				currentTime = System.currentTimeMillis() - initTime;
				if(frameCount > (60*10)) {
					player.setDirection('W');
				}
				player.updateGraphic();
				
				lives.setText(String.format(String.format("Lives: %d", player.getLives())));
				score.setText(String.format(String.format("Score: %d", player.getScore())));
				multiplyer.setText(String.format(String.format("Multiplier: %d", player.getMultiplyer())));
				
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
