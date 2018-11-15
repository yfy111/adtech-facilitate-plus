function closeAddQuestion() {
    layer.closeAll();
}

function changeImageCaptcha() {
    $('#feedback-question-add-form .image-captcha-img').attr('src', '/captcha/image2');
}

function submitFeedbackQuestion() {
    // 校验数据是否完整
    var trueCount = 0;
    var questionTypes = $('#feedback-question-add-form .question-type');
    for (var i = 0; i < questionTypes.length; i++) {
        var checked = $(questionTypes[i]).prop('checked');
        if (checked) {
            trueCount++;
        }
    }
    if (trueCount === 0) {
        parent.layer.msg("请选择至少一种问题类型");
        return false;
    }

    var questionDesc = $('#feedback-question-add-form .question-desc').val().trim();
    if (questionDesc.length === 0) {
        parent.layer.msg("请输入反馈问题的问题描述");
        return false;
    }

    $.ajax({
        url: '/web/feedback/add',
        method: 'post',
        data: $('#feedback-question-add-form').serialize(),
        success: function (data) {
            parent.layer.msg(data.message);

            if (data.success) {
                $('#feedback-question-add-form')[0].reset();
            }
        }
    });
}