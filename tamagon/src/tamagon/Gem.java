package tamagon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Gem extends Entity{
	
	/**
	 * Gems type
	 */
	public static final int SAPPHIRE = 1, RUBY = 2, EMERALD = 3, DIAMOND = 4;
	
	/**
	 * Gem type
	 */
	public int type = 0;
	
	/**
	 * Animation frames
	 */
	private int animationFrames = 0;

	/**
	 * Max animation frame
	 */
	private int maxFrame = 5;

	/**
	 * Max animation index
	 */
	private int maxIndex = 1;

	/**
	 * Animation index
	 */
	private int animationIndex = 0;

	/**
	 * Money
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Gem(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	@Override
	public void update() {
		if (this.checkCollisionWithPlayer(this)){
			
			//Gem type
			if (type == SAPPHIRE){
				Player.score+=10;
				if (Game.sfx) Game.sounds.sapphire.play();
			} else if (type == RUBY) {
				Player.score+=20;
				if (Game.sfx) Game.sounds.ruby.play();
			} else if (type == EMERALD) {
				Player.score+=30;
				if (Game.sfx) Game.sounds.emerald.play();
			} else if (type == DIAMOND) {
				Player.score+=40;
				if (Game.sfx) Game.sounds.diamond.play();
			}
			
			Game.entities.remove(this);
			
		}
		
	}
	
	
	@Override
	public void render(Graphics g) {
		BufferedImage[] sprites = null;

		// Animation frames
		animationFrames++;
		if (animationFrames > maxFrame) {
			animationFrames = 0;
			animationIndex++;
			if (animationIndex > maxIndex) {
				animationIndex = 0;
			}
		}

		// Select sprite to draw
		if (type == Gem.SAPPHIRE) {
			sprites = SpriteLoader.sapphire;
		} else if (type == Gem.RUBY) {
			sprites = SpriteLoader.ruby;
		} else if (type == Gem.EMERALD) {
			sprites = SpriteLoader.emerald;
		} else if (type == Gem.DIAMOND) {
			sprites = SpriteLoader.diamond;
		}

		// Draw the sprite
		g.drawImage(sprites[animationIndex], (x * Game.scale - Camera.x), y * Game.scale - Camera.y, w * Game.scale,
				h * Game.scale, null);
	}

}
