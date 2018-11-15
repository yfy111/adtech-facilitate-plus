package cn.com.adtech.controller.app;

import cn.com.adtech.component.UserinfoService;
import cn.com.adtech.domain.password.change.ChangePasswordParameter;
import cn.com.adtech.domain.password.change.FirstLoginChangePasswordParameter;
import cn.com.adtech.domain.password.reset.ResetPasswordParameter;
import cn.com.adtech.service.PasswordService;
import cn.com.adtech.stereotype.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 密码操作的控制器
 * @Author chenguangxue
 * @CreateDate 2018/11/2 14:09
 */
@RestController
@RequestMapping(value = "/rest")
@Api(value = "密码操作接口", tags = "密码操作接口")
public class PasswordRestController {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserinfoService userinfoService;

    // 首次访问系统，设置密码的操作
    @PostMapping(value = "/first-change")
    @ApiOperation(value = "首次访问系统设置密码的接口")
    public ResponseResult firstLoginChangePassword(@RequestBody FirstLoginChangePasswordParameter parameter) {
        return passwordService.doFirstLoginChangePassword(parameter);
    }

    @PostMapping(value = "/change-password")
    @ApiOperation(value = "修改密码的接口")
    public ResponseResult changePassword(@RequestBody ChangePasswordParameter parameter) {
        return passwordService.doChangePassword(parameter, userinfoService.loadH5LoginUserinfo());
    }

    @PostMapping(value = "/reset-password")
    @ApiOperation(value = "重置密码的接口")
    public ResponseResult resetPassword(@RequestBody ResetPasswordParameter parameter) {
        return passwordService.doResetPassword(parameter);
    }
}