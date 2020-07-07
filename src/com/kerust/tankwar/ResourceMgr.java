package com.kerust.tankwar;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceMgr {

    /* 子弹 */
    public static BufferedImage[] bullets = new BufferedImage[4];

    /* 主战坦克 */
    public static BufferedImage[] mainTanks = new BufferedImage[4];

    /* 敌对坦克 */
    public static BufferedImage[] hostileTanks = new BufferedImage[4];

    /* 地雷奖励 */
    public static BufferedImage[] mines = new BufferedImage[21];

    /* 五角星奖励*/
    public static BufferedImage[] stars = new BufferedImage[19];

    /* 爆炸特效 */
    public static BufferedImage[] explode = new BufferedImage[19];

    /* 奖励一条命 */
    public static BufferedImage[] awardTanks = new BufferedImage[21];

    static {
        try {
            for (int i = 0; i < bullets.length; i ++) {
                String path = "images/bullet/bullet" + i + ".png";
                bullets[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream(path));
            }

            for (int i = 0; i < mainTanks.length; i ++) {
                String path = "images/tank/main_tank_" + i + ".png";
                mainTanks[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream(path));
            }

            for (int i = 0; i < hostileTanks.length; i ++) {
                String path = "images/tank/hostile_tank_" + i + ".png";
                hostileTanks[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream(path));
            }

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

            for (int i = 0; i < awardTanks.length; i ++) {
                String path = "images/new/new_tank" + i + ".png";
                awardTanks[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream(path));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
