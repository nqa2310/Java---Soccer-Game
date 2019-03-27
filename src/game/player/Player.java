package game.player;

import game.GameObject;
import game.GameWindow;
import game.Settings;
import game.Vector2D;
import game.physics.BoxCollider;
import game.platform.Platform;
import game.renderer.Renderer;
import tklibs.SpriteUtils;


import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    BufferedImage playerImage;
    private final float GRAVITY = 0.5f;
    private final float JUMSPEED = 10;
    private final float HORZSPEED = 5;
    private final int PLAYER_SIZE = 70;
    public Player() {
//        renderer = new Renderer("assets/images/players/straight");
        playerImage = SpriteUtils.loadImage("assets/images/players/straight/638283338.png");
        velocity.set(0,0);
        position.set(200,200);
        hitBox = new BoxCollider(this,PLAYER_SIZE,PLAYER_SIZE);

    }
    @Override
    public void render(Graphics g) {
        super.render(g);
        Image image = playerImage.getScaledInstance(PLAYER_SIZE,PLAYER_SIZE,1);
        g.drawImage(
                image,
                (int) (position.x - this.anchor.x*PLAYER_SIZE),
                (int) (position.y - this.anchor.y*PLAYER_SIZE),
                null
        );

    }

    @Override
    public void run() {
        velocity.y += GRAVITY;
        velocity.x = 0;
        move();
        limit();
        moveHorizontal();
        moveVertical();
    }
    
    // Di chuyen player
    private void move() {
        if(GameWindow.isUpPress) { // chi cho phep nhay 1 lan
//            Vector2D nextPosition = new Vector2D();
//            nextPosition.set(this.position.x,this.position.y + 1);
            BoxCollider nextHitBox = nextHitBox(this,0,1);
            if(GameObject.findIntersects(Platform.class,nextHitBox) != null) {
                velocity.y = -JUMSPEED;
            }
        }
        if(GameWindow.isRightPress) {
            velocity.x = HORZSPEED;
        }
        if(GameWindow.isLeftPress) {
            velocity.x = -HORZSPEED;
        }
    }

    private void moveHorizontal() {
//        Vector2D nextPositon = new Vector2D();
//        double shiftDistance = Math.signum(velocity.x);
//        nextPositon.set(this.position.x + shiftDistance,this.position.y);
        BoxCollider nextHitBox = nextHitBox(this,velocity.x,0);
        Platform platform = GameObject.findIntersects(Platform.class,nextHitBox);
        if (platform != null) {
            boolean moveContinue = true;
            double shiftDistance = Math.signum(velocity.x);
            while(moveContinue) {
                if (GameObject.findIntersects(Platform.class,nextHitBox(this,shiftDistance,0)) != null) {
                    moveContinue = false;
                } else {
                    shiftDistance++;
                    this.position.add(shiftDistance, 0);
                }
            }
            velocity.x = 0;
        }
        this.position.add(velocity.x,0);
    }

    // Tao trong luc
    private void moveVertical() { //kiem tra xem vi tri tiep theo co cham vao platform hay ko, neu co thi dung lai
//        Vector2D nextPositon = new Vector2D();
//        double shiftDistance = Math.signum(velocity.y);
//        nextPositon.set(this.position.x,this.position.y + shiftDistance);
        BoxCollider nextHitBox = nextHitBox(this,0,velocity.y);
        Platform platform = GameObject.findIntersects(Platform.class,nextHitBox);
        if (platform != null) {
            boolean moveContinue = true;
            double shiftDistance = Math.signum(velocity.y);
            while(moveContinue) {
                if (GameObject.findIntersects(Platform.class,nextHitBox(this,0,shiftDistance)) != null) {
                    moveContinue = false;
                } else {
                    shiftDistance++;
                    this.position.add(0, shiftDistance);
                }
            }
            velocity.y = 0;
        }
        this.position.add(0,velocity.y);
    }

    // Gioi han di chuyen
    private void limit() {
        if (position.x < PLAYER_SIZE/2) {
            position.set(PLAYER_SIZE/2, position.y);
        }
        if (position.x > Settings.GAME_WIDTH - PLAYER_SIZE/2) {
            position.set(
                    Settings.GAME_WIDTH-PLAYER_SIZE/2,
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
