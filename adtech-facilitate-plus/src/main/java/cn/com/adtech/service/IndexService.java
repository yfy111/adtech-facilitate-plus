package cn.com.adtech.service;

import cn.com.adtech.component.AuthoriseMemberProvider;
import cn.com.adtech.component.FamilyMemberProvider;
import cn.com.adtech.component.UserinfoService;
import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.family.request.FamilyMemberAddParameter;
import cn.com.adtech.domain.family.request.FamilyMemberDelParameter;
import cn.com.adtech.domain.family.request.FamilyMemberQueryParameter;
import cn.com.adtech.domain.family.response.FamilyMemberShowData;
import cn.com.adtech.domain.index.IndexShowData;
import cn.com.adtech.domain.login.LoginSuccessUserinfo;
import cn.com.adtech.domain.vo.AuthoriseMemberVo;
import cn.com.adtech.domain.vo.FamilyMemberVo;
import cn.com.adtech.enums.ResponseResultStatus;
import cn.com.adtech.stereotype.ResponseResult;
import cn.com.adtech.table.DataTables;
import cn.com.adtech.util.CommonVariable;
import cn.com.adtech.util.IdWorker;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description index页面的业务流程
 * @Author PYH
 * @CreateDate 2018/11/6 14:56
 */
@Service
@Transactional
public class IndexService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private DictionaryDetailService dictionaryDetailServce;
    @Autowired
    private UserinfoService userinfoService;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private FamilyMemberProvider familyMemberProvider;
    @Autowired
    private AuthoriseMemberProvider authoriseMemberProvider;
    @Autowired
    @Qualifier("primaryJooq")
    private DSLContext jooq;

    // 查询出主页展示时所需的信息
    public IndexShowData queryIndexShowData() {
        IndexShowData data = new IndexShowData();
        LoginSuccessUserinfo userinfo = userinfoService.loadWebLoginUserinfo();

        // 当前登录用户的个人信息
        data.setUserinfo(userinfo);

        // 当前登录用户的家庭成员列表，其中包含登录用户自身
        List<FamilyMemberVo> familyMemberVos = familyMemberProvider.findAllByUserId(userinfo.getId());
        familyMemberVos.add(0, FamilyMemberVo.ofLoginUserinfo(userinfo));
        data.setFamilyMembers(familyMemberVos);

        // 当前登录用户的授权成员列表
        List<AuthoriseMemberVo> authoriseMemberVos = authoriseMemberProvider.findAllByIdentity(userinfo.getId());
        data.setAuthoriseMembers(authoriseMemberVos);

        // 查询医疗基本信息的日期列表
        return null;
    }

    /**
     * 查询家庭成员或授权信息
     *
     * @param parameter
     * @return
     */
    public List<FamilyMemberShowData> findFamilyMember(FamilyMemberQueryParameter parameter, LoginSuccessUserinfo loginInfo) {
        String memberIden = parameter.getMemberIden();
        List<FamilyMemberShowData> vos = new ArrayList<FamilyMemberShowData>();
        String size = parameter.getPageSize();
        if (StringUtils.isEmpty(size)) {
            size = "100";
        }
        SelectQuery<Record> selectQuery = jooq.selectQuery(DataTables.familyMember);
        if (memberIden.equals(CommonVariable.MEMBER_IDEN_MEMBER)) {//家庭成员信息
            //获取当前登录用户

            FamilyMemberShowData myVo = new FamilyMemberShowData();
            myVo.setId(loginInfo.getId());
            myVo.setAppellation("本人");
            myVo.setFamilyUserId(loginInfo.getId());
            myVo.setFamilyIdcard(loginInfo.getIdentity());
            myVo.setFamilyUsername(loginInfo.getName());
            myVo.setFamilyUserphone(loginInfo.getPhone());
            myVo.setExtmpi(loginInfo.getExtmpi());
            vos.add(myVo);
        }
        Condition eq = DSL.field("MASTER_ID").eq(parameter.getUserId()).and(DSL.field("MEMBER_IDEN").eq(memberIden));
        selectQuery.addConditions(eq);
        Result<Record> fetch = selectQuery.maxRows(Integer.valueOf(size)).fetch();

        List<DictionaryDetailShowData> familyType = dictionaryDetailServce.findDictionaryDetailDataList("familyType");
        Map<String, String> map = familyType.stream()
                .collect(Collectors.toMap(DictionaryDetailShowData::getTypeValue, DictionaryDetailShowData::getTypeName));
        for (Object aResult : fetch) {
            FamilyMemberShowData vo = new FamilyMemberShowData();
            Record record = (Record) aResult;
            String id = record.getValue("ID").toString();
            String appellation = record.getValue("APPELLATION") != null ? record.getValue("APPELLATION").toString() : "";
            String familyUserId = record.getValue("FAMILY_USER_ID").toString();
            String familyIdcard = record.getValue("FAMILY_IDCARD").toString();
            String familyUsername = record.getValue("FAMILY_USERNAME").toString();
            String familyUserphone = record.getValue("FAMILY_USERPHONE").toString();
            String extmpi = record.getValue("EXTMPI").toString();
            vo.setId(id);
            vo.setAppellation(map.get(appellation));
            vo.setFamilyUserId(familyUserId);
            vo.setFamilyIdcard(familyIdcard);
            vo.setFamilyUsername(familyUsername);
            vo.setFamilyUserphone(familyUserphone);
            vo.setExtmpi(extmpi);
//            //判断成员关系在redis中是否存在，不存在从数据库中查询
//            String member = redisTemplate.opsForValue().get(CommonVariable.REDIS_KEY_HM_CYGX + appellation);
//            if (member != null && !member.isEmpty()) {
//                vo.setAppellation(member);
//            }
//            else {
//                DictionaryDetail dictionary = new DictionaryDetail();
//                dictionary.setTypeCode(CommonVariable.DICTIONARY_TYPE_CODE);
//                dictionary.setTypeName(appellation);
//                DictionaryDetail info = dictionaryDetailServce.findDictionaryDetailInfo(dictionary);
//                if (info != null) vo.setAppellation(info.getTypeValue());
//            }
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 删除成员信息：删除成员信息时同时删除授权信息
     *
     * @param parameter
     * @return
     */
    public ResponseResult deleteMember(FamilyMemberDelParameter parameter) {
//        List<String> idList = Arrays.asList(parameter.getIds().split(","));
//        for (String id : idList) {
            //根据ID查询家庭成员信息
            FamilyMemberShowData fm = selectById(parameter.getIds());
            //根据ID删除成员信息/授权信息
            DeleteConditionStep<Record> deleteConditionStep = jooq.deleteFrom(DataTables.familyMember).where("id=" + parameter.getIds());
            int execute1 = deleteConditionStep.execute();
            //删除授权信息/成员信息
            DeleteQuery<Record> delleteQuery = jooq.deleteQuery(DataTables.familyMember);
            if (parameter.getMemberIden().equals(CommonVariable.MEMBER_IDEN_MEMBER))
                parameter.setMemberIden(CommonVariable.MEMBER_IDEN_ACCREDIT);
            else parameter.setMemberIden(CommonVariable.MEMBER_IDEN_MEMBER);
            Condition eq = DSL.field("MASTER_ID").eq(fm.getFamilyUserId())
                    .and(DSL.field("FAMILY_USER_ID").eq(fm.getMasterId()))
                    .and(DSL.field("MEMBER_IDEN").eq(parameter.getMemberIden()));
            delleteQuery.addConditions(eq);
            int excute2 = delleteQuery.execute();
//        }
        return ResponseResult.result(ResponseResultStatus.SUCCESS);

    }

    /**
     * 新增家庭成员关系
     *
     * @param parameter
     * @return
     */
    public ResponseResult insertFamilyMember(FamilyMemberAddParameter parameter, LoginSuccessUserinfo myInfo) {
//            String code = redisTemplate.opsForValue().get(parameter.getUserPhone());
//            if(parameter.getPhoneCode().equals(code)) {
        //根据身份证查询家庭成员个人信息
        LoginSuccessUserinfo userInfo = userinfoService.findByIdentity(parameter.getIdentityCard());
        if (userInfo != null) {
            //获取当前登录用户信息


            //新增家庭成员
            jooq.insertInto(DataTables.familyMember)
                    .set(DSL.field("ID"), idWorker.getId())
                    .set(DSL.field("MASTER_ID"), myInfo.getId())
                    .set(DSL.field("FAMILY_USER_ID"), userInfo.getId())
                    .set(DSL.field("APPELLATION"), parameter.getAppellation())
                    .set(DSL.field("FAMILY_USERNAME"), userInfo.getName())
                    .set(DSL.field("FAMILY_IDCARD"), userInfo.getIdentity())
                    .set(DSL.field("FAMILY_USERPHONE"), userInfo.getPhone())
                    .set(DSL.field("EXTMPI"),userInfo.getExtmpi())
                    .set(DSL.field("MEMBER_IDEN"), CommonVariable.MEMBER_IDEN_MEMBER)
                    .execute();
            //新增家庭成员对应授权用户
            jooq.insertInto(DataTables.familyMember)
                    .set(DSL.field("ID"), idWorker.getId())
                    .set(DSL.field("MASTER_ID"), userInfo.getId())
                    .set(DSL.field("FAMILY_USER_ID"), myInfo.getId())
                    .set(DSL.field("FAMILY_USERNAME"), myInfo.getName())
                    .set(DSL.field("FAMILY_IDCARD"), myInfo.getIdentity())
                    .set(DSL.field("FAMILY_USERPHONE"), myInfo.getPhone())
                    .set(DSL.field("EXTMPI"),myInfo.getExtmpi())
                    .set(DSL.field("MEMBER_IDEN"), CommonVariable.MEMBER_IDEN_ACCREDIT)
                    .execute();
            return ResponseResult.result(ResponseResultStatus.SUCCESS);
        }
        else return ResponseResult.result(ResponseResultStatus.USER_NOT_EXIST);
//            }else return ResponseResult.result(ResponseResultStatus.SMS_CAPTCHA_MISMATCH);

    }

    /**
     * 根据ID查询家庭成员信息
     *
     * @param id
     * @return
     */
    public FamilyMemberShowData selectById(String id) {
        SelectQuery<Record> selectQuery = jooq.selectQuery(DataTables.familyMember);
        Condition eq = DSL.field("ID").eq(id);
        selectQuery.addConditions(eq);
        List<Record> record = selectQuery.fetch();
        FamilyMemberShowData fetch = null;
        if (record != null && record.size() > 0) {
            fetch = record.get(0).into(FamilyMemberShowData.class);
        }
        return fetch;
    }

    // 退出系统
    public ResponseResultStatus logout() {
        return ResponseResultStatus.LOGOUT_SUCCESS;
    }
}
