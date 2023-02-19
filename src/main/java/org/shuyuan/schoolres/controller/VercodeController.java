package org.shuyuan.schoolres.controller;

import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;

@Controller
public class VercodeController
{
    private final int MAX_WIDTH = 100;
    private final int MAX_HEIGHT = 38;

    @SneakyThrows
    @GetMapping("/vercode")
    public void validate(OutputStream os, HttpSession session)
    {
        Random rd = new Random();
        StringBuffer vercode = new StringBuffer("");
        BufferedImage image = new BufferedImage(MAX_WIDTH, MAX_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setFont(new Font("SemiBold", Font.BOLD, 18));

        for (int i = 0; i < 6; i++)
        {
            var nextId = rd.nextInt(3);
            char val;
            switch (nextId)
            {
                case 0:
                    val = (char) (rd.nextInt(26) + 'a');
                    graphics.setColor(Color.red);
                    graphics.drawString(val + "", 15 * i + 1, 30);
                    graphics.drawLine(0, 5 * i + 5, 200, 2 * i + 2);
                    break;
                case 1:
                    val = (char) (rd.nextInt(26) + 'A');
                    graphics.setColor(Color.YELLOW);
                    graphics.drawString(val + "", 15 * i + 1, 20);
                    graphics.drawLine(0, 5 * i + 5, 200, 4 * i + 6);
                    break;
                default:
                    val = (char) (rd.nextInt(10) + '0');
                    graphics.setColor(Color.CYAN);
                    graphics.drawString(val + "", 15 * i + 1, 25);
                    graphics.drawLine(0, 5 * i + 5, 200, 6 * i + 4);
                    break;
            }
            vercode.append(val);
        }
        session.setAttribute("vercode", vercode);
        ImageIO.write(image, "PNG", os);
    }
}
