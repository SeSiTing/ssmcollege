package com.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ImageUtil {

    public static final int WIDTH =80;//图片的宽
    public static final int HEIGHT =30;//图片的高
    public static String code = "";//定义一个变量保存生成后的验证码字符串

    //生成验证码
    public static String createcode() {
        code = "";
        String a = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 4; i++) {
            int index= (int) (Math.random() * 62);
            char k = a.charAt(index);
            code = code + k;
        }
        return code;
    }

    //查看已生成的验证码
    public static String getCode() {
        return code;
    }

    //生成图片
    public static BufferedImage createimage() {

        //1.得到一个画布，内存中创建一张图片
        BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        //2.得到图片
        Graphics g=bi.getGraphics();
        //3.设置图片背景，边框,干扰线
        setBackGround(g);
        setBorder(g);
        drawRandomLine(g);
        //4.添加随机数
        setcode(g);
        //5.将随机数填充到画板上
        g.drawString(code,10,25);
        //6.关闭画布
        g.dispose();

        return bi;
    }

    //设置图片背景色
    private static void setBackGround(Graphics g) {
        //颜色
        g.setColor(Color.WHITE);
        //填充区域
        g.fillRect(0,0,WIDTH,HEIGHT);
    }

    //设置图片的边框
    private static void setBorder(Graphics g) {
        //边框颜色
        g.setColor(Color.WHITE);
        //边框区域
        g.drawRect(1,1,WIDTH-2,HEIGHT-2);
    }

    //在图片上画随机条
    private static void drawRandomLine(Graphics g) {
        //干扰线个数
        for (int i=0;i<9;i++) {
            //干扰线颜色
            int red = new Random().nextInt(256);
            int green = new Random().nextInt(256);
            int blue = new Random().nextInt(256);
            Color c = new Color(red, green, blue);
            g.setColor(c);
            //干扰线长度
            int x1=new Random().nextInt(WIDTH);
            int x2=new Random().nextInt(WIDTH);
            int y1=new Random().nextInt(HEIGHT);
            int y2=new Random().nextInt(HEIGHT);
            //画干扰线 Line为横线 Oval为椭圆
            g.drawLine(x1,x2,y1,y2);
        }
    }

    //将验证码写在图片上
    private static void setcode(Graphics g) {
        //字体颜色
        g.setColor(Color.RED);
        //字体
        g.setFont(new Font("宋体",Font.BOLD,25));
    }
}
