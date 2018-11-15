package cn.com.adtech.controller.web.doctor;

import cn.com.adtech.domain.doctor.DoctorIndexParameter;
import cn.com.adtech.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/13 10:20
 */
@Controller
@RequestMapping(value = "/web/doctor")
@Slf4j
public class DoctorPageController {

    // 显示医生工作站的登录页面
    @GetMapping(value = "/login")
    public String showDoctorLogin(Model model) {
        return "doctor/login";
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IndexService indexService;

    // 进入医生工作站的web端主页
    @RequestMapping(value = "/index")
    public String showDoctorIndex(DoctorIndexParameter parameter) {
        // 从redis中获取进入医生端的身份证信息
        String loginToken = parameter.getLoginToken();
        String value = redisTemplate.opsForValue().get(loginToken);

        // 将value拆分为2个部分：第一部分是loginType、第二部分是身份证号
        String[] array = parameter.getLoginType().split(value);
        String identity = array[1];

        log.info("访问医生工作站主页的用户信息为：{}", identity);

        // 返回医生工作站的主页
        return "doctor/index";
    }

    // 显示医生工作站主页 - 测试专用
    @GetMapping(value = "/index-test")
    public String showDoctorIndexTest() {
        // 默认读取指定用户的医疗信息和就诊记录
//        indexService.queryIndexShowData();

        return "doctor/index";
    }
}
