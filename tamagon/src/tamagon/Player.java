package tamagon;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

	/**
	 * Player controls
	 */
	static boolean right = false, left = false, jump = false, attack = false, canAttack = true, hurt = false;

	/**
	 * Death animation
	 */
	private boolean goUp = true, goDown = false;

	/**
	 * Player's manipulable statistics;
	 */
	static int speed = 2, gravity = 4, direction = 1, life = 3, score = 0;

	/**
	 * Jump duration and animation control
	 */
	private int jumpHeight = 64, jumpFrames = 0, animationFrames = 0, maxFrame = 8, animationIndex = 0, maxIndex = 1,
			offsetW = w * Game.scale, offsetX = 0, hurtFrames = 0, hurtMaxFrames = 120, deadFrames = 0,
			maxDeadFrames = 20;

	/**
	 * Detects motion
	 */
	static boolean isMoving = false;

	/**
	 * Animations
	 */
	private BufferedImage[] sprites = SpriteLoader.playerWalk;

	/**
	 * Store eggs
	 */
	static ArrayList<Egg> eggs;

	/**
	 * Player
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Player(int x, int y, int w, int h) {
		super(x, y, w, h);

		// Initialize egg list
		eggs = new ArrayList<>();
	}

	@Override
	public void update() {
		if (alive) {
			fallDeathCheck();
			movement();
			updateCamera();
		} else {
			deathAnimation();
		}
	}

	/**
	 * Checks if player has fallen
	 */
	private void fallDeathCheck() {
		if (y >= Level.levelH * Level.dimension - Level.dimension * 2) {
			alive = false;
			Player.life--;

			// Stops any music if there's one
			if (Game.music && Game.currentSong != null)
				Game.currentSong.stop();

			// Whomp whomp
			if (Game.music)
				Game.sounds.dead.play();
		}
	}

	/**
	 * Player death animation
	 */
	private void deathAnimation() {
		if (goUp)
			y -= Game.scale;
		if (goDown)
			y += Game.scale;

		deadFrames++;
		if (deadFrames > maxDeadFrames) {
			deadFrames = 0;

			if (goDown) {
				goUp = true;
				goDown = false;
				hurt = false;
				alive = true;
				Game.gameState = "restart";
			}

			if (goUp) {
				maxDeadFrames = 80;
				goUp = false;
				goDown = true;
			}

		}

	}

	@Override
	public void render(Graphics2D g) {
		// Animation control
		if (jump || isMoving || !alive)
			animationFrames++;

		// Animation frames
		if (animationFrames > maxFrame) {
			animationFrames = 0;
			animationIndex++;
			if (animationIndex > maxIndex) {
				animationIndex = 0;
			}
		}

		// Hurt frames
		if (hurt && alive) {
			hurtFrames++;
			if (hurtFrames >= hurtMaxFrames) {
				hurtFrames = 0;
				hurt = false;
			}
		}

		// Death animation
		if (!alive) {
			sprites = SpriteLoader.playerDead;
		}

		// Draw the sprite
		// Check if hurt frames is even so it triggers flickering
		if (hurtFrames % 2 == 0) {
			g.drawImage(sprites[animationIndex], (x * Game.scale - Camera.x) + offsetX, y * Game.scale - Camera.y,
					offsetW, h * Game.scale, null);
		}
	}

	/**
	 * Controls the player's inputs
	 */
	private void movement() {
		
		//Check if it's on secret area 
		Game.secretArea = (this.checkSecretArea(x, y)) ? true : false;

		// Check if player is grounded
		if (checkTileCollision(x, y + gravity)) {

			// Moving sound
			if (isMoving)
				if (Game.sfx)
					Game.sounds.walk.play();
		}

		// If player is airborne and its not jumping, then proceed to fall (gravity)
		if (!checkTileCollision(x, y + gravity) && !jump) {

			// Background animation
			Level.bgY -= 2 * Game.scale;
			Level.bgY1 -= 0.1f * Game.scale;
			Level.bgY2 -= 0.2f * Game.scale;
			Level.bgY3 -= 0.3f * Game.scale;
			Level.bgY4 -= 0.5f * Game.scale;

			// Movement
			y += gravity;
		}

		// Horizontal movement
		if (right && !checkTileCollision(x + speed, y)) {
			// Moves right while path is clear of colliders
			x += speed;
			direction = 1;

			// Animation
			if (!jump && canAttack) {
				sprites = SpriteLoader.playerWalk;
			} else if (!canAttack) {
				sprites = SpriteLoader.playerWalkATK;
			}

			maxFrame = 1;
			isMoving = true;

			// Invert sprite
			offsetW = w * Game.scale;
			offsetX = 0;
		} else if (left && !checkTileCollision(x - speed, y)) {

			// Moves left while path is clear of colliders
			x -= speed;
			direction = -1;

			// Animation
			if (!jump && canAttack) {
				sprites = SpriteLoader.playerWalk;
			} else if (!canAttack) {
				sprites = SpriteLoader.playerWalkATK;
			}

			maxFrame = 1;
			isMoving = true;

			// Invert sprite
			offsetW = -(w * Game.scale);
			offsetX = (w * Game.scale);
		} else {
			isMoving = false;
			if (!canAttack)
				sprites = SpriteLoader.playerWalkATK;
		}

		// Jump control
		if (jump) {

			// Animation
			sprites = (canAttack) ? SpriteLoader.playerJump : SpriteLoader.playerJumpATK;

			// Activates jumping state if there is no ceiling
			if (!checkTileCollision(x, y - gravity)) {

				// Background animation
				Level.bgY += 2 * Game.scale;
				Level.bgY1 += 0.1f * Game.scale;
				Level.bgY2 += 0.2f * Game.scale;
				Level.bgY3 += 0.3f * Game.scale;
				Level.bgY4 += 0.5f * Game.scale;

				// Movement
				y -= gravity;
				jumpFrames += gravity;
				if (jumpFrames >= jumpHeight) {
					// Disables jump if player reached max jump
					jump = false;
					jumpFrames = 0;
				}
			} else {
				// Disables jump if there is a ceiling preventing the player from jumping
				jump = false;
				jumpFrames = 0;
			}
		}

		// Player attacks
		if (attack && canAttack) {
			if (Game.sfx)
				Game.sounds.fire.play();
			canAttack = false;
			Fireball fb = new Fireball(x, y, w, h);
			fb.direction = direction;
			//Collision mask
			if (direction == 1) fb.setMask(17, 11, 15, 4); else fb.setMask(0, 11, 15, 4);
			Game.entities.add(fb);
		}

	}

	/**
	 * Updates player's camera
	 */
	private void updateCamera() {
		int cameraX = (x * Game.scale) - (Game.width / 2 - (Level.dimension * Game.scale) / 2);
		int cameraY = (y * Game.scale) - (Game.height / 2 - (Level.dimension * Game.scale) / 2);

		Camera.x = Camera.clampCamera(cameraX, 0, Level.levelW * (Level.dimension * Game.scale) - Game.width);
		Camera.y = Camera.clampCamera(cameraY, 0, Level.levelH * (Level.dimension * Game.scale) - Game.height);
	}

}
