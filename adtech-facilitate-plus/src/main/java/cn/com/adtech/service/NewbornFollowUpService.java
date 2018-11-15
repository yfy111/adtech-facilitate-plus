package cn.com.adtech.service;

import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.medicalinformation.request.IdentityCardParameter;
import cn.com.adtech.domain.medicalinformation.request.MedicalRecordsParameter;
import cn.com.adtech.domain.medicalinformation.request.NewbornFollowUpParameter;
import cn.com.adtech.domain.medicalinformation.response.MedicalRecordsShowData;
import cn.com.adtech.domain.medicalinformation.response.NewbornFollowUpDiseaseShowData;
import cn.com.adtech.domain.medicalinformation.response.NewbornFollowUpJaundiceShowData;
import cn.com.adtech.domain.medicalinformation.response.NewbornFollowUpShowData;
import cn.com.adtech.table.DataTables;
import cn.com.adtech.util.GetMpiUtil;
import cn.com.adtech.util.SetNullToStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 新生儿记录的显示数据
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:57
 */
@Slf4j
@Service
public class NewbornFollowUpService {

    @Autowired
    private SetNullToStringUtil setNullToStringUtil;
    @Autowired
    @Qualifier("secondJooq")
    private DSLContext jooq2;

    @Autowired
    private GetMpiUtil getMpiUtil;

    /**
     * 新生儿记录
     *
     * @return
     */


    public NewbornFollowUpShowData showIndex(NewbornFollowUpParameter newbornFollowUpParameter) {
        NewbornFollowUpShowData data = new NewbornFollowUpShowData();
        //新生儿记录

//        List<NewbornFollowUpShowData> baseShowDataList = jooq2.fetch(NewbornFollowUpService.SqlFactor.listSqlCreate(newbornFollowUpParameter))
//                .into(NewbornFollowUpShowData.class);
        List<NewbornFollowUpShowData> baseShowDataList = jooq2.select().from(DataTables.T_HR_B010302)
                .where(DSL.field("HDSD0001216").eq(newbornFollowUpParameter.getIdentityCard())
                        .and(DSL.field("EXTSID").eq(newbornFollowUpParameter.getCheckDate())))
                .fetch().into(NewbornFollowUpShowData.class);
        if(baseShowDataList!=null&&baseShowDataList.size()>0){
            data=baseShowDataList.get(0);
        }

        if(data.getExtsid()!=null){
            //疾病筛选
            List<NewbornFollowUpDiseaseShowData> diseaseShowDataList = jooq2.select().from(DataTables.T_HR_B010301).where(DSL.field("EXTSPID")
                    .eq(data.getExtsid())).fetch().into(NewbornFollowUpDiseaseShowData.class);
            if(diseaseShowDataList!=null&&diseaseShowDataList.size()>0){
                data.setHdsb0103025(diseaseShowDataList.get(0).getHdsb0103025());
            }
            //黄疸
            List<NewbornFollowUpJaundiceShowData> jaundiceShowDataList = jooq2.select().from(DataTables.T_HR_B01030203).where(DSL.field("EXTSPID")
                    .eq(data.getExtsid())).fetch().into(NewbornFollowUpJaundiceShowData.class);
            if (jaundiceShowDataList != null && jaundiceShowDataList.size() > 0) {
                data.setHdsd0001252(jaundiceShowDataList.get(0).getHdsd0001252());
            }
        }
        setNullToStringUtil.setValue(data,"-");
        return data;
    }

    /**
     * 随访时间列表
     *
     * @return
     */
    public List<DictionaryDetailShowData> followUpTime(IdentityCardParameter identityCardParameter) {
        //新生儿记录随访日期
        List<NewbornFollowUpShowData> baseShowDataList = jooq2.select().from(DataTables.T_HR_B010302)
                .where(DSL.field("EXTMPI").in(getMpiUtil.getMpiByList(identityCardParameter.getIdentityCard())))
                .orderBy(DSL.field("HDSD0001287").desc())
                //.where(DSL.field("HDSD0001216").eq(identityCardParameter.getIdentityCard()))
                .fetch().into(NewbornFollowUpShowData.class);

        List<DictionaryDetailShowData> list = new ArrayList<>();
        for(NewbornFollowUpShowData base :baseShowDataList){
            if(StringUtils.isEmpty(base.getExtsid())||StringUtils.isEmpty(base.getHdsd0001287())) continue;
            DictionaryDetailShowData date = new DictionaryDetailShowData();
            date.setTypeName(base.getHdsd0001287());
            date.setTypeValue(base.getExtsid());
            list.add(date);
        }
        return list;
    }

    static class SqlFactor{
        /**
         * 查询sql
         * @return
         */
        public static String listSqlCreate(NewbornFollowUpParameter newbornFollowUpParameter){
            StringBuffer sb = new StringBuffer();
            sb.append("select * from T_HR_B010302 where EXTCA='").append(newbornFollowUpParameter.getIdentityCard()).append("'");
            sb.append(" and to_char(HDSD0001287,'yyyy-mm-dd')=").append("'").append(newbornFollowUpParameter.getCheckDate()).append("'");
            log.info("sql打印："+sb.toString());
            return sb.toString();
        }
    }
}
