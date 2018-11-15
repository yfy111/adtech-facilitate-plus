package cn.com.adtech.service;

import cn.com.adtech.component.LoginVerifier;
import cn.com.adtech.component.UserinfoService;
import cn.com.adtech.domain.login.LoginSuccessUserinfo;
import cn.com.adtech.domain.question.FeedbackQuestionPo;
import cn.com.adtech.enums.ResponseResultStatus;
import cn.com.adtech.stereotype.ResponseResult;
import cn.com.adtech.stereotype.ResultStatus;
import cn.com.adtech.table.DBFields.FeedbackQuestion;
import cn.com.adtech.table.DBFields.FeedbackType;
import cn.com.adtech.table.DataTables;
import cn.com.adtech.util.IdWorker;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep4;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/11 13:07
 */
@Service
@Transactional
public class FeedbackQuestionService {

    @Autowired
    @Qualifier("primaryJooq")
    private DSLContext jooq;
    @Autowired
    private UserinfoService userinfoService;
    @Autowired
    private LoginVerifier loginVerifier;
    @Autowired
    private HttpServletRequest request;

    private Table<?> questionRecord = DataTables.feedbackQuestion;
    private Table<?> typeRecord = DataTables.feedbackType;

    // 执行添加操作
    public ResponseResult doAddQuestion(FeedbackQuestionPo po) {
        // 添加之前，校验图形验证码是否正确
        ResultStatus resultStatus = loginVerifier.verifyImageCaptcha.apply(request.getSession().getId(), po.getImageCaptcha());
        if (resultStatus.isFail()) {
            return ResponseResult.result(resultStatus);
        }

        IdWorker idWorker = new IdWorker();

        LoginSuccessUserinfo userinfo = userinfoService.loadWebLoginUserinfo();

        String questionId = Long.toString(idWorker.getId());
        Date feedbackTime = new Date(System.currentTimeMillis());

        // 添加反馈问题的记录
        int execute = jooq.insertInto(questionRecord).columns(
                FeedbackQuestion.id, FeedbackQuestion.questionAllType, FeedbackQuestion.questionDesc, FeedbackQuestion.feedbackUserId,
                FeedbackQuestion.feedbackTime
        ).values(questionId, po.getQuestionFullType(), po.getQuestionDesc(), userinfo.getId(), feedbackTime).execute();

        // 添加反馈问题的类型记录
        List<? extends InsertValuesStep4<?, String, String, String, Date>> step4s = po.getQuestionType().stream().map(type -> {
            return jooq.insertInto(typeRecord).columns(
                    FeedbackType.id, FeedbackType.questionId, FeedbackType.questionType, FeedbackType.feedbackTime
            ).values(Long.toString(idWorker.getId()), questionId, type, feedbackTime);
        }).collect(Collectors.toList());
        int[] execute1 = jooq.batch(step4s).execute();

        return ResponseResult.result(ResponseResultStatus.FEEDBACK_QUESTION_SUCCESS);
    }
}
