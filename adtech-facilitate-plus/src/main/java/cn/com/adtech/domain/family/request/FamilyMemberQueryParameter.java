package cn.com.adtech.domain.family.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 家庭成员基本信息查看
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:31
 */
@Data
public class FamilyMemberQueryParameter {

    @ApiModelProperty(value = "用户ID", required = true)
    private String userId;

    @ApiModelProperty(value = "标识(F:标识家庭成员，A:授权管理)", required = true)
    private String memberIden;

    @ApiModelProperty(value = "数据条数")
    private String pageSize;
}
