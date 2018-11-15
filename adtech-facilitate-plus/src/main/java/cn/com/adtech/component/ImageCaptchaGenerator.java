package cn.com.adtech.component;

import cn.com.adtech.environment.properties.ImageCaptchaProperties;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Description 验证码生成器
 * @Author chenguangxue
 * @CreateDate 2018/11/6 10:36
 */
@Component
public class ImageCaptchaGenerator {

    @Autowired
    private ImageCaptchaProperties properties;

    // 生成验证码及图片
    public ImageCaptchaResult createVerifyObjects() {
        String code = createVerifyCode();
        Image image = createVerifyImage(code);
        return new ImageCaptchaResult(code, image);
    }

    @Getter
    public static class ImageCaptchaResult {
        private final String code;
        private final Image image;

        private ImageCaptchaResult(String code, Image image) {
            this.code = code;
            this.image = image;
        }
    }

    // 生成随机验证码
    private String createVerifyCode() {
        int length = properties.getCharacterScope().length();
        char[] code = new char[properties.getCharacterCount()];

        for (int i = 0, size = properties.getCharacterCount(); i < size; i++) {
            int n = random.nextInt(length);
            code[i] = properties.getCharacterScope().charAt(n);
        }

        return new String(code);
    }

    private final Random random = new Random();

    // 生成图片
    private Image createVerifyImage(String code) {
        int width = properties.getWidth();
        int height = properties.getHeight();

        // 空白图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图片画笔
        Graphics graphic = image.getGraphics();
        // 设置画笔颜色
        graphic.setColor(Color.WHITE);
        // 绘制矩形背景
        graphic.fillRect(0, 0, width, height);

        for (int i = 0, size = code.length(); i < size; i++) {
            // 设置随机颜色
            graphic.setColor(getRandomColor());
            // 设置字体大小
            graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, properties.getFontSize()));
            // 画字符
            graphic.drawString(code.charAt(i) + "", i * width / properties.getCharacterCount(), height * 2 / 3);
        }

        for (int i = 0, size = properties.getLineCount(); i < size; i++) {
            // 设置随机颜色
            graphic.setColor(getRandomColor());
            // 随机画线
            graphic.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
        }

        return image;
    }

    // 获取随机颜色
    private Color getRandomColor() {
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
