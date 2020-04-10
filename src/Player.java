import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * This is the player and more importantly their shield.
 * @author ledbetterj1
 *
 */
public class Player extends GameObject {
	
	private static String imgPath = "sprites\\player.png";
	
	private int lives; // The players lives. At 0 they are dead.
	private int score; // The player's score for the level.
	private int multiplier; // A multiplier, goes up to 4x. Resets on hit.
	
	/**
	 * Your basic constructor.
	 * Direction is 'N'and isVisible is false by default ;
	 * @param lives
	 * @throws FileNotFoundException 
	 */
	public Player(int lives) throws FileNotFoundException {
		super(new ImageView(new Image(new FileInputStream(imgPath))), 'N'); // TODO: make the copy for this.
		this.score = 0;
		this.multiplier = 1;
		this.lives = lives;
		this.shape.setFitHeight(100); // Force player to be 100x100 px.
		this.shape.setFitWidth(100);
		this.setShapeRotation(90);
		this.makeVisible();
	}
	
	/**
	 * Set the direction the shield is facing.
	 * @param direction
	 */
	public void setDirection(char direction) {
		this.direction = direction;
		
		// TODO: add code to change this.shape so shield faces <direction>.
		switch(direction) {
		case 'N': break;
		case 'S': break;
		case 'E': break;
		case 'W': break;
		default: break; // You shouldn't get here so error or warn.
		}
		
	}
	
	/**
	 * Getter for score.
	 * @return score
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * Getter for mult.
	 * @return
	 */
	public int getMultiplyer() {
		return this.multiplier;
	}
	
	/**
	 * Call when the player successfully blocks an arrow.
	 */
	public void score() {
		if(this.multiplier<4) this.multiplier ++;
		this.score += 1*this.multiplier;
	}
	
	/**
	 * Getter for lives.
	 * @return lives
	 */
	public int getLives() {
		return this.lives;
	}
	
	/**
	 * Call when player fails to block an arrow.
	 */
	public void gotHit() {
		this.lives--;
		this.multiplier = 1;
		// TODO: Add hit animation.
	}
	
	/**
	 * Call from game when the player runs out of lives.
	 */
	public void die() {
		// TODO: Add death animation
		this.makeInvisible();
	}

	public void updateGraphic() {
		if(!this.isVisible()) return; // If not visible. Don't bother changing anything.
		
		// Big if block for moving from current direction to next direction.
		if(this.direction == 'N') {
			this.setShapeRotation(90);
		}
		else if(this.direction == 'S') {
			this.setShapeRotation(-90);
		}
		else if (this.direction == 'E') {
			this.setShapeRotation(180);
		}
		else if (this.direction == 'W') {
			this.setShapeRotation(0);
		}
		
	}
	
	// TODO: Anything else needed?

}
