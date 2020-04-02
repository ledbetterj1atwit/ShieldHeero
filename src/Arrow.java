import javafx.scene.layout.Pane;

/**
 * Represents an arrow that will hit the player.
 * @author ledbetterj1
 *
 */
public class Arrow extends GameObject {
	
	private static Object N_ARROW_SHAPE; // TODO: Set these.
	private static Object S_ARROW_SHAPE;
	private static Object E_ARROW_SHAPE;
	private static Object W_ARROW_SHAPE;
	
	private long frame; // The frame when the arrow will collide with the shield.
		// Its long just in the amount of frames in a level is a lot. Longs at just like int s.
	
	private boolean hit = false; // Did the player block the arrow.
	private boolean missed = false; // Or did they not.
		// If both are false the arrow has not come by the player yet.
	
	private int frameBuffer; // How many frames between when the arrow appears on screen and when it hits the player.
	
	/**
	 * Your basic constructor.
	 * @param direction
	 * @param frame
	 */
	public Arrow(char direction, long frame) {
		switch(direction) {
			case 'N': this.shape = N_ARROW_SHAPE; this.direction = direction; break; // TODO: Make copy for shape.
			case 'S': this.shape = S_ARROW_SHAPE; this.direction = direction; break;
			case 'E': this.shape = E_ARROW_SHAPE; this.direction = direction; break;
			case 'W': this.shape = W_ARROW_SHAPE; this.direction = direction; break;
			default: this.shape = N_ARROW_SHAPE; this.direction = direction; break;
		}
		
		this.frame = frame;
		
	}
	
	public void hit() {
		this.hit = true;
		// TODO: hit animation.
	}
	
	public void miss() {
		this.missed = true;
		// TODO: miss animation.
	}
	
	/**
	 * Getter for hit.
	 * @return hit
	 */
	public boolean wasHit() {
		return this.hit;
	}
	
	/**
	 * Getter for missed. 
	 * @return missed
	 */
	public boolean wasMissed() {
		return this.missed;
	}
	
	/**
	 * Update the arrow's shape based on size and the current frame.
	 * @param currentFrame
	 * @param screenX
	 * @param screenY
	 */
	public void updateGraphic(long currentFrame, int screenX, int screenY) {
		if(!this.isVisible()) return; // If not visible. Don't bother changing anything.
		if(currentFrame < this.frame-this.frameBuffer) return; // Do nothing if too early in level.
		int frameRatio = (int) (currentFrame/this.frame); // Where between the player and the edge of the screen the arrow should be.
		int arrowEdgeDistanceX = 0; // Distance between the origin of the arrow and its edge.
		int arrowEdgeDistanceY = 0;
		int playerEdgeDistanceX = 0; // Distance between the origin of the player and the edge.
		int playerEdgeDistanceY = 0; // Distance between the origin of the player and the edge.
		int distanceX = screenX/2 - playerEdgeDistanceX;
		int distanceY = screenY/2 - playerEdgeDistanceY;
		int x = 0; //Final x and y positions.
		int y = 0; 
		
		switch(direction) {
		case 'N': x = screenX/2; y = (distanceY*frameRatio) - arrowEdgeDistanceY; break;
		case 'W': x = (distanceX*frameRatio) - arrowEdgeDistanceX; y = screenY/2; break;
		case 'S': x = screenX/2; y = (distanceY*(1/frameRatio)) + arrowEdgeDistanceY; break;
		case 'E': x = (distanceX*(1/frameRatio)) + arrowEdgeDistanceX; y = screenY/2; break;
		}
		
		//Set the calculated x and y for the arrow;
		
		// TODO: make the thing move towards the player.
		// Use <this.frame> to do this.
	}
	
	// TODO: Anything else needed.

}
