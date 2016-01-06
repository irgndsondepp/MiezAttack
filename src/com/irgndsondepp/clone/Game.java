package com.irgndsondepp.clone;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

import com.irgndsondepp.clone.graphics.menu.Highscores;
import com.irgndsondepp.clone.audio.Sound;
import com.irgndsondepp.clone.entity.mob.Player;
import com.irgndsondepp.clone.graphics.menu.MenuScreen;
import com.irgndsondepp.clone.graphics.Screen;
import com.irgndsondepp.clone.graphics.menu.HighscoreScreen;
import com.irgndsondepp.clone.graphics.menu.MainMenuScreen;
import com.irgndsondepp.clone.input.Keyboard;
import com.irgndsondepp.clone.input.Mouse;
import com.irgndsondepp.clone.level.Level;
import com.irgndsondepp.clone.level.SpawnLevel;
import com.irgndsondepp.clone.level.TileCoordinate;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int width = 600;
	public static int height = width / 16 * 9;
	public static int scale = 2;
	public static int framerate = 60;
	public static String title = "Clone Game";	
	public static int startingMiezen = 200;
	// show the game in fullscreen mode
	private boolean fullscreen = true;
	// show dynamicLighting
	// 0 = no dynamic Lighting
	// 1 = burn and explosion lighting
	// 2 = radiance around player
	public static int dynamicLighting = 1;
	// play sounds and music
	public static boolean mute = false;
	
	private Thread thread;
	private JFrame frame;
	private boolean running = false;

	private Screen screen;
	private MenuScreen menuScreen;
	private Keyboard key;
	private Level level;
	private Player player;
	private Random random = new Random();

	

	private BufferedImage image = new BufferedImage(width, height,
			BufferedImage.TYPE_INT_RGB);
	// Als DataBufferInt gecastet Raster == Array des Bilds
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
			.getData();

	/**
	 * create a new game
	 */
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		frame = new JFrame();
		key = new Keyboard();
		screen = new Screen(width, height);
		menuScreen = new MainMenuScreen(this);
		// level = new RandomLevel(64, 64);
		level = new SpawnLevel("/levels/spawn.png", this);

		TileCoordinate playerSpawn = new TileCoordinate(50, 50);

		level.add(player = new Player(playerSpawn.getX(), playerSpawn.getY(),
				key));

		addKeyListener(key);

		Mouse mouse = new Mouse();

		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		this.setCursor(frame.getToolkit().createCustomCursor(
				new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),
				new Point(0, 0), "null"));

	}

	/**
	 * start the game process -> see run()
	 */
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Dispay");
		thread.start();
	}

	/**
	 * stop the game thread
	 */
	public synchronized void stop() {
		running = false;
		frame.dispose();
		System.exit(0);
		try {

			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();

		}

	}

	/**
	 * the games run() method. contains the while(running) loop and calls the update() and render() methods. also calculates the framerate
	 */
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1.0e9 / ((double) framerate);
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}

			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("Fps: " + frames + ", Updates:" +
				// updates);
				frame.setTitle(title + "    fps: " + frames + "   updates: "
						+ updates);
				frames = 0;
				updates = 0;
			}
			if (key.exit)
				running=false;
		}
		stop();
	}

	/**
	 * call the update() methods for the screen or the level
	 */
	public void update() {
		if (!menuScreen.isActive() && level != null) {
			level.update();
			if (Sound.menu_theme.isPlaying()) {
				Sound.menu_theme.stop();
			}
		} else {
			if (!(Sound.menu_theme.isPlaying())) {
				Sound.menu_theme.playSound();
			}
			if (key.up) {
				menuScreen.up();
			}
			if (key.down) {
				menuScreen.down();
			}
			if (key.left) {
				menuScreen.left();
			}
			if (key.right) {
				menuScreen.right();
			}
			if (key.enter) {
				menuScreen.select();
			}
			if (key.exit) {
				menuScreen.exit();
			}
			menuScreen.update();
		}
		key.update();

	}

	/**
	 * render a menuscreen or level. also overlays some text for now.
	 */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		// Triple Buffering um ein zweites Bild gleichzeitig zu berechnen und im
		// Buffer zu speichern
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();

		if (!menuScreen.isActive() && level != null) {
			int xScroll = player.getX() - screen.width / 2;
			int yScroll = player.getY() - screen.height / 2;
			level.render(xScroll, yScroll, screen);
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = screen.pixels[i];
			}
		} else {
			menuScreen.render();
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = menuScreen.pixels[i];
			}
		}

		// screen.renderSheet(40, 40, SpriteSheet.player_down, false);

		Graphics g = bs.getDrawGraphics();
		// alle Grafiken in den Bereich zwischen den geschweiften Klammern
		{
			// drawImage(Bild,startx,starty,breite,höhe,ImageObserver???)
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			// g.fillRect(Mouse.getX()-10, Mouse.getY()-10, 20, 20);
			// g.drawString("Button:" + Mouse.getB(),50,50);

			if (menuScreen.isActive()
					&& (menuScreen instanceof HighscoreScreen)) {
				HighscoreScreen hscreen = (HighscoreScreen) menuScreen;
				Highscores scores = hscreen.getHighscores();
				g.setColor(Color.BLACK);
				g.setFont(new Font("Verdana", 20, getScreenHeight() / 40));
				g.drawString("#" + "   " + "Name" + "    " + "Kills" + "    "
						+ "Kill Streak" + "   " + "% of Level destroyed",
						(getScreenWidth() / 2 - 12 * getScreenHeight() / 40),
						((getScreenHeight() / 13)));
				for (int i = 0; i < scores.kills.length; i++) {
					g.setColor(Color.BLACK);
					g.setFont(new Font("Verdana", 20, getScreenHeight() / 40));
					g.drawString(
							"" + (i + 1) + "   " + scores.names[i] + "    "
									+ scores.kills[i] + "    "
									+ scores.killStreak[i] + "    "
									+ scores.levelDestruction[i],
							(getScreenWidth() / 2 - 6 * getScreenHeight() / 40),
							((i + 3) * (getScreenHeight() / 16)));
				}

			}

			if (!menuScreen.isActive()) {
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, this.frame.getWidth(), 30);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Verdana", 20, 20));
				g.drawString("Kills: " + level.killCounter, 20, 20);
				g.drawString("Miezen: " + level.getMiezen(),
						this.frame.getWidth() - 150, 20);
				g.drawString("HP: " + level.getClientPlayer().getHitpoints(),
						130, 20);
				if (level.isMiezAttack()
						&& !(level.getMiezAttackCounter() % 10 == 0)) {
					g.setColor(Color.RED);
					g.drawString(
							"MIEZ ATTACK LEVEL " + level.getMiezAttackLevel()
									+ "!!!", this.frame.getWidth() / 2 - 350,
							20);
				}
				g.setColor(Color.WHITE);
				if (player.getGun() != null) {

					g.drawString("Shots: " + player.getGun().getShotsLeft()
							+ " / " + player.getGun().getShotsTotal(),
							this.frame.getWidth() - 350, 20);
				}

				if (level.isMessageShowing()) {
					int kills = level.getMultiKillCounter();
					if (kills >= 25) {

						g.setColor(Color.RED);
						g.setFont(new Font("Verdana", 80, 80));

						g.drawString(
								"YOU PSYCHO!!!!!",
								this.frame.getWidth() / 2 - 190
										+ random.nextInt(7),
								this.frame.getHeight() / 2 - 40
										+ random.nextInt(7));
					} else if (kills >= 20 && kills < 25) {

						g.setColor(Color.RED);
						g.setFont(new Font("Verdana", 60, 60));

						g.drawString(
								"BARBARIC!!!!!",
								this.frame.getWidth() / 2 - 170
										+ random.nextInt(5),
								this.frame.getHeight() / 2 - 30
										+ random.nextInt(5));

					} else if (kills >= 15 && kills < 20) {

						g.setColor(Color.BLUE);
						g.setFont(new Font("Verdana", 50, 50));
						if (!(level.getMultiKillTimer() % 10 == 0)) {
							g.drawString("INCREDIBLE!!!!",
									this.frame.getWidth() / 2 - 130,
									this.frame.getHeight() / 2 - 25);
						}

					} else if (kills >= 10 && kills < 15) {

						g.setColor(Color.BLUE);
						g.setFont(new Font("Verdana", 40, 40));
						if (!(level.getMultiKillTimer() % 10 == 0)) {
							g.drawString("AWESOME!!!",
									this.frame.getWidth() / 2 - 100,
									this.frame.getHeight() / 2 - 17);
						}

					}
					if (kills >= 5 && kills < 10) {

						g.setColor(Color.BLUE);
						g.setFont(new Font("Verdana", 30, 30));
						if (!(level.getMultiKillTimer() % 10 == 0)) {
							g.drawString("BRUTAL!!",
									this.frame.getWidth() / 2 - 100,
									this.frame.getHeight() / 2 - 15);
						}

					}

				}
			}

		}
		g.dispose();
		bs.show();
	}

	/**
	 * the main method initializes the game and starts a new game thread
	 * @param ars
	 */
	public static void main(String[] ars) {
		Game game = new Game();
		if (game.fullscreen) {
			game.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
			game.frame.setUndecorated(true);
		}
		game.frame.setResizable(false);

		game.frame.setTitle("Clone Game");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.start();
	}

	/**
	 * get the window width
	 * @return
	 */
	public static int getWindowWidth() {
		return width * scale;
	}

	/**
	 * get the window height
	 * @return
	 */
	public static int getWindowHeight() {
		// TODO Auto-generated method stub
		return height * scale;
	}

	/**
	 * go to a menuscreen
	 * @param screen
	 */
	public void setMenuScreen(Screen screen) {
		this.screen = screen;

	}

	/**
	 * initialize a level and start a new round
	 */
	public void makeNewLevel() {
		// TODO Auto-generated method stub
		TileCoordinate playerSpawn = new TileCoordinate(50, 50);

		this.level = new SpawnLevel("/levels/spawn.png", this);
		level.add(player = new Player(playerSpawn.getX(), playerSpawn.getY(),
				key));
	}

	/**
	 * go to a menuscreen
	 * @param menuScreen
	 */
	public void setMenuScreen(MenuScreen menuScreen) {
		this.menuScreen = menuScreen;
	}

	/**
	 * return the screen width
	 * @return
	 */
	public int getScreenWidth() {
		return this.frame.getWidth();
	}

	/**
	 * return the screen height
	 * @return
	 */
	public int getScreenHeight() {
		return this.frame.getHeight();
	}

}
