package cn.com.adtech.exception;

import cn.com.adtech.stereotype.ResultStatus;
import lombok.Getter;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/1 11:52
 */
@Getter
public enum ExceptionStatus implements ResultStatus {

    RESULT_STATUS_MUST_IMPLEMENTS_BY_ENUM("ResultStatus必须由枚举实现"),
    ;

    private final String code;
    private final String message;

    ExceptionStatus(String message) {
        this.message = message;
        this.code = this.name().toUpperCase();
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}
