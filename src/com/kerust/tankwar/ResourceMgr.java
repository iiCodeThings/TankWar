package com.kerust.tankwar;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceMgr {

    public static BufferedImage tankL = null;
    public static BufferedImage tankU = null;
    public static BufferedImage tankR = null;
    public static BufferedImage tankD = null;

    public static BufferedImage bulletL = null;
    public static BufferedImage bulletU = null;
    public static BufferedImage bulletR = null;
    public static BufferedImage bulletD = null;

    /* 地雷 */
    public static BufferedImage[] mines = new BufferedImage[21];

    public static BufferedImage[] stars = new BufferedImage[19];

    public static BufferedImage[] explode = new BufferedImage[19];
    static {
        try {
            tankL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankL.png"));
            tankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankU.png"));
            tankR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankR.png"));
            tankD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankD.png"));

            bulletL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletL.png"));
            bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
            bulletR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletR.png"));
            bulletD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletD.png"));

            for (int i = 0; i < explode.length; i ++) {
                String path = "images/explode/" + i + ".png";
                explode[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream(path));
            }

            for (int i = 0; i < mines.length; i ++) {
                String path = "images/mine/mine" + i + ".png";
                mines[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream(path));
            }

            for (int i = 0; i < stars.length; i ++) {
                String path = "images/star/star" + i + ".png";
                stars[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
