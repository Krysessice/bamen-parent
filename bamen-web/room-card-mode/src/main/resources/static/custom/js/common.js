var CommonJs = function() {}

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

CommonJs.prototype.getByAccount=function(account,fn){
         $.ajax({
           type:'get',
           url:'/admin/getSysAget',
           dataType:'Json',
           data:{
                F_ACCOUNT:account
           },
          success:function(data){
                 if (!data.status)
                   return alert(data.msg);

                 if (typeof fn === 'function')
                    fn(data.content);
                 }
         });
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



 CommonJs.prototype.authorizeGiveAgent = function(e) {
     var id = $(e).parents('tr').find('td[name="id"]').text();
     var gameId = $(e).parents('tr').find('td[name="gameId"]').text();
     if (window.confirm('确定为 ' + gameId + '取消授权吗?')) {
         $.ajax({
             type: 'put',
             url: '/account/authorizeGiveAgent/' + gameId + '/',
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


CommonJs.prototype.authorize = function(e) {
     var id = $(e).parents('tr').find('td[name="id"]').text();
     var account = $(e).parents('tr').find('td[name="account"]').text();
     if (window.confirm('确定为 ' + account + ' 授权吗?')) {
         $.ajax({
             type: 'put',
             url: '/agent/authorize/' + account + '/',
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

 CommonJs.prototype.authorizes = function(e) {
     var id = $(e).parents('tr').find('td[name="id"]').text();
     var account = $(e).parents('tr').find('td[name="account"]').text();
     if (window.confirm('确定为 ' + account + ' 授权吗?')) {
         $.ajax({
             type: 'put',
             url: '/agent/authorizes/' + account + '/',
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

CommonJs.prototype.SuperAgentId=function(SuperAgentId,fn){
            $.ajax({
                      type:'get',
                      url:'/admin/SuperAgentId',
                      dataType:'Json',
                      data:{
                           F_SUPER_AGENT_ID:SuperAgentId
                      },
                     success:function(data){
                            if (!data.status)
                              return alert(data.msg);

                            if (typeof fn === 'function')
                               fn(data.content);
                            }
                    });
}


CommonJs.prototype.getAccount=function(account,fn){
        $.ajax({
                type:"get",
                url:"/admin/getAccount",
                dataType:"Json",
                data:{
                    F_ACCOUNT:account
                },
                success:function(data){
                    if(!data.status){
                        return alert(data.msg);
                    }
                    if(typeof fn =='function'){
                        fn(data.content);
                    }

                }

        })
}


CommonJs.prototype.freeze=function(sysFlag){
   if (window.confirm('确定' + (sysFlag ? '将 ' + account + ' 解冻吗？' : '冻结 ' + account + ' 吗？'))) {
            $.ajax({
                type:'put',
                url:'/admin/sysAgent/Freeze/'+ account +'/',
                dataType:'Json',
                data:{
                    sysFlag:sysFlag
                },
                success:function(data){
                    if(data.status){
                        alert("操作成功");
                        window.location.reload();
                    }else{
                        alert(data.msg);
                    }
                }
            })
        }
}


CommonJs.prototype.pwdReset=function(){
    if(window.confirm('确定为'+account +'重置密码吗？'))
    $.ajax({
        type:'put',
        url:'/admin/sysAgent/pwdReset/'+ account+'/',
        dataType:'Json',
        success:function(data){
            if(data.status){
                alert("操作成功");
                window.location.reload();
            }else{
                alert(data.msg);
            }
        }

    })
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
