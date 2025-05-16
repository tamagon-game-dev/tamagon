package tamagon;

import java.awt.Graphics2D;

public class Arrow extends Projectile {

	/**
	 * Archer's arrow
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Arrow(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics2D g) {

		if (!alive) {
			//Final animation execution
			g.drawImage(SpriteLoader.arrowBurn[animationIndex], (x * Game.scale - Camera.x) + offsetX,
					y * Game.scale - Camera.y, offsetW, h * Game.scale, null);
			
			maxIndex = 2;
			animationFrames++;
			if (animationFrames > maxFrame) {
				animationFrames = 0;
				animationIndex++;
				if (animationIndex > maxIndex) {
					Game.enemies.remove(this);
				}
			}
			
		}else {
			g.drawImage(SpriteLoader.arrow, (x * Game.scale - Camera.x) + offsetX,
					y * Game.scale - Camera.y, offsetW, h * Game.scale, null);
		}
	}

}
