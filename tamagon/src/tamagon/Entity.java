package tamagon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Entity {

	/**
	 * Entity size & position
	 */
	public int x, y, w, h;

	/**
	 * Entity collision mask
	 */
	public int maskX, maskY, maskW, maskH;

	/**
	 * Entity is alive
	 */
	public boolean alive = true;

	/**
	 * Entity has shield against projectiles
	 */
	public boolean shield = false;
	
	/**
	 * Entity can be destroyed? 
	 */
	public boolean indestructible = false;

	/**
	 * An entity is a game object
	 * 
	 * @param x - horizontal coordinate
	 * @param y - vertical coordinate
	 * @param w - width
	 * @param h - height
	 */
	public Entity(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	/**
	 * Entity logic
	 */
	public void update() {

	}

	/**
	 * Entity graphics
	 * 
	 * @param g
	 */
	public void render(Graphics g) {
		showHitBox(g);
	}

	/**
	 * Checks if player is getting collided with another entity
	 * 
	 * @return boolean
	 */
	protected boolean checkCollisionWithPlayer(Entity entity) {
		int playerX = Game.player.x + Game.player.maskX;
		int playerY = Game.player.y + Game.player.maskY;
		int playerW = Game.player.maskW;
		int playerH = Game.player.maskH;
		Rectangle player = new Rectangle(playerX, playerY, playerW, playerH);

		int entityX = entity.x + entity.maskX;
		int entityY = entity.y + entity.maskY;
		int entityW = entity.maskW;
		int entityH = entity.maskH;
		Rectangle target = new Rectangle(entityX, entityY, entityW, entityH);

		return player.intersects(target);
	}

	/**
	 * Setting the collision mask
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public void setMask(int x, int y, int w, int h) {
		this.maskX = x;
		this.maskY = y;
		this.maskW = w;
		this.maskH = h;
	}

	/**
	 * Predicts if the entity will collide with a tile
	 * 
	 * @param nextX - The near future horizontal coordinate
	 * @param nextY - The near future vertical coordinate
	 * @return true if its colliding
	 */
	@SuppressWarnings("unused")
	protected boolean checkTileCollision(int nextX, int nextY) {

		int x1 = nextX / Level.dimension;
		int y1 = nextY / Level.dimension;

		int x2 = (nextX + Level.dimension - 1) / Level.dimension;
		int y2 = nextY / Level.dimension;

		int x3 = nextX / Level.dimension;
		int y3 = (nextY + Level.dimension - 1) / Level.dimension;

		int x4 = (nextX + Level.dimension - 1) / Level.dimension;
		int y4 = (nextY + Level.dimension - 1) / Level.dimension;

		// Verdict
		return (Level.tiles[x1 + (y1 * Level.levelW)] instanceof Collider
				|| Level.tiles[x2 + (y2 * Level.levelW)] instanceof Collider
				|| Level.tiles[x3 + (y3 * Level.levelW)] instanceof Collider
				|| Level.tiles[x4 + (y4 * Level.levelW)] instanceof Collider);
	}

	/**
	 * Predicts if the entity will collide with a secret area
	 * 
	 * @param nextX - The near future horizontal coordinate
	 * @param nextY - The near future vertical coordinate
	 * @return true if its colliding
	 */
	@SuppressWarnings("unused")
	protected boolean checkSecretArea(int nextX, int nextY) {

		int x1 = nextX / Level.dimension;
		int y1 = nextY / Level.dimension;

		int x2 = (nextX + Level.dimension - 1) / Level.dimension;
		int y2 = nextY / Level.dimension;

		int x3 = nextX / Level.dimension;
		int y3 = (nextY + Level.dimension - 1) / Level.dimension;

		int x4 = (nextX + Level.dimension - 1) / Level.dimension;
		int y4 = (nextY + Level.dimension - 1) / Level.dimension;

		// Verdict
		return (Level.tiles2[x1 + (y1 * Level.levelW)] instanceof Tile
				|| Level.tiles2[x2 + (y2 * Level.levelW)] instanceof Tile
				|| Level.tiles2[x3 + (y3 * Level.levelW)] instanceof Tile
				|| Level.tiles2[x4 + (y4 * Level.levelW)] instanceof Tile);
	}

	/**
	 * Returns the distance that the current entity is from the player
	 * 
	 * @param e - Entity
	 * @return distance in pixels (int)
	 */
	protected int distanceFromPlayer(Entity e) {
		int entityX = e.x;
		int entityY = e.y;
		int playerX = Game.player.x;
		int playerY = Game.player.y;

		return (int) Math.sqrt((entityX - playerX) * (entityX - playerX) + (entityY - playerY) * (entityY - playerY));
	}

	/**
	 * Checks if one entity is colliding with other
	 * 
	 * @param entity1 - Entity
	 * @param entity2 - Entity
	 * @return - true if collision occurs
	 */
	protected boolean checkCollision(Entity entity1, Entity entity2) {
		int entity1X = entity1.x + entity1.maskX;
		int entity1Y = entity1.y + entity1.maskY;
		int entity1W = entity1.maskW;
		int entity1H = entity1.maskH;
		Rectangle e1 = new Rectangle(entity1X, entity1Y, entity1W, entity1H);

		int entity2X = entity2.x + entity2.maskX;
		int entity2Y = entity2.y + entity2.maskY;
		int entity2W = entity2.maskW;
		int entity2H = entity2.maskH;
		Rectangle e2 = new Rectangle(entity2X, entity2Y, entity2W, entity2H);

		return e1.intersects(e2);
	}

	/**
	 * Checks if player has been damaged
	 */
	protected void checkPlayerDamage() {
		if (this.checkCollisionWithPlayer(this) && !Player.hurt) {

			// Triggers hurt status
			Player.hurt = true;

			if (Player.eggs.size() > 0) {
				// Kills the egg
				Player.eggs.get(0).alive = false;

				// Reduces egg position if there's any
				if (Player.eggs.size() > 1) {
					Player.eggs.forEach(egg -> {
						egg.position--;
					});
				}

				// Hurt sound effect
				if (Game.sfx)
					Game.sounds.hurt.play();
			} else {
				Game.player.alive = false;
				Player.life--;

				// Stops any music if there's one
				if (Game.music && Game.currentSong != null)
					Game.currentSong.stop();

				// Whomp whomp
				if (Game.music)
					Game.sounds.dead.play();
			}

		}
	}

	/**
	 * Shows objects hit box
	 * 
	 * @param g
	 */
	protected void showHitBox(Graphics g) {
		// Entity hit box and position
		if (alive) {
			g.setColor(new Color(255, 0, 0, 100));
			g.fillRect((x + maskX) * Game.scale - Camera.x, (y + maskY) * Game.scale - Camera.y, maskW * Game.scale,
					maskH * Game.scale);
		}
	}
}
