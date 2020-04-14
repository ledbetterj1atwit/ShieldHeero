import java.util.ArrayList;

/**
 * A collection of arrows called a level.
 * @author ledbetterj1
 *
 */
public class Level {
	private ArrayList<Arrow> arrows; // The arrows.
	private String name; // A name.
	private int difficulty; // An arbitrary difficulty thing.
	//private Object music; // Music for level.
	
	/**
	 * Basic constructor.
	 * @param arrows
	 * @param name
	 * @param difficulty
	 */
	public Level(ArrayList<Arrow> arrows, String name, int difficulty) {
		this.arrows = arrows;
		this.name = name;
		this.difficulty = difficulty;
	}
	
	/**
	 * Get the arrow at <idx>.
	 * @param idx
	 * @return Arrow
	 */
	public Arrow getArrow(int idx) {
		return this.arrows.get(idx);
	}
	
	/**
	 * Get the arrows ArrayList.
	 * @return arrows
	 */
	public ArrayList<Arrow> getArrows(){
		return this.arrows;
	}
	
	/**
	 * Get stats about how many arrows where hit or missed.
	 * Returns with the following format:
	 * 
	 * 	{total arrows in level,
	 * 	arrows hit,
	 * 	arrows missed}
	 * 
	 * @return int[]
	 */
	public int[] getStats() {
		int total = this.arrows.size();
		int hit = 0;
		int missed = 0;
		
		for(Arrow a : this.arrows) {
			if(a.wasHit()) hit ++;
			if(a.wasMissed()) missed ++;
		}
		int[] result = {total, hit, missed};
		return result; 
	}
	
	/**
	 * Gets the level name.
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the level difficulty.
	 * @return difficulty
	 */
	public int getDifficulty() {
		return this.difficulty;
	}
	
	/**
	 * Update the shape of all arrows in the level.
	 * @param currentFrame
	 * @param screenX
	 * @param screenY
	 */
	public void updateGraphic(long currentFrame, int screenX, int screenY) {
		for(Arrow a : this.arrows) {
			a.updateGraphic(currentFrame, screenX, screenY);
		}
	}
	
	/**
	 * Check collisions of the arrows in this level against the player.
	 * @param p the player
	 * @param currentFrame the level frame
	 */
	public void checkColisions(Player p, Long currentFrame) {
		for(Arrow a : this.getArrows()) { // Handle arrow collision.
			if(a.getFrame() == currentFrame) {
				if (p.getDirection() == a.getDirection()){
					p.score();
					a.hit();
				}
				else {
					p.gotHit();
					a.miss();
				}
				a.makeInvisible();
			}
		}
	}
}
