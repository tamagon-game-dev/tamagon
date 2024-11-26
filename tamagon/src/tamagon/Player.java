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
		Camera.x = Camera.clampCamera((this.x * Game.scale), 0, Level.levelW * (Level.dimension * Game.scale) - Game.width);
		Camera.y = Camera.clampCamera((this.y * Game.scale), 0, Level.levelH * (Level.dimension * Game.scale) - Game.height);
	}

}
