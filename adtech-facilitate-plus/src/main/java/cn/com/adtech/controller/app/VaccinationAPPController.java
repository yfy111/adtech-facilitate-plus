package cn.com.adtech.controller.app;

import cn.com.adtech.domain.medicalinformation.request.VaccinationListParameter;
import cn.com.adtech.domain.medicalinformation.response.VaccinationBasicShowData;
import cn.com.adtech.domain.medicalinformation.response.VaccinationListShowData;
import cn.com.adtech.domain.po.Page;
import cn.com.adtech.enums.ResponseResultStatus;
import cn.com.adtech.service.VaccinationService;
import cn.com.adtech.stereotype.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 预防接种个人基本信息参数
 */
@Api(value="/app/vaccination", tags = "app预防接种页面")
@Controller
@RequestMapping("/app/vaccination")
public class VaccinationAPPController {

    @Autowired
    private VaccinationService vaccinationService;
    /**
     *  预防接种页面
     * @return
     */
    @ApiOperation(value = "预防接种页面基本信息加载", notes = "预防接种页面基本信息加载")
    @PostMapping(value = "/index")
    @ResponseBody
    public ResponseResult<VaccinationBasicShowData> showIndex(@RequestBody VaccinationListParameter vaccinationListParameter) {
        return  ResponseResult.result(ResponseResultStatus.SUCCESS).data(vaccinationService.showIndex(vaccinationListParameter));
    }


    /**
     * 出生医学证明分页列是表
     * @return
     */
    @ApiOperation(value = "预防接种列表", notes = "预防接种列表")
    @PostMapping(value = "/list")
    @ResponseBody
    public ResponseResult<Page<VaccinationListShowData>> vaccinationList(@RequestBody  VaccinationListParameter vaccinationListParameter){
        return  ResponseResult.result(ResponseResultStatus.SUCCESS).data(vaccinationService.vaccinationList(vaccinationListParameter));
    }

}
