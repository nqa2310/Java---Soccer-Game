package game.ball;

import game.*;
import game.enemy.Enemy;
import game.maps.GoalLeft;
import game.maps.GoalRight;
import game.physics.BoxCollider;
import game.platform.Platform;

import game.player.Player;
import game.renderer.Animation;
import game.renderer.Renderer;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Ball extends GameObject {
    ballAnimation animation;
    private final float GRAVITY = 0.3f;
    private final int BALL_SIZE = 30;
    private float JUMP_SPEED = 7;
    private float GROUND_FRICTION = 0.5f;
    private float AIR_FRICTION = 0.02f;
    public Ball() {
        BufferedImage image = SpriteUtils.loadImage("assets/images/ball/ball0.png");
        renderer = new Renderer(image);
        animation = new ballAnimation();
        position.set(248,200);
        velocity.set(0,0);
        hitBox = new BoxCollider(this,16,16);
    }


    @Override
    public void render(Graphics g, ViewPort viewPort) {
        animation.render(g,viewPort,this,this.velocity);
    }

    @Override
    public void run() {
        velocity.y += GRAVITY;
        friction(velocity);
        goalLeftHit();
        goalRightHit();
        hitEnemyHorizontal();
        hitEnemyVertical();
        hitPlayerHorizontal();
        hitPlayerVertical();
        bounceVertical();
        bounceHorizontal();
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

    private void goalLeftHit() {
        BoxCollider nextHitBox = nextHitBox(this,velocity.x,0);
        GoalLeft platform = GameObject.findIntersects(GoalLeft.class,nextHitBox);
        if (platform!= null) {
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
    }

    private void goalRightHit() {
        BoxCollider nextHitBox = nextHitBox(this,velocity.x,0);
        GoalRight platform = GameObject.findIntersects(GoalRight.class,nextHitBox);
        if (platform!= null) {
            boolean moveContinue = true;
            double shiftDistance = Math.signum(velocity.x);
            while(moveContinue) {
                if (GameObject.findIntersects(GoalRight.class,nextHitBox(this,shiftDistance,0)) != null) {
                    moveContinue = false;
                } else {
                    shiftDistance += Math.signum(velocity.x);
                    this.position.add(Math.signum(velocity.x), 0);
                }
            }
            velocity.x = 0;
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
                velocity.x = player.velocity.x*1.2;
                velocity.y = player.velocity.y*1.3;
        }

    }


    private void hitEnemyVertical() {
        BoxCollider nextHitBox = nextHitBox(this,0,velocity.y);
        Enemy enemy = GameObject.findIntersects(Enemy.class,nextHitBox);
        if (enemy != null) {
            velocity.x = enemy.velocity.x;
            velocity.y = - JUMP_SPEED;
        }
    }

    private void hitEnemyHorizontal() {
        Enemy enemy = GameObject.findIntersects(Enemy.class, this.hitBox);
        if (enemy != null) {
            velocity.x = enemy.velocity.x;
            velocity.y = enemy.velocity.y * 1.3;
        }

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