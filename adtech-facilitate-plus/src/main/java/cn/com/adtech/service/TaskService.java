package cn.com.adtech.service;

import cn.com.adtech.component.UserinfoService;
import cn.com.adtech.domain.login.LoginSuccessUserinfo;
import cn.com.adtech.domain.medicalinformation.request.BirthCertificateParameter;
import cn.com.adtech.domain.medicalinformation.response.BirthCertificateShowData;
import cn.com.adtech.domain.task.ResidentCount;
import cn.com.adtech.domain.task.Source;
import cn.com.adtech.domain.task.SourceCount;
import cn.com.adtech.table.DBFields;
import cn.com.adtech.table.DataTables;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 定时器任务
 * @Author LJL
 */
@Service
@Transactional
public class TaskService {


    @Autowired
    @Qualifier("primaryJooq")
    private DSLContext jooq;


    /*****
     *
     *
     * 定时汇总机构基层明显表到
     * count表
     * */
    public void taskSource() {

        long startTime = System.currentTimeMillis();

        int total = 0;//用于记录增加多少数据
        StringBuffer sbcs = new StringBuffer();
        SimpleDateFormat y = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String times = y.format(date);
        sbcs.append("select  " +
                "t.parent_id," +
                "t.extcda," +
                "t.institution_code," +
                "t.institution_name, " +
                "t.institution_type," +
                "max(t.create_time) time ," +
                "count(t.user_id) count " +
                "from n_source_platform t ");
        sbcs.append("WHERE  TO_CHAR(T.CREATE_TIME,'yyyy-mm-dd') like '%" + times + "%'");
        sbcs.append(" AND T.INSTITUTIONSTATUS='0' ");
        sbcs.append("group by  t.parent_id,t.city_id,t.extcda,t.institution_code,t.institution_name, t.institution_type");
        List<Source> sourcesListRead = jooq.fetch(sbcs.toString()).into(Source.class);//获取查询人次
        StringBuffer sbcount = new StringBuffer();
        sbcount.append("select  " +
                "t.parent_id," +
                "t.extcda," +
                "t.institution_code," +
                "t.institution_name, " +
                "t.institution_type," +
                "max(t.create_time) time," +
                "count(distinct(t.user_id)) count " +
                "from n_source_platform t ");
        sbcount.append("WHERE  TO_CHAR(T.CREATE_TIME,'yyyy-mm-dd') like '%" + times + "%'");
        sbcount.append(" AND T.INSTITUTIONSTATUS='0' ");
        sbcount.append("group by  t.parent_id,t.city_id,t.extcda,t.institution_code,t.institution_name, t.institution_type");
        List<Source> sourcesListPeople = jooq.fetch(sbcount.toString()).into(Source.class);//获取查询人数

        List<SourceCount> dataList = new ArrayList<SourceCount>();
        for (int i = 0; i < sourcesListPeople.size(); i++) {
            SourceCount sourceCount = new SourceCount();
            for (int j = 0; j < sourcesListRead.size(); j++) {
                if (sourcesListPeople.get(i).getInstitutionCode().equals(sourcesListRead.get(j).getInstitutionCode())) {
                    sourceCount.setReadcount(sourcesListRead.get(j).getCount());
                    sourceCount.setPeoplecount(sourcesListPeople.get(i).getCount());
                }
            }
            sourceCount.setParentId(sourcesListPeople.get(i).getParentId());
            sourceCount.setExtcda(sourcesListPeople.get(i).getExtcda());
            sourceCount.setInstitutionCode(sourcesListPeople.get(i).getInstitutionCode());
            sourceCount.setInstitutionName(sourcesListPeople.get(i).getInstitutionName());
            sourceCount.setInstitutionType(sourcesListPeople.get(i).getInstitutionType());
            sourceCount.setLasttime(sourcesListPeople.get(i).getTime());
            sourceCount.setCountTime(date);
            int restult = jooq.insertInto(DataTables.N_MEDICAL_INSTITUTION_COUNT).columns(
                    DBFields.nMediclaInstitutionCount.parentId, DBFields.nMediclaInstitutionCount.cityId,
                    DBFields.nMediclaInstitutionCount.extcdaId, DBFields.nMediclaInstitutionCount.institutionCode,
                    DBFields.nMediclaInstitutionCount.institutionName, DBFields.nMediclaInstitutionCount.institutionType,
                    DBFields.nMediclaInstitutionCount.archivesCount, DBFields.nMediclaInstitutionCount.readingCount,
                    DBFields.nMediclaInstitutionCount.readingPeopleCount, DBFields.nMediclaInstitutionCount.status,
                    DBFields.nMediclaInstitutionCount.lastTime, DBFields.nMediclaInstitutionCount.countDate
            ).values(sourceCount.getParentId(), sourceCount.getCityId(), sourceCount.getExtcda(),
                    sourceCount.getInstitutionCode(), sourceCount.getInstitutionName(), sourceCount.getInstitutionType(),
                    sourceCount.getArchivesCount(), sourceCount.getReadcount(), sourceCount.getPeoplecount(), "0",
                    sourceCount.getLasttime(), sourceCount.getCountTime()).execute();
            total = total + restult;
        }

        StringBuffer updateSql = new StringBuffer();
        updateSql.append("update N_SOURCE_PLATFORM ");
        updateSql.append("set INSTITUTIONSTATUS = '1' ");
        updateSql.append(" WHERE TO_CHAR(CREATE_TIME,'yyyy-mm-dd') like '%" + times + "%'");
        jooq.execute(updateSql.toString());
        System.out.println("机构和基层增量数据:" + total + "条");
        long endTime = System.currentTimeMillis();
        System.out.println("机构和基层增量数据运行时间：" + (endTime - startTime) + "ms"+"====="+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));    //输出程序运行时间

    }

    //定时明细居民数据汇总
    public void taskresident() {
        long startTime = System.currentTimeMillis();
        int total = 0;//用于记录增加多少数据
        StringBuffer sbcs = new StringBuffer();
        SimpleDateFormat y = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String times = y.format(date);
        //   jooq.deleteFrom(DataTables.N_RESIDENT_COUNT).where("TO_CHAR(COUNT_DATE,'yyyy-mm-dd') like '%" + times + "%'").execute();
        sbcs.append("select  " +
                "t.parent_id," +
                "t.city_id," +
                "t.platform_id," +
                "t.extcda," +
                "max(t.create_time) time ," +
                "count(t.user_id) count " +
                "from n_source_platform t ");
        sbcs.append("WHERE  TO_CHAR(T.CREATE_TIME,'yyyy-mm-dd') like '%" + times + "%'");
        sbcs.append(" AND T.RESIDENTSTATUS='0' ");
        sbcs.append("group by  t.parent_id,t.city_id, t.platform_id,t.extcda");
        List<Source> sourcesListQuery = jooq.fetch(sbcs.toString()).into(Source.class);//获取查询总数量
        StringBuffer sbcount = new StringBuffer();


        sbcount.append("select  " +
                "t.parent_id," +
                "t.city_id," +
                "t.platform_id," +
                "t.extcda," +
                "max(t.create_time) time ," +
                "count(t.user_id) count " +
                "from n_source_platform t ");
        sbcount.append("WHERE  TO_CHAR(T.CREATE_TIME,'yyyy-mm-dd') like '%" + times + "%'");
        sbcount.append(" AND T.USER_TYPE='1' ");
        sbcount.append(" AND T.RESIDENTSTATUS='0' ");
        sbcount.append("group by  t.parent_id,t.city_id, t.platform_id,t.extcda");

        List<Source> sourcesListPeople = jooq.fetch(sbcount.toString()).into(Source.class);//获取查询人次

        List<SourceCount> dataList = new ArrayList<SourceCount>();
        for (int i = 0; i < sourcesListQuery.size(); i++) {
            ResidentCount residentCount = new ResidentCount();
            for (int j = 0; j < sourcesListPeople.size(); j++) {
                if (sourcesListPeople.get(j).getPlatformId().equals(sourcesListQuery.get(i).getPlatformId()) &&
                        sourcesListPeople.get(j).getExtcda().equals(sourcesListQuery.get(i).getExtcda())) {
                    residentCount.setPeoplecount(sourcesListPeople.get(j).getCount());
                }
            }
            residentCount.setQuerycount(sourcesListQuery.get(i).getCount());
            residentCount.setParentId(sourcesListQuery.get(i).getParentId());
            residentCount.setExtcda(sourcesListQuery.get(i).getExtcda());
            residentCount.setCountTime(sourcesListQuery.get(i).getTime());
            residentCount.setPlatformCount(sourcesListQuery.get(i).getCount());
            residentCount.setPlatfromId(sourcesListQuery.get(i).getPlatformId());
            int restult = jooq.insertInto(DataTables.N_RESIDENT_COUNT).columns(
                    DBFields.nResidentCount.parentId, DBFields.nResidentCount.cityId,
                    DBFields.nResidentCount.extcdaId,
                    DBFields.nResidentCount.queryCount, DBFields.nResidentCount.queryPeopleCount,
                    DBFields.nResidentCount.platformId, DBFields.nResidentCount.platformCount,
                    DBFields.nResidentCount.status,
                    DBFields.nResidentCount.countDate
            ).values(residentCount.getParentId(), residentCount.getCityId(), residentCount.getExtcda(),
                    residentCount.getQuerycount(), residentCount.getPeoplecount(), residentCount.getPlatfromId(),
                    residentCount.getPlatformCount(), "0",
                    residentCount.getCountTime()).execute();
            total = total + restult;

        }

        StringBuffer updateSql = new StringBuffer();
        updateSql.append("update N_SOURCE_PLATFORM ");
        updateSql.append("set RESIDENTSTATUS = '1' ");
        updateSql.append(" WHERE TO_CHAR(CREATE_TIME,'yyyy-mm-dd') like '%" + times + "%'");
        jooq.execute(updateSql.toString());
        System.out.println("居民增量数据:" + total + "条");
        long endTime = System.currentTimeMillis();
        System.out.println("居民增量数据运行时间：" + (endTime - startTime) + "ms"+"====="+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));    //输出程序运行时间

    }
}
