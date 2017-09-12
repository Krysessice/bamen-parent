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
            case 'junior':
                var flag = args[0] === 'true' ? true : false;
                $element.bootstrapTable(this.paramsMap[flag ? 'juniorAgent' : 'juniorMember'], 'refresh');
                break;
            case 'payOrder':
                this.paramsMap.payOrder.queryParams = {
                    startDate: $('#startDate').val(),
                    endDate: $('#endDate').val()
                }
                $element.bootstrapTable(this.paramsMap.payOrder);
                break;
            case 'juniorPayOrder':
                this.paramsMap.juniorPayOrder.queryParams.F_GAME_ID = args[0];
                $element.bootstrapTable(this.paramsMap.juniorPayOrder, 'refresh');
                break;
            case 'juniorPayOrderStatistic':
                this.paramsMap.juniorPayOrderStatistic.queryParams.F_GAME_ID = args[0];
                $element.bootstrapTable(this.paramsMap.juniorPayOrderStatistic, 'refresh');
                break;
            case 'sumTeamPayOrderActual':
                this.paramsMap.sumTeamPayOrderActual.queryParams = {
                    startDate: $('#startDate').val(),
                    endDate: $('#endDate').val()
                }
                $element.bootstrapTable(this.paramsMap.sumTeamPayOrderActual);
                break;
            case 'sumTeamPayOrderDaily':
                this.paramsMap.sumTeamPayOrderDaily.queryParams = {
                    startDate: $('#startDate').val(),
                    endDate: $('#endDate').val()
                }
                $element.bootstrapTable(this.paramsMap.sumTeamPayOrderDaily);
                break;
            case 'teamPayOrder':
                this.paramsMap.teamPayOrder.queryParams = {
                    startDate: $('#startDate').val(),
                    endDate: $('#endDate').val(),
                    teamNo: args[0]
                }
                $element.bootstrapTable(this.paramsMap.teamPayOrder, 'refresh');
                break;
            case 'teamPayOrderStatistic':
                this.paramsMap.teamPayOrderStatistic.queryParams = {
                    startDate: $('#startDate').val(),
                    endDate: $('#endDate').val(),
                    teamNo: args[0]
                }
                $element.bootstrapTable(this.paramsMap.teamPayOrderStatistic, 'refresh');
                break;
            case 'clearRecord':
                $element.bootstrapTable(this.paramsMap.clearRecord);
                break;
            case 'cardCostPerday':
                this.paramsMap.cardCostPerday.queryParams = {
                    startDate: $('#startDate').val(),
                    endDate: $('#endDate').val()
                }
                $element.bootstrapTable(this.paramsMap.cardCostPerday);
                break;
             case 'cardCostUser':
                this.paramsMap.cardCostUser.queryParams = {
                    startDate: $('#startDate').val(),
                    endDate: $('#endDate').val()
                }
                $element.bootstrapTable(this.paramsMap.cardCostUser);
                break;
            case 'cardGiftRecord':
                $element.bootstrapTable(this.paramsMap.cardGiftRecord);
                break;
            case 'gameScoreLockerList':
                $element.bootstrapTable(this.paramsMap.gameScoreLockerList);
                break;
            case 'payOrderPerday':
                $element.bootstrapTable(this.paramsMap.payOrderPerday);
                break;
            case 'groupRoom':
                $element.bootstrapTable(this.paramsMap.groupRoom);
                break;
            case 'groupRoomMember':
                this.paramsMap.groupRoomMember.queryParams = {
                    AgentRoomid: args[0]
                }
                $element.bootstrapTable(this.paramsMap.groupRoomMember, 'refresh');
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

            juniorMember: {
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
                        content: '<button type="button" class="btn btn-default btn-sm" role="button" ' +
                                    'data-toggle="modal" data-target="#payOrderModel">充值明细</button>'
                    }
                ]
            },

            juniorAgent: {
                url: '/agent/junior/list/',
                queryParams: {
                    F_IS_AUTHORIZED: 1
                },
                columns: [
                    {
                        field: 'id',
                        title: 'id',
                        hidden: true
                    },
                    {
                        field: 'gameId',
                        title: '邀请码'
                    },
                    {
                        field: 'realName',
                        title: '真实姓名'
                    },
                    {
                        field: 'recruitNum',
                        title: '招募人数'
                    },
                    {
                        field: 'authorizedTime',
                        title: '授权日期',
                        formatter: 'yy-MM-dd'
                    },
                    {
                        title: '操作',
                        content: '<button type="button" class="btn btn-default btn-sm" role="button" ' +
                                    'data-toggle="modal" data-target="#payOrderModel">充值明细</button>'
                    }
                ]
            },

            payOrder: {
                url: '/order/list/',
                columns: [
                    {
                        field: 'id',
                        title: 'id',
                        hidden: true
                    },
                    {
                        field: 'payPrice',
                        title: '充值金额'
                    },
                    {
                        field: 'createTime',
                        title: '充值时间'
                    }
                ]
            },

            juniorPayOrder: {
                url: '/agent/order/junior/list/',
                queryParams: {},
                columns: [
                    {
                        field: 'id',
                        title: 'id',
                        hidden: true
                    },
                    {
                        field: 'payPrice',
                        title: '充值金额'
                    },
                    {
                        field: 'createTime',
                        title: '充值时间'
                    }
                ]
            },

            juniorPayOrderStatistic: {
                url: '/order/statistic/junior/list/',
                queryParams: {},
                columns: [
                    {
                        field: 'id',
                        title: 'id',
                        hidden: true
                    },
                    {
                        field: 'payPrice',
                        title: '充值金额'
                    },
                    {
                        field: 'createTime',
                        title: '充值日期',
                        formatter: 'yy-MM-dd'
                    }
                ]
            },

            sumTeamPayOrderActual: {
                url: '/order/team/actual/list/',
                queryParams: {},
                columns: [
                    {
                        field: 'teamNo',
                        title: 'teamNo',
                        hidden: true
                    },
                    {
                        field: 'teamName',
                        title: ''
                    },
                    {
                        field: 'payPrice',
                        title: '充值总额'
                    },
                    {
                        title: '操作',
                        content: '<button type="button" class="btn btn-default btn-sm" role="button" ' +
                                    'data-toggle="modal" data-target="#payOrderModel">充值明细</button>'
                    }
                ]
            },

            sumTeamPayOrderDaily: {
                url: '/order/team/daily/list/',
                queryParams: {},
                columns: [
                    {
                        field: 'teamNo',
                        title: 'teamNo',
                        hidden: true
                    },
                    {
                        field: 'teamName',
                        title: ''
                    },
                    {
                        field: 'payPrice',
                        title: '充值总额'
                    },
                    {
                        field: 'commission',
                        title: '结算总额'
                    },
                    {
                        title: '操作',
                        content: '<button type="button" class="btn btn-default btn-sm" role="button" ' +
                                    'data-toggle="modal" data-target="#payOrderModel">充值明细</button>'
                    }
                ]
            },

            teamPayOrder: {
                 url: '/order/junior/list/',
                 queryParams: {},
                 columns: [
                     {
                         field: 'gameId',
                         title: '游戏id'
                     },
                     {
                          field: 'nickName',
                          title: '昵称'
                      },
                     {
                         field: 'payPrice',
                         title: '充值金额'
                     },
                     {
                         field: 'createTime',
                         title: '充值时间',
                         formatter: 'yy-MM-dd HH:mm'
                     }
                 ]
            },

            teamPayOrderStatistic: {
                 url: '/order/statistic/junior/list/',
                 queryParams: {},
                 columns: [
                     {
                         field: 'payPrice',
                         title: '充值总额'
                     },
                     {
                         field: 'createTime',
                         title: '充值日期',
                         formatter: 'yyyy-MM-dd'
                     }
                 ]
            },

            clearRecord: {
                url: '/clear/record/list/',
                queryParams: {},
                columns: [
                    {
                        field: 'startDate',
                        title: '起始日期'
                    },
                    {
                        field: 'endDate',
                        title: '结束日期'
                    },
                    {
                        field: 'payPrice',
                        title: '充值总额'
                    },
                    {
                        field: 'clearPrice',
                        title: '结算总额'
                    },
                    {
                        title: '操作',
                        content: '<button type="button" class="btn btn-primary btn-sm" onclick="downClearExcel(this)">下载</button>'
                    }
                ]
            },

            cardCostPerday: {
                url: '/admin/cardCost/perday/list/',
                queryParams: {},
                columns: [
                    {
                        field: 'openRoomNum',
                        title: '开房局数'
                    },
                    {
                        field: 'cardCost',
                        title: '房卡消耗'
                    },
                    {
                        field: 'createTime',
                        title: '开房日期',
                        formatter: 'yy-MM-dd'
                    },
                    {
                        field: 'gameTypeId',
                        title: '房间类型'
                     }
                ]
            },
            cardCostUser: {
                    url: '/admin/cardCost/User/list/',
                    queryParams: {},
                    columns: [
                        {
                            field: 'gameId',
                            title: '新增'
                        },
                         {
                            field: 'createTime',
                            title: '开房日期',
                            formatter: 'yy-MM-dd'
                        }
                    ]
                },

            cardGiftRecord: {
                url: '/admin/cardGift/list/',
                queryParams: {},
                columns: [
                    {
                        field: 'presentee',
                        title: '游戏id'
                    },
                    {
                        field: 'cardNum',
                        title: '赠卡数量'
                    },
                    {
                        field: 'giftReason',
                        title: '备注'
                    },
                    {
                        field: 'createTime',
                        title: '赠送日期',
                        formatter: 'yy-MM-dd'
                    }
                ]
            },

            gameScoreLockerList: {
                url: '/admin/gameScoreLocker/list/',
                queryParams: {},
                columns: [
                    {
                        field: 'userId',
                        title: '用户ID',
                        hidden: true
                    },
                    {
                        field: 'gameId',
                        title: '游戏ID',
                    },
                    {
                        field: 'nickName',
                        title: '用户昵称'
                    },
                    {
                        field: 'kindName',
                        title: '游戏名称'
                    },
                    {
                        field: 'roomId',
                        title: '房间号',
                    },
                    {
                        field: 'collectDate',
                        title: '进房时间',
                        formatter: 'yy-MM-dd HH:mm'
                    },
                    {
                        title: '操作',
                        content: '<button type="button" class="btn btn-primary btn-sm" onclick="commonJs.delGameScoreLocker(this)">删除</button>'
                    }
                ]
            },

            payOrderPerday: {
                url: '/admin/payOrder/perday/list/',
                queryParams: {},
                columns: [
                    {
                        field: 'payPrice',
                        title: '总充值金额'
                    },
                    {
                        field: 'cardGold',
                        title: '总购买房卡',
                    },
                    {
                        field: 'createTime',
                        title: '充值日期',
                        formatter: 'yy-MM-dd'
                    }
                ]
            },

            groupRoom: {
                url: '/groupRoom/listSelf/',
                queryParams: {},
                columns: [
                    {
                        field: 'id',
                        title: 'id',
                        hidden: true
                    },
                    {
                        field: 'groupName',
                        title: '群房间名',
                    },
                    {
                        field: 'roomStatus',
                        title: '房间状态',
                        content: function(field) {
                            return field ? '<button type="button" class="btn btn-warning btn-sm" onclick="commonJs.group_room_status(this, 0)">已禁用</button>' :
                                            '<button type="button" class="btn btn-info btn-sm" onclick="commonJs.group_room_status(this, 1)">开放中</button>';
                        }
                    },
                    {
                        field: 'playerNum',
                        title: '玩家数量',
                    },
                    {
                        field: 'createTime',
                        title: '创建时间',
                        formatter: 'yy-MM-dd HH:mm'
                    },
                    {
                        title: '操作',
                        content: '<button type="button" class="btn btn-default btn-sm" role="button" data-toggle="modal" data-target="#groupRoomMemberModel">详细</button>' +
                                    '&nbsp;<button type="button" class="btn btn-danger btn-sm" onclick="delGroupRoom(this)">删除</button>'+
                                     '&nbsp;<button type="button" class="btn btn btn-inverse" onclick="queryState(this)">状态</button>'
                    }
                ]
            },

            groupRoomMember: {
                url: '/groupRoomMember/list/',
                queryParams: {},
                columns: [
                    {
                        field: 'userId',
                        title: '用户id',
                        hidden: true
                    },
                    {
                        field: 'groupRoomId',
                        title: '群房间号',
                        hidden: true
                    },
                    {
                        field: 'gameId',
                        title: '游戏id'
                    },
                    {
                        field: 'nickName',
                        title: '昵称'
                    },
                    {
                        title: '操作',
                        content: '<button type="button" class="btn btn-default btn-sm" onclick="delGroupRoomMember(this)">移除</button>'
                    }
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