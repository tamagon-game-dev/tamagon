package tamagon;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Tile {

	/**
	 * Tile sprite
	 */
	private BufferedImage sprite;

	/**
	 * Tile images
	 */
	public static BufferedImage testTile = Level.levelSprites.getSprite(0, 0, Level.dimension, Level.dimension),
			transparent = Level.levelSprites.getSprite(Level.dimension * 1, 0, Level.dimension, Level.dimension),
			cavernFloor = Level.levelSprites.getSprite(Level.dimension * 2, 0, Level.dimension, Level.dimension),
			grass = Level.levelSprites.getSprite(Level.dimension * 3, 0, Level.dimension, Level.dimension),
			ground = Level.levelSprites.getSprite(Level.dimension * 4, 0, Level.dimension, Level.dimension),
			underwater = Level.levelSprites.getSprite(Level.dimension * 8, 0, Level.dimension, Level.dimension),
			goldfloor_ul = Level.levelSprites.getSprite(288, 0, Level.dimension, Level.dimension),
			goldfloor_um = Level.levelSprites.getSprite(256, 32, Level.dimension, Level.dimension),
			goldfloor_ur = Level.levelSprites.getSprite(288, 96, Level.dimension, Level.dimension),
			goldfloor_mr = Level.levelSprites.getSprite(288, 96+32, Level.dimension, Level.dimension),
			goldfloor_lr = Level.levelSprites.getSprite(288, 96+64, Level.dimension, Level.dimension),
			goldfloor_lm = Level.levelSprites.getSprite(256, 96, Level.dimension, Level.dimension),
			goldfloor_ll = Level.levelSprites.getSprite(288, 64, Level.dimension, Level.dimension),
			goldfloor_ml = Level.levelSprites.getSprite(288, 32, Level.dimension, Level.dimension),
			stoneblock = Level.levelSprites.getSprite(256, 64, Level.dimension, Level.dimension),
			stoneblock2 = Level.levelSprites.getSprite(288, 192, Level.dimension, Level.dimension),
			castleTowerL = Level.levelSprites.getSprite(0, 32, Level.dimension, Level.dimension),
			castleTowerM = Level.levelSprites.getSprite(32, 32, Level.dimension, Level.dimension),
			castleTowerR = Level.levelSprites.getSprite(64, 32, Level.dimension, Level.dimension),
			castleTowerWindow = Level.levelSprites.getSprite(96, 32, Level.dimension, Level.dimension),
			castleTowerTL = Level.levelSprites.getSprite(128, 32, Level.dimension, Level.dimension),
			castleTowerTM = Level.levelSprites.getSprite(128+32, 32, Level.dimension, Level.dimension),
			castleTowerTR = Level.levelSprites.getSprite(128+64, 32, Level.dimension, Level.dimension),
			castleBlockL  = Level.levelSprites.getSprite(0, 64, Level.dimension, Level.dimension),
			castleBlockM  = Level.levelSprites.getSprite(32, 64, Level.dimension, Level.dimension),
			castleBlockR  = Level.levelSprites.getSprite(64, 64, Level.dimension, Level.dimension),
			castleTower = Level.levelSprites.getSprite(224, 32, Level.dimension, Level.dimension),
			battlementTop = Level.levelSprites.getSprite(96, 64, Level.dimension, Level.dimension),
			battlementBot = Level.levelSprites.getSprite(96, 96, Level.dimension, Level.dimension),
			rampartTop = Level.levelSprites.getSprite(96+32, 64, Level.dimension, Level.dimension),
			rampartBot = Level.levelSprites.getSprite(96+32, 96, Level.dimension, Level.dimension),
			rampartWindowTL = Level.levelSprites.getSprite(160, 64, Level.dimension, Level.dimension),
			rampartWindowTR = Level.levelSprites.getSprite(160+32, 64, Level.dimension, Level.dimension),
			rampartWindowBL = Level.levelSprites.getSprite(160, 96, Level.dimension, Level.dimension),
			rampartWindowBR = Level.levelSprites.getSprite(160+32, 96, Level.dimension, Level.dimension),
			battlement = Level.levelSprites.getSprite(224, 64, Level.dimension, Level.dimension),
			woodWall = Level.levelSprites.getSprite(0, 96, Level.dimension, Level.dimension),
			woodFloorUL = Level.levelSprites.getSprite(0, 128, Level.dimension, Level.dimension),
			woodFloorUM = Level.levelSprites.getSprite(32*1, 128, Level.dimension, Level.dimension),
			woodFloorUR = Level.levelSprites.getSprite(32*2, 128, Level.dimension, Level.dimension),
			woodFloorL = Level.levelSprites.getSprite(0, 128+32, Level.dimension, Level.dimension),
			woodFloorR = Level.levelSprites.getSprite(32*2, 128+32, Level.dimension, Level.dimension),
			woodFloorBL = Level.levelSprites.getSprite(0, 128+64, Level.dimension, Level.dimension),
			woodFloorBM = Level.levelSprites.getSprite(32*1, 128+64, Level.dimension, Level.dimension),
			woodFloorBR = Level.levelSprites.getSprite(32*2, 128+64, Level.dimension, Level.dimension),
			woodFloorCBR = Level.levelSprites.getSprite(128, 160, Level.dimension, Level.dimension),
			woodFloorCUL = Level.levelSprites.getSprite(96, 128, Level.dimension, Level.dimension),
			woodTiles = Level.levelSprites.getSprite(32, 160, Level.dimension, Level.dimension);

	/**
	 * Tile coordinates
	 */
	private int x, y;

	/**
	 * Generate a tile
	 * 
	 * @param x      - Horizontal position
	 * @param y      - Vertical position
	 * @param sprite - Tile's image
	 */
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}

	/**
	 * Renders the tile
	 * 
	 * @param g - Graphic component
	 */
	public void render(Graphics g) {
		g.drawImage(sprite, x * Game.scale - Camera.x, y * Game.scale - Camera.y, Level.dimension * Game.scale, Level.dimension * Game.scale,
				null);
	}

}
