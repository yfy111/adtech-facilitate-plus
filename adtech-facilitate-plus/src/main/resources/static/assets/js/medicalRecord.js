// 查询就诊记录的条件页面
function queryMedicalRecordCondition(identity) {
    $.ajax({
        type: "POST",
        url: "/web/medical-record/condition",
        data: {
            identityCard: identity
        },
        success: function (data) {
            $('.medical-record-condition').html(data);
            queryMedicalRecordList(identity, 1);
        }
    })
}

// 查询就诊记录的列表页面
function queryMedicalRecordList(identity, index) {
    $.ajax({
        url: '/web/medical-record/list',
        method: 'post',
        data: {
            identityCard: identity,
            pageNum: index,
            beginDate: $('#medical-record-condition-beginDate').val(),
            endDate: $('#medical-record-condition-endDate').val(),
            cycleType: $('#medical-record-condition-cycleType').val()
        },
        success: function (data) {
            $('.medical-record-data').html(data);
        }
    });
}

// 查询就诊记录的详情页面
function queryMedicalRecordDetail(btn) {
    var array = $(btn).attr("value").split("(_)");

    // 这是就诊记录类型
    var type = array[0];

    // 这是就诊记录的编号
    var extid = array[1];

    if(type=='门诊病历'){
        var params={"extsid":extid.split(",")[0]};
        toShowDetail("/web/medical-record/emergencyDoor",params,'门诊病历');
    }else if(type=='西药处方'){
        var params={"extsid":extid.split(",")[0]};
        toShowDetail("/web/medical-record/westernMedicine",params,'西药处方');
    }else if(type=='中药处方'){
        var params={"extsid":extid.split(",")[0]};
        toShowDetail("/web/medical-record/chineseMedicine",params,'中药处方');
    }else if(type=='检查报告'){
        var params=extid.split(",");
        toShowDetail("/web/medical-record/findCheckRecordName",params,'检查报告');
    }else if(type=='检验报告'){
        var params=extid.split(",");
        toShowDetail("/web/medical-record/findInspectionRecordName",params,'检验报告');
    }else if(type=='住院病案首页'){
        var params={"extsid":extid.split(",")[0]};
        toShowDetail("/web/medical-record/hospitalization",params,'住院病案首页');
    }
}

//进入弹窗
function toShowDetail(url,param,title){
    console.log(param,url);
    $.ajax({
        url: url,
        dataType: "html",
        contentType: 'application/json',
        method: 'post',
        data: JSON.stringify(param),
        success: function (data) {
            layer.open({
                title: title,
                content: data,
                area: ['1200px', '800px'],
                success: function(layero, index){
                    if(title=='检查报告'){
                        console.log(layero, index,title,"----------------");
                        var extsid = $('.layui-layer-content .medical-information-selected-date').val();
                        var param = {extsid:extsid};
                        getcheck("/web/medical-record/checkRecord",param);
                    }
                    else if(title=='检验报告'){
                        var extsid = $('.layui-layer-content .medical-information-selected-date').val();
                        var param = {extsid:extsid};
                        getcheck("/web/medical-record/inspectionRecord",param);
                    }
                }
            });

        }
    });
}

/**
 * 获取检查检验报告
 */
function getcheck(url,param){
    console.log(param);
    $.ajax({
        url: url,
        dataType: "html",
        contentType: 'application/json',
        method: 'post',
        data: JSON.stringify(param),
        success: function (data) {
            $(".layui-layer-content").append(data);
        }
    });
}