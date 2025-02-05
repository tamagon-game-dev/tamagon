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
	static BufferedImage[] playerWalk, playerJump, playerWalkATK, playerJumpATK, fireball, fireballCollide, playerDead;
	
	/**
	 * Eggs animation
	 */
	static BufferedImage[] egg1, egg2, egg3, egg4, egg5;
	
	/**
	 * Gems animation
	 */
	static BufferedImage[] sapphire, ruby, emerald, diamond;
	
	/**
	 * Enemy: Knight
	 */
	static BufferedImage[] knightWalk, knightDetect, knightAttack, knightDoubt, knightDeath;
	
	/**
	 * Enemy: Archer
	 */
	static BufferedImage[] archerShoot, archerDead, arrowBurn;
	
	/**
	 * Archer's arrow
	 */
	static BufferedImage arrow;
	
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
		
		playerDead = new BufferedImage[2];
		playerDead[0] = playerSheet.getSprite(0, 192, 32, 32);
		playerDead[1] = playerSheet.getSprite(32, 192, 32, 32);
		
		
		//EGGS
		egg1 = new BufferedImage[5];
		egg1[0] = entitySheet.getSprite(0, 0, 32, 32);
		egg1[1] = entitySheet.getSprite(32, 0, 32, 32);
		egg1[2] = entitySheet.getSprite(0, 0, 32, 32);
		egg1[3] = entitySheet.getSprite(32*2, 0, 32, 32);
		egg1[4] = entitySheet.getSprite(128, 64, 32, 32);
		
		egg2 = new BufferedImage[5];
		egg2[0] = entitySheet.getSprite(32*3, 0, 32, 32);
		egg2[1] = entitySheet.getSprite(32*4, 0, 32, 32);
		egg2[2] = entitySheet.getSprite(32*3, 0, 32, 32);
		egg2[3] = entitySheet.getSprite(32*5, 0, 32, 32);
		egg2[4] = entitySheet.getSprite(128+32, 64, 32, 32);
		
		egg3 = new BufferedImage[5];
		egg3[0] = entitySheet.getSprite(32*6, 0, 32, 32);
		egg3[1] = entitySheet.getSprite(32*7, 0, 32, 32);
		egg3[2] = entitySheet.getSprite(32*6, 0, 32, 32);
		egg3[3] = entitySheet.getSprite(32*8, 0, 32, 32);
		egg3[4] = entitySheet.getSprite(128+(32*2), 64, 32, 32);
		
		egg4 = new BufferedImage[5];
		egg4[0] = entitySheet.getSprite(0, 32, 32, 32);
		egg4[1] = entitySheet.getSprite(32, 32, 32, 32);
		egg4[2] = entitySheet.getSprite(0, 32, 32, 32);
		egg4[3] = entitySheet.getSprite(32*2, 32, 32, 32);
		egg4[4] = entitySheet.getSprite(128+(32*3), 64, 32, 32);
		
		egg5 = new BufferedImage[5];
		egg5[0] = entitySheet.getSprite(32*3, 32, 32, 32);
		egg5[1] = entitySheet.getSprite(32*4, 32, 32, 32);
		egg5[2] = entitySheet.getSprite(32*3, 32, 32, 32);
		egg5[3] = entitySheet.getSprite(32*5, 32, 32, 32);
		egg5[4] = entitySheet.getSprite(128+(32*4), 64, 32, 32);
		
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
		
		//--------------------ENEMIES----------------------//
		//KNIGHT
		knightWalk = new BufferedImage[2];
		knightWalk[0] = entitySheet.getSprite(0, 96, 32, 48);
		knightWalk[1] = entitySheet.getSprite(32, 96, 32, 48);
		
		knightDetect = new BufferedImage[2];
		knightDetect[0] = entitySheet.getSprite(32*2, 96, 32, 48);
		knightDetect[1] = entitySheet.getSprite(32*3, 96, 32, 48);
		
		knightAttack = new BufferedImage[2];
		knightAttack[0] = entitySheet.getSprite(128, 96, 42, 48);
		knightAttack[1] = entitySheet.getSprite(128+42, 96, 42, 48);
		
		knightDoubt = new BufferedImage[2];
		knightDoubt[0] = entitySheet.getSprite(212, 96, 32, 48);
		knightDoubt[1] = entitySheet.getSprite(212+32, 96, 32, 48);
		
		knightDeath = new BufferedImage[9];
		knightDeath[0] = entitySheet.getSprite(0, 144, 32, 48);
		knightDeath[1] = entitySheet.getSprite(32, 144, 32, 48);
		knightDeath[2] = entitySheet.getSprite(32*2, 144, 32, 48);
		knightDeath[3] = entitySheet.getSprite(32*3, 144, 32, 48);
		knightDeath[4] = entitySheet.getSprite(32*4, 144, 32, 48);
		knightDeath[5] = entitySheet.getSprite(32*5, 144, 32, 48);
		knightDeath[6] = entitySheet.getSprite(32*6, 144, 32, 48);
		knightDeath[7] = entitySheet.getSprite(32*7, 144, 32, 48);
		knightDeath[8] = entitySheet.getSprite(32*7, 144, 32, 48);
		
		//Archer
		archerShoot = new BufferedImage[3];
		archerShoot[0] = entitySheet.getSprite(0, 192, 32, 48);
		archerShoot[1] = entitySheet.getSprite(32, 192, 32, 48);
		archerShoot[2] = entitySheet.getSprite(64, 192, 32, 48);
		
		archerDead = new BufferedImage[7];
		archerDead[0] = entitySheet.getSprite(0, 240, 32, 48);
		archerDead[1] = entitySheet.getSprite(32, 240, 32, 48);
		archerDead[2] = entitySheet.getSprite(32*2, 240, 32, 48);
		archerDead[3] = entitySheet.getSprite(32*3, 240, 32, 48);
		archerDead[4] = entitySheet.getSprite(32*4, 240, 32, 48);
		archerDead[5] = entitySheet.getSprite(32*5, 240, 32, 48);
		archerDead[6] = entitySheet.getSprite(32*6, 240, 32, 48);
		
		arrow = entitySheet.getSprite(288, 64, 32, 32);
		arrowBurn = new BufferedImage[3];
		arrowBurn[0] = entitySheet.getSprite(288, 64+32, 32, 32);
		arrowBurn[1] = entitySheet.getSprite(288, 64+64, 32, 32);
		arrowBurn[2] = entitySheet.getSprite(288, 64+96, 32, 32);
	}
}
