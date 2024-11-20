package tamagon;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

	/**
	 * Screen scale, width and height
	 */
	static int scale = 4, width = 320 * scale, height = 224 * scale;

	/**
	 * frames per second
	 */
	private final int fps = 60;

	/**
	 * Game's canvas
	 */
	private BufferedImage gameCanvas;

	/**
	 * Game's title
	 */
	public final String title = "Tamagon";

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Renders the user's interface
	 */
	private UserInterface ui;

	/**
	 * Current game state
	 */
	static String gameState = "logo";

	/**
	 * Game's highest score
	 */
	static int highscore = 0;

	/**
	 * For writing and reading files
	 */
	private BufferedReader bufferedReader;

	/**
	 * Game version
	 */
	static String version = "1.0.0";

	/**
	 * Initializes game's objects
	 */
	public Game() {
		// Makes the game listen to keyboard events
		this.addKeyListener(this);

		// setting up the screen size
		this.setPreferredSize(new Dimension(width, height));

		// sets up the game's canvas
		this.gameCanvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Gets player's high score
		try {
			InputStream inputStream = Game.class.getClassLoader().getResourceAsStream("hiscore.txt");
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String hiscore = bufferedReader.readLine();
			highscore = Integer.valueOf(hiscore);
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		}

		// Initializes the game's interfaces
		this.ui = new UserInterface();
	}

	/**
	 * Executes the game
	 * 
	 * @param args - Program arguments
	 */
	public static void main(String[] args) {
		Game game = new Game();
		JFrame window = new JFrame();

		// Adding the game application to the window
		window.add(game);

		// Sets the window's name
		window.setTitle(game.title);

		// Prevents screen resizing
		window.setResizable(false);

		// Applies the window's dimensions
		window.pack();

		// Placing the window on the screen's center
		window.setLocationRelativeTo(null);

		// By clicking the X button on the window, it will make the application close
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Makes the window visible
		window.setVisible(true);

		// Starts the game thread
		new Thread(game).start();
	}

	/**
	 * Game logic
	 */
	private void update() {

	}

	/**
	 * Game graphics
	 */
	private void render() {
		// Canva's buffer strategy
		BufferStrategy bufferStrategy = this.getBufferStrategy();

		// If there's no buffer strategy
		if (bufferStrategy == null) {

			// Create a new buffer strategy, to optimize rendering
			this.createBufferStrategy(3);
			return;
		}

		// Gets the canva's graphics
		Graphics graphics = gameCanvas.getGraphics();

		// Renders the graphic interface
		this.ui.render(graphics);

		// Clears rendered resources
		graphics.dispose();

		// Re-creates a new graphic component, using the current buffer this time
		graphics = bufferStrategy.getDrawGraphics();

		// Draws the game canvas
		graphics.drawImage(gameCanvas, 0, 0, width, height, null);

		// Makes the current buffer visible
		bufferStrategy.show();
	}

	/**
	 * Runnable class method
	 */
	@Override
	public void run() {

		// Renders the game and applies it's logic
		while (true) {
			update();
			render();

			// Frames per second
			try {
				Thread.sleep(1000 / fps);
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// Title screen commands
		if (gameState.equals("title")) {

			if (arg0.getKeyCode() == KeyEvent.VK_W) {
				ui.cursor = 0;
			} else if (arg0.getKeyCode() == KeyEvent.VK_S) {
				ui.cursor = 1;
			}

		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
