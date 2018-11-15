package cn.com.adtech.controller.web;

import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import cn.com.adtech.domain.medicalinformation.request.IdentityCardParameter;
import cn.com.adtech.domain.medicalinformation.request.NewbornFollowUpParameter;
import cn.com.adtech.domain.medicalinformation.response.NewbornFollowUpShowData;
import cn.com.adtech.service.NewbornFollowUpService;
import cn.com.adtech.util.CreateHtmlFiledUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Description 新生儿随访记录
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:55
 */
@Api(value = "/web/newborn", tags = "web新生儿随访主页")
@Controller
@RequestMapping("/web/medical-information")
public class NewbornFollowUpController extends MedicalInformationShowData {

    @Autowired
    private NewbornFollowUpService newbornFollowUpService;

    /**
     * 新生儿随访主页
     *
     * @param newbornFollowUpParameter
     * @return
     */
    @ApiOperation(value = "新生儿随访主页", notes = "显示新生儿随访记录信息")
//    @PostMapping(value = "/index-1")
    public ModelAndView showIndex(@RequestBody NewbornFollowUpParameter newbornFollowUpParameter) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("obj", 1);
        return modelAndView;
    }

    /**
     * 新生儿随访主页随访日期加载
     *
     * @param identityCardParameter
     * @return
     */
    @ApiOperation(value = "新生儿随访主页随访日期", notes = "新生儿随访主页随访日期")
    @PostMapping(value = "/followUpTime")
    public ModelAndView followUpTime(@RequestBody IdentityCardParameter identityCardParameter) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("obj", 1);
        return modelAndView;
    }

    // 显示新生儿随访的日期页面
    @PostMapping(value = "/newborn-followup-date")
    public String showNewbornFollowUpDate(IdentityCardParameter parameter, Model model) {
        List<DictionaryDetailShowData> data = newbornFollowUpService.followUpTime(parameter);
        model.addAttribute("date", data);
        return "medical_information/common_date_list";
    }

    // 显示新生儿随访的数据页面
    @PostMapping(value = "/newborn-followup-data")
    public String showNewbornFollowUpData(NewbornFollowUpParameter parameter, Model model) throws IllegalAccessException {
        NewbornFollowUpShowData info = newbornFollowUpService.showIndex(parameter);
        CreateHtmlFiledUtil createHtmlUtil = new CreateHtmlFiledUtil(info, "-");
        model.addAttribute("info", createHtmlUtil.create());
        return "medical_information/newborn_followup";
    }
}
