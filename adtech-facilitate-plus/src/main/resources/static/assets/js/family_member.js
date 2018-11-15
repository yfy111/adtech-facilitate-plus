// 查询家庭成员的简表页面
function queryFamilyMemberSimpleList() {
    $.ajax({
        url: '/web/index/family-member-simple-list',
        method: 'get',
        data: {},
        success: function (data) {
            $('.family-member-simple-list').html(data);
        }
    });
}

// 查询家庭成员的完整页面
function showFamilyMemberFullList() {
    $.ajax({
        url: '/web/index/family-member-full-list',
        method: 'post',
        success: function (data) {
            layer.open({
                type: 1,
                area: ['800px'],
                title: '家庭成员',
                content: data,
                btn: []
            });
        }
    });
}

// 切换当前选中用户
function changeUser(a) {
    // 关闭当前的弹窗页面
    layer.closeAll();
    // 获取选中的用户id，切换主页的显示信息
    var info = $(a).attr("value").split('-');
    $.ajax({
        url: '/web/index/change-userinfo',
        method: 'post',
        data: {
            userId: info[0]
        },
        success: function (data) {
            // 改变显示的人物信息
            $('.current-selected-user-name').html(data.data.name);
            $('.current-selected-user-appellation').html(info[1]);

            // 改变隐藏的当前用户的身份证信息
            $('#selectedUserIdentity').val(data.data.identity);
            $("#selectedUserIdAppellation").val(info[1]);
            $("#selectedUserMPI").val(info[2]);

            // 查询医疗档案基本信息，触发点击事件
            $('.icon-show_view_base').trigger('click');
            queryMedicalInformationBasicInfoDate();
        }
    });
}

// 发送短信验证码
function sendFamilyMemberAddSmsCaptcha() {
    var name = $('.family-member-add-form .name').val();
    var identity = $('.family-member-add-form .identity').val();
    var phone = $('.family-member-add-form .phone').val();

    if (name.trim().length === 0 || identity.trim().length === 0 || phone.trim().length === 0) {
        layer.alert("请先完善个人信息");
        return;
    }

    $.ajax({
        url: '/captcha/sms',
        method: 'post',
        contentType: 'application/json',
        data: JSON.stringify({
            realName: name,
            identity: identity,
            phone: phone
        }),
        success: function (data) {
            layer.msg(data.message);
        }
    });
}

// 显示新增家庭成员页面
function showFamilyMemberAdd() {
    $.ajax({
        url: '/web/family-member/add',
        method: 'get',
        success: function (data) {
            layer.open({
                type: 1,
                content: data,
                area: ['800px'],
                btn: [],
                title: '新增家庭成员'
            });
        }
    });
}

// 执行家庭成员的添加操作
function doFamilyMemberAdd() {
    $.ajax({
        url: '/web/family-member/add',
        method: 'post',
        data: $('.family-member-add-form').serialize(),
        success: function (data) {
            layer.msg(data.message);
        }
    });
}

// 删除家庭成员
function deleteFamilyMember(a) {
    layer.confirm("确认要删除这个家庭成员吗？", function () {
        // 获取待删除用户的id
        var userId = $(a).attr("value");

        $.ajax({
            url: '/web/index/delete-family-member',
            method: 'post',
            data: {
                ids: userId,
                memberIden: 'F'
            },
            success: function (data) {
                layer.msg(data.message);
            }
        });
    });
}