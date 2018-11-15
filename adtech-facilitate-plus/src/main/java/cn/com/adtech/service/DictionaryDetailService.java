package cn.com.adtech.service;

import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.po.DictionaryDetail;
import cn.com.adtech.table.DataTables;
import cn.com.adtech.util.CommonVariable;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description 数据字典的业务流程
 * @Author PYH
 * @CreateDate 2018/11/6 14:56
 */
@Service
@Transactional
public class DictionaryDetailService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    @Qualifier("primaryJooq")
    private DSLContext jooq;

    @Autowired
    @Qualifier("secondJooq")
    private DSLContext jooq2;
    /**
     * 根据类别编号和类别名称
     * @param dictionary
     * @return
     */
    public DictionaryDetail findDictionaryDetailInfo(DictionaryDetail dictionary){
        SelectQuery<Record> selectQuery = jooq.selectQuery(DataTables.dictionaryDetail);
        Condition eq = DSL.field("TYPE_CODE").eq(dictionary.getTypeCode())
                .and(DSL.field("TYPE_NAME").eq(dictionary.getTypeName()));
        selectQuery.addConditions(eq);
        List<Record> record=selectQuery.fetch();
        DictionaryDetail fetch =null;
        if(record!=null&&record.size()>0){
            fetch = record.get(0).into(DictionaryDetail.class);
            String member=redisTemplate.opsForValue().get(CommonVariable.REDIS_KEY_HM_CYGX+fetch.getTypeName());
            if(member!=null&&!member.isEmpty()){
                redisTemplate.opsForValue().set(CommonVariable.REDIS_KEY_HM_CYGX+fetch.getTypeName(),fetch.getTypeValue());
            }
        }
        return fetch;
    }


    /**
     * 根据typeValue查询字典列表
     * @param type_code
     * @return
     */
    public List<DictionaryDetailShowData> findDictionaryDetailDataList(String type_code,String typeValue){
        SelectQuery<Record> selectQuery = jooq.selectQuery(DataTables.dictionaryDetail);
        Condition eq = DSL.field("TYPE_CODE").eq(type_code).and(DSL.field("IS_DISCONTINUATION").eq("F")
                .and(DSL.field("TYPE_VALUE").eq(typeValue))
        );
        selectQuery.addConditions(eq);
        List<DictionaryDetailShowData> dataList  = selectQuery.fetch().into(DictionaryDetailShowData.class);
        return dataList;
    }


    /**
     * 根据typeValue查询字典列表
     * @param type_code
     * @return
     */
    public List<DictionaryDetailShowData> findDictionaryDetailDataList(String type_code){
        SelectQuery<Record> selectQuery = jooq.selectQuery(DataTables.dictionaryDetail);
        Condition eq = DSL.field("TYPE_CODE").eq(type_code).and(DSL.field("IS_DISCONTINUATION").eq("F"));
        selectQuery.addConditions(eq);
        List<DictionaryDetailShowData> dataList  = selectQuery.fetch().into(DictionaryDetailShowData.class);
        return dataList;
    }

    /**
     * @param csId
     * @return
     */
    public List<DictionaryDetailShowData> loadRRS_DIC_CODE(String csId){
        List<DictionaryDetailShowData> list =
         jooq2.select(DSL.field("NAME").as("typeName"),DSL.field("CODE").as("typeValue"))
                .from(CommonVariable.LOAD_TABLE_NAME_RRS_DIC_CODE)
                .where(DSL.field("CS_ID").eq(csId))
//                 .unionAll(
//                         jooq2.select(DSL.field("ID").as("typeValue"),DSL.field("NAME").as("typeName"))
//                         .from(CommonVariable.LOAD_TABLE_NAME_RRS_DIC_CODE)
//                         .where(DSL.field("ID").eq(code))
//                        )
                .fetch().into(DictionaryDetailShowData.class);
        return list;
    }

    /**
     *  区域
     * @param areaCode 对应值
     * @return
     */
    public List<DictionaryDetailShowData> loadCountry(String areaCode){
        List<DictionaryDetailShowData> list =
                jooq2.select(DSL.field("AREA_NAME").as("typeName"),DSL.field("AREA_CODE").as("typeValue"))
                        .from(CommonVariable.LOAD_TABLE_NAME_REGION)
                        .where(DSL.field("AREA_CODE").eq(areaCode))
                        .fetch().into(DictionaryDetailShowData.class);
        return list;
    }

}
