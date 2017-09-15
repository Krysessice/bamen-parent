/* 表格初次渲染 */

document.write("<script language=javascript src='/custom/js/bootstrap-table.js'></script>");

(function ($) {

    var TableInit = function(element, tableName, args) {

        this.init(element, tableName, args);
    }

    TableInit.prototype = {

        init: function(element, tableName, args) {

            var $element = $(element);
            switch(tableName) {
            case 'authorize':
                $element.bootstrapTable(this.paramsMap.authorize);
                break;
            case 'queryPayOrder':
                 $element.bootstrapTable(this.paramsMap.queryPayOrder);
                 break;
            case 'queryRebate':
                 $element.bootstrapTable(this.paramsMap.queryRebate);
                 break;
            }
        },


        paramsMap: {

            authorize: {
                url: '/agent/junior/list/',
                queryParams: {
                    F_IS_AUTHORIZED: 0
                },
                columns: [
                    {
                        field: 'id',
                        title: 'id',
                        hidden: true
                    },
                    {
                        field: 'gameId',
                        title: '游戏id'
                    },
                    {
                        field: 'nickName',
                        title: '用户昵称'
                    },
                    {
                        field: 'transMemberTime',
                        title: '绑定日期',
                        formatter: 'yy-MM-dd'
                    },
                    {
                        title: '操作',
                        content: '<button type="button" class="btn btn-primary btn-sm" onclick="commonJs.authorize(this)">授权</button>'
                    }
                ]
            },
            queryPayOrder: {
                            url: '/admin/queryPayOrder',
                            queryParams: {},
                            columns: [
                                {
                                    field: 'id',
                                    title: 'id',
                                    hidden: true
                                },
                                {
                                    field: 'account',
                                    title: '被充值者'
                                  },
                                {
                                    field: 'nickName',
                                    title: '充值者',
                                },

                                {
                                    field: 'payPrice',
                                    title: '充值金额'
                                },
                                {
                                    field: 'cardNum',
                                    title: '购卡数量',
                                },
                                {
                                    field: 'createTime',
                                    title: '购卡时间',
                                    formatter: 'yy-MM-dd HH:mm'
                                },

                            ]
                        },
                         queryRebate: {
                            url: '/admin/queryRebate',
                            queryParams: {},
                            columns: [
                             {
                                field: 'id',
                                title: 'id',
                                hidden: true
                            },
                            {
                                field: 'account',
                                title: '被充值者'
                              },
                            {
                                field: 'nickName',
                                title: '充值者',
                            },

                            {
                                field: 'firSuperAgentId',
                                title: '上级代理ID'
                            },
                            {
                                field: 'firBonus',
                                title: '上级代理返利',
                            },
                             {
                                field: 'secSuperAgentId',
                                title: '上上级代理ID'
                            },
                            {
                                field: 'secBonus',
                                title: '上上级代理返利',
                            },
                            {
                                field: 'createTime',
                                title: '购卡时间',
                                formatter: 'yy-MM-dd HH:mm'
                            },

                        ]
                    }



        }
    }

    $.fn.tableInit = function(tableName) {
        var args = Array.apply(null, arguments);
        args.shift();

        new TableInit(this, tableName, args);
    }

}(window.jQuery));