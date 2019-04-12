package game.ball;

import game.GameObject;
import game.Vector2D;
import game.ViewPort;
import game.renderer.Animation;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ballAnimation {
    BufferedImage image;
    Animation straight;
    Animation rollRight;
    Animation rollLeft;

    public ballAnimation() {
        image = SpriteUtils.loadImage("assets/images/ball/ball0.png");
        straight = new Animation(image);
        rollRight = new Animation("assets/images/ball/rollRight");
        rollLeft = new Animation("assets/images/ball/rollLeft");
        rollLeft.speed = 1;
        rollRight.speed = 1;
    }

    public void render(Graphics g, ViewPort viewPort, GameObject master, Vector2D velocity) {
        if (velocity.x > 0) {
            rollRight.render(g,viewPort.camera(master));
        }
        else if(velocity.x < 0) {
            rollLeft.render(g,viewPort.camera(master));
        }
        else {
            straight.render(g,viewPort.camera(master));
        }
    }
}
