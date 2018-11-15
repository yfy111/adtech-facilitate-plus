package cn.com.adtech.service;

import cn.com.adtech.component.UserinfoService;
import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.medicalinformation.request.DiabetesFollowUpParameter;
import cn.com.adtech.domain.medicalinformation.request.IdentityCardParameter;
import cn.com.adtech.domain.medicalinformation.response.DiabetesFollowUpMedicineShowData;
import cn.com.adtech.domain.medicalinformation.response.DiabetesFollowUpShowData;
import cn.com.adtech.domain.medicalinformation.response.DiabetesFollowUpSymptomShowData;
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
 * @Description 糖尿病随访记录的显示数据
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:57
 */
@Slf4j
@Service
public class DiabetesFollowUpService {

    @Autowired
    @Qualifier("secondJooq")
    private DSLContext jooq2;

    @Autowired
    private GetMpiUtil getMpiUtil;
    /**
     * 糖尿病随访记录
     *
     * @return
     */
    public DiabetesFollowUpShowData showIndex(DiabetesFollowUpParameter diabetesFollowUpParameter) {
        DiabetesFollowUpShowData data = new DiabetesFollowUpShowData();
        //糖尿病随访记录
//        List<DiabetesFollowUpShowData> baseShowDataList = jooq2.fetch(DiabetesFollowUpService.SqlFactor
//                .listSqlCreate(diabetesFollowUpParameter)).into(DiabetesFollowUpShowData.class);
        List<DiabetesFollowUpShowData> baseShowDataList = jooq2.select().from(DataTables.T_HR_B040202)
                .where(DSL.field("EXTSID").eq(diabetesFollowUpParameter.getCheckDate()))
//                        .and(DSL.field("EXTCA").eq(diabetesFollowUpParameter.getIdentityCard())))
                .fetch().into(DiabetesFollowUpShowData.class);
        if(baseShowDataList!=null&&baseShowDataList.size()>0){
            data=baseShowDataList.get(0);
        }


        if(data.getExtsid()!=null){
            //用药记录
            List<DiabetesFollowUpMedicineShowData> medicineShowDataList = jooq2.select().from(DataTables.T_HR_B04020202).where(DSL.field("EXTSPID")
                    .eq(data.getExtsid())).fetch().into(DiabetesFollowUpMedicineShowData.class);
            if(medicineShowDataList!=null&&medicineShowDataList.size()>0){
                data.setHdsd0001194(medicineShowDataList.get(0).getHdsd0001194());
                data.setHdsd0001195(medicineShowDataList.get(0).getHdsd0001195());
                data.setHdsd0001196(medicineShowDataList.get(0).getHdsd0001196());
                data.setHdsd0001197(medicineShowDataList.get(0).getHdsd0001197());
                data.setHdsd0001198(medicineShowDataList.get(0).getHdsd0001198());
            }
            //症状表
            List<DiabetesFollowUpSymptomShowData> symptomShowDataList = jooq2.select().from(DataTables.T_HR_B04020201).where(DSL.field("EXTSPID")
                    .eq(data.getExtsid())).fetch().into(DiabetesFollowUpSymptomShowData.class);
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
        //糖尿病随访记录随访日期
        List<DiabetesFollowUpShowData> baseShowDataList = jooq2.select().from(DataTables.T_HR_B040202)
                .where(DSL.field("EXTMPI").in(getMpiUtil.getMpiByList(identityCardParameter.getIdentityCard())))
                .orderBy(DSL.field("hdsd0001466").desc())
                .fetch().into(DiabetesFollowUpShowData.class);
        List<DictionaryDetailShowData> list = new ArrayList<>();
        for(DiabetesFollowUpShowData base :baseShowDataList){
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
        public static String listSqlCreate(DiabetesFollowUpParameter diabetesFollowUpParameter){
            StringBuffer sb = new StringBuffer();
            sb.append("select * from T_HR_B040202 where EXTCA='").append(diabetesFollowUpParameter.getIdentityCard()).append("'");
            sb.append(" and to_char(HDSD0001466,'yyyy-mm-dd')=").append("'").append(diabetesFollowUpParameter.getCheckDate()).append("'");
            log.info("sql打印："+sb.toString());
            return sb.toString();
        }
    }
}
