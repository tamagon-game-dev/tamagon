package tamagon;

public class Projectile extends Entity {

	/**
	 * Projectile direction
	 */
	public int direction = 1;

	/**
	 * Animation variables
	 */
	public int animationFrames = 0, maxFrame = 5, maxIndex = 0, animationIndex = 0;

	/**
	 * Sprite flip offsets
	 */
	protected int offsetX = 0, offsetW = 0;

	/**
	 * Time until the fire ball explodes
	 */
	protected int timer = 0, maxTimer = 80;

	/**
	 * Enemy projectiles
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Projectile(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		this.checkPlayerDamage();

		// Delete entity after 1.2 seconds
		timer++;
		if (timer >= maxTimer)
			Game.entities.remove(this);

		if (direction == 1 && !checkTileCollision(x + 4, y)) {
			// Fly right
			x += 4;

			// Invert sprite
			offsetW = w * Game.scale;
			offsetX = 0;
		} else if (direction == -1 && !checkTileCollision(x - 4, y)) {
			// Fly left
			x -= 4;

			// Invert sprite
			offsetW = -(w * Game.scale);
			offsetX = (w * Game.scale);
		}else {
			Game.enemies.remove(this);
		}

		//Reset frame data before destruction
		if (!alive) {
			animationFrames = 0;
			animationIndex = 0;
		}
	}
}
