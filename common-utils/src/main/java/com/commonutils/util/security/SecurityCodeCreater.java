package com.commonutils.util.security;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *  功能描述：  用来生成验证码图片，测试需要，常量，方法都设置成了static<br/>
 */
public class SecurityCodeCreater {

    //图片的宽度
    private final static int IMAGEWIDTH = 70;
    //图片的高度
    private final static int IMAGEHEIGHT = 30;

    //字体大小
    private final static int FONTSIZE = 20;

    //字符串长度
    private final static int CODE_LENGTH = 4;

    //随机字符范围
    private final static char[] CHAR_RANGE = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9'
    };

//    public static  void main(String[] args)throws Exception
//    {
//
//	     //此main方法用来测试生成的验证码图像
//
//	     FileOutputStream fos = new FileOutputStream("c://test.jpg");
//	     JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
//	     encoder.encode(getImage(getRandString()));
//    }


    private static Random random = new Random();

    /**
     * 生成随机字符串
     * @return 随机字符串
     */
    public  static String getRandString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++)
        {
            sb.append(CHAR_RANGE[random.nextInt(CHAR_RANGE.length)]);
        }
        return sb.toString();
    }

    /**
     * 生成随机颜色
     * @param ll 产生颜色值下限(lower limit)
     * @param ul 产生颜色值上限(upper limit)
     * @return 生成的随机颜色对象
     */
    private static Color getRandColor(int ll, int ul) {
        if (ll > 255) {
            ll = 255;
        }
        if (ll < 1) {
            ll = 1;
        }
        if (ul > 255) {
            ul = 255;
        }
        if (ul < 1) {
            ul = 1;
        }
        if (ul == ll) {
            ul = ll + 1;
        }
        int r = random.nextInt(ul - ll) + ll;
        int g = random.nextInt(ul - ll) + ll;
        int b = random.nextInt(ul - ll) + ll;
        Color color = new Color(r,g,b);
        return color;
    }

    /**
     * 生成指定字符串的图像数据
     * @param verifyCode 即将被打印的随机字符串
     * @return 生成的图像数据
     * */
    public static BufferedImage getImage(String verifyCode){
        BufferedImage buffImg = new BufferedImage(IMAGEWIDTH, IMAGEHEIGHT,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();

        // 创建一个随机数生成器类。
        Random random = new Random();
        // 设定图像背景色(因为是做背景，所以偏淡)
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, IMAGEWIDTH, IMAGEHEIGHT);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Times New Roman", Font.HANGING_BASELINE, FONTSIZE);
        // 设置字体。
        g.setFont(font);
        // 画边框。
        // g.setColor(Color.BLACK);
        g.drawRect(0, 0, IMAGEWIDTH - 1, IMAGEHEIGHT - 1);
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到。
        // g.setColor(Color.GRAY);
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(IMAGEWIDTH);
            int y = random.nextInt(IMAGEHEIGHT);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // 随机产生4位数字的验证码。
        for (int i = 0; i < verifyCode.length(); i++) {
            // 得到随机产生的验证码数字。
            String temp = verifyCode.substring(i, i+1);

            // 用随机产生的颜色将验证码绘制到图像中。
            // 生成随机颜色(因为是做前景，所以偏深) 80 //g.setColor(getRandColor(1, 100)); 81
            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));

            g.drawString(temp, 15 * i + 6, 24);
        }
        return buffImg;
    }
}

