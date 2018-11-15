package cn.com.adtech.controller.web;

import cn.com.adtech.component.UserinfoService;
import cn.com.adtech.domain.password.change.ChangePasswordParameter;
import cn.com.adtech.service.PasswordService;
import cn.com.adtech.stereotype.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/12 16:44
 */
@Controller
@RequestMapping(value = "/web/password")
public class PasswordWebController {

    @Autowired
    private PasswordService passwordService;
    @Autowired
    private UserinfoService userinfoService;

    @PostMapping(value = "/change")
    @ResponseBody
    public ResponseResult doChangePassword(ChangePasswordParameter parameter) {
        return passwordService.doChangePassword(parameter, userinfoService.loadWebLoginUserinfo());
    }
}
