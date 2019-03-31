package game.ball;

import game.GameObject;
import game.Settings;
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
    private final int BALL_SIZE = 16;
    private float JUMP_SPEED = 10;
    private float ROLL_SPEED = 5;
    boolean touchGround = false;
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
        bounce();
        hitPlayer();
        limit();
    }

    private void bounce() {
        BoxCollider nextHitBox = nextHitBox(this,0,velocity.y);
        Platform platform = GameObject.findIntersects(Platform.class,nextHitBox);
        if (platform!= null) {
            velocity.y = - JUMP_SPEED;
            JUMP_SPEED = 4 * JUMP_SPEED / 5;
            if (JUMP_SPEED < 0.1) {
                JUMP_SPEED = 0;
                velocity.y = 0;
                touchGround = true;
            }
        }
        this.position.add(velocity.x,velocity.y);
    }


    private void hitPlayer() {
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

//            if (touchGround) {
//                velocity.x = player.velocity.x;
//            } else {
//                velocity.y = player.velocity.y - 5;
//                velocity.x = player.velocity.x / 3;
//            }
            velocity.y = player.velocity.y - 5;
            velocity.x = player.velocity.x / 3;
        }
        this.position.add(velocity.x, velocity.y);
    }

    public BoxCollider nextHitBox(GameObject master, double shiftDistanceX, double shiftDistanceY) {
        Vector2D nextPosition = new Vector2D();
        nextPosition.set(master.position.x + shiftDistanceX,this.position.y + shiftDistanceY);
        BoxCollider nextHitBox = new BoxCollider(nextPosition,this.anchor,hitBox.width,hitBox.height);
        return nextHitBox;
    }

    private void limit() {
        if (position.x < BALL_SIZE/2) {
            velocity.set(- velocity.x, velocity.y);
        }

        if (position.x > Settings.GAME_WIDTH - BALL_SIZE/2) {
            velocity.set(- velocity.x, velocity.y);
        }
    }
}