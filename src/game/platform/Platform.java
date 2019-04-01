package game.platform;

import game.GameObject;
import game.ViewPort;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Platform extends GameObject {
    BufferedImage platformImage;

    public Platform() {
//        hitBox = new BoxCollider(this, 95, 95);

    }

    @Override
    public void render(Graphics g, ViewPort viewPort) {
        super.render(g, viewPort);
    }

//    public Platform() {
//        platformImage = SpriteUtils.loadImage("assets/images/platformer/445906.png");
//        position.set(400,550);
//        hitBox = new BoxCollider(this, Settings.GAME_WIDTH, 100);
//    }
//
//    @Override
//    public void render(Graphics g) {
//        super.render(g);
//        Image image = platformImage.getScaledInstance(Settings.GAME_WIDTH,100,1);
//        g.drawImage(
//                image,
//                (int) (position.x - this.anchor.x*Settings.GAME_WIDTH),
//                (int) (position.y - this.anchor.y*100),
//                null
//        );
//    }
}
