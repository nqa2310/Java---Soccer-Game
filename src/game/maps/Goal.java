package game.maps;

import game.GameObject;
import game.renderer.Renderer;
import tklibs.SpriteUtils;

import java.awt.image.BufferedImage;

public class Goal extends GameObject {

    public Goal(int x, int y, String url) {
        BufferedImage image = SpriteUtils.loadImage(url);
        renderer = new Renderer(image);
        position.set(x,y);
    }

}
