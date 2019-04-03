package game.maps;

import game.GameObject;
import game.ViewPort;
import game.physics.BoxCollider;
import game.renderer.Renderer;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GoalLeft extends GameObject {
    public GoalLeft() {
    }

    public GoalLeft(int x, int y, String url) {
        BufferedImage image = SpriteUtils.loadImage(url);
        renderer = new Renderer(image);
        position.set(x,y);
        hitBox = new BoxCollider(this,image.getWidth()/100,image.getHeight());
    }

    @Override
    public void render(Graphics g, ViewPort viewPort) {
        super.render(g, viewPort);
    }
}
