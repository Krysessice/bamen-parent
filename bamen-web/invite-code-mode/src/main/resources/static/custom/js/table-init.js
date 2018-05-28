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
            case 'GoldClearRecord':
                $element.bootstrapTable(this.paramsMap.GoldClearRecord);
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
                this.paramsMap.cardGiftRecord.queryParams = {
                  startDate: $('#startDate').val(),
                  endDate: $('#endDate').val(),
                   selected: args[0]
                }
                $element.bootstrapTable(this.paramsMap.cardGiftRecord, 'refresh');
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
             case 'queryPartner':
                $element.bootstrapTable(this.paramsMap.queryPartner);
              break;
             case 'queryPartnerGold':
                 $element.bootstrapTable(this.paramsMap.queryPartnerGold);
               break;
             case 'queryAgentList':
                   this.paramsMap.queryAgentList.queryParams = {
                        gameId: args[0]
                   }
                   $element.bootstrapTable(this.paramsMap.queryAgentList, 'refresh');
                    break;
             case 'querySumCard':
                   this.paramsMap.querySumCard.queryParams = {
                       startDate: $('#startDate').val(),
                       endDate: $('#endDate').val()
                   }
                   $element.bootstrapTable(this.paramsMap.querySumCard);
                    break;
             case 'querySumGold':
                    this.paramsMap.querySumGold.queryParams = {
                        startDate: $('#startDate').val(),
                        endDate: $('#endDate').val()
                    }
                    $element.bootstrapTable(this.paramsMap.querySumGold);
                     break;
             case 'payTimeModel':
                  this.paramsMap.payTimeModel.queryParams = {
                                  teamNo: args[0]
                              }
                 $element.bootstrapTable(this.paramsMap.payTimeModel ,'refresh');
                 break;
             case 'queryGameType':
                 this.paramsMap.queryGameType.queryParams = {
                     gameTypeId: args[0],
                     createTime:args[1]
                 }
                 $element.bootstrapTable(this.paramsMap.queryGameType, 'refresh');
                 break;
             case 'cardactiveUser':
                   this.paramsMap.cardactiveUser.queryParams = {
                       startDate: $('#startDate').val(),
                       endDate: $('#endDate').val()
                   }
                   $element.bootstrapTable(this.paramsMap.cardactiveUser, 'refresh');
                   break;
             case 'queryLook':
                 $element.bootstrapTable(this.paramsMap.queryLook);
                 break;
             case 'groupQueryRoom':
                 $element.bootstrapTable(this.paramsMap.groupQueryRoom);
                 break;
             case 'querycount':
                  this.paramsMap.querycount.queryParams = {
                      startDate: $('#startDate').val(),
                      endDate: $('#endDate').val(),
                      qunid: args[0]
                  }
                  $element.bootstrapTable(this.paramsMap.querycount, 'refresh');
                  break;
             case 'queryPartnerPlayer':
                  $element.bootstrapTable(this.paramsMap.queryPartnerPlayer);
                  break;
             case 'queryPartnerMessage':
                   this.paramsMap.queryPartnerMessage.queryParams = {
                       startDate: $('#startDate').val(),
                       endDate: $('#endDate').val(),
                       qunid:args[0]
                   }
                   $element.bootstrapTable(this.paramsMap.queryPartnerMessage, 'refresh');
                   break;
             case 'partnerQuerysubclass':
                   this.paramsMap.partnerQuerysubclass.queryParams = {
                       startDate: $('#startDate').val(),
                       endDate: $('#endDate').val(),
                       gameId: args[0]
                   }
                   $element.bootstrapTable(this.paramsMap.partnerQuerysubclass, 'refresh');
                   break;
             case 'partnerGoldQuerysubclass':
                    this.paramsMap.partnerGoldQuerysubclass.queryParams = {
                        startDate: $('#startDate').val(),
                        endDate: $('#endDate').val(),
                        gameId: args[0]
                    }
                    $element.bootstrapTable(this.paramsMap.partnerGoldQuerysubclass, 'refresh');
                    break;
             case 'giveAgentList':
                  $element.bootstrapTable(this.paramsMap.giveAgentList);
                  break;
             case 'GiveQueryPartner':
                   $element.bootstrapTable(this.paramsMap.GiveQueryPartner);
                   break;
             case 'queryAgent':
                   this.paramsMap.queryAgent.queryParams = {
                        startDate: $('#startDate').val(),
                        endDate: $('#endDate').val()
                    }
                   $element.bootstrapTable(this.paramsMap.queryAgent);
                    break;
             case 'getUserScore':
                  this.paramsMap.getUserScore.queryParams = {
                    startDate: $('#startDate').val(),
                    endDate: $('#endDate').val(),
                    gameid: args[0]
                  }
                  $element.bootstrapTable(this.paramsMap.getUserScore, 'refresh');
                  break;
             case 'queryAllList':
                  this.paramsMap.queryAllList.queryParams = {
                          endDate: $('#endDate').val(),
                          card: args[0]
                       }
                    $element.bootstrapTable(this.paramsMap.queryAllList,'refresh');
                    break;
             case 'pastNoModel':
                    this.paramsMap.pastNoModel.queryParams = {
                            endDate: $('#endDate').val(),
                         }
                      $element.bootstrapTable(this.paramsMap.pastNoModel,'refresh');
                      break;
             case 'queryAllRoom':
                    this.paramsMap.queryAllRoom.queryParams = {
                         startDate: $('#startDate').val(),
                         endDate: $('#endDate').val()
                     }
                    $element.bootstrapTable(this.paramsMap.queryAllRoom);
                     break;
            case 'getAccredit':
                   this.paramsMap.getAccredit.queryParams = {
                        gameId: args[0]
                   }
                   $element.bootstrapTable(this.paramsMap.getAccredit, 'refresh');
                    break;
             case 'getDaily':
                var flag = args[0] === 'true' ? true : false;
                $element.bootstrapTable(this.paramsMap[flag ? 'TurntableIntegral' : 'TurntableDaily'], 'refresh');
                break;
            case 'getWithrawal':
               this.paramsMap.getWithrawal.queryParams = {
                    gameId: args[0]
               }
               $element.bootstrapTable(this.paramsMap.getWithrawal, 'refresh');
                break;
            case 'cashRecord':
               this.paramsMap.cashRecord.queryParams = {
                   startDate: $('#startDate').val(),
                   endDate: $('#endDate').val(),
                   gameId: args[0]
               }
               $element.bootstrapTable(this.paramsMap.cashRecord, 'refresh');
               break;
            case 'UserTeamGoldlist':
                this.paramsMap.UserTeamGoldlist.queryParams = {
                    startDate: $('#startDate').val(),
                    endDate: $('#endDate').val()
                }
                $element.bootstrapTable(this.paramsMap.UserTeamGoldlist);
                break;
             case 'queryUserGoldScore':
               this.paramsMap.queryUserGoldScore.queryParams = {
                    gameId: args[0]
               }
               $element.bootstrapTable(this.paramsMap.queryUserGoldScore, 'refresh');
                break;
             case 'querycleangold':
                 this.paramsMap.querycleangold.queryParams = {
                     startDate: $('#startDate').val(),
                     endDate: $('#endDate').val()
                 }
                 $element.bootstrapTable(this.paramsMap.querycleangold);
                 break;
             case 'getCleanGoldRecord':
                  this.paramsMap.getCleanGoldRecord.queryParams = {
                      startDate: $('#startDate').val(),
                      endDate: $('#endDate').val()
                  }
                  $element.bootstrapTable(this.paramsMap.getCleanGoldRecord);
                  break;
              case 'getWxPay':
                  this.paramsMap.getWxPay.queryParams = {
                      startDate: $('#startDate').val(),
                      endDate: $('#endDate').val()
                  }
                  $element.bootstrapTable(this.paramsMap.getWxPay);
                  break;
               case 'userGold':
                  this.paramsMap.userGold.queryParams = {
                      startDate: $('#startDate').val(),
                      endDate: $('#endDate').val(),
                      teamNo: args[0]
                  }
                  $element.bootstrapTable(this.paramsMap.userGold, 'refresh');
                  break;
               case 'payGoldRecord':
                   this.paramsMap.payGoldRecord.queryParams = {
                     startDate: $('#startDate').val(),
                     endDate: $('#endDate').val(),
                      selected: args[0]
                   }
                   $element.bootstrapTable(this.paramsMap.payGoldRecord, 'refresh');
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

            cardactiveUser: {
                    url: '/account/cardactiveUser/',
                    queryParams: {},
                    columns: [
                        {
                            field: 'gameId',
                            title: '活跃人数'
                        },
                        {
                            field: 'lastlogonDate',
                            title: '时间',
                            formatter: 'yy-MM-dd'
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
                 userGold: {
                             url: '/User/team/list/',
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
                                   field: 'systemcost',
                                   title: '消耗总额'
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
//                    {
//                          field: 'gameId',
//                          title: '游戏id'
//                      },
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

            GoldClearRecord: {
                  url: '/clear/gold/record/list/',
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
                          field: 'systemCost',
                          title: '金币消耗总额'
                      },
                      {
                          field: 'clearPrice',
                          title: '结算总额'
                      },
                      {
                          title: '操作',
                          content: '<button type="button" class="btn btn-primary btn-sm" onclick="downClearExcels(this)">下载</button>'
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
                     },
                      {
                        title: '操作',
                        content: '<button type="button" class="btn btn-default btn-sm" role="button" ' +
                                  'data-toggle="modal" data-target="#payTimeModel">开房明细</button>'
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
                            title: '统计日期',
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
                        field: 'cardPrice',
                        title: '购卡金额'
                    },
                    {
                        field: 'selected',
                        title: '推荐人'
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

            payGoldRecord: {
                            url: '/admin/payGold/list/',
                            queryParams: {},
                            columns: [
                                {
                                    field: 'presentee',
                                    title: '游戏id'
                                },
                                {
                                    field: 'goldNum',
                                    title: '充币数量'
                                },
                                {
                                    field: 'goldPrice',
                                    title: '金币金额'
                                },
                                {
                                    field: 'selected',
                                    title: '推荐人'
                                },
                                {
                                    field: 'payReason',
                                    title: '备注'
                                },
                                {
                                    field: 'createTime',
                                    title: '充值日期',
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
                            return field ? '<button type="button" class="btn btn-warning btn-sm" onclick="commonJs.group_room_status(this,0)">已禁用</button>' :
                                            '<button type="button" class="btn btn-info btn-sm" onclick="commonJs.group_room_status(this, 1)">开放中</button>';
                        }
                    },
                    {
                        field: 'playerNum',
                        title: '玩家数量',
                    },
                    {
                        title: '操作',
                        content: '<button type="button" class="btn btn-default btn-sm" role="button" data-toggle="modal" data-target="#groupRoomMemberModel">详细</button>' +
                                    '&nbsp;<button type="button" class="btn btn-danger btn-sm" onclick="delGroupRoom(this)">删除</button>'
                    },
                    {
                        field: 'createTime',
                        title: '创建时间',
                        formatter: 'yy-MM-dd HH:mm'
                    },
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
                        content: '<button type="button" class="btn btn-default btn-sm" onclick="delGroupRoomMember(this)">移除</button>'//+
//                                 '&nbsp;<button type="button" id="sq" class="btn btn-success btn-sm" onclick="GiveGroupRoom(this)">授权</button>'
                    },

                ]
            }, queryAgentList: {
                   url: '/admin/getPartner/',
                   queryParams: {},
                   columns: [
                       {
                           field: 'id',
                           title: 'id',
                           hidden: true
                       },
                       {
                           field: 'gameId',
                           title: 'GameID',
                       },
                       {
                           field: 'nickName',
                           title: '昵称'
                       },
                        {
                           field: 'createTime',
                           title: '注册时间',
                           formatter: 'yy-MM-dd HH:mm'
                       },
                       {
                           title: '操作',
                           content: '<button type="button" id="sq" class="btn btn-primary btn-sm" onclick="commonJs.authorizeAgent(this)">授权</button>'
                       }
                     ]
                 },

                 queryPartner: {
                         url: '/admin/cancelPartner/',
                         queryParams: {},
                         columns: [
                             {
                                 field: 'id',
                                 title: 'id',
                                 hidden: true
                             },
                             {
                                 field: 'gameId',
                                 title: 'GameID',
                             },
                             {
                                 field: 'nickName',
                                 title: '昵称'
                             },
                              {
                                 field: 'createTime',
                                 title: '注册时间',
                                 formatter: 'yy-MM-dd HH:mm'
                             },
                             {
                                 title: '操作',
                                 content: '<button type="button" id="sq" class="btn btn-primary btn-sm" onclick="commonJs.authorizeNo(this)">取消授权</button>'+
                             '&nbsp;<button type="button" id="mess" class="btn btn-default btn-sm" role="button" data-toggle="modal" data-target="#agentPartnerMessageModel">详细</button>'
                             }
                           ]
                       },

                       queryPartnerGold: {
                            url: '/admin/cancelPartner/',
                            queryParams: {},
                            columns: [
                                {
                                    field: 'id',
                                    title: 'id',
                                    hidden: true
                                },
                                {
                                    field: 'gameId',
                                    title: 'GameID',
                                },
                                {
                                    field: 'nickName',
                                    title: '昵称'
                                },
                                 {
                                    field: 'createTime',
                                    title: '注册时间',
                                    formatter: 'yy-MM-dd HH:mm'
                                },
                                {
                                    title: '操作',
                                    content: '<button type="button" id="sq" class="btn btn-primary btn-sm" onclick="commonJs.authorizeNo(this)">取消授权</button>'+
                                '&nbsp;<button type="button" id="mess" class="btn btn-default btn-sm" role="button" data-toggle="modal" data-target="#agentPartnerGoldModel">详细</button>'
                                }
                              ]
                          },


                       querySumCard: {
                           url: '/agent/getSumCard',
                           queryParams:{},
                           columns: [
                            {
                                 field: 'id',
                                 title: 'id',
                                 hidden: true
                             },
                             {
                                 field: 'gameId',
                                 title: 'GameID'
                               },
                             {
                                 field: 'nickName',
                                 title: '名字',
                             },
                               {
                                  field: 'leve',
                                  title: '代理商级别',
                              },
                              {
                                  field: 'sumAmount',
                                  title: '团队充值',
                              },
                              {
                                  field: 'scaleMoney',
                                  title: '结算金额',
                               },
                              {
                                   title: '操作',
                                   content: '<button type="button" id="sq" class="btn btn-primary btn-sm" onclick="commonJs.loginAgent(this)">进入后台</button>'
                                }

                         ]
                    },
                        querySumGold: {
                                   url: '/agent/User/list/',
                                   queryParams:{},
                                   columns: [
                                    {
                                         field: 'id',
                                         title: 'id',
                                         hidden: true
                                     },
                                     {
                                         field: 'gameId',
                                         title: 'GameID'
                                       },
                                     {
                                         field: 'nickName',
                                         title: '名字',
                                     },
                                       {
                                          field: 'leve',
                                          title: '代理商级别',
                                      },
                                      {
                                          field: 'sumAmount',
                                          title: '团队消耗金额',
                                      },
                                      {
                                          field: 'scaleMoney',
                                          title: '结算金额',
                                       },
                                      {
                                           title: '操作',
                                           content: '<button type="button" id="sq" class="btn btn-primary btn-sm" onclick="commonJs.loginAgent(this)">进入后台</button>'
                                        }

                                 ]
                            },

                         partnerGoldQuerysubclass: {
                                       url: '/admin/partnerGoldQuerysubclass/',
                                       queryParams:{},
                                       columns: [
                                        {
                                             field: 'id',
                                             title: 'id',
                                             hidden: true
                                         },
                                         {
                                             field: 'gameId',
                                             title: 'GameID'
                                           },
                                         {
                                             field: 'nickName',
                                             title: '名字',
                                         },
                                           {
                                              field: 'leve',
                                              title: '代理商级别',
                                          },
                                          {
                                              field: 'sumAmount',
                                              title: '团队消耗金额',
                                          },
                                          {
                                              field: 'scaleMoney',
                                              title: '结算金额',
                                           }

                                     ]
                                },

                    payTimeModel: {
                              url: '/agent/payTimeModel',
                              queryParams:{},
                              columns: [
                               {
                                    field: 'id',
                                    title: 'id',
                                    hidden: true
                                },
                                {
                                   field: 'gameId',
                                   title: 'GameID'
                                  },
                                {
                                    field: 'nickName',
                                    title: '名字',
                                },
                                  {
                                     field: 'leve',
                                     title: '代理商级别',
                                 },
                                 {
                                     field: 'payPrice',
                                     title: '充值金额',
                                 }
                            ]
                        },queryGameType: {
                           url: '/admin/getGameType/',
                           queryParams:{},
                           columns: [
                            {
                               field: 'gameTypeId',
                               title: '房间类型'
                            },
                            {
                                field: 'gameType',
                                title: '游戏类型'
                            },
                            {
                                field: 'cardCost',
                                title: '房卡消耗'
                            },
                            {
                                field: 'gameTypes',
                                title: '开房局数'
                            },
                            {
                                field: 'createTime',
                                title: '开房日期',
                                formatter: 'yy-MM-dd'
                            },
                         ]
                     },queryLook: {
                         url: '/account/queryLook/',
                         queryParams:{},
                         columns: [
                          {
                             field: 'userId',
                             title: 'id',
                             hidden: true
                          },
                          {
                              field: 'gameId',
                              title: 'ID'
                          },
                          {
                              field: 'nickName',
                              title: '名字'
                          },
                          {
                              field: 'registerDate',
                              title: '日期',
                              formatter: 'yy-MM-dd'
                          },
                          {
                               title: '操作',
                               content: '<button type="button" id="sq" class="btn btn-primary btn-sm" onclick="commonJs.authorizeLookCard(this)">取消授权</button>'
                            }
                       ]
                   },groupQueryRoom: {
                        url: '/groupRoom/queryCountRoom/',
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
                              title: '操作',
                              content: '<button type="button" class="btn btn-default btn-sm" role="button" data-toggle="modal" data-target="#queryCountRoom">详细</button>'
                          },
                        ]
                    },querycount: {
                          url: '/user/countKind/',
                          queryParams: {},
                          columns: [
                                {
                                field: 'gameid',
                                title: 'gameId',
                               },
                               {
                                 field: 'kindid',
                                 title: '类型',
                               },
                               {
                                 field: 'id',
                                 title: '局数',
                                 },
                          ]
                      },queryPartnerPlayer: {
                            url: '/groupRoom/queryCountRoom/',
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
                                  title: '操作',
                                  content: '<button type="button" class="btn btn-default btn-sm" role="button" data-toggle="modal" data-target="#queryPartnerMessage">详细</button>'
                              },
                            ]
                        },queryPartnerMessage: {
                              url: '/user/queryPartnerAgent/',
                              queryParams: {},
                              columns: [
                                     {
                                      field: 'parnerid',
                                      title: '合伙人',
                                     },
                                  {
                                      field: 'kindid',
                                      title: '类型',
                                  },
                                  {
                                    field: 'id',
                                    title: '局数',
                                },

                              ]
                          },partnerQuerysubclass: {
                              url: '/admin/partnerQuerysubclass',
                              queryParams:{},
                              columns: [
                               {
                                    field: 'id',
                                    title: 'id',
                                    hidden: true
                                },
                                {
                                    field: 'gameId',
                                    title: 'GameID'
                                  },
                                {
                                    field: 'nickName',
                                    title: '名字',
                                },
                                  {
                                     field: 'leve',
                                     title: '代理商级别',
                                 },
                                 {
                                     field: 'sumAmount',
                                     title: '团队充值',
                                 },
                                 {
                                     field: 'scaleMoney',
                                     title: '结算金额',
                                  }

                            ]
                       }, giveAgentList: {
                           url: '/admin/giveAgentList/',
                           queryParams: {},
                           columns: [
                               {
                                   field: 'id',
                                   title: 'id',
                                   hidden: true
                               },
                               {
                                   field: 'gameId',
                                   title: 'GameID',
                               },
                               {
                                   field: 'nickName',
                                   title: '昵称'
                               },
                                {
                                   field: 'createTime',
                                   title: '注册时间',
                                   formatter: 'yy-MM-dd HH:mm'
                               },
                               {
                                   title: '操作',
                                   content: '<button type="button" id="sq" class="btn btn-primary btn-sm" onclick="commonJs.updateAiveAgentList(this)">授权</button>'
                               }
                             ]
                         }, GiveQueryPartner: {
                               url: '/admin/queryGiveAgentList/',
                               queryParams: {},
                               columns: [
                                   {
                                       field: 'id',
                                       title: 'id',
                                       hidden: true
                                   },
                                   {
                                       field: 'gameId',
                                       title: 'GameID',
                                   },
                                   {
                                       field: 'nickName',
                                       title: '昵称'
                                   },
                                    {
                                       field: 'createTime',
                                       title: '注册时间',
                                       formatter: 'yy-MM-dd HH:mm'
                                   },
                                   {
                                       title: '操作',
                                       content: '<button type="button" id="sq" class="btn btn-primary btn-sm" onclick="commonJs.updateNoAiveAgentList(this)">取消授权</button>'+
                                        '&nbsp;<button type="button" id="mess" class="btn btn-default btn-sm" role="button" data-toggle="modal" data-target="#agentPartnerMessageModel">详细</button>'
                                   }
                                 ]
                                }, queryAgent: {
                                   url: '/agent/cardGift/list/',
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
                                           field: 'cardPrice',
                                           title: '购卡金额'
                                       },
                                       {
                                           field: 'selected',
                                           title: '推荐人'
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
                               },getUserScore: {
                                    url: '/user/getUserScore/',
                                    queryParams: {},
                                    columns: [
                                        {
                                            field: 'kindid',
                                            title: '类型'
                                        },
                                        {
                                            field: 'id',
                                            title: '局数'
                                        },
                                        {
                                            field: 'score',
                                            title: '分数'
                                        }
                                    ]
                                },queryAllList: {
                                  url: '/redeem/queryAllList/',
                                  queryParams: {},
                                  columns: [
                                      {
                                          field: 'code',
                                          title: '兑换码'
                                      },
                                      {
                                          field: 'card',
                                          title: '房卡'
                                      },
                                      {
                                          field: 'createtime',
                                          title: '创建日期',
                                          formatter: 'yy-MM-dd'
                                      },
                                     {
                                         field: 'endtime',
                                         title: '过期日期',
                                         formatter: 'yy-MM-dd'
                                     }
                                  ]
                              },pastNoModel: {
                                      url: '/redeem/queryPastNoAllList/',
                                      queryParams: {},
                                      columns: [
                                          {
                                              field: 'code',
                                              title: '兑换码'
                                          },
                                          {
                                              field: 'card',
                                              title: '房卡'
                                          },
                                          {
                                            field: 'createtime',
                                            title: '创建日期',
                                            formatter: 'yy-MM-dd'
                                          },
                                          {
                                              field: 'usetime',
                                              title: '使用日期',
                                              formatter: 'yy-MM-dd'
                                          },
                                         {
                                             field: 'endtime',
                                             title: '过期日期',
                                             formatter: 'yy-MM-dd'
                                         }
                                      ]
                                  },queryAllRoom: {
                                      url: '/user/queryAllRoom/',
                                      queryParams: {},
                                      columns: [
                                          {
                                              field: 'gameid',
                                              title: 'gameid'
                                          },
                                          {
                                              field: 'nickName',
                                              title: '名字'
                                          },
                                          {
                                            field: 'qunName',
                                            title: '房间名',
                                          },
                                          {
                                              field: 'roomid',
                                              title: '局数',
                                          }
                                      ]
                                  },getAccredit: {
                                      url: '/admin/querySubsets/',
                                      queryParams: {},
                                      columns: [
                                          {
                                              field: 'gameId',
                                              title: 'gameId'
                                          },
                                          {
                                              field: 'nickName',
                                              title: '名字'
                                          },
                                          {
                                              field: 'createTime',
                                              title: '创建日期',
                                              formatter: 'yy-MM-dd'
                                         },
                                        {
                                             title: '操作',
                                             content: '<button type="button" id="sq" class="btn btn-primary btn-sm" onclick="commonJs.getAccredit(this)">进入后台</button>'
                                         }
                                      ]
                                  },TurntableDaily: {
                                      url: '/Daily/getDaily/',
                                      queryParams: {},
                                      columns: [
                                          {
                                              field: 'turnAwardInstruction',
                                              title: '奖品名称'
                                          },
                                          {
                                              field: 'turnawardno',
                                              title: '奖品序号'
                                          },
                                          {
                                              field: 'turnawardamount',
                                              title: '奖品金额',
                                          },
                                            {
                                                field: 'turnawardprobability',
                                                title: '奖品概率',
                                           },
                                           {
                                               title: '操作',
                                               content: '<button type="button" id="sq" class="btn btn-primary btn-sm" >删除</button>'+
                                                '&nbsp;<button type="button" id="mess" class="btn btn-default btn-sm" role="button" data-toggle="modal" data-target="#DailyModel">编辑</button>'
                                           }
                                      ]
                                  },TurntableIntegral: {
                                          url: '/Integral/getIntegral/',
                                          queryParams: {},
                                          columns: [
                                              {
                                                  field: 'turnawardinstruction',
                                                  title: '奖品名称'
                                              },
                                              {
                                                  field: 'turnawardno',
                                                  title: '奖品序号'
                                              },
                                              {
                                                  field: 'turnawardamount',
                                                  title: '奖品积分',
                                              },
                                                {
                                                    field: 'turnawardgoodamcout',
                                                    title: '奖品个数',
                                               },
                                               {
                                                   title: '操作',
                                                   content: '<button type="button" id="sq" class="btn btn-primary btn-sm" >删除</button>'+
                                                    '&nbsp;<button type="button" id="mess" class="btn btn-default btn-sm" role="button" data-toggle="modal" data-target="#IntegralModel" >编辑</button>'
                                               }
                                          ]
                                      },getWithrawal: {
                                          url: '/rmb/getWithrawal/',
                                          queryParams: {},
                                          columns: [
                                             {
                                                field: 'userid',
                                                title: 'userid',
                                                hidden: true
                                             },
                                              {
                                                  field: 'gameId',
                                                  title: '游戏ID'
                                              },
                                              {
                                                  field: 'nickName',
                                                  title: '名称'
                                              },
                                              {
                                                  field: 'integral',
                                                  title: '积分',
                                              },
                                                {
                                                    field: 'rmb',
                                                    title: '红包',
                                               }
                                          ]
                                      },cashRecord: {
                                          url: '/cash/getCashRecord/',
                                          queryParams: {},
                                          columns: [
                                             {
                                                field: 'userid',
                                                title: 'userid',
                                                hidden: true
                                             },
                                              {
                                                  field: 'gameId',
                                                  title: '游戏ID'
                                              },
                                              {
                                                  field: 'nickName',
                                                  title: '名称'
                                              },
                                              {
                                                  field: 'recordawardamount',
                                                  title: '兑现红包',
                                              },
                                                {
                                                    field: 'playerturntime',
                                                    title: '兑现时间',
                                                    formatter: 'yy-MM-dd'
                                               }
                                          ]
                                      },
                                      UserTeamGoldlist: {
                                              url: '/User/team/Gold/list/',
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
                                                      field: 'systemcost',
                                                      title: '消耗总额'
                                                  },{
                                                      field: 'commission',
                                                      title: '结算总额'
                                                  },
                                                  {
                                                      title: '操作',
                                                      content: '<button type="button" class="btn btn-default btn-sm" role="button" ' +
                                                                  'data-toggle="modal" data-target="#userGoldModel">充值明细</button>'
                                                  }
                                              ]
                                          },queryUserGoldScore: {
                                                  url: '/update/user/gold/',
                                                  queryParams: {},
                                                  columns: [
                                                     {
                                                        field: 'userId',
                                                        title: 'userId',
                                                        hidden: true
                                                     },
                                                      {
                                                          field: 'gameId',
                                                          title: '游戏ID'
                                                      },
                                                      {
                                                          field: 'score',
                                                          title: '分数'
                                                      }
                                                  ]
                                              },querycleangold: {
                                                  url: '/record/Gold/list/',
                                                  queryParams: {},
                                                  columns: [
                                                     {
                                                        field: 'agentGameid',
                                                        title: '代理',
                                                     },
                                                      {
                                                          field: 'playGameid',
                                                          title: '下分代理'
                                                      },
                                                      {
                                                          field: 'score',
                                                          title: '分数'
                                                      },{
                                                         field: 'createTime',
                                                         title: '创建日期',
                                                         formatter: 'yy-MM-dd'
                                                    },
                                                  ]
                                              },getCleanGoldRecord: {
                                                  url: '/admin/clean/gold/list/',
                                                  queryParams: {},
                                                  columns: [
                                                     {
                                                        field: 'agentGameid',
                                                        title: '代理',
                                                     },
                                                      {
                                                          field: 'playGameid',
                                                          title: '下分代理'
                                                      },
                                                      {
                                                          field: 'score',
                                                          title: '分数'
                                                      },{
                                                         field: 'createTime',
                                                         title: '创建日期',
                                                         formatter: 'yy-MM-dd'
                                                    },
                                                  ]
                                              },getWxPay: {
                                                  url: '/wxpay/getWxPay/',
                                                  queryParams: {},
                                                  columns: [
                                                     {
                                                        field: 'gameId',
                                                        title: '代理',
                                                     },
                                                      {
                                                          field: 'priceCard',
                                                          title: '充值金额'
                                                      },
                                                      {
                                                          field: 'payCard',
                                                          title: '充值房卡'
                                                      },{
                                                         field: 'createTime',
                                                         title: '创建日期',
                                                         formatter: 'yy-MM-dd'
                                                    },
                                                  ]
                                              },

        }
    }

    $.fn.tableInit = function(tableName) {
        var args = Array.apply(null, arguments);
        args.shift();

        new TableInit(this, tableName, args);
    }

}(window.jQuery));