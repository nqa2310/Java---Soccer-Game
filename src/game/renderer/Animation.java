package game.renderer;

import game.GameObject;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Animation {
    public int speed = 2;
    public BufferedImage image;
    public ArrayList<BufferedImage> images;
    int currentIndex;
    int frameCount;
    boolean isOnce;

    public Animation(BufferedImage image) {
        this.image = image;
        this.currentIndex = 0;
        this.frameCount = 0;
    }
    // TODO: 1.upgrade order fileName
    // TODO: 2.load file .png only


    public Animation(String folderPath) {
        images = new ArrayList<>();
        File folder = new File(folderPath);
        java.util.List<String> fileNames = Arrays.asList(folder.list());
        fileNames.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        for(int i = 0; i < fileNames.size(); i++) {
            String fileName = fileNames.get(i);
            if(fileName.toLowerCase().endsWith(".png")) {
                BufferedImage image = SpriteUtils.loadImage(
                        folderPath + "/" + fileName
                );
                images.add(image);
            }
        }
    }


    public Animation(String folderPath, boolean isOnce) {
        this(folderPath);
        this.isOnce = isOnce;
    }

    public void render(Graphics g, GameObject master){
        // master > position
        if(image != null) {
            g.drawImage(
                    image,
                    (int) (master.position.x - master.anchor.x*image.getWidth()),
                    (int) (master.position.y - master.anchor.y*image.getHeight()),
                    null
            );
        } else if(images != null) {
            BufferedImage currentImage = images.get(currentIndex);
            g.drawImage(
                    currentImage,
                    (int) (master.position.x - master.anchor.x*currentImage.getWidth()),
                    (int) (master.position.y - master.anchor.y*currentImage.getHeight()),
                    null
            );

            frameCount++;
            if(frameCount > speed) {
                currentIndex++;
                if(currentIndex >= images.size()) {
                    if(isOnce) {
                        master.deactive();
                    }
                    currentIndex = 0;
                }
                frameCount = 0;
            }
        }

    }
}
