package main.game;

import main.graphics.TextureAtlas;

import java.awt.image.BufferedImage;

public enum TEXTURES {
    PAUSE_EMPTY(289, 200, 39, 7),
    PAUSE(289, 176, 39, 7);

    private int	x, y, h, w;

    TEXTURES(int x, int y, int h, int w) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public BufferedImage texture(TextureAtlas atlas) {
        return atlas.cut(x, y, w, h);
    }
}
