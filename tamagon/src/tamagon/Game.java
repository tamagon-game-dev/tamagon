package tamagon;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {

	/**
	 * Screen scale, width and height
	 */
	static int scale = 4, width, height;

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
	static UserInterface ui;

	/**
	 * Current game state
	 */
	static String gameState = "playing";

	/**
	 * Game's highest score
	 */
	static int highscore = 0;

	/**
	 * Game version
	 */
	static String version = "1.0.0";

	/**
	 * Game sounds
	 */
	static Sound sounds;

	/**
	 * Music options
	 */
	static boolean music = true, sfx = true;

	/**
	 * For toggling music on/off
	 */
	static AudioClip currentSong;

	/**
	 * Game levels
	 */
	static Level level;

	/**
	 * Game entities
	 */
	 static ArrayList<Entity> entities;
	
	/**
	 * Level number
	 */
	static int levelNumber = 1;
	
	/**
	 * Player
	 */
	static Player player;
	

	/**
	 * Initializes game's objects
	 */
	public Game() {
		// Makes the game listen to keyboard events
		this.addKeyListener(this);

		// Makes the game listen to mouse events
		this.addMouseListener(this);

		// Gets player's settings
		try {

			File file = new File("data/settings.txt");
			if (!file.exists()) {
				// If file doesn't exist, create the directory and file

				File dir = new File("data");
				if (!dir.exists()) {
					dir.mkdirs();
				}

				BufferedWriter bw = new BufferedWriter(new FileWriter("data/settings.txt", false));

				// Create high score
				bw.write("0");
				bw.newLine();

				// Create music setting
				bw.write("true");
				bw.newLine();

				// Create sound effects setting
				bw.write("true");
				bw.newLine();

				// Create screen size setting
				bw.write("2");
				bw.close();

			} else {
				// If file exists, just read it from it
				BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));

				String hiscore = br.readLine();
				String msc = br.readLine();
				String efc = br.readLine();
				String scr = br.readLine();

				highscore = Integer.valueOf(hiscore);
				music = Boolean.valueOf(msc);
				sfx = Boolean.valueOf(efc);
				scale = Integer.valueOf(scr);

				br.close();
			}
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		}

		// Setting screen scale
		width = 320 * scale;
		height = 224 * scale;

		// setting up the screen size
		this.setPreferredSize(new Dimension(width, height));

		// sets up the game's canvas
		this.gameCanvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Initializes game sounds
		sounds = new Sound();

		// Initializes the game's interfaces
		ui = new UserInterface();

		// Initializes entities sprite
		new SpriteLoader();

		// Initializes the game's entities
		entities = new ArrayList<Entity>();

		// Initializes the game's level
		level = new Level("level1");
	}

	/**
	 * Executes the game
	 * 
	 * @param args - Program arguments
	 */
	public static void main(String[] args) {
		Game game = new Game();
		JFrame window = new JFrame();

		// Save game settings before closing
		window.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter("data/settings.txt", false));

					// Save high score
					bw.write(String.valueOf(highscore));
					bw.newLine();

					// Save music setting
					bw.write(String.valueOf(music));
					bw.newLine();

					// Save sound effects setting
					bw.write(String.valueOf(sfx));
					bw.newLine();

					// Save screen settings
					bw.write(String.valueOf(ui.resolution + 1));
					bw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		});

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

		// Music control
		if (music) {
			if (Game.gameState.equals("title") || Game.gameState.equals("options")) {
				sounds.titlescreen.play();
				currentSong = sounds.titlescreen;
			}
		} else {
			if (currentSong != null)
				currentSong.stop();
		}

		// Entity control
		if (gameState.equals("playing")) {
			// Entity logic
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).update();
			}

		}

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

		if (Game.gameState.equals("playing")) {
			
			// Render level
			level.renderBackground(graphics);
			
			// Renders the level
			level.render(graphics);

			// Renders the entities
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).render(graphics);
			}
			
		}

		// Renders the graphic interface
		ui.render(graphics);

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
		requestFocus();

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

			// Navigates title menu
			if (arg0.getKeyCode() == KeyEvent.VK_W) {
				ui.cursor = 0;
				if (sfx)
					sounds.cursor.play();
			} else if (arg0.getKeyCode() == KeyEvent.VK_S) {
				ui.cursor = 1;
				if (sfx)
					sounds.cursor.play();
			}

		} else if (gameState.equals("options")) {

			// Navigates option menu
			if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {

				// Goes back to title menu
				gameState = "title";
				ui.cursor = 0;
			} else if (arg0.getKeyCode() == KeyEvent.VK_W) {
				ui.cursor -= 1;
				if (sfx)
					sounds.cursor.play();
			} else if (arg0.getKeyCode() == KeyEvent.VK_S) {
				ui.cursor += 1;
				if (sfx)
					sounds.cursor.play();
			}
		} else if (gameState.equals("playing")) {

			// Player movement
			if (arg0.getKeyCode() == KeyEvent.VK_D) {
				Player.right = true;
			} else if (arg0.getKeyCode() == KeyEvent.VK_A) {
				Player.left = true;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_SPACE && gameState.equals("options")) {
			// Music and SFX toggles
			if (ui.cursor == 0) {
				music = (music) ? false : true;
			} else if (ui.cursor == 1) {
				sfx = (sfx) ? false : true;
			} else if (ui.cursor == 2) {
				ui.resolution += 1;
			}
		} else if (arg0.getKeyCode() == KeyEvent.VK_SPACE && gameState.equals("title")) {
			// Enters the options menu
			if (ui.cursor == 1) {
				gameState = "options";
				ui.cursor = 0;
			} else if (ui.cursor == 0) {
				// Starts the game
				gameState = "starting";
			}
		} else if (gameState.equals("playing")) {

			// Stops player movement
			if (arg0.getKeyCode() == KeyEvent.VK_D) {
				Player.right = false;
			} else if (arg0.getKeyCode() == KeyEvent.VK_A) {
				Player.left = false;
			} else if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
				Player.jump = true;

				// jump sound
				if (sfx) {
					sounds.jump.play();
				}

			} else if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {

				// Game pause
				gameState = "paused";

			}

		} else if (gameState.equals("paused")) {
			
			if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {

				// revert pause
				gameState = "playing";

			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (gameState.equals("playing")) {
			Player.attack = true;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (gameState.equals("playing")) {
			Player.attack = false;
		}
	}

}
