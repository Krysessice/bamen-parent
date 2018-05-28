(function ($) {

    var BootstrapTable = function(element, params) {

        this.init(element, params);
    }

    BootstrapTable.prototype = {

        params: {},

        options: {
            bootstrapMajorVersion: 3,
            numberOfPages: 5,
            totalPages: 1,
            shouldShowPage: true,
            onPageClicked: null
        },

        init: function(element, params) {

            this.$element = $(element);
            this.params = params;

            this.renderTHead(this.$element);
            this.renderTBody(this.$element, this.renderPaginator);
        },

        renderTHead: function($element) {

            $element.find('thead').detach();
            $element.find('tbody').detach();
            $element.append('<thead><tr></tr></thead><tbody></tbody>');
            var $tr = $element.find('thead tr');

            $(this.params.columns).each(function(index, column) {

                if (column.hidden)
                    $tr.append("<th style='display: none;'>" + column.title + "</th>");
                else
                    $tr.append("<th>" + column.title + "</th>");
            })
        },

        renderTBody: function($element, fn) {

            var params = this.params;
            $.ajax({
                type: params.method ? params.method : 'get',
                url: params.url,
                dataType: params.dataType ? params.dataType : 'json',
                data: params.queryParams ? params.queryParams : {},
                context: this,
                error: function(XMLHttpRequest, errorMsg, exception) {

                },
                success: function(data) {

                    if (!data.status)
                        return alert(data.msg);

                    this.createGrid($element, data.content.list);

                    if (fn) fn(this, $element, data.content);
                }
            })
        },

        renderPaginator: function(_this, $element, page) {

            var params = _this.params;
            $.extend(_this.options, {
                totalPages: page.pages,
                shouldShowPage: page.pages <= 1 ? false : true,
                onPageClicked: function (event, originalEvent, type, page) {
                    params.queryParams.page = page;
                    _this.renderTBody($element);
                }
            });
            $element.next().find('ul').bootstrapPaginator(_this.options);
        },

        createGrid: function($element, data) {
            var columns = this.params.columns,
                tbody = $element.find('tbody');
            var reg = /^(\d+)-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;

            tbody.empty();

            if (!data || data.length == 0)
                return tbody.append('<tr><td colspan=' + columns.length + '>暂无数据</td></tr>');

            var $tr, field;
            $(data).each(function(index, item) {

                $tr = "<tr>";
                $(columns).each(function(index, column) {
                    field = item[column.field];

                    var c_content = column.content;
                    if (typeof c_content == 'string')
                        return $tr += "<td>" + c_content + "</td>";
                    else if ((typeof c_content) == 'function')
                        return $tr += "<td>" + c_content(field) + "</td>";

                    if (field == null)
                        return $tr += "<td name='" + column.field + "'>" + '<font color="grey"><i>未填写</i></font>' + "</td>";
                    if ((typeof field == 'string') && field.match(reg)){
                        field = column.formatter ? new Date(field).format(column.formatter) : field;
                    }else if(typeof column.formatter == 'function'){
                        return $tr += "<td name='" + column.field + "'>" + column.formatter(field) + "</td>";
                    }

                    if (column.hidden)
                        $tr += "<td style='display: none;' name='" + column.field + "'>" + field + "</td>";
                    else
                        $tr += "<td name='" + column.field + "'>" + field + "</td>";
                });
                $tr += "</tr>";
                tbody.append($tr);
            });
        },

        reloadGrid: function(_this, $element, args) {

            $.extend(_this.params, args[0]);
            _this.renderTBody($element, _this.renderPaginator);
        }

    }

    $.fn.bootstrapTable = function(obj) {
        var args = Array.apply(null, arguments);
        args.shift();

        var $this = $(this), data = $this.data('bootstrapTable');

        if (typeof args[0] === 'string' && args[0] === 'refresh')
            return $this.data('bootstrapTable', (data = new BootstrapTable(this, typeof obj === 'object' ? obj : {})));

        if (!data)
            return $this.data('bootstrapTable', (data = new BootstrapTable(this, typeof obj === 'object' ? obj : {})));

        if (typeof obj === 'string' && typeof data[obj] === 'function')
            data[obj](data, $this, args);
    }

    $.fn.bootstrapTable.Constructor = BootstrapTable;

}(window.jQuery));