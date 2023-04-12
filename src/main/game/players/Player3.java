package main.game.players;

import main.IO.Input;
import main.game.Entity;
import main.game.EntityType;
import main.game.Game;
import main.graphics.Sprite;
import main.graphics.SpriteSheet;
import main.graphics.TextureAtlas;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Player3 extends Entity {

	public static final int	SPRITE_SCALE		= 16;
	public static final int	SPRITES_PER_HEADING	= 1;

	private enum Heading {
		NORTH((0 + 8) * SPRITE_SCALE, 8 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
		EAST((6 + 8) * SPRITE_SCALE, 8 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
		SOUTH((4 + 8) * SPRITE_SCALE, 8 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
		WEST((2 + 8) * SPRITE_SCALE, 8 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE);

		private int	x, y, h, w;

		Heading(int x, int y, int h, int w) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}

		protected BufferedImage texture(TextureAtlas atlas) {
			return atlas.cut(x, y, w, h);
		}
	}

	private Heading					heading;
	private Map<Heading, Sprite>	spriteMap;
	private float					scale;
	private float					speed;

	public Player3(float x, float y, float scale, float speed, TextureAtlas atlas) {
		super(EntityType.Player, x, y);

		heading = Heading.NORTH;
		spriteMap = new HashMap<Heading, Sprite>();
		this.scale = scale;
		this.speed = speed;

		for (Heading h : Heading.values()) {
			SpriteSheet sheet = new SpriteSheet(h.texture(atlas), SPRITES_PER_HEADING, SPRITE_SCALE);
			Sprite sprite = new Sprite(sheet, scale);
			spriteMap.put(h, sprite);
		}

	}

	@Override
	public void update(Input input) {

		float newX = x;
		float newY = y;

		if (input.getKey(KeyEvent.VK_NUMPAD8)) {
			newY -= speed;
			heading = Heading.NORTH;
		} else if (input.getKey(KeyEvent.VK_NUMPAD6)) {
			newX += speed;
			heading = Heading.EAST;
		} else if (input.getKey(KeyEvent.VK_NUMPAD5)) {
			newY += speed;
			heading = Heading.SOUTH;
		} else if (input.getKey(KeyEvent.VK_NUMPAD4)) {
			newX -= speed;
			heading = Heading.WEST;
		}

		if (newX < 0) {
			newX = 0;
		} else if (newX >= Game.WIDTH - SPRITE_SCALE * scale) {
			newX = Game.WIDTH - SPRITE_SCALE * scale;
		}

		if (newY < 0) {
			newY = 0;
		} else if (newY >= Game.HEIGHT - SPRITE_SCALE * scale) {
			newY = Game.HEIGHT - SPRITE_SCALE * scale;
		}

		x = newX;
		y = newY;

	}

	@Override
	public void render(Graphics2D g) {
		spriteMap.get(heading).render(g, x, y);
	}

}
