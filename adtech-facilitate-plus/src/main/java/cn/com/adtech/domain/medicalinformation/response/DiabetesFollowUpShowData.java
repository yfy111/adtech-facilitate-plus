package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.annotation.DesensitizationProperty;
import cn.com.adtech.annotation.FieldDisplayProperty;
import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import cn.com.adtech.util.CommonVariable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 糖尿病随访记录的显示数据
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:57
 */
@Data
public class DiabetesFollowUpShowData extends MedicalInformationShowData {

    @FieldDisplayProperty(hidden = true)
    @ApiModelProperty(value = "extmpi",required = true)
    private String extmpi;
    @FieldDisplayProperty(hidden = true)
    @ApiModelProperty(value = "extsid",required = true)
    private String extsid;
    @ApiModelProperty(value = "健康档案编号",required = true)
    private String hdsd0001001;
    @ApiModelProperty(value = "患者姓名",required = true)
    private String hdsd0001002;
    @ApiModelProperty(value = "身高(cm)",required = true)
    private String hdsd0001055;
    @ApiModelProperty(value = "体重(kg)",required = true)
    private String hdsd0001056;
    @ApiModelProperty(value = "体质指数",required = true)
    private String hdsd0001058;
    @ApiModelProperty(value = "运动时长(min)",required = true)
    private String hdsd0001066;
    @ApiModelProperty(value = "日吸烟量(支)",required = true)
    private String hdsd0001071;
    @ApiModelProperty(value = "日饮酒量(两)",required = true)
    private String hdsd0001075;
    @ApiModelProperty(value = "空腹血糖值(mmol/L)",required = true)
    private String hdsd0001148;
    @FieldDisplayProperty(titleWidth = 2)
    @ApiModelProperty(value = "餐后两小时血糖值(mmol/L)",required = true)
    private String hdsd0001149;
    @FieldDisplayProperty(titleWidth = 2)
    @ApiModelProperty(value = "糖化血红蛋白值(%)",required = true)
    private String hdsd0001153;
    @ApiModelProperty(value = "目标体重(kg)",required = true)
    private String hdsd0001209;
    @ApiModelProperty(value = "随访医师姓名",required = true)
    private String hdsd0001330;
    @ApiModelProperty(value = "本次随访日期",required = true)
    private String hdsd0001466;
    @ApiModelProperty(value = "收缩压(mmHg)",required = true)
    private String hdsd0001342;
    @ApiModelProperty(value = "舒张压(mmHg)",required = true)
    private String hdsd0001343;
    @ApiModelProperty(value = "辅助检查项目",required = true)
    private String hdsd0001478;
    @ApiModelProperty(value = "辅助检查结果",required = true)
    private String hdsd0001460;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_FOLLOW_UP_METHOD)
    @ApiModelProperty(value = "随访方式代码",required = true)
    private String hdsd0001467;
    @ApiModelProperty(value = "其他阳性体征",required = true)
    private String hdsd0001469;
    @ApiModelProperty(value = "检查(测)人员姓名",required = true)
    private String hdsd0001479;
    @ApiModelProperty(value = "药物不良反应描述",required = true)
    private String hdsd0001481;
    @ApiModelProperty(value = "下次随访日期",required = true)
    private String hdsd0001483;
    @ApiModelProperty(value = "日主食量(g)",required = true)
    private String hdsd0001486;
    @ApiModelProperty(value = "目标日主食量(g)",required = true)
    private String hdsd0001487;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_HYPOGLYCEMIC_REACTION)
    @ApiModelProperty(value = "低血糖反应代码",required = true)
    private String hdsd0001488;
    @ApiModelProperty(value = "胰岛素用药种类",required = true)
    private String hdsd0001489;
    @ApiModelProperty(value = "胰岛素用药使用频率(次/d)",required = true)
    private String hdsd0001490;
    @ApiModelProperty(value = "胰岛素用药次剂量(U)",required = true)
    private String hdsd0001491;
    @ApiModelProperty(value = "症状名称",required = true)
    private String hdsd0001047;
    @ApiModelProperty(value = "药物名称",required = true)
    private String hdsd0001194;
    @ApiModelProperty(value = "药物使用频率",required = true)
    private String hdsd0001195;
    @ApiModelProperty(value = "药物使用剂量单位",required = true)
    private String hdsd0001196;
    @ApiModelProperty(value = "药物使用次剂量",required = true)
    private String hdsd0001197;
    @ApiModelProperty(value = "药物使用总剂量",required = true)
    private String hdsd0001198;

}
