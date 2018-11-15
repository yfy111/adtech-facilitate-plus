package cn.com.adtech.event.handler;

import cn.com.adtech.domain.login.LoginSuccessUserinfo;
import cn.com.adtech.domain.vo.FamilyMemberVo;
import cn.com.adtech.environment.properties.FacilitateProperties;
import cn.com.adtech.event.LoginSuccessEvent;
import cn.com.adtech.table.DBFields.FamilyMember;
import cn.com.adtech.table.DataTables;
import cn.com.adtech.table.DataTables.Userinfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/7 14:40
 */
@Component
@Slf4j
public class LoginSuccessEventHandler implements ApplicationListener<LoginSuccessEvent> {

    @Autowired
    private FacilitateProperties facilitateProperties;

    @Autowired
    @Qualifier("primaryJooq")
    private DSLContext jooq;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void onApplicationEvent(LoginSuccessEvent loginSuccessEvent) {
        log.info("开始处理登录成功事件");

        JSONObject jsonObject = new JSONObject();

        // 将登录用户的信息查询出来保存到缓存中
        Record record = jooq.selectFrom(DataTables.t_userinfo).where(Userinfo.identity.eq(loginSuccessEvent.getIdentity())).fetchOne();

        LoginSuccessUserinfo userinfo = LoginSuccessUserinfo.of(record, loginSuccessEvent.getAppId());
        jsonObject.put("userinfo", userinfo);

        // 查询登录用户的家庭成员信息
        Result<?> records = jooq.selectFrom(DataTables.t_family_member).where(FamilyMember.masterId.eq(userinfo.getId())).fetch();
        List<FamilyMemberVo> familyMemberVos = records.stream().map(FamilyMemberVo::of).collect(Collectors.toList());
        jsonObject.put("familyMembers", familyMemberVos);

        String jsonString = JSON.toJSONString(jsonObject);
        redisTemplate.opsForValue().set(loginSuccessEvent.getLoginTokenKey(), jsonString, facilitateProperties.getLoginTimeOut(), TimeUnit.SECONDS);

        log.info("登录成功事件处理成功：key：{}，value：{}", loginSuccessEvent.getLoginTokenKey(), jsonString);
    }
}
