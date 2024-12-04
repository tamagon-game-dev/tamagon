package tamagon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Level {
	
	/**
	 * Sprite sheet containing level tiles
	 */
	static Spritesheet levelSprites = new Spritesheet("level");
	
	/**
	 * Game tiles
	 */
	static Tile[] tiles;
	
	/**
	 * Level width and height
	 */
	static int levelW, levelH;
	
	/**
	 * Level sprite dimension
	 */
	public static int dimension = 32;

	/**
	 * Loads the level in question
	 * 
	 * @param name - level name
	 */
	public Level(String name) {
		//Stops any music if there's one
		if(Game.music && Game.currentSong != null)
			Game.currentSong.stop();
		
		//Load level
		if (name.equals("test")) {
			levelTest();
		} else if (name.equals("level1")) {
			level1();
		}else {
			return;
		}

	}

	/**
	 * Loads the testing level
	 */
	private void levelTest() {
		// Grab map layout
		try {
			// Locating the level's image
			BufferedImage level = ImageIO.read(getClass().getResource("/test.png"));
			
			//updating level's dimensions
			levelW = level.getWidth();
			levelH = level.getHeight();
			
			//Initialize tiles array
			tiles = new Tile[levelW * levelH];

			// Creating an array that will carry image's pixels
			int[] pixels = new int[levelW * levelH];

			// Setting image pixels into the pixels[] array
			level.getRGB(0, 0, levelW, levelH, pixels, 0, levelW);

			// Iterating through the level image's pixels.
			for (int x = 0; x < levelW; x++) {
				for (int y = 0; y < levelH; y++) {

					// Store current pixel color data
					int currentPixel = pixels[x + (y * levelW)];

					if (currentPixel == 0xFF000068) {
						// Test tile
						tiles[x + (y * levelW)] = new Collider(x*dimension, y*dimension, Tile.testTile);
					}else if (currentPixel == 0xFF004810) {
						//THE PLAYER
						tiles[x + (y * levelW)] = new Tile(x*dimension, y*dimension, Tile.transparent);
						Player player = new Player(x*dimension, y*dimension, dimension, dimension);
						Game.entities.add(player);
					}else {
						//Transparent tile (avoids crazy blur effect)
						tiles[x + (y * levelW)] = new Tile(x*dimension, y*dimension, Tile.transparent);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the first level
	 */
	private void level1() {
		// Grab map layout
		try {
			// Locating the level's image
			BufferedImage level = ImageIO.read(getClass().getResource("/level1.png"));
			
			//updating level's dimensions
			levelW = level.getWidth();
			levelH = level.getHeight();
			
			//Initialize tiles array
			tiles = new Tile[levelW * levelH];

			// Creating an array that will carry image's pixels
			int[] pixels = new int[levelW * levelH];

			// Setting image pixels into the pixels[] array
			level.getRGB(0, 0, levelW, levelH, pixels, 0, levelW);

			// Iterating through the level image's pixels.
			for (int x = 0; x < levelW; x++) {
				for (int y = 0; y < levelH; y++) {

					// Store current pixel color data
					int currentPixel = pixels[x + (y * levelW)];

					if (currentPixel == 0xFF004810) {
						//THE PLAYER
						tiles[x + (y * levelW)] = new Tile(x*dimension, y*dimension, Tile.transparent);
						Player player = new Player(x*dimension, y*dimension, dimension, dimension);
						Game.entities.add(player);
					}else {
						//Transparent tile (avoids crazy blur effect)
						tiles[x + (y * levelW)] = new Tile(x*dimension, y*dimension, Tile.transparent);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Renders the level
	 * @param g - Graphic component
	 */
	public void render(Graphics g) {
		//Rendering optimization
		int xStart = Camera.x / (dimension*Game.scale);
		int yStart = Camera.y / (dimension*Game.scale);
		
		int xEnd = xStart + (Game.width / (dimension*Game.scale));
		int yEnd = xStart + (Game.width / (dimension*Game.scale));
		
		//Iterating over level's tiles
		for (int x = xStart; x <= xEnd; x++) {
			for (int y = yStart; y <= yEnd; y++) {
				
				//Preventing array out of bounds
				if(x < 0 || y < 0 || x >= levelW || y >= levelH)
					continue;
				
				//Grab current tile
				Tile tile = tiles[x + (y * levelW)];
				
				//Don't render if there's no tile
				if(tile == null)
					continue;
				
				//Render the tile
				tile.render(g);
			}
		}
	}
}
