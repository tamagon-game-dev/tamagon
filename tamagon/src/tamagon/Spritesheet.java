package tamagon;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

	/**
	 * The loaded sprite sheet
	 */
	public BufferedImage spritesheet;

	/**
	 * Loads a sprite sheet
	 * 
	 * @param filename - File's name (without extension)
	 */
	public Spritesheet(String filename) {
		try {
			spritesheet = ImageIO.read(getClass().getResource("/" + filename + ".png"));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Gets a sprite from current sprite sheet from given positions (in pixel)
	 * 
	 * @param x - horizontal starting position
	 * @param y - vertical starting position
	 * @param w - sprite width
	 * @param h - sprite height
	 * @return
	 */
	public BufferedImage getSprite(int x, int y, int w, int h) {
		return spritesheet.getSubimage(x, y, w, h);
	}
}
