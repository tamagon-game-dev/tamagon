package tamagon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class AnimatedTile {
	/**
	 * Animation sprite
	 */
	private BufferedImage[] sprite;

	/**
	 * Tile coordinates
	 */
	private int x, y;

	/**
	 * Animated tiles
	 */
	static BufferedImage water1 = Level.levelSprites.getSprite(160, 0, 32, 32), water2 = Level.levelSprites.getSprite(160+32, 0, 32, 32),
			water3 = Level.levelSprites.getSprite(160+64, 0, 32, 32);

	/**
	 * Animation variables
	 */
	private int animationFrames = 0, maxFrame = 15, animationIndex = 0, maxIndex = 0;

	/**
	 * Generate an animated tile
	 * 
	 * @param x      - Horizontal position
	 * @param y      - Vertical position
	 * @param sprite - Animation
	 */
	public AnimatedTile(int x, int y, BufferedImage[] sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		maxIndex = sprite.length - 1;
	}

	/**
	 * Renders the tile
	 * 
	 * @param g - Graphic component
	 */
	public void render(Graphics g) {
		// Animation frames
		animationFrames++;
		if (animationFrames > maxFrame) {
			animationFrames = 0;
			animationIndex++;
			if (animationIndex > maxIndex) {
				animationIndex = 0;
			}
		}

		g.drawImage(sprite[animationIndex], x * Game.scale - Camera.x, y * Game.scale - Camera.y,
				Level.dimension * Game.scale, Level.dimension * Game.scale, null);
	}
}
