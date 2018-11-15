package cn.com.adtech.service;

import cn.com.adtech.domain.doctor.DoctorIndexShowData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 医生工作站的服务
 * @Author chenguangxue
 * @CreateDate 2018/11/13 19:36
 */
@Service
@Transactional
public class DoctorService {

    @Autowired
    private MedicalRecordService medicalRecordService;

    // 获取医生工作站主页的数据
    public DoctorIndexShowData getDoctorIndexShowData() {
        return null;
    }
}
