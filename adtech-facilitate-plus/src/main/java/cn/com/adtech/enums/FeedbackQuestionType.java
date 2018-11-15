package cn.com.adtech.enums;

import lombok.Getter;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/9 21:21
 */
@Getter
public enum FeedbackQuestionType {

    USER_BASIC_INFO("个人基本信息"),
    VACCINATION_REPORT("预防接种报告"),
    HYPERTENSION_SERVICE("高血压患者随访服务"),
    OUTPATIENT_SERVICE("门诊记录"),
    VERIFICATION_REPORT("校验报告"),
    BIRTH_CERTIFICATE("出生医生证明"),
    NEWBORN_FOLLOWUP("新生儿随访记录"),
    DIABETES_SERVICE("2型糖尿病患者随访服务"),
    INSPECTION_REPORT("检查报告"),
    CHINESE_MEDICINE_RECIPE("中药处方"),
    WESTERN_MEDICINE_RECIPE("西药处方"),
    INPATIENT_INDEX("住院病案首页"),
    OTHER("其他"),
    ;

    private final String desc;

    FeedbackQuestionType(String desc) {
        this.desc = desc;
    }
}
