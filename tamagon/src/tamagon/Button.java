package tamagon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Button extends Entity {
	
	/**
	 * Box reference
	 */
	static Entity box;
	
	/**
	 * Button states
	 */
	private boolean on = false;

	/**
	 * Interacts with the Box
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Button(int x, int y, int w, int h) {
		super(x, y, w, h);
		this.setMask(16, 31, 2, 1);
	}
	
	@Override
	public void update() {
		if (box != null) {
			on = this.checkCollision(this, box);
		}
		
		if(on) {
			Door.active = false;
		}else {
			Door.active = true;
		}
	}

	@Override
	public void render(Graphics g) {
		
		//Button state
		BufferedImage sprite = (on) ? SpriteLoader.button_on : SpriteLoader.button_off;
		
		// Draw the sprite
		g.drawImage(sprite, (x * Game.scale - Camera.x), y * Game.scale - Camera.y, w * Game.scale, h * Game.scale, null);
	
	}

}
