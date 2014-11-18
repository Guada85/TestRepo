package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends Canvas {

	/** The Strategy that allows us to use accelerate page flipping */
	private BufferStrategy strategy;
	/** True if the game is currently "running", i.e. the game loop is looping */
	private boolean gameRunning;
	/** The list of all the entities that exist in our game */
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	/** The list of entities that need to be removed from the game this loop */
	private ArrayList<Entity> removeList = new ArrayList<Entity>();
	/** The entity representing the player */
	private Entity ship;
	/** The speed at which the player's ship should move (pixels/sec) */
	private double moveSpeed = 300;
	/** The time at which last fired a shot */
	private long lastFire = 0;
	/** The interval between our players shot (ms) */
	private long firingInterval = 500;
	/** The number of aliens left on the screen */
	private int alienCount;

	/** The message to display which waiting for a key press */
	private String message = "";
	/** True if we're holding up game play until a key has been pressed */
	private boolean waitingForKeyPress = true;
	/** True if the left cursor key is currently pressed */
	private boolean leftPressed = false;
	/** True if the right cursor key is currently pressed */
	private boolean rightPressed = false;
	/** True if we are firing */
	private boolean firePressed = false;
	/**
	 * True if game logic needs to be applied this loop, normally as a result of
	 * a game event
	 */
	private boolean logicRequiredThisLoop = false;

	/**
	 * Construct our Game and set it running
	 */
	public Game() {
		// create a frame to contain our game
		JFrame container = new JFrame("Guda Space Invaders");

		// get hold the content of the frame and set up the resolution of the
		// game
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(800, 600));
		panel.setLayout(null);

		// set up our canvas size and put it into the content of the frame
		setBounds(0, 0, 800, 600);
		panel.add(this);

		// tell AWT not to bother repainting our canvas since we're going to do
		// that our self in accelerated mode
		setIgnoreRepaint(true);

		// finally make the window visible
		container.pack();
		container.setResizable(false);
		container.setVisible(true);

		// create a buffering strategy which will allow AWT to manage our
		// accelerated graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();

		
		addKeyListener(new KeyImputHandler());
		
		
		// TEST
		gameRunning = true;
		initEntity();

	}

	private void startGame() {
		// clear out any existing entities and initialize a new set
		entities.clear();
		initEntity();
	}

	/**
	 * The main game loop. This loop is running during all game play as is
	 * responsible for the following activities:
	 * <p>
	 * - Working out the speed of the game loop to update moves - Moving the
	 * game entities - Drawing the screen contents (entities, text) - Updating
	 * game events - Checking Input
	 * <p>
	 */
	public void gameLoop() {

		long lastLoopTime = System.currentTimeMillis();

		// keep looping round until the game ends
		while (gameRunning) {
			// work out how long its been since the last update, this will be
			// used to calculate
			// how far the entities should move this loop
			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();

			// get hold of a graphics context for the accelerated surface and
			// blank it out
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, 800, 600);

			// cycle round asking each entity to move itself
			if (!waitingForKeyPress) {
				for (int i = 0; i < entities.size(); i++) {
					Entity entity = (Entity) entities.get(i);
					entity.move(delta);
				}
			}

			// cycle round drawing all the entities we have in the game
			for (int i = 0; i < entities.size(); i++) {
				Entity entity = (Entity) entities.get(i);
				entity.draw(g);
			}

			// finally, we've completed drawing so clear up the graphics and
			// flip the buffer over
			g.dispose();
			strategy.show();
			
			
			// resolve the movement of the ship. First assume the ship 
			// isn't moving. If either cursor key is pressed then
			// update the movement appropriately
			
			
			
			// finally pause for a bit.
			// Note: this should run us at about 100fps but on windows this
			// might vary each loop due to
			// a bad implementation of timer
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	private void initEntity() {
		// create the play ship and place it roughly in the center of the screen
		ship = new ShipEntity(this, "resources/ship.gif", 370, 550);
		entities.add(ship);

		// create a block of aliens (5 rows, by 12 alien,spaced evenly)
		alienCount = 0;
		for (int row = 0; row < 5; row++) {
			for (int x = 0; x < 12; x++) {
				Entity alien = new AlienEntity(this, "resources/alien.gif", 100 + (x * 50), (50) + row * 30);
				entities.add(alien);
				alienCount++;
			}

		}
	}

	public static void main(String[] args) {
		Game g = new Game();

		g.gameLoop();
	}

	public void removeEntity(Entity entity) {
		// TODO Auto-generated method stub

	}

	public void updateLogic() {
		// TODO Auto-generated method stub

	}

	private class KeyImputHandler extends KeyAdapter {

		public void keyPressed(KeyEvent e) {

			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				leftPressed = true;
				break;

			case KeyEvent.VK_RIGHT:
				rightPressed = true;
				break;

			case KeyEvent.VK_SPACE:
				firePressed = true;
				break;

			default:
				break;
			}
		}

		public void keyReleased(KeyEvent e) {

			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				leftPressed = false;
				break;

			case KeyEvent.VK_RIGHT:
				rightPressed = false;
				break;

			case KeyEvent.VK_SPACE:
				firePressed = false;
				break;

			default:
				break;
			}
		}
		
		public void keyTyped(KeyEvent e){
			// if we hit escape,then quit the game
			if (e.getKeyChar() == 27){
				System.exit(0);
			}
		}
	}

}
