package tamagon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Egg extends Entity {

	/**
	 * Egg identifier
	 */
	public int id = 0;

	/**
	 * Egg state
	 */
	private String state = "standing";

	/**
	 * Animation frames
	 */
	private int animationFrames = 0;

	/**
	 * Max animation frame
	 */
	private int maxFrame = 10;

	/**
	 * Max animation index
	 */
	private int maxIndex = 3;

	/**
	 * Animation index
	 */
	private int animationIndex = 0;
	
	/**
	 * Egg position
	 */
	private int position = 0;
	
	/**
	 * Egg speed
	 */
	private int speed = 2;

	/**
	 * Egg is life, egg is power
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Egg(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void update() {

		// Checks if player collided
		if (this.checkCollisionWithPlayer(this) && state.equals("standing")) {
			state = "follow";
			Player.eggs++;
			
			//Score distribution
			if (id == 1) {
				Player.score+=100;
			}else if (id == 2) {
				Player.score+=200;
			}
			
			//Position distribution
			if (Player.eggs == 1) {
				position = 1;
			} else if (Player.eggs == 2) {
				position = 2;
			}
			
			//Sound
			if (Game.sfx) Game.sounds.egg.play();
		}

		// Following the player
		if (state.equals("follow")) {
			//Offset
			int offset = w;
			if(position == 2) {
				offset = w*2;
			}
			
			
			//Horizontal movement
			if(x > Game.player.x + offset) {
				x-=speed;
			}else if (x < Game.player.x - offset) {
				x+=speed;
			}
			
			//Vertical movement
			if(y > Game.player.y) {
				y-=speed;
			}else if (y < Game.player.y) {
				y+=speed;
			}
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
		if (id == 1) {
			sprites = SpriteLoader.egg1;
		} else if (id == 2) {
			sprites = SpriteLoader.egg2;
		} else if (id == 3) {
			sprites = SpriteLoader.egg3;
		} else if (id == 4) {
			sprites = SpriteLoader.egg4;
		} else if (id == 5) {
			sprites = SpriteLoader.egg5;
		}

		// Draw the sprite
		g.drawImage(sprites[animationIndex], (x * Game.scale - Camera.x), y * Game.scale - Camera.y, w * Game.scale,
				h * Game.scale, null);
	}

}
