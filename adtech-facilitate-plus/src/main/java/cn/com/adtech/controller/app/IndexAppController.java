package cn.com.adtech.controller.app;

import cn.com.adtech.component.UserinfoService;
import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.family.request.FamilyMemberAddParameter;
import cn.com.adtech.domain.family.request.FamilyMemberDelParameter;
import cn.com.adtech.domain.family.request.FamilyMemberQueryParameter;
import cn.com.adtech.domain.family.response.FamilyMemberShowData;
import cn.com.adtech.domain.login.LoginSuccessUserinfo;
import cn.com.adtech.service.DictionaryDetailService;
import cn.com.adtech.service.IndexService;
import cn.com.adtech.stereotype.ResponseResult;
import cn.com.adtech.stereotype.ResultStatus;
import cn.com.adtech.util.CommonVariable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 主页上相关操作的控制器
 * @Author PYH
 * @CreateDate 2018/11/6 14:49
 */
@Api(value = "/app/index", tags = "H5首页")
@Controller
@RequestMapping(value = "/app/index")
public class IndexAppController {
    @Autowired
    private UserinfoService userinfoService;
    @Autowired
    private IndexService indexService;
    @Autowired
    private DictionaryDetailService dictionaryDetailService;
    /**
     * 查询家庭成员或授权信息
     * @param parameter
     * @return
     */
    @ApiOperation(value="H5查询家庭成员或授权信息")
    @PostMapping(value = "/findFamilyMember")
    @ResponseBody
    public ResponseResult<FamilyMemberShowData> findFamilyMember(@RequestBody FamilyMemberQueryParameter parameter) {

        LoginSuccessUserinfo loginInfo = userinfoService.loadH5LoginUserinfo();
        List<FamilyMemberShowData> list=indexService.findFamilyMember(parameter,loginInfo);
        return  ResponseResult.result(ResultStatus.success()).data(list);
    }

    /**
     * 删除成员关系/取消授权
     * @param parameter
     * @return
     */
    @ApiOperation(value="H5删除成员关系/取消授权")
    @PostMapping(value = "/deleteFamilyMember")
    @ResponseBody
    public ResponseResult deleteFamilyMember(@RequestBody FamilyMemberDelParameter parameter){
        indexService.deleteMember(parameter);
        return ResponseResult.result(ResultStatus.success());

    }
    /**
     * 新增成员
     * @param parameter
     * @return
     */
    @ApiOperation(value="H5新增成员")
    @PostMapping(value = "/addMember")
    @ResponseBody
    public ResponseResult addMember(@RequestBody FamilyMemberAddParameter parameter){
        LoginSuccessUserinfo  user = userinfoService.loadH5LoginUserinfo();
        indexService.insertFamilyMember(parameter,user);
        return ResponseResult.result(ResultStatus.success());

    }

    /**
     * 根据类型查询数据字典相关下拉框信息
     * @return
     */
    @ApiOperation(value="H5加载成员关系")
    @PostMapping(value = "/selectDictInfo")
    @ResponseBody
    public ResponseResult<DictionaryDetailShowData> selectDictInfo() {
        List<DictionaryDetailShowData> list=dictionaryDetailService.findDictionaryDetailDataList(CommonVariable.DICTIONARY_TYPE_CODE);
        return  ResponseResult.result(ResultStatus.success()).data(list);
    }


}
