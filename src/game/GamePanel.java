package game;

import game.ball.Ball;
import game.maps.Map;
import game.platform.Platform;
import game.player.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel {
    Player player;
    Background background;
    Map map;
    Ball ball;

    public GamePanel() {
        background = new Background();
        map = Map.load("assets/images/platformer/tvt.json");
        map.generate();
        player = new Player();
        ball = new Ball();

    }

    public void gameLoop() {

        long lastLoop = 0;
        long delay = 1000 / 60;
        while(true) {
            long currentTime = System.currentTimeMillis();
            if(currentTime - lastLoop > delay) {
                runAll(); // logic game
                renderAll(); // render anh cua game
                lastLoop = currentTime;
            }
        }
    }

    private void renderAll() {
        repaint(); // goi lai ham paint()
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < GameObject.objects.size(); i++) {
            GameObject object = GameObject.objects.get(i);
            if (object.active) {
                object.render(g,ball.viewPort);
            }
        }
    }

    private void runAll() {
        for (int i = 0; i < GameObject.objects.size(); i++) {
            GameObject object = GameObject.objects.get(i);
            if(object.active) {
                object.run();
            }
        }
        System.out.println(GameObject.objects.size());
    }


}
