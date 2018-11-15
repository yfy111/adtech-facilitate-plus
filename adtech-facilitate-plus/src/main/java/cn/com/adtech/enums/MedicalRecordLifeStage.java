package cn.com.adtech.enums;

import lombok.Getter;

/**
 * @Description 就诊记录的生命周期
 * @Author chenguangxue
 * @CreateDate 2018/11/9 16:10
 */
@Getter
public enum MedicalRecordLifeStage {

    CHILDHOOD("童年"),
    JUVENILE("少年"),
    YOUTH("青年"),
    MIDDLEAGE("中年"),
    OLD("老年"),
    ;

    private final String code;
    private final String desc;

    MedicalRecordLifeStage(String desc) {
        this.code = this.name().toLowerCase();
        this.desc = desc;
    }
}
