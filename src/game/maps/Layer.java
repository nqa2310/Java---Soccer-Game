package game.maps;

import game.physics.BoxCollider;
import game.platform.Platform;
import game.renderer.Renderer;
import tklibs.SpriteUtils;

import java.awt.image.BufferedImage;
import java.util.List;

public class Layer {
     private List<Integer> data;
     private int height;
     private int width;

    public void generate() {
        for (int titleY = 0; titleY < height; titleY++) {
            for (int titleX = 0; titleX < width; titleX++) {
                int mapData = data.get(titleY * width + titleX);
                if (mapData != 0) {
                    Platform platform = new Platform();
                    if (mapData == 1) {
                        BufferedImage image = SpriteUtils.loadImage("assets/images/platformer/Sprite/glass.png");
                        platform.renderer = new Renderer(image);
                    }
                    else if (mapData == 2) {
                        BufferedImage image = SpriteUtils.loadImage("assets/images/platformer/Sprite/platform.png");
                        platform.renderer = new Renderer(image);
                    }
                    platform.position.set(titleX * 32, titleY * 32);
                    platform.hitBox = new BoxCollider(platform,32,32);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Layer{" +
                "data=" + data +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
