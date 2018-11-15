package cn.com.adtech.domain.family.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 删除家庭成员/取消授权
 * @Author PYH
 * @CreateDate 2018/11/6 16:31
 */
@Data
public class FamilyMemberDelParameter {
    @ApiModelProperty(value="拼接的家庭成员主键ID，以逗号分隔",required = true)
    private String ids;
    @ApiModelProperty(value="标识(F:标识家庭成员，A：授权管理)",required = true)
    private String memberIden;
}
