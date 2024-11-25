package tamagon;

public class Player extends Entity {

	/**
	 * Player
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Player(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	//Player's logic 
	public void update() {
		this.x++;
		Camera.x = (this.x * Game.scale) - (Game.width/2);
		Camera.y = (this.y * Game.scale) - (Game.height/2);
	}

}
