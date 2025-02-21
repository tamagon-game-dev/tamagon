package tamagon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Particle extends Entity {

	/**
	 * Particle types: shield
	 */
	public String type = "";

	/**
	 * Particle direction
	 */
	public int direction = 1;

	/**
	 * Animation
	 */
	private int maxFrame, maxIndex, animationFrames = 0, animationIndex = 0, offsetW = 0, offsetX = 0;

	/**
	 * Particle effects
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Particle(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics g) {
		// Setting direction
		if (direction == 1) {
			offsetW = w * Game.scale;
			offsetX = 0;
		} else {
			offsetW = -(w * Game.scale);
			offsetX = (w * Game.scale);
		}

		// Sprite select
		BufferedImage[] sprites = null;
		if (type.equals("shield")) {
			sprites = SpriteLoader.shieldParticle;
			maxIndex = 5;
			maxFrame = 3;
		}

		// Animation frames
		animationFrames++;
		if (animationFrames > maxFrame) {
			animationFrames = 0;
			animationIndex++;
			if (animationIndex > maxIndex) {
				animationIndex = 0;
				this.alive = false;
				Game.entities.remove(this);
			}
		}

		//render
		if (alive) {
			g.drawImage(sprites[animationIndex], (x * Game.scale - Camera.x) + offsetX, y * Game.scale - Camera.y, offsetW,
				h * Game.scale, null);
		}
	}

}
