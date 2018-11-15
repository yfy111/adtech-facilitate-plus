package cn.com.adtech.controller.app;

import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import cn.com.adtech.domain.medicalinformation.request.DiabetesFollowUpParameter;
import cn.com.adtech.domain.medicalinformation.request.IdentityCardParameter;
import cn.com.adtech.domain.medicalinformation.request.NewbornFollowUpParameter;
import cn.com.adtech.domain.medicalinformation.response.DiabetesFollowUpShowData;
import cn.com.adtech.domain.medicalinformation.response.NewbornFollowUpShowData;
import cn.com.adtech.enums.ResponseResultStatus;
import cn.com.adtech.service.DiabetesFollowUpService;
import cn.com.adtech.service.DictionaryDetailService;
import cn.com.adtech.stereotype.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 糖尿病随访记录
 * @CreateDate 2018/11/2 16:55
 */
@Api(value="/app/diabetes", tags = "app糖尿病随访主页")
@Controller
@RequestMapping("/app/diabetes")
public class DiabetesFollowUpAPPController extends MedicalInformationShowData {

    @Autowired
    private DiabetesFollowUpService diabetesFollowUpService;
    /**
     * 糖尿病随访主页
     * @return
     */
    @ApiOperation(value="糖尿病随访主页",notes = "显示糖尿病随访记录信息")
    @PostMapping(value = "/index")
    @ResponseBody
    public ResponseResult<DiabetesFollowUpShowData> showIndex(@RequestBody DiabetesFollowUpParameter diabetesFollowUpParameter) {
        return  ResponseResult.result(ResponseResultStatus.SUCCESS).data(diabetesFollowUpService.showIndex(diabetesFollowUpParameter));
    }

    /**
     * 糖尿病随访主页随访日期加载
     * @param identityCardParameter
     * @return
     */
    @ApiOperation(value="糖尿病随访主页随访日期",notes = "糖尿病随访主页随访日期")
    @PostMapping(value = "/followUpTime")
    @ResponseBody
    public ResponseResult<DictionaryDetailShowData> followUpTime(@RequestBody IdentityCardParameter identityCardParameter) {
        return  ResponseResult.result(ResponseResultStatus.SUCCESS).data(diabetesFollowUpService.followUpTime(identityCardParameter));
    }
}
