package game;

import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background extends GameObject {
    BufferedImage backgroundImage;
    public Background() {
        backgroundImage = SpriteUtils.loadImage("assets/images/background/445906.png");
    }

    @Override
    public void render(Graphics g, ViewPort viewPort) {
//        Image image = backgroundImage.getScaledInstance(Settings.GAME_WIDTH,Settings.GAME_HEIGHT,1);
        g.drawImage(backgroundImage,0,0,null);
    }
}