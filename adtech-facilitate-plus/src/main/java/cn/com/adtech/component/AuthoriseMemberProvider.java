package cn.com.adtech.component;

import cn.com.adtech.domain.vo.AuthoriseMemberVo;
import cn.com.adtech.table.DBFields.AuthoriseMember;
import cn.com.adtech.table.DataTables;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 授权成员的查询提供者
 * @Author chenguangxue
 * @CreateDate 2018/11/8 12:44
 */
@Component
public class AuthoriseMemberProvider {

    @Autowired
    @Qualifier("primaryJooq")
    private DSLContext jooq;

    private Table table = DataTables.t_authorise_member;

    // 查询出指定用户编号对应的所有授权成员列表
    public List<AuthoriseMemberVo> findAllByIdentity(String userId) {
        return findListByIdentity(userId, 100);
    }

    // 查询出指定用户编号对应的授权成员列表，限制数量
    public List<AuthoriseMemberVo> findListByIdentity(String userId, int count) {
        return jooq.select(table.asterisk()).from(table).where(
                AuthoriseMember.memberIden.eq("A").and(AuthoriseMember.masterId.eq(userId))
        ).maxRows(count).fetchStream().map(AuthoriseMemberVo::of).collect(Collectors.toList());
    }
}
