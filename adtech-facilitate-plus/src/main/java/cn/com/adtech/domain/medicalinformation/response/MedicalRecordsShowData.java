package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.annotation.DesensitizationIdentity;
import cn.com.adtech.annotation.FieldDisplayProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class MedicalRecordsShowData {

    @ApiModelProperty(value = "年龄", required = true)
    @FieldDisplayProperty(hidden = true)
    private Integer age;

    @ApiModelProperty(value = "extmpi", required = true)
    @FieldDisplayProperty(hidden = true)
    private String extmpi;

    @ApiModelProperty(value = "创建时间", required = true)
    private String createdate;

    @ApiModelProperty(value = "姓名", required = true)
    private String username;

    @ApiModelProperty(value = "身份证号", required = true)
    @DesensitizationIdentity
    private String exhdsd0202249;

    @ApiModelProperty(value = "extsid:主外关联字段", required = true)
    @FieldDisplayProperty(hidden = true)
    private String extsid;

    @ApiModelProperty(value = "机构编码", required = true)
    private String extcc;

    @ApiModelProperty(value = "extid", required = true)
    @FieldDisplayProperty(hidden = true)
    private String extid;

    @ApiModelProperty(value = "就诊类型")
    private String bltype;
}
