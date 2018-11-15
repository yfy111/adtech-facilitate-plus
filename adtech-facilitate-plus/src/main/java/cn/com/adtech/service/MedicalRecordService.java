package cn.com.adtech.service;

import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.medicalinformation.request.IdentityCardParameter;
import cn.com.adtech.domain.medicalinformation.request.MedicalRecordsParameter;
import cn.com.adtech.domain.medicalinformation.request.medicalRecords.*;
import cn.com.adtech.domain.medicalinformation.response.*;
import cn.com.adtech.domain.po.Page;
import cn.com.adtech.table.DataTables;
import cn.com.adtech.util.CommonVariable;
import cn.com.adtech.util.GetMpiUtil;
import cn.com.adtech.util.LifeUtil;
import cn.com.adtech.util.SetNullToStringUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description 医疗记录业务流程
 * @Author chenguangxue
 * @createdate 2018/11/2 17:11
 */
@Slf4j
@Service
public class MedicalRecordService {
    @Autowired
    @Qualifier("secondJooq")
    private DSLContext jooq2;

    @Autowired
    private RedisLoadService redisLoadService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SetNullToStringUtil setNullToStringUtil;

    @Autowired
    private GetMpiUtil getMpiUtil;
    /**
     * 加载就诊记录列表
     *
     * @param medicalRecordsParameter
     * @return
     */
    public Page showIndex(MedicalRecordsParameter medicalRecordsParameter) {
//        //分页参数初始化
//        medicalRecordsParameter.setPageNum(medicalRecordsParameter.getPageNum() == null || medicalRecordsParameter.getPageNum() == 0
//                ? 1 : medicalRecordsParameter.getPageNum());
//        medicalRecordsParameter.setPageSize(medicalRecordsParameter.getPageSize() == null || medicalRecordsParameter.getPageSize() == 0
//                ? 5 : medicalRecordsParameter.getPageSize());
//        Integer total = getTotalCount(jooq2.fetch(SqlFactor.listSqlCreateCount(medicalRecordsParameter)));
//        List<MedicalRecordsShowData> list = new ArrayList<>();
//        //数据查询
//        if (total > 0)
//            list = jooq2.fetch(SqlFactor.listSqlCreate(medicalRecordsParameter)).into(MedicalRecordsShowData.class);
        //返回数据
        Page page = new Page();
//        page.setClazz(MedicalRecordsShowData.class);
//        page.setList(list);
//        page.setPageNum(medicalRecordsParameter.getPageNum());
//        page.setPageSize(medicalRecordsParameter.getPageSize());
//        page.setTotalCount(total);
        return page;
    }

    /**
     * 改版就诊记录列表查询
     *
     * @param medicalRecordsParameter
     * @return
     */
    public Page<MedicalRecordsShowNewData> showIndexNew(MedicalRecordsParameter medicalRecordsParameter) {
        //分页参数初始化(从list取，0开始)
        medicalRecordsParameter.setPageNum(medicalRecordsParameter.getPageNum() == null || medicalRecordsParameter.getPageNum() == 0
                ? 1 : medicalRecordsParameter.getPageNum());
        medicalRecordsParameter.setPageSize(medicalRecordsParameter.getPageSize() == null || medicalRecordsParameter.getPageSize() == 0
                ? 5 : medicalRecordsParameter.getPageSize());
        //获得条件查询后的list
        List<MedicalRecordsShowNewData> dataList =
                encapsulationList(paramsDataList(medicalRecordsParameter, medicalRecordsShowDataList(medicalRecordsParameter)));
        for(MedicalRecordsShowNewData data : dataList){
            setNullToStringUtil.setValue(data,"-");
        }
        //条件查询
        Page page = new Page();
        page.setPageNum(medicalRecordsParameter.getPageNum());
        page.setPageSize(medicalRecordsParameter.getPageSize());
        page.setTotalCount(dataList.size());
        //分页
        //分页参数参数计算
        dataList.add(null);
        int limit = medicalRecordsParameter.getPageNum() * medicalRecordsParameter.getPageSize();
        if (limit == 0) limit = 5;
        int bgin = limit - medicalRecordsParameter.getPageSize();
        if (page.getTotalCount() < limit) {
            limit = dataList.size() - 1;
            if (page.getTotalCount() == 1) limit = 1;
        }
        if (bgin < 0||bgin>page.getTotalCount()) bgin = 0;

        //截取数据集
        page.setClazz(MedicalRecordsShowNewData.class);
        page.setList(dataList.subList(bgin, limit));
        return page;
    }

    /**
     * 条件筛选返回list
     *
     * @param medicalRecordsParameter
     * @param dataList
     * @return
     */
    public List<MedicalRecordsShowData> paramsDataList(MedicalRecordsParameter medicalRecordsParameter
            , List<MedicalRecordsShowData> dataList) {
        List<MedicalRecordsShowData> dataList1 = new ArrayList<>();
        //根据就诊类型筛选
        if (!StringUtils.isEmpty(medicalRecordsParameter.getVisitType())) {
            for (MedicalRecordsShowData data : dataList) {
                if (medicalRecordsParameter.getVisitType().equals(data.getBltype())) {
                    dataList1.add(data);
                }
            }
            dataList.clear();
            dataList.addAll(dataList1);
            //每次递归
            medicalRecordsParameter.setVisitType(null);
            dataList1.clear();
            paramsDataList(medicalRecordsParameter, dataList);
        }
        //生命周期 childhood,少年:juvenile,青年:youth,中年:middleAge,old:老年
        if (!StringUtils.isEmpty(medicalRecordsParameter.getCycleType())) {
            Map<String, Map<String, Integer>> lifeMap = LifeUtil.lifeMap;
            for (MedicalRecordsShowData data : dataList) {
                if (StringUtils.isEmpty(data.getAge())) continue;
                Integer age = data.getAge();
                if (lifeMap.containsKey(medicalRecordsParameter.getCycleType())) {
                    Map<String, Integer> life = lifeMap.get(medicalRecordsParameter.getCycleType());
                    if (age >= life.get("begin") && age <= life.get("end")) {
                        dataList1.add(data);
                    }
                }
            }
            dataList.clear();
            dataList.addAll(dataList1);
            //每次递归
            medicalRecordsParameter.setCycleType(null);
            dataList1.clear();
            paramsDataList(medicalRecordsParameter, dataList);
        }
        //就诊开始时间
        if (!StringUtils.isEmpty(medicalRecordsParameter.getBeginDate())) {
            for (MedicalRecordsShowData data : dataList) {
                if(StringUtils.isEmpty(data.getCreatedate())) continue;
                if (medicalRecordsParameter.getBeginDate().compareTo(data.getCreatedate()) <= 0) {
                    dataList1.add(data);
                }
            }
            dataList.clear();
            dataList.addAll(dataList1);
            //每次递归
            medicalRecordsParameter.setBeginDate(null);
            dataList1.clear();
            paramsDataList(medicalRecordsParameter, dataList);
        }
        //就诊结束时间
        if (!StringUtils.isEmpty(medicalRecordsParameter.getEndDate())) {
            for (MedicalRecordsShowData data : dataList) {
                if(StringUtils.isEmpty(data.getCreatedate())) continue;
                if (medicalRecordsParameter.getEndDate().compareTo(data.getCreatedate()) >= 0) {
                    dataList1.add(data);
                }
            }
            dataList.clear();
            dataList.addAll(dataList1);
            //每次递归
            medicalRecordsParameter.setEndDate(null);
            dataList1.clear();
            paramsDataList(medicalRecordsParameter, dataList);
        }
        //就诊医院
        if (!StringUtils.isEmpty(medicalRecordsParameter.getHospitalName())) {
            for (MedicalRecordsShowData data : dataList) {
                if(StringUtils.isEmpty(data.getExtcc())) continue;
                if (medicalRecordsParameter.getHospitalName().equals(data.getExtcc())) {
                    dataList1.add(data);
                }
            }
            dataList.clear();
            dataList.addAll(dataList1);
            //每次递归
            medicalRecordsParameter.setHospitalName(null);
            dataList1.clear();
            paramsDataList(medicalRecordsParameter, dataList);
        }
        return dataList;
    }


    /**
     * 门急诊弹框
     *
     * @return
     */
    public EmerencyDoorShowData emergencyDoor(EmergencyDoorMedicalRecordParameter doorMedicalRecordParameter) {
        EmerencyDoorShowData data = null;
        //门急诊主表
        List<EmerencyDoorShowData> showDataList = jooq2.select().from(DataTables.EMERGENCDOOR).where(DSL.field("EXTSID")
                .eq(doorMedicalRecordParameter.getExtsid())).fetch().into(EmerencyDoorShowData.class);
        if (showDataList != null && showDataList.size() > 0) {
            data = showDataList.get(0);
        }
        else {
            data = new EmerencyDoorShowData();
        }

        //中医病名
        List<EmergencyDoorChineseNameShowData> chineseNameList = jooq2.select(DataTables.EmergencDoor.chineseName).from(DataTables.CHINESE_NAME).where(DSL.field("EXTSPID")
                .eq(doorMedicalRecordParameter.getExtsid())).fetch().into(EmergencyDoorChineseNameShowData.class);
        if (chineseNameList != null && chineseNameList.size() > 0) {
            data.setChinesedisease(chineseNameList.get(0).getExhdsd0202243());
        }
        //过敏史
        List<EmergencyDoorAnaphylaxisShowData> anaphylaxisList = jooq2.select(DataTables.EmergencDoor.anaphylaxis).from(DataTables.ANAPHYLAXIS).where(DSL.field("EXTSPID")
                .eq(doorMedicalRecordParameter.getExtsid())).fetch().into(EmergencyDoorAnaphylaxisShowData.class);
        if (anaphylaxisList != null && anaphylaxisList.size() > 0) {
            data.setHdsd0003014(anaphylaxisList.get(0).getHdsd0003014());
        }
        //既往史
        List<EmergencyDoorPassHistoryShowData> pastHistoryList = jooq2.select(DataTables.EmergencDoor.pastHistory).from(DataTables.PAST_HISTORY).where(DSL.field("EXTSPID")
                .eq(doorMedicalRecordParameter.getExtsid())).fetch().into(EmergencyDoorPassHistoryShowData.class);
        if (pastHistoryList != null && pastHistoryList.size() > 0) {
            data.setHistory(pastHistoryList.get(0).getHdsd0003025());
        }
        //西医诊断
        List<EmergencyDoorWestNameShowData> westNameList = jooq2.select(DataTables.EmergencDoor.westName).from(DataTables.WEST_NAME).where(DSL.field("EXTSPID")
                .eq(doorMedicalRecordParameter.getExtsid())).fetch().into(EmergencyDoorWestNameShowData.class);
        if (westNameList != null && westNameList.size() > 0) {
            data.setExhdsd0202243(westNameList.get(0).getExhdsd0202243());
        }
        //中医症候
        List<EmergencyDoorChineseSysptomShowData> chineseSymptomList = jooq2.select(DataTables.EmergencDoor.chineseSymptom).from(DataTables.CHINESE_SYMPTOM).where(DSL.field("EXTSPID")
                .eq(doorMedicalRecordParameter.getExtsid())).fetch().into(EmergencyDoorChineseSysptomShowData.class);
        if (chineseSymptomList != null && chineseSymptomList.size() > 0) {
            data.setChinesesymptom(chineseSymptomList.get(0).getExhdsd0202243());
        }
        //辅助检查
        List<EmergencyDoorAuxiliaryCheckShowData> auxiliaryItemList = jooq2.select(DataTables.EmergencDoor.auxiliaryItem, DataTables.EmergencDoor.auxiliaryResult)
                .from(DataTables.AUXILIARY_CHECK).where(DSL.field("EXTSPID")
                        .eq(doorMedicalRecordParameter.getExtsid())).fetch().into(EmergencyDoorAuxiliaryCheckShowData.class);
        if (auxiliaryItemList != null && auxiliaryItemList.size() > 0) {
            data.setHdsd0003012(auxiliaryItemList.get(0).getHdsd0003012());
            data.setHdsd0003013(auxiliaryItemList.get(0).getHdsd0003013());
        }
        setNullToStringUtil.setValue(data,"-");
        return data;
    }

    /**
     * 住院病案首页弹框
     *
     * @return
     */
    public HospitalizationShowData hospitalization(HospitalizationParameter hospitalizationParameter) {
        //西医病案首页主表
        HospitalizationShowData data = null;
        List<HospitalizationShowData> showDataList = jooq2.select().from(DataTables.HOSPITALIZATION)
                .where(DSL.field("EXTSID").eq(hospitalizationParameter.getExtsid()))
                .fetch().into(HospitalizationShowData.class);
        if (showDataList != null && showDataList.size() > 0)
            data = showDataList.get(0);
        else data = new HospitalizationShowData();

        //西医病案首页-手术
        List<HospitalizationOperationShowData> hospitalizationOperationShowDataList = jooq2.select().from(DataTables.T_HR_C02050102)
                .where(DSL.field("EXTSPID").eq(hospitalizationParameter.getExtsid()))
                .fetch().into(HospitalizationOperationShowData.class);
        if (hospitalizationOperationShowDataList != null && hospitalizationOperationShowDataList.size() > 0) {
            data.setHdsd0011091(hospitalizationOperationShowDataList.get(0).getHdsd0011091());
            data.setHdsd0011094(hospitalizationOperationShowDataList.get(0).getHdsd0011094());
            data.setHdsd0011090(hospitalizationOperationShowDataList.get(0).getHdsd0011090());
            data.setHdsd0011074(hospitalizationOperationShowDataList.get(0).getHdsd0011074());
        }

        //西医病案首页-过敏药物
        List<HospitalizationAntihistamineShowData> hospitalizationAntihistamineShowDataList = jooq2.select().from(DataTables.T_HR_C02050103)
                .where(DSL.field("EXTSPID").eq(hospitalizationParameter.getExtsid()))
                .fetch().into(HospitalizationAntihistamineShowData.class);
        if (hospitalizationAntihistamineShowDataList != null && hospitalizationAntihistamineShowDataList.size() > 0) {
            data.setHdsd0012044(hospitalizationAntihistamineShowDataList.get(0).getHdsd0012044());
        }
        //西医病案首页-门（急）诊诊断名称
        List<HospitalizationDiagnosisNameShowData> hospitalizationDiagnosisNameShowData = jooq2.select().from(DataTables.T_HR_C02050104)
                .where(DSL.field("EXTSPID").eq(hospitalizationParameter.getExtsid()))
                .fetch().into(HospitalizationDiagnosisNameShowData.class);
        if (hospitalizationDiagnosisNameShowData != null && hospitalizationDiagnosisNameShowData.size() > 0) {
            data.setHdsd0012083(hospitalizationDiagnosisNameShowData.get(0).getHdsd0012083());
        }
        //西医病案首页-主要诊断
        List<HospitalizationDiagnosisMainShowData> hospitalizationDiagnosisMainShowDataList = jooq2.select().from(DataTables.T_HR_C02050106)
                .where(DSL.field("EXTSPID").eq(hospitalizationParameter.getExtsid()))
                .fetch().into(HospitalizationDiagnosisMainShowData.class);
        if (hospitalizationDiagnosisMainShowDataList != null && hospitalizationDiagnosisMainShowDataList.size() > 0) {
            data.setExhdsd0203148(hospitalizationDiagnosisMainShowDataList.get(0).getExhdsd0203148());
        }
        setNullToStringUtil.setValue(data,"-");
        return data;
    }


    /**
     * 检查记录类型加载
     *
     * @param extsidList 传入多个检查类型的extsid
     *                   页面优先加载检查项目，而后根据extsid 进行加载数据
     * @return
     */
    public List<DictionaryDetailShowData> findCheckRecordName(List<String> extsidList) {
        List<DictionaryDetailShowData> list = jooq2.select(DSL.field("HDSD0005033").as("typeName"), DSL.field("EXTSID").as("typeValue"))
                .from(DataTables.MEDICALREPORT).where(DSL.field("EXTSID").in(extsidList)).fetch().into(DictionaryDetailShowData.class);
        return list;
    }


    /**
     * 检查报告弹窗
     *
     * @return
     */
    public CheckRecordShowData checkRecord(CheckRecordParameter checkRecordParameter) {
        CheckRecordShowData data = null;
        //检查记录主表
        List<CheckRecordShowData> checkRecordShowData = jooq2.select().from(DataTables.MEDICALREPORT)
                .where(DSL.field("EXTSID").eq(checkRecordParameter.getExtsid()))
                .fetch().into(CheckRecordShowData.class);
        if (checkRecordShowData != null && checkRecordShowData.size() > 0) {
            data = checkRecordShowData.get(0);
        }
        else data = new CheckRecordShowData();

        //西医病案首页-就诊信息
        List<CheckRecordVisitShowData> checkRecordVisitShowDataList = jooq2.select().from(DataTables.T_HR_YX010201)
                .where(DSL.field("EXTSPID").eq(checkRecordParameter.getExtsid()))
                .fetch().into(CheckRecordVisitShowData.class);
        if (checkRecordVisitShowDataList != null && checkRecordVisitShowDataList.size() > 0) {
            data.setExhdsd1001024(checkRecordVisitShowDataList.get(0).getExhdsd1001024());
            data.setExhdsd1001025(checkRecordVisitShowDataList.get(0).getExhdsd1001025());
            data.setExhdsd1001026(checkRecordVisitShowDataList.get(0).getExhdsd1001026());
        }

        //西医病案首页-就诊信息
        List<CheckRecordResultShowData> checkRecordResultShowDataList = jooq2.select().from(DataTables.T_HR_C02100102)
                .where(DSL.field("EXTSPID").eq(checkRecordParameter.getExtsid()))
                .fetch().into(CheckRecordResultShowData.class);
        if (checkRecordResultShowDataList != null && checkRecordResultShowDataList.size() > 0) {
            data.setExhdsd0202182(checkRecordResultShowDataList.get(0).getExhdsd0202182());
            data.setHdsd0005030(checkRecordResultShowDataList.get(0).getHdsd0005030());
            data.setHdsd0005031(checkRecordResultShowDataList.get(0).getHdsd0005031());
            data.setHdsd0005034(checkRecordResultShowDataList.get(0).getHdsd0005034());
            data.setHdsd0005038(checkRecordResultShowDataList.get(0).getHdsd0005038());
            data.setHdsd0005073(checkRecordResultShowDataList.get(0).getHdsd0005073());
        }

        //西医病案首页-手术记录
        List<CheckRecordOperationShowData> checkRecordOperationShowDataList = jooq2.select().from(DataTables.T_HR_C02100103)
                .where(DSL.field("EXTSPID").eq(checkRecordParameter.getExtsid()))
                .fetch().into(CheckRecordOperationShowData.class);
        if (checkRecordOperationShowDataList != null && checkRecordOperationShowDataList.size() > 0) {
            data.setHdsd0005060(checkRecordOperationShowDataList.get(0).getHdsd0005060());
            data.setHdsd0005062(checkRecordOperationShowDataList.get(0).getHdsd0005062());
        }
        setNullToStringUtil.setValue(data, "-");
        return data;
    }


    /**
     * 检验记录类型加载
     *
     * @param extsidList 传入多个检查类型的extsid
     *                   页面优先加载检验项目，而后根据extsid 进行加载数据
     * @return
     */
    public List<DictionaryDetailShowData> findInspectionRecordName(List<String> extsidList) {
        List<DictionaryDetailShowData> list = jooq2.select(DSL.field("EXHDSD0202196").as("typeName"), DSL.field("EXTSID").as("typeValue"))
                .from(DataTables.INSPECTIONREPORT).where(DSL.field("EXTSID").in(extsidList)).fetch().into(DictionaryDetailShowData.class);
        return list;
    }


    /**
     * 检验记录弹框
     *
     * @return
     */
    public InspectionRecordShowData inspectionRecord(InspectionRecordParameter inspectionRecordParameter) {
        InspectionRecordShowData data = new InspectionRecordShowData();
        //检验报告基本信息
        List<InspectionRecordBaseShowData> baseShowDataList = jooq2.select().from(DataTables.INSPECTIONREPORT)
                .where(DSL.field("EXTSID").eq(inspectionRecordParameter.getExtsid()))
                .fetch().into(InspectionRecordBaseShowData.class);
        if (baseShowDataList != null && baseShowDataList.size() > 0) {
            data.setInspectionRecordBaseShowData(baseShowDataList.get(0));
        }

        //检验报告-检验记录结果
        List<InspectionRecordItemListShowData> itemListShowDataList = jooq2.select().from(DataTables.T_HR_C02100202)
                .where(DSL.field("EXTSID").eq(inspectionRecordParameter.getExtsid()))
                .fetch().into(InspectionRecordItemListShowData.class);
        if (itemListShowDataList == null) itemListShowDataList = new ArrayList<>();
        data.setInspectionRecordItemListShowData(itemListShowDataList);
        setNullToStringUtil.setValue(data.getInspectionRecordBaseShowData(), "_");
        for (InspectionRecordItemListShowData d : itemListShowDataList) {
            setNullToStringUtil.setValue(d, "-");
        }
        return data;
    }


    /**
     * 中药处方弹窗
     *
     * @return
     */
    public ChineseMedicineShowData chineseMedicine(ChineseMedicineParameter chineseMedicineParameter) {
        ChineseMedicineShowData data = new ChineseMedicineShowData();
        //中药处方报告基本信息
        List<ChineseMedicineBaseShowData> baseShowDataList = jooq2.select().from(DataTables.CHINESEMEDICINE)
                .where(DSL.field("EXTSID").eq(chineseMedicineParameter.getExtsid()))
                .fetch().into(ChineseMedicineBaseShowData.class);
        if (baseShowDataList != null && baseShowDataList.size() > 0) {
            data.setChineseMedicineBaseShowData(baseShowDataList.get(0));
        }

        //中药处方饮片明细
        List<ChineseMedicineItemListShowData> itemListShowDataList = jooq2.select().from(DataTables.T_HR_C01030202)
                .where(DSL.field("EXTSID").eq(chineseMedicineParameter.getExtsid()))
                .fetch().into(ChineseMedicineItemListShowData.class);
        data.setChineseMedicineItemListShowDataList(itemListShowDataList);
        setNullToStringUtil.setValue(data.getChineseMedicineBaseShowData(), "_");
        for (ChineseMedicineItemListShowData d : itemListShowDataList) {
            setNullToStringUtil.setValue(d, "-");
        }
        return data;
    }

    /**
     * 西药处方弹窗
     *
     * @return
     */
    public WesternMedicineShowData westernMedicine(WesternPrescriptionParameter westernPrescriptionParameter) {
        WesternMedicineShowData data = new WesternMedicineShowData();
        //西药处方报告基本信息
        List<WesternMedicineBaseShowData> baseShowDataList = jooq2.select().from(DataTables.WESTERNMEDICINE)
                .where(DSL.field("EXTSID").eq(westernPrescriptionParameter.getExtsid()))
                .fetch().into(WesternMedicineBaseShowData.class);
        if (baseShowDataList != null && baseShowDataList.size() > 0) {
            data.setWesternMedicineBaseShowData(baseShowDataList.get(0));
        }

        //西药处方饮片明细
        List<WesternMedicineItemShowData> itemListShowDataList = jooq2.select().from(DataTables.T_HR_C01030101)
                .where(DSL.field("EXTSID").eq(westernPrescriptionParameter.getExtsid()))
                .fetch().into(WesternMedicineItemShowData.class);
        data.setWesternMedicineItemShowData(itemListShowDataList);
        setNullToStringUtil.setValue(data.getWesternMedicineBaseShowData(), "-");
        for (WesternMedicineItemShowData d : itemListShowDataList) {
            setNullToStringUtil.setValue(d, "-");
        }
        return data;
    }

    /**
     * 加载sql，获得个人所有就诊记录list
     *
     * @return
     */
    public List<MedicalRecordsShowData> medicalRecordsShowDataList(MedicalRecordsParameter medicalRecordsParameter) {
        List<MedicalRecordsShowData> list = null;
        String key = CommonVariable.REDIS_KEY_HM_MEDICAL + medicalRecordsParameter.getIdentityCard();
        //存在则从redis取出
//        redisTemplate.delete(key);
        if (redisTemplate.hasKey(key)) {
            list = JSON.parseArray(redisTemplate.opsForValue()
                    .get(key), MedicalRecordsShowData.class);
        }
        else {
            //如果没有数据，就从数据库查询并放入redis
            list = jooq2.fetch(SqlFactor.listSqlCreateWebShow(medicalRecordsParameter,getMpis(medicalRecordsParameter.getIdentityCard())))
                    .into(MedicalRecordsShowData.class);
            redisTemplate.opsForValue().set(key, JSON.toJSONString(list));
            //设置值为过期为30分钟
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        }
        return list;
    }


    /**
     * 获取MPI
     * @param identityCard
     * @return
     */
    public String getMpis(String identityCard){
        return getMpiUtil.getMpisForMedical(identityCard);
    }

    /**
     * 封装list
     * 依旧放入redis
     *
     * @return
     */
    public List<MedicalRecordsShowNewData> encapsulationList(List<MedicalRecordsShowData> list) {
        Map<String, Map<String, List<String>>> paramsMap = paramsMap(list);
        List<MedicalRecordsShowNewData> dataList = new ArrayList<>();
        if (paramsMap.size() > 0) {
            // 每一个 医院+身份证+时间作为一条数据（数据维度）
            MedicalRecordsShowNewData data = null;
            for (String key : paramsMap.keySet()) {
                data = new MedicalRecordsShowNewData();
                String[] keys = key.split("_");
                data.setIdentityCard(keys[0]);
                data.setUsername(keys[1]);
                data.setHospitalName(keys[2]);
                data.setCreatedate(keys[3]);
                List<MedicalRecordsShowNewBltypeData> ls = new ArrayList<>();
                for (String s : paramsMap.get(key).keySet()) {
                    MedicalRecordsShowNewBltypeData d = new MedicalRecordsShowNewBltypeData();
                    d.setVisitName(s);
                    d.setExtsid(paramsMap.get(key).get(s));
                    ls.add(d);
                }
                data.setTypeList(ls);
                dataList.add(data);
            }
        }
        return dataList;
    }


    /**
     * 外层map key: 医院+身份证+时间+就诊类别
     * 内部map key: 就诊类别
     *
     * @param list
     * @return
     */
    public Map<String, Map<String, List<String>>> paramsMap(List<MedicalRecordsShowData> list) {
        // 外层map
        Map<String, Map<String, List<String>>> paramsMap = new LinkedHashMap<>();
        if (list != null || list.size() > 0) {
            //内部map
            Map<String, List<String>> typeMap = null;
            List<String> medicalRecordsShowDataList = null;
            String date = null;
            String visitType = null;
            String identityCard = null;
            String hospitalName = null;
            String name = null;
            String key = null;//外层key
            for (MedicalRecordsShowData data : list) {
                if(StringUtils.isEmpty(data.getCreatedate())) continue;
                date = data.getCreatedate();
                visitType = data.getBltype();
                identityCard = data.getExhdsd0202249();
                hospitalName = data.getExtcc();
                name = data.getUsername();
                //下划线隔开
                key = identityCard + "_" + name + "_" + hospitalName + "_" + date;
                //如果存在
                if (paramsMap.containsKey(key)) {
                    typeMap = paramsMap.get(key);
                    //判断类型是否存在
                    if (typeMap.containsKey(visitType)) {
                        //如果存在
                        medicalRecordsShowDataList = typeMap.get(visitType);
                    }
                    else {
                        //如果不存在
                        medicalRecordsShowDataList = new ArrayList<>();
                    }
                    //将对应类型数据放入list
                    medicalRecordsShowDataList.add(data.getExtsid());
                }
                else {
                    //否则
                    medicalRecordsShowDataList = new ArrayList<>();
                    medicalRecordsShowDataList.add(data.getExtsid());
                    typeMap = new LinkedHashMap<>();
                    typeMap.put(visitType, medicalRecordsShowDataList);
                    paramsMap.put(key, typeMap);
                }
            }
        }
        return paramsMap;
    }

    /**
     * 加载条件
     *
     * @param identityCardParameter
     * @return
     */
    public Map<String, List<DictionaryDetailShowData>> parameterLoad(IdentityCardParameter identityCardParameter) {
        Map<String, List<DictionaryDetailShowData>> map = new HashMap();
        MedicalRecordsParameter medicalRecordsParameter = new MedicalRecordsParameter();
        medicalRecordsParameter.setIdentityCard(identityCardParameter.getIdentityCard());
//        System.err.println(System.currentTimeMillis());
        List<MedicalRecordsShowData> list = jooq2.fetch(SqlFactor.listSqlCreateWebShow(medicalRecordsParameter,getMpis(medicalRecordsParameter.getIdentityCard())))
                .into(MedicalRecordsShowData.class);
//        System.err.println(System.currentTimeMillis());
        //生命周期
        Map<String, Object> ageMap = new HashMap<>();
        Integer childhood = 0;
        Integer juvenile = 0;
        Integer youth = 0;
        Integer middleAge = 0;
        Integer old = 0;
        //医院名称
        Map<String, Object> hName = new HashMap<>();
        for (MedicalRecordsShowData data : list) {
            //设置医院名称
            hName.put(data.getExtcc(), redisLoadService.getHosptalName(data.getExtcc()));
            //如果没有医院名字，略过
            if(StringUtils.isEmpty(data.getExtcc())) continue;
            if (!StringUtils.isEmpty(data.getAge()) && data.getAge() >=0) {
                if (data.getAge() >= 0 && data.getAge() <= 6) {
                    //童年
                    ageMap.put("childhood", ++childhood);
                }
                else if (data.getAge() >= 7 && data.getAge() <= 17) {
                    //少年
                    ageMap.put("juvenile", ++juvenile);
                }
                else if (data.getAge() >= 18 && data.getAge() <= 40) {
                    //青年
                    ageMap.put("youth", ++youth);
                }
                else if (data.getAge() >= 41 && data.getAge() <= 65) {
                    //中年
                    ageMap.put("middleAge", ++middleAge);
                }
                else {
                    //老年
                    ageMap.put("old", ++old);
                }
            }
        }
//        System.err.println(System.currentTimeMillis());
        map.put("life", setHospitalName(ageMap));
        map.put("hspName", setHospitalName(hName));
        map.put("btlType", redisLoadService.getBltypeName("documentType"));
        return map;
    }

    /**
     * 包装参数
     *
     * @param map
     * @return
     */
    public List<DictionaryDetailShowData> setHospitalName(Map<String, Object> map) {
        List<DictionaryDetailShowData> list = new ArrayList<>();
        for (String s : map.keySet()) {
            if(StringUtils.isEmpty(map.get(s))) continue;
            DictionaryDetailShowData data = new DictionaryDetailShowData();
            data.setTypeValue(s);
            data.setTypeName(map.get(s).toString());
            list.add(data);
        }
        return list;
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
        //生命周期
        public static Map<String, Map<String, Integer>> lifeMap = new HashMap<>();

        static {
            for (Integer i = 1; i <= 5; i++) {
                Map<String, Integer> mp = new HashMap<>();
                if (i == 1) {
                    mp.put("begin", 0);
                    mp.put("end", 6);
                    lifeMap.put("childhood", mp);
                }
                else if (i == 2) {
                    mp.put("begin", 1);
                    mp.put("end", 17);
                    lifeMap.put("juvenile", mp);
                }
                else if (i == 3) {
                    mp.put("begin", 18);
                    mp.put("end", 40);
                    lifeMap.put("youth", mp);
                }
                else if (i == 4) {
                    mp.put("begin", 41);
                    mp.put("end", 65);
                    lifeMap.put("middleAge", mp);
                }
                else if (i == 5) {
                    mp.put("begin", 66);
                    mp.put("end", 300);
                    lifeMap.put("old", mp);
                }
            }
        }


        /**
         * 前端显示条件sql
         *
         * @param medicalRecordsParameter
         * @return
         */
        public static String listSqlCreateWebShow(MedicalRecordsParameter medicalRecordsParameter,String sbmp) {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT * FROM (");
            sb.append(listSqlCreateBase(medicalRecordsParameter,sbmp));
            sb.append(") order by createDate desc");
            log.info("前端显示条件sql打印：" + sb.toString());
            return sb.toString();
        }


        /**
         * 分页条件查询sql
         *
         * @param medicalRecordsParameter
         * @return
         */
        public static String listSqlCreate(MedicalRecordsParameter medicalRecordsParameter,String sbmp) {
            StringBuffer sb = new StringBuffer();
            //分页计算
            Integer pageNum = medicalRecordsParameter.getPageNum();
            Integer pageSize = medicalRecordsParameter.getPageSize();
            Integer rowNum = pageNum * pageSize;
            sb.append("select * from(");
            sb.append("SELECT a.*,ROWNUM rn FROM (");
            sb.append(listSqlCreateBase(medicalRecordsParameter,sbmp));
            sb.append(") a where 1=1");
            sb.append(listSqlCreateCondition(medicalRecordsParameter));
            sb.append(" and  ROWNUM<=").append(rowNum);
            sb.append("  order by a.createdate desc");
            int r = rowNum - pageSize;
            sb.append(") a1 where a1.rn>=").append(r == 0 ? 1 : r);
            log.info("sql打印：" + sb.toString());
            return sb.toString();
        }

        /**
         * 条件组合sql
         *
         * @param medicalRecordsParameter
         * @return
         */
        public static String listSqlCreateCondition(MedicalRecordsParameter medicalRecordsParameter) {
            StringBuffer sb = new StringBuffer();
            if (!StringUtils.isEmpty(medicalRecordsParameter.getVisitType()) && !"1".equals(medicalRecordsParameter.getVisitType())) {
                //病例类型
                sb.append(" and a.bltype='").append(medicalRecordsParameter.getVisitType()).append("' ");
            }
            if (!StringUtils.isEmpty(medicalRecordsParameter.getHospitalName())) {
                //医院
                sb.append("and a.EXTCC='").append(medicalRecordsParameter.getHospitalName()).append("' ");
            }
            if (!StringUtils.isEmpty(medicalRecordsParameter.getCycleType())) {
                //生命周期
                Map<String, Integer> mp = lifeMap.get(medicalRecordsParameter.getCycleType());
                sb.append("and a.age>=").append(mp.get("begin")).append(" ");
                sb.append("and a.age<=").append(mp.get("end")).append(" ");
            }
            if (!StringUtils.isEmpty(medicalRecordsParameter.getBeginDate())) {
                sb.append("and a.createdate>=to_date('").append(medicalRecordsParameter.getBeginDate()).append("','yyyy-mm-dd') ");
            }
            if (!StringUtils.isEmpty(medicalRecordsParameter.getEndDate())) {
                sb.append("and a.createdate<=to_date('").append(medicalRecordsParameter.getEndDate()).append("','yyyy-mm-dd') ");
            }
            return sb.toString();
        }


        /**
         * 总条数sql
         *
         * @param medicalRecordsParameter
         * @return
         */
        public static String listSqlCreateCount(MedicalRecordsParameter medicalRecordsParameter,String sbmp) {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT count(1) totalCount FROM (");
            sb.append(listSqlCreateBase(medicalRecordsParameter,sbmp));
            sb.append(") a  where 1=1");
            sb.append(listSqlCreateCondition(medicalRecordsParameter));
            log.info("sql打印：" + sb.toString());
            return sb.toString();
        }

        /**
         * 基础sql
         *
         * @param medicalRecordsParameter
         * @return
         */
        public static String listSqlCreateBase(MedicalRecordsParameter medicalRecordsParameter,String sbmp) {

            StringBuffer sb = new StringBuffer();
            //门诊病历
            sb.append("select EXHDSD0202253 age,extmpi,HDSD0003023 createdate,EXHDSD0202250 username,EXHDSD0202249,EXTID,EXTCC,EXTSID, '门诊病历' bltype from T_HR_C010203 where EXTMPI in ");
            //sb.append(medicalRecordsParameter.getIdentityCard()).append("' ");
            sb.append(sbmp);
            sb.append(" UNION ALL ");
            //西药处方
            sb.append("select EXHDSD0202253 age,extmpi,HDSD0004006 createdate,EXHDSD0202250 username,EXHDSD0202249,EXTID,EXTCC,EXTSID ,'西药处方' bltype from T_HR_C010301 where EXTMPI in ");
            //sb.append(medicalRecordsParameter.getIdentityCard()).append("' ");
            sb.append(sbmp);
            sb.append(" UNION ALL ");
            //中药处方
            sb.append("select EXHDSD0202253 age,extmpi,HDSD0004006 createdate,EXHDSD0202250 username,EXHDSD0202249,EXTID,EXTCC,EXTSID,'中药处方' bltype from T_HR_C010302 where EXTMPI in ");
            //sb.append(medicalRecordsParameter.getIdentityCard()).append("' ");
            sb.append(sbmp);
            sb.append(" UNION ALL ");
            //检查报告
            sb.append("select EXHDSD0202253 age,extmpi,EXHDSD0202283 createdate,EXHDSD0202250 username,EXHDSD0202249,EXTID,EXTCC,EXTSID,'检查报告' bltype from T_HR_C021001 where EXTMPI in ");
            //sb.append(medicalRecordsParameter.getIdentityCard()).append("' ");
            sb.append(sbmp);
            sb.append(" UNION ALL ");
            //检验报告
            sb.append("select EXHDSD0202253 age,extmpi,EXHDSD0202231 createdate,EXHDSD0202250 username,EXHDSD0202249,EXTID,EXTCC,EXTSID,'检验报告' bltype from T_HR_C021002 where EXTMPI in ");
            //sb.append(medicalRecordsParameter.getIdentityCard()).append("' ");
            sb.append(sbmp);
            sb.append(" UNION ALL ");
            //西医病案首页
            sb.append("select  to_char(HDSD0011079,'999') age,extmpi,EXHDSD0202312 createdate,HDSD0011110 username,EXHDSD0202258 EXHDSD0202249,EXTID,EXTCC,EXTSID,'住院病案首页' bltype from T_HR_C020501 where EXTMPI in ");
            //sb.append(medicalRecordsParameter.getIdentityCard()).append("'");
            sb.append(sbmp);
            return sb.toString();
        }
    }
}
