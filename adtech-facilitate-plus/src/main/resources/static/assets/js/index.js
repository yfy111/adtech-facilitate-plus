$(function () {
    // 加载家庭成员简表
    queryFamilyMemberSimpleList();

    // 加载授权列表简表
    queryAuthoriseMemberSimpleList();

    // 加载医疗基本信息的日期列表
    queryMedicalInformationBasicInfoDate();
});

//出生医学证明点击
$(".icon-show_view_birth").click(function () {
    // var data = {"appellation": "本人", "identityCard": "111", "realName": "111"};
    var selectedIdentity = $('#selectedUserIdentity').val();
    var selectedUserIdAppellation = $('#selectedUserIdAppellation').val();
    $.ajax({
        url: '/web/medical-information/birth-certificate-data',
        method: 'get',
        data: {
            identityCard: selectedIdentity,
            appellation: selectedUserIdAppellation
        },
        success: function (data) {
            $('.birth-certificate-data').html(data);
        }
    })
});

//预防接种点击
$(".icon-show_view_inoculation").click(function () {
    var idCard = $("#selectedUserIdentity").val();
    queryVaccinationList(idCard, 1);
});

//新生儿随访点击
$(".icon-show_view_new_birth").click(function () {
    queryNewbornFollowupDate();
});

//高血压随访点击
$(".icon-show_view_birth_hypertension").click(function () {
    queryMedicalInformationHypertensionDate();
});

//糖尿病随访点击
$(".icon-show_view_birth_diabetes").click(function () {
    queryMedicalInformationDiabetesDate();
});

//就诊记录点击
$(".icon-show_view_birth_medical").click(function () {
    var identity = $("#selectedUserIdentity").val();
    queryMedicalRecordCondition(identity);
});

// 显示修改密码页面
function showChangePassword() {
    $.ajax({
        url: '/web/index/change-password',
        method: 'get',
        success: function (data) {
            layer.open({
                content: data,
                title: '修改查询密码',
                area: ['800px'],
                type: 1
            });
        }
    });
}

// 执行修改密码操作
function doChangePassword() {
    $.ajax({
        url: '/web/password/change',
        method: 'post',
        data: $('.change-password-form').serialize(),
        success: function (data) {
            if (data.success) {
                layer.msg(data.message);
                layer.closeAll();
            } else {
                layer.msg(data.message);
            }
        }
    });

}

// 点击问题反馈，显示添加页面
function showFeedbackQuestionAdd() {
    $.ajax({
        url: '/web/feedback/add',
        method: 'get',
        success: function (data) {
            layer.open({
                type: 1,
                title: '您的宝贵意见将有助于提升我市整体健康档案及电子病历质量，请准确描述，谢谢！',
                area: ['800px'],
                shadeClose: false,
                content: data,
                btn: []
            });
        }
    });
}


// 退出系统
function logout() {
    $.ajax({
        url: '/web/index/logout',
        method: 'post',
        success: function (data) {
            layer.msg(data.message);
            location.href = '/login?appId=1';
        }
    });
}