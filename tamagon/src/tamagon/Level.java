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
	 * Sprite sheets containing level backgrounds and decorations
	 */
	static Spritesheet backgrounds = new Spritesheet("background"), decorations = new Spritesheet("decorations");

	/**
	 * Backgrounds
	 */
	static BufferedImage sky = backgrounds.getSprite(0, 0, 320, 448), hill = backgrounds.getSprite(0, 448, 320, 56),
			castle = backgrounds.getSprite(0, 504, 320, 112), mountains = backgrounds.getSprite(0, 616, 320, 56),
			clouds = backgrounds.getSprite(0, 672, 320, 112);

	/**
	 * Decorations
	 */
	static BufferedImage cave = decorations.getSprite(0, 0, 416, 288), tree = decorations.getSprite(0, 288, 96, 128);
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
	 * background animation variables
	 */
	static float bg1ScrollX, bg2ScrollX, bg3ScrollX, bg4ScrollX, bgY, bgY1, bgY2, bgY3, bgY4, bgYvalue, bgY1value,
			bgY2value, bgY3value, bgY4value;

	/**
	 * Loads the level in question
	 * 
	 * @param name - level name
	 */
	public Level(String name) {
		// Stops any music if there's one
		if (Game.music && Game.currentSong != null)
			Game.currentSong.stop();

		// Load level
		if (name.equals("test")) {
			levelTest();
		} else if (name.equals("level1")) {
			bgYvalue = 164 * Game.scale;
			bgY1value = 168 * Game.scale;
			bgY2value = 90 * Game.scale;
			bgY3value = 62 * Game.scale;
			bgY4value = -40 * Game.scale;

			bgY = -(bgYvalue);
			bgY1 = bgY1value;
			bgY2 = bgY2value;
			bgY3 = bgY3value;
			bgY4 = bgY4value;
			level1();
		} else {
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

			// updating level's dimensions
			levelW = level.getWidth();
			levelH = level.getHeight();

			// Initialize tiles array
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
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.testTile);
					} else if (currentPixel == 0xFF004810) {
						// THE PLAYER
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Player player = new Player(x * dimension, y * dimension, dimension, dimension);
						player.setMask(10, 5, 16, 27);
						Game.entities.add(player);
						Game.player = player;
					} else {
						// Transparent tile (avoids crazy blur effect)
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
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

			// updating level's dimensions
			levelW = level.getWidth();
			levelH = level.getHeight();

			// Initialize tiles array
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
						// THE PLAYER
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Player player = new Player(x * dimension, y * dimension, dimension, dimension);
						player.setMask(10, 5, 16, 27);
						Game.entities.add(player);
						Game.player = player;
					} else if (currentPixel == 0xFFF8D800) {
						//EGG number one
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Egg egg1 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg1.id = 1;
						egg1.setMask(9, 8, 14, 16);
						Game.entities.add(egg1);	
					}  else if (currentPixel == 0xFFF8D801) {
						//EGG number one
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Egg egg2 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg2.id = 2;
						egg2.setMask(9, 8, 14, 16);
						Game.entities.add(egg2);	
					} else if (currentPixel == 0xFF00FCD8) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Gem diamond = new Gem(x * dimension, y * dimension, dimension, dimension);
						diamond.type = Gem.DIAMOND;
						diamond.setMask(8, 6, 18, 17);
						Game.entities.add(diamond);
					} else if (currentPixel == 0xFF0000F8) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Gem sapphire = new Gem(x * dimension, y * dimension, dimension, dimension);
						sapphire.type = Gem.SAPPHIRE;
						sapphire.setMask(11, 6, 9, 14);
						Game.entities.add(sapphire);
					} else if (currentPixel == 0xFF484848) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Knight knight = new Knight(x * dimension, (y * dimension) - 16, dimension, 48);
						Game.enemies.add(knight);
					} else if (currentPixel == 0xFF785830) {
						// Cavern floor
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.cavernFloor);
					} else if (currentPixel == 0xFF00B468) {
						// Grass
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.grass);
					} else if (currentPixel == 0xFFF0D4A8) {
						// Ground
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.ground);
					} else if (currentPixel == 0xFFFFFFF1) {
						// Invisible wall
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.transparent);
					} else {
						// Transparent tile (avoids crazy blur effect)
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Renders the level
	 * 
	 * @param g - Graphic component
	 */
	public void render(Graphics g) {
		// Rendering optimization
		int xStart = Camera.x / (dimension * Game.scale);
		int yStart = Camera.y / (dimension * Game.scale);

		int xEnd = (xStart + (Game.width / (dimension * Game.scale)));
		int yEnd = yStart + (Game.height / (dimension * Game.scale));

		// Iterating over level's tiles
		for (int x = xStart; x <= xEnd; x++) {
			for (int y = yStart; y <= yEnd; y++) {

				// Preventing array out of bounds
				if (x < 0 || y < 0 || x >= levelW || y >= levelH)
					continue;

				// Grab current tile
				Tile tile = tiles[x + (y * levelW)];

				// Don't render if there's no tile
				if (tile == null)
					continue;

				// Render the tile
				tile.render(g);
			}
		}
	}

	/**
	 * Background image
	 * 
	 * @param g
	 */
	public void renderBackground(Graphics g) {

		// Level 1 background
		if (Game.levelNumber == 1) {

			// Reset horizontal variables
			if (bg1ScrollX <= -Game.width) {
				bg1ScrollX = 0;
			} else if (bg1ScrollX > Game.width) {
				bg1ScrollX = 0;
			}

			if (bg2ScrollX <= -Game.width) {
				bg2ScrollX = 0;
			} else if (bg2ScrollX > Game.width) {
				bg2ScrollX = 0;
			}

			if (bg3ScrollX <= -Game.width) {
				bg3ScrollX = 0;
			} else if (bg3ScrollX > Game.width) {
				bg3ScrollX = 0;
			}

			if (bg4ScrollX <= -Game.width) {
				bg4ScrollX = 0;
			}

			// Reset vertical variables
			if (bgY1 < bgY1value) {
				bgY1 = bgY1value;
			}

			if (bgY2 < bgY2value) {
				bgY2 = bgY2value;
			}

			if (bgY3 < bgY3value) {
				bgY3 = bgY3value;
			}

			if (bgY4 < bgY4value) {
				bgY4 = bgY4value;
			}

			// ---------- BACKGROUNDS ------------ //
			// Draw the sky
			g.drawImage(sky, 0, (int) bgY, 320 * Game.scale, 448 * Game.scale, null);

			// clouds
			g.drawImage(clouds, (int) bg4ScrollX - Game.width, (int) bgY4, 320 * Game.scale, 112 * Game.scale, null);
			g.drawImage(clouds, (int) bg4ScrollX, (int) bgY4, 320 * Game.scale, 112 * Game.scale, null);
			g.drawImage(clouds, (int) bg4ScrollX + Game.width, (int) bgY4, 320 * Game.scale, 112 * Game.scale, null);

			// mountains
			g.drawImage(mountains, (int) bg3ScrollX - Game.width, (int) bgY3, 320 * Game.scale, 56 * Game.scale, null);
			g.drawImage(mountains, (int) bg3ScrollX, (int) bgY3, 320 * Game.scale, 56 * Game.scale, null);
			g.drawImage(mountains, (int) bg3ScrollX + Game.width, (int) bgY3, 320 * Game.scale, 56 * Game.scale, null);

			// castle
			g.drawImage(castle, (int) bg2ScrollX - Game.width, (int) bgY2, 320 * Game.scale, 112 * Game.scale, null);
			g.drawImage(castle, (int) bg2ScrollX, (int) bgY2, 320 * Game.scale, 112 * Game.scale, null);
			g.drawImage(castle, (int) bg2ScrollX + Game.width, (int) bgY2, 320 * Game.scale, 112 * Game.scale, null);

			// hills
			g.drawImage(hill, (int) bg1ScrollX - Game.width, (int) bgY1, 320 * Game.scale, 56 * Game.scale, null);
			g.drawImage(hill, (int) bg1ScrollX, (int) bgY1, 320 * Game.scale, 56 * Game.scale, null);
			g.drawImage(hill, (int) bg1ScrollX + Game.width, (int) bgY1, 320 * Game.scale, 56 * Game.scale, null);

			// BG movement
			if (Player.isMoving && Camera.x != 0 && Camera.x != levelW * dimension * Game.scale - Game.width) {
				if (Player.right) {
					bg1ScrollX -= Player.speed * Game.scale;
					bg2ScrollX -= 1.5f * Game.scale;
					bg3ScrollX -= 1 * Game.scale;
				} else if (Player.left) {
					bg1ScrollX += Player.speed * Game.scale;
					bg2ScrollX += 1.5f * Game.scale;
					bg3ScrollX += 1 * Game.scale;
				}
			}

			bg4ScrollX--;

			// ------------ DECORATIONS --------------//

			// Cave
			g.drawImage(cave, 0 - Camera.x, (dimension * 4) * Game.scale - Camera.y, 416 * Game.scale, 288 * Game.scale,
					null);
			
			// Tree
			g.drawImage(tree, (dimension * 18)*Game.scale - Camera.x, (dimension * 7) * Game.scale - Camera.y, 96 * Game.scale, 128 * Game.scale,
					null);

		}

	}
}
