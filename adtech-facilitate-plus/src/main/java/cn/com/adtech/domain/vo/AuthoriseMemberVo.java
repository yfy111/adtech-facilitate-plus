package cn.com.adtech.domain.vo;

import cn.com.adtech.table.DBFields.AuthoriseMember;
import lombok.Data;
import org.jooq.Record;

/**
 * @Description 授权成员vo
 * @Author chenguangxue
 * @CreateDate 2018/11/8 10:52
 */
@Data
public class AuthoriseMemberVo {

    private String id;
    private String appellation;
    private String name;
    private String phone;
    private String identity;

    // 从record记录生成一个vo对象
    public static AuthoriseMemberVo of(Record record) {
        AuthoriseMemberVo vo = new AuthoriseMemberVo();
        vo.id = record.get(AuthoriseMember.id);
        vo.appellation = record.get(AuthoriseMember.appellation);
        vo.name = record.get(AuthoriseMember.authoriseUsername);
        vo.phone = record.get(AuthoriseMember.authoriseUserphone);
        vo.identity = record.get(AuthoriseMember.authoriseIdcard);
        return vo;
    }
}
