var CommonJs = function() {}

CommonJs.prototype.getByGameId = function(gameId, fn) {
     $.ajax({
         type: 'get',
         url: '/agent/',
         dataType: 'json',
         data: {
             F_GAME_ID : gameId
         },
         success: function(data) {

             if (!data.status)
                 return alert(data.msg);

             if (typeof fn === 'function')
                 fn(data.content);
         }
     });
}


CommonJs.prototype.updateAiveAgentList = function(e) {
     var id = $(e).parents('tr').find('td[name="id"]').text();
     var gameId = $(e).parents('tr').find('td[name="gameId"]').text();
     if (window.confirm('确定为 ' + gameId + ' 授权吗?')) {
         $.ajax({
             type: 'put',
             url: '/admin/updateAiveAgentList/' + gameId + '/',
             dataType: 'json',
             success: function(data) {
                 if (data.status){
                     alert('授权成功');
                 }
                 else
                     return alert(data.msg);
                 $(e).parents('table').bootstrapTable('reloadGrid');
             }
         });
     }
 }

 CommonJs.prototype.loginAgent = function(e) {
      var gameId = $(e).parents('tr').find('td[name="gameId"]').text();
      if (window.confirm('确定要进 ' + gameId + ' 后台吗?')) {
        top.location.href='/agent/loginAgent/' + gameId + '/';
      }
  }

CommonJs.prototype.getAccredit = function(e) {
    var gameId = $(e).parents('tr').find('td[name="gameId"]').text();
    if (window.confirm('确定要进 ' + gameId + ' 后台吗?')) {
      top.location.href='/admin/getAccredit/' + gameId + '/';
    }
}


 CommonJs.prototype.updateNoAiveAgentList = function(e) {
     var id = $(e).parents('tr').find('td[name="id"]').text();
     var gameId = $(e).parents('tr').find('td[name="gameId"]').text();
     if (window.confirm('确定为 ' + gameId + '取消授权吗?')) {
         $.ajax({
             type: 'put',
             url: '/admin/updateNoAiveAgentList/' + gameId + '/',
             dataType: 'json',
             success: function(data) {
                 if (data.status){
                     alert('取消成功');
                 }
                 else
                     return alert(data.msg);
                 $(e).parents('table').bootstrapTable('reloadGrid');
             }
         });
     }
 }



 CommonJs.prototype.authorizeLookCard = function(e) {
     var id = $(e).parents('tr').find('td[name="id"]').text();
     var gameId = $(e).parents('tr').find('td[name="gameId"]').text();
     if (window.confirm('确定为 ' + gameId + '取消授权吗?')) {
         $.ajax({
             type: 'put',
             url: '/account/updateAgentLookCard/' + gameId + '/',
             dataType: 'json',
             success: function(data) {
                 if (data.status){
                     alert('取消成功');
                 }
                 else
                     return alert(data.msg);
                 $(e).parents('table').bootstrapTable('reloadGrid');
             }
         });
     }
 }

CommonJs.prototype.authorizeAgent = function(e) {
     var id = $(e).parents('tr').find('td[name="id"]').text();
     var gameId = $(e).parents('tr').find('td[name="gameId"]').text();
     if (window.confirm('确定为 ' + gameId + ' 授权吗?')) {
         $.ajax({
             type: 'put',
             url: '/agent/authorizeAgent/' + gameId + '/',
             dataType: 'json',
             success: function(data) {
                 if (data.status){
                     alert('授权成功');
                 }
                 else
                     return alert(data.msg);
                 $(e).parents('table').bootstrapTable('reloadGrid');
             }
         });
     }
 }

 CommonJs.prototype.authorizeNo = function(e) {
     var id = $(e).parents('tr').find('td[name="id"]').text();
     var gameId = $(e).parents('tr').find('td[name="gameId"]').text();
     if (window.confirm('确定为 ' + gameId + ' 取消授权吗?')) {
         $.ajax({
             type: 'put',
             url: '/agent/authorizeNo/' + gameId + '/',
             dataType: 'json',
             success: function(data) {
                 if (data.status){
                     alert('取消成功');
                 }
                 else
                     return alert(data.msg);
                 $(e).parents('table').bootstrapTable('reloadGrid');
             }
         });
     }
 }



CommonJs.prototype.getByGameIDWithCardNum = function(gameId, fn) {
     $.ajax({
         type: 'get',
         url: '/account/withCardNum',
         dataType: 'json',
         data: {
             gameId : gameId
         },
         success: function(data) {

             if (!data.status)
                 return alert(data.msg);

             if (typeof fn === 'function')
                 fn(data.content);
         }
     });
}





/* 代理信息表单渲染 */
CommonJs.prototype.sysAgentInfoFormRender = function() {
    this.getByGameId($('input[name="F_GAME_ID"]').val(), function(data) {
        if (data == null) return alert('未查询到结果');

        if (data.finishInfo) {
            $('div#finishInfo').show();
            $('div#unfinishInfo').hide();
        } else {
            $('div#finishInfo').hide();
            $('div#unfinishInfo').show();
        }

        if (data.superAgentGameId) {
            $('div#fillInvitedCode').show();
            $('div#unfillInvitedCode').hide();
        } else {
            $('div#fillInvitedCode').hide();
            $('div#unfillInvitedCode').show();
        }

        if (data.finishInfo || data.superAgentGameId)
            $('button[type="submit"]').parent().show();
        else
            $('button[type="submit"]').parent().hide();

        var $form = $('#sysAgentInfoForm');
        var $item, param;
        $form.find('p.form-control-static').each(function(index, item) {
            $item = $(item);
            param = data[$item.attr('id')];
            $item.html(param == null ? "<font color=\"grey\"><i>未填写</i></font>" : param);
        });
        $form.find('input').each(function(index, item) {
            $item = $(item);
            param = data[$item.attr('name')];
            $item.val(param);
        });

        $('p#isAuthorized').text(data.authorized ? '已授权' : '未授权');
    });
}

CommonJs.prototype.freeze = function(sysFlag) {
    if (window.confirm('确定' + (sysFlag ? '将 ' + gameId + ' 解冻吗？' : '冻结 ' + gameId + ' 吗？'))) {
        $.ajax({
            type: 'put',
            url: '/admin/sysAgent/freeze/' + gameId + '/',
            dataType: 'json',
            data: {
                sysFlag: sysFlag
            },
            success: function(data) {

                if (data.status) {
                    alert('操作成功');
                    window.location.reload();
                } else
                    alert(data.msg);
            }
        });
    }
}

CommonJs.prototype.pwdReset = function() {
    if (window.confirm('确定为 ' + gameId + ' 重置密码吗？')) {
        $.ajax({
            type: 'put',
            url: '/admin/sysAgent/pwdReset/' + gameId + '/',
            dataType: 'json',
            success: function(data) {

                if (data.status)
                    alert('操作成功');
                else
                    alert(data.msg);
            }
        });
    }
}

CommonJs.prototype.authorize = function(e) {
    var id = $(e).parents('tr').find('td[name="id"]').text();
    var gameId = $(e).parents('tr').find('td[name="gameId"]').text();
    if (window.confirm('确定为 ' + gameId + ' 授权吗?')) {
        $.ajax({
            type: 'put',
            url: '/agent/authorize/' + id + '/',
            dataType: 'json',
            success: function(data) {

                if (data.status)
                    alert('操作成功');
                else
                    return alert(data.msg);
                $(e).parents('table').bootstrapTable('reloadGrid');
            }
        });
    }
}

CommonJs.prototype.datetimeInit = function(fn) {
    $('.form_date').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        minView: 2,
        todayBtn: 'linked',
        todayHighlight: true,
        endDate: new Date()
    }).on('changeDate', function(ev) {
        if (typeof intercept === 'undefined' || !intercept)
            $("#table").bootstrapTable('reloadGrid', {
                queryParams: {
                    startDate: $('#startDate').val(),
                    endDate: $('#endDate').val()
                }
            });
    });

    var now = new Date();
    $('#endDate').val(now.format('yyyy-MM-dd'));
    now.setDate(now.getDate() - 7);
    $('#startDate').val(now.format('yyyy-MM-dd'));

    (fn && typeof fn === 'function') && fn();
}

CommonJs.prototype.closeAnnounce = function() {
    $.ajax({
        type: 'put',
        url: '/agent/announce/close/',
        dataType: 'json',
        success: function(data) {

            if (!data.status)
                return alert(data.msg);

            $(".alert").alert('close');
        }
    });
}


/* 赠送靓号后刷新代理信息 */
CommonJs.prototype.refresh = function() {
    var srcGameId = $('input#srcGameId').val();
    var destGameId = $('input#destGameId').val();
    if (srcGameId === destGameId) {
        alert('不能输入同样的代理号');
        return false;
    }

    $.ajax({
        type: 'put',
        url: '/admin/refresh/',
        dataType: 'json',
        data: $('form#sysAgentRefreshForm').serialize(),
        success: function(data) {

            if (data.status)
                alert('操作成功');
            else
                alert(data.msg);
        }
    });

    return false;
}

CommonJs.prototype.cardGift = function() {
    if (window.confirm('确定为 ' + $('input#presentee').val() + ' 赠送 ' + $('input#cardNum').val() + ' 张房卡吗？'))
        $.ajax({
            type: 'put',
            url: '/admin/cardGift/',
            dataType: 'json',
            data: $('form#cardGiftForm').serialize(),
            success: function(data) {

                if (data.status) {
                    $('form#cardGiftForm')[0].reset();
                    $('#table').bootstrapTable('reloadGrid');
                    location.reload();
                    alert('操作成功');
                }
                else
                    alert(data.msg);
            }
        });

    return false;
}

CommonJs.prototype.payGold = function() {
    if (window.confirm('确定为 ' + $('input#presentee').val() + ' 赠送 ' + $('input#goldNum').val() + ' 金币吗？'))
        $.ajax({
            type: 'put',
            url: '/admin/payGold/',
            dataType: 'json',
            data: $('form#payGoldForm').serialize(),
            success: function(data) {

                if (data.status) {
                    $('form#payGoldForm')[0].reset();
                    $('#table').bootstrapTable('reloadGrid');
                    location.reload();
                    alert('操作成功');
                }
                else
                    alert(data.msg);
            }
        });

    return false;
}



/* 清理卡线 */
CommonJs.prototype.delGameScoreLocker = function(e) {
    var $e = $(e);
    var userId = $e.parents('tr').find('td[name="userId"]').text();
    if (window.confirm("确定要为" + userId + "清理卡线吗？")) {
        $.ajax({
            type: 'delete',
            url: '/admin/gameScoreLocker/' + userId + '/',
            dataType: 'json',
            success: function(data) {
                if(data.status)
                    alert('操作成功');
                else
                    return alert(data.msg);
                $e.parents('table').bootstrapTable('reloadGrid');
            }
        });
    }
}

CommonJs.prototype.group_room_status = function(e, status) {
    var $e = $(e);
    $.ajax({
        type: 'put',
        url: '/groupRoom/',
        dataType: 'json',
        contentType:"application/json",
        data: JSON.stringify({
            id : $e.parents('tr').find('td[name="id"]').text(),
            roomStatus : status
        }),
        success: function(data) {
            if (!data.status)
                return alert(data.msg);
            $('#table').bootstrapTable('reloadGrid');
        }
    });
}

Date.prototype.format = function(format) {
    /*
    * 使用例子:format="yyyy-MM-dd HH:mm:ss";
    */
    var o = {
        "M+" : this.getMonth() + 1, // month
        "d+" : this.getDate(), // day
        "H+" : this.getHours(), // hour
        "m+" : this.getMinutes(), // minute
        "s+" : this.getSeconds(), // second
        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
        "S" : this.getMilliseconds()
        // millisecond
    }

    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));

    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));

    return format;
}

/** form 表单格式化为json对象 */
$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
