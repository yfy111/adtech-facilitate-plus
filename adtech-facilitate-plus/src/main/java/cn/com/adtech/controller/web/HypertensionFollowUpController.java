package cn.com.adtech.controller.web;

import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import cn.com.adtech.domain.medicalinformation.request.HypertensionFollowUpParameter;
import cn.com.adtech.domain.medicalinformation.request.IdentityCardParameter;
import cn.com.adtech.domain.medicalinformation.request.NewbornFollowUpParameter;
import cn.com.adtech.service.HypertensionFollowUpService;
import cn.com.adtech.util.CreateHtmlFiledUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description 高血压随访记录
 * @CreateDate 2018/11/2 16:55
 */
@Api(value="/web/hypertension", tags = "web高血压随访主页")
@Controller
@RequestMapping("/web/hypertension")
public class HypertensionFollowUpController extends MedicalInformationShowData {

    @Autowired
    private HypertensionFollowUpService hypertensionFollowUpService;
    /**
     * 高血压随访主页
     * @return
     */
    @ApiOperation(value="高血压随访主页",notes = "显示高血压随访记录信息")
    @PostMapping(value = "/index")
    @ResponseBody
    public ModelAndView showIndex(@RequestBody HypertensionFollowUpParameter hypertensionFollowUpParameter) {
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("hypertension/basic_info");
        modelAndView.setViewName("medical_information/newborn_followup");
        CreateHtmlFiledUtil createHtmlUtil = new CreateHtmlFiledUtil(hypertensionFollowUpService.showIndex(hypertensionFollowUpParameter),"-");
        modelAndView.addObject("info",createHtmlUtil.create());
        return modelAndView;
    }

    /**
     * 高血压随访主页随访日期加载
     * @param identityCardParameter
     * @return
     */
    @ApiOperation(value="高血压随访主页随访日期",notes = "高血压随访主页随访日期")
    @PostMapping(value = "/followUpTime")
    @ResponseBody
    public ModelAndView followUpTime(@RequestBody IdentityCardParameter identityCardParameter) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("medical_information/common_date_list");
        modelAndView.addObject("date",hypertensionFollowUpService.followUpTime(identityCardParameter));
        return modelAndView;
    }
}
