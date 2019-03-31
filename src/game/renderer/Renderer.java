package game.renderer;

import game.GameObject;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Renderer {
    private final int PLAYER_SIZE = 70;
    public BufferedImage image;
    public ArrayList<BufferedImage> images;
    public ArrayList<Image> images0;
    int currentIndex;
    int currentIndex0;
    int frameCount;
    int frameCount0;
    boolean isOnce;

    public Renderer(BufferedImage image) {
        this.image = image;
        this.currentIndex = 0;
        this.frameCount = 0;
        this.currentIndex0 = 0;
        this.frameCount0 = 0;
    }
    // TODO: 1.upgrade order fileName
    // TODO: 2.load file .png only


    public Renderer(String folderPath) {
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

    // load anh tu folde, scale va them vao mang images0
    public Renderer(String folderPath, int i) {
        if (i!=0) {
            images0 = new ArrayList<>();
            File folder = new File(folderPath);
            java.util.List<String> fileNames = Arrays.asList(folder.list());
            fileNames.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
            for (int j = 0; j < fileNames.size(); j++) {
                String fileName = fileNames.get(j);
                if (fileName.toLowerCase().endsWith(".png")) {
                    BufferedImage image = SpriteUtils.loadImage(
                            folderPath + "/" + fileName
                    );
                    Image im = image.getScaledInstance(70, 70, 1);
                    images0.add(im);
                }
            }
        }
    }

    public Renderer(String folderPath, boolean isOnce) {
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
            if(frameCount > 10) {
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
        else if(images0 != null) { // render anh da scale
            Image currentImage0 = images0.get(currentIndex0);
            g.drawImage(
                    currentImage0,
                    (int) (master.position.x - master.anchor.x*PLAYER_SIZE),
                    (int) (master.position.y - master.anchor.y*PLAYER_SIZE),
                    null
            );

            frameCount0++;
            if(frameCount0 > 10) {
                currentIndex0++;
                if(currentIndex0 >= images.size()) {
                    if(isOnce) {
                        master.deactive();
                    }
                    currentIndex0 = 0;
                }
                frameCount0 = 0;
            }
        }
    }
}