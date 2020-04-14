import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.Pos;

/**
 * Home of main, most GUI logic, and all the game logic.
 * @author ledbetterj1
 *
 */
public class Game extends Application {
	static long frameCount = 0; // How many frames have passed(and what frame your on)(level independent)
	static int screenWidth = 750; // Width of screen in pixels.
	static int screenHeight = 500; // Height of screen in pixels.
	static int levelSelected = 0; // Index of the selected level.
	static ArrayList<Level> levels = new ArrayList<Level>(); // The list of all levels.
	static long levelStartFrame = 0; // The frame when the player started the level.
	static long levelEndFrame = 0; // The frame where the level ends.
	
	/**
	 * Sets up the scene for level selection.
	 * @param s The scene to set up.
	 * @param startScene The scene of the start screen.
	 * @param gameScene The scene where the game happens.
	 * @param p The player.
	 * @param mainStage The main stage.
	 */
	public void setupLevelSelcetScene(Scene s, Scene startScene, Scene gameScene, Player p, Stage mainStage) {
		BorderPane basePane = new BorderPane();
		VBox centerPane = new VBox(); // Holds level info.
		VBox rightPane = new VBox(); // Holds level selection buttons.
		
		Text prevLev = new Text("Unset");
		prevLev.setStyle("-fx-font: 20 arial;");
		
		Text currLev = new Text("Unset");
		currLev.setFill(Color.RED);
		currLev.setStroke(Color.BLACK);
		currLev.setStyle("-fx-font: 40 arial;");
		
		Text nextLev = new Text("Unset");
		nextLev.setStyle("-fx-font: 20 arial;");
		
		currLev.setText(String.format("%s: D %s", levels.get(levelSelected).getName(), levels.get(levelSelected).getDifficulty())); // Set the text.
		
		if(levelSelected+1 == levels.size()) { // Set next level text to wrap to begining.
			nextLev.setText(String.format("%s: D %s", levels.get(0).getName(), levels.get(0).getDifficulty()));
		}
		else {
			nextLev.setText(String.format("%s: D %s", levels.get(levelSelected+1).getName(), levels.get(levelSelected+1).getDifficulty()));
		}
		
		if(levelSelected-1 == -1) { // Set previous level text to wrap to end. 
			prevLev.setText(String.format("%s: D %s", levels.get(levels.size()-1).getName(), levels.get(levels.size()-1).getDifficulty()));
		}
		else {
			prevLev.setText(String.format("%s: D %s", levels.get(levelSelected-1).getName(), levels.get(levelSelected-1).getDifficulty()));
		}
		
		Button prev = new Button("Previous");
		Button next = new Button("Next");
		Button startGame = new Button("Start");
		Button back = new Button("Back");
		
		Circle indicator = new Circle(7.0); // A small pointless indicator.
		indicator.setFill(Color.AQUA);
		indicator.setStroke(Color.BLACK);
		
		basePane.setCenter(centerPane); // Put the things in their panes.
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
			/**
			 * Go back to title screen.
			 */
			@Override
			public void handle(ActionEvent event) {
				mainStage.setScene(startScene);
				mainStage.setTitle("Main Menu");
			}
		};
		
		EventHandler<ActionEvent> startAction = new EventHandler<ActionEvent>() {
			/**
			 * Go to game scene. Load level.
			 */
			@Override
			public void handle(ActionEvent event) {
				loadLevel(gameScene, p);
				levelStartFrame = frameCount;
				mainStage.setScene(gameScene);
				mainStage.setTitle("");
				mainStage.requestFocus();
			}
		};
		
		EventHandler<ActionEvent> prevAction = new EventHandler<ActionEvent>() {
			/**
			 * Update text and level selected.
			 */
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
			/**
			 * Update text and level selected but forwards.
			 */
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
		
		startGame.setOnAction(startAction); // Bind actions to buttons.
		back.setOnAction(backAction);
		prev.setOnAction(prevAction);
		next.setOnAction(nextAction);
		
		s.setRoot(basePane); // Attach everything to s.
	}
	
	/**
	 * Setup the startScreen
	 * @param s The scene to set up.
	 * @param levelSelectScene The scene with the level select.
	 * @param mainStage
	 */
	public void setupStartScene(Scene s, Scene levelSelectScene, Stage mainStage) {
		Pane basePane = new Pane();
		VBox centerPane = new VBox(); // Holds text and start button.
		
		Text title = new Text("Sheild Heero");
		title.setStyle("-fx-font: 40 arial;");
		Button start = new Button("Start");
		Button exit = new Button("Quit");
		
		basePane.getChildren().add(exit); // Add things to panes.
		basePane.getChildren().add(centerPane);
		
		centerPane.getChildren().add(title);
		centerPane.getChildren().add(start);
		centerPane.setLayoutX((s.getWidth()/2)-130);
		centerPane.setLayoutY((s.getHeight()/2)-30);
		centerPane.setAlignment(Pos.CENTER);
		
		EventHandler<ActionEvent> exitAction = new EventHandler<ActionEvent>() {
			/**
			 * Exit
			 */
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		};
		
		EventHandler<ActionEvent> startAction = new EventHandler<ActionEvent>() {
			/**
			 * Go to the level select screen.
			 */
			@Override
			public void handle(ActionEvent event) {
				mainStage.setScene(levelSelectScene);
				mainStage.setTitle("Level Select");
			}
		};
		
		exit.setOnAction(exitAction); // Bind actions to buttons.
		start.setOnAction(startAction);
		
		s.setRoot(basePane); // Attach everything to s.
		
	}
	
	/**
	 * Set up the scene you see when you win.
	 * @param s The scene to set up.
	 * @param levelSelectScene The level select screen.
	 * @param p The player.
	 * @param mainStage
	 */
	public void setupWinScene(Scene s, Scene levelSelectScene, Player p, Stage mainStage) {	
		BorderPane basePane = new BorderPane();
		VBox centerPane= new VBox(); // Basically holds everything.
		Button backToLevelSelect = new Button("Back to Level Select");
		Text winText = new Text();
		
		centerPane.getChildren().add(winText); // Add stuff to panes.
		centerPane.getChildren().add(backToLevelSelect);
		centerPane.setAlignment(Pos.CENTER);
		
		basePane.setCenter(centerPane);
		
		Level level = levels.get(levelSelected); // Calculate the stats for the completed(currently selected) level.
		int[] stats = level.getStats();
		double accuracy = stats[1]/(double)stats[0];
		
		winText.setText(String.format("Level Complete!%n%nScore: %d%nAccuracy: %.2f%%, Missed: %d%n", p.getScore(), accuracy, stats[2]));
			// Put stats on screen.
		winText.setTextAlignment(TextAlignment.CENTER);
		
		EventHandler<ActionEvent> backToLevelSelectAction = new EventHandler<ActionEvent>() {
			/**
			 * Go back to the level select screen.
			 */
			@Override
			public void handle(ActionEvent event) {
				mainStage.setScene(levelSelectScene);
				mainStage.setTitle("Level Select");
			}
		};
		
		backToLevelSelect.setOnAction(backToLevelSelectAction); // Bind action to button.
		
		s.setRoot(basePane); // Attach stuff to s.
	}
	
	/**
	 * Setup the scene you see when you lose.
	 * @param s The scene to set up.
	 * @param levelSelectScene The level select scene.
	 * @param mainStage
	 */
	public void setupLoseScene(Scene s, Scene levelSelectScene, Stage mainStage) {
		BorderPane basePane = new BorderPane();
		VBox centerPane= new VBox(); // Holds everything.
		Button backToLevelSelect = new Button("Back to Level Select");
		Text loseText = new Text();
		
		centerPane.getChildren().add(loseText);
		centerPane.getChildren().add(backToLevelSelect);
		centerPane.setAlignment(Pos.CENTER);
		
		basePane.setCenter(centerPane);
		
		loseText.setText(String.format("Game OVER!"));
		loseText.setTextAlignment(TextAlignment.CENTER);
		loseText.setFill(Color.RED);
		loseText.setStyle("-fx-font: 40 arial;");
		
		EventHandler<ActionEvent> backToLevelSelectAction = new EventHandler<ActionEvent>() {
			/**
			 * Go back to level select.
			 */
			@Override
			public void handle(ActionEvent event) {
				mainStage.setScene(levelSelectScene);
				mainStage.setTitle("Level Select");
			}
		};
		
		backToLevelSelect.setOnAction(backToLevelSelectAction); // Bind action to button.
		
		s.setRoot(basePane); // Attach everything to s.
		
		
	}
	
	/**
	 * Ready the game scene with selected level and set the levelEnd frame.
	 * @param gameScene The game scene.
	 * @param p The player.
	 */
	public void loadLevel(Scene gameScene, Player p) {
		Pane basePane = (Pane) gameScene.getRoot(); // Get the root(only) pane.
		Level level = levels.get(levelSelected); // Get the selected level.
		
		long maxFrame = 0;
		for(Arrow a : level.getArrows()) {
			basePane.getChildren().add(a.getShape()); // Add all arrows in level to scene.
			a.reInit(); // Reinit all arrows.
			a.makeVisible(); // Make SURE they are all visible.
			if(a.getFrame() > maxFrame) { // Calculate the level end frame(Highest frame of all arrows).
				maxFrame = a.getFrame();
			}
		}
		p.reInit(); // Reinit player.
		levelEndFrame = maxFrame; // Set level end frame.
	}
	
	/**
	 * Remove all level elements from the game scene and reset the level start and end frames.
	 * @param gameScene
	 */
	public void unloadLevel(Scene gameScene) {
		Pane basePane = (Pane) gameScene.getRoot(); // Get the root(only) pane of the game scene. 
		Level level = levels.get(levelSelected); // Get the selected level.
		for(Arrow a : level.getArrows()) {
			basePane.getChildren().remove(a.getShape()); // Remove all the arrows
		}
		levelEndFrame = 0; // Reset end and start frames.
		levelStartFrame = 0;
	}
	
	/**
	 * Main
	 * Start is more important.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args); // Don't forget to set vm arguments with "--module-path "\path\to\javafx-sdk-14\lib" --add-modules javafx.controls,javafx.fxml"
		// in run configurations
	}
	
	public void start(Stage mainStage) throws FileNotFoundException {
		//final double FPS30 = (1000/30.0); // Constants for 30 and 60 fps respectively.
		final double FPS60 = (1000/60.0);
		//final double FPS5 = (1000/5.0); // Constant for 5 fps (debug).
		
		
		Player player = new Player(3); // The player object.
		
		ArrayList<Arrow> fourDirectionsArrows = new ArrayList<Arrow>(); // Levels
		fourDirectionsArrows.add(new Arrow('N', (long)2*60, 1*60));
		fourDirectionsArrows.add(new Arrow('S', (long)2*60, 1*60));
		fourDirectionsArrows.add(new Arrow('E', (long)2*60, 1*60));
		fourDirectionsArrows.add(new Arrow('W', (long)4*60, 4*60));
		levels.add(new Level(fourDirectionsArrows, "Four Directions", 999));
		
		ArrayList<Arrow> lRArrows = new ArrayList<Arrow>();
		lRArrows.add(new Arrow('E', (long)3*60, 2*60));
		lRArrows.add(new Arrow('W', (long)6*60, 2*60));
		lRArrows.add(new Arrow('E', (long)9*60, 2*60));
		lRArrows.add(new Arrow('W', (long)10*60, 30));
		levels.add(new Level(lRArrows, "Left and Right", 1));
		
		ArrayList<Arrow> loooongArrows = new ArrayList<Arrow>();
		loooongArrows.add(new Arrow('E', (long)180, 3*60));
		loooongArrows.add(new Arrow('E', (long)200, 3*60));
		loooongArrows.add(new Arrow('E', (long)220, 3*60));
		loooongArrows.add(new Arrow('E', (long)240, 3*60));
		loooongArrows.add(new Arrow('E', (long)260, 3*60));
		loooongArrows.add(new Arrow('E', (long)280, 3*60));
		loooongArrows.add(new Arrow('E', (long)300, 3*60));
		loooongArrows.add(new Arrow('E', (long)320, 3*60));
		loooongArrows.add(new Arrow('E', (long)340, 3*60));
		loooongArrows.add(new Arrow('E', (long)360, 3*60));
		loooongArrows.add(new Arrow('E', (long)380, 3*60));
		loooongArrows.add(new Arrow('E', (long)400, 3*60));
		loooongArrows.add(new Arrow('E', (long)420, 3*60));
		loooongArrows.add(new Arrow('E', (long)440, 3*60));
		loooongArrows.add(new Arrow('E', (long)460, 3*60));
		loooongArrows.add(new Arrow('E', (long)480, 3*60));
		loooongArrows.add(new Arrow('E', (long)500, 3*60));
		loooongArrows.add(new Arrow('E', (long)520, 3*60));
		loooongArrows.add(new Arrow('E', (long)540, 3*60));
		loooongArrows.add(new Arrow('E', (long)560, 3*60));
		loooongArrows.add(new Arrow('E', (long)580, 3*60));
		levels.add(new Level(loooongArrows, "Looooooooong", 1));
		
		Pane basePane = new Pane(); // Set up the game scene.
		Text lives = new Text(String.format("Lives: $d", player.getLives()));
		Text score = new Text(String.format("Score: $d", player.getScore()));
		Text multiplyer = new Text(String.format("Multiplyer: $d", player.getMultiplyer()));
		basePane.getChildren().add(lives);
		lives.setLayoutY(20);
		basePane.getChildren().add(score);
		score.setLayoutX(screenWidth-120);
		score.setLayoutY(20);
		basePane.getChildren().add(multiplyer);
		multiplyer.setLayoutX(screenWidth-80);
		multiplyer.setLayoutY(40);
		
		basePane.getChildren().add(player.getShape());
		player.setShapePos((screenWidth/2)-50, (screenHeight/2)-50); // Put the player in the center of the screen.
		
		Scene gameScene = new Scene(basePane, screenWidth, screenHeight); // Setup all scenes.
		Scene startScene = new Scene(new Pane(), screenWidth, screenHeight);
		Scene levelScene = new Scene(new Pane(), screenWidth, screenHeight);
		Scene winScene = new Scene(new Pane(), screenWidth, screenHeight);
		Scene loseScene = new Scene(new Pane(), screenWidth, screenHeight);
		
		setupStartScene(startScene, levelScene, mainStage);
		setupLevelSelcetScene(levelScene, startScene, gameScene, player, mainStage);
		// win Scene is setup in game loop so it has accurate player and level info.
		setupLoseScene(loseScene, levelScene, mainStage);
		
		mainStage.setTitle("Main Menu"); // Show the start screen.
		mainStage.setScene(startScene);
		mainStage.show();
		
		// Game loop
		EventHandler<ActionEvent> frameAction = new EventHandler<ActionEvent>() {
			/**
			 * Game loop.
			 * Code in this overridden method tries to run every frame.
			 */
			@Override
			public void handle(ActionEvent event) {
				
				long levelFrame = frameCount - levelStartFrame; // Calculate how many frames have passed since-
					// the start of the level.
				
				if(levelStartFrame != 0 || levelFrame < levelEndFrame) { // Make sure a level has actually been-
						// selected and hasn't ended.
					levels.get(levelSelected).updateGraphic(levelFrame, screenWidth, screenHeight); // Graphics
					player.updateGraphic();
					levels.get(levelSelected).checkColisions(player, levelFrame); // Check collisions.
				}
				
				if(player.getLives() <= 0 && player.isVisible()) { // Check if player died.
					player.die();
					mainStage.setScene(loseScene);
				}
				
				lives.setText(String.format(String.format("Lives: %d", player.getLives()))); // Update text.
				score.setText(String.format(String.format("Score: %d", player.getScore())));
				multiplyer.setText(String.format(String.format("Multiplier: %d", player.getMultiplyer())));
				
				if(levelFrame >= levelEndFrame && levelStartFrame != 0) { // Check if the level is over.
					if(player.getLives()>0 && player.isVisible()) {
						setupWinScene(winScene, levelScene, player, mainStage); // This is here so setupWinScene-
							//uses the latest player and level data.
						mainStage.setScene(winScene);
						player.makeInvisible();
					}
					unloadLevel(gameScene); // Don't forget to unload the level!
				}
				
				frameCount ++; // Increment the frame count.
				
			}
		};
		
		Timeline a = new Timeline(new KeyFrame(Duration.millis(FPS60), frameAction)); // Thing the make frames go.
		a.setCycleCount(Timeline.INDEFINITE);
		a.play();
		
		
		
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			/**
			 * Handle keypresses.
			 */
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.UP) {
					player.setDirection('N');
				}
				else if(event.getCode() == KeyCode.DOWN) {
					player.setDirection('S');
				}
				else if(event.getCode() == KeyCode.LEFT) {
					player.setDirection('W');
				}
				else if(event.getCode() == KeyCode.RIGHT) {
					player.setDirection('E');
				}
				else if(event.getCode() == KeyCode.ESCAPE) {
					System.exit(0);
				}
			}
		});
	}

}
