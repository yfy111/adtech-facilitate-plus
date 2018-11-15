package cn.com.adtech.controller.app;

import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import cn.com.adtech.domain.medicalinformation.request.HypertensionFollowUpParameter;
import cn.com.adtech.domain.medicalinformation.request.IdentityCardParameter;
import cn.com.adtech.domain.medicalinformation.response.HypertensionFollowUpShowData;
import cn.com.adtech.enums.ResponseResultStatus;
import cn.com.adtech.service.HypertensionFollowUpService;
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
 * @Description 高血压随访记录
 * @CreateDate 2018/11/2 16:55
 */
@Api(value="/app/hypertension", tags = "app高血压随访主页")
@Controller
@RequestMapping("/app/hypertension")
public class HypertensionFollowUpAPPController extends MedicalInformationShowData {

    @Autowired
    private HypertensionFollowUpService hypertensionFollowUpService;
    /**
     * 高血压随访主页
     * @return
     */
    @ApiOperation(value="高血压随访主页",notes = "显示高血压随访记录信息")
    @PostMapping(value = "/index")
    @ResponseBody
    public ResponseResult<HypertensionFollowUpShowData> showIndex(@RequestBody HypertensionFollowUpParameter hypertensionFollowUpParameter) {
        return  ResponseResult.result(ResponseResultStatus.SUCCESS).data(hypertensionFollowUpService.showIndex(hypertensionFollowUpParameter));
    }

    /**
     * 高血压随访主页随访日期加载
     * @param identityCardParameter
     * @return
     */
    @ApiOperation(value="高血压随访主页随访日期",notes = "高血压随访主页随访日期")
    @PostMapping(value = "/followUpTime")
    @ResponseBody
    public ResponseResult<DictionaryDetailShowData> followUpTime(@RequestBody IdentityCardParameter identityCardParameter) {
        return  ResponseResult.result(ResponseResultStatus.SUCCESS).data(hypertensionFollowUpService.followUpTime(identityCardParameter));
    }
}
