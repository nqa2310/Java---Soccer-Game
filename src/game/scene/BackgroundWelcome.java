package game.scene;

import game.GameObject;
import game.GameWindow;
import game.Settings;
import game.renderer.Renderer;
import tklibs.SpriteUtils;

import java.awt.image.BufferedImage;

public class BackgroundWelcome extends GameObject {
    public BackgroundWelcome() {
        BufferedImage image = SpriteUtils.loadImage("assets/images/background/noucamp.jpg");
        this.renderer = new Renderer(image);
        this.position.set(Settings.GAME_WIDTH/2, Settings.GAME_HEIGHT/2);
    }

    @Override
    public void run() {
        super.run();
        if (GameWindow.isAnyKeyPress) {
            SceneManager.signNewScene(new SceneStage1());
        }
    }
}
