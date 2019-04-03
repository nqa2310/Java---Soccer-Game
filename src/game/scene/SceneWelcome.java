package game.scene;

import game.GameObject;
import game.enemy.Enemy;
import game.player.Player;

import java.util.ArrayList;

public class SceneWelcome extends Scene{
    @Override
    public void init() {
        GameObject.recycle(BackgroundWelcome.class);
        Player player = null;
        ArrayList<Enemy> enemies = null;
    }
    @Override
    public void clear() {
        GameObject.clearAll();
    }
}
