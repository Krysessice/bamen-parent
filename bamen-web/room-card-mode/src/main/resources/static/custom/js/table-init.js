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
                this.paramsMap.queryPayOrder.queryParams = {
                    startDate: $('#startDate').val(),
                    endDate: $('#endDate').val()
                }
                $element.bootstrapTable(this.paramsMap.queryPayOrder);
                 break;
            case 'queryRebate':
                 $element.bootstrapTable(this.paramsMap.queryRebate);
                 break;
            case 'queryPayCard':
                 $element.bootstrapTable(this.paramsMap.queryPayCard);
                 break;
             case 'queryAgentList':
                 this.paramsMap.queryAgentList.queryParams = {
                     startDate: $('#startDate').val(),
                     endDate: $('#endDate').val()
                 }
                 $element.bootstrapTable(this.paramsMap.queryAgentList);
                  break;
             case 'queryGiftAgent':
                  $element.bootstrapTable(this.paramsMap.queryGiftAgent);
                  break;
              case 'queryAgentPlayer':
                  $element.bootstrapTable(this.paramsMap.queryAgentPlayer);
                  break;
               case 'queryOne':
                 $element.bootstrapTable(this.paramsMap.queryOne);
                 break;
               case 'queryTwo':
                 $element.bootstrapTable(this.paramsMap.queryTwo);
                 break;
                case 'queryPartner':
                 $element.bootstrapTable(this.paramsMap.queryPartner);
                 break;
                case 'downAgent':
//                   this.paramsMap.downAgent.queryParams = {
//                       startDate: $('#startDate').val(),
//                       endDate: $('#endDate').val()
//                   }
                   $element.bootstrapTable(this.paramsMap.downAgent);
                    break;
                case 'queryAll':
                   $element.bootstrapTable(this.paramsMap.queryAll);
                   break;
                case 'queryAgentDown':
                    this.paramsMap.queryAgentDown.queryParams = {
                                    teamNo: args[0]
                                }
                   $element.bootstrapTable(this.paramsMap.queryAgentDown ,'refresh');
                   break;
               case 'downAgents':
                  $element.bootstrapTable(this.paramsMap.downAgents);
                  break;
               case 'queryAllAgentDown':
                   this.paramsMap.queryAllAgentDown.queryParams = {
                                   teamNo: args[0]
                               }
                  $element.bootstrapTable(this.paramsMap.queryAllAgentDown ,'refresh');
                  break;
               case 'queryCardSum':
                    this.paramsMap.queryCardSum.queryParams = {
                        startDate: $('#startDate').val(),
                        endDate: $('#endDate').val()
                    }
                    $element.bootstrapTable(this.paramsMap.queryCardSum);
                     break;
                case 'queryAgentModel':
                    this.paramsMap.queryAgentModel.queryParams = {
                                     startDate: $('#startDate').val(),
                                     endDate: $('#endDate').val(),
                                     teamNo: args[0]
                                }
                   $element.bootstrapTable(this.paramsMap.queryAgentModel ,'refresh');
                   break;
               case 'querySumCard':
                    this.paramsMap.querySumCard.queryParams = {
                        startDate: $('#startDate').val(),
                        endDate: $('#endDate').val()
                    }
                    $element.bootstrapTable(this.paramsMap.querySumCard);
                     break;
               case 'queryCardforList':
                    this.paramsMap.queryCardforList.queryParams = {
                        startDate: $('#startDate').val(),
                        endDate: $('#endDate').val()
                    }
                    $element.bootstrapTable(this.paramsMap.queryCardforList);
                     break;
               case 'querypayforList':
                    this.paramsMap.querypayforList.queryParams = {
                        startDate: $('#startDate').val(),
                        endDate: $('#endDate').val()
                    }
                    $element.bootstrapTable(this.paramsMap.querypayforList);
                     break;
               case 'queryCardPlayer':
                    $element.bootstrapTable(this.paramsMap.queryCardPlayer);
                    break;
               case 'queryLook':
                     $element.bootstrapTable(this.paramsMap.queryLook);
                     break;
                case 'queryGiveAgent':
                     $element.bootstrapTable(this.paramsMap.queryGiveAgent);
                     break;
                case 'groupPartnerMessage':
                    this.paramsMap.groupPartnerMessage.queryParams = {
                                     startDate: $('#startDate').val(),
                                     endDate: $('#endDate').val(),
                                     account: args[0]
                                }
                   $element.bootstrapTable(this.paramsMap.groupPartnerMessage ,'refresh');
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
                                    field: 'sysAgentId',
                                    title: '被充值者'
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
                                    field: 'cardGift',
                                    title: '赠卡数量',
                                },
                                {
                                    field: 'createTime',
                                    title: '购卡时间',
                                    formatter: 'yy-MM-dd'
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
                                field: 'sysAgentId',
                                title: '代理'
                              },
                            {
                                field: 'firSuperAgentId',
                                title: '上级代理账号'
                            },
                            {
                                field: 'firBonus',
                                title: '上级代理返卡',
                            },
                             {
                                field: 'secSuperAgentId',
                                title: '上上级代理账号'
                            },
                            {
                                field: 'secBonus',
                                title: '上上级代理返卡',
                            },
                            {
                                field: 'createTime',
                                title: '购卡时间',
                                formatter: 'yy-MM-dd'
                            },

                        ]
                    }, queryPayCard: {
                              url: '/agent/queryPayCard/',
                              queryParams: {},
                              columns: [
                               {
                                  field: 'id',
                                  title: 'id',
                                  hidden: true
                              },
                              {
                                  field: 'account',
                                  title: 'account'
                                },
                              {
                                  field: 'nickName',
                                  title: '名字',
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
                                   field: 'cardGift',
                                   title: '赠卡数量',
                               },
                               {
                                   field: 'createTime',
                                   title: '购卡时间',
                                   formatter: 'yy-MM-dd'
                               },
                          ]
                      },queryAgentList: {
                              url: '/admin/queryAgentList/',
                              queryParams: {
                                    F_PARTNER:0
                                },
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
                                field: 'superAgentId',
                                title: '上级代理',
                                },
                               {
                                   field: 'createTime',
                                   title: '购卡时间',
                                   formatter: 'yy-MM-dd'
                               },
                               {
                                   title: '操作',
                                   content: '<button type="button" id="sq" class="btn btn-primary btn-sm" onclick="commonJs.authorize(this)">授权</button>'
                               }
                          ]
                      },queryGiftAgent: {
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
                     },queryOne: {
                               url: '/agent/queryOne/',
                               queryParams: {},
                               columns: [
                               {
                                   field: 'sysAgentId',
                                   title: '代理'
                                 },
                                 {
                                   field: 'firSuperAgentId',
                                   title: '上级代理'
                                 },
                              {
                                  field: 'firBonus',
                                  title: '上级代理返卡',
                               },
                                {
                                    field: 'createTime',
                                    title: '返利时间',
                                    formatter: 'yy-MM-dd'
                                },
                           ]
                       },queryTwo: {
                               url: '/agent/queryTwo/',
                               queryParams: {},
                               columns: [
                               {
                                  field: 'sysAgentId',
                                  title: '代理'
                                },
                               {
                                   field: 'firSuperAgentId',
                                   title: '上级代理'
                                 },
                                 {
                                   field: 'secSuperAgentId',
                                   title: '上上级代理'
                                 },
                              {
                                  field: 'secBonus',
                                  title: '上上级代理返卡',
                               },
                                {
                                    field: 'createTime',
                                    title: '返利时间',
                                    formatter: 'yy-MM-dd'
                                },
                           ]
                       },queryPartner: {
                              url: '/admin/queryPartner/',
                              queryParams: {
                                    F_PARTNER:1
                                },
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
                                field: 'superAgentId',
                                title: '上级代理',
                                },
                               {
                                   field: 'createTime',
                                   title: '购卡时间',
                                   formatter: 'yy-MM-dd'
                               },
                               {
                                  title: '操作',
                                  content: '<button type="button" id="sq" class="btn btn-primary btn-sm" onclick="commonJs.authorizes(this)">取消授权</button>'+
                                 '&nbsp;<button type="button" id="mess" class="btn btn-default btn-sm" role="button" data-toggle="modal" data-target="#groupPartnerMessageModel">详细</button>'
                              }
                            ]
                       },downAgent: {
                              url: '/agent/downAgent/',
                              queryParams: {},
                              columns: [
                               {
                                  field: 'id',
                                  title: 'id',
                                  hidden: true
                              },
                              {
                                  field: 'account',
                                  title: '代理号'
                                },
                              {
                                  field: 'nickName',
                                  title: '名字',
                              },
                              {
                                   field: 'payPrice',
                                   title: '总充值金额'
                               },
                               {
                                   field: 'cardNum',
                                   title: '总购买房卡',
                               },
                                {
                                   field: 'cardGift',
                                   title: '总赠送房卡',
                               },
                               {
                                   field: 'createTime',
                                   title: '创建时间',
                                   formatter: 'yy-MM-dd'
                               },
                                {
                                     title: '操作',
                                     content: '<button type="button" class="btn btn-default btn-sm" role="button" ' +
                                                 'data-toggle="modal" data-target="#payOrderModel">下级</button>'
                                 }
                            ]
                       },queryAll: {
                                  url: '/admin/queryAll/',
                                  queryParams: {},
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
                                    field: 'superAgentId',
                                    title: '上级代理',
                                    },
                                   {
                                       field: 'createTime',
                                       title: '创建时间',
                                       formatter: 'yy-MM-dd'
                                   }
                              ]
                          },queryAgentDown: {
                                     url: '/agent/queryAgentDown/',
                                     queryParams:{

                                     },
                                     columns: [
                                      {
                                         field: 'id',
                                         title: 'id',
                                         hidden: true
                                     },
                                     {
                                         field: 'account',
                                         title: '代理号'
                                       },
                                     {
                                         field: 'nickName',
                                         title: '名字',
                                     },
                                     {
                                         field: 'payPrice',
                                         title: '总充值金额'
                                     },
                                     {
                                         field: 'cardNum',
                                         title: '总购买房卡',
                                     },
                                      {
                                         field: 'cardGift',
                                         title: '总赠送房卡',
                                     },
                                      {
                                          field: 'createTime',
                                          title: '创建时间',
                                          formatter: 'yy-MM-dd'
                                      }

                                   ]
                              },downAgents: {
                                        url: '/agent/downAgents/',
                                        queryParams:{

                                        },
                                        columns: [
                                         {
                                            field: 'id',
                                            title: 'id',
                                            hidden: true
                                        },
                                        {
                                            field: 'account',
                                            title: '代理号'
                                          },
                                        {
                                            field: 'nickName',
                                            title: '名字',
                                        },
                                         {
                                             field: 'createTime',
                                             title: '创建时间',
                                             formatter: 'yy-MM-dd'
                                         }
                                      ]
                                 },queryCardSum: {
                                      url: '/admin/queryCardSum/',
                                      queryParams:{},
                                      columns: [
                                      {
                                          field: 'payPrice',
                                          title: '总充值金额'
                                        },
                                      {
                                          field: 'cardNum',
                                          title: '总购卡数量',
                                      },
                                       {
                                           field: 'cardGift',
                                           title: '总赠卡数量',
                                       },
                                        {
                                             title: '操作',
                                             content: '<button type="button" class="btn btn-default btn-sm" role="button" ' +
                                                         'data-toggle="modal" data-target="#queryAgentModel">充值明细</button>'
                                         }
                                    ]
                               },queryAgentModel: {
                                    url: '/admin/queryAgentModel',
                                    queryParams: {},
                                    columns: [
                                        {
                                            field: 'id',
                                            title: 'id',
                                            hidden: true
                                        },
                                        {
                                            field: 'sysAgentId',
                                            title: '被充值者'
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
                                            field: 'cardGift',
                                            title: '赠卡数量',
                                        },
                                        {
                                            field: 'createTime',
                                            title: '购卡时间',
                                            formatter: 'yy-MM-dd'
                                        },

                                    ]
                                },querySumCard: {
                                       url: '/agent/getSumCard',
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
//                                           {
//                                              field: 'leve',
//                                              title: '代理商级别',
//                                          },
                                          {
                                              field: 'payPrice',
                                              title: '充值金额',
                                          },
                                          {
                                            field: 'cardNum',
                                            title: '充值房卡',
                                         },
                                         {
                                             field: 'createTime',
                                             title: '购卡时间',
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
                                  }, queryCardforList: {
                                          url: '/admin/queryCardforList',
                                          queryParams: {},
                                          columns: [
                                           {
                                              field: 'id',
                                              title: 'id',
                                              hidden: true
                                          },
                                           {
                                                 field: 'sysAgentId',
                                                 title: '账号'
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
                                               field: 'cardGift',
                                               title: '赠卡数量',
                                           },
                                           {
                                               field: 'createTime',
                                               title: '购卡时间',
                                               formatter: 'yy-MM-dd'
                                           },
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
                                   },queryGiveAgent: {
                                         url: '/account/queryGiveAgent/',
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
                                               content: '<button type="button" id="sq" class="btn btn-primary btn-sm" onclick="commonJs.authorizeGiveAgent(this)">取消授权</button>'
                                            }
                                       ]
                                   },groupPartnerMessage: {
                                       url: '/admin/groupPartnerMessage',
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
    //                                           {
    //                                              field: 'leve',
    //                                              title: '代理商级别',
    //                                          },
                                          {
                                              field: 'payPrice',
                                              title: '充值金额',
                                          },
                                          {
                                            field: 'cardNum',
                                            title: '充值房卡',
                                         },
                                         {
                                             field: 'createTime',
                                             title: '购卡时间',
                                             formatter: 'yy-MM-dd'
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