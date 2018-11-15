package cn.com.adtech.stereotype;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 标识出结果的接口
 * @Author chenguangxue
 * @CreateDate 2018/11/1 11:29
 */
public interface ResultStatus {

    // 默认必须是枚举实现这个接口，所以code就是枚举值的name()
    String getCode();

    String getMessage();

    boolean isSuccess();

    default boolean isFail() {
        return !isSuccess();
    }

    // 这是代表某种操作成功的结果状态
    static ResultStatus success() {
        return new ResultStatus() {
            @Override
            public String getCode() {
                return "";
            }

            @Override
            public String getMessage() {
                return "成功";
            }

            @Override
            public boolean isSuccess() {
                return true;
            }
        };
    }

    // 这是代表某种操作失败的结果状态
    static ResultStatus fail(String message) {
        return new ResultStatus() {
            @Override
            public String getCode() {
                return "";
            }

            @Override
            public String getMessage() {
                return message;
            }

            @Override
            public boolean isSuccess() {
                return false;
            }
        };
    }

    Map<String, ResultStatus> cache = new HashMap<>();
}
