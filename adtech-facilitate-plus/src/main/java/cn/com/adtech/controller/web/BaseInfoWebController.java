package cn.com.adtech.controller.web;

import cn.com.adtech.component.UserinfoService;
import cn.com.adtech.domain.baseinfo.BaseInfoQueryParameter;
import cn.com.adtech.domain.baseinfo.BaseInfoShowData;
import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.medicalinformation.request.IdentityCardParameter;
import cn.com.adtech.service.BaseInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 基本信息相关操作的控制器
 * @Author pyh
 * @CreateDate 2018/11/6 10:18
 */
@Api(value = "/web/baseinfo", tags = "Web基本信息")
@Controller
@RequestMapping(value = "/web/medical-information")
public class BaseInfoWebController {

    private static final String VIEW_PREFIX = "html/common";

    @Autowired
    private BaseInfoService baseInfoService;

    /**
     * 基本信息页面
     *
     * @param parameter
     * @return
     */
    @PostMapping(value = "/baseInfo")
    public ModelAndView baseInfo(@RequestBody IdentityCardParameter parameter) {
        ModelAndView view = new ModelAndView();
        view.setViewName(VIEW_PREFIX + "/mainIndex");
        return view;
    }

    // 显示医疗基本信息的日期页面
    @GetMapping(value = "/basic-info-date")
    public String showMedicalInformationBasicInfoDate(@RequestParam String identity, Model model) {
        // 查询出基本信息的日期列表
        IdentityCardParameter parameter = IdentityCardParameter.of(identity);
        List<DictionaryDetailShowData> dates = baseInfoService.selDateList(parameter);
        model.addAttribute("date", dates);
        return "medical_information/common_date_list";
    }

    // 显示医疗基本信息的详情页面
    @GetMapping(value = "/basic-info-data")
    public String showMedicalInformationBasicInfoData(@RequestParam String identity, @RequestParam String date, Model model) throws Exception {
        BaseInfoQueryParameter parameter = new BaseInfoQueryParameter();
        parameter.setIdentityCard(identity);
        parameter.setCheckDate(date);

        BaseInfoShowData baseInfo = baseInfoService.selectBaseInfo(parameter);

        model.addAttribute("info", baseInfo);
        return "medical_information/basic_info";
    }
}
