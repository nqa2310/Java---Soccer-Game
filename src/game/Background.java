package game;

import game.renderer.Renderer;
import tklibs.SpriteUtils;

import javax.imageio.metadata.IIOMetadataFormatImpl;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;

public class Background extends GameObject {
    BufferedImage backgroundImage;
    public Background() {
        backgroundImage = SpriteUtils.loadImage("assets/images/background/445906.png");
    }

    @Override
    public void render(Graphics g) {
//        Image image = backgroundImage.getScaledInstance(Settings.GAME_WIDTH,Settings.GAME_HEIGHT,1);
        g.drawImage(backgroundImage,0,0,null);
    }
}