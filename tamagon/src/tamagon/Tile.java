package tamagon;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Tile {

	/**
	 * Tile sprite
	 */
	private BufferedImage sprite;

	/**
	 * Tile images
	 */
	public static BufferedImage testTile = Level.levelSprites.getSprite(0, 0, Level.dimension, Level.dimension),
			transparent = Level.levelSprites.getSprite(Level.dimension * 1, 0, Level.dimension, Level.dimension),
			cavernFloor = Level.levelSprites.getSprite(Level.dimension * 2, 0, Level.dimension, Level.dimension),
			grass = Level.levelSprites.getSprite(Level.dimension * 3, 0, Level.dimension, Level.dimension),
			ground = Level.levelSprites.getSprite(Level.dimension * 4, 0, Level.dimension, Level.dimension),
			underwater = Level.levelSprites.getSprite(Level.dimension * 8, 0, Level.dimension, Level.dimension),
			goldfloor_ul = Level.levelSprites.getSprite(288, 0, Level.dimension, Level.dimension),
			goldfloor_um = Level.levelSprites.getSprite(256, 32, Level.dimension, Level.dimension),
			goldfloor_ur = Level.levelSprites.getSprite(288, 96, Level.dimension, Level.dimension),
			goldfloor_mr = Level.levelSprites.getSprite(288, 96+32, Level.dimension, Level.dimension),
			goldfloor_lr = Level.levelSprites.getSprite(288, 96+64, Level.dimension, Level.dimension),
			goldfloor_lm = Level.levelSprites.getSprite(256, 96, Level.dimension, Level.dimension),
			goldfloor_ll = Level.levelSprites.getSprite(288, 64, Level.dimension, Level.dimension),
			goldfloor_ml = Level.levelSprites.getSprite(288, 32, Level.dimension, Level.dimension),
			stoneblock = Level.levelSprites.getSprite(256, 64, Level.dimension, Level.dimension),
			stoneblock2 = Level.levelSprites.getSprite(288, 192, Level.dimension, Level.dimension),
			castleTowerL = Level.levelSprites.getSprite(0, 32, Level.dimension, Level.dimension),
			castleTowerM = Level.levelSprites.getSprite(32, 32, Level.dimension, Level.dimension),
			castleTowerR = Level.levelSprites.getSprite(64, 32, Level.dimension, Level.dimension),
			castleTowerWindow = Level.levelSprites.getSprite(96, 32, Level.dimension, Level.dimension),
			castleTiles = Level.levelSprites.getSprite(128, 32, Level.dimension, Level.dimension);

	/**
	 * Tile coordinates
	 */
	private int x, y;

	/**
	 * Generate a tile
	 * 
	 * @param x      - Horizontal position
	 * @param y      - Vertical position
	 * @param sprite - Tile's image
	 */
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}

	/**
	 * Renders the tile
	 * 
	 * @param g - Graphic component
	 */
	public void render(Graphics g) {
		g.drawImage(sprite, x * Game.scale - Camera.x, y * Game.scale - Camera.y, Level.dimension * Game.scale, Level.dimension * Game.scale,
				null);
	}

}
