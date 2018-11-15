package cn.com.adtech.domain.family.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 家庭成员/授权管理查询参数
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:31
 */
@Data
public class FamilyMemberParameter {
    @ApiModelProperty(value="标识(F:标识家庭成员，A：授权管理)",required = true)
    private String memberIden;

}
