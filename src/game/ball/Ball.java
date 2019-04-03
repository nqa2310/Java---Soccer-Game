package game.ball;

import game.*;
import game.maps.GoalLeft;
import game.physics.BoxCollider;
import game.platform.Platform;

import game.player.Player;
import game.renderer.Animation;
import game.renderer.Renderer;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Ball extends GameObject {
    public int scoreLeft;
    public int scoreRight;
    Animation rollRight;
    Animation rollLeft;
    public ViewPort viewPort;
    private final float GRAVITY = 0.3f;
    private final int BALL_SIZE = 30;
    private float JUMP_SPEED = 7;
    private float GROUND_FRICTION = 0.5f;
    private float AIR_FRICTION = 0.02f;
    public Ball() {
        scoreRight = scoreLeft = 0;
        BufferedImage image = SpriteUtils.loadImage("assets/images/ball/ball0.png");
        renderer = new Renderer(image);
        rollRight = new Animation("assets/images/ball/rollRight");
        rollLeft = new Animation("assets/images/ball/rollLeft");
        rollLeft.speed = 1;
        rollRight.speed = 1;
        position.set(100,100);
        velocity.set(0,0);
        hitBox = new BoxCollider(this,16,16);
        viewPort = new ViewPort();
    }


    @Override
    public void render(Graphics g, ViewPort viewPort) {
        if (velocity.x > 0) {
            rollRight.render(g,viewPort.camera(this));
        }
        else if(velocity.x < 0) {
            rollLeft.render(g,viewPort.camera(this));
        }
        else {
            super.render(g, viewPort);
        }
    }

    @Override
    public void run() {
        velocity.y += GRAVITY;
        friction(velocity);
        viewPort.position2.x = 0;
        hitPlayerHorizontal();
        hitPlayerVertical();
        goalHit();
        bounceVertical();
        bounceHorizontal();

//        limit();
    }

    private void friction(Vector2D velocity) {
        BoxCollider nextHitBox = nextHitBox(this,0,1);
        if (velocity.x > 0 ) {
            if (GameObject.findIntersects(Platform.class,nextHitBox) != null) {
                velocity.x -= GROUND_FRICTION;
            }
            else {
                velocity.x -= AIR_FRICTION;
            }
            if (velocity.x < 0.5) {
                velocity.x = 0;
            }
        }
        else if (velocity.x < 0) {
            if (GameObject.findIntersects(Platform.class,nextHitBox) != null) {
                velocity.x += GROUND_FRICTION;
            }
            else {
                velocity.x += AIR_FRICTION;
            }
            if (velocity.x > -0.5) {
                velocity.x = 0;
            }
        }

    }

    private void goalHit() {
        BoxCollider nextHitBox = nextHitBox(this,velocity.x,0);
        GoalLeft platform = GameObject.findIntersects(GoalLeft.class,nextHitBox);
        if (platform!= null) {
            scoreRight += 1;
            boolean moveContinue = true;
            double shiftDistance = Math.signum(velocity.x);
            while(moveContinue) {
                if (GameObject.findIntersects(GoalLeft.class,nextHitBox(this,shiftDistance,0)) != null) {
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
            velocity.y = -velocity.y/2;

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
            velocity.x = -velocity.x * 4 /5;

        }
        this.position.add(velocity.x,0);
    }


    private void hitPlayerVertical() {
        BoxCollider nextHitBox = nextHitBox(this,0,velocity.y);
        Player player = GameObject.findIntersects(Player.class,nextHitBox);
        if (player != null) {
                velocity.y = -JUMP_SPEED;
        }
    }

    private void hitPlayerHorizontal() {
        Player player = GameObject.findIntersects(Player.class,this.hitBox);
        if (player != null) {
                velocity.x = player.velocity.x/2;
                velocity.y = player.velocity.y*1.3;
        }

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