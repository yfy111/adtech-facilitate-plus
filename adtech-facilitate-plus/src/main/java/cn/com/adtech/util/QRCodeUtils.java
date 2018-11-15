package cn.com.adtech.util;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.EnumMap;
import java.util.Hashtable;
import java.util.UUID;

/**
 * @Description 专门用于处理二维码的工具类
 * @Author chenguangxue
 * @CreateDate 2018/10/28 14:31
 */
public class QRCodeUtils {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int margin = 0;
    private static final int LogoPart = 4;

    public static void main(String[] args) throws IOException {
        // 生成测试用的json数据
        JSONObject testJson = new JSONObject();

        // 扫码登录验证的接口路径
        testJson.put("verifyUrl", "http://localhost:8082/rest/scan-login");

        // 扫码登录验证接口的请求方式
        testJson.put("requestMethod", "post");

        // 执行扫码登录的时候必须携带，否则不允许扫码登录验证
        testJson.put("verifyCode", UUID.randomUUID().toString());

        // 这个是生成二维码的时间(毫秒数)，二维码从生成到过期的有效期为1分钟
        testJson.put("timestamp", System.currentTimeMillis());

        BitMatrix bitMatrix = setBitMatrix(testJson.toJSONString(), 300, 300);
        BufferedImage bufferedImage = toBufferedImage(bitMatrix);
        addLogo(bufferedImage, "/Users/cgx/Downloads/qr-code-logo.png");
        File file = new File("/Users/cgx/work/Adtech/test/qr-code.png");
        ImageIO.write(bufferedImage, "png", file);

        String content = decodeQR("/Users/cgx/work/Adtech/test/qr-code.png");
        System.out.println(content);
    }

    /**
     * 生成二维码矩阵信息
     *
     * @param content 二维码图片内容
     * @param width   二维码图片宽度
     * @param height  二维码图片高度
     */
    public static BitMatrix setBitMatrix(String content, int width, int height) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();

        // 指定编码方式,防止中文乱码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

        // 指定二维码四周白色区域大小
        hints.put(EncodeHintType.MARGIN, margin);

        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        }
        catch (WriterException e) {
            e.printStackTrace();
        }
        return bitMatrix;
    }

    /**
     * 生成二维码图片
     *
     * @param matrix 二维码矩阵信息
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    /**
     * 为图片添加文字
     *
     * @param pressText   文字
     * @param newImage    带文字的图片
     * @param targetImage 需要添加文字的图片
     * @param fontStyle   字体风格
     * @param color       字体颜色
     * @param fontSize    字体大小
     * @param width       图片宽度
     * @param height      图片高度
     */
    public static void pressText(String pressText, String newImage, String targetImage, int fontStyle, Color color, int fontSize, int width, int height) {
        // 计算文字开始的位置
        // x开始的位置：（图片宽度-字体大小*字的个数）/2
        int startX = (width - (fontSize * pressText.length())) / 2;
        // y开始的位置：图片高度-（图片高度-图片宽度）/2
        int startY = height - (height - width) / 2 + fontSize;
        try {
            File file = new File(targetImage);
            BufferedImage src = ImageIO.read(file);
            int imageW = src.getWidth(null);
            int imageH = src.getHeight(null);
            BufferedImage image = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, imageW, imageH, null);
            g.setColor(color);
            g.setFont(new Font(null, fontStyle, fontSize));
            g.drawString(pressText, startX, startY);
            g.dispose();
            FileOutputStream out = new FileOutputStream(newImage);
            ImageIO.write(image, "png", out);
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将二维码图片输出
     *
     * @param matrix    二维码矩阵信息
     * @param format    图片格式
     * @param outStream 输出流
     * @param logoPath  logo图片路径
     */
    public static void writeToFile(BitMatrix matrix, String format, OutputStream outStream, String logoPath) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        addLogo(image, logoPath);
        ImageIO.write(image, format, outStream);
    }

    /**
     * 在二维码图片中添加logo图片
     *
     * @param image    二维码图片
     * @param logoPath logo图片路径
     */
    public static BufferedImage addLogo(BufferedImage image, String logoPath) throws IOException {
        Graphics2D g = image.createGraphics();
        BufferedImage logoImage = ImageIO.read(new File(logoPath));
        // 计算logo图片大小,可适应长方形图片,根据较短边生成正方形
        int width = image.getWidth() < image.getHeight() ? image.getWidth() / LogoPart : image.getHeight() / LogoPart;
        int height = width;
        // 计算logo图片放置位置
        int x = (image.getWidth() - width) / 2;
        int y = (image.getHeight() - height) / 2;
        // 在二维码图片上绘制logo图片
        g.drawImage(logoImage, x, y, width, height, null);
        // 绘制logo边框,可选
        g.setStroke(new BasicStroke(2)); // 画笔粗细
        g.setColor(Color.WHITE); // 边框颜色
        g.drawRect(x, y, width, height); // 矩形边框
        logoImage.flush();
        g.dispose();
        return image;
    }

    // 解析二维码的内容
    public static String decodeQR(String filePath) {
        if ("".equalsIgnoreCase(filePath) && filePath.length() == 0) {
            return "二维码图片不存在!";
        }
        String content = "";
        EnumMap<DecodeHintType, Object> hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8"); // 指定编码方式,防止中文乱码
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            MultiFormatReader reader = new MultiFormatReader();
            Result result = reader.decode(binaryBitmap, hints);
            content = result.getText();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    // 生成二维码的唯一编码
    public static String generateServerCode(HttpServletRequest request) {
        return "QR-CODE-SERVER-CODE-" + request.getSession().getId();
    }
}
