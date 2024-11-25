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
	public static BufferedImage testTile = Level.levelSprites.getSprite(0, 0, Level.dimension, Level.dimension);
	/**
	 * Tile coordinates
	 */
	private int x,y;
	
	/**
	 * Generate a tile
	 * @param x - Horizontal position
	 * @param y - Vertical position
	 * @param sprite - Tile's image
	 */
	public Tile(int x, int  y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	/**
	 * Renders the tile
	 * @param g - Graphic component
	 */
	public void render(Graphics g) {
		g.drawImage(sprite, x*Game.scale, y*Game.scale, Level.dimension * Game.scale, Level.dimension * Game.scale,  null);
	}

}
