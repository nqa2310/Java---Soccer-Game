package game;

import game.ball.Ball;
import game.enemy.Enemy;
import game.maps.GoalLeft;
import game.maps.GoalRight;
import game.player.Player;
import game.scene.SceneManager;
import game.scene.SceneStage1;
import game.scene.SceneWelcome;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    public int scoreRight;
    public int scoreLeft;
    ArrayList<Enemy> enemies;

    public GamePanel() {
        scoreLeft = scoreRight = 0;
        enemies = new ArrayList<>();
        SceneManager.signNewScene(new SceneWelcome());
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
            if(SceneManager.currentScene instanceof SceneStage1) {
                if (object.active) {
                    Player player = GameObject.find(Player.class);
                    if (player != null) {
                        object.render(g, player.viewPort);
                    }
                }
            } else {
                if (object.active) {
                    object.render(g, null);
                }
            }
        }
        drawScore(g);
    }

    private void drawScore(Graphics g) {
        g.setFont(font);
        g.setColor(Color.green);
        Ball ball = GameObject.find(Ball.class);
        if (ball != null) {
            g.drawString(scoreLeft + "  -  " + scoreRight, 600, 30);
        }
    }

    private void runAll() {
        summonEnemies();
        for (int i = 0; i < GameObject.objects.size(); i++) {
            GameObject object = GameObject.objects.get(i);
            if(object.active) {
                object.run();
            }
        }
        spawnPlayer();
    }

    private void spawnPlayer() {

        Player player = GameObject.find(Player.class);
        Ball ball = GameObject.find(Ball.class);
        GoalRight goalRight = GameObject.find(GoalRight.class);
        GoalLeft goalLeft = GameObject.find(GoalLeft.class);
        if (ball != null && goalLeft != null && goalRight != null) {
            if (ball.position.x + ball.renderer.image.getWidth() / 2 < goalLeft.position.x + goalLeft.renderer.image.getWidth() / 2) {
                scoreRight += 1;
                player.position.set(goalLeft.position.x +100,200);
                ball.position.set(goalLeft.position.x+200,200);
                ball.velocity.set(0,0);
                player.animation.p = 0;
            } else if (ball.position.x - ball.renderer.image.getWidth() / 2 > goalRight.position.x - goalRight.renderer.image.getWidth() / 2) {
                scoreLeft += 1;
                player.position.set(goalLeft.position.x +100,200);
                ball.position.set(goalLeft.position.x+200,200);
                ball.velocity.set(0,0);
                player.viewPort.position2.set(-2200,0);
                player.animation.p = 0;
            }
        }
    }

    int summonCount;
    int waveCount;
    int n;
    private void summonEnemies() {
        waveCount++;
        if (waveCount > 300 - scoreLeft * 5) {
            summonCount++;
            if(summonCount > 5) {
                Enemy enemy = GameObject.recycle(Enemy.class);
                if (enemy != null) {
                    enemies.add(enemy);
                    summonCount = 0;
                    waveCount = 0;
                }
            }
        }
    }

}
