package cn.com.adtech.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author shijin 2011-11-18
 */
public class EncryptUtils {
    // 十六进制下数字到字符的映射数组
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 把inputString加密
     */
    public static String md5(String inputString) {
        return encodeByMD5(inputString);
    }

    /**
     * 验证输入的密码是否正确
     *
     * @param md5Str      真正的密码（加密后的真密码）
     * @param inputString 输入的字符串
     * @return 验证结果，boolean类型
     */
    public static boolean compare(String md5Str, String inputString) {
        return md5Str.equals(encodeByMD5(inputString));
    }

    /**
     * 对字符串进行MD5编码
     */
    private static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                // 创建具有指定算法名称的信息摘要
                MessageDigest md = MessageDigest.getInstance("MD5");
                // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md.digest(originString.getBytes(StandardCharsets.UTF_8));
                // 将得到的字节数组变成字符串返回
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 轮换字节数组为十六进制字符串
     *
     * @param b 字节数组
     * @return 十六进制字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /**
     * 将一个字节转化成十六进制形式的字符串
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
