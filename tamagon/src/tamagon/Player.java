package tamagon;

public class Player extends Entity {
	
	/**
	 * Player controls
	 */
	static boolean right = false, left = false, jump = false, attack = false, canAttack = true;
	
	/**
	 * Player speed, gravity, jump counter and direction;
	 */
	static int speed = 2, gravity = 4, jumpCounter = 0, maxJumps = 3, direction = 1;
	
	/**
	 * Jump duration
	 */
	private int jumpHeight = 64, jumpFrames = 0; 

	/**
	 * Player
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Player(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	//Player's logic 
	public void update() {	
		movement();
		updateCamera();
	}
	
	/**
	 * Controls the player's inputs
	 */
	private void movement() {
		//Check if player is grounded
		if(checkTileCollision(x, y+gravity))
			jumpCounter = 0;
		
		//If player is airborne and its not jumping, then proceed to fall (gravity)
		if(!checkTileCollision(x, y+gravity) && !jump)
			y+=gravity;
		
		//Horizontal movement
		if(right && !checkTileCollision(x+speed, y)) {
			//Moves right while path is clear of colliders
			x+=speed;
			direction = 1;
		}else if (left && !checkTileCollision(x-speed, y)) {
			//Moves left while path is clear of colliders
			x-=speed;
			direction = -1;
		}
		
		//Jump control
		if(jump) {
			//Activates jumping state if there is no ceiling and player can still jump
			if(!checkTileCollision(x, y-gravity) && jumpCounter < maxJumps) {
				y-=gravity;
				jumpFrames+=gravity;
				if(jumpFrames >= jumpHeight) {
					//Disables jump if player reached max jump
					jump = false;
					jumpFrames = 0;
				}
			}else {
				//Disables jump if there is a ceiling preventing the player from jumping
				jump = false;
				jumpFrames = 0;
			}
		}
		
		//Player attacks
		if(attack && canAttack) {
			canAttack = false;
			Fireball fb = new Fireball(x, y, w, h);
			fb.direction = direction;
			Game.entities.add(fb);
		}
		
	}
	
	/**
	 * Updates player's camera
	 */
	private void updateCamera() {
		Camera.x = Camera.clampCamera((this.x * Game.scale), 0, Level.levelW * (Level.dimension * Game.scale) - Game.width);
		Camera.y = Camera.clampCamera((this.y * Game.scale), 0, Level.levelH * (Level.dimension * Game.scale) - Game.height);
	}

}
