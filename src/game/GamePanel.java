package game;

import game.ball.Ball;
import game.maps.GoalLeft;
import game.maps.GoalRight;
import game.maps.Map;
import game.player.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    Player player;
    Background background;
    Map map;
    Ball ball;
    GoalRight goalRight;
    GoalLeft goalLeft;

    public GamePanel() {
        background = new Background();
        map = Map.load("assets/images/platformer/tvt.json");
        map.generate();
        player = new Player();
        ball = new Ball();
        goalRight = new GoalRight(3119,481,"assets/images/goal.png");
        goalLeft = new GoalLeft(48,481,"assets/images/goal - Copy.png");

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

    static  Font font = new Font("Verdana",Font.BOLD,32);

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < GameObject.objects.size(); i++) {
            GameObject object = GameObject.objects.get(i);
            if (object.active) {
                object.render(g,player.viewPort);
            }
        }
        g.setFont(font);
        g.setColor(Color.green);
        g.drawString(ball.scoreRight + "",300,500);
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
