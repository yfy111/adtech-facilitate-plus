package cn.com.adtech.domain.medicalinformation.response;

import lombok.Data;

import java.util.List;

/**
 * 检验记录
 */
@Data
public class InspectionRecordShowData {

    //基本信息
    private InspectionRecordBaseShowData inspectionRecordBaseShowData;

    //检查项目参考
    private List<InspectionRecordItemListShowData> inspectionRecordItemListShowData;

}
