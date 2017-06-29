<link href="${ctx}/static/zui/lib/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/static/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
<script src="${ctx}/static/zui/lib/chosen/chosen.js"></script>
<script src="${ctx}/static/zTree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('select.chosen-select').chosen({
            no_results_text: '没有找到',    // 当检索时没有找到匹配项时显示的提示文本
            disable_search_threshold: 10, // 10 个以下的选择项则不显示检索框
            search_contains: true         // 从任意位置开始检索
        });

    });

    /**
     * 同步方法
     **/
    function queryMenuTree(projectCode) {
        var loading = webUtil.loading();

        $.ajax({
            url: "${ctx }/menu/queryMenuList",
            type: 'post',
            data: {'projectCode': projectCode},
            dataType: 'json',
            async: false,
            traditional: true,
            success: function (rsData) {
                webUtil.closeLoading(loading);
                if (rsData.code == 'SUCCESS') {
                    webUtil.msg("菜单加载成功", function () {
                    });

                    initTree(rsData.data.menuList);
                } else {
                    webUtil.msg(rsData.message, null);
                }
            }
        });


    }


    function initTree(data) {
        /**
         * 初始化树的数据
         */
        var zNodes = data;
        /**
         * 设置树的参数
         */
        var zTree;


        var setting = {
            view: {
                showLine: true
            },
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: {"Y": "ps", "N": "ps"}
            },
            data: {
                simpleData: {
                    idKey: 'id',
                    pIdKey: 'pid',
                    rootPId: '0',
                    enable: true
                },
                key: {
                    name: 'name'
                }
            },
            callback: {
                onCheck: getChecked,
//            onDrop: zTreeOnDrop
            },
            edit: {
                enable: true,
                showRemoveBtn: false,
                showRenameBtn: false,
                drag: {
                    isCopy: false,
                    isMove: true
                }
            }
        };
        zTree = $.fn.zTree.init($('#zTree'), setting, zNodes);
        zTree.expandAll(true);
    }


    function getChecked() {
        var zTree = $.fn.zTree.getZTreeObj("zTree");
        var nodes = zTree.getCheckedNodes(true);
        var menuIdArray = new Array();
        for (var i = 0; i < nodes.length; i++) {
            menuIdArray[i] = nodes[i].id;
            console.log("节点0 name:%s   id:%s\n", nodes[i].menuName, nodes[i].id);
        }
        $('#menuIds').val(menuIdArray.join(","));
        $('#menuIds').isValid();
    }

    function save() {
        $('#user_form').isValid(function (v) {
                    if (v) {
                        webUtil.confirm("确定保存吗？", function () {
                            $.ajax({
                                url: "${ctx }/user/add",
                                type: 'post',
                                data: $('#user_form').serialize(),
                                dataType: 'json',
                                traditional: true,
                                success: function (rsData) {
                                    if (rsData.code == 'SUCCESS') {
                                        webUtil.info("操作成功", function () {
                                            gobackAnimation();
                                            setTimeout(function () {
                                                loadListPage();
                                            }, 500)
                                        });
                                    } else {
                                        webUtil.error(rsData.message, null);
                                    }
                                }
                            });
                        }, null);
                    } else {
                        $('#formDiv').removeClass().addClass("animated shake");
                    }
                }
        );

    }

    function goback() {
        gobackAnimation();
        setTimeout(function () {
            loadListPage();
        }, 500)

    }
    function gobackAnimation() {
        $('#userNameDiv').removeClass("animated fadeInLeft").addClass("animated fadeOutLeft");
        $('#realNameDiv').removeClass("animated fadeInLeft").addClass("animated fadeOutLeft");
        $('#projectCodeDiv').removeClass("animated fadeInLeft").addClass("animated fadeOutLeft");
        $('#submitDiv').removeClass("animated fadeInLeft").addClass("animated fadeOutLeft");

        $('#passWordDiv').removeClass("animated fadeInRight").addClass("animated fadeOutRight");
        $('#mobileDiv').removeClass("animated fadeInRight").addClass("animated fadeOutRight");
        $('#menuDiv').removeClass("animated fadeInRight").addClass("animated fadeOutRight");
    }

</script>


<ol class="breadcrumb">
    <li><a href="javascript:void(0);">首页</a></li>
    <li><a href="javascript:void(0);">系统管理</a></li>
    <li><a href="javascript:void(0)" onclick="goback()">用户管理</a></li>
    <li class="active">添加</li>
</ol>

</p>
<form class="form-horizontal" name="user_form" id="user_form"
      data-validator-option="{timely:2, theme:'simple_right'}">


    <div id="userNameDiv" class="form-group animated fadeInLeft">
        <label class="col-sm-2 required">用户名</label>
        <div class="col-md-6 col-sm-10">
            <input type="text" class="form-control" id="userName" name="jadminUser.userName"
                   placeholder="3-20位字母、数字"
                   data-rule="用户名:required;username;"
                   data-rule-username="[/^[\w\d]{3,20}$/, '请输入3-20位数字和字母组合']"
            >
        </div>
    </div>
    <div id="passWordDiv" class="form-group animated fadeInRight">
        <label class="col-sm-2 required">密码</label>
        <div class="col-md-6 col-sm-10">
            <input type="password" class="form-control" id="passWord" name="jadminUser.passWord"
                   placeholder="10位以上的字母、数字、符号"
                   data-rule="密码:required;password;"
                   data-rule-password="[/^[\w\d]{10,30}$/, '请输入10-30位数字和字母组合']">
        </div>
    </div>
    <div id="realNameDiv" class="form-group animated fadeInLeft">
        <label class="col-sm-2 required">真实姓名</label>
        <div class="col-md-6 col-sm-10">
            <input type="text" class="form-control" id="realName" name="jadminUser.realName" placeholder="使用人的真实姓名"
                   data-rule="真实姓名:required;">
        </div>
    </div>
    <div id="mobileDiv" class="form-group animated fadeInRight">
        <label class="col-sm-2 ">手机号</label>
        <div class="col-md-6 col-sm-10">
            <input type="text" class="form-control" id="mobile" name="jadminUser.mobile" placeholder="使用人手机号">
        </div>
    </div>
    <div id="projectCodeDiv" class="form-group animated fadeInLeft">
        <label class="col-sm-2 required">项目</label>
        <div class="col-md-6 col-sm-10">
            <select name="jadminUser.projectCode" data-placeholder="选择一个所属项目..." class="chosen-select form-control"
                    tabindex="2" onchange="queryMenuTree(this.value);"
                    data-rule="项目:required;" data-target="#projectCodeError">
                <option value=""></option>
            <#if projectList?? && projectList?size gt 0>
                <#list projectList as list>
                    <option value="${list.code}">${list.name}</option>
                </#list>
            </#if>
            </select>
            <span id="projectCodeError"></span>
        </div>
    </div>
    <div id="menuDiv" class="form-group animated fadeInRight">
        <label class="col-sm-2 ">菜单</label>
        <div class="col-md-6 col-sm-10">
            <div class="panel">
                <div class="panel-body">
                    <div id="zTree" class="ztree" style="overflow:auto; height:350px;line-height: 350px"></div>
                </div>
            </div>
        </div>
        <input type="hidden" id="menuIds" name="jadminUser.menuIds" value="" data-rule="菜单:required;"/>
    </div>
    <div id="submitDiv" class="form-group animated fadeInLeft">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="button" class="btn btn-success" onclick="save();"><i class="icon icon-save"></i> 保存
            </button>
            <button type="button" class="btn btn-info" onclick="toAddOrUpdatePage('0',null);"><i class="icon icon-refresh"></i> 刷新</button>
            <button type="button" class="btn btn-default" onclick="goback();"><i
                    class="icon icon-reply"></i> 返回
            </button>
        </div>
    </div>
</form>
