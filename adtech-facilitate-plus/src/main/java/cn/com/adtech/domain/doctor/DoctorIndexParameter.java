package cn.com.adtech.domain.doctor;

import cn.com.adtech.enums.LoginType;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @Description 进入医生工作站主页的请求参数
 * @Author chenguangxue
 * @CreateDate 2018/11/13 19:34
 */
@Data
public class DoctorIndexParameter {

    // 登录类型
    private LoginType loginType;

    // 登录token
    private String loginToken;

    // 校验参数是否完整
    public boolean verifyParameter() {
        return !StringUtils.isEmpty(loginType) && !StringUtils.isEmpty(loginToken);
    }
}
