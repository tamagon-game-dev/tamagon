package tamagon;

import java.awt.image.BufferedImage;

public class SpriteLoader {
	
	/**
	 * Player's sprite sheet
	 */
	private Spritesheet playerSheet = new Spritesheet("player");
	
	/**
	 * Entities' sprite sheet
	 */
	private Spritesheet entitySheet = new Spritesheet("entities");
	
	/**
	 *  player's animated sprite
	 */
	static BufferedImage[] playerWalk, playerJump, playerWalkATK, playerJumpATK, fireball, fireballCollide;
	
	/**
	 * Eggs animation
	 */
	static BufferedImage[] egg1, egg2, egg3, egg4, egg5;
	
	/**
	 * Gems animation
	 */
	static BufferedImage[] sapphire, ruby, emerald, diamond;
	
	/**
	 * Loads all entities sprite once
	 */
	public SpriteLoader() {
		//Player
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
		
		
		//EGGS
		egg1 = new BufferedImage[4];
		egg1[0] = entitySheet.getSprite(0, 0, 32, 32);
		egg1[1] = entitySheet.getSprite(32, 0, 32, 32);
		egg1[2] = entitySheet.getSprite(0, 0, 32, 32);
		egg1[3] = entitySheet.getSprite(32*2, 0, 32, 32);
		
		egg2 = new BufferedImage[4];
		egg2[0] = entitySheet.getSprite(32*3, 0, 32, 32);
		egg2[1] = entitySheet.getSprite(32*4, 0, 32, 32);
		egg2[2] = entitySheet.getSprite(32*3, 0, 32, 32);
		egg2[3] = entitySheet.getSprite(32*5, 0, 32, 32);
		
		egg3 = new BufferedImage[4];
		egg3[0] = entitySheet.getSprite(32*6, 0, 32, 32);
		egg3[1] = entitySheet.getSprite(32*7, 0, 32, 32);
		egg3[2] = entitySheet.getSprite(32*6, 0, 32, 32);
		egg3[3] = entitySheet.getSprite(32*8, 0, 32, 32);
		
		egg4 = new BufferedImage[4];
		egg4[0] = entitySheet.getSprite(0, 32, 32, 32);
		egg4[1] = entitySheet.getSprite(32, 32, 32, 32);
		egg4[2] = entitySheet.getSprite(0, 32, 32, 32);
		egg4[3] = entitySheet.getSprite(32*2, 32, 32, 32);
		
		egg5 = new BufferedImage[4];
		egg5[0] = entitySheet.getSprite(32*3, 32, 32, 32);
		egg5[1] = entitySheet.getSprite(32*4, 32, 32, 32);
		egg5[2] = entitySheet.getSprite(32*3, 32, 32, 32);
		egg5[3] = entitySheet.getSprite(32*5, 32, 32, 32);
		
		
		
		//GEMS
		sapphire = new BufferedImage[2];
		sapphire[0] = entitySheet.getSprite(192, 32, 32, 32);
		sapphire[1] = entitySheet.getSprite(192+32, 32, 32, 32);
		
		ruby = new BufferedImage[2];
		ruby[0] = entitySheet.getSprite(256, 32, 32, 32);
		ruby[1] = entitySheet.getSprite(256+32, 32, 32, 32);
		
		emerald = new BufferedImage[2];
		emerald[0] = entitySheet.getSprite(0, 64, 32, 32);
		emerald[1] = entitySheet.getSprite(32, 64, 32, 32);
		
		diamond = new BufferedImage[2];
		diamond[0] = entitySheet.getSprite(64, 64, 32, 32);
		diamond[1] = entitySheet.getSprite(64+32, 64, 32, 32);
		
	}
}
