package tamagon;

import java.awt.Graphics;

public class Flagpole extends Entity {
	
	/**
	 * Animation variables
	 */
	private int animationFrames = 0, maxFrame = 5, maxIndex = 2, animationIndex = 0;

	/**
	 * Ends the level
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Flagpole(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update() {
		//Ends the level
		if (this.checkCollisionWithPlayer(this)) {
			Game.gameState = "levelend";
			if(Game.music) {
				Game.currentSong.stop();
				Game.currentSong = Game.sounds.levelEnd;
				Game.currentSong.play();
			}
		}
	}

	@Override
	public void render(Graphics g) {
		animationFrames++;
		
		// Animation frames
		if (animationFrames > maxFrame) {
			animationFrames = 0;
			animationIndex++;
			if (animationIndex > maxIndex) {
				animationIndex = 0;
			}
		}
		
		// Draw the sprite
		g.drawImage(SpriteLoader.flagpole[animationIndex], (x * Game.scale - Camera.x), y * Game.scale - Camera.y, w * Game.scale,
						h * Game.scale, null);
	}

}
