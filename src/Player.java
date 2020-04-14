import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This is the player and more importantly their shield.
 * @author ledbetterj1
 *
 */
public class Player extends GameObject {
	
	private static String imgPath = "sprites\\player.png";
	
	private int maxLives; // The total lives the player starts with.
	private int lives; // The players lives. At 0 they are dead.
	private int score; // The player's score for the level.
	private int multiplier; // A multiplier, goes up to 4x. Resets on hit.
	
	/**
	 * Your basic constructor.
	 * Direction is 'E' by default ;
	 * @param lives
	 * @throws FileNotFoundException In case the sprite can't be found.
	 */
	public Player(int lives) throws FileNotFoundException {
		super(new ImageView(new Image(new FileInputStream(imgPath))), 'W');
		this.score = 0;
		this.multiplier = 1;
		this.maxLives = lives;
		this.lives = lives;
		
		this.shape.setFitHeight(100); // Force player to be 100x100 px.
		this.shape.setFitWidth(100);
		
		this.setShapeRotation(0);
		this.makeVisible();
	}
	
	/**
	 * Set the direction the shield is facing.
	 * @param direction
	 */
	public void setDirection(char direction) {
		this.direction = direction;
		
		switch(direction) {
		case 'N': break;
		case 'S': break;
		case 'E': break;
		case 'W': break;
		default: break; // You shouldn't get here.
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
	 * Getter for multiplier.
	 * @return
	 */
	public int getMultiplyer() {
		return this.multiplier;
	}
	
	/**
	 * Getter for lives.
	 * @return lives
	 */
	public int getLives() {
		return this.lives;
	}
	

	/**
	 * Increment score when the player successfully blocks an arrow.
	 */
	public void score() {
		if(this.multiplier<4) this.multiplier ++;
		this.score += 1*this.multiplier;
	}
	
	/**
	 * Call when player fails to block an arrow.
	 */
	public void gotHit() {
		this.lives--;
		this.multiplier = 1;
	}
	
	/**
	 * Call from game when the player runs out of lives.
	 */
	public void die() {
		this.makeInvisible();
	}
	
	/**
	 * Reset the player's direction, lives, score, multiplier and visibility.
	 */
	public void reInit() {
		this.setDirection('W');
		this.lives = this.maxLives;
		this.score = 0;
		this.multiplier = 0;
		this.makeVisible();
	}
	
	/**
	 * Update the shape of the player to match player input.
	 */
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
	
}
