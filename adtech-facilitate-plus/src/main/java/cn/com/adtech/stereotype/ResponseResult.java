package cn.com.adtech.stereotype;

import cn.com.adtech.enums.ResponseResultStatus;
import lombok.Getter;

/**
 * @Description 响应结果的统一封装类
 * @Author chenguangxue
 * @CreateDate 2018/11/1 11:07
 */
@Getter
public class ResponseResult<T> {

    private final String code;
    private final String message;

    // 代表是否处理完成，如果没有发生异常，则为true，否则为false，和业务结果无关
    private final boolean deal;

    // 代表处理结果是否成功，必须在处理完成的情况下，由业务的实际结果为准
    private final boolean success;

    private T data;

    private ResponseResult(String code, String message, boolean deal, boolean success) {
        this.code = code;
        this.message = message;
        this.deal = deal;
        this.success = success;
        this.data = null;
    }

    // 默认的操作成功的结果
    public static ResponseResult mockSuccess() {
        return result(ResponseResultStatus.SUCCESS);
    }

    // 默认的操作失败的结果
    public static ResponseResult mockFail() {
        return result(ResponseResultStatus.FAIL);
    }

    // 异常的错误结果
    public static ResponseResult exception(ResultStatus status) {
        return new ResponseResult(status.getCode(), status.getMessage(), false, status.isSuccess());
    }

    // 以指定的message为返回的文本内容
    public static ResponseResult success(String message) {
        return new ResponseResult("SUCCESS", message, true, true);
    }

    // 以指定的message为返回的文本内容
    public static ResponseResult fail(String message) {
        return new ResponseResult("FAIL", message, true, false);
    }

    // 设置结果状态
    public static ResponseResult result(ResultStatus status) {
        return new ResponseResult(status.getCode(), status.getMessage(), true, status.isSuccess());
    }

    // 向响应中添加返回的单个数据
    public ResponseResult data(T t) {
        this.data = t;
        return this;
    }
}
