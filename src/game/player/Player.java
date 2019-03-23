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
        moveVertical();
        move();
        limit();
        position.add(velocity.x,velocity.y);
    }
    // Di chuyen player
    private void move() {
        if(GameWindow.isUpPress) { // chi cho phep nhay 1 lan
            Vector2D nextPosition = new Vector2D();
            nextPosition.set(this.position.x,this.position.y + 1);
            BoxCollider nextHitBox = new BoxCollider(nextPosition,this.anchor,hitBox.width,hitBox.height);
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
    // Tao trong luc
    private void moveVertical() { //kiem tra xem vi tri tiep theo co cham vao platform hay ko, neu co thi dung lai
        Vector2D nextPositon = new Vector2D();
        nextPositon.set(this.position.x,this.position.y + velocity.y);
        BoxCollider nextBoxCollider = new BoxCollider(nextPositon,this.anchor,hitBox.width,hitBox.height);
        Platform platform = GameObject.findIntersects(Platform.class,nextBoxCollider);
        if (platform != null) {
            velocity.y = 0;
        }
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
}
