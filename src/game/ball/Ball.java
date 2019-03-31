package game.ball;

import game.GameObject;
import game.Vector2D;
import game.physics.BoxCollider;
import game.platform.Platform;
import game.player.Player;
import game.renderer.Renderer;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Ball extends GameObject {
    private final float GRAVITY = 0.5f;
    private final float JUMSPEED = 5;
    public Ball() {
        BufferedImage image = SpriteUtils.loadImage("assets/images/players/straight/stand1.png");
        this.renderer = new Renderer(image);
        position.set(200,100);
        anchor.set(0.5,0.5);
        velocity.set(0,0);
        hitBox = new BoxCollider(this,18,29);
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }

    @Override
    public void run() {
        velocity.y += GRAVITY;
        moveVertical();

    }

    private void moveVertical() {
        BoxCollider nextHitBox = nextHitBox(this,0,velocity.y);
        Player player = GameObject.findIntersects(Player.class,nextHitBox);
        if (player != null) {
            boolean moveContinue = true;
            double shiftDistance = Math.signum(velocity.y);
            while(moveContinue) {
                if (GameObject.findIntersects(Player.class,nextHitBox(this,0,shiftDistance)) != null) {
                    moveContinue = false;
                } else {
                    shiftDistance++;
                    this.position.add(player.velocity.x, shiftDistance);
                }
            }
            velocity.y = -10;
        }
        this.position.add(0,velocity.y);
    }
    public BoxCollider nextHitBox(GameObject master,double shiftDistanceX, double shiftDistanceY) {
        Vector2D nextPosition = new Vector2D();
        nextPosition.set(master.position.x + shiftDistanceX,this.position.y + shiftDistanceY);
        BoxCollider nextHitBox = new BoxCollider(nextPosition,this.anchor,hitBox.width,hitBox.height);
        return nextHitBox;
    }
}
