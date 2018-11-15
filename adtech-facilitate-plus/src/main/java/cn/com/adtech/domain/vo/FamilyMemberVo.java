package cn.com.adtech.domain.vo;

import cn.com.adtech.domain.login.LoginSuccessUserinfo;
import cn.com.adtech.table.DBFields.FamilyMember;
import lombok.Data;
import org.jooq.Record;

/**
 * @Description 家庭成员的vo
 * @Author chenguangxue
 * @CreateDate 2018/11/8 10:50
 */
@Data
public class FamilyMemberVo {

    private String id;
    private String appellation;
    private String name;
    private String phone;
    private String identity;
    private String extmpi;

    public static FamilyMemberVo of(Record record) {
        FamilyMemberVo vo = new FamilyMemberVo();
        vo.id = record.get(FamilyMember.id);
        vo.appellation = record.get(FamilyMember.appellation);
        vo.name = record.get(FamilyMember.familyUsername);
        vo.phone = record.get(FamilyMember.familyUserphone);
        vo.identity = record.get(FamilyMember.familyIdcard);
        vo.setExtmpi( record.get(FamilyMember.extmpi));
        return vo;
    }

    // 将登录用户转换为家庭成员
    public static FamilyMemberVo ofLoginUserinfo(LoginSuccessUserinfo userinfo) {
        FamilyMemberVo vo = new FamilyMemberVo();
        vo.id = userinfo.getId();
        vo.identity = userinfo.getIdentity();
        vo.phone = userinfo.getPhone();
        vo.appellation = "本人";
        return vo;
    }
}
