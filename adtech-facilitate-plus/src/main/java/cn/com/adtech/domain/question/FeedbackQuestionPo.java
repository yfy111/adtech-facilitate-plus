package cn.com.adtech.domain.question;

import lombok.Data;

import java.util.List;

/**
 * @Description 问题反馈的pojo
 * @Author chenguangxue
 * @CreateDate 2018/11/11 13:05
 */
@Data
public class FeedbackQuestionPo {

    private List<String> questionType;
    private String questionDesc;
    private String imageCaptcha;

    // 获取反馈问题的完整类型，将类型合并起来
    public String getQuestionFullType() {
        if (questionType != null && !questionType.isEmpty()) {
            return String.join(",", questionType);
        }
        else {
            return "-";
        }
    }
}
