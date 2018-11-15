function queryAuthoriseMemberSimpleList() {
    $.ajax({
        url: '/web/index/authorise-member-simple-list',
        method: 'get',
        success: function (data) {
            $('.authorise-member-simple-list').html(data);
        }
    });
}