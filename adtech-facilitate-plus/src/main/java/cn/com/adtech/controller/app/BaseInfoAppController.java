package cn.com.adtech.controller.app;

import cn.com.adtech.domain.baseinfo.BaseInfoQueryParameter;
import cn.com.adtech.domain.baseinfo.BaseInfoShowData;
import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.family.request.FamilyMemberAddParameter;
import cn.com.adtech.domain.medicalinformation.request.IdentityCardParameter;
import cn.com.adtech.enums.ResponseResultStatus;
import cn.com.adtech.service.BaseInfoService;
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

import java.util.List;

/**
 * @Description 基本信息相关操作的控制器
 * @Author PYH
 * @CreateDate 2018/11/6 14:13
 */
@Api(value = "/app/baseinfo", tags = "H5基本信息")
@Controller
@RequestMapping(value = "/app/baseinfo")
public class BaseInfoAppController {

    @Autowired
    private BaseInfoService baseInfoService;

    /**
     * 查询基本信息
     *
     * @param parameter
     * @return
     */
    @ApiOperation(value = "H5查询基本信息")
    @PostMapping(value = "/findBaseInfoList")
    @ResponseBody
    public ResponseResult<BaseInfoShowData> findBaseInfoList(@RequestBody BaseInfoQueryParameter parameter) throws Exception {
        BaseInfoShowData baseinfo = baseInfoService.selectBaseInfo(parameter);
        return ResponseResult.result(ResultStatus.success()).data(baseinfo);
    }

    /**
     * 查询基本信息时间下拉框
     *
     * @param parameter
     * @return
     */
    @ApiOperation(value = "H5基本信息时间下拉框")
    @PostMapping(value = "/findDataList")
    @ResponseBody
    public ResponseResult<DictionaryDetailShowData> findDataList(@RequestBody IdentityCardParameter parameter) {
        List<DictionaryDetailShowData> list = baseInfoService.selDateList(parameter);
        return ResponseResult.result(ResultStatus.success()).data(list);
    }

    /**
     * 验证用户是否存在于基本信息表中
     *
     * @param parameter
     * @return
     */
    @ApiOperation(value = "H5验证用户是否存在于基本信息表中")
    @PostMapping(value = "/verification")
    @ResponseBody
    public ResponseResult<BaseInfoShowData> verification(@RequestBody FamilyMemberAddParameter parameter) {
        BaseInfoShowData baseinfo = baseInfoService.verification(parameter);
        if (baseinfo != null) return ResponseResult.result(ResponseResultStatus.SUCCESS);
        else return ResponseResult.result(ResponseResultStatus.USER_NOT_EXIST);

    }
}
