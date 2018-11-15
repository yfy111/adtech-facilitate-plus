package cn.com.adtech.controller.web;

import cn.com.adtech.domain.medicalinformation.request.VaccinationListParameter;
import cn.com.adtech.domain.medicalinformation.response.VaccinationBasicShowData;
import cn.com.adtech.domain.medicalinformation.response.VaccinationListShowData;
import cn.com.adtech.domain.po.Page;
import cn.com.adtech.enums.ResponseResultStatus;
import cn.com.adtech.service.VaccinationService;
import cn.com.adtech.stereotype.ResponseResult;
import cn.com.adtech.util.ViewObjectUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Description 预防接种个人基本信息参数
 * @CreateDate 2018/11/2 16:54
 */
@Api(value = "/web/vaccination", tags = "web预防接种页面")
@Controller
@RequestMapping("/web/medical-information")
public class VaccinationController {

    @Autowired
    private VaccinationService vaccinationService;

    /**
     * 预防接种页面
     *
     * @param vaccinationListParameter
     * @return
     */
    @ApiOperation(value = "预防接种页面", notes = "预防接种页面")
    @PostMapping(value = "/vaccination-index")
    @ResponseBody
    public ModelAndView showIndex(@RequestBody VaccinationListParameter vaccinationListParameter) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("vaccination/index_table");
        modelAndView.addObject("base", vaccinationService.showIndex(vaccinationListParameter));
        return modelAndView;
    }

    // 显示预防接种的个人信息页面
    @PostMapping(value = "/vaccination-userinfo")
    public String showVaccinationUserinfoData(@RequestParam String identity, Model model) {
        VaccinationListParameter parameter = new VaccinationListParameter();
        parameter.setIdentityCard(identity);

        VaccinationBasicShowData data = vaccinationService.showIndex(parameter);
        // 转换为list数据
        List<JSONObject> jsonObjects = ViewObjectUtils.convert(data);

        model.addAttribute("userinfo", jsonObjects);
        return "medical_information/vaccination_userinfo";
    }

    // 显示预防接种的分页数据
    @PostMapping(value = "/vaccination-list")
    public String showVaccinationListData(VaccinationListParameter parameter, Model model) {
        Page<VaccinationListShowData> list = vaccinationService.vaccinationList(parameter);
        model.addAttribute("page", list);
        return "medical_information/vaccination_list";
    }

    /**
     * 出生医学证明分页列表
     *
     * @return
     */
    @ApiOperation(value = "预防接种列表", notes = "预防接种列表")
    @PostMapping(value = "/list")
    @ResponseBody
    public ResponseResult<Page<VaccinationListShowData>> vaccinationList(@RequestBody VaccinationListParameter vaccinationListParameter) {
        return ResponseResult.result(ResponseResultStatus.SUCCESS).data(vaccinationService.vaccinationList(vaccinationListParameter));
    }
}
