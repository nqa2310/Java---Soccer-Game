package game.ball;

import game.*;
import game.physics.BoxCollider;
import game.platform.Platform;

import game.player.Player;
import game.renderer.Renderer;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Ball extends GameObject {
    public ViewPort viewPort;
    boolean moveHorizontalContinue = true;
    private final float GRAVITY = 0.3f;
    private final int BALL_SIZE = 16;
    private float JUMP_SPEED = 5;
    private float ROLL_SPEED = 5;
    boolean touchGround = false;
    public Ball() {
        BufferedImage image = SpriteUtils.loadImage("assets/images/ball/Poke_ball_Single_Front.png");
        renderer = new Renderer(image);
        position.set(400,300);
        velocity.set(0,0);
        hitBox = new BoxCollider(this,16,16);
        viewPort = new ViewPort();
    }


    @Override
    public void render(Graphics g, ViewPort viewPort) {
        super.render(g, viewPort);
    }

    @Override
    public void run() {
        velocity.y += GRAVITY;
        friction(velocity);
        viewPort.position2.x = 0;
        hitPlayerVertical();
        hitPlayerHorizontal();
        bounceVertical();
        bounceHorizontal();

//        limit();
    }

    private void friction(Vector2D velocity) {
        if (velocity.x > 0) {
            velocity.x -=  0.2f;
            if (velocity.x < 0.5) {
                velocity.x = 0;
            }
        }
        else if (velocity.x < 0) {
            velocity.x += 0.2f;
            if (velocity.x > -0.5) {
                velocity.x = 0;
            }
        }

    }

    private void bounceVertical() {
        BoxCollider nextHitBox = nextHitBox(this,0,velocity.y);
        Platform platform = GameObject.findIntersects(Platform.class,nextHitBox);
        if (platform!= null) {
            boolean moveContinue = true;
            double shiftDistance = Math.signum(velocity.y);
            while(moveContinue) {
                if (GameObject.findIntersects(Platform.class,nextHitBox(this,0,shiftDistance)) != null) {
                    moveContinue = false;
                } else {
                    shiftDistance += Math.signum(velocity.y);
                    this.position.add(0, Math.signum(velocity.y));
                }
            }
            if (velocity.y > 0) {
                velocity.y = -JUMP_SPEED;
            }
            else if (velocity.y < 0) {
                velocity.y = JUMP_SPEED;
            }
            JUMP_SPEED = 4 * JUMP_SPEED / 5;
            if (JUMP_SPEED < 0.5) {
                JUMP_SPEED = 0;
                touchGround = true;
            }

        }
        this.position.add(0,velocity.y);
    }

    private void bounceHorizontal() {
        BoxCollider nextHitBox = nextHitBox(this,velocity.x,0);
        Platform platform = GameObject.findIntersects(Platform.class,nextHitBox);
        if (platform!= null) {
            boolean moveContinue = true;
            double shiftDistance = Math.signum(velocity.x);
            while(moveContinue) {
                if (GameObject.findIntersects(Platform.class,nextHitBox(this,shiftDistance,0)) != null) {
                    moveContinue = false;
                } else {
                    shiftDistance += Math.signum(velocity.x);
                    this.position.add(Math.signum(velocity.x), 0);
                }
            }
            velocity.x = 0;
        }
        this.position.add(velocity.x,0);
    }


    private void hitPlayerVertical() {
        BoxCollider nextHitBox = nextHitBox(this,0,velocity.y);
        Player player = GameObject.findIntersects(Player.class,nextHitBox);
        if (player != null) {
            velocity.y = player.velocity.y/10;
            velocity.x = player.velocity.x / 2 ;
        }

    }

    private void hitPlayerHorizontal() {
        Player player = GameObject.findIntersects(Player.class,this.hitBox);
        if (player != null) {
//            if (GameObject.findIntersects(Platform.class,nextHitBox(this,player.velocity.x,0)) != null) {
//                velocity.x = 0;
//            }
//            else {
                velocity.x = player.velocity.x * 1.5;
                velocity.y = player.velocity.y * 2;
//            }
        }


//        if (this.position.x == 400) {
//            viewPort.position2.x = velocity.x * 2 ;
//
//        }
//        viewPort.position2.x = velocity.x * 3 ;
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