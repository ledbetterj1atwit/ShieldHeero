/**
 * This class holds a game object, either an arrow or the player's shield.
 * This is abstract as you should never instantiate this.
 * @author ledbetterj1
 *
 */
public abstract class GameObject {
	
	protected Object shape; // Holds the JavaEffects shape to display on screen.
		// TODO: Pick a better type. Is JavaFX.Shape a thing?
	
	protected char direction; // Holds what direction the thing is facing.
		// Values: 'N', 'S', 'E', 'W'.
		// For arrows this represents where the arrow is coming from('N' is top of screen).
		// For shield its where the shield is facing
	
	private boolean isVisible = false; // Used for toggling the object's visibility on screen and in game logic.
		// always false by default.
	
	
	/**
	 * Constructor.
	 * @param shape
	 * @param direction
	 */
	protected GameObject(Object shape, char direction) {
		this.shape = shape;
		this.direction = direction;
	}
	
	/**
	 * Zero parameter constructor needed for arrow.
	 * @param shape
	 * @param direction
	 */
	protected GameObject() {
		this.shape = null;
		this.direction = 'N';
	}
	
	/**
	 * Makes the object visible on screen and to game logic.
	 */
	protected void makeVisible() {
		this.isVisible = true;
	}
	
	/**
	 * Makes the object NOT visible on screen and to game logic.
	 */
	protected void makeInvisible() {
		this.isVisible = false;
	}
	
	/**
	 * Getter for isVisible.
	 * @return isVisible
	 */
	protected boolean isVisible() {
		return this.isVisible;
	}
	
	/**
	 * Getter for direction.
	 * @return direction
	 */
	protected char getDirection() {
		return this.direction;
	}
	
	/**
	 * Abstract method for anything graphic junk that gets updated every frame.
	 */
	public abstract void updateGraphic();
	
	// TODO: any other methods needed for the shield and arrows?
}
