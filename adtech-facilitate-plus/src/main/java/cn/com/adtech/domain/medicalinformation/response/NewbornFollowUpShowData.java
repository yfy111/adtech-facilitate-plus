package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.annotation.DesensitizationProperty;
import cn.com.adtech.annotation.FieldDisplayProperty;
import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import cn.com.adtech.util.CommonVariable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 新生儿随访记录
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:55
 */
@Data
public class NewbornFollowUpShowData extends MedicalInformationShowData {

    @ApiModelProperty(value = "extsid", required = true)
    @FieldDisplayProperty(hidden = true)
    private String extsid;

    @FieldDisplayProperty(hidden = true)
    @ApiModelProperty(value = "extmpi", required = true)
    private String extmpi;

    @ApiModelProperty(value = "健康档案编号", required = true)
    private String hdsd0001001;
    @ApiModelProperty(value = "新生儿姓名", required = true)
    private String hdsd0001212;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_SEX)
    @ApiModelProperty(value = "新生儿性别", required = true)
    private String hdsd0001213;
    @ApiModelProperty(value = "脉率（次/min）", required = true)
    private String hdsd0001049;
    @ApiModelProperty(value = "呼吸频率(次/min)", required = true)
    private String hdsd0001050;
    @ApiModelProperty(value = "体重(kg)", required = true)
    private String hdsd0001056;
    @ApiModelProperty(value = "体温(℃)", required = true)
    private String hdsd0001048;
    @ApiModelProperty(value = "新生儿出生日期", required = true)
    private String hdsd0001214;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_SKIN)
    @ApiModelProperty(value = "皮肤检查结果代码", required = true)
    private String hdsd0001099;
    @ApiModelProperty(value = "家庭访视记录表单编号", required = true)
    @FieldDisplayProperty(width = 5)
    private String hdsd0001211;
    @ApiModelProperty(value = "父亲姓名", required = true)
    private String hdsd0001217;
    @ApiModelProperty(value = "父亲出生日期", required = true)
    private String hdsd0001218;
    @ApiModelProperty(value = "父亲电话号码", required = true)
    private String hdsd0001219;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_OCCUPATION)
    @ApiModelProperty(value = "父亲职业类别代码", required = true)
    private String hdsd0001220;
    @ApiModelProperty(value = "母亲姓名", required = true)
    private String hdsd0001221;
    @ApiModelProperty(value = "母亲出生日期", required = true)
    private String hdsd0001222;
    @ApiModelProperty(value = "母亲电话号码", required = true)
    private String hdsd0001223;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_OCCUPATION)
    @ApiModelProperty(value = "母亲职业类别", required = true)
    private String hdsd0001224;
    @ApiModelProperty(value = "现住地址-省", required = true)
    private String hdsd0001225;
    @ApiModelProperty(value = "现住地址-市", required = true)
    private String hdsd0001226;
    @ApiModelProperty(value = "现住地址-县", required = true)
    private String hdsd0001227;
    @ApiModelProperty(value = "现住地址-乡", required = true)
    @FieldDisplayProperty(width = 5)
    private String hdsd0001228;
    @ApiModelProperty(value = "现住地址-村", required = true)
    @FieldDisplayProperty(width = 5)
    private String hdsd0001229;
    @ApiModelProperty(value = "现住地址门牌号", required = true)
    private String hdsd0001230;
    @ApiModelProperty(value = "出生孕周", required = true)
    private String hdsd0001231;
    @ApiModelProperty(value = "母亲妊娠合并症/并发症史", required = true)
    private String hdsd0001232;
    @ApiModelProperty(value = "助产机构名称", required = true)
    private String hdsd0001233;
    @ApiModelProperty(value = "末次分娩代码", required = true)
    private String hdsd0001234;
    @ApiModelProperty(value = "Apgar评分值(分)", required = true)
    private String hdsd0001237;
    @ApiModelProperty(value = "双多胎标志", required = true)
    private String hdsd0001235;
    @ApiModelProperty(value = "新生儿听力筛查结果代码", required = true)
    private String hdsd0001241;
    @ApiModelProperty(value = "新生儿畸形描述", required = true)
    private String hdsd0001239;
    @ApiModelProperty(value = "出生体重", required = true)
    private String hdsd0001243;
    @ApiModelProperty(value = "出生身长", required = true)
    private String hdsd0001244;
    @ApiModelProperty(value = "喂养方式类别代码", required = true)
    private String hdsd0001245;
    @ApiModelProperty(value = "每天吃奶次数（次/日）", required = true)
    private String hdsd0001246;
    @ApiModelProperty(value = "每天吃奶量(ml/次)", required = true)
    private String hdsd0001247;
    @ApiModelProperty(value = "呕吐标志", required = true)
    private String hdsd0001248;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_NEWBORN_SHIT)
    @ApiModelProperty(value = "新生儿大便性形状代码", required = true)
    private String hdsd0001249;
    @ApiModelProperty(value = "大便次数", required = true)
    private String hdsd0001250;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_NEWBORN_FACE_COLOR)
    @ApiModelProperty(value = "新生儿面色代码", required = true)
    private String hdsd0001251;
    @ApiModelProperty(value = "前囟横径(cm)", required = true)
    private String hdsd0001253;
    @ApiModelProperty(value = "前囟纵径(cm）", required = true)
    private String hdsd0001254;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_NEWBORN_SHIT)
    @ApiModelProperty(value = "前囟张力代码", required = true)
    private String hdsd0001255;
    @ApiModelProperty(value = "眼外观检查异常结果描述", required = true)
    private String hdsd0001257;
    @ApiModelProperty(value = "耳外观检查异常结果描述", required = true)
    private String hdsd0001259;
    @ApiModelProperty(value = "鼻检查异常结果描述", required = true)
    private String hdsd0001261;
    @ApiModelProperty(value = "随访时间")
    private String hdsd0001287;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_NEWBORN_SHIT)
    @ApiModelProperty(value = "疾病筛查项目代码", required = true)
    private String hdsb0103025;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_SITE_OF_JAUNDICE)
    @ApiModelProperty(value = "黄疸部位代码", required = true)
    private String hdsd0001252;
}
