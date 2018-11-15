package cn.com.adtech.controller.web;

import cn.com.adtech.component.UserinfoService;
import cn.com.adtech.domain.family.request.FamilyMemberAddParameter;
import cn.com.adtech.domain.login.LoginSuccessUserinfo;
import cn.com.adtech.service.IndexService;
import cn.com.adtech.stereotype.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 家庭成员的控制器
 * @Author chenguangxue
 * @CreateDate 2018/11/11 16:13
 */
@Controller
@RequestMapping(value = "/web/family-member")
public class FamilyMemberController {

    @Autowired
    private UserinfoService userinfoService;

    @GetMapping(value = "/add")
    public String showAdd(Model model) {
        return "index/family_member_add";
    }

    @Autowired
    private IndexService indexService;

    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseResult doAdd(FamilyMemberAddParameter parameter) {
        LoginSuccessUserinfo userinfo = userinfoService.loadWebLoginUserinfo();
        return indexService.insertFamilyMember(parameter, userinfo);
    }
}
