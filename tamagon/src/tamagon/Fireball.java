package tamagon;

public class Fireball extends Entity{
	
	/**
	 * Fire ball direction
	 */
	public int direction = 1;

	/**
	 * Player shoots these
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Fireball(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update() {
		if (direction == 1 && !checkTileCollision(x+4,y)) {
			//Fly right
			x+=4;
		} else if (direction == -1 && !checkTileCollision(x-4,y)) {
			//Fly left
			x-=4;
		} else {
			//Destroy self
			Player.canAttack = true;
			Game.entities.remove(this);
		}
	}
}
