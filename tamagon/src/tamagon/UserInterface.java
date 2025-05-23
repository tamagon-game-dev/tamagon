package tamagon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class UserInterface {

	/**
	 * User Interface's sprite sheet
	 */
	private Spritesheet UISpritesheet;

	/**
	 * UI graphic elements
	 */
	private BufferedImage hitoriLogo, titleBg, tamagonLogo, wkey, skey, space, backspace, lifeIcon, eggIcon, castleland,
			gameOver;

	/**
	 * Fade in/out animation frames
	 */
	private int fadeOut = 255, fadeIn = 0, maxInterval = 60 * 3, interval = maxInterval;

	/**
	 * Fade in/out animation flags
	 */
	private boolean fadedOut = false, fadedIn = true, startInterval = false;

	/**
	 * Title background horizontal scrolling animation variables
	 */
	private int titleBgX = 0, titleBgX2 = Game.width;

	/**
	 * Tamagon logo position
	 */
	private int tamagonLogoY = 0;

	/**
	 * Menu cursor position
	 */
	public int cursor = 0;

	/**
	 * Array with resolution options
	 */
	private String[] resolutionOptions;

	/**
	 * Game resolution adjustment
	 */
	public int resolution = Game.scale - 1;

	/**
	 * Initializes user interface's objects
	 */
	public UserInterface() {
		// Data initialization
		resolutionOptions = new String[4];
		resolutionOptions[0] = "320x224";
		resolutionOptions[1] = "640x448";
		resolutionOptions[2] = "960x672";
		resolutionOptions[3] = "1280x896";

		// Image initialization
		UISpritesheet = new Spritesheet("ui");
		hitoriLogo = UISpritesheet.getSprite(0, 0, 122, 76);
		titleBg = UISpritesheet.getSprite(0, 76, 320, 224);
		tamagonLogo = UISpritesheet.getSprite(0, 300, 213, 62);
		wkey = UISpritesheet.getSprite(122, 0, 16, 16);
		skey = UISpritesheet.getSprite(122, 16, 16, 16);
		space = UISpritesheet.getSprite(138, 0, 49, 16);
		backspace = UISpritesheet.getSprite(138, 16, 32, 16);
		lifeIcon = UISpritesheet.getSprite(122, 32, 21, 17);
		eggIcon = UISpritesheet.getSprite(143, 32, 17, 17);
		castleland = UISpritesheet.getSprite(187, 0, 107, 62);
		gameOver = UISpritesheet.getSprite(213, 300, 107, 62);
	}

	/**
	 * Renders the graphic interface
	 * 
	 * @param g - the graphic component used to render the whole game
	 */
	public void render(Graphics g) {

		if (Game.gameState.equals("logo")) {
			renderLogo(g);
		} else if (Game.gameState.equals("title") || Game.gameState.equals("starting")) {
			renderTitle(g);
		} else if (Game.gameState.equals("options")) {
			renderOptions(g);
		} else if (Game.gameState.equals("intro")) {
			renderWorldIntro(g);
		} else if (Game.gameState.equals("playing")) {
			renderPlaying(g);
		} else if (Game.gameState.equals("paused")) {
			renderPaused(g);
		} else if (Game.gameState.equals("restart")) {
			renderRestart(g);
		} else if (Game.gameState.equals("gameover")) {
			renderGameOver(g);
		} else if (Game.gameState.equals("levelend")) {
			renderLevelEnd(g);
		}

	}

	/**
	 * Render level's final result
	 * 
	 * @param g
	 */
	private void renderLevelEnd(Graphics g) {
		startInterval = true;

		// Darkened background
		g.setColor(new Color(0, 0, 0, 1));
		g.fillRect(0, 0, Game.width, Game.height);

		g.setColor(new Color(248, 144, 32));
		g.setFont(new Font("Calibri", Font.BOLD, 16 * Game.scale));

		// Final score calculus
		int calc = (Player.eggs.size() * 10) + (Level.enemiesDefeated * 5);

		if (interval <= 60 * 3 - 30) {
			g.drawString("EGG", Game.width / 5, Game.height - (32 * Game.scale) * 6);
			g.drawString(Player.eggs.size() + " x 10", Game.width - 128 * Game.scale,
					Game.height - (32 * Game.scale) * 6);
		}

		if (interval <= 60 * 3 - 60) {
			g.drawString("ENEMY", Game.width / 5, Game.height - (32 * Game.scale) * 4);
			g.drawString(Level.enemiesDefeated + " x 5", Game.width - 128 * Game.scale,
					Game.height - (32 * Game.scale) * 4);
		}

		if (interval <= 60 * 3 - 90) {
			g.drawString("TOTAL", Game.width / 5, Game.height - (32 * Game.scale) * 2);
			g.drawString(calc + " points!", Game.width - 128 * Game.scale, Game.height - (32 * Game.scale) * 2);
		}

		g.setColor(new Color(248, 216, 32));
		g.setFont(new Font("Calibri", Font.PLAIN, 16 * Game.scale));

		if (interval <= 60 * 3 - 30) {
			g.drawString("EGG", Game.width / 5, Game.height - (32 * Game.scale) * 6);
			g.drawString(Player.eggs.size() + " x 10", Game.width - 128 * Game.scale,
					Game.height - (32 * Game.scale) * 6);
		}

		if (interval <= 60 * 3 - 60) {
			g.drawString("ENEMY", Game.width / 5, Game.height - (32 * Game.scale) * 4);
			g.drawString(Level.enemiesDefeated + " x 5", Game.width - 128 * Game.scale,
					Game.height - (32 * Game.scale) * 4);
		}

		if (interval <= 60 * 3 - 90) {
			g.drawString("TOTAL", Game.width / 5, Game.height - (32 * Game.scale) * 2);
			g.drawString(calc + " points!", Game.width - 128 * Game.scale, Game.height - (32 * Game.scale) * 2);
		}

		// Interval between text
		if (startInterval) {
			interval--;
			if (interval == 0) {
				//interval reset
				startInterval = false;
				interval = maxInterval;
				
				//high score set
				Player.score += calc;
				if(Player.score > Game.highscore) Game.highscore = Player.score;
				
				//variables reset
				Player.eggs.clear();
				Game.entities.clear();
				Game.enemies.clear();
				Player.right = false;
				Player.left = false;
				Player.jump = false;
				Player.attack = false;
				Player.canAttack = true;
				Game.levelNumber+= 1;
				
				//Temporary
//				if (Game.levelNumber >= 3) {
//					Game.gameState = "title";
//					Game.levelNumber = 1;
//					return;
//				}
				
				//Next Level
				Game.nextLevel = true;
				Game.gameState = "restart";
			}
		}
	}

	/**
	 * Render game over
	 * 
	 * @param g
	 */
	private void renderGameOver(Graphics g) {
		// Game Over label
		g.setColor(new Color(248, 144, 32));
		g.setFont(new Font("Calibri", Font.BOLD, 16 * Game.scale));
		g.drawString("GAME OVER", (Game.width / 2) - (42 * Game.scale), (Game.height / 2) - 40 * Game.scale);

		g.setColor(new Color(248, 216, 32));
		g.setFont(new Font("Calibri", Font.PLAIN, 16 * Game.scale));
		g.drawString("GAME OVER", (Game.width / 2) - (42 * Game.scale), (Game.height / 2) - 40 * Game.scale);

		// Broken eggs
		g.drawImage(gameOver, (Game.width / 2) - (107 * Game.scale) / 2, (Game.height / 2) - (31 * Game.scale) / 2,
				107 * Game.scale, 62 * Game.scale, null);

		// Interval between fade in and fade out
		if (startInterval) {
			interval--;
			if (interval == 0) {
				startInterval = false;
				interval = maxInterval;
				Game.gameState = "title";
			}
		}
	}

	/**
	 * Game restart transition
	 * 
	 * @param g
	 */
	private void renderRestart(Graphics g) {
		// If the screen is faded out, then fade it in!
		if (fadedOut) {
			// Color black for fade in
			g.setColor(new Color(0, 0, 0, fadeIn));

			// Black background for fade in
			g.fillRect(0, 0, Game.width, Game.height);

			// Makes the black background fade in
			if (fadeIn < fadeOut) {
				fadeIn += 3;
			}

			// Completes the fade in animation
			if (fadeIn == fadeOut) {
				fadedOut = false;
				fadedIn = true;
				fadeOut = 255;
				fadeIn = 0;
				if (Player.life > 0) {
					Game.restartLevel();
				} else {
					Game.gameState = "gameover";
					startInterval = true;
					Game.entities.clear();
					Game.enemies.clear();
					Player.life = 3;
					Player.score = 0;
				}
			}
		}

		// If the screen is faded in, then fade it out!
		if (fadedIn) {

			// LOADING
			g.setColor(new Color(248, 216, 32));
			g.setFont(new Font("Calibri", Font.PLAIN, 12 * Game.scale));
			g.drawString("LOADING", Game.width / 2 - 30 * Game.scale, Game.height / 2);

			// Color black for fade out
			g.setColor(new Color(0, 0, 0, fadeOut));

			// Black background for fade out
			g.fillRect(0, 0, Game.width, Game.height);

			// Makes the black background fade out
			if (fadeOut > fadeIn) {
				fadeOut -= 3;
			}

			// Completes the fade out animation
			if (fadeOut == fadeIn) {
				fadedOut = true;
				fadedIn = false;
				fadeOut = 255;
				Game.gameState = "playing";
			}
		}
	}

	/**
	 * Renders a splash image with the level name
	 * 
	 * @param g
	 */
	private void renderWorldIntro(Graphics g) {

		// Splash image
		if (Game.levelNumber >= 1 && Game.levelNumber <= 4) {
			// Castleland

			// WORLD 1
			g.setColor(new Color(248, 216, 32));
			g.setFont(new Font("Calibri", Font.PLAIN, 16 * Game.scale));
			g.drawString("WORLD 1", Game.width / 2 - 30 * Game.scale, 80 * Game.scale);

			// WORLD NAME
			g.setColor(new Color(248, 144, 32));
			g.setFont(new Font("Calibri", Font.BOLD, 16 * Game.scale));
			g.drawString("CASTLELAND", Game.width / 2 - 40 * Game.scale, 160 * Game.scale);

			g.drawImage(castleland, (Game.width / 2) - (107 * Game.scale) / 2,
					(Game.height / 2) - (62 * Game.scale) / 2, 107 * Game.scale, 62 * Game.scale, null);
		}

		// If the screen is faded in, then fade it out!
		if (fadedIn) {
			// Color black for fade out
			g.setColor(new Color(0, 0, 0, fadeOut));

			// Black background for fade out
			g.fillRect(0, 0, Game.width, Game.height);

			// Makes the black background fade out
			if (fadeOut > fadeIn) {
				fadeOut -= 3;
			}

			// Completes the fade out animation
			if (fadeOut == fadeIn) {
				startInterval = true;
				fadedIn = false;
				fadeOut = 255;
			}
		}

		// If the screen is faded out, then fade it in!
		if (fadedOut) {
			// Color black for fade in
			g.setColor(new Color(0, 0, 0, fadeIn));

			// Black background for fade in
			g.fillRect(0, 0, Game.width, Game.height);

			// Makes the black background fade in
			if (fadeIn < fadeOut) {
				fadeIn += 3;
			}

			// Completes the fade in animation
			if (fadeIn == fadeOut) {
				fadedOut = false;
				fadedIn = true;
				fadeOut = 255;
				fadeIn = 0;
				Game.level = new Level("level" + Game.levelNumber);
				Game.gameState = "playing";
			}
		}

		// Interval between fade in and fade out
		if (startInterval) {
			interval--;
			if (interval == 0) {
				startInterval = false;
				interval = maxInterval;
				fadedOut = true;
			}
		}

	}

	/**
	 * Renders the game on a paused state
	 * 
	 * @param g
	 */
	private void renderPaused(Graphics g) {
		g.setColor(new Color(0, 0, 0, 1));
		g.fillRect(0, 0, Game.width, Game.height);

		// Paused label
		g.setColor(new Color(248, 144, 32));
		g.setFont(new Font("Calibri", Font.BOLD, 16 * Game.scale));
		g.drawString("PAUSED", (Game.width / 2) - 32 * Game.scale, (Game.height / 2));

		g.setColor(new Color(248, 216, 32));
		g.setFont(new Font("Calibri", Font.PLAIN, 16 * Game.scale));
		g.drawString("PAUSED", (Game.width / 2) - 32 * Game.scale, (Game.height / 2));

	}

	/**
	 * Renders the Hitori games logo
	 * 
	 * @param g - the graphic component used to render the whole game
	 */
	private void renderLogo(Graphics g) {
		// Hitori Games Presents (background color)
		g.setColor(new Color(248, 252, 248));

		// Hitori Games Presents (background)
		g.fillRect(0, 0, Game.width, Game.height);

		// Hitori Games Presents
		g.drawImage(hitoriLogo, Game.width / 3, Game.height / 3, hitoriLogo.getWidth() * Game.scale,
				hitoriLogo.getHeight() * Game.scale, null);

		// If the screen is faded in, then fade it out!
		if (fadedIn) {
			// Color black for fade out
			g.setColor(new Color(0, 0, 0, fadeOut));

			// Black background for fade out
			g.fillRect(0, 0, Game.width, Game.height);

			// Makes the black background fade out
			if (fadeOut > fadeIn) {
				fadeOut -= 3;
			}

			// Completes the fade out animation
			if (fadeOut == fadeIn) {
				startInterval = true;
				fadedIn = false;
				fadeOut = 255;
			}
		}

		// If the screen is faded out, then fade it in!
		if (fadedOut) {
			// Color black for fade in
			g.setColor(new Color(0, 0, 0, fadeIn));

			// Black background for fade in
			g.fillRect(0, 0, Game.width, Game.height);

			// Makes the black background fade in
			if (fadeIn < fadeOut) {
				fadeIn += 3;
			}

			// Completes the fade in animation
			if (fadeIn == fadeOut) {
				fadedOut = false;
				fadedIn = true;
				fadeOut = 255;
				fadeIn = 0;
				Game.gameState = "title";
			}
		}

		// Interval between fade in and fade out
		if (startInterval) {
			interval--;
			if (interval == 0) {
				startInterval = false;
				interval = maxInterval;
				fadedOut = true;
			}
		}
	}

	/**
	 * Renders the game's title
	 * 
	 * @param g - the graphic component used to render the whole game
	 */
	private void renderTitle(Graphics g) {
		// Draws the title bg
		titleBackground(g);

		// Game logo animation
		if (tamagonLogoY <= Game.height / 6) {
			tamagonLogoY += Game.scale;
		}

		// Draws the game's logo
		g.drawImage(tamagonLogo, Game.width / 6, tamagonLogoY, tamagonLogo.getWidth() * Game.scale,
				tamagonLogo.getHeight() * Game.scale, null);

		// Menu cursor render
		int[] cursorY = new int[2];
		cursorY[0] = Game.width / 2;
		cursorY[1] = Game.width / 2 + 16 * Game.scale;

		// Draw the strings highlights
		String hiscore = "HI-SCORE: " + Game.highscore;

		g.setColor(new Color(248, 144, 32));
		g.setFont(new Font("Calibri", Font.BOLD, 16 * Game.scale));
		g.drawString(hiscore, Game.width / 2 - (Game.scale * hiscore.length() + (34 * Game.scale)), 32 * Game.scale);
		g.drawString("START", Game.width / 3 + 32 * Game.scale, Game.width / 2);
		g.drawString("OPTIONS", Game.width / 3 + 24 * Game.scale, Game.width / 2 + 16 * Game.scale);
		g.drawString("->", Game.width / 3, cursorY[cursor]);

		// Draw the strings
		g.setColor(new Color(248, 216, 32));
		g.setFont(new Font("Calibri", Font.PLAIN, 16 * Game.scale));
		g.drawString(hiscore, Game.width / 2 - (Game.scale * hiscore.length() + (34 * Game.scale)), 32 * Game.scale);
		g.drawString("START", Game.width / 3 + 32 * Game.scale, Game.width / 2);
		g.drawString("OPTIONS", Game.width / 3 + 24 * Game.scale, Game.width / 2 + 16 * Game.scale);
		g.drawString("->", Game.width / 3, cursorY[cursor]);

		// Render application version
		g.setFont(new Font("Calibri", Font.BOLD, 8 * Game.scale));
		g.drawString("version: " + Game.version, 0, Game.height - 8 * Game.scale);

		// Menu tutorial
		menuInstructions(g);

		// If the screen is faded in, then fade it out!
		if (fadedIn) {

			// Color black for fade out
			g.setColor(new Color(0, 0, 0, fadeOut));

			// Black background for fade out
			g.fillRect(0, 0, Game.width, Game.height);

			// Makes the black background fade out
			if (fadeOut > fadeIn) {
				fadeOut -= 3;
			}

			// Completes the fade out animation
			if (fadeOut == fadeIn) {
				fadedIn = false;
				fadeOut = 255;
				fadedOut = true;
			}
		}

		// If player started the game, lets fade in!
		if (Game.gameState.equals("starting") && !fadedIn) {

			// Color black for fade in
			g.setColor(new Color(0, 0, 0, fadeIn));

			// Black background for fade in
			g.fillRect(0, 0, Game.width, Game.height);

			// Makes the black background fade in
			if (fadeIn < fadeOut) {
				fadeIn += 3;
			}

			// Completes the fade in animation
			if (fadeIn == fadeOut) {
				fadedOut = false;
				fadedIn = true;
				fadeOut = 255;
				fadeIn = 0;
				interval = maxInterval;
				Game.gameState = "intro";

				// Stops the title screen song
				if (Game.currentSong != null)
					Game.currentSong.stop();

				// Starts the introduction song
				if (Game.music) {
					Game.currentSong = Game.sounds.intro;
					Game.currentSong.play();
				}

			}

		}

	}

	/**
	 * Renders the game's options
	 * 
	 * @param g - the graphic component used to render the whole game
	 */
	private void renderOptions(Graphics g) {
		// Title background
		titleBackground(g);

		// Menu instructions
		menuInstructions(g);	

		// Menu cursor render
		int[] cursorY = new int[3];
		cursorY[0] = Game.height - (32 * Game.scale) * 6;
		cursorY[1] = Game.height - (32 * Game.scale) * 4;
		cursorY[2] = Game.height - (32 * Game.scale) * 2;

		// Options highlight
		g.setColor(new Color(248, 144, 32));
		g.setFont(new Font("Calibri", Font.BOLD, 16 * Game.scale));
		g.drawString("MUSIC", Game.width / 5, Game.height - (32 * Game.scale) * 6);
		g.drawString("SFX", Game.width / 5, Game.height - (32 * Game.scale) * 4);
		g.drawString("SCREEN SIZE", Game.width / 5, Game.height - (32 * Game.scale) * 2);

		String music = (Game.music) ? "ON" : "OFF";
		String sfx = (Game.sfx) ? "ON" : "OFF";
		g.drawString(music, Game.width - 128 * Game.scale, Game.height - (32 * Game.scale) * 6);
		g.drawString(sfx, Game.width - 128 * Game.scale, Game.height - (32 * Game.scale) * 4);
		g.drawString(resolutionOptions[resolution], Game.width - 128 * Game.scale, Game.height - (32 * Game.scale) * 2);
		g.drawString("->", Game.width / 5 - 16 * Game.scale, cursorY[cursor]);

		// Screen resizing notice
		if (resolution + 1 != Game.scale)
			g.drawString("Restart the game to apply resolution setting.", 12 * Game.scale,
					Game.height - (16 * Game.scale) * 2);

		// Options strings
		g.setColor(new Color(248, 216, 32));
		g.setFont(new Font("Calibri", Font.PLAIN, 16 * Game.scale));
		g.drawString("MUSIC", Game.width / 5, Game.height - (32 * Game.scale) * 6);
		g.drawString("SFX", Game.width / 5, Game.height - (32 * Game.scale) * 4);
		g.drawString("SCREEN SIZE", Game.width / 5, Game.height - (32 * Game.scale) * 2);
		g.drawString(music, Game.width - 128 * Game.scale, Game.height - (32 * Game.scale) * 6);
		g.drawString(sfx, Game.width - 128 * Game.scale, Game.height - (32 * Game.scale) * 4);
		g.drawString(resolutionOptions[resolution], Game.width - 128 * Game.scale, Game.height - (32 * Game.scale) * 2);
		g.drawString("->", Game.width / 5 - 16 * Game.scale, cursorY[cursor]);
	}

	/**
	 * Renders while the game is being played
	 * 
	 * @param g
	 */
	private void renderPlaying(Graphics g) {
		// Player info
		renderPlayerUI(g);

		// If the screen is faded in, then fade it out!
		if (fadedIn) {

			// Color black for fade out
			g.setColor(new Color(0, 0, 0, fadeOut));

			// Black background for fade out
			g.fillRect(0, 0, Game.width, Game.height);

			// Makes the black background fade out
			if (fadeOut > fadeIn) {
				fadeOut -= 3;
			}

			// Completes the fade out animation
			if (fadeOut == fadeIn) {
				fadedIn = false;
				fadeOut = 255;
				fadedOut = true;
			}
		}
	}

	/**
	 * Renders Lives, Eggs and High-Score
	 * 
	 * @param g
	 */
	private void renderPlayerUI(Graphics g) {
		// Life Icon
		g.drawImage(lifeIcon, 0, 0, 21 * Game.scale, 17 * Game.scale, null);

		// Egg
		g.drawImage(eggIcon, (Game.width / 2) - 26 * Game.scale, 0, 17 * Game.scale, 17 * Game.scale, null);

		// Draw highlight
		g.setColor(new Color(248, 144, 32));
		g.setFont(new Font("Calibri", Font.BOLD, 16 * Game.scale));
		g.drawString(Player.life + "/3", 21 * Game.scale, 14 * Game.scale);
		g.drawString(Player.eggs.size() + "/5", (Game.width / 2) - 8 * Game.scale, 14 * Game.scale);
		g.drawString("SCORE: " + Player.score, Game.width - 100 * Game.scale, 14 * Game.scale);

		// Draw text
		g.setColor(new Color(248, 216, 32));
		g.setFont(new Font("Calibri", Font.PLAIN, 16 * Game.scale));
		g.drawString(Player.life + "/3", 21 * Game.scale, 14 * Game.scale);
		g.drawString(Player.eggs.size() + "/5", (Game.width / 2) - 8 * Game.scale, 14 * Game.scale);
		g.drawString("SCORE: " + Player.score, Game.width - 100 * Game.scale, 14 * Game.scale);

	}

	/**
	 * Renders menu instructions
	 * 
	 * @param g - the graphic component used to render the whole game
	 */
	private void menuInstructions(Graphics g) {
		// Render game instructions
		g.setColor(new Color(248, 144, 32));
		g.setFont(new Font("Calibri", Font.BOLD, 8 * Game.scale));
		g.drawImage(wkey, Game.width - 96 * Game.scale, Game.height - 16 * Game.scale, 16 * Game.scale, 16 * Game.scale,
				null);
		g.drawImage(skey, Game.width - 48 * Game.scale, Game.height - 16 * Game.scale, 16 * Game.scale, 16 * Game.scale,
				null);
		g.drawImage(space, Game.width - 192 * Game.scale, Game.height - 16 * Game.scale, 49 * Game.scale,
				16 * Game.scale, null);
		g.drawString(": UP", Game.width - 80 * Game.scale, Game.height - 5 * Game.scale);
		g.drawString(": DOWN", Game.width - 32 * Game.scale, Game.height - 5 * Game.scale);
		g.drawString(": CONFIRM", Game.width - 143 * Game.scale, Game.height - 5 * Game.scale);

		if (Game.gameState.equals("options")) {
			g.drawImage(backspace, Game.width - 272 * Game.scale, Game.height - 16 * Game.scale, 32 * Game.scale,
					16 * Game.scale, null);
			g.drawString(": GO BACK", Game.width - 240 * Game.scale, Game.height - 5 * Game.scale);
		}

	}

	/**
	 * Renders the title background
	 * 
	 * @param g - the graphic component used to render the whole game
	 */
	private void titleBackground(Graphics g) {
		// If the first image is outside the screen, reset both images position
		if (titleBgX <= -Game.width) {
			titleBgX = 0;
			titleBgX2 = Game.width;
		}

		// Makes the horizontal scrolling effect
		titleBgX--;
		titleBgX2--;

		// Draws the background image for the title
		g.drawImage(titleBg, titleBgX, 0, Game.width, Game.height, null);
		g.drawImage(titleBg, titleBgX2, 0, Game.width, Game.height, null);
	}
}
