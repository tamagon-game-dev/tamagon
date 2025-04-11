package tamagon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Door extends Entity{
	
	/**
	 * Determines if door is acting as a, well, door
	 */
	static boolean active = true;
	
	/**
	 * The tile that will appear when the door is open
	 */
	public BufferedImage tile;
	
	/**
	 * Tile Array position
	 */
	public int position;

	/**
	 * Blocks player's path, disappears when the box is touching the button
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Door(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update() {
		if (active) {
			Level.tiles[position] = new Collider(x, y, tile);
		}else {
			Level.tiles[position] = new Tile(x, y, tile);
		}
	}
	
	@Override
	public void render(Graphics g) {
		
		if(active)
			g.drawImage(SpriteLoader.door, (x * Game.scale - Camera.x), y * Game.scale - Camera.y, w * Game.scale, h * Game.scale, null);
	}

}
