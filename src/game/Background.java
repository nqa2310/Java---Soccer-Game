package game;

import game.renderer.Renderer;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background extends GameObject {
    BufferedImage backgroundImage;
    public Background() {
        backgroundImage = SpriteUtils.loadImage("assets/images/background/Untitled.png");
        renderer = new Renderer(backgroundImage);
        position.set(1700,300);
    }

}