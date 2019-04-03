package game.scene;

import game.GameObject;
import game.GameWindow;
import game.renderer.Renderer;
import tklibs.SpriteUtils;

import java.awt.image.BufferedImage;

public class BackgroundWelcome extends GameObject {
    public BackgroundWelcome() {
        BufferedImage image = SpriteUtils.loadImage("assets/images/background/445906.png");
        this.renderer = new Renderer(image);
        this.position.set(0, 0);
    }

    @Override
    public void run() {
        super.run();
        if (GameWindow.isAnyKeyPress) {
            SceneManager.signNewScene(new SceneStage1());
        }
    }
}
