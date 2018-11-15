package cn.com.adtech.controller.app;

import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import cn.com.adtech.domain.medicalinformation.request.IdentityCardParameter;
import cn.com.adtech.domain.medicalinformation.request.NewbornFollowUpParameter;
import cn.com.adtech.domain.medicalinformation.response.HypertensionFollowUpShowData;
import cn.com.adtech.domain.medicalinformation.response.NewbornFollowUpShowData;
import cn.com.adtech.enums.ResponseResultStatus;
import cn.com.adtech.service.NewbornFollowUpService;
import cn.com.adtech.stereotype.ResponseResult;
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
 * @Description 新生儿随访记录
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:55
 */
@Api(value="/app/newborn", tags = "app新生儿随访主页")
@Controller
@RequestMapping("/app/newborn")
public class NewbornFollowUpAPPController extends MedicalInformationShowData {


    @Autowired
    private NewbornFollowUpService newbornFollowUpService;
    /**
     * 新生儿随访主页
     * @param newbornFollowUpParameter
     * @return
     */
    @ApiOperation(value="新生儿随访主页",notes = "显示新生儿随访记录信息")
    @PostMapping(value = "/index")
    @ResponseBody
    public ResponseResult<NewbornFollowUpShowData> showIndex(@RequestBody NewbornFollowUpParameter newbornFollowUpParameter) {
        return  ResponseResult.result(ResponseResultStatus.SUCCESS).data(newbornFollowUpService.showIndex(newbornFollowUpParameter));
    }

    /**
     * 新生儿随访主页随访日期加载
     * @param identityCardParameter
     * @return
     */
    @ApiOperation(value="新生儿随访主页随访日期",notes = "新生儿随访主页随访日期")
    @PostMapping(value = "/followUpTime")
    @ResponseBody
    public ResponseResult<DictionaryDetailShowData> followUpTime(@RequestBody IdentityCardParameter identityCardParameter) {
        return  ResponseResult.result(ResponseResultStatus.SUCCESS).data(newbornFollowUpService.followUpTime(identityCardParameter));
    }
}
