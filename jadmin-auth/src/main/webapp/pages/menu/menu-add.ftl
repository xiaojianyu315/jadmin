<!doctype html>
<html class="no-js" lang="zh-CN">
<head>
    <style>
        table td {
            height:40px;
            line-height: 40px;
        }
    </style>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/zui/css/zui.min.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/nice-validator/jquery.validator.css"/>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/jquery-1.11.2.min.js"></script>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/zui/js/zui.min.js"></script>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/nice-validator/jquery.validator.min.js"></script>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/nice-validator/local/zh-CN.js"></script>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/layer/layer/layer.js"></script>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/web-util.js"></script>
    <script type="text/javascript">
        function save() {
            var projectCode = $('#projectCode').val();
            $('#menu_form').isValid(function (v) {
                if (v) {
                    webUtil.confirm("确定要保存吗？", function () {
                        $.ajax({
                            url: "${ctx }/menu/add",
                            type: 'post',
                            data: $('#menu_form').serialize(),
                            dataType: 'json',
                            traditional: true,
                            success: function (rsData) {
                                if (rsData.code == 'SUCCESS') {
                                    webUtil.info("操作成功", function () {
                                        parent.closeAddDialog(projectCode);
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
    </script>
</head>
<body>
</p>
<div class="row">
    <div class="col-xs-1"></div>
    <div class="col-xs-8">
        <form class="form-horizontal " name="menu_form" id="menu_form"
              data-validator-option="{timely:2, theme:'simple_right'}">
            <table width="100%">
                <col width="20%"/>
                <col width="80%"/>
                <tr>
                    <td><label class="col-sm-2 ">项目编码</label></td>
                    <td>
                        <input type="text" class="form-control" value="${project.code}" name="jadminMenu.projectCode"
                               disabled>
                        <input type="hidden" class="form-control" value="${project.code}" id="projectCode"
                               name="jadminMenu.projectCode"/>
                    </td>
                </tr>

                <tr>
                    <td><label class="col-sm-2 ">项目名称</label></td>
                    <td>
                        <input type="text" class="form-control" value="${project.name}" disabled>
                    </td>
                </tr>

                <tr>
                    <td><label class="col-sm-2 required">菜单名称</label></td>
                    <td>
                        <input type="text" class="form-control" id="menuName" name="jadminMenu.name"

                               value=""
                               placeholder="4-20位的字母、数字、符号、中文"
                               data-rule="菜单名称:required;length(4~20);"

                        >
                    </td>
                </tr>

                <tr>
                    <td><label class="col-sm-2 required">菜单编码</td>
                    <td>
                        <input type="text" class="form-control" id="menuCode" name="jadminMenu.code"
                               value=""
                               placeholder="4-20位的字母、数字、符号"
                               data-rule="菜单编码:required;length(4~20);"

                        >
                    </td>
                </tr>

                <tr>
                    <td><label class="col-sm-2 required">菜单类型</label></td>
                    <td>
                        <select name="jadminMenu.type" data-placeholder="选择一个所属项目..."
                                class="chosen-select form-control" tabindex="2"
                                data-rule="项目:required;" data-target="#projectCodeError">
                            <option value="0">菜单</option>
                            <option value="1">按钮</option>
                        </select>
                        <span id="projectCodeError"></span>
                    </td>
                </tr>

                <tr>
                    <td><label class="col-sm-2 required">菜单路径</label></td>
                    <td>
                        <input type="text" class="form-control" id="url" name="jadminMenu.url" value=""
                               placeholder="1-200位字母、数字、符号"
                               data-rule="菜单路径:required;length(1~200);"
                        >
                    </td>
                </tr>

                <tr>
                    <td><label class="col-sm-2 required">菜单层级</label></td>
                    <td>
                        <input type="number" class="form-control" id="level" name="jadminMenu.level"

                               value=""
                               placeholder="1位数字"
                               data-rule="菜单层级:required;length(1);"
                        >
                    </td>
                </tr>

                <tr>
                    <td><label class="col-sm-2 required">排序</label></td>
                    <td>
                        <input type="number" class="form-control" id="sort" name="jadminMenu.sort"
                               value=""
                               placeholder="1-3位数字"
                               data-rule="排序:required;length(1~3);"
                        >
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="hidden" id="menuId" name="jadminMenu.pid" value="${menu.id}"/>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="button" class="btn btn-success" onclick="save();"><i
                                        class="icon icon-save"></i> 保存
                                </button>
                            </div>
                        </div>
                    </td>

                </tr>
            </table>


        </form>
        <#--<form class="form-horizontal " name="menu_form" id="menu_form"-->
              <#--data-validator-option="{timely:2, theme:'simple_right'}">-->
            <#--<div class="form-group">-->
                <#--<label class="col-sm-2">项目编码</label>-->
                <#--<div class="col-md-6 col-sm-10">-->
                    <#--<input type="text" class="form-control" value="${project.code}" name="jadminMenu.projectCode"-->
                           <#--disabled>-->
                    <#--<input type="hidden" class="form-control" value="${project.code}" id="projectCode"-->
                           <#--name="jadminMenu.projectCode"/>-->
                <#--</div>-->
            <#--</div>-->
            <#--<div class="form-group">-->
                <#--<label class="col-sm-2">项目名称</label>-->
                <#--<div class="col-md-6 col-sm-10">-->
                    <#--<input type="text" class="form-control" value="${project.name}" disabled>-->
                <#--</div>-->
            <#--</div>-->
            <#--<div class="form-group">-->
                <#--<label class="col-sm-2 required">菜单名称</label>-->
                <#--<div class="col-md-6 col-sm-10">-->
                    <#--<input type="text" class="form-control" id="menuName" name="jadminMenu.name"-->

                           <#--value=""-->
                           <#--placeholder="4-20位的字母、数字、符号、中文"-->
                           <#--data-rule="菜单名称:required;"-->

                    <#-->-->
                <#--</div>-->
            <#--</div>-->
            <#--<div class="form-group">-->
                <#--<label class="col-sm-2 required">菜单编码</label>-->
                <#--<div class="col-md-6 col-sm-10">-->
                    <#--<input type="text" class="form-control" id="menuCode" name="jadminMenu.code"-->
                           <#--value=""-->
                           <#--placeholder="4-20位的字母、数字、符号"-->
                           <#--data-rule="菜单编码:required;"-->

                    <#-->-->
                <#--</div>-->
            <#--</div>-->

            <#--<div class="form-group">-->
                <#--<label class="col-sm-2 required">菜单类型</label>-->
                <#--<div class="col-md-6 col-sm-10">-->
                    <#--<select name="jadminMenu.type" data-placeholder="选择一个所属项目..."-->
                            <#--class="chosen-select form-control" tabindex="2"-->
                            <#--data-rule="项目:required;" data-target="#projectCodeError">-->
                        <#--<option value="0">菜单</option>-->
                        <#--<option value="1">按钮</option>-->
                    <#--</select>-->
                    <#--<span id="projectCodeError"></span>-->
                <#--</div>-->
            <#--</div>-->
            <#--<div class="form-group">-->
                <#--<label class="col-sm-2 required">菜单路径</label>-->
                <#--<div class="col-md-6 col-sm-10">-->
                    <#--<input type="text" class="form-control" id="url" name="jadminMenu.url" value=""-->
                           <#--placeholder="4-200位字母、数字、符号"-->
                           <#--data-rule="菜单路径:required;"-->

                    <#-->-->
                <#--</div>-->
            <#--</div>-->

            <#--<div class="form-group">-->
                <#--<label class="col-sm-2 required">菜单层级</label>-->
                <#--<div class="col-md-6 col-sm-10">-->
                    <#--<input type="number" class="form-control" id="level" name="jadminMenu.level"-->

                           <#--value=""-->
                           <#--placeholder="1位数字"-->
                           <#--data-rule="菜单层级:required;level;"-->
                           <#--data-rule-level="[/^[\d]{1,1}$/, '请输入1位数字']"-->
                    <#-->-->
                <#--</div>-->
            <#--</div>-->

            <#--<div class="form-group">-->
                <#--<label class="col-sm-2 required">排序</label>-->
                <#--<div class="col-md-6 col-sm-10">-->
                    <#--<input type="number" class="form-control" id="sort" name="jadminMenu.sort"-->
                           <#--value=""-->
                           <#--placeholder="1-3位数字"-->
                           <#--data-rule="排序:required;sort;"-->
                           <#--data-rule-sort="[/^[\d]{1,3}$/, '请输入1-3位数字']"-->
                    <#-->-->
                <#--</div>-->
            <#--</div>-->

            <#--<input type="hidden" id="menuId" name="jadminMenu.pid" value="${menu.id}"/>-->
            <#--<div class="form-group">-->
                <#--<div class="col-sm-offset-2 col-sm-10">-->
                    <#--<button type="button" class="btn btn-success" onclick="save();"><i-->
                            <#--class="icon icon-save"></i> 保存-->
                    <#--</button>-->
                <#--</div>-->
            <#--</div>-->

        <#--</form>-->
    </div>
    <div class="col-xs-3"></div>
</div>


</body>
