package game.player;

import game.*;
import game.enemy.Enemy;
import game.physics.BoxCollider;
import game.platform.Platform;
import game.renderer.Renderer;
import tklibs.SpriteUtils;


import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

    public PlayerAnimation animation;
    BufferedImage playerImage;
    public ViewPort viewPort;
    private final float GRAVITY = 0.4f;
    private final float JUMSPEED = 10;
    private final float HORZSPEED = 7;
    private final int PLAYER_HEIGHT = 65;
    private final int PLAYER_WIDTH = 40;

    public Player() {
        playerImage = SpriteUtils.loadImage("assets/images/players/right/player2 - Copy.png");
        animation = new PlayerAnimation();
        viewPort = new ViewPort();
        renderer = new Renderer(playerImage);
        velocity.set(0,0);
        position.set(148,200);
        hitBox = new BoxCollider(this,PLAYER_WIDTH,PLAYER_HEIGHT);

    }
    @Override
    public void render(Graphics g, ViewPort viewPort) {
        animation.render(g,viewPort,this);
    }

    @Override
    public void run() {
        velocity.y += GRAVITY;
        viewPort.position2.x = 0;
        velocity.x = 0;
        move();
        hitEnemyHorizontal();
        hitEnemyVertical();
        moveHorizontal();
        moveVertical();
    }

    // Di chuyen player
    private void move() {
        if(GameWindow.isUpPress) { // chi cho phep nhay khi cham dat
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

}
