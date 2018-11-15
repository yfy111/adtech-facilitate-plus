package cn.com.adtech.domain.index;

import cn.com.adtech.domain.login.LoginSuccessUserinfo;
import cn.com.adtech.domain.vo.AuthoriseMemberVo;
import cn.com.adtech.domain.vo.FamilyMemberVo;
import cn.com.adtech.domain.vo.MedicalBasicInfoVo;
import lombok.Data;

import java.util.List;

/**
 * @Description 主页上需要显示的数据
 * @Author chenguangxue
 * @CreateDate 2018/11/2 14:58
 */
@Data
public class IndexShowData {

    // 当前登录用户的个人信息
    private LoginSuccessUserinfo userinfo;

    // 家庭成员列表
    private List<FamilyMemberVo> familyMembers;

    // 授权成员列表
    private List<AuthoriseMemberVo> authoriseMembers;

    // 当前登录用户的基本信息的日期列表
    private List<String> basicInfoDates;

    // 当前登录用户的基本信息
    private MedicalBasicInfoVo basicInfo;
}
