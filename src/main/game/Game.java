package main.game;

import java.awt.Graphics2D;

import main.IO.Input;
import main.display.Display;
import main.game.players.*;
import main.graphics.TextureAtlas;
import main.utils.Time;

public class Game implements Runnable {

	public static final int		WIDTH			= 800;
	public static final int		HEIGHT			= 600;
	public static final String	TITLE			= "Tanks";
	public static final int		CLEAR_COLOR		= 0xff000000;
	public static final int		NUM_BUFFERS		= 3;

	public static final float	UPDATE_RATE		= 60.0f;
	public static final float	UPDATE_INTERVAL	= Time.SECOND / UPDATE_RATE;
	public static final long	IDLE_TIME		= 1;

	public static final String	ATLAS_FILE_NAME	= "texture_atlas.png";

	private boolean				running;
	private Thread				gameThread;
	private Graphics2D			graphics;
	private Input				input;
	private TextureAtlas atlas;

	// players
	private Player player;
	private Player2 player2;
	private Player3 player3;
	private Player4 player4;
	private int players;

	public Game(int players) {
		this.players = players;
		running = false;
		Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
		graphics = Display.getGraphics();
		input = new Input();
		Display.addInputListener(input);
		atlas = new TextureAtlas(ATLAS_FILE_NAME);

		player = new Player(300, 300, 2, 3, atlas);
		if (players > 1) {
			player2 = new Player2(325, 300, 2, 3, atlas);
		}
		if (players > 2) {
			player3 = new Player3(345, 300, 2, 3, atlas);
		}
		if (players > 3) {
			player4 = new Player4(365, 300, 2, 3, atlas);
		}


	}

	public synchronized void start() {

		if (running)
			return;

		running = true;
		gameThread = new Thread(this);
		gameThread.start();

	}

	public synchronized void stop() {

		if (!running)
			return;

		running = false;

		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		cleanUp();

	}

	private void update() {
		player.update(input);
		if (players > 1) {
			player2.update(input);
		}
		if (players > 2) {
			player3.update(input);
		}
		if (players > 3) {
			player4.update(input);
		}
	}

	private void render() {
		Display.clear();
		player.render(graphics);
		if (players > 1) {
			player2.render(graphics);
		}
		if (players > 2) {
			player3.render(graphics);
		}
		if (players > 3) {
			player4.render(graphics);
		}
		Display.swapBuffers();
	}

	public void run() {

		int fps = 0;
		int upd = 0;
		int updl = 0;

		long count = 0;

		float delta = 0;

		long lastTime = Time.get();
		while (running) {
			long now = Time.get();
			long elapsedTime = now - lastTime;
			lastTime = now;

			count += elapsedTime;

			boolean render = false;
			delta += (elapsedTime / UPDATE_INTERVAL);
			while (delta > 1) {
				update();
				upd++;
				delta--;
				if (render) {
					updl++;
				} else {
					render = true;
				}
			}

			if (render) {
				render();
				fps++;
			} else {
				try {
					Thread.sleep(IDLE_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (count >= Time.SECOND) {
				Display.setTitle(TITLE + " || Fps: " + fps + " | Upd: " + upd + " | Updl: " + updl);
				upd = 0;
				fps = 0;
				updl = 0;
				count = 0;
			}

		}

	}

	private void cleanUp() {
		Display.destroy();
	}

	public void BREAK() {
		stop();
	}
}
