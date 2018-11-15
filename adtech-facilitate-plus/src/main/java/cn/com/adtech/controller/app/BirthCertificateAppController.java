package cn.com.adtech.controller.app;

import cn.com.adtech.component.UserinfoService;
import cn.com.adtech.domain.login.LoginSuccessUserinfo;
import cn.com.adtech.domain.medicalinformation.request.BirthCertificateParameter;
import cn.com.adtech.domain.medicalinformation.response.BirthCertificateShowData;
import cn.com.adtech.service.BaseInfoService;
import cn.com.adtech.service.BirthCertificateService;
import cn.com.adtech.stereotype.ResponseResult;
import cn.com.adtech.stereotype.ResultStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * 出生医学证明
 * @CreateDate 2018/11/2 16:53
 */
@Api(value="/app/birth", tags = "app出生医学证明页面")
@Controller
@RequestMapping("/app/birth")
public class BirthCertificateAppController{
    @Autowired
    private BirthCertificateService birthCertificateService;
    @Autowired
    private UserinfoService userinfoService;
    /**
     * 出生医学证明页面
     * @param parameter
     * @return
     */
    @ApiOperation(value="出生医学证明",notes = "出生医学证明")
    @PostMapping(value = "/index")
    @ResponseBody
    public ResponseResult<BirthCertificateShowData> showIndex(@RequestBody BirthCertificateParameter parameter) {
        LoginSuccessUserinfo user =userinfoService.loadH5LoginUserinfo();
        BirthCertificateShowData info = birthCertificateService.selectBirthCertificateInfo(parameter,user);
        return ResponseResult.result(ResultStatus.success()).data(info);
    }

}
