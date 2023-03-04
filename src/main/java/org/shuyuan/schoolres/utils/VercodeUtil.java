package org.shuyuan.schoolres.utils;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Component
public class VercodeUtil
{
    private static final int MAX_WIDTH = 100;
    private static final int MAX_HEIGHT = 38;

    public static String getVerifyCode(Random rmd)
    {
        StringBuffer verifyCode = new StringBuffer("");

        for (int i = 0; i < 6; i++)
        {
            var nextId = rmd.nextInt(3);
            char val;

            switch (nextId)
            {
                case 0:
                    val = (char) (rmd.nextInt(26) + 'a');
                    break;
                case 1:
                    val = (char) (rmd.nextInt(26) + 'A');
                    break;
                default:
                    val = (char) (rmd.nextInt(10) + '0');
                    break;
            }
            verifyCode.append(val);
        }

        return verifyCode.toString();
    }
    public static BufferedImage getVerifyCodeImg(String verifyCode, Random rdm)
    {
        if (verifyCode == null || rdm == null)
        {
            return null;
        }

        BufferedImage image = new BufferedImage(MAX_WIDTH, MAX_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setFont(new Font("SemiBold", Font.BOLD, 18));

        var chars = verifyCode.toCharArray();

        for (int i = 0; i < chars.length; i++)
        {
            int nextId = rdm.nextInt(3);

            switch (nextId)
            {
                case 0:
                    graphics.setColor(Color.red);
                    graphics.drawString(chars[i] + "", 15 * i + 1, 30);
                    graphics.drawLine(0, 5 * i + 5, 200, 2 * i + 2);
                    break;
                case 1:
                    graphics.setColor(Color.YELLOW);
                    graphics.drawString(chars[i] + "", 15 * i + 1, 20);
                    graphics.drawLine(0, 5 * i + 5, 200, 4 * i + 6);
                    break;
                default:
                    graphics.setColor(Color.CYAN);
                    graphics.drawString(chars[i] + "", 15 * i + 1, 25);
                    graphics.drawLine(0, 5 * i + 5, 200, 6 * i + 4);
                    break;
            }
        }

        return image;
    }
}
