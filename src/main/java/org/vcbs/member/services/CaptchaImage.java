package org.vcbs.member.services;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class CaptchaImage {

    private static final Random random = new Random();
    private final int width = 160;// 宽
    private final int height = 40;// 高
    private final int lineSize = 30;// 干扰线数量
    private final int stringNum = 4;//随机产生字符的个数
    private final String randomString = "123456789";

    /*
     *  获取颜色
     */
    private static Color getRandomColor(int fc, int bc) {

        fc = Math.min(fc, 255);
        bc = Math.min(bc, 255);

        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 12);

        return new Color(r, g, b);
    }

    /*
     *  获取字体
     */
    private Font getFont() {
        return new Font(Font.SANS_SERIF, Font.BOLD, 40);
    }

    /*
     *  绘制干扰线
     */
    private void drawLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(20);
        int yl = random.nextInt(10);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /*
     *  获取随机字符
     */
    private String getRandomString(int num) {
        num = num > 0 ? num : randomString.length();
        return String.valueOf(randomString.charAt(random.nextInt(num)));
    }

    /*
     *  绘制字符串
     */
    private String drawString(Graphics g, String randomStr, int i) {
        g.setFont(getFont());
        g.setColor(getRandomColor(108, 190));
        String rand = getRandomString(random.nextInt(randomString.length()));
        randomStr += rand;
        g.translate(random.nextInt(3), random.nextInt(6));
        g.drawString(rand, 40 * i + 10, 25);
        return randomStr;
    }

    /*
     *  生成随机图片
     */
    public CaptchaImageInfo getRandomCodeImage() throws IOException {
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, width, height);
        g.setColor(getRandomColor(105, 189));
        g.setFont(getFont());

        // 绘制干扰线
        for (int i = 0; i < lineSize; i++) {
            drawLine(g);
        }

        // 绘制随机字符
        String randomString = "";
        for (int i = 0; i < stringNum; i++) {
            randomString = drawString(g, randomString, i);
        }

        g.dispose();

        StringBuilder encodedImage = new StringBuilder();
        encodedImage.append("data:image/png;base64,");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", bos);
        byte[] bytes = bos.toByteArray();
        Base64.Encoder encoder = Base64.getEncoder();
        encodedImage.append(encoder.encodeToString(bytes));
        return new CaptchaImageInfo(randomString, encodedImage.toString());
    }

    public class CaptchaImageInfo {
        public String code;
        public String img;

        public CaptchaImageInfo(String _code, String _img) {
            this.code = _code;
            this.img = _img;
        }
    }
}
