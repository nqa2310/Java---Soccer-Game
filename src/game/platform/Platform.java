package game.platform;

import game.GameObject;
import game.Settings;
import game.physics.BoxCollider;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Platform extends GameObject {
    BufferedImage platformImage;

    public Platform() {
        platformImage = SpriteUtils.loadImage("assets/images/platformer/445906.png");
        position.set(400,550);
        hitBox = new BoxCollider(this, Settings.GAME_WIDTH, 100);
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        Image image = platformImage.getScaledInstance(Settings.GAME_WIDTH,100,1);
        g.drawImage(
                image,
                (int) (position.x - this.anchor.x*Settings.GAME_WIDTH),
                (int) (position.y - this.anchor.y*100),
                null
        );
    }
}
