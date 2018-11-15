package cn.com.adtech.controller.web;

import cn.com.adtech.domain.question.FeedbackQuestionPo;
import cn.com.adtech.enums.FeedbackQuestionType;
import cn.com.adtech.service.FeedbackQuestionService;
import cn.com.adtech.stereotype.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/9 21:30
 */
@Controller
@RequestMapping(value = "/web/feedback")
public class FeedbackQuestionController {

    @Autowired
    private FeedbackQuestionService feedbackQuestionService;

    // 显示问题反馈的添加页面
    @GetMapping(value = "/add")
    public String showFeedbackQuestionAdd(Model model) {
        model.addAttribute("types", FeedbackQuestionType.values());
        return "question_feedback/question_feedback_add";
    }

    // 执行反馈问题的添加操作
    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseResult doFeedbackQuestionAdd(FeedbackQuestionPo po) {
        return feedbackQuestionService.doAddQuestion(po);
    }
}
