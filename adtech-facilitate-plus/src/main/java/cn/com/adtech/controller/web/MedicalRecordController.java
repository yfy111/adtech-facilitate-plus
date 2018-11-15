package cn.com.adtech.controller.web;

import cn.com.adtech.domain.dictionary.DictionaryDetailShowData;
import cn.com.adtech.domain.medicalinformation.request.IdentityCardParameter;
import cn.com.adtech.domain.medicalinformation.request.MedicalRecordsParameter;
import cn.com.adtech.domain.medicalinformation.request.medicalRecords.*;
import cn.com.adtech.domain.medicalinformation.response.MedicalRecordsShowData;
import cn.com.adtech.domain.medicalinformation.response.MedicalRecordsShowNewData;
import cn.com.adtech.domain.po.Page;
import cn.com.adtech.enums.MedicalRecordLifeStage;
import cn.com.adtech.service.MedicalRecordService;
import cn.com.adtech.stereotype.ResponseResult;
import cn.com.adtech.stereotype.ResultStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @Description 就诊记录
 * @Author chenguangxue
 * @CreateDate 2018/11/2 17:06
 */
@Api(value = "/web/medical-record", tags = "web就诊记录主页")
@Controller
@RequestMapping(value = "/web/medical-record")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * 就诊记录主页
     *
     * @return
     */
    @ApiOperation(value = "就诊记录主页", notes = "显示就诊记录记录信息")
    @PostMapping(value = "/index")
    public ModelAndView showIndex(@RequestBody MedicalRecordsParameter medicalRecordsParameter) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("page", medicalRecordService.showIndex(medicalRecordsParameter));
        return modelAndView;
    }

    /**
     * 就诊记录主页条件加载
     *
     * @param identityCardParameter
     * @return
     */
    @ApiOperation(value = "就诊记录主页条件加载", notes = "就诊记录主页条件加载")
    @PostMapping(value = "/parameterLoad")
    public ModelAndView parameterLoad(@RequestBody IdentityCardParameter identityCardParameter) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("obj", medicalRecordService.parameterLoad(identityCardParameter));
        return modelAndView;
    }

    @PostMapping(value = "/condition")
    public String showMedicalRecordCondition(IdentityCardParameter parameter, Model model) {
        Map<String, List<DictionaryDetailShowData>> data = medicalRecordService.parameterLoad(parameter);
        model.addAttribute("condition", data);

        // 对生命周期进行处理
        model.addAttribute("life", MedicalRecordLifeStage.values());

        return "medical_record/medical_record_condition";
    }

    @PostMapping(value = "/list")
    public String showMedicalRecordList(MedicalRecordsParameter parameter, Model model) {
        Page<MedicalRecordsShowNewData> page = medicalRecordService.showIndexNew(parameter);
        model.addAttribute("page", page);
        return "medical_record/medical_record_list";
    }

    /**
     * 就诊记录主页
     *
     * @return
     */
    @ApiOperation(value = "就诊记录主页", notes = "显示就诊记录记录信息")
    @PostMapping(value = "/table")
    @ResponseBody
    public ResponseResult<Page<MedicalRecordsShowData>> table(@RequestBody MedicalRecordsParameter medicalRecordsParameter) {
        return ResponseResult.result(ResultStatus.success()).data(medicalRecordService.showIndex(medicalRecordsParameter));
    }

    /**
     * 门急诊弹框
     *
     * @return
     */
    @ApiOperation(value = "门急诊弹框", notes = "门急诊弹框")
    @PostMapping(value = "/emergencyDoor")
    @ResponseBody
    public ModelAndView emergencyDoor(@RequestBody EmergencyDoorMedicalRecordParameter doorMedicalRecordParameter) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("medical_record/emergencyDoor");
        modelAndView.addObject("obj", medicalRecordService.emergencyDoor(doorMedicalRecordParameter));
        return modelAndView;
    }

    /**
     * 住院病案首页弹框
     *
     * @return
     */
    @ApiOperation(value = "住院病案首页弹框", notes = "住院病案首页弹框")
    @PostMapping(value = "/hospitalization")
    @ResponseBody
    public ModelAndView hospitalization(@RequestBody HospitalizationParameter hospitalizationParameter) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("medical_record/hospitalization");
        modelAndView.addObject("obj", medicalRecordService.hospitalization(hospitalizationParameter));
        return modelAndView;
    }


    /**
     * 检查记录弹窗
     *
     * @return
     */
    @ApiOperation(value = "检查记录弹窗", notes = "检查记录弹窗")
    @PostMapping(value = "/checkRecord")
    @ResponseBody
    public ModelAndView checkRecord(@RequestBody CheckRecordParameter checkRecordParameter) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("medical_record/checkRecord");
        modelAndView.addObject("info", medicalRecordService.checkRecord(checkRecordParameter));
        return modelAndView;
    }

    /**
     *  检查项目加载
     * @return
     */
    @ApiOperation(value="",notes = "检查项目加载")
    @PostMapping(value = "/findCheckRecordName")
    @ResponseBody
    public ModelAndView findCheckRecordName(@RequestBody List<String> extsidList) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("medical_record/common_list");
        modelAndView.addObject("date",medicalRecordService.findCheckRecordName(extsidList));
        return modelAndView;
    }

    /**
     * 检验记录弹框
     *
     * @return
     */
    @ApiOperation(value = "检验记录弹框", notes = "检验记录弹框")
    @PostMapping(value = "/inspectionRecord")
    @ResponseBody
    public ModelAndView inspectionRecord(@RequestBody InspectionRecordParameter inspectionRecordParameter) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("medical_record/inspectionRecord");
        modelAndView.addObject("info", medicalRecordService.inspectionRecord(inspectionRecordParameter));
        return modelAndView;
    }


    /**
     *  检验记录项目加载
     * @return
     */
    @ApiOperation(value="检验记录项目加载",notes = "检验记录项目加载")
    @PostMapping(value = "/findInspectionRecordName")
    @ResponseBody
    public ModelAndView findInspectionRecordName(@RequestBody List<String> extsidList) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("medical_record/common_list");
        modelAndView.addObject("date",medicalRecordService.findInspectionRecordName(extsidList));
        return modelAndView;
    }

    /**
     * 中药处方弹窗
     *
     * @return
     */
    @ApiOperation(value = "中药处方弹窗", notes = "中药处方弹窗")
    @PostMapping(value = "/chineseMedicine")
    @ResponseBody
    public ModelAndView chineseMedicine(@RequestBody ChineseMedicineParameter chineseMedicineParameter) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("medical_record/chineseMedicine");
        modelAndView.addObject("info", medicalRecordService.chineseMedicine(chineseMedicineParameter));
        return modelAndView;
    }

    /**
     * 西药处方弹窗
     *
     * @return
     */
    @ApiOperation(value = "西药处方弹窗", notes = "西药处方弹窗")
    @PostMapping(value = "/westernMedicine")
    @ResponseBody
    public ModelAndView westernMedicine(@RequestBody WesternPrescriptionParameter westernPrescriptionParameter) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("medical_record/westernMedicine");
        modelAndView.addObject("info", medicalRecordService.westernMedicine(westernPrescriptionParameter));
        return modelAndView;
    }

}


