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
	private BufferedImage hitoriLogo, titleBg, tamagonLogo, wkey, skey;

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
	 * Initializes user interface's objects
	 */
	public UserInterface() {
		UISpritesheet = new Spritesheet("ui");
		hitoriLogo = UISpritesheet.getSprite(0, 0, 122, 76);
		titleBg = UISpritesheet.getSprite(0, 76, 320, 224);
		tamagonLogo = UISpritesheet.getSprite(0, 300, 213, 62);
		wkey = UISpritesheet.getSprite(122, 0, 16, 16);
		skey = UISpritesheet.getSprite(122, 16, 16, 16);
	}

	/**
	 * Renders the graphic interface
	 * 
	 * @param g - the graphic component used to render the whole game
	 */
	public void render(Graphics g) {

		if (Game.gameState.equals("logo")) {
			renderLogo(g);
		} else if (Game.gameState.equals("title")) {
			renderTitle(g);
		}

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

			// Black background for fade un
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
	 * @param g
	 */
	private void renderTitle(Graphics g) {

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

		// Game logo animation
		if (tamagonLogoY <= Game.height / 6) {
			tamagonLogoY += Game.scale;
		}

		// Draws the game's logo
		g.drawImage(tamagonLogo, Game.width / 6, tamagonLogoY, tamagonLogo.getWidth() * Game.scale,
				tamagonLogo.getHeight() * Game.scale, null);
		
		//Menu cursor render
		int[] cursorY = new int[2];
		cursorY[0] = Game.width / 2;
		cursorY[1] = Game.width / 2 + 16 * Game.scale;
		
		
		// Draw the strings highlights
		g.setColor(new Color(248, 144, 32));
		g.setFont(new Font("Calibri", Font.BOLD, 16 * Game.scale));
		g.drawString("HI-SCORE: " + Game.highscore, Game.width / 3 + 16 * Game.scale, 32 * Game.scale);
		g.drawString("START", Game.width / 3 + 32 * Game.scale, Game.width / 2);
		g.drawString("OPTIONS", Game.width / 3 + 24 * Game.scale, Game.width / 2 + 16 * Game.scale);
		g.drawString("->", Game.width / 3, cursorY[cursor]);

		// Draw the strings
		g.setColor(new Color(248, 216, 32));
		g.setFont(new Font("Calibri", Font.PLAIN, 16 * Game.scale));
		g.drawString("HI-SCORE: " + Game.highscore, Game.width / 3 + 16 * Game.scale, 32 * Game.scale);
		g.drawString("START", Game.width / 3 + 32 * Game.scale, Game.width / 2);
		g.drawString("OPTIONS", Game.width / 3 + 24 * Game.scale, Game.width / 2 + 16 * Game.scale);
		g.drawString("->", Game.width / 3, cursorY[cursor]);

		// Render application version
		g.setFont(new Font("Calibri", Font.BOLD, 8 * Game.scale));
		g.drawString("version: " + Game.version, 0, Game.height - 8 * Game.scale);
		
		//Render game instructions
		g.setColor(new Color(248, 144, 32));
		g.drawImage(wkey, Game.width / 3, Game.height - 16*Game.scale, 16*Game.scale, 16*Game.scale,  null);
		g.drawImage(skey, Game.width / 2, Game.height - 16*Game.scale, 16*Game.scale, 16*Game.scale, null);
		g.drawString(": UP",Game.width / 3 + 16 * Game.scale, Game.height - 4*Game.scale);
		g.drawString(": DOWN",Game.width / 2 + 16 * Game.scale, Game.height - 4*Game.scale);

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
			}
		}

	}
}
