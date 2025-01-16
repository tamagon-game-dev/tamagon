package tamagon;

import java.awt.Graphics;
import java.awt.Image;

public class Fireball extends Entity {

	/**
	 * Fire ball direction
	 */
	public int direction = 1;
	/**
	 * Fire ball sprite
	 */
	private Image[] sprites = SpriteLoader.fireball;

	/**
	 * Sprite flip offsets
	 */
	private int offsetX = 0, offsetW = 0;
	
	/**
	 * Animation variables
	 */
	private int animationFrames = 0, maxFrame = 30, maxIndex = 1, animationIndex = 0;
	
	/**
	 * Plays before exploding
	 */
	private boolean finalAnimation = false;
	
	/**
	 * Time until the fire ball explodes
	 */
	private int timer = 0, maxTimer = 80;

	/**
	 * Player shoots these
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Fireball(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		
		//Delete entity after 1.2 seconds
		timer++;
		if(timer >= maxTimer) {
			Player.canAttack = true;
			Game.entities.remove(this);
		}
		
		if (direction == 1 && !checkTileCollision(x + 4, y)) {
			// Fly right
			x += 4;

			// Invert sprite
			offsetW = w * Game.scale;
			offsetX = 0;
		} else if (direction == -1 && !checkTileCollision(x - 4, y)) {
			// Fly left
			x -= 4;

			// Invert sprite
			offsetW = -(w * Game.scale);
			offsetX = (w * Game.scale);
		} else {
			// Destroy self
			if (!finalAnimation) {
				finalAnimation = true;
				animationFrames = 0;
				animationIndex = 0;
				sprites = SpriteLoader.fireballCollide;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		animationFrames++;
		
		// Animation frames
		if (animationFrames > maxFrame) {
			animationFrames = 0;
			animationIndex++;
			if (animationIndex > maxIndex) {
				//Exploding animation
				if(finalAnimation) {
					if (Game.sfx) Game.sounds.hit.play();
					Player.canAttack = true;
					Game.entities.remove(this);
				}
				animationIndex = 0;
			}
		}

		g.drawImage(sprites[animationIndex], (x * Game.scale - Camera.x) + offsetX, y * Game.scale - Camera.y, offsetW,
				h * Game.scale, null);
	}
}
