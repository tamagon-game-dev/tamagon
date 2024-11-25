package tamagon;

import java.awt.Color;
import java.awt.Graphics;

public class Entity {
	
	/**
	 * Entity size & position
	 */
	public int x, y, w, h;

	/**
	 * An entity is a game object
	 * @param x - horizontal coordinate
	 * @param y - vertical coordinate
	 * @param w - width
	 * @param h - height
	 */
	public Entity(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	/**
	 * Entity logic
	 */
	public void update() {
		
	}
	
	/**
	 * Entity graphics
	 * @param g
	 */
	public void render(Graphics g) {
		//Entity hit box and position
		g.setColor(Color.RED);
		g.fillRect(x*Game.scale - Camera.x, y*Game.scale - Camera.y, w*Game.scale, h*Game.scale);
	}
}
