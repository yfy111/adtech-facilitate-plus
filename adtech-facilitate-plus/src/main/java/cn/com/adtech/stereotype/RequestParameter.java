package cn.com.adtech.stereotype;

/**
 * @Description 请求参数抽象父类
 * @Author chenguangxue
 * @CreateDate 2018/11/1 11:06
 */
public abstract class RequestParameter {

    // 请求参数的校验方法：默认为成功，子类可自行重写
    public ResultStatus verify() {
        return ResultStatus.success();
    }

    // 请求参数的数据库校验方法：默认为成功，子类可以重写
}
