package tamagon;

import java.awt.Graphics2D;

public class Spear extends Entity {

	/**
	 * Decides the offset
	 */
	public String type = "";

	/**
	 * Animation variables
	 */
	private int animationFrames = 0, maxFrame = 30, maxIndex = 5, animationIndex = 0, offsetH = 0, offsetY = 0;

	/**
	 * Animation state
	 */
	private String state = "inactive";

	/**
	 * Stabby stab
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Spear(int x, int y, int w, int h) {
		super(x, y, w, h);
		this.shield = true;
	}

	@Override
	public void update() {
		// Do something only if player is near
		int distance = this.distanceFromPlayer(this);
		if (distance >= 256) {
			state = "inactive";
		} else {
			// Damage check
			this.checkPlayerDamage();
			state = "active";

		}

	}

	@Override
	public void render(Graphics2D g) {

		//Dynamic hit box values
		int hitboxOffsetY = 0;
		
		if (state.equals("active")) {

			// Animation frames
			animationFrames++;
			if (animationFrames > maxFrame) {
				animationFrames = 0;
				animationIndex++;
				
				if (animationIndex > maxIndex) {
					animationIndex = 0;
				}
				
			}
			
			//Changing hit box values
			if (animationIndex == 0) {
				hitboxOffsetY = 0;
			} else if (animationIndex == 1 || animationIndex == 5) {
				hitboxOffsetY = 14;
			} else if (animationIndex == 2 || animationIndex == 4) {
				hitboxOffsetY = 14*2;
			} else if (animationIndex == 3) {
				hitboxOffsetY = 14*3;
			}

			// Define the direction which the spear will render
			if (type.equals("botToTop")) {
				offsetH = h * Game.scale;
				offsetY = 0;
				this.setMask(11, 45-hitboxOffsetY, 10, 16);
			} else if (type.equals("topToBot")) {
				offsetH = -(h * Game.scale);
				offsetY = (h * Game.scale);
				this.setMask(11, 3+hitboxOffsetY, 10, 16);
			}
			
			

			// Rendering
			g.drawImage(SpriteLoader.spear[animationIndex], (x * Game.scale - Camera.x),
					(y * Game.scale - Camera.y) + offsetY, w * Game.scale, offsetH, null);

		}

	}

}
