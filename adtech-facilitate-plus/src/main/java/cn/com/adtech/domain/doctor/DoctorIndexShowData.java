package cn.com.adtech.domain.doctor;

import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.medicalinformation.response.BasicInfoShowData;
import cn.com.adtech.domain.medicalinformation.response.MedicalRecordsShowNewData;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description 医生工作站主页所需的显示数据
 * @Author chenguangxue
 * @CreateDate 2018/11/14 11:57
 */
@Data
public class DoctorIndexShowData {

    // 健康档案基本信息的数据
    private BasicInfoShowData basicInfoShowData;

    // 查询条件
    private Map<String, List<DictionaryDetailShowData>> condition;

    // 分页数据
    private List<MedicalRecordsShowNewData> data;
}
