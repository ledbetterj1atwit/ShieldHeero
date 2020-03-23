/**
 * This is the player and more importantly their shield.
 * @author ledbetterj1
 *
 */
public class Player extends GameObject {
	
	private static Object DEFAULT_PLAYER_SHAPE; // The default player shape. DO NOT MODIFY!
		// TODO: Define this.
	
	private int lives; // The players lives. At 0 they are dead.
	private int score; // The player's score for the level.
	private int multiplier; // A multiplier, goes up to 4x. Resets on hit.
	
	/**
	 * Your basic constructor.
	 * Direction is 'N'and isVisible is false by default ;
	 * @param lives
	 */
	public Player(int lives) {
		super(shape, 'N'); // TODO: make the copy for this.
		this.score = 0;
		this.multiplier = 1;
		this.lives = lives;
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
		return this.getLives();
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
		// TODO: add animations maybe???
		
	}
	
	// TODO: Anything else needed?

}
