package cn.com.adtech.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 共通变量
 * @Author PYH
 * @CreateDate 2018/11/7 09:26
 */
public class CommonVariable {

    public static final String MEMBER_IDEN_MEMBER = "F";//家庭授权:家庭成员
    public static final String MEMBER_IDEN_ACCREDIT = "A";//家庭授权:授权管理
    public static final String DICTIONARY_TYPE_CODE = "familyType";//数据字典类型编号：家庭成员
    public static final String DECTIONALRY_CAST = "DT";//字典转换
    public static final String DESENSITIZATION = "DZ";//脱敏


    /*********** redis key 分割************/
    public static final String REDIS_KEY_HM_CYGX = "HM_CYGX_";//Redis中KEY值：成员关系
    public static final String REDIS_KEY_HM_YWGMY = "HM_YWGMY_";//Redis中KEY值：药物过敏源
    public static final String REDIS_KEY_HM_BTN_TYPE = "HM_BTN_TYPE_";//Redis中KEY值：就诊类型
    public static final String REDIS_KEY_HM_HOSPITAL_NAME = "HM_HOSPITAL_NAME_";//Redis中KEY值：医院名称
    public static final String REDIS_KEY_HM_MEDICAL = "HM_MEDICAL_";//Redis中KEY值：就诊记录列表
    public static final String REDIS_KEY_HM_SEX = "HM_SEX_";//Redis中KEY值：性别
    public static final String REDIS_KEY_HM_NATION = "HM_NATION_";//Redis中KEY值：民族
    public static final String REDIS_KEY_HM_NATIONALITY = "HM_NATIONALITY_";//Redis中KEY值：国籍
    public static final String REDIS_KEY_HM_VACCINE_NAME = "HM_VACCINE_NAME_";//Redis中KEY值：疫苗名称
    public static final String REDIS_KEY_HM_BODY_PARTS = "HM_BODY_PARTS_";//Redis中KEY值：接种部位
    public static final String REDIS_KEY_HM_SKIN = "HM_SKIN_";//Redis中KEY值：皮肤检查结果代码
    public static final String REDIS_KEY_HM_OCCUPATION = "HM_OCCUPATION_";//Redis中KEY值：职业
    public static final String REDIS_KEY_HM_BLOOD_TYPE = "HM_BLOOD_TYPE_";//Redis中KEY值：ABO血型
    public static final String REDIS_KEY_HM_ANAESTHESIA = "HM_ANAESTHESIA_";//Redis中KEY值：麻醉方法
    public static final String REDIS_KEY_HM_INSPECTION_RESULT = "HM_INSPECTION_RESULT_";//Redis中KEY值：检验结果代码
    public static final String REDIS_KEY_HM_INSPECTION_ITEM = "HM_INSPECTION_ITEM_";//Redis中KEY值：检验项目代码
    public static final String REDIS_KEY_HM_RH_BLOOD = "HM_RH_BLOOD_";//Redis中KEY值：RH血型代码
    public static final String REDIS_KEY_HM_EDUCATION = "HM_EDUCATION_";//Redis中KEY值：学历代码
    public static final String REDIS_KEY_HM_MARRIAGE = "HM_MARRIAGE_";//Redis中KEY值：婚姻代码
    public static final String REDIS_KEY_HM_HISTORY_OF_DRUG_ALLERGY = "HM_HISTORY_OF_DRUG_ALLERGY_";//Redis中KEY值：药物过敏史
    public static final String REDIS_KEY_HM_DRUG_ALLERGEN = "HM_DRUG_ALLERGEN_";//Redis中KEY值：药物过敏源
    public static final String REDIS_KEY_HM_FAMILIAL_DISEASES = "HM_FAMILIAL_DISEASES_";//Redis中KEY值：家族性疾病
    public static final String REDIS_KEY_HM_DISABILITY = "HM_DISABILITY_";//Redis中KEY值：残疾情况
    public static final String REDIS_KEY_HM_OCCUPATIONAL_DISEASES = "HM_OCCUPATIONAL_DISEASES_";//Redis中KEY值：职业病
    public static final String REDIS_KEY_HM_DOCUMENT_TYPE = "HM_DOCUMENT_TYPE_";//Redis中KEY值：证件类型
    public static final String REDIS_KEY_HM_NEWBORN_SHIT = "HM_NEWBORN_SHIT_";//Redis中KEY值：新生儿大便形状
    public static final String REDIS_KEY_HM_NEWBORN_FACE_COLOR = "HM_NEWBORN_FACE_COLOR_";//Redis中KEY值：新生儿面色
    public static final String REDIS_KEY_HM_ANTERIOR_FONTANELLE_TENSION = "HM_NEWBORN_FACE_COLOR_";//Redis中KEY值：前囟张力代码
    public static final String REDIS_KEY_HM_SITE_OF_JAUNDICE = "HM_NEWBORN_FACE_COLOR_";//Redis中KEY值：黄疸部位代码
    public static final String REDIS_KEY_HM_SALT_UPTAKE = "HM_SALT_UPTAKE_";//Redis中KEY值：黄疸部位代码
    public static final String REDIS_KEY_HM_FOLLOW_UP_METHOD = "HM_FOLLOW_UP_METHOD_";//Redis中KEY值：随访方式代码
    public static final String REDIS_KEY_HM_HYPOGLYCEMIC_REACTION = "HM_HYPOGLYCEMIC_REACTION_METHOD_";//Redis中KEY值：低血糖反应代码
    public static final String  REDIS_KEY_HM_PROVINCE = "HM_PROVINCE_";//Redis中KEY值：省
    public static final String  REDIS_KEY_HM_CITY = "HM_CITY_";//Redis中KEY值：市
    public static final String  REDIS_KEY_HM_AREA = "HM_AREA_";//Redis中KEY值：区
    public static final String  REDIS_KEY_HM_TOWN = "HM_TOWN_";//Redis中KEY值：乡镇
    public static final String  REDIS_KEY_HM_VILLAGE = "HM_VILLAGE_";//Redis中KEY值：村
    /**************** redis key 分割**************/


    /*******脱敏配置******/
    //姓名
    public static final String DESENSITIZATION_NAME="name";
    //身份证
    public static final String DESENSITIZATION_ID_CARD="identityCard";

    /***需要加载的字典(PS:需要的自己加)**/
    //bigmiddle字典表
    public static final String LOAD_TABLE_NAME_RRS_DIC_CODE = "RRS_DIC_CODE";
    //省市区乡村表
    public static final String LOAD_TABLE_NAME_REGION="REGION";
    //医院对应表
    public static final String LOAD_TABLE_NAME_ORGANIZATION = "ORGANIZATION";


    /**
     * 字典表查询映射
     */
    public static Map<String,String> getMapping = new HashMap<>();

    static{
        getMapping.put(REDIS_KEY_HM_SEX,"6");
        getMapping.put(REDIS_KEY_HM_NATION,"5");
        getMapping.put(REDIS_KEY_HM_NATIONALITY,"353");
        getMapping.put(REDIS_KEY_HM_VACCINE_NAME,"144");
        getMapping.put(REDIS_KEY_HM_BODY_PARTS,"10167");
        getMapping.put(REDIS_KEY_HM_SKIN,"245");
        getMapping.put(REDIS_KEY_HM_OCCUPATION,"9");
        getMapping.put(REDIS_KEY_HM_BLOOD_TYPE,"275");
        getMapping.put(REDIS_KEY_HM_ANAESTHESIA,"86");
        getMapping.put(REDIS_KEY_HM_INSPECTION_RESULT,"323");
        getMapping.put(REDIS_KEY_HM_INSPECTION_ITEM,"386");
        getMapping.put(REDIS_KEY_HM_RH_BLOOD,"188");
        getMapping.put(REDIS_KEY_HM_EDUCATION,"10024");
        getMapping.put(REDIS_KEY_HM_MARRIAGE,"10142");
        getMapping.put(REDIS_KEY_HM_HISTORY_OF_DRUG_ALLERGY,"10003");
        getMapping.put(REDIS_KEY_HM_DRUG_ALLERGEN,"64");
        getMapping.put(REDIS_KEY_HM_FAMILIAL_DISEASES,"10021");
        getMapping.put(REDIS_KEY_HM_DISABILITY,"10022");
        getMapping.put(REDIS_KEY_HM_OCCUPATIONAL_DISEASES,"15");
        getMapping.put(REDIS_KEY_HM_DOCUMENT_TYPE,"486");
        getMapping.put(REDIS_KEY_HM_NEWBORN_FACE_COLOR,"250");
        getMapping.put(REDIS_KEY_HM_ANTERIOR_FONTANELLE_TENSION,"261");
        getMapping.put(REDIS_KEY_HM_SITE_OF_JAUNDICE,"251");
        getMapping.put(REDIS_KEY_HM_SALT_UPTAKE,"308");
        getMapping.put(REDIS_KEY_HM_FOLLOW_UP_METHOD,"113");
        getMapping.put(REDIS_KEY_HM_HYPOGLYCEMIC_REACTION,"312");
        getMapping.put(REDIS_KEY_HM_PROVINCE,"1");
        getMapping.put(REDIS_KEY_HM_CITY,"2");
        getMapping.put(REDIS_KEY_HM_AREA,"3");
        getMapping.put(REDIS_KEY_HM_TOWN,"4");
        getMapping.put(REDIS_KEY_HM_VILLAGE,"5");
    }
}

