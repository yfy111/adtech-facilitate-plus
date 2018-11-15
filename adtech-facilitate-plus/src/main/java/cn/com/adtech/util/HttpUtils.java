package cn.com.adtech.util;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.BiConsumer;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/6 17:48
 */
public class HttpUtils {

    // 使用响应向页面上输出图形
    public static BiConsumer<HttpServletResponse, Image> outputImage = (response, image) -> {
        response.setDateHeader("Expires", 0);
        response.setHeader("Transfer-Encoding", "utf-8");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/png");

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write((BufferedImage) image, "png", outputStream);
            outputStream.flush();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
