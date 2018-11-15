// 获取当前选中的用户的身份证
function currentSelectedIdentity() {
    return $('#selectedUserIdentity').val();
}

// 查询健康档案基本信息的日期数据
function queryMedicalInformationBasicInfoDate() {
    var identity = currentSelectedIdentity();
    $.ajax({
        url: '/web/medical-information/basic-info-date',
        method: 'get',
        data: {
            identity: identity
        },
        success: function (data) {
            $('.basic-info-date').html(data);
            queryMedicalInformationBasicInfoData(identity);
        }
    });
}

// 查询健康档案基本信息的详情数据
function queryMedicalInformationBasicInfoData(selectedIdentity) {
    var date = $('.basic-info-date .medical-information-selected-date').val();
    $.ajax({
        url: '/web/medical-information/basic-info-data',
        method: 'get',
        data: {
            date: date,
            identity: selectedIdentity
        },
        success: function (data) {
            $('.basic-info-data').html(data);
        }
    });
}

// 查询预防接种的分页数据
function queryVaccinationList(identity, index) {
    $.ajax({
        url: '/web/medical-information/vaccination-list',
        method: 'post',
        data: {
            identityCard: identity,
            pageNum: index
        },
        success: function (data) {
            $('.vaccination-data').html(data);
        }
    });
}

// 根据选中的用户，查询新生儿随访的记录日期
function queryNewbornFollowupDate() {
    var selectedIdentity = currentSelectedIdentity();

    // 首先请求新生儿随访的日期数据
    $.ajax({
        type: "post",
        url: "/web/medical-information/newborn-followup-date",
        data: {
            identityCard: selectedIdentity
        },
        success: function (data) {
            $('.newborn-followup-date').html(data);
            queryNewbornFollowupData(selectedIdentity);
        }
    })
}

// 根据指定的日期，查询新生儿随访的详情页面
function queryNewbornFollowupData(identity) {
    // 获取选中的日期
    var selectedData = $('.newborn-followup-date .medical-information-selected-date').val();
    // 在有日期的情况下，使用这个日期来请求数据
    $.ajax({
        url: '/web/medical-information/newborn-followup-data',
        method: 'post',
        data: {
            checkDate: selectedData,
            identityCard: identity
        },
        success: function (data) {
            $('.newborn-followup-data').html(data);
        }
    });
}

// 查询高血压随访的日期数据
function queryMedicalInformationHypertensionDate() {
    var idCard = $("#selectedUserIdentity").val();
    var data = {"identityCard": idCard};
    $.ajax({
        type: "POST",
        url: "/web/hypertension/followUpTime",
        dataType: "html",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (data) {
            $(".hypertension-info-date").html(data);

            // 加载随访基本信息的数据内容
            queryMedicalInformationHypertensionData(idCard);
        }
    })
}

// 查询高血压随访的详情数据
function queryMedicalInformationHypertensionData(identity) {
    var dateExtsid = $('.hypertension-info-date .medical-information-selected-date').val();
    var data = {checkDate: dateExtsid, identityCard: identity};
    $.ajax({
        url: '/web/hypertension/index',
        method: 'POST',
        dataType: "html",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (data) {
            $('.hypertension-info-data').html(data);
        }
    });
}

// 查询糖尿病随访的日期数据
function queryMedicalInformationDiabetesDate() {
    var idCard = $("#selectedUserIdentity").val();
    var data = {"identityCard": idCard};
    $.ajax({
        type: "POST",
        url: "/web/diabetes/followUpTime",
        dataType: "html",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (data) {
            $(".diabetes-info-date").html(data);
            // 加载随访基本信息的数据内容
            queryMedicalInformationDiabetesData(idCard);
        }
    })
}

// 查询糖尿病随访的详情数据
function queryMedicalInformationDiabetesData(identity) {
    var dateExtsid = $('.diabetes-info-date .medical-information-selected-date').val();
    var data = {checkDate: dateExtsid, identityCard: identity};
    $.ajax({
        url: '/web/diabetes/index',
        method: 'POST',
        dataType: "html",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (data) {
            $('.diabetes-info-data').html(data);
        }
    });
}