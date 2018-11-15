package cn.com.adtech.domain.login;

import cn.com.adtech.domain.vo.FamilyMemberVo;
import cn.com.adtech.table.DataTables.Userinfo;
import lombok.Data;
import org.jooq.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 登录成功用户的信息
 * @Author chenguangxue
 * @CreateDate 2018/11/7 14:49
 */
@Data
public class LoginSuccessUserinfo {

    private String id;
    private String name;
    private String identity;
    private String phone;
    private String appId;
    private String extmpi;

    private List<FamilyMemberVo> familyMembers = new ArrayList<>();

    public static LoginSuccessUserinfo of(Record record, String appId) {
        LoginSuccessUserinfo userinfo = new LoginSuccessUserinfo();
        userinfo.id = record.get(Userinfo.id);
        userinfo.name = record.get(Userinfo.realName);
        userinfo.identity = record.get(Userinfo.identity);
        userinfo.phone = record.get(Userinfo.phone);
        userinfo.appId = appId;
        userinfo.extmpi = record.get(Userinfo.extmpi);
        return userinfo;
    }

    public static LoginSuccessUserinfo of(Record record) {
        LoginSuccessUserinfo userinfo = new LoginSuccessUserinfo();
        userinfo.id = record.get(Userinfo.id);
        userinfo.name = record.get(Userinfo.realName);
        userinfo.identity = record.get(Userinfo.identity);
        userinfo.phone = record.get(Userinfo.phone);
        userinfo.extmpi = record.get(Userinfo.extmpi);
        return userinfo;
    }
}
