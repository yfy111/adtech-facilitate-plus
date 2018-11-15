package cn.com.adtech.service;

import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.medicalinformation.request.DiabetesFollowUpParameter;
import cn.com.adtech.domain.medicalinformation.request.HypertensionFollowUpParameter;
import cn.com.adtech.domain.medicalinformation.request.IdentityCardParameter;
import cn.com.adtech.domain.medicalinformation.request.NewbornFollowUpParameter;
import cn.com.adtech.domain.medicalinformation.response.*;
import cn.com.adtech.table.DataTables;
import cn.com.adtech.util.GetMpiUtil;
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
 * @Description 高血压随访记录的显示数据
 * @CreateDate 2018/11/2 16:57
 */
@Slf4j
@Service
public class HypertensionFollowUpService {

    @Autowired
    @Qualifier("secondJooq")
    private DSLContext jooq2;

    @Autowired
    private GetMpiUtil getMpiUtil;
    /**
     * 高血压随访记录
     *
     * @return
     */
    public HypertensionFollowUpShowData showIndex(HypertensionFollowUpParameter hypertensionFollowUpParameter) {
        HypertensionFollowUpShowData data = new HypertensionFollowUpShowData();
        //高血压随访记录
//        List<HypertensionFollowUpShowData> baseShowDataList = jooq2
//                .fetch(HypertensionFollowUpService.SqlFactor.listSqlCreate(hypertensionFollowUpParameter)).into(HypertensionFollowUpShowData.class);
        List<HypertensionFollowUpShowData> baseShowDataList = jooq2.select().from(DataTables.T_HR_B040103)
                .where(DSL.field("EXTSID").eq(hypertensionFollowUpParameter.getCheckDate()))
                        //.and(DSL.field("EXTCA").eq(hypertensionFollowUpParameter.getIdentityCard())))
                .fetch().into(HypertensionFollowUpShowData.class);
        if(baseShowDataList!=null&&baseShowDataList.size()>0){
            data=baseShowDataList.get(0);
        }


        if(data.getExtsid()!=null){
            //用药记录
            List<HypertensionFollowUpMedicineShowData> medicineShowDataList = jooq2.select().from(DataTables.T_HR_B04010302).where(DSL.field("EXTSPID")
                    .eq(data.getExtsid())).fetch().into(HypertensionFollowUpMedicineShowData.class);
            if(medicineShowDataList!=null&&medicineShowDataList.size()>0){
                data.setHdsd0001194(medicineShowDataList.get(0).getHdsd0001194());
                data.setHdsd0001195(medicineShowDataList.get(0).getHdsd0001195());
                data.setHdsd0001196(medicineShowDataList.get(0).getHdsd0001196());
                data.setHdsd0001197(medicineShowDataList.get(0).getHdsd0001197());
                data.setHdsd0001198(medicineShowDataList.get(0).getHdsd0001198());
            }
            //症状表
            List<HypertensionFollowUpSymptomShowData> symptomShowDataList = jooq2.select().from(DataTables.T_HR_B04010301).where(DSL.field("EXTSPID")
                    .eq(data.getExtsid())).fetch().into(HypertensionFollowUpSymptomShowData.class);
            if (symptomShowDataList != null && symptomShowDataList.size() > 0) {
                data.setHdsd0001047(symptomShowDataList.get(0).getHdsd0001047());
            }
        }

        return data;
    }

    /**
     * 随访时间列表
     *
     * @return
     */
    public List<DictionaryDetailShowData> followUpTime(IdentityCardParameter identityCardParameter) {
        //高血压随访记录随访日期
        List<HypertensionFollowUpShowData> baseShowDataList = jooq2.select().from(DataTables.T_HR_B040103)
                .where(DSL.field("EXTMPI").in(getMpiUtil.getMpiByList(identityCardParameter.getIdentityCard())))
                .orderBy(DSL.field("HDSD0001466").desc())
                .fetch().into(HypertensionFollowUpShowData.class);

        List<DictionaryDetailShowData> list = new ArrayList<>();
        for(HypertensionFollowUpShowData base :baseShowDataList){
            if(StringUtils.isEmpty(base.getExtsid())||StringUtils.isEmpty(base.getHdsd0001466())) continue;
            DictionaryDetailShowData date = new DictionaryDetailShowData();
            date.setTypeName(base.getHdsd0001466());
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
        public static String listSqlCreate(HypertensionFollowUpParameter hypertensionFollowUpParameter){
            StringBuffer sb = new StringBuffer();
            sb.append("select * from T_HR_B040103 where EXTCA='").append(hypertensionFollowUpParameter.getIdentityCard()).append("'");
            sb.append(" and to_char(HDSD0001466,'yyyy-mm-dd')=").append("'").append(hypertensionFollowUpParameter.getCheckDate()).append("'");
            log.info("sql打印："+sb.toString());
            return sb.toString();
        }
    }
}
