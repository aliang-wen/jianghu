package com.aliang.aliangbok_until.securitycode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SecurityCode {
    /**
     * @Description: 图片验证码的自动生成
     * @Param: 高度 宽度 字符个数  字符间的间距  字符的大小  字符的高度
     * @Return: map.code编码  map.buffImg图像流
     * @author: 作者 阿良
     * @CreateDate:
     */
    public static Map<String,Object> generateCodeAndPic(Integer height, Integer width, Integer codeCount, Integer  xx,
                                                        Integer fontHeight, Integer codeY) {
        char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a','b','c','d','e','f','g','h','i','j','k','l','m',
                'm','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        // 定义图像buffer   就是定义了一个画板   设置其中的宽高
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // Graphics2D gd = buffImg.createGraphics();
        // Graphics2D gd = (Graphics2D) buffImg.getGraphics();
        Graphics gd = buffImg.getGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色  这里应该是设置一块画布为白色  设置宽高为多少的
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);//什么样的字体  字体的粗细  字体的大小
        // 设置字体。  将设置好的字体带入
        gd.setFont(font);

        // 画边框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);//从位置0,0开始  到了尾部的时候余下位置进行 画线

        // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
        gd.setColor(Color.BLACK);
        //gd.setColor(new Color(red, green, blue));这种方法不是更好？？
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(width);//0-89
            int y = random.nextInt(height);//0-20
            int xl = random.nextInt(12);//0-11
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl); //第一个点的xy 和第二个点的xy  这样就能在两点之间画出一条线

        }

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;//三原色的定义

        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String code = String.valueOf(codeSequence[random.nextInt(61)]);//这是随机从数组中取字母
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red, green, blue));
            gd.drawString(code, (i + 1) * xx, codeY);//第一个参数是字符  第二个是设置的每个距离前面的距离   第三个是设置的高度

            // 将产生的四个随机数组合在一起。
            randomCode.append(code);
        }
        Map<String,Object> map  =new HashMap<String,Object>();
        //存放验证码
        map.put("code", randomCode);
        //存放生成的验证码BufferedImage对象
        map.put("codePic", buffImg);
        return map;
    }
}
