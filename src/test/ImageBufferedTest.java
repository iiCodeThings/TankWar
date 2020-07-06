package test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageBufferedTest {

    public static void main(String[] args) {

        try {
            BufferedImage bufferedImage = ImageIO.read(ImageBufferedTest.class.getClassLoader().getResourceAsStream("images/tank.jpg"));
            System.out.println(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
