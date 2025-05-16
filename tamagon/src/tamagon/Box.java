package tamagon;

import java.awt.Graphics2D;

public class Box extends Entity {

	/**
	 * 0 = X position 1 = Y position Stores box's initial position
	 */
	public int[] position;

	/**
	 * Can be dragged by the player
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Box(int x, int y, int w, int h) {
		super(x, y, w, h);
		this.setMask(0, 0, 32, 32);
	}

	@Override
	public void update() {
		fallDeathCheck();
		gravity();
		movement();
	}
	
	/**
	 * Makes the box fall
	 */
	private void gravity() {
		if (!checkTileCollision(x, y + Player.gravity)) {
			// Falling
			y += Player.gravity;
		}
	}
	
	/**
	 * Checks if the box is being pushed
	 */
	private void movement() {
		if (this.checkCollisionWithPlayer(this)) {
			if(Game.player.x < this.x && !this.checkTileCollision(x+Player.speed, y)) {
				//Player is pushing from the left
				x += Player.speed;
			}else if (Game.player.x > this.x && !this.checkTileCollision(x-Player.speed, y)) {
				//Player is pushing from the right
				x -= Player.speed;
			}
		}
	}
	
	/**
	 * Checks if box is going out of bounds
	 */
	private void fallDeathCheck() {
		if (y >= Level.levelH * Level.dimension - Level.dimension * 2) alive = false;
	}

	@Override
	public void render(Graphics2D g) {
		//Restore position
		restorePosition();
		
		// Draw the sprite
		g.drawImage(SpriteLoader.box, (x * Game.scale - Camera.x), y * Game.scale - Camera.y, w * Game.scale,
				h * Game.scale, null);
	}

	/**
	 * Restore the initial position
	 */
	private void restorePosition() {
		if (!alive) {
			x = position[0];
			y = position[1];
			alive = true;
		}
	}

}
