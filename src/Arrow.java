import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents an arrow that will hit the player.
 * @author ledbetterj1
 *
 */
public class Arrow extends GameObject {
	
	private long frame; // The frame when the arrow will collide with the shield.
		// Its long just in the amount of frames in a level is a lot. Longs at just like int s.
	
	private static String imgPath = "sprites\\arrow_black.png"; // Image of an arrow.
		//"sprites\\arrow_white.png" is also available.
	
	private boolean hit = false; // Did the player block the arrow.
	private boolean missed = false; // Or did they not.
		// If both are false the arrow has not come by the player yet.
	
	private int frameBuffer; // How many frames between when the arrow appears on screen and when it hits the player.
	
	/**
	 * Your basic constructor.
	 * @param direction
	 * @param frame
	 * @throws FileNotFoundException In case the sprite can't be found.
	 */
	public Arrow(char direction, long frame, int frameBuffer) throws FileNotFoundException {
		super(new ImageView(new Image(new FileInputStream(imgPath))), direction);
		this.shape.setFitWidth(40); // make the image width a nice to compute number.
		switch(direction) {
			case 'N': this.setShapeRotation(180); break;
			case 'S': break;
			case 'E': this.setShapeRotation(-90); break;
			case 'W': this.setShapeRotation(90); break;
			default: break;
		}
		
		this.direction = direction;
		this.setShapePos(-50, -50); // Put out of view until needed.
		this.frame = frame;
		this.frameBuffer = frameBuffer;
		this.makeVisible();
	}
	
	/**
	 * Called when the player successfully blocks the arrow.
	 */
	public void hit() {
		this.hit = true;
	}
	
	/**
	 * Called when the player fails to block the arrow.
	 */
	public void miss() {
		this.missed = true;
	}
	
	/**
	 * Unused
	 * Sets missed and hit to default values.
	 */
	public void statClear() {
		this.missed = false;
		this.hit = false;
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
	 * Getter for frame.
	 * @return
	 */
	public long getFrame() {
		return this.frame;
	}
	
	/**
	 * Update the arrow's shape based on screen size and the current frame.
	 * @param currentFrame
	 * @param screenX Screens's width.
	 * @param screenY Screens's height.
	 */
	public void updateGraphic(long currentFrame, int screenX, int screenY) {
		if(!this.isVisible()) return; // If not visible. Don't bother changing anything.
		if(currentFrame < this.frame-this.frameBuffer) return; // Do nothing if too early in level.
		if(currentFrame > this.frame) { // Make the arrow invisible if past the fame it collides on.
			this.makeInvisible();
			return;
		}
		
		double frameRatio = 1-((this.frame-currentFrame)/(double)this.frameBuffer); // Where between the player-
			//and the edge of the screen the arrow should be.
		
		int arrowCenterDistance = 20; // Distance between the origin of the arrow and its center width.
			//Assuming direction of north.
		
		int playerWidth = 100; // Width of the square player.
		
		int distanceX = (screenX/2) - (playerWidth); // Distance between the edge of the player and the edge- 
			// of the screen.
		int distanceY = (screenY/2) - (playerWidth);
		
		int x = 0; // Final x and y positions.
		int y = 0; 
		
		switch(direction) { // Calculate position based on the other stuff and the arrow's direction.
		case 'N': x = screenX/2-arrowCenterDistance; 
			y = (int) (distanceY*frameRatio); break;
		case 'W': x = (int) (distanceX*frameRatio); 
			y = screenY/2-arrowCenterDistance; break;
		case 'S': x = screenX/2-arrowCenterDistance; 
			y = (int) (distanceY + (playerWidth*1.5) +(distanceY*(1-frameRatio))); break;
		case 'E': x = (int) (distanceX + (playerWidth*1.5) +(distanceX*(1-frameRatio)));
			y = screenY/2-arrowCenterDistance; break;
		}

		this.setShapePos(x, y); //Set the calculated x and y for the arrow;
	}

	/**
	 * Reset the visibility, position, and stat fields of the arrow.
	 */
	public void reInit() {
		this.makeVisible();
		this.setShapePos(-50, -50);
		this.hit = false;
		this.missed = false;
		
		
	}
}
