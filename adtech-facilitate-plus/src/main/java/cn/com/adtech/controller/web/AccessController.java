package cn.com.adtech.controller.web;

import cn.com.adtech.domain.vo.LoginUserinfo;
import cn.com.adtech.environment.properties.FacilitateProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description 访问控制器
 * @Author chenguangxue
 * @CreateDate 2018/11/1 22:14
 */
@Controller
@RequestMapping(value = {"/page", "/"})
@Api(value = "web端登录页", tags = "web端登录页")
public class AccessController {

    @Autowired
    private FacilitateProperties properties;

    /* 普通用户的登录页面 */
    @GetMapping(value = "/login")
    @ApiOperation(value = "普通用户的登录页")
    public String showLogin(@RequestParam(required = false) String appId, @RequestParam(required = false) String institutionCode, Model model) {
        // appId默认情况为12320网站
        if (StringUtils.isEmpty(appId)) {
            appId = "ADTECH_12320";
        }
        LoginUserinfo loginUserinfo = properties.isUseMockData() ? LoginUserinfo.mock() : new LoginUserinfo();
        model.addAttribute("login", loginUserinfo);
        model.addAttribute("projectName", properties.getProjectName());
        return "login";
    }

    // 普通用户首次登录，强制修改密码的页面
    @GetMapping(value = "/must-change-password")
    public String showFirstLoginChangePassword(@RequestParam String identity, Model model) {
        model.addAttribute("identity", identity);
        return "must_change_password";
    }

    /* 医生端的登录页面 */
    @GetMapping(value = "/doctor-login")
    @ApiOperation(value = "医生端的登录页")
    public String showDoctorLogin(@RequestParam String appId, @RequestParam String institutionCode, Model model) {
        return "doctor-login";
    }
}
