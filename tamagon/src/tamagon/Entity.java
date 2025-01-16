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
		// Entity hit box and position
		g.setColor(Color.RED);
		g.fillRect(x * Game.scale - Camera.x, y * Game.scale - Camera.y, w * Game.scale, h * Game.scale);
	}

	
	/**
	 * Checks if player is getting collided with another entity
	 * @return boolean
	 */
	protected boolean checkCollisionWithPlayer(Entity entity) {
		int playerX = Game.player.x + Game.player.maskX;
		int playerY = Game.player.y + Game.player.maskY;
		int playerW = Game.player.w - Game.player.maskW;
		int playerH = Game.player.h - Game.player.maskH;
		Rectangle player = new Rectangle(playerX, playerY, playerW, playerH);
		
		int entityX = entity.x + entity.maskX;
		int entityY = entity.y + entity.maskY;
		int entityW = entity.w - entity.maskW;
		int entityH = entity.h - entity.maskH;
		Rectangle target = new Rectangle(entityX, entityY, entityW, entityH);
		
		return player.intersects(target);
	}
	
	/**
	 * Setting the collision mask
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
	 */

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
		
		//Verdict
		return (Level.tiles[x1 + (y1 * Level.levelW)] instanceof Collider
				|| Level.tiles[x2 + (y2 * Level.levelW)] instanceof Collider
				|| Level.tiles[x3 + (y3 * Level.levelW)] instanceof Collider
				|| Level.tiles[x4 + (y4 * Level.levelW)] instanceof Collider);
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
		
		return (int)Math.sqrt((entityX - playerX) * (entityX - playerX) + (entityY - playerY) * (entityY - playerY));
	}
}
