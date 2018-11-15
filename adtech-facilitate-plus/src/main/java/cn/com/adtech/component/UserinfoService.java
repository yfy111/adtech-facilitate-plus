package cn.com.adtech.component;

import cn.com.adtech.domain.login.LoginSuccessUserinfo;
import cn.com.adtech.domain.vo.FamilyMemberVo;
import cn.com.adtech.table.DataTables;
import cn.com.adtech.table.DataTables.Userinfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Description 提供用户信息的操作服务操作
 * @Author chenguangxue
 * @CreateDate 2018/11/7 14:47
 */
@Component
public class UserinfoService {

    @Autowired
    @Qualifier("primaryJooq")
    private DSLContext jooq;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CacheKey cacheKey;
    @Autowired
    private StringRedisTemplate redisTemplate;

    // 根据登录请求的header来自动获取当前登录用户的完整信息
    public LoginSuccessUserinfo loadLoginUserinfo() {
        String loginType = request.getHeader("loginType");

        if (!StringUtils.isEmpty(loginType) && "H5".equalsIgnoreCase(loginType)) {
            return loadH5LoginUserinfo();
        }
        else {
            return loadWebLoginUserinfo();
        }
    }

    // 获取web端的登录用户信息
    public LoginSuccessUserinfo loadWebLoginUserinfo() {
        String key = cacheKey.loginToken.apply(request.getSession().getId());
        String jsonString = redisTemplate.opsForValue().get(key);
        return jsonToObject(jsonString);
    }

    // 获取h5端的登录用户信息
    public LoginSuccessUserinfo loadH5LoginUserinfo() {
        String loginToken = request.getHeader("token");
        String jsonString = redisTemplate.opsForValue().get(loginToken);
        return jsonToObject(jsonString);
    }

    // 将redis中的jsonString转换为java中的对象
    private LoginSuccessUserinfo jsonToObject(String jsonString) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        JSONObject userinfoJsonObject = jsonObject.getJSONObject("userinfo");
        LoginSuccessUserinfo userinfo = userinfoJsonObject.toJavaObject(LoginSuccessUserinfo.class);

        JSONArray familyMembers = jsonObject.getJSONArray("familyMembers");
        List<FamilyMemberVo> memberVos = IntStream.range(0, familyMembers.size())
                .mapToObj(index -> familyMembers.getJSONObject(index).toJavaObject(FamilyMemberVo.class))
                .collect(Collectors.toList());
        userinfo.getFamilyMembers().addAll(memberVos);

        return userinfo;
    }

    // 根据用户的id查询用户的基本信息
    public LoginSuccessUserinfo findByUserId(String userId) {
        Result<Record> records = jooq.select().from(DataTables.t_userinfo).where(Userinfo.id.eq(userId)).fetch();
        if (records.isEmpty()) {
            return null;
        }
        else {
            return LoginSuccessUserinfo.of(records.get(0));
        }
    }

    // 根据用户的身份证查询用户的基本信息
    public LoginSuccessUserinfo findByIdentity(String identity) {
        Result<Record> records = jooq.select().from(DataTables.t_userinfo).where(Userinfo.identity.eq(identity)).fetch();

        if (records.isEmpty()) {
            return null;
        }
        else {
            return LoginSuccessUserinfo.of(records.get(0));
        }
    }
}
