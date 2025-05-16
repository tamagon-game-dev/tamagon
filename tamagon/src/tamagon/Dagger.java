package tamagon;

import java.awt.Graphics2D;

public class Dagger extends Projectile {

	/**
	 * Throw by the Paladin
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Dagger(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void render(Graphics2D g) {
		g.drawImage(SpriteLoader.dagger, (x * Game.scale - Camera.x) + offsetX,
				y * Game.scale - Camera.y, offsetW, h * Game.scale, null);
	}

}
