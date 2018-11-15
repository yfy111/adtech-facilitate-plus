package cn.com.adtech.component;

import cn.com.adtech.environment.properties.QRCodeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/12 18:01
 */
@Component
public class QRCodeService {

    @Autowired
    private QRCodeProperties qrCodeProperties;

    // 生成二维码图片文件
    public BufferedImage generateQRCodeImage(String randomCode) {
        return null;
    }
}
