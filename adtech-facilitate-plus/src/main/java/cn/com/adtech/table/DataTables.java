package cn.com.adtech.table;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

public class DataTables {

    // 用户信息表
    public static Table t_userinfo = DSL.table("N_USER_IDENTITY");

    // 用户信息表的字段
    public static class Userinfo {
        public static Field<String> id = DSL.field("ID", SQLDataType.VARCHAR);
        public static Field<String> identity = DSL.field("IDENTITY_CARD", SQLDataType.VARCHAR);
        public static Field<String> realName = DSL.field("TRUE_NAME", SQLDataType.VARCHAR);
        public static Field<String> phone = DSL.field("USER_PHONE", SQLDataType.VARCHAR);
        public static Field<String> type = DSL.field("USER_TYPE", SQLDataType.VARCHAR);
        public static Field<String> password = DSL.field("USER_PWD", SQLDataType.VARCHAR);
        public static Field<String> extmpi = DSL.field("EXTMPI", SQLDataType.VARCHAR);
        public static Field<Integer> loginCount = DSL.field("LOGIN_COUNT", SQLDataType.INTEGER);
    }

    public static Table N_SOURCE_PLATFORM = DSL.table("N_SOURCE_PLATFORM");//机构和基层明细表
    public static Table N_MEDICAL_INSTITUTION_COUNT = DSL.table("N_MEDICAL_INSTITUTION_COUNT");//机构和基层统一表
    public static Table N_RESIDENT_COUNT = DSL.table("N_RESIDENT_COUNT");//居民统计表
    public static Table feedbackQuestion = DSL.table("N_FEEDBACK_QUESTION_RECORD");
    public static Table feedbackType = DSL.table("N_FEEDBACK_TYPE_RECORD");

    public static Table<?> t_family_member = DSL.table("N_FAMILY_MEMBER");
    public static Table t_authorise_member = DSL.table("N_FAMILY_MEMBER");

    public static Table familyMember = DSL.table("N_FAMILY_MEMBER").as("familyMember");//家庭成员关系

    public static Table dictionaryDetail = DSL.table("N_DICTIONARY_DETAIL").as("dictionaryDetail");//字典
    public static Table T_HR_JKDA = DSL.table("T_HR_A010101");//个人健康档案
    public static Table T_HR_JKDA_GMS = DSL.table("T_HR_A01010102");//个人健康档案-过敏史
    public static Table T_HR_JKDA_JZJBS = DSL.table("T_HR_A01010108");//个人健康档案-家族疾病史
    public static Table T_HR_JKDA_CJS = DSL.table("T_HR_A01010109");//个人健康档案-残疾史

    public static Table ORGANIZATION = DSL.table("ORGANIZATION");//医院数据
    public static Table T_HR_CSYXDJ = DSL.table("T_HR_B010101");//出生医学登记


    public static Table T_HR_B010302 = DSL.table("T_HR_B010302");//新生儿随访记录-主表
    public static Table T_HR_B010301 = DSL.table("T_HR_B010301");//新生儿随访记录-疾病筛查
    public static Table T_HR_B01030203 = DSL.table("T_HR_B01030203");//新生儿随访记录-黄疸

    public static Table T_HR_B040103 = DSL.table("T_HR_B040103");//高血压随访记录-主表
    public static Table T_HR_B04010301 = DSL.table("T_HR_B04010301");//高血压随访记录-症状
    public static Table T_HR_B04010302 = DSL.table("T_HR_B04010302");//高血压随访记录-用药记录


    public static Table T_HR_B040202 = DSL.table("T_HR_B040202");//糖尿病随访-主表
    public static Table T_HR_B04020201 = DSL.table("T_HR_B04020201");//糖尿病随访-症状
    public static Table T_HR_B04020202 = DSL.table("T_HR_B04020202");//糖尿病随访-用药记录

    public static Table EMERGENCDOOR = DSL.table("T_HR_C010203").as("emergencDoor");//门急诊
    public static Table ANAPHYLAXIS = DSL.table("T_HR_C01020301");//(门急诊病历-过敏史)
    public static Table PAST_HISTORY = DSL.table("T_HR_C01020302");//(门急诊病历-既往史)
    public static Table CHINESE_NAME = DSL.table("T_HR_C01020306");//(门急诊病历-中医病名)
    public static Table CHINESE_SYMPTOM = DSL.table("T_HR_C01020307");//(门急诊病历-中医证候)
    public static Table WEST_NAME = DSL.table("T_HR_C01020308");//(门急诊病历-西医诊断)
    public static Table AUXILIARY_CHECK = DSL.table("T_HR_C01020304");//(门急诊病历-辅助检查项目)


    public static Table HOSPITALIZATION = DSL.table("T_HR_C020501");//住院病案
    public static Table T_HR_C02050102 = DSL.table("T_HR_C02050102");//西医病案首页-手术
    public static Table T_HR_C02050103 = DSL.table("T_HR_C02050103");//西医病案首页-过敏药物
    public static Table T_HR_C02050104 = DSL.table("T_HR_C02050104");//西医病案首页-门（急）诊诊疾病
    public static Table T_HR_C02050106 = DSL.table("T_HR_C02050106");//西医病案首页-出院诊断主要疾病


    public static Table MEDICALREPORT = DSL.table("T_HR_C021001");//检查报告-主表
    public static Table T_HR_YX010201 = DSL.table("T_HR_YX010201");//检查报告-就诊信息
    public static Table T_HR_C02100102 = DSL.table("T_HR_C02100102");//检查报告-检查报告结果
    public static Table T_HR_C02100103 = DSL.table("T_HR_C02100103");//检查报告-手术记录


    public static Table INSPECTIONREPORT = DSL.table("T_HR_C021002");//检验报告-主表
    public static Table T_HR_C02100202 = DSL.table("T_HR_C02100202");//检验报告-检验记录结果

    public static Table CHINESEMEDICINE = DSL.table("T_HR_C010302");//中药处方主表
    public static Table T_HR_C01030202 = DSL.table("T_HR_C010302");//中药处方-饮片明细表

    public static Table WESTERNMEDICINE = DSL.table("T_HR_C010301");//西药处方
    public static Table T_HR_C01030101 = DSL.table("T_HR_C01030101");//西药处方-西药处方明细

    public static Table T_HR_B030101 = DSL.table("T_HR_B030101");//预防接种卡信息
    public static Table T_HR_B03010101 = DSL.table("T_HR_B03010101");//预防接种记录


    /**
     * 门急诊（主子表）字段
     */
    public static class EmergencDoor {
        public static Field<Object> anaphylaxis = DSL.field("HDSD0003014").as("anaphylaxis");//过敏史
        public static Field<Object> pastHistory = DSL.field("HDSD0003025").as("pastHistory");//既往史
        public static Field<Object> westName = DSL.field("EXHDSD0202243").as("westName");//西医诊断
        public static Field<Object> chineseName = DSL.field("EXHDSD0202243").as("chineseName");//中医病名
        public static Field<Object> chineseSymptom = DSL.field("EXHDSD0202243").as("chineseSymptom");//中医症候
        public static Field<Object> auxiliaryItem = DSL.field("HDSD0003013").as("auxiliaryItem");//辅助检查项目
        public static Field<Object> auxiliaryResult = DSL.field("HDSD0003012").as("auxiliaryResult");//辅助检查结果
    }

    /**
     * 医院机构字段
     */
    public static class OrganizationField {
        public static Field<Object> orgCode = DSL.field("ORG_CODE");
        public static Field<Object> orgName = DSL.field("ORG_NAME");
        public static Field<Object> parentId = DSL.field("PARENT_ID");
        public static Field<Object> areaCode = DSL.field("AREA_CODE");
        public static Field<Object> orgType = DSL.field("ORG_TYPE");

    }
}
