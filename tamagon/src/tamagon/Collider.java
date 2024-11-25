package tamagon;

import java.awt.image.BufferedImage;

public class Collider extends Tile {

	/**
	 * Generates a tile that collides with the game's entities
	 * @param x
	 * @param y
	 * @param sprite
	 */
	public Collider(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}
}
