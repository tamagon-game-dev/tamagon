package tamagon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Knight extends Entity {

	/**
	 * Animation variables & movement variables
	 */
	private int animationFrames = 0, maxFrame = 10, maxIndex = 1, animationIndex = 0, direction = 1, speed = 1,
			stoppedFrames = 0, maxStoppedFrames = 30;

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

		// Do something only if player is near
		int distance = this.distanceFromPlayer(this);
		if (distance >= 256) {
			state = "inactive";
		} else {

			// Reset state to walking if its inactive
			if (state.equals("inactive")) {
				state = "walking";
			}
			
			//Damage check
			this.checkPlayerDamage();

		}

		if (state.equals("walking")) {
			// Walking effect
			if (Game.sfx) {

				// Dynamic sound
				if (distance >= 224 - 32) {
					Game.sounds.knightWalk.setVolume(0.1f);
				} else if (distance >= 224 - 32 * 2) {
					Game.sounds.knightWalk.setVolume(0.2f);
				} else if (distance >= 224 - 32 * 3) {
					Game.sounds.knightWalk.setVolume(0.4f);
				} else if (distance >= 224 - 32 * 4) {
					Game.sounds.knightWalk.setVolume(0.6f);
				} else if (distance >= 224 - 32 * 5) {
					Game.sounds.knightWalk.setVolume(0.8f);
				} else if (distance >= 224 - 32 * 6) {
					Game.sounds.knightWalk.setVolume(1f);
				}

				Game.sounds.knightWalk.play();
			}

			// Walking state
			// Slowing down
			speed = 1;
			maxFrame = 10;

			// Walking sprite width reset
			w = 32;

			if (direction == 1) {

				// Walk towards right if right is free and there is ground to walk on
				if (!this.checkTileCollision(x + speed, y) && this.checkTileCollision(x + w, y + h)) {
					x += speed;
				} else {
					// Walk left instead
					direction = -1;
				}

				// Check if player is on sight
				if (Game.player.y <= y + h && Game.player.y >= y && Game.player.x > x) {
					state = "spotted";
				}
			} else {

				// Walk towards left if left is free and there is ground to walk on
				if (!this.checkTileCollision(x - speed, y) && this.checkTileCollision(x - w, y + h)) {
					x -= speed;
				} else {
					// Walk right instead
					direction = 1;
				}

				// Check if player is on sight
				if (Game.player.y <= y + h && Game.player.y >= y && Game.player.x < x) {
					state = "spotted";
				}
			}
		} else if (state.equals("spotted")) {
			// Spotted sprite width reset
			w = 32;

			// Found the player! Begin surprised state
			stoppedFrames++;
			if (stoppedFrames > maxStoppedFrames) {
				stoppedFrames = 0;
				state = "attack";
			}
		} else if (state.equals("attack")) {
			// Running effect
			if (Game.sfx) {

				// Dynamic sound
				if (distance >= 224 - 32) {
					Game.sounds.knightRun.setVolume(0.1f);
				} else if (distance >= 224 - 32 * 2) {
					Game.sounds.knightRun.setVolume(0.2f);
				} else if (distance >= 224 - 32 * 3) {
					Game.sounds.knightRun.setVolume(0.4f);
				} else if (distance >= 224 - 32 * 4) {
					Game.sounds.knightRun.setVolume(0.6f);
				} else if (distance >= 224 - 32 * 5) {
					Game.sounds.knightRun.setVolume(0.8f);
				} else if (distance >= 224 - 32 * 6) {
					Game.sounds.knightRun.setVolume(1f);
				}

				Game.sounds.knightRun.play();
			}

			// Speeding up
			speed = 4;
			maxFrame = 5;

			// Attacking sprite is wider
			w = 42;

			if (Game.player.x > x + speed && !this.checkTileCollision(x + speed, y)
					&& this.checkTileCollision(x + w, y + h)) {
				// Chase the player towards right
				x += speed;

				// Updating direction
				direction = 1;
			} else if (Game.player.x < x - speed && !this.checkTileCollision(x - speed, y)
					&& this.checkTileCollision(x - w, y + h)) {
				// Chase the player towards left
				x -= speed;

				// Updating direction
				direction = -1;
			}

			// Losing player from view
			if (Game.player.y < y - h || Game.player.y > y + h) {
				state = "doubt";
			}

		} else if (state.equals("doubt")) {
			// Doubt sprite width reset
			w = 32;

			// Lost the player! Begin doubt state
			stoppedFrames++;
			if (stoppedFrames > maxStoppedFrames) {
				stoppedFrames = 0;
				state = "walking";
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
			maxIndex = 8;
			deathAnimation = true;
			w = 32;
			if (Game.sfx) Game.sounds.hit.play();
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
			g.drawImage(SpriteLoader.knightDeath[animationIndex], (x * Game.scale - Camera.x) + offsetX,
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
					Player.score+=50;
				}
			}
		}

		// Only do something if entity is active
		if (!state.equals("inactive") && alive) {
			BufferedImage[] sprites = SpriteLoader.knightWalk;

			// Possible animations
			if (state.equals("walking")) {
				sprites = SpriteLoader.knightWalk;
			} else if (state.equals("spotted")) {
				sprites = SpriteLoader.knightDetect;
			} else if (state.equals("attack")) {
				sprites = SpriteLoader.knightAttack;
			} else if (state.equals("doubt")) {
				sprites = SpriteLoader.knightDoubt;
			}

			// Rendering the enemy
			g.drawImage(sprites[animationIndex], (x * Game.scale - Camera.x) + offsetX, y * Game.scale - Camera.y,
					offsetW, h * Game.scale, null);
		}

	}

}
