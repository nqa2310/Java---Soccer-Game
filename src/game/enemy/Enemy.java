package game.enemy;

import game.GameObject;
import game.Vector2D;
import game.ViewPort;
import game.physics.BoxCollider;
import game.renderer.Renderer;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject {
    Renderer renderer0;
    public ViewPort viewPort;
    BufferedImage enemyImage1;
    BufferedImage enemyImage2;
    BufferedImage enemyImage3;

    private final float GRAVITY = 0.4f;
    private final float HORZSPEED = 2.5f;
    public final int ENEMY_HEIGHT = 20;
    public final int ENEMY_WIDTH = 65;

    public Enemy() {
        viewPort = new ViewPort();
        enemyImage1 = SpriteUtils.loadImage("assets/images/enemy.png");
        enemyImage2 = SpriteUtils.loadImage("assets/images/enemy1.png");
        enemyImage3 = SpriteUtils.loadImage("assets/images/enemy3.png");


//        sliding.speed = 10;
        velocity.set(0,0);

        Random rand = new Random();
        int n = rand.nextInt(3);
        if (n == 0) {
            renderer = new Renderer(enemyImage1);
            position.set(1216, 505);
        } else if (n == 1) {
            renderer = new Renderer(enemyImage2);
            position.set(1216, 535);
        } else {
            renderer = new Renderer(enemyImage3);
            position.set(1216, 565);
        }
        hitBox = new BoxCollider(this,ENEMY_WIDTH,ENEMY_HEIGHT);

    }
    @Override
    public void render(Graphics g, ViewPort viewPort) {
        super.render(g, viewPort);
    }

    @Override
    public void run() {
        viewPort.position2.x = 0;
        velocity.x = 0;
        move();
    }

    // Di chuyen player
    private void move() {
        velocity.x = - HORZSPEED;
        viewPort.position2.x = velocity.x;
        this.position.add(velocity.x,0);
    }


    public BoxCollider nextHitBox(GameObject master, double shiftDistanceX, double shiftDistanceY) {
        Vector2D nextPosition = new Vector2D();
        nextPosition.set(master.position.x + shiftDistanceX,this.position.y + shiftDistanceY);
        BoxCollider nextHitBox = new BoxCollider(nextPosition,this.anchor,hitBox.width,hitBox.height);
        return nextHitBox;
    }
}
