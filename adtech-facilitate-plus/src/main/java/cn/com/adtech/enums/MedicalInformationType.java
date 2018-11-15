package cn.com.adtech.enums;

import cn.com.adtech.stereotype.Descriptive;
import lombok.Getter;

/**
 * @Description 主页左侧医疗信息的类型
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:21
 */
@Getter
public enum MedicalInformationType implements Descriptive {

    BASIC_INFO("基本信息"),
    BIRTH_CERTIFICATE("出生医学证明"),
    VACCINATION("预防接种"),
    NEWBORN_FOLLOW_UP("新生儿随访记录"),
    HYPERTENSION_FOLLOW_UP("高血压随访"),
    DIABETES_FOLLOW_UP("糖尿病随访"),

    // 就诊记录暂时未放在这里
    ;

    // 获取对应的html资源名称
    public String html() {
        return this.name().toLowerCase();
    }

    private final String desc;

    MedicalInformationType(String desc) {
        this.desc = desc;
    }
}
