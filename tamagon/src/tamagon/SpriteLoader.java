package tamagon;

import java.awt.image.BufferedImage;

public class SpriteLoader {
	
	/**
	 * Player's sprite sheet
	 */
	private Spritesheet playerSheet = new Spritesheet("player");
	
	/**
	 * animated sprite
	 */
	static BufferedImage[] playerWalk;
	
	/**
	 * Loads all entities sprite once
	 */
	public SpriteLoader() {
		playerWalk = new BufferedImage[2];
		playerWalk[0] = playerSheet.getSprite(0, 0, 32, 32);
		playerWalk[1] = playerSheet.getSprite(32, 0, 32, 32);
	}
}
