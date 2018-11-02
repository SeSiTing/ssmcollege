package com.web;

import com.util.ImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Controller
public class ImgcodeController {

    @RequestMapping(value = "/getcode")
    public void getcode(HttpServletResponse response) {
        try {
            ImageUtil.createcode();
            BufferedImage image = ImageUtil.createimage();

            //以流的形式返回客户端
            response.setContentType("image/jsep");
            ByteArrayOutputStream bt = new ByteArrayOutputStream();

            //将图片转换成字节流
            ImageIO.write(image, "jpeg", bt);

            //得到输出流，返回给客户端
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bt.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
