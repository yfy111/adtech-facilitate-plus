var use = layui.use("element", function () {
    var element = layui.element;
});

// 获取短信验证码
function sendSmsCaptcha() {
    var name = $('.smsCaptcha-login-container .imglabel-user').val();
    var identity = $('.smsCaptcha-login-container .imglabel-ID').val();
    var phone = $('.smsCaptcha-login-container .imglabel-phone').val();

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

// 复选框和提交按钮的关联操作
function controlSubmitButton(input, button) {
    if (!$(input).prop('checked')) {
        $(button).prop('disabled', true);
        $(button).addClass('disabled-button');
    } else {
        $(button).prop('disabled', false);
        $(button).removeClass('disabled-button');
    }
}

// 切换图形验证码
function changeImageCaptcha2() {
    $('#captchaImg').attr('src', '/captcha/image2?s='+Math.random());
   // $('.password-login-container .image-captcha-img').attr('src', '/captcha/image2');
}

// 以短信验证码方式进行登录验证
function smsCaptchaLogin() {
    var name = $('.smsCaptcha-login-container .imglabel-user').val();
    var identity = $('.smsCaptcha-login-container .imglabel-ID').val();
    var phone = $('.smsCaptcha-login-container .imglabel-phone').val();
    var captcha = $('.smsCaptcha-login-container .imglabel-code').val();

    if (name.trim().length === 0 || identity.trim().length === 0 || phone.trim().length === 0) {
        layer.msg("请先完善个人信息");
        return;
    }
    if (captcha.trim().length === 0) {
        layer.msg("请输入短信验证码");
        return;
    }

    var appId = GetUrlParam("appId");
    $.ajax({
        url: '/rest/identity-sms-captcha-login',
        method: 'post',
        contentType: 'application/json',
        data: JSON.stringify({
            realName: name,
            identityCard: identity,
            userPhone: phone,
            smsCaptcha: captcha,
            appId: appId,
            institutionCode: 'test'
        }),
        success: function (data) {
            if (!data.success) {
                layer.msg(data.message);
            }

            // 如果是初次登录，则显示修改密码的页面
            if (data.code === 'IS_FIRST_LOGIN') {
                $.ajax({
                    url: '/must-change-password',
                    method: 'get',
                    data: {
                        identity: identity
                    },
                    success: function (data) {
                        layer.open({
                            type: 1,
                            content: data,
                            btn: [],
                            resize: false,
                            title: '首次访问系统，请先设置查询密码',
                            // closeBtn: 0,
                            cancel: function () {
                                layer.confirm("如果不设置查询密码，则无法访问本系统，确认放弃吗？", function (index) {
                                    layer.closeAll();
                                });
                                return false;
                            }
                        })
                    }
                });
            }
            else if (data.success) {
                // 如果是登录验证成功，则跳转到主页
                location.href = '/web/index/mainIndex';
            }
        }
    });
}

// 以姓名、身份证、密码方式登录
function passwordLogin() {
    var name = $('.password-login-container .imglabel-user').val();
    var identity = $('.password-login-container .imglabel-ID').val();
    var password = $('.password-login-container .imglabel-password').val();
    if (name.trim().length === 0 || identity.trim().length === 0 || password.trim().length === 0) {
        layer.msg("请输入完整的登录信息");
        return false;
    }

    var captcha = $('.password-login-container .image-captcha-code').val();
    if (captcha.trim().length === 0) {
        layer.msg('请输入验证码');
        return false;
    }

    $.ajax({
        url: '/rest/identity-password-login',
        method: 'post',
        contentType: 'application/json',
        data: JSON.stringify({
            realName: name,
            identityCard: identity,
            password: password,
            imageCaptcha: captcha
        }),
        success: function (data) {
            // 如果是初次登录，则显示修改密码的页面
            if (data.code === 'IS_FIRST_LOGIN') {
                $.ajax({
                    url: '/must-change-password',
                    method: 'get',
                    data: {
                        identity: identity
                    },
                    success: function (data) {
                        layer.open({
                            type: 1,
                            content: data,
                            btn: [],
                            resize: false,
                            title: '首次访问系统，请先设置查询密码',
                            // closeBtn: 0,
                            cancel: function () {
                                layer.confirm("如果不设置查询密码，则无法访问本系统，确认放弃吗？", function (index) {
                                    layer.closeAll();
                                });
                                return false;
                            }
                        })
                    }
                });
            }
            if (data.success) {
                // 如果是登录验证成功，则跳转到主页
                location.href = '/web/index/mainIndex';
            } else {
                layer.msg(data.message);
            }
        }
    });
}

function GetUrlParam(paraName) {
    var url = document.location.toString();
    var arrObj = url.split("?");

    if (arrObj.length > 1) {
        var arrPara = arrObj[1].split("&");
        var arr;

        for (var i = 0; i < arrPara.length; i++) {
            arr = arrPara[i].split("=");

            if (arr != null && arr[0] == paraName) {
                return arr[1];
            }
        }
        return "";
    }
    else {
        return "";
    }
}