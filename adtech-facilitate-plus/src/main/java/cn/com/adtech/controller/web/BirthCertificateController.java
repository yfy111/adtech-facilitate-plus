package cn.com.adtech.controller.web;

import cn.com.adtech.component.UserinfoService;
import cn.com.adtech.domain.login.LoginSuccessUserinfo;
import cn.com.adtech.domain.medicalinformation.request.BirthCertificateParameter;
import cn.com.adtech.domain.medicalinformation.response.BirthCertificateShowData;
import cn.com.adtech.service.BirthCertificateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description
 * @Author chenguangxue
 * 出生医学证明
 * @CreateDate 2018/11/2 16:53
 */
@Api(value = "/web/birth", tags = "web出生医学证明页面")
@Controller
@RequestMapping("/web/medical-information")
public class BirthCertificateController {

    @Autowired
    private BirthCertificateService birthCertificateService;
    @Autowired
    private UserinfoService userinfoService;
    /**
     * 出生医学证明页面
     *
     * @param birthCertificateParameter
     * @return
     */
    @ApiOperation(value = "出生医学证明", notes = "出生医学证明")
    @PostMapping(value = "/index")
    @ResponseBody
    public ModelAndView showIndex(@RequestBody BirthCertificateParameter birthCertificateParameter) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("birth/index");
        LoginSuccessUserinfo user =userinfoService.loadWebLoginUserinfo();
        modelAndView.addObject("obj", birthCertificateService.selectBirthCertificateInfo(birthCertificateParameter,user));
        return modelAndView;
    }

    // 显示出生医学证明的详情页面
    @GetMapping(value = "/birth-certificate-data")
    public String showBirthCertificateData(BirthCertificateParameter parameter, Model model) {
        LoginSuccessUserinfo user =userinfoService.loadWebLoginUserinfo();
        BirthCertificateShowData data = birthCertificateService.selectBirthCertificateInfo(parameter,user);
        model.addAttribute("info", data);
        return "medical_information/birth_certificate";
    }
}
