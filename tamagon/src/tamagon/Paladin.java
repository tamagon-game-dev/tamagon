package tamagon;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Paladin extends Entity {

	/**
	 * Animation variables & movement variables
	 */
	private int animationFrames = 0, maxFrame = 20, maxIndex = 1, animationIndex = 0, direction = 1, timer = 60*3;

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
	 * Enemy paladin
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Paladin(int x, int y, int w, int h) {
		super(x, y, w, h);

	}

	@Override
	public void update() {
		// Do something only if player is near
		int distance = this.distanceFromPlayer(this);
		if (distance >= 256) {
			//Reset timer
			timer = 60*3;
			state = "inactive";
		} else {
			//timer
			if (state.equals("shield") && timer > 0 ) timer--;
			
			//state change
			state = (timer == 0 || state.equals("attack")) ? "attack" : "shield";
			
			// Damage check
			this.checkPlayerDamage();

			// Change directions
			direction = (Game.player.x < x) ? -1 : 1;
			
			if (state.equals("attack")) {
				//Animation reset
				if (timer == 0) {
					animationFrames = 0;
					animationIndex = 0;
				}
				
				//timer increment
				timer++;
				
				//Launch attack
				if (animationIndex == 1 && animationFrames == 0) {
					//Dagger sfx
					if (Game.sfx) Game.sounds.dagger.play();
					Dagger dagger = new Dagger(x, y+3, 32, 32);
					dagger.indestructible = true;
					dagger.setMask(11, 14, 11, 5);
					dagger.direction = direction;
					Game.enemies.add(dagger);
				}
				
				
				//Reset variables after attack animation is finished
				if (timer == 60) {
					
					state = "shield";
					animationFrames = 0;
					animationIndex = 0;
					timer = 60*3;
				}
				
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		if (!alive && !deathAnimation) {
			// Reseting frame data
			animationFrames = 0;
			animationIndex = 0;
			maxFrame = 10;
			maxIndex = 7;
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
			g.drawImage(SpriteLoader.paladinDeath[animationIndex], (x * Game.scale - Camera.x) + offsetX,
					y * Game.scale - Camera.y, offsetW, h * Game.scale, null);
		}

		// Only render if it's alive
		if (!state.equals("inactive") && alive) {
			BufferedImage[] sprites = SpriteLoader.paladinShield;

			if (state.equals("shield")) {
				sprites = SpriteLoader.paladinShield;
				maxIndex = 1;
				shield = true;
			} else if (state.equals("attack")) {
				sprites = SpriteLoader.paladinAttack;
				maxIndex = 2;
				shield = false;
			}

			// Rendering the enemy
			if(animationIndex < sprites.length) {
				g.drawImage(sprites[animationIndex], (x * Game.scale - Camera.x) + offsetX, y * Game.scale - Camera.y,
					offsetW, h * Game.scale, null);
			}
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
					Player.score += 100;
					Level.enemiesDefeated++;
				}
			}
		}

	}

}
