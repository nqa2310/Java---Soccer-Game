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
    private float JUMSPEED = 5;
    private float ROLL_SPEED = 5;
    boolean touchGround = false;
    public Ball() {
        BufferedImage image = SpriteUtils.loadImage("assets/images/ball/Poke_ball_Single_Front.png");
        renderer = new Renderer(image);
        position.set(200,100);
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
//        viewPort.position2.x = 0;
//        move();

//        hitPlayerVertical();
        hitPlayerHorizontal();
        bounceVertical();
        bounceHorizontal();
        limit();
    }

    private void move() {
        if(GameWindow.isUpPress) { // chi cho phep nhay 1 lan
            BoxCollider nextHitBox = nextHitBox(this,0,1);
            if(GameObject.findIntersects(Platform.class,nextHitBox) != null) {
                velocity.y = -JUMSPEED;
            }
        }
        if(GameWindow.isRightPress) {
            velocity.x = 10;
        }
        if(GameWindow.isLeftPress) {
            velocity.x = -10;
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
            velocity.y = 0;
//            velocity.y = - velocity.y;
//            velocity.y = 4 * velocity.y / 5;
//            if (velocity.y < 0.1) {
//                velocity.y = 0;
//                velocity.y = 0;
//                touchGround = true;
//            }

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
                    moveHorizontalContinue = false;
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
            boolean moveContinue = true;
            double shiftDistance = Math.signum(velocity.y);
            while(moveContinue) {
                if (GameObject.findIntersects(Player.class,nextHitBox(this,0,shiftDistance)) != null) {
                    moveContinue = false;
                } else {
                    shiftDistance += Math.signum(velocity.y);
                    this.position.add(0, Math.signum(velocity.y));
                }
            }
            velocity.y = player.velocity.y/10;
            velocity.x = player.velocity.x / 2 ;
        }
        if (GameObject.findIntersects(Platform.class,nextHitBox(this,velocity.x,0)) != null) {
            this.position.add(0,0);
        }
        else {
            this.position.add(velocity.x, velocity.y);
        }
    }

    private void hitPlayerHorizontal() {
//        BoxCollider nextHitBox = nextHitBox(this,velocity.x,0);
        Player player = GameObject.findIntersects(Player.class,this.hitBox);
        if (player != null) {
//            boolean moveContinue = true;
//            double shiftDistance = Math.signum(velocity.x);
//            while(moveContinue) {
//                if (GameObject.findIntersects(Player.class,this.hitBox) != null) {
//                    moveContinue = false;
//                } else {
//                    shiftDistance += Math.signum(velocity.x);
//                    this.position.add(Math.signum(velocity.x), 0);
//                }
//            }
                velocity.y = player.velocity.y - 5;
                velocity.x = player.velocity.x / 5;
        }

            this.position.add(velocity.x, velocity.y/10 );
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