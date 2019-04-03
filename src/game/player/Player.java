package game.player;

import game.*;
import game.ball.Ball;
import game.enemy.Enemy;
import game.physics.BoxCollider;
import game.platform.Platform;
import game.renderer.Animation;
import game.renderer.Renderer;
import tklibs.SpriteUtils;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;

public class Player extends GameObject {
    Animation jumpLeft;
    Animation jumpRight;
    Animation walkRight;
    Animation walkLeft;
    Renderer renderer0;
    public ViewPort viewPort;
    BufferedImage playerImage;
    BufferedImage playerImage0;
    private final float GRAVITY = 0.4f;
    private final float JUMSPEED = 10;
    private final float HORZSPEED = 7;
    private final int PLAYER_HEIGHT = 65;
    private final int PLAYER_WIDTH = 40;
    public Player() {

        viewPort = new ViewPort();
        playerImage = SpriteUtils.loadImage("assets/images/players/right/player2.png");
        playerImage0 = SpriteUtils.loadImage("assets/images/players/left/player2.png");
        renderer = new Renderer(playerImage);
        renderer0 = new Renderer(playerImage0);
        walkRight = new Animation("assets/images/players/right");
        walkLeft = new Animation("assets/images/players/left");
        jumpRight = new Animation("assets/images/players/jumpRight");
        jumpLeft = new Animation("assets/images/players/jumpLeft");
        jumpLeft.speed = 10;
        jumpRight.speed = 10;
        velocity.set(0,0);
        position.set(400,200);
        hitBox = new BoxCollider(this,PLAYER_WIDTH,PLAYER_HEIGHT);

    }
    @Override
    public void render(Graphics g, ViewPort viewPort) {
        if (GameWindow.isRightPress == true  && GameWindow.isUpPress == false) {
            walkRight.render(g,viewPort.camera(this));
        }
        else if (GameWindow.isLeftPress == true && GameWindow.isUpPress == false) {
            walkLeft.render(g,viewPort.camera(this));
        }
        else if (GameWindow.isUpPress) {
            if (GameWindow.isRightPress) {
                jumpRight.render(g, viewPort.camera(this));
            } else if (GameWindow.isLeftPress) {
                jumpLeft.render(g, viewPort.camera(this));
            } else {jumpRight.render(g, viewPort.camera(this));}
        }
        else {
            super.render(g,viewPort);
        }

    }

    @Override
    public void run() {
        velocity.y += GRAVITY;
        viewPort.position2.x = 0;
        velocity.x = 0;
        move();
        hitEnemyHorizontal();
        hitEnemyVertical();
//        limit();
        moveHorizontal();
        moveVertical();
    }

    // Di chuyen player
    private void move() {
        if(GameWindow.isUpPress) { // chi cho phep nhay 1 lan
            BoxCollider nextHitBox = nextHitBox(this,0,1);
            if(GameObject.findIntersects(Platform.class,nextHitBox) != null) {
                velocity.y = -JUMSPEED;
            }
        }
        if(GameWindow.isRightPress) {
            velocity.x = HORZSPEED;
            if (position.x < Settings.GAME_WIDTH/2) {
                viewPort.position2.x = velocity.x / 2;
            }
            else if (position.x == Settings.GAME_WIDTH/2) {
                viewPort.position2.x = velocity.x;
            }
            else if (position.x > Settings.GAME_WIDTH/2) {
                viewPort.position2.x = velocity.x * 1.5;
            }
        }
        if(GameWindow.isLeftPress) {
            velocity.x = -HORZSPEED;
            if (position.x < Settings.GAME_WIDTH/2) {
                viewPort.position2.x = velocity.x * 1.5;
            }
            else if (position.x == Settings.GAME_WIDTH/2) {
                viewPort.position2.x = velocity.x;
            }
            else if (position.x > Settings.GAME_WIDTH/2) {
                viewPort.position2.x = velocity.x /2;
            }

        }
    }

    private void moveHorizontal() {
        BoxCollider nextHitBox = nextHitBox(this,velocity.x,0);
        Platform platform = GameObject.findIntersects(Platform.class,nextHitBox);
        if (platform != null) {
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
            viewPort.position2.x = velocity.x;
        }
        this.position.add(velocity.x,0);
    }

    // Tao trong luc
    private void moveVertical() { //kiem tra xem vi tri tiep theo co cham vao platform hay ko, neu co thi dung lai
        BoxCollider nextHitBox = nextHitBox(this,0,velocity.y);
        Platform platform = GameObject.findIntersects(Platform.class,nextHitBox);
        if (platform != null) {
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
        }
        this.position.add(0,velocity.y);
    }

    private void hitEnemyVertical() {
        BoxCollider nextHitBox = nextHitBox(this,0,velocity.y);
        Enemy enemy = GameObject.findIntersects(Enemy.class, nextHitBox);
        if (enemy != null && !GameWindow.isUpPress) {
            velocity.x = enemy.velocity.x;
            velocity.y = 0;
        }
    }
    private void hitEnemyHorizontal() {
        Enemy enemy = GameObject.findIntersects(Enemy.class, this.hitBox);
        if (enemy != null && position.x > 15) {
            velocity.x = enemy.velocity.x;
        }

    }

    // Gioi han di chuyen
    private void limit() {
        if (position.x < PLAYER_WIDTH/2) {
            position.set(PLAYER_WIDTH/2, position.y);
        }
        if (position.x > Settings.GAME_WIDTH - PLAYER_WIDTH/2) {
            position.set(
                    Settings.GAME_WIDTH-PLAYER_WIDTH/2,
                    position.y
            );
        }
    }


    public BoxCollider nextHitBox(GameObject master,double shiftDistanceX, double shiftDistanceY) {
        Vector2D nextPosition = new Vector2D();
        nextPosition.set(master.position.x + shiftDistanceX,this.position.y + shiftDistanceY);
        BoxCollider nextHitBox = new BoxCollider(nextPosition,this.anchor,hitBox.width,hitBox.height);
        return nextHitBox;
    }
}
