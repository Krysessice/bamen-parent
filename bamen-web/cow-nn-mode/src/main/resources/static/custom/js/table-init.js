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

            case 'getOfficeMessage':
                $element.bootstrapTable(this.paramsMap.getOfficeMessage);
                 break;
             case 'queryGiftAgent':
                  $element.bootstrapTable(this.paramsMap.queryGiftAgent);
                  break;
              case 'queryAgentPlayer':
                  $element.bootstrapTable(this.paramsMap.queryAgentPlayer);
                  break;
               case 'querySumCard':
                    this.paramsMap.querySumCard.queryParams = {
                        startDate: $('#startDate').val(),
                        endDate: $('#endDate').val()
                    }
                    $element.bootstrapTable(this.paramsMap.querySumCard);
                     break;

               case 'querypayforList':
                    this.paramsMap.querypayforList.queryParams = {
                        startDate: $('#startDate').val(),
                        endDate: $('#endDate').val()
                    }
                    $element.bootstrapTable(this.paramsMap.querypayforList);
                     break;
                 case 'queryCardPlayer':
                    this.paramsMap.queryCardPlayer.queryParams = {
                        startDate: $('#startDate').val(),
                        endDate: $('#endDate').val()
                    }
                    $element.bootstrapTable(this.paramsMap.queryCardPlayer);
                     break;

                case 'groupPartnerMessage':
                    this.paramsMap.groupPartnerMessage.queryParams = {
                                     startDate: $('#startDate').val(),
                                     endDate: $('#endDate').val(),
                                     account: args[0]
                                }
                   $element.bootstrapTable(this.paramsMap.groupPartnerMessage ,'refresh');
                   break;
                case 'getPrice':
                     $element.bootstrapTable(this.paramsMap.getPrice);
                     break;
                case 'getOfficeAgentPayMessage':
                    this.paramsMap.getOfficeAgentPayMessage.queryParams = {
                                     startDate: $('#startDate').val(),
                                     endDate: $('#endDate').val(),
                                     superAgentId: args[0]
                                }
                   $element.bootstrapTable(this.paramsMap.getOfficeAgentPayMessage ,'refresh');
                   break;
                 case 'getDetail':
                    this.paramsMap.getDetail.queryParams = {
                        startDate: $('#startDate').val(),
                        endDate: $('#endDate').val()
                    }
                    $element.bootstrapTable(this.paramsMap.getDetail);
                     break;
                case 'salesmanPayOrderModel':
                    this.paramsMap.salesmanPayOrderModel.queryParams = {
                                     startDate: $('#startDate').val(),
                                     endDate: $('#endDate').val(),
                                     superAgentId: args[0]
                                }
                   $element.bootstrapTable(this.paramsMap.salesmanPayOrderModel ,'refresh');
                   break;
                case 'clearRecord':
                   $element.bootstrapTable(this.paramsMap.clearRecord);
                   break;
               case 'getAgentPay':
                   this.paramsMap.getAgentPay.queryParams = {
                       startDate: $('#startDate').val(),
                       endDate: $('#endDate').val()
                   }
                   $element.bootstrapTable(this.paramsMap.getAgentPay);
                    break;
               case 'cardCostUser':
                   this.paramsMap.cardCostUser.queryParams = {
                       startDate: $('#startDate').val(),
                       endDate: $('#endDate').val()
                   }
                   $element.bootstrapTable(this.paramsMap.cardCostUser);
                   break;
               case 'payOrderPerday':
                   $element.bootstrapTable(this.paramsMap.payOrderPerday);
                   break;
             case 'payOrder':
                 this.paramsMap.payOrder.queryParams = {
                     startDate: $('#startDate').val(),
                     endDate: $('#endDate').val()
                 }
                 $element.bootstrapTable(this.paramsMap.payOrder);
                 break;
             case 'queryGiveAgent':
                $element.bootstrapTable(this.paramsMap.queryGiveAgent);
                break;
            }
        },

        paramsMap: {
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
                                    title: '操作',
                                    content: '<button type="button" class="btn btn-primary btn-sm" onclick="downClearExcel(this)">下载</button>'
                                }
                            ]
                        },

                  cardCostUser: {
                         url: '/admin/cardCost/User/list/',
                         queryParams: {},
                         columns: [
                             {
                                 field: 'account',
                                 title: '新增'
                             },
                              {
                                 field: 'createTime',
                                 title: '统计日期',
                                 formatter: 'yy-MM-dd'
                             }
                         ]
                     },
                   payOrderPerday: {
                       url: '/admin/payOrder/list/',
                       queryParams: {},
                       columns: [
                           {
                               field: 'payPrice',
                               title: '总充值金额'
                           },
                           {
                               field: 'cardNum',
                               title: '总购买钻石',
                           },
                           {
                               field: 'createTime',
                               title: '充值日期',
                               formatter: 'yy-MM-dd'
                           }
                       ]
                   },

               payOrder: {
                           url: '/agent/pay/list/',
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
                         queryGiftAgent: {
                              url: '/agent/queryGiftAgent/',
                              queryParams: {},
                              columns: [
                               {
                                  field: 'id',
                                  title: 'id',
                                  hidden: true
                              },
                              {
                                  field: 'account',
                                  title: '赠送者'
                                },
                                {
                                  field: 'presentee',
                                  title: '受赠者'
                                },
                             {
                                 field: 'cardNum',
                                 title: '赠卡数量',
                              },
                              {
                                field: 'giftReason',
                                title: '备注',
                                },
                               {
                                   field: 'createTime',
                                   title: '赠卡时间',
                                   formatter: 'yy-MM-dd'
                               },
                          ]
                      },queryAgentPlayer: {
                             url: '/agent/queryAgentPlayer/',
                             queryParams: {},
                             columns: [
                              {
                                 field: 'id',
                                 title: 'id',
                                 hidden: true
                             },
                             {
                                 field: 'presenter',
                                 title: '赠送者'
                               },
                               {
                                 field: 'presentee',
                                 title: '受赠者'
                               },
                            {
                                field: 'cardNum',
                                title: '赠卡数量',
                             },
                             {
                               field: 'giftReason',
                               title: '备注',
                               },
                              {
                                  field: 'createTime',
                                  title: '赠卡时间',
                                  formatter: 'yy-MM-dd'
                              },
                         ]
                     },payTimeModel: {
                                         url: '/agent/payTimeModel',
                                         queryParams:{},
                                         columns: [
                                          {
                                               field: 'id',
                                               title: 'id',
                                               hidden: true
                                           },
                                           {
                                               field: 'account',
                                               title: '账号'
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
                                  }, querypayforList: {
                                         url: '/admin/querypayforList',
                                         queryParams: {},
                                         columns: [
                                          {
                                             field: 'id',
                                             title: 'id',
                                             hidden: true
                                         },
                                          {
                                                field: 'presenter',
                                                title: '赠送者'
                                            },
                                          {
                                              field: 'presentee',
                                              title: '受赠者'
                                          },
                                          {
                                              field: 'cardNum',
                                              title: '赠卡数量',
                                          },
                                           {
                                              field: 'giftReason',
                                              title: '备注',
                                          },
                                          {
                                              field: 'createTime',
                                              title: '购卡时间',
                                              formatter: 'yy-MM-dd'
                                          },
                                     ]
                                 }, queryCardPlayer: {
                                       url: '/admin/queryCardPlayer',
                                       queryParams: {},
                                       columns: [
                                        {
                                           field: 'id',
                                           title: 'id',
                                           hidden: true
                                       },
                                        {
                                              field: 'presenter',
                                              title: '赠送者'
                                          },
                                        {
                                            field: 'presentee',
                                            title: '受赠者'
                                        },
                                        {
                                            field: 'cardNum',
                                            title: '赠卡数量',
                                        },
                                         {
                                            field: 'giftReason',
                                            title: '备注',
                                        },
                                        {
                                            field: 'createTime',
                                            title: '购卡时间',
                                            formatter: 'yy-MM-dd'
                                        },
                                   ]
                               },getPrice: {
                                        url: '/Price/getPrice/',
                                        queryParams:{},
                                        columns: [
                                         {
                                              field: 'id',
                                              title: 'id',
                                              hidden: true
                                          },{
                                            field: 'image',
                                            title: '',
                                            align: 'center',
                                            formatter: function(value,row,index){
                                                return '<img src="/img/zs.jpg" class="img-rounded" />';
                                            }
                                          },
                                          {
                                              field: 'rmb',
                                              align: 'center',
                                              title: '金额'
                                            },
                                          {
                                              field: 'drill',
                                              align: 'center',
                                              title: '钻石',
                                            }, {
                                             title: '操作',
                                             content: '<button type="button" id="sq" class="btn btn-primary btn-sm" onclick="commonJs.payZs(this)">购买</button>'
                                          }
                                      ]
                                 },getOfficeMessage: {
                                        url: '/admin/getOfficeMessage/',
                                        queryParams:{},
                                        columns: [
                                         {
                                            field: 'id',
                                            title: 'id',
                                            hidden: true
                                         },
                                         {
                                             field: 'account',
                                             title: '账号'
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
                                              content: '<button type="button" class="btn btn-default btn-sm" role="button" ' +
                                                          'data-toggle="modal" data-target="#payOrderModel">充值明细</button>'+
                                                          '&nbsp;&nbsp;<button type="button" id="sq" class="btn btn-primary btn-sm" onclick="commonJs.loginAgent(this)">进后台</button>'
                                          }
                                      ]
                                  },getOfficeAgentPayMessage: {
                                           url: '/admin/getOfficeAgentPayMessage/',
                                           queryParams:{},
                                           columns: [
                                            {
                                               field: 'id',
                                               title: 'id',
                                               hidden: true
                                            },
                                            {
                                                field: 'account',
                                                title: '账号'
                                            },
                                            {
                                                field: 'nickName',
                                                title: '名字'
                                            },
                                            {
                                                field: 'payPrice',
                                                title: '充值金额'
                                            },
                                            {
                                                field: 'cardNum',
                                                title: '钻石'
                                            },
                                            {
                                                field: 'createTime',
                                                title: '充值日期',
                                                formatter: 'yy-MM-dd'
                                            }
                                         ]
                                     },getDetail: {
                                             url: '/rebate/getDetail/',
                                             queryParams:{},
                                             columns: [
                                              {
                                                 field: 'id',
                                                 title: 'id',
                                                 hidden: true
                                              },
                                              {
                                                  field: 'account',
                                                  title: '账号'
                                              },
                                              {
                                                  field: 'nickName',
                                                  title: '名字'
                                              },
                                              {
                                                  field: 'payPrice',
                                                  title: '充值'
                                              },
                                              {
                                                  field: 'rebateMoney',
                                                  title: '返利'
                                              },
                                              {
                                                  field: 'createTime',
                                                  title: '充值/返利日期',
                                                  formatter: 'yy-MM-dd'
                                              },

                                            {
                                                 title: '操作',
                                                 content: '<button type="button" id="sq" class="btn btn-primary btn-sm" onclick="commonJs.loginAgent(this)">进后台</button>'
                                             }
                                           ]
                                       },salesmanPayOrderModel: {
                                          url: '/agent/salesmanPayOrderModel/',
                                          queryParams:{},
                                          columns: [
                                           {
                                              field: 'id',
                                              title: 'id',
                                              hidden: true
                                           },
                                           {
                                               field: 'account',
                                               title: '账号'
                                           },
                                           {
                                               field: 'nickName',
                                               title: '名字'
                                           },
                                           {
                                               field: 'payPrice',
                                               title: '充值金额'
                                           },
                                           {
                                               field: 'createTime',
                                               title: '充值日期',
                                               formatter: 'yy-MM-dd'
                                           },
                                        ]
                                    },salesmanPayOrderModel: {
                                       url: '/agent/getSalesmanPayOrder/',
                                       queryParams:{},
                                       columns: [
                                        {
                                           field: 'id',
                                           title: 'id',
                                           hidden: true
                                        },
                                        {
                                            field: 'account',
                                            title: '账号'
                                        },
                                        {
                                            field: 'nickName',
                                            title: '名字'
                                        },
                                        {
                                            field: 'payPrice',
                                            title: '充值金额'
                                        },
                                        {
                                            field: 'createTime',
                                            title: '充值日期',
                                            formatter: 'yy-MM-dd'
                                        },
                                     ]
                                 },getAgentPay: {
                                     url: '/pay/sum/',
                                     queryParams:{},
                                     columns: [
                                      {
                                         field: 'id',
                                         title: 'id',
                                         hidden: true
                                      },
                                      {
                                          field: 'account',
                                          title: '账号'
                                      },
                                      {
                                          field: 'nickName',
                                          title: '名字'
                                      },
                                      {
                                          field: 'payPrice',
                                          title: '充值金额'
                                      },
                                      {
                                          field: 'createTime',
                                          title: '充值日期',
                                          formatter: 'yy-MM-dd'
                                      },
                                   ]
                               },queryGiveAgent: {
                                     url: '/account/queryGiveAgent/',
                                     queryParams:{},
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
                                          field: 'nickName',
                                          title: '名字'
                                      },
                                      {
                                          field: 'loveLiness',
                                          title: '状态',
                                          content: function(field) {
                                             return field ? '<button type="button" class="btn btn-warning btn-sm" onclick="commonJs.love_less_status(this,0)">已授权</button>' :
                                                             '<button type="button" class="btn btn-info btn-sm" onclick="commonJs.love_less_status(this, 1)">授权</button>';
                                         }

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