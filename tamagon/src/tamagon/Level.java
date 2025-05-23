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
			clouds = backgrounds.getSprite(0, 672, 320, 112),
			castleInside = backgrounds.getSprite(320, 0, 320, 448);

	/**
	 * Decorations
	 */
	static BufferedImage cave = decorations.getSprite(0, 0, 416, 288), tree = decorations.getSprite(0, 288, 96, 128);

	/**
	 * Game tiles
	 */
	static Tile[] tiles, tiles2;

	/**
	 * Animated tiles
	 */
	static AnimatedTile[] animatedTiles;

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
	static float bgScrollX, bg1ScrollX, bg2ScrollX, bg3ScrollX, bg4ScrollX, bgY, bgY1, bgY2, bgY3, bgY4, bgYvalue, bgY1value,
			bgY2value, bgY3value, bgY4value;

	/**
	 * Enemies defeated counts
	 */
	static int enemiesDefeated = 0;

	/**
	 * Loads the level in question
	 * 
	 * @param name - level name
	 */
	public Level(String name) {
		// Resets enemies defeated count
		enemiesDefeated = 0;

		// Stops any music if there's one
		if (Game.music && Game.currentSong != null)
			Game.currentSong.stop();

		// Background animation position
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

		// Load level
		if (name.equals("test")) {
			levelTest();
		} else if (name.equals("level1")) {
			level1();

			// Play music after level is loaded
			if (Game.music) {
				Game.currentSong = Game.sounds.world1;
				Game.currentSong.setVolume(0.5f);
				Game.currentSong.loop();
			}
		} else if (name.equals("level2")) {
			level2();

			// Play music after level is loaded
			if (Game.music) {
				Game.currentSong = Game.sounds.world1;
				Game.currentSong.setVolume(0.5f);
				Game.currentSong.loop();
			}
		} else if (name.equals("level3")) {
			bgYvalue = (164 - 48) * Game.scale;
			bgY = -(bgYvalue);
			level3();

			// Play music after level is loaded
			if (Game.music) {
				Game.currentSong = Game.sounds.world1;
				Game.currentSong.setVolume(0.5f);
				Game.currentSong.loop();
			}
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
			tiles2 = new Tile[levelW * levelH];
			animatedTiles = new AnimatedTile[levelW * levelH];

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
			tiles2 = new Tile[levelW * levelH];
			animatedTiles = new AnimatedTile[levelW * levelH];

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
						// EGG number one
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Egg egg1 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg1.id = 1;
						egg1.setMask(9, 8, 14, 16);
						Game.entities.add(egg1);
					} else if (currentPixel == 0xFFF8D801) {
						// EGG number two
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Egg egg2 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg2.id = 2;
						egg2.setMask(9, 8, 14, 16);
						Game.entities.add(egg2);
					} else if (currentPixel == 0xFFF8D802) {
						// EGG number three
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Egg egg3 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg3.id = 3;
						egg3.setMask(9, 8, 14, 16);
						Game.entities.add(egg3);
					} else if (currentPixel == 0xFFF8D812) {
						// EGG number three LEVEL1 hidden area
						tiles2[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.stoneblock2);
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerM);
						Egg egg3 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg3.id = 3;
						egg3.setMask(9, 8, 14, 16);
						Game.entities.add(egg3);
					} else if (currentPixel == 0xFFF8D803) {
						// EGG number four
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Egg egg4 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg4.id = 4;
						egg4.setMask(9, 8, 14, 16);
						Game.entities.add(egg4);
					} else if (currentPixel == 0xFFF8D804) {
						// EGG number five
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Egg egg5 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg5.id = 5;
						egg5.setMask(9, 8, 14, 16);
						Game.entities.add(egg5);
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
					} else if (currentPixel == 0xFF0000F9) {
						// Sapphire tower middle
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerM);
						Gem sapphire = new Gem(x * dimension, y * dimension, dimension, dimension);
						sapphire.type = Gem.SAPPHIRE;
						sapphire.setMask(11, 6, 9, 14);
						Game.entities.add(sapphire);
					} else if (currentPixel == 0xFF904830) {
						// Sapphire wood wall
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
						Gem sapphire = new Gem(x * dimension, y * dimension, dimension, dimension);
						sapphire.type = Gem.SAPPHIRE;
						sapphire.setMask(11, 6, 9, 14);
						Game.entities.add(sapphire);
					} else if (currentPixel == 0xFFF80000) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Gem ruby = new Gem(x * dimension, y * dimension, dimension, dimension);
						ruby.type = Gem.RUBY;
						ruby.setMask(11, 6, 9, 14);
						Game.entities.add(ruby);
					} else if (currentPixel == 0xFF484848) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Knight knight = new Knight(x * dimension, (y * dimension) - 16, dimension, 48);
						knight.setMask(11, 6, 10, 42);
						Game.enemies.add(knight);
					} else if (currentPixel == 0xFF782400) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.rampartBot);
						Archer archer = new Archer(x * dimension, (y * dimension) - 16, dimension, 48);
						archer.setMask(10, 5, 11, 43);
						Game.enemies.add(archer);
					} else if (currentPixel == 0xFFD0D7EA) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.rampartWindowTL);
						Paladin paladin = new Paladin(x * dimension, (y * dimension) - 16, dimension, 48);
						paladin.setMask(2, 16, 28, 18);
						Game.enemies.add(paladin);
					} else if (currentPixel == 0xFF000000) {
						// Flagpole
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
						Flagpole flagpole = new Flagpole(x * dimension, y * dimension, dimension, dimension);
						flagpole.setMask(16, 31, 1, 1);
						Game.entities.add(flagpole);
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
					} else if (currentPixel == 0xFF0000B0) {
						// Water
						BufferedImage[] sprites = new BufferedImage[3];
						sprites[0] = AnimatedTile.water1;
						sprites[1] = AnimatedTile.water2;
						sprites[2] = AnimatedTile.water3;

						animatedTiles[x + (y * levelW)] = new AnimatedTile(x * dimension, y * dimension, sprites);
					} else if (currentPixel == 0xFF000068) {
						// Under water
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.underwater);
					} else if (currentPixel == 0xFFD8D800) {
						// Gold floor upper left
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.goldfloor_ul);
					} else if (currentPixel == 0xFFD8D801) {
						// Gold floor upper middle
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.goldfloor_um);
					} else if (currentPixel == 0xFFD8D802) {
						// Gold floor upper right
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.goldfloor_ur);
					} else if (currentPixel == 0xFFD8D803) {
						// Gold floor middle right
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.goldfloor_mr);
					} else if (currentPixel == 0xFFD8D804) {
						// Gold floor lower right
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.goldfloor_lr);
					} else if (currentPixel == 0xFFD8D805) {
						// Gold floor lower middle
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.goldfloor_lm);
					} else if (currentPixel == 0xFFD8D806) {
						// Gold floor lower left
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.goldfloor_ll);
					} else if (currentPixel == 0xFFD8D807) {
						// Gold floor middle left
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.goldfloor_ml);
					} else if (currentPixel == 0xFF787C78) {
						// Stone block
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.stoneblock);
					} else if (currentPixel == 0xFF788C88) {
						// Stone block 2
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.stoneblock2);
					} else if (currentPixel == 0xFF484849) {
						// Castle Tower left
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerL);
					} else if (currentPixel == 0xFF484850) {
						// Castle Tower middle
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerM);
					} else if (currentPixel == 0xFF484851) {
						// Castle Tower right
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerR);
					} else if (currentPixel == 0xFF484852) {
						// Castle Tower window
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerWindow);
					} else if (currentPixel == 0xFF98A0A8) {
						// Castle Tower Top Left
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerTL);
					} else if (currentPixel == 0xFF98A0A9) {
						// Castle Tower Top Middle
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerTM);
					} else if (currentPixel == 0xFF98A0B0) {
						// Castle Tower Top Right
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerTR);
					} else if (currentPixel == 0xFF98A0B1) {
						// Castle Block Left
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.castleBlockL);
					} else if (currentPixel == 0xFF98A0B2) {
						// Castle Block Middle
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.castleBlockM);
					} else if (currentPixel == 0xFF98A0B3) {
						// Castle Block Right
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.castleBlockR);
					} else if (currentPixel == 0xFF98A0B4) {
						// Castle Tower
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.castleTower);
					} else if (currentPixel == 0xFF98A0B5) {
						// Battlement Top
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.battlementTop);
					} else if (currentPixel == 0xFF98A0B6) {
						// Battlement Bottom
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.battlementBot);
					} else if (currentPixel == 0xFF98A0B7) {
						// Rampart Top
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.rampartTop);
					} else if (currentPixel == 0xFF98A0B8) {
						// Rampart Bottom
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.rampartBot);
					} else if (currentPixel == 0xFF98A0B9) {
						// Rampart Window Top Left
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.rampartWindowTL);
					} else if (currentPixel == 0xFF98A0C0) {
						// Rampart Window Top Right
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.rampartWindowTR);
					} else if (currentPixel == 0xFF98A0C1) {
						// Rampart Window Bottom Left
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.rampartWindowBL);
					} else if (currentPixel == 0xFF98A0C2) {
						// Rampart Window Bottom Right
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.rampartWindowBR);
					} else if (currentPixel == 0xFF98A0C3) {
						// Battlement
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.battlement);
					} else if (currentPixel == 0xFFD8D813) {
						// Secret area entrance
						tiles2[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.goldfloor_mr);
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerM);
					} else if (currentPixel == 0xFF788C98) {
						// Secret area inside
						tiles2[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.stoneblock2);
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerM);
					} else if (currentPixel == 0xFF906C20) {
						// Torch
						BufferedImage[] sprites = new BufferedImage[2];
						sprites[0] = AnimatedTile.woodWallTorch1;
						sprites[1] = AnimatedTile.woodWallTorch2;

						animatedTiles[x + (y * levelW)] = new AnimatedTile(x * dimension, y * dimension, sprites);
					} else if (currentPixel == 0xFF904820) {
						// Wood wall
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
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
	 * Loads the second level
	 */
	private void level2() {
		try {
			// Locating the level's image
			BufferedImage level = ImageIO.read(getClass().getResource("/level2.png"));

			// updating level's dimensions
			levelW = level.getWidth();
			levelH = level.getHeight();

			// Initialize tiles array
			tiles = new Tile[levelW * levelH];
			tiles2 = new Tile[levelW * levelH];
			animatedTiles = new AnimatedTile[levelW * levelH];

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
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
						Player player = new Player(x * dimension, y * dimension, dimension, dimension);
						player.setMask(10, 5, 16, 27);
						Game.entities.add(player);
						Game.player = player;
					} else if (currentPixel == 0xFFF8D800) {
						// EGG number one Wood Wall
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
						Egg egg1 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg1.id = 1;
						egg1.setMask(9, 8, 14, 16);
						Game.entities.add(egg1);
					} else if (currentPixel == 0xFFF8D801) {
						// EGG number two Castle Tower middle
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerM);
						Egg egg2 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg2.id = 2;
						egg2.setMask(9, 8, 14, 16);
						Game.entities.add(egg2);
					} else if (currentPixel == 0xFFF8D802) {
						// EGG number three
						tiles2[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.stoneblock2);
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerM);
						Egg egg3 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg3.id = 3;
						egg3.setMask(9, 8, 14, 16);
						Game.entities.add(egg3);
					} else if (currentPixel == 0xFFF8D803) {
						// EGG number four
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
						Egg egg4 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg4.id = 4;
						egg4.setMask(9, 8, 14, 16);
						Game.entities.add(egg4);
					} else if (currentPixel == 0xFFF8D804) {
						// EGG number five hidden area
						tiles2[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.stoneblock2);
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerM);
						Egg egg5 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg5.id = 5;
						egg5.setMask(9, 8, 14, 16);
						Game.entities.add(egg5);
					} else if (currentPixel == 0xFF0000F8) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Gem sapphire = new Gem(x * dimension, y * dimension, dimension, dimension);
						sapphire.type = Gem.SAPPHIRE;
						sapphire.setMask(11, 6, 9, 14);
						Game.entities.add(sapphire);
					} else if (currentPixel == 0xFF0000F7) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.rampartTop);
						Gem sapphire = new Gem(x * dimension, y * dimension, dimension, dimension);
						sapphire.type = Gem.SAPPHIRE;
						sapphire.setMask(11, 6, 9, 14);
						Game.entities.add(sapphire);
					} else if (currentPixel == 0xFFF80000) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Gem ruby = new Gem(x * dimension, y * dimension, dimension, dimension);
						ruby.type = Gem.RUBY;
						ruby.setMask(11, 6, 9, 14);
						Game.entities.add(ruby);
					} else if (currentPixel == 0xFFF80001) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
						Gem ruby = new Gem(x * dimension, y * dimension, dimension, dimension);
						ruby.type = Gem.RUBY;
						ruby.setMask(11, 6, 9, 14);
						Game.entities.add(ruby);
					} else if (currentPixel == 0xFF009048) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Gem emerald = new Gem(x * dimension, y * dimension, dimension, dimension);
						emerald.type = Gem.EMERALD;
						emerald.setMask(10, 9, 12, 13);
						Game.entities.add(emerald);
					} else if (currentPixel == 0xFF009049) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.battlementTop);
						Gem emerald = new Gem(x * dimension, y * dimension, dimension, dimension);
						emerald.type = Gem.EMERALD;
						emerald.setMask(10, 9, 12, 13);
						Game.entities.add(emerald);
					} else if (currentPixel == 0xFF009050) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerM);
						Gem emerald = new Gem(x * dimension, y * dimension, dimension, dimension);
						emerald.type = Gem.EMERALD;
						emerald.setMask(10, 9, 12, 13);
						Game.entities.add(emerald);
					}else if (currentPixel == 0xFF00FCD9) {
						//Diamond Castle Tower middle
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerM);
						Gem diamond = new Gem(x * dimension, y * dimension, dimension, dimension);
						diamond.type = Gem.DIAMOND;
						diamond.setMask(8, 6, 18, 17);
						Game.entities.add(diamond);
					}else if (currentPixel == 0xFF00FCD8) {
						//Diamond Hidden Area
						tiles2[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.stoneblock2);
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerM);
						Gem diamond = new Gem(x * dimension, y * dimension, dimension, dimension);
						diamond.type = Gem.DIAMOND;
						diamond.setMask(8, 6, 18, 17);
						Game.entities.add(diamond);
					} else if (currentPixel == 0xFF00FCD7) {
						//Diamond Wood Wall
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
						Gem diamond = new Gem(x * dimension, y * dimension, dimension, dimension);
						diamond.type = Gem.DIAMOND;
						diamond.setMask(8, 6, 18, 17);
						Game.entities.add(diamond);
					} else if (currentPixel == 0xFF782400) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.battlement);
						Archer archer = new Archer(x * dimension, (y * dimension) - 16, dimension, 48);
						archer.setMask(10, 5, 11, 43);
						Game.enemies.add(archer);
					} else if (currentPixel == 0xFF484848) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerM);
						Knight knight = new Knight(x * dimension, (y * dimension) - 16, dimension, 48);
						knight.setMask(11, 6, 10, 42);
						Game.enemies.add(knight);
					} else if (currentPixel == 0xFFD0D7EA) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
						Paladin paladin = new Paladin(x * dimension, (y * dimension) - 16, dimension, 48);
						paladin.setMask(2, 16, 28, 18);
						Game.enemies.add(paladin);
					} else if (currentPixel == 0xFFF8FCD6) {
						//Spear + diamond wood wall
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
						Spear spear = new Spear(x * dimension, (y * dimension) - 32, dimension, 64);
						spear.type = "botToTop";
						Game.enemies.add(spear);
						
						Gem diamond = new Gem(x * dimension, y * dimension, dimension, dimension);
						diamond.type = Gem.DIAMOND;
						diamond.setMask(8, 6, 18, 17);
						Game.entities.add(diamond);
					} else if (currentPixel == 0xFFF8FCD7) {
						//Spear + saphire wood wall
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
						Spear spear = new Spear(x * dimension, (y * dimension) - 32, dimension, 64);
						spear.type = "botToTop";
						Game.enemies.add(spear);
						
						Gem sapphire = new Gem(x * dimension, y * dimension, dimension, dimension);
						sapphire.type = Gem.SAPPHIRE;
						sapphire.setMask(11, 6, 9, 14);
						Game.entities.add(sapphire);
					} else if (currentPixel == 0xFFF8FCD8) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
						Spear spear = new Spear(x * dimension, (y * dimension) - 32, dimension, 64);
						spear.type = "botToTop";
						Game.enemies.add(spear);
					} else if (currentPixel == 0xFFF8FCD9) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
						Spear spear = new Spear(x * dimension, (y * dimension), dimension, 64);
						spear.type = "topToBot";
						Game.enemies.add(spear);
					} else if (currentPixel == 0xFF482400) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Box box = new Box(x * dimension, (y * dimension), dimension, dimension);
						box.position = new int[2];
						box.position[0] = x * dimension;
						box.position[1] = y * dimension;
						Game.enemies.add(box);
						Button.box = box;
					} else if (currentPixel == 0xFF780030) {
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.castleTowerM);
						Door door = new Door(x * dimension, (y * dimension), dimension, dimension);
						door.tile = Tile.castleTowerM;
						door.position = x + (y * levelW);
						Game.enemies.add(door);
					} else if (currentPixel == 0xFF780031) {
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.woodWall);
						Door door = new Door(x * dimension, (y * dimension), dimension, dimension);
						door.tile = Tile.woodWall;
						door.position = x + (y * levelW);
						Game.enemies.add(door);
					} else if(currentPixel == 0xFFF80020) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Button button = new Button(x * dimension, (y * dimension), dimension, dimension);
						Game.entities.add(button);
					} else if (currentPixel == 0xFF000000) {
						// Flagpole
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
						Flagpole flagpole = new Flagpole(x * dimension, y * dimension, dimension, dimension);
						flagpole.setMask(16, 31, 1, 1);
						Game.entities.add(flagpole);
					} else if (currentPixel == 0xFFD8D800) {
						// Gold floor upper left
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.goldfloor_ul);
					} else if (currentPixel == 0xFFD8D801) {
						// Gold floor upper middle
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.goldfloor_um);
					} else if (currentPixel == 0xFFD8D802) {
						// Gold floor upper right
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.goldfloor_ur);
					} else if (currentPixel == 0xFFD8D803) {
						// Gold floor middle right
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.goldfloor_mr);
					} else if (currentPixel == 0xFFD8D804) {
						// Gold floor lower right
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.goldfloor_lr);
					} else if (currentPixel == 0xFFD8D805) {
						// Gold floor lower middle
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.goldfloor_lm);
					} else if (currentPixel == 0xFFD8D806) {
						// Gold floor lower left
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.goldfloor_ll);
					} else if (currentPixel == 0xFFD8D807) {
						// Gold floor middle left
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.goldfloor_ml);
					} else if (currentPixel == 0xFF787C78) {
						// Stone block
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.stoneblock);
					} else if (currentPixel == 0xFF788C88) {
						// Stone block 2
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.stoneblock2);
					} else if (currentPixel == 0xFF484850) {
						// Castle Tower middle
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerM);
					} else if (currentPixel == 0xFF904820) {
						// Wood wall
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
					} else if (currentPixel == 0xFF98A0B5) {
						// Battlement Top
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.battlementTop);
					} else if (currentPixel == 0xFF98A0B6) {
						// Battlement Bottom
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.battlementBot);
					} else if (currentPixel == 0xFF98A0C3) {
						// Battlement
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.battlement);
					} else if (currentPixel == 0xFF98A0B7) {
						// Rampart Top
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.rampartTop);
					} else if (currentPixel == 0xFF98A0B8) {
						// Rampart Bottom
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.rampartBot);
					}  else if (currentPixel == 0xFF98A0B9) {
						// Rampart Window Top Left
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.rampartWindowTL);
					} else if (currentPixel == 0xFF98A0C0) {
						// Rampart Window Top Right
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.rampartWindowTR);
					} else if (currentPixel == 0xFF98A0C1) {
						// Rampart Window Bottom Left
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.rampartWindowBL);
					} else if (currentPixel == 0xFF98A0C2) {
						// Rampart Window Bottom Right
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.rampartWindowBR);
					}else if (currentPixel == 0xFF788C98) {
						// Secret area inside
						tiles2[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.stoneblock2);
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerM);
					} else if (currentPixel == 0xFFD8D813) {
						// Secret area entrance
						tiles2[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.goldfloor_mr);
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.castleTowerM);
					} else if (currentPixel == 0xFF906C20) {
						// Torch
						BufferedImage[] sprites = new BufferedImage[2];
						sprites[0] = AnimatedTile.woodWallTorch1;
						sprites[1] = AnimatedTile.woodWallTorch2;
						animatedTiles[x + (y * levelW)] = new AnimatedTile(x * dimension, y * dimension, sprites);
					} else if (currentPixel == 0xFFFFFFF1) {
						// Invisible wood wall
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.woodWall);
					} else if (currentPixel == 0xFFFFFFF2) {
						// Invisible Castle Tower middle
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.castleTowerM);
					} else if (currentPixel == 0xFFFFFFF3) {
						// Invisible wall
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.transparent);
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the third level
	 */
	private void level3() {
		// Grab map layout
		try {
			// Locating the level's image
			BufferedImage level = ImageIO.read(getClass().getResource("/level3.png"));

			// updating level's dimensions
			levelW = level.getWidth();
			levelH = level.getHeight();

			// Initialize tiles array
			tiles = new Tile[levelW * levelH];
			tiles2 = new Tile[levelW * levelH];
			animatedTiles = new AnimatedTile[levelW * levelH];

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
					}else if (currentPixel == 0xFFF8D800) {
						// EGG number one
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Egg egg1 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg1.id = 1;
						egg1.setMask(9, 8, 14, 16);
						Game.entities.add(egg1);
					}else if (currentPixel == 0xFFF8D801) {
						// EGG number two
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Egg egg2 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg2.id = 2;
						egg2.setMask(9, 8, 14, 16);
						Game.entities.add(egg2);
					}else if (currentPixel == 0xFFF8D802) {
						// EGG number three
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodTiles);
						tiles2[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
						Egg egg3 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg3.id = 3;
						egg3.setMask(9, 8, 14, 16);
						Game.entities.add(egg3);
					}else if (currentPixel == 0xFFF8D803) {
						// EGG number four hidden area
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodTiles);
						tiles2[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
						Egg egg4 = new Egg(x * dimension, y * dimension, dimension, dimension);
						egg4.id = 4;
						egg4.setMask(9, 8, 14, 16);
						Game.entities.add(egg4);
					}else if (currentPixel == 0xFF782400) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Archer archer = new Archer(x * dimension, (y * dimension) - 16, dimension, 48);
						archer.setMask(10, 5, 11, 43);
						Game.enemies.add(archer);
					}else if (currentPixel == 0xFFD0D7EA) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Paladin paladin = new Paladin(x * dimension, (y * dimension) - 16, dimension, 48);
						paladin.setMask(2, 16, 28, 18);
						Game.enemies.add(paladin);
					}else if (currentPixel == 0xFFF8FCD8) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Spear spear = new Spear(x * dimension, (y * dimension) - 32, dimension, 64);
						spear.type = "botToTop";
						Game.enemies.add(spear);
					} else if (currentPixel == 0xFFF8FCD9) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Spear spear = new Spear(x * dimension, (y * dimension), dimension, 64);
						spear.type = "topToBot";
						Game.enemies.add(spear);
					}else if (currentPixel == 0xFFF8FCD7) {
						//Spear + sapphire
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Spear spear = new Spear(x * dimension, (y * dimension) - 32, dimension, 64);
						spear.type = "botToTop";
						Game.enemies.add(spear);
						
						Gem sapphire = new Gem(x * dimension, y * dimension, dimension, dimension);
						sapphire.type = Gem.SAPPHIRE;
						sapphire.setMask(11, 6, 9, 14);
						Game.entities.add(sapphire);
					}else if (currentPixel == 0xFFF8FCD6) {
						//Spear + emerald
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Spear spear = new Spear(x * dimension, (y * dimension) - 32, dimension, 64);
						spear.type = "botToTop";
						Game.enemies.add(spear);
						
						Gem emerald = new Gem(x * dimension, y * dimension, dimension, dimension);
						emerald.type = Gem.EMERALD;
						emerald.setMask(10, 9, 12, 13);
						Game.entities.add(emerald);
					}else if(currentPixel == 0xFFF80020) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Button button = new Button(x * dimension, (y * dimension), dimension, dimension);
						Game.entities.add(button);
					}else if (currentPixel == 0xFF482400) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Box box = new Box(x * dimension, (y * dimension), dimension, dimension);
						box.position = new int[2];
						box.position[0] = x * dimension;
						box.position[1] = y * dimension;
						Game.enemies.add(box);
						Button.box = box;
					}else if (currentPixel == 0xFF780030) {
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.transparent);
						Door door = new Door(x * dimension, (y * dimension), dimension, dimension);
						door.tile = Tile.transparent;
						door.position = x + (y * levelW);
						Game.enemies.add(door);
					}else if (currentPixel == 0xFF0000F8) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Gem sapphire = new Gem(x * dimension, y * dimension, dimension, dimension);
						sapphire.type = Gem.SAPPHIRE;
						sapphire.setMask(11, 6, 9, 14);
						Game.entities.add(sapphire);
					}else if (currentPixel == 0xFFF80000) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Gem ruby = new Gem(x * dimension, y * dimension, dimension, dimension);
						ruby.type = Gem.RUBY;
						ruby.setMask(11, 6, 9, 14);
						Game.entities.add(ruby);
					}else if (currentPixel == 0xFF009048) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Gem emerald = new Gem(x * dimension, y * dimension, dimension, dimension);
						emerald.type = Gem.EMERALD;
						emerald.setMask(10, 9, 12, 13);
						Game.entities.add(emerald);
					}else if (currentPixel == 0xFF00FCD8) {
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.transparent);
						Gem diamond = new Gem(x * dimension, y * dimension, dimension, dimension);
						diamond.type = Gem.DIAMOND;
						diamond.setMask(8, 6, 18, 17);
						Game.entities.add(diamond);
					}else if (currentPixel == 0xFFF8D848) {
						// Wood floor upper left
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.woodFloorUL);
					}else if (currentPixel == 0xFFF8D847) {
						// Wood floor upper middle
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.woodFloorUM);
					}else if (currentPixel == 0xFFF8D846) {
						// Wood floor upper left
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.woodFloorUR);
					}else if (currentPixel == 0xFFF8D845) {
						// Wood floor left
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.woodFloorL);
					}else if (currentPixel == 0xFF904820) {
						// Wood wall
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
					}else if (currentPixel == 0xFF904821) {
						// Wood wall solid
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.woodWall);
					}else if (currentPixel == 0xFFF8D844) {
						// Wood floor right
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.woodFloorR);
					}else if (currentPixel == 0xFFF8D843) {
						// Wood floor bottom left
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.woodFloorBL);
					}else if (currentPixel == 0xFFF8D842) {
						// Wood floor bottom middle
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.woodFloorBM);
					}else if (currentPixel == 0xFFF8D841) {
						// Wood floor bottom left
						tiles[x + (y * levelW)] = new Collider(x * dimension, y * dimension, Tile.woodFloorBR);
					}else if (currentPixel == 0xFFF8D840) {
						// Wood floor corner bottom right
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodFloorCBR);
					}else if (currentPixel == 0xFFF8D839) {
						// Wood floor corner bottom right
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodFloorCUL);
					}else if (currentPixel == 0xFFF8D838) {
						// Wood floor right secret area
						tiles2[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodFloorR);
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodTiles);
					}else if (currentPixel == 0xFFF8D849) {
						// Wood floor left secret area
						tiles2[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodFloorL);
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodTiles);
					}else if (currentPixel == 0xFF484820) {
						// wood wall hidden area
						tiles2[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodWall);
						tiles[x + (y * levelW)] = new Tile(x * dimension, y * dimension, Tile.woodTiles);
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
				AnimatedTile animatedTile = animatedTiles[x + (y * levelW)];

				// Render tile
				if (tile != null) {
					tile.render(g);
				}

				// Render animated tile
				if (animatedTile != null) {
					animatedTile.render(g);
				}
			}
		}
	}

	/**
	 * Renders the level's second layer
	 * 
	 * @param g - Graphic component
	 */
	public void render2(Graphics g) {
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
				Tile tile = tiles2[x + (y * levelW)];

				// Render tile
				if (tile != null) {
					tile.render(g);
				}
			}
		}
	}

	/**
	 * Background image
	 * 
	 * @param g
	 */
	public void renderBackground(Graphics g) {

		// Variable initialization
		BufferedImage bg = null;
		BufferedImage bg1 = null;
		BufferedImage bg2 = null;
		BufferedImage bg3 = null;
		BufferedImage bg4 = null;

		// World 1
		if (Game.levelNumber == 1 || Game.levelNumber == 2) {
			bg = sky;
			bg1 = hill;
			bg2 = castle;
			bg3 = mountains;
			bg4 = clouds;
		}else if (Game.levelNumber == 3){
			bg = castleInside;
		}

		// Reset horizontal variables
		if (bgScrollX <= -Game.width) {
			bgScrollX = 0;
		} else if (bgScrollX > Game.width) {
			bgScrollX = 0;
		}

		
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
		g.drawImage(bg, (int) bgScrollX - Game.width, (int) bgY, 320 * Game.scale, 448 * Game.scale, null);
		g.drawImage(bg, (int) bgScrollX, (int) bgY, 320 * Game.scale, 448 * Game.scale, null);
		g.drawImage(bg, (int) bgScrollX + Game.width, (int) bgY, 320 * Game.scale, 448 * Game.scale, null);

		// clouds
		g.drawImage(bg4, (int) bg4ScrollX - Game.width, (int) bgY4, 320 * Game.scale, 112 * Game.scale, null);
		g.drawImage(bg4, (int) bg4ScrollX, (int) bgY4, 320 * Game.scale, 112 * Game.scale, null);
		g.drawImage(bg4, (int) bg4ScrollX + Game.width, (int) bgY4, 320 * Game.scale, 112 * Game.scale, null);

		// mountains
		g.drawImage(bg3, (int) bg3ScrollX - Game.width, (int) bgY3, 320 * Game.scale, 56 * Game.scale, null);
		g.drawImage(bg3, (int) bg3ScrollX, (int) bgY3, 320 * Game.scale, 56 * Game.scale, null);
		g.drawImage(bg3, (int) bg3ScrollX + Game.width, (int) bgY3, 320 * Game.scale, 56 * Game.scale, null);

		// castle
		g.drawImage(bg2, (int) bg2ScrollX - Game.width, (int) bgY2, 320 * Game.scale, 112 * Game.scale, null);
		g.drawImage(bg2, (int) bg2ScrollX, (int) bgY2, 320 * Game.scale, 112 * Game.scale, null);
		g.drawImage(bg2, (int) bg2ScrollX + Game.width, (int) bgY2, 320 * Game.scale, 112 * Game.scale, null);

		// hills
		g.drawImage(bg1, (int) bg1ScrollX - Game.width, (int) bgY1, 320 * Game.scale, 56 * Game.scale, null);
		g.drawImage(bg1, (int) bg1ScrollX, (int) bgY1, 320 * Game.scale, 56 * Game.scale, null);
		g.drawImage(bg1, (int) bg1ScrollX + Game.width, (int) bgY1, 320 * Game.scale, 56 * Game.scale, null);

		// BG movement
		if (Player.isMoving && Camera.x != 0 && Camera.x != levelW * dimension * Game.scale - Game.width
				&& Game.player.alive) {
			if (Player.right) {
				bgScrollX -= (Player.speed-1) * Game.scale;
				bg1ScrollX -= Player.speed * Game.scale;
				bg2ScrollX -= 1.5f * Game.scale;
				bg3ScrollX -= 1 * Game.scale;
			} else if (Player.left) {
				bgScrollX += (Player.speed-1) * Game.scale;
				bg1ScrollX += Player.speed * Game.scale;
				bg2ScrollX += 1.5f * Game.scale;
				bg3ScrollX += 1 * Game.scale;
			}
		}

		bg4ScrollX--;

		// ------------ DECORATIONS --------------//

		if (Game.levelNumber == 1) {

			// Cave
			g.drawImage(cave, 0 - Camera.x, (dimension * 4) * Game.scale - Camera.y, 416 * Game.scale, 288 * Game.scale,
					null);
			// Tree
			g.drawImage(tree, (dimension * 18) * Game.scale - Camera.x, (dimension * 7) * Game.scale - Camera.y,
					96 * Game.scale, 128 * Game.scale, null);

		}

	}

}
