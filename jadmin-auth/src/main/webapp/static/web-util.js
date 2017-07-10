var webUtil = {
    /** 同级页面跳转,可接受无限个参数， */
    goUrl: function () {
        var args = arguments;
        var str = "";
        for (var i = 0; i < args.length; i++) {
            if (i == 0) {
                if (i != args.length - 1) {
                    str += args[i] + "?";
                    continue;
                }
            }
            if (i % 2 != 0) {
                str += args[i] + "=";
            } else {
                str += args[i];
                if (i != args.length - 1) {
                    str += "&";
                }
            }
        }
        window.location.href = str;
    },

    /** 父级页面跳转,可接受无限个参数 */
    goParentUrl: function () {
        var args = arguments;
        var str = "";
        for (var i = 0; i < args.length; i++) {
            if (i == 0) {
                if (i != args.length - 1) {
                    str += args[i] + "?";
                    continue;
                }
            }
            if (i % 2 != 0) {
                str += args[i] + "=";
            } else {
                str += args[i];
                if (i != args.length - 1) {
                    str += "&";
                }
            }
        }
        parent.window.location.href = str;
    },

    /** checkbox 全不选 ，全选，反选 */
    checkBoxSelect: function (obj, name) {
        var checkboxArr = document.getElementsByName(name);
        for (var i = 0; i < checkboxArr.length; i++) {
            var checkboxObj = checkboxArr[i];
            checkboxObj.checked = obj.checked;
        }
    },
    /** 正确框，带绿色对勾图标 */
    info: function (msg, callback) {
        return layer.alert(msg,
            {icon:1},
            function (index) {
                layer.close(index);
                webUtil.callOnClick(callback);
            });
    },

    /** 正确框，带绿色对勾图标 */
    msg: function (msg, callback) {
        layer.msg(msg,
            {
                time: 3000,
                offset: 't'
            },
            function () {
                webUtil.callOnClick(callback);
            })
    },

    /** 错误框，带红色错误图标 */
    error: function (msg, callback) {
        return layer.alert(msg, {icon: 2, anim: 6}, function (index) {
            layer.close(index);
            webUtil.callOnClick(callback);
        });
    },

    /** 自定义确认框 */
    confirm: function (msg, callbackOk, callbackCancel) {

        // 询问框
        return layer.confirm(msg, {
            icon: 3
        }, function (index) {
            webUtil.callOnClick(callbackOk);
            layer.close(index);
        }, function (index) {
            webUtil.callOnClick(callbackCancel);
            layer.close(index);
        });
    },

    /** 开启蒙板 */
    loading: function () {
        return layer.load(1, {shade: [0.3, '#393D49']}); //0代表加载的风格，支持0-2
    },

    /** 关闭弹出框 */
    closeLoading: function (loadingObj) {
        layer.close(loadingObj);
    },

    /** 弹出框-根据url确定打开页面 */
    openByUrl: function (_title, _width, _height, _url) {
        var width = _width + "px";
        var height = _height + "px";
        return layer.open({
            type:2,
            title: _title,
            shadeClose: true,
            shade: [0.3, '#393D49'],
            area: [width, height],
            content: _url //iframe的url
        });

    },

    /** 弹出框-根据页面dom元素打开页面 */
    openByDom: function (_title, _width, _height, _domId) {


        var width = _width + "px";
        var height = _height + "px";

        return layer.open({
            type: 1,
            area: [width, height],
            shadeClose: true,
            shift: 1,
            shade: [0.8, '#393D49'],
            title: [_title, 'background: rgb(0, 153, 204);color:#fff;'],
            content: '#' + _domId
        });


    },

    /** 回调函数 */
    callOnClick: function (callback) {// private
        if (typeof (callback) == 'function') {
            callback();
        }
    },

    /** 去除字符串左右空格 */
    trim: function (str) {
        if (typeof (str) == "string") {
            var tempLtrim = str.replace(/(^\s*)/g, "");// 去掉左空格
            var tempRtrim = tempLtrim.replace(/(\s*$)/g, "");//
            return tempRtrim;
        } else {
            return str;
        }
    },

    /** 剔除非数字字符 */
    rNotDigt: function (obj) {
        var reg = /[^0-9]*/g;
        obj.value = obj.value.replace(reg, '');
    },

    /** 快捷键 */
    IsDigit: function () {
        return ((event.keyCode >= 48) && (event.keyCode <= 57));
    },

    /**
     * 根据checkbox的name，拼接成指定type的字符串 name：checkbox的name type：拼接类型（比如","）
     */
    getCheckedBoxsValByName: function (name, type) {
        var str = "";
        var $arr = $("input[name='" + name + "']:checked");
        $arr.each(function (i, obj) {
            if ((i + 1) == $arr.length) {
                str += $(obj).val();
            } else {
                str += $(obj).val() + type;
            }
        });
        return str;
    },
    getCheckedBoxsIdByName: function (name, type) {
        var str = "";
        var $arr = $("input[name='" + name + "']:checked");
        $arr.each(function (i, obj) {
            if ((i + 1) == $arr.length) {
                str += $(obj).attr("id");
            } else {
                str += $(obj).attr("id") + type;
            }
        });
        return str;
    },
    /**
     * 把数组arr内的元素拼接成以指定type分隔的字符串（例如：aaa,bbb,cccs） arr:数组
     * attr:如果数组内是对象形式，此参数则是指定对象的某个属性 type:分隔符
     */
    arrayToStr: function (arr, attr, type) {
        var str = "";
        for (var i = 0; i < arr.length; i++) {
            if (attr == null) {
                if ((i + 1) == arr.length) {
                    str += arr[i];
                } else {
                    str += arr[i] + type;
                }
            } else {
                if ((i + 1) == arr.length) {
                    str += eval("arr[i]." + attr);
                } else {
                    str += eval("arr[i]." + attr) + type;
                }
            }
        }
        return str;
    },
    /**
     * 根据checkbox的name，获得选中的长度 name：checkbox的name
     */
    getCheckedBoxsLengthByName: function (name) {
        return $("input[name='" + name + "']:checked").length;
    },
    /**
     * 判断是否为空，空返回true val：需要判断的值
     */
    isEmpty: function (val) {
        if (val == null || webUtil.trim(val).length < 1) {
            return true;
        } else {
            return false;
        }
    },
    /**
     * 给指定的对象改变class样式 obj：对象 removeClass：需要移除的class newClass：需要添加的class
     */
    changeClass: function (obj, removeClass, newClass) {
        $(obj).removeClass(removeClass);
        $(obj).addClass(newClass);
    },
    /**
     * 计算两个日期的天数差 startDate：开始时间 endDate：结束时间
     */
    getDateDiff: function (startDate, endDate) {
        var re = /^(\d{4})\S(\d{1,2})\S(\d{1,2})$/;
        var dt1, dt2;
        if (re.test(startDate)) {
            dt1 = new Date(RegExp.$1, RegExp.$2 - 1, RegExp.$3);
        }
        if (re.test(endDate)) {
            dt2 = new Date(RegExp.$1, RegExp.$2 - 1, RegExp.$3);
        }
        return Math.floor((dt2 - dt1) / (1000 * 60 * 60 * 24));
    },
    /**
     * 格式化金额为2位小数 price：需要格式化的金额
     */
    price_format: function (price) {
        // if(typeof(PRICE_FORMAT) == 'undefined'){
        // PRICE_FORMAT = '&yen;%s';
        // }
        return webUtil.number_format(price, 2);
        // return PRICE_FORMAT.replace('%s', price);
    },
    /**
     * 私有方法，禁止直接调用 num：需要格式化的金额 ext：小数点位数
     */
    number_format: function (num, ext) {
        if (ext < 0) {
            return num;
        }
        num = Number(num);
        if (isNaN(num)) {
            num = 0;
        }
        var _str = num.toString();
        var _arr = _str.split('.');
        var _int = _arr[0];
        var _flt = _arr[1];
        if (_str.indexOf('.') == -1) {
            /* 找不到小数点，则添加 */
            if (ext == 0) {
                return _str;
            }
            var _tmp = '';
            for (var i = 0; i < ext; i++) {
                _tmp += '0';
            }
            _str = _str + '.' + _tmp;
        } else {
            if (_flt.length == ext) {
                return _str;
            }
            /* 找得到小数点，则截取 */
            if (_flt.length > ext) {
                _str = _str.substr(0, _str.length - (_flt.length - ext));
                if (ext == 0) {
                    _str = _int;
                }
            } else {
                for (var i = 0; i < ext - _flt.length; i++) {
                    _str += '0';
                }
            }
        }
        return _str;
    },
    /**
     * 删除js数组中指定的数值 array：源数组 value：待删除数值
     */
    remove: function (array, value) {
        array.splice($.inArray(value, array), 1);
    }
};
