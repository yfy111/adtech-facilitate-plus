package cn.com.adtech.domain.family.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class FamilyMemberListShowData {

    @ApiModelProperty(value = "家庭成员/授权管理列表数据",required = true)
    private List<FamilyMemberShowData> familyMemberShowDataList;
}
