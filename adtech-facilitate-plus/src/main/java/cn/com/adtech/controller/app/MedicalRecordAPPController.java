package cn.com.adtech.controller.app;

import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.medicalinformation.request.IdentityCardParameter;
import cn.com.adtech.domain.medicalinformation.request.MedicalRecordsParameter;
import cn.com.adtech.domain.medicalinformation.request.medicalRecords.*;
import cn.com.adtech.domain.medicalinformation.response.*;
import cn.com.adtech.domain.po.Page;
import cn.com.adtech.service.MedicalRecordService;
import cn.com.adtech.stereotype.ResponseResult;
import cn.com.adtech.stereotype.ResultStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Description 就诊记录
 * @Author chenguangxue
 * @CreateDate 2018/11/2 17:06
 */
@Api(value="/app/medical-record", tags = "app就诊记录主页")
@Controller
@RequestMapping(value = "/app/medical-record")
public class MedicalRecordAPPController {

    @Autowired
    private MedicalRecordService medicalRecordService;
    /**
     * 就诊记录主页
     * @return
     */
    @ApiOperation(value="就诊记录主页",notes = "显示就诊记录记录信息")
    @PostMapping(value = "/table")
    @ResponseBody
    public ResponseResult<Page<MedicalRecordsShowNewData>> showIndex(@RequestBody MedicalRecordsParameter medicalRecordsParameter) {
//        Page<MedicalRecordsShowData> page = medicalRecordService.showIndex(medicalRecordsParameter);
         Page<MedicalRecordsShowNewData> page = medicalRecordService.showIndexNew(medicalRecordsParameter);
        return ResponseResult.result(ResultStatus.success()).data(page);
    }

    /**
     * 就诊记录主页条件加载
     * @param identityCardParameter
     * @return
     */
    @ApiOperation(value="就诊记录主页条件加载",notes = "就诊记录主页条件加载")
    @PostMapping(value = "/parameterLoad")
    @ResponseBody
    public ResponseResult<Map<String,List<DictionaryDetailShowData>>> parameterLoad(@RequestBody IdentityCardParameter identityCardParameter) {
        return ResponseResult.result(ResultStatus.success()).data(medicalRecordService.parameterLoad(identityCardParameter));
    }


    /**
     * 门急诊弹框
     * @return
     */
    @ApiOperation(value="门急诊弹框",notes = "门急诊弹框")
    @PostMapping(value = "/emergencyDoor")
    @ResponseBody
    public ResponseResult<List<EmerencyDoorShowData>> emergencyDoor(@RequestBody EmergencyDoorMedicalRecordParameter doorMedicalRecordParameter) {
        return ResponseResult.result(ResultStatus.success()).data(medicalRecordService.emergencyDoor(doorMedicalRecordParameter));
    }

    /**
     * 住院病案首页弹框
     * @return
     */
    @ApiOperation(value="住院病案首页弹框",notes = "住院病案首页弹框")
    @PostMapping(value = "/hospitalization")
    @ResponseBody
    public ResponseResult<HospitalizationShowData> hospitalization(@RequestBody HospitalizationParameter hospitalizationParameter) {
        return ResponseResult.result(ResultStatus.success()).data(medicalRecordService.hospitalization(hospitalizationParameter));
    }



    /**
     * 检查记录项目加载
     * @return
     */
    @ApiOperation(value="检查记录项目加载",notes = "检查记录项目加载")
    @PostMapping(value = "/findCheckRecordName")
    @ResponseBody
    public ResponseResult<List<DictionaryDetailShowData>> findCheckRecordName(@RequestBody List<String> extsid) {
        return ResponseResult.result(ResultStatus.success()).data(medicalRecordService.findCheckRecordName(extsid));
    }




    /**
     * 检查记录弹窗
     * @return
     */
    @ApiOperation(value="检查记录弹窗",notes = "检查记录弹窗")
    @PostMapping(value = "/checkRecord")
    @ResponseBody
    public ResponseResult<CheckRecordShowData> checkRecord(@RequestBody CheckRecordParameter checkRecordParameter) {
        return ResponseResult.result(ResultStatus.success()).data(medicalRecordService.checkRecord(checkRecordParameter));
    }


    /**
     * 检验记录项目加载
     * @return
     */
    @ApiOperation(value="检验记录项目加载",notes = "检验记录项目加载")
    @PostMapping(value = "/findInspectionRecordName")
    @ResponseBody
    public ResponseResult<List<DictionaryDetailShowData>> findInspectionRecordName(@RequestBody List<String> extsid) {
        return ResponseResult.result(ResultStatus.success()).data(medicalRecordService.findInspectionRecordName(extsid));
    }

    /**
     * 检验记录弹框
     * @return
     */
    @ApiOperation(value="检验记录弹框",notes = "检验记录弹框")
    @PostMapping(value = "/inspectionRecord")
    @ResponseBody
    public ResponseResult<InspectionRecordShowData> inspectionRecord(@RequestBody InspectionRecordParameter inspectionRecordParameter) {
        return ResponseResult.result(ResultStatus.success()).data(medicalRecordService.inspectionRecord(inspectionRecordParameter));
    }


    /**
     * 中药处方弹窗
     * @return
     */
    @ApiOperation(value="中药处方弹窗",notes = "中药处方弹窗")
    @PostMapping(value = "/chineseMedicine")
    @ResponseBody
    public ResponseResult<ChineseMedicineShowData> chineseMedicine(@RequestBody ChineseMedicineParameter chineseMedicineParameter) {
        return ResponseResult.result(ResultStatus.success()).data(medicalRecordService.chineseMedicine(chineseMedicineParameter));
    }

    /**
     * 西药处方弹窗
     * @return
     */
    @ApiOperation(value="西药处方弹窗",notes = "西药处方弹窗")
    @PostMapping(value = "/westernMedicine")
    @ResponseBody
    public ResponseResult<WesternMedicineShowData> westernMedicine(@RequestBody WesternPrescriptionParameter westernPrescriptionParameter) {
        return ResponseResult.result(ResultStatus.success()).data(medicalRecordService.westernMedicine(westernPrescriptionParameter));
    }

}
