package cn.com.adtech.service;

import cn.com.adtech.domain.baseinfo.BaseInfoQueryParameter;
import cn.com.adtech.domain.baseinfo.BaseInfoShowData;
import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.family.request.FamilyMemberAddParameter;
import cn.com.adtech.domain.medicalinformation.request.IdentityCardParameter;
import cn.com.adtech.table.DataTables;
import cn.com.adtech.util.CommonVariable;
import cn.com.adtech.util.GetMpiUtil;
import cn.com.adtech.util.SetNullToStringUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 基本信息页面的业务流程
 * @Author PYH
 * @CreateDate 2018/11/6 14:56
 */
@Service
@Transactional
@Slf4j
public class BaseInfoService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    @Qualifier("secondJooq")
    private DSLContext jooq2;

    @Autowired
    private SetNullToStringUtil setNullToStringUtil;

    @Autowired
    private GetMpiUtil getMpiUtil;

    /**
     * 查询时间
     *
     * @param parameter
     * @return
     */
    public List<DictionaryDetailShowData> selDateList(IdentityCardParameter parameter) {
        Field<Object> field1 = DSL.field("EXTSID").as("typeValue");
        Field<Object> field2 = DSL.field("EXHDSD0001025").as("typeName");
        Condition eq = DSL.field("EXTMPI").in(getMpiUtil.getMpiByList(parameter.getIdentityCard()));
        List<DictionaryDetailShowData> list = jooq2.select(field1, field2)
                .from(DataTables.T_HR_JKDA).where(eq).orderBy(DSL.field("EXHDSD0001025").desc()).fetch()
                .into(DictionaryDetailShowData.class);
        List<DictionaryDetailShowData> list1 = new ArrayList<>();
        for(DictionaryDetailShowData base : list){
            if(StringUtils.isEmpty(base.getTypeName())||StringUtils.isEmpty(base.getTypeValue())) continue;
            list1.add(base);

        }
        return list1;
    }

    /**
     * 查询基本信息
     *
     * @param parameter
     * @return
     * @throws Exception
     */
    public BaseInfoShowData selectBaseInfo(BaseInfoQueryParameter parameter) throws Exception {
        log.info(JSON.toJSONString(parameter));

        SelectQuery<Record> selectQuery = jooq2.selectQuery(DataTables.T_HR_JKDA);
        Condition eq = DSL.field("EXTSID").eq(parameter.getCheckDate());
        selectQuery.addConditions(eq);
        List<Record> record = selectQuery.fetch();

        log.info(record.toString());

        BaseInfoShowData fetch = null;
        if (record != null && record.size() > 0) {
            fetch = record.get(0).into(BaseInfoShowData.class);
            String extSid = fetch.getExtsid();
            //药物过敏源
            String gmsName = selectGms(extSid);
            //家族性疾病
            String jbsName = selectJzjbs(extSid);
            //残疾情况
            String cjsName = selectCjqk(extSid);
            fetch.setHdsd0001020(gmsName);
            fetch.setExhdsd0001003(jbsName);
            fetch.setHdsd0001036(cjsName);
        }
        setNullToStringUtil.setValue(fetch,"-");
        return fetch;
    }

    /**
     * 查询药物过敏源
     *
     * @param extSid
     * @return
     * @throws Exception
     */
    public String selectGms(String extSid) {
        String gmsName = "";
        SelectQuery<Record> selectQuery = jooq2.selectQuery(DataTables.T_HR_JKDA_GMS);
        Condition eq = DSL.field("EXTSPID").eq(extSid);
        selectQuery.addConditions(eq);
        Result<Record> fetch = selectQuery.fetch();
        for (Record record : fetch) {
            Object hdsd0001020 = record.getValue("HDSD0001020");
            if (StringUtils.isEmpty(hdsd0001020)) {
                hdsd0001020 = "";
            }
            //判断在redis中是否存在，不存在从数据库中查询
            String name = redisTemplate.opsForValue().get(CommonVariable.REDIS_KEY_HM_YWGMY + hdsd0001020);
            if (name != null && !name.isEmpty()) {
                gmsName += name;
            }
            else {

            }
        }
        return gmsName;
    }

    /**
     * 查询家族性疾病
     *
     * @param extSid
     * @return
     * @throws Exception
     */
    public String selectJzjbs(String extSid) {
        String jbsName = "";
        SelectQuery<Record> selectQuery = jooq2.selectQuery(DataTables.T_HR_JKDA_JZJBS);
        Condition eq = DSL.field("EXTSPID").eq(extSid);
        selectQuery.addConditions(eq);
        Result<Record> fetch = selectQuery.fetch();
        for (Record record : fetch) {
            Object hdsd0001033 = record.getValue("HDSD0001033");
            if (StringUtils.isEmpty(hdsd0001033)) {
                hdsd0001033 = "";
            }

            //判断在redis中是否存在，不存在从数据库中查询
            String name = redisTemplate.opsForValue().get(CommonVariable.REDIS_KEY_HM_YWGMY + hdsd0001033);
            if (name != null && !name.isEmpty()) {
                jbsName += name;
            }
            else {

            }
        }
        return jbsName;
    }

    /**
     * 查询残疾情况代码
     *
     * @param extSid
     * @return
     * @throws Exception
     */
    public String selectCjqk(String extSid) {
        String cjsName = "";
        SelectQuery<Record> selectQuery = jooq2.selectQuery(DataTables.T_HR_JKDA_CJS);
        Condition eq = DSL.field("EXTSPID").eq(extSid);
        selectQuery.addConditions(eq);
        Result<Record> fetch = selectQuery.fetch();
        for (Record record : fetch) {
            Object hdsd0001036 = record.getValue("HDSD0001036");
            if (StringUtils.isEmpty(hdsd0001036)) {
                hdsd0001036 = "";
            }

            //判断在redis中是否存在，不存在从数据库中查询
            String name = redisTemplate.opsForValue().get(CommonVariable.REDIS_KEY_HM_YWGMY + hdsd0001036);
            if (name != null && !name.isEmpty()) {
                cjsName += name;
            }
            else {

            }
        }
        return cjsName;
    }

    /**
     * 查询基本信息
     *
     * @param parameter
     * @return
     * @throws Exception
     */
    public BaseInfoShowData verification(FamilyMemberAddParameter parameter) {
        SelectQuery<Record> selectQuery = jooq2.selectQuery(DataTables.T_HR_JKDA);
        Condition eq = DSL.field("HDSD0001006").eq(parameter.getIdentityCard())
                .and(DSL.field("HDSD0001002").eq(parameter.getTrueName()))
                .and(DSL.field("HDSD0001008").eq(parameter.getUserPhone()));
        selectQuery.addConditions(eq);
        List<Record> record = selectQuery.fetch();
        BaseInfoShowData fetch = null;
        if (record != null && record.size() > 0) fetch = record.get(0).into(BaseInfoShowData.class);
        return fetch;
    }
}
