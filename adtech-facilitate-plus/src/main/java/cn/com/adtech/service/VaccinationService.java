package cn.com.adtech.service;

import cn.com.adtech.domain.medicalinformation.request.VaccinationListParameter;
import cn.com.adtech.domain.medicalinformation.response.VaccinationBasicShowData;
import cn.com.adtech.domain.medicalinformation.response.VaccinationListShowData;
import cn.com.adtech.domain.po.Page;
import cn.com.adtech.table.DataTables;
import cn.com.adtech.util.GetMpiUtil;
import cn.com.adtech.util.SetNullToStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 预防接种
 */
@Service
@Slf4j
public class VaccinationService {

    @Autowired
    @Qualifier("secondJooq")
    private DSLContext jooq2;

    @Autowired
    private SetNullToStringUtil setNullToStringUtil;

    @Autowired
    private GetMpiUtil getMpiUtil;

    /**
     * 预防接种个人基本信息
     *
     * @return
     */
    public VaccinationBasicShowData showIndex(VaccinationListParameter vaccinationListParameter) {
        List<VaccinationBasicShowData> baseShowDataList = jooq2.select().from(DataTables.T_HR_B030101)
                .where(DSL.field("EXTCA").eq(vaccinationListParameter.getIdentityCard()))
                .fetch().into(VaccinationBasicShowData.class);
        if (baseShowDataList != null && baseShowDataList.size() > 0) return baseShowDataList.get(0);
        else return new VaccinationBasicShowData();
    }

    /**
     * 接种记录 分页
     *
     * @param vaccinationListParameter
     * @return
     */
    public Page<VaccinationListShowData> vaccinationList(VaccinationListParameter vaccinationListParameter) {
        Integer total = 0;
       total = getTotalCount(jooq2.fetch(VaccinationService.SqlFactor.totalCount(getMpiUtil.getMpisForMedical(vaccinationListParameter.getIdentityCard()))));
        List<VaccinationListShowData> listShowData = new ArrayList<>();
        if (total > 0) {
            listShowData = jooq2.fetch(VaccinationService.SqlFactor
                    .pageList(vaccinationListParameter,getMpiUtil.getMpisForMedical(vaccinationListParameter.getIdentityCard())))
                    .into(VaccinationListShowData.class);
        }
        for(VaccinationListShowData data : listShowData){
            setNullToStringUtil.setValue(data,"-");
        }
        //返回数据
        Page<VaccinationListShowData> page = new Page<>();
        page.setClazz(VaccinationListShowData.class);
        page.setList(listShowData);
        page.setPageNum(vaccinationListParameter.getPageNum());
        page.setPageSize(5);
        page.setTotalCount(total);
        return page;
    }

    /**
     * 获取总条数
     *
     * @param count
     * @return
     */
    public Integer getTotalCount(Result count) {
        Integer total = Integer.valueOf(count.getValues(0).get(0).toString());
        return total;
    }


    static class SqlFactor {
        //分页sql
        public static String pageList(VaccinationListParameter vaccinationListParameter,String mpis) {
            Integer pageSize = 5;
            Integer pageNum = vaccinationListParameter.getPageNum() == null ? 1 : vaccinationListParameter.getPageNum();
            Integer row = pageSize * pageNum;
            // 拼装sql语句
            String innerSql = String.format("select t.*,ROWNUM rowno from T_HR_B03010101 t where extmpi in %s and ROWNUM<=%d",
                    mpis, row);

            return String.format("select * from (%s) tt where tt.rowno>%d", innerSql, row - pageSize);

//            StringBuilder sb = new StringBuilder();
//            sb.append("select * from T_HR_B03010101 where EXTCA='").append(vaccinationListParameter.getIdentityCard()).append("'");
//            sb.append(" and ROWNUM<=").append(row);
//            sb.append(" and ROWNUM>=").append(row - pageSize);
//            log.info("sql:" + sb.toString());
//            return sb.toString();
        }

        /**
         * 总条数
         *
         * @return
         */
        public static String totalCount(String mpis) {
            StringBuilder sb = new StringBuilder();
            sb.append("select count(*) from T_HR_B03010101");
            sb.append(" where extmpi in ").append(mpis);
            log.info("总条数sql:" + sb.toString());
            return sb.toString();
        }
    }
}
