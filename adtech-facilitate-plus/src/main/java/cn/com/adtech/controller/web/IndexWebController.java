package cn.com.adtech.controller.web;

import cn.com.adtech.component.UserinfoService;
import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.family.request.FamilyMemberAddParameter;
import cn.com.adtech.domain.family.request.FamilyMemberDelParameter;
import cn.com.adtech.domain.family.request.FamilyMemberQueryParameter;
import cn.com.adtech.domain.family.response.FamilyMemberShowData;
import cn.com.adtech.domain.login.LoginSuccessUserinfo;
import cn.com.adtech.domain.password.change.ChangePasswordParameter;
import cn.com.adtech.enums.ResponseResultStatus;
import cn.com.adtech.service.DictionaryDetailService;
import cn.com.adtech.service.IndexService;
import cn.com.adtech.service.PasswordService;
import cn.com.adtech.stereotype.ResponseResult;
import cn.com.adtech.stereotype.ResultStatus;
import cn.com.adtech.util.CommonVariable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Description 主页上相关操作的控制器
 * @Author PYH
 * @CreateDate 2018/11/6 14:49
 */
@Api(value = "/web/index", tags = "Web首页")
@Controller
@RequestMapping(value = "/web/index")
public class IndexWebController {

    private static final String VIEW_PREFIX = "html/common";
    @Autowired
    private IndexService indexService;
    @Autowired
    private UserinfoService userinfoService;
    @Autowired
    private DictionaryDetailService dictionaryDetailService;

    /**
     * 主页面
     *
     * @param parameter
     * @return
     */
    @GetMapping(value = "/mainIndex")
    public ModelAndView mainIndex(FamilyMemberQueryParameter parameter) {
        ModelAndView view = new ModelAndView();
        //获取当前登录用户
        LoginSuccessUserinfo loginInfo = userinfoService.loadWebLoginUserinfo();
        view.addObject("loginInfo", loginInfo);

        // 默认情况下，选中的用户为当前登录用户，称谓是本人
        view.addObject("selectedUserIdentity", loginInfo.getIdentity());
        view.addObject("selectedUserIdAppellation", "本人");
        view.addObject("selectedUserMPI", loginInfo.getExtmpi());

        view.setViewName("index");
        return view;
    }

    /**
     * 更多家庭成员
     *
     * @param parameter
     * @return
     */
    @GetMapping(value = "/moreMember")
    public ModelAndView moreMember(FamilyMemberQueryParameter parameter) {
        ModelAndView view = new ModelAndView();
        LoginSuccessUserinfo loginInfo = userinfoService.loadWebLoginUserinfo();
        List<FamilyMemberShowData> list = indexService.findFamilyMember(parameter, loginInfo);
        view.addObject("memberList", list);
        view.setViewName(VIEW_PREFIX + "/moreMember");
        return view;
    }

    /**
     * 更多授权
     *
     * @param parameter
     * @return
     */
    @GetMapping(value = "/moreAccredit")
    public ModelAndView moreAccredit(FamilyMemberQueryParameter parameter) {
        ModelAndView view = new ModelAndView();
        LoginSuccessUserinfo loginInfo = userinfoService.loadWebLoginUserinfo();
        List<FamilyMemberShowData> list = indexService.findFamilyMember(parameter, loginInfo);
        view.addObject("accreditList", list);
        view.setViewName(VIEW_PREFIX + "/moreAccredit");
        return view;
    }

    /**
     * 查询家庭成员或授权信息
     *
     * @param parameter
     * @return
     */
    @ApiOperation(value = "Web查询家庭成员或授权信息")
    @GetMapping(value = "/findFamilyMember")
    @ResponseBody
    public ResponseResult<FamilyMemberShowData> findFamilyMember(FamilyMemberQueryParameter parameter) {
        LoginSuccessUserinfo loginInfo = userinfoService.loadWebLoginUserinfo();
        List<FamilyMemberShowData> list = indexService.findFamilyMember(parameter, loginInfo);
        return ResponseResult.result(ResultStatus.success()).data(list);
    }

    // 切换当前选中的用户，不是变更登录用户
    @PostMapping(value = "/change-user")
    @ResponseBody
    public ResponseResult<LoginSuccessUserinfo> getChangeUserinfo(@RequestParam String identity) {
        LoginSuccessUserinfo userinfo = userinfoService.findByIdentity(identity);
        return ResponseResult.mockSuccess().data(userinfo);
    }

    // 显示家庭成员的简表
    @GetMapping(value = "/family-member-simple-list")

    public String showFamilyMemberSimpleList(@RequestParam(required = false) Integer pageSize, Model model) {
        FamilyMemberQueryParameter parameter = new FamilyMemberQueryParameter();
        if (pageSize == null) {
            pageSize = 1;
        }
        parameter.setPageSize(Integer.toString(pageSize));
        parameter.setMemberIden("F");
        parameter.setUserId(userinfoService.loadWebLoginUserinfo().getId());
        LoginSuccessUserinfo loginInfo = userinfoService.loadWebLoginUserinfo();
        List<FamilyMemberShowData> list = indexService.findFamilyMember(parameter, loginInfo);
        model.addAttribute("list", list);

        return "index/family_member_simple_list";
    }

    // 显示家庭成员的全表
    @PostMapping(value = "/family-member-full-list")
    public String showFamilyMemberFullList(Model model) {
        FamilyMemberQueryParameter parameter = new FamilyMemberQueryParameter();
        parameter.setMemberIden("F");
        parameter.setUserId(userinfoService.loadWebLoginUserinfo().getId());
        LoginSuccessUserinfo loginInfo = userinfoService.loadWebLoginUserinfo();
        List<FamilyMemberShowData> list = indexService.findFamilyMember(parameter, loginInfo);
        model.addAttribute("list", list);

        return "index/family_member_full_list";
    }

    // 显示授权成员的简表
    @GetMapping(value = "/authorise-member-simple-list")
    public String showAuthoriseMemberSimpleList(Model model) {
        FamilyMemberQueryParameter parameter = new FamilyMemberQueryParameter();
        parameter.setPageSize(Integer.toString(3));
        parameter.setMemberIden("A");
        parameter.setUserId(userinfoService.loadWebLoginUserinfo().getId());
        LoginSuccessUserinfo loginInfo = userinfoService.loadWebLoginUserinfo();
        List<FamilyMemberShowData> members = indexService.findFamilyMember(parameter, loginInfo);
        model.addAttribute("list", members);

        return "index/authorise_member_simple_list";
    }

    /**
     * 根据类型查询数据字典相关下拉框信息
     *
     * @return
     */
    @ApiOperation(value = "Web加载成员关系")
    @PostMapping(value = "/selectDictInfo")
    @ResponseBody
    public ResponseResult<DictionaryDetailShowData> selectDictInfo() {
        List<DictionaryDetailShowData> list = dictionaryDetailService.findDictionaryDetailDataList(CommonVariable.DICTIONARY_TYPE_CODE);
        return ResponseResult.result(ResultStatus.success()).data(list);
    }

    @PostMapping(value = "/change-userinfo")
    @ResponseBody
    public ResponseResult changeSelectedUser(@RequestParam String userId) {
        LoginSuccessUserinfo userinfo = userinfoService.findByUserId(userId);

        return ResponseResult.result(ResponseResultStatus.CHANGE_USER_SUCCESS).data(userinfo);
    }

    // 删除家庭成员
    @PostMapping(value = "/delete-family-member")
    @ResponseBody
    public ResponseResult deleteFamilyMember(FamilyMemberDelParameter parameter) {
        return indexService.deleteMember(parameter);
    }

    // 显示修改用户密码页面
    @GetMapping(value = "/change-password")
    public String showChangePassword() {
        return "index/change_password";
    }

    @Autowired
    private PasswordService passwordService;

    // 执行修改用户密码的操作
    @PostMapping(value = "/change-password")
    @ResponseBody
    public ResponseResult doChangePassword(ChangePasswordParameter parameter) {
        return passwordService.doChangePassword(parameter,userinfoService.loadWebLoginUserinfo());
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
    public ResponseResult verification(@RequestBody FamilyMemberAddParameter parameter) {
        return ResponseResult.result(ResultStatus.success());
    }

    // 退出系统
    @PostMapping(value = "/logout")
    @ResponseBody
    public ResponseResult logout() {
        return ResponseResult.result(indexService.logout());
    }
}
