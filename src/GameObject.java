import javafx.scene.image.ImageView;

/**
 * This class holds a game object, either an arrow or the player's shield.
 * This is abstract as you should never instantiate this.
 * @author ledbetterj1
 *
 */
public abstract class GameObject {
	
	protected ImageView shape; // Holds the JavaEffects shape to display on screen.
	
	protected char direction; // Holds what direction the thing is facing.
		// Values: 'N', 'S', 'E', 'W'.
		// For arrows this represents where the arrow is coming from('N' is top of screen).
		// For shield its where the shield is facing
	
	private boolean isVisible = false; // Used for toggling the object's visibility in game logic.
	
	
	/**
	 * Constructor.
	 * @param imageView
	 * @param direction
	 */
	protected GameObject(ImageView imageView, char direction) {
		this.shape = imageView;
		this.direction = direction;
	}
	
	
	/**
	 * Makes the object visible on screen and to game logic.
	 */
	protected void makeVisible() {
		this.isVisible = true;
		this.shape.setVisible(true);
	}
	
	/**
	 * Makes the object NOT visible on screen and to game logic.
	 */
	protected void makeInvisible() {
		this.isVisible = false;
		this.shape.setVisible(false);
	}
	
	/**
	 * Returns the shape to put into the scene.
	 */
	public ImageView getShape() {
		return this.shape;
	}
	
	/**
	 * Sets the layout x and y of the shape.
	 * @param x layout x in pixels
	 * @param y layout y in pixels
	 */
	public void setShapePos(int x, int y) {
		this.shape.setLayoutX(x);
		this.shape.setLayoutY(y);
	}
	
	/**
	 * Sets the rotation of the shape.
	 * @param deg Rotation in degrees, clockwise.
	 */
	public void setShapeRotation(double deg) {
		this.shape.setRotate(deg);
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
	 * Method to reInitalize a gameObject, usualy when loading a level.
	 */
	public abstract void reInit();
}
