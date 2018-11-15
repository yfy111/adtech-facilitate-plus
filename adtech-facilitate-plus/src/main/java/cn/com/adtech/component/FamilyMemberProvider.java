package cn.com.adtech.component;

import cn.com.adtech.domain.vo.FamilyMemberVo;
import cn.com.adtech.table.DBFields.FamilyMember;
import cn.com.adtech.table.DataTables;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 查询家庭成员的提供者
 * @Author chenguangxue
 * @CreateDate 2018/11/8 10:58
 */
@Component
public class FamilyMemberProvider {

    @Autowired
    @Qualifier("primaryJooq")
    private DSLContext jooq;

    private Table table = DataTables.t_family_member;

    // 根据身份证查询指定全部的家庭成员
    public List<FamilyMemberVo> findAllByUserId(String userId) {
        return findListByUserId(userId, 100);
    }

    // 查询指定条数的家庭成员
    public List<FamilyMemberVo> findListByUserId(String userId, int count) {
        return jooq.select(table.asterisk()).from(table).where(
                FamilyMember.memberIden.eq("F").and(FamilyMember.familyUserId.eq(userId))
        ).maxRows(count).fetchStream().map(FamilyMemberVo::of).collect(Collectors.toList());
    }
}
