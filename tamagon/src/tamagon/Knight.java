package tamagon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Knight extends Entity {

	/**
	 * Animation variables & movement variables
	 */
	private int animationFrames = 0, maxFrame = 10, maxIndex = 1, animationIndex = 0, direction = 1, speed = 1;

	/**
	 * Sprite flip offsets
	 */
	private int offsetX = 0, offsetW = 0;

	/**
	 * Entity behavior
	 */
	private String state = "inactive";

	/**
	 * Enemy knight
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Knight(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void update() {
		
		//Do something only if player is near
		state = (this.distanceFromPlayer(this) <= 320) ? "walking" : "inactive";
		
		
		if(state.equals("walking")) {
			//Walking state
			
			if(direction == 1) {
				// Invert sprite
				offsetW = w * Game.scale;
				offsetX = 0;
				
				//Walk towards right if right is free and there is ground to walk on
				if (!this.checkTileCollision(x+speed, y) && this.checkTileCollision(x+w, y+h)) {
					x+=speed;
				}else {
					//Walk left instead
					direction = -1;
				}
				
			}else {
				// Invert sprite
				offsetW = -(w * Game.scale);
				offsetX = (w * Game.scale);
				
				//Walk towards left if left is free and there is ground to walk on
				if (!this.checkTileCollision(x-speed, y) && this.checkTileCollision(x-w, y+h)) {
					x-=speed;
				}else {
					//Walk right instead
					direction = 1;
				}
			}
		}

	}

	@Override
	public void render(Graphics g) {

		// Only do something if entity is active
		if (!state.equals("inactive")) {
			BufferedImage[] sprites = SpriteLoader.knightWalk;

			// Animation frames
			animationFrames++;
			if (animationFrames > maxFrame) {
				animationFrames = 0;
				animationIndex++;
				if (animationIndex > maxIndex) {
					animationIndex = 0;
				}
			}
			
			//Possible animations
			if (state.equals("walking")) {
				sprites = SpriteLoader.knightWalk;
			}

			//Rendering the enemy
			g.drawImage(sprites[animationIndex], (x * Game.scale - Camera.x) + offsetX, y * Game.scale - Camera.y,
					offsetW, h * Game.scale, null);
		}

	}

}
