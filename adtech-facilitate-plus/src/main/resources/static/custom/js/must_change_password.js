function submitChangePassword() {
    $.ajax({
        url: '/password/first-change',
        method: 'post',
        data: JSON.stringify({
            identity: $('.identity-input').val(),
            newPassword: $('.newPassword-input').val(),
            confirmPassword: $('.confirmPassword-input').val()
        }),
        contentType: 'application/json',
        success: function (data) {
            if (data.success) {
                // 成功的情况下，跳转到主页
                location.href = '/web/index/mainIndex'
            } else {
                layer.alert(data.message);
            }
        }
    });
}