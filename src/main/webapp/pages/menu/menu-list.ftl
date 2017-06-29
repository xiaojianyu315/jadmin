<#include "/pages/common/layout.ftl"/>
<@head title="首页">
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

    var commonNode;
    function getNodeDetail(event, treeId, treeNode) {
        var loading = webUtil.loading();
        var id = treeNode.id;
        $.ajax({
            url: "${ctx }/menu/detail",
            type: 'post',
            data: {'id': id},
            dataType: 'html',
            traditional: true,
            success: function (rsData) {
                webUtil.closeLoading(loading);
                $('#dataDiv').html(rsData)
            }
        });
        commonNode = treeNode;
    }


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

    function update() {
        var projectCode = $('#projectCode').val();
        $('#menu_form').isValid(function (v) {
            if (v) {
                webUtil.confirm("确定要更新吗？", function () {
                    $.ajax({
                        url: "${ctx }/menu/update",
                        type: 'post',
                        data: $('#menu_form').serialize(),
                        dataType: 'json',
                        traditional: true,
                        success: function (rsData) {
                            if (rsData.code == 'SUCCESS') {
                                webUtil.info("操作成功", function () {
                                    queryMenuTree(projectCode);
                                    var treeObj = $.fn.zTree.getZTreeObj("zTree");
                                    treeObj.selectNode(commonNode);
                                });
                            } else {
                                webUtil.error(rsData.message, null);
                            }
                        }
                    });
                }, null)

            }
        });

    }

    function removeMenu(id) {
        webUtil.confirm("确定要删除吗？", function () {
            $.ajax({
                url: "${ctx }/menu/remove",
                type: 'post',
                data: {'id': id},
                dataType: 'json',
                traditional: true,
                success: function (rsData) {
                    if (rsData.code == 'SUCCESS') {
                        webUtil.info("操作成功", function () {
                            queryMenuTree(projectCode);
                            var treeObj = $.fn.zTree.getZTreeObj("zTree");
                            treeObj.selectNode(commonNode);
                        });
                    } else {
                        webUtil.error(rsData.message, null);
                    }
                }
            });
        }, null)

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
                onClick: getNodeDetail,
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


    var openAddDialog;
    function toAdd() {
        var pid = $('#menuId').val();
        openAddDialog = webUtil.openByUrl("添加子菜单", 600, 600, "${ctx}/menu/toAdd?id=" + pid);
    }
    /**
     * 关闭添加弹出框
     */
    function closeAddDialog(projectCode) {
        webUtil.closeLoading(openAddDialog);
        queryMenuTree(projectCode);
    }


</script>
</@head>
<@body>
<ol class="breadcrumb">
    <li><a href="javascript:void(0);" onclick="goUrl('/index')">首页</a></li>
    <li><a href="javascript:void(0);">系统管理</a></li>
    <li class="active">菜单管理</li>
</ol>

</p>

<div class="row">
    <div class="col-md-4">
        <div class="form-group">
            <div class="col-md-12 ">
                <div class="panel">
                    <div class="panel-heading">
                        项目菜单树
                    </div>
                    <div class="panel-body" style="height: 500px;">
                        <div class="form-group">
                            <div class="col-md-12">
                                <select name="searchProjectCode" data-placeholder="请选择一个项目展示菜单..."
                                        class="chosen-select form-control" tabindex="2"
                                        onchange="queryMenuTree(this.value);">
                                    <option value=""></option>
                                    <#if projectList?? && projectList?size gt 0>
                                        <#list projectList as list>
                                            <option value="${list.code}">${list.name}</option>
                                        </#list>
                                    </#if>
                                </select>
                            </div>
                        </div>

                        <div id="zTree" class="ztree" style="overflow:auto; height:350px;line-height: 350px"></div>
                    </div>
                </div>
            </div>
            <input type="hidden" id="menuIds" name="jadminUser.menuIds" value=""/>
        </div>
    </div>
    <div id="dataDiv"></div>
</div>

</@body>