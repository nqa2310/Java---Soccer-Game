package game.scene;

import game.Background;
import game.ball.Ball;
import game.maps.GoalLeft;
import game.maps.GoalRight;
import game.maps.Map;
import game.player.Player;

import java.util.Scanner;

public class SceneStage1 extends Scene {
    public Player player;
    public Background background;
    public Map map;
    public Ball ball;
    public GoalRight goalRight;
    public GoalLeft goalLeft;

    @Override
    public void init() {
        background = new Background();
        map = Map.load("assets/images/platformer/tvt.json");
        map.generate();
        player = new Player();
        ball = new Ball();
        goalRight = new GoalRight(3119,481,"assets/images/goal.png");
        goalLeft = new GoalLeft(48,481,"assets/images/goal - Copy.png");

    }
}
