package tamagon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Archer extends Entity {

	/**
	 * Animation variables & movement variables
	 */
	private int animationFrames = 0, maxFrame = 20, maxIndex = 2, animationIndex = 0, direction = 1;

	/**
	 * Sprite flip offsets
	 */
	private int offsetX = 0, offsetW = 0;

	/**
	 * Entity behavior
	 */
	private String state = "inactive";

	/**
	 * Triggers the death animation
	 */
	private boolean deathAnimation = false;

	/**
	 * Enemy archer
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Archer(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// Do something only if player is near
		int distance = this.distanceFromPlayer(this);
		if (distance >= 256) {
			state = "inactive";
		} else {
			// Reset state to shooting if its inactive
			if (state.equals("inactive")) {
				state = "shooting";
			}

			// Damage check
			this.checkPlayerDamage();

			// Change directions
			direction = (Game.player.x < x) ? -1 : 1;

			// Shoot arrow
			if (animationFrames == 0 && animationIndex == maxIndex) {
				Arrow arrow = new Arrow(x, y+9, 32, 32);
				arrow.direction = direction;
				arrow.setMask(26, 14, 29, 27);
				Game.enemies.add(arrow);
			}

		}

	}

	@Override
	public void render(Graphics g) {
		if (!alive && !deathAnimation) {
			// Reseting frame data
			animationFrames = 0;
			animationIndex = 0;
			maxFrame = 12;
			maxIndex = 6;
			deathAnimation = true;
			if (Game.sfx)
				Game.sounds.hit.play();
		}

		// Updating direction
		if (direction == 1) {
			offsetW = w * Game.scale;
			offsetX = 0;
		} else {
			offsetW = -(w * Game.scale);
			offsetX = (w * Game.scale);
		}

		// Death animation
		if (deathAnimation) {
			g.drawImage(SpriteLoader.archerDead[animationIndex], (x * Game.scale - Camera.x) + offsetX,
					y * Game.scale - Camera.y, offsetW, h * Game.scale, null);
		}

		// Animation frames
		animationFrames++;
		if (animationFrames > maxFrame) {
			animationFrames = 0;
			animationIndex++;
			if (animationIndex > maxIndex) {
				animationIndex = 0;
				if (deathAnimation) {
					Game.enemies.remove(this);
					Player.score += 50;
				}
			}
		}

		// Only render if it's alive
		if (!state.equals("inactive") && alive) {
			BufferedImage[] sprites = SpriteLoader.archerShoot;

			// Rendering the enemy
			g.drawImage(sprites[animationIndex], (x * Game.scale - Camera.x) + offsetX, y * Game.scale - Camera.y,
					offsetW, h * Game.scale, null);
		}
	}

}
