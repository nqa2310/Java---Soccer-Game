package game.player;

import game.GameObject;
import game.GameWindow;
import game.ViewPort;
import game.renderer.Animation;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerAnimation {
    public BufferedImage imageLeft;
    public BufferedImage imageRight;
    public Animation left;
    public Animation right;
    public Animation jumpLeft;
    public Animation jumpRight;
    public Animation walkRight;
    public Animation walkLeft;

    public PlayerAnimation() {
        imageLeft = SpriteUtils.loadImage("assets/images/players/left/player2 - Copy.png");
        left = new Animation(imageLeft);
        imageRight = SpriteUtils.loadImage("assets/images/players/right/player2 - Copy.png");
        right = new Animation(imageRight);
        walkRight = new Animation("assets/images/players/right");
        jumpRight = new Animation("assets/images/players/jumpRight");
        jumpRight.speed = 10;
        walkLeft = new Animation("assets/images/players/left");
        jumpLeft = new Animation("assets/images/players/jumpLeft");
        jumpLeft.speed = 10;
    }

    public int p = 0;
    public void render(Graphics g, ViewPort viewPort, GameObject master) {
        if (GameWindow.isRightPress == true  && GameWindow.isUpPress == false) {
            walkRight.render(g,viewPort.camera(master));
            p = 0;
        }
        else if (GameWindow.isLeftPress == true && GameWindow.isUpPress == false) {
            walkLeft.render(g,viewPort.camera(master));
            p = 1;
        }
        else if (GameWindow.isUpPress) {
            if (GameWindow.isRightPress) {
                jumpRight.render(g, viewPort.camera(master));
            } else if (GameWindow.isLeftPress) {
                jumpLeft.render(g, viewPort.camera(master));
            } else {
                if (p == 0) {
                    jumpRight.render(g, viewPort.camera(master));
                }
                else {
                    jumpLeft.render(g,viewPort.camera(master));
                }
            }
        }
        else {
            if (p == 0) {
                right.render(g, viewPort.camera(master));
            }
            else {
                left.render(g,viewPort.camera(master));
            }
        }
    }
}
