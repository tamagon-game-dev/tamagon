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
	static BufferedImage[] playerWalk, playerJump, playerWalkATK, playerJumpATK, fireball, fireballCollide;
	
	/**
	 * Loads all entities sprite once
	 */
	public SpriteLoader() {
		playerWalk = new BufferedImage[2];
		playerWalk[0] = playerSheet.getSprite(0, 0, 32, 32);
		playerWalk[1] = playerSheet.getSprite(32, 0, 32, 32);
		
		playerJump = new BufferedImage[2];
		playerJump[0] = playerSheet.getSprite(0, 32, 32, 32);
		playerJump[1] = playerSheet.getSprite(32, 32, 32, 32);
		
		playerWalkATK = new BufferedImage[2];
		playerWalkATK[0] = playerSheet.getSprite(0, 64, 32, 32);
		playerWalkATK[1] = playerSheet.getSprite(32, 64, 32, 32);
		
		playerJumpATK = new BufferedImage[2];
		playerJumpATK[0] = playerSheet.getSprite(0, 96, 32, 32);
		playerJumpATK[1] = playerSheet.getSprite(32, 96, 32, 32);
		
		fireball = new BufferedImage[2];
		fireball[0] = playerSheet.getSprite(0, 128, 32, 32);
		fireball[1] = playerSheet.getSprite(32, 128, 32, 32);
		
		fireballCollide = new BufferedImage[2];
		fireballCollide[0] = playerSheet.getSprite(0, 160, 32, 32);
		fireballCollide[1] = playerSheet.getSprite(32, 160, 32, 32);
		
		
	}
}
