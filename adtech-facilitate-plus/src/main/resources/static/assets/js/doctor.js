// 显示页面之后的轮询操作
var serialNumber = null;

$(function () {
    // 加载页面的时候，向服务器申请一个二维码的序列号，以此序列号为编号向服务器申请二维码
    generateQRCode();

    // 生成二维码之后，开始执行轮询操作
    polling();
});

// 生成二维码
function generateQRCode() {
    $.ajax({
        url: '/captcha/qr-code-serial-number',
        method: 'post',
        success: function (data) {
            serialNumber = data.data;

            // 以这个序列号向服务器申请一个二维码
            $('.doctor-scan-qr-code-img').attr('src', '/captcha/qr-code?serialNumber=' + serialNumber);
        }
    });
}

// 轮询请求二维码的处理结果
function polling() {
    $.ajax({
        url: '/captcha/qr-code-result',
        method: 'post',
        data: {
            serialNumber: serialNumber
        },
        success: function (data) {
            console.log(data);
            if (data.data === undefined || data.message === '二维码已过期') {
                generateQRCode();

                setTimeout(function () {
                    polling();
                }, 2500);
            } else if (data.message === '二维码正在处理') {
                setTimeout(function () {
                    polling();
                }, 2500);
            } else if (data.message === '二维码处理完成') {
                var loginType = data.data;
                location.href = '/web/doctor/index?loginType=' + loginType + '&loginToken=' + serialNumber;
            }
        }
    });
}