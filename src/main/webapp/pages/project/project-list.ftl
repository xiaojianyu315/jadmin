<#include "/pages/common/layout.ftl"/>
<@head title="首页">
<link rel="stylesheet" href="${ctx}/static/zui/lib/datatable/zui.datatable.min.css" >
<link rel="stylesheet" href="${ctx}/static/zui/lib/chosen/chosen.css" >
<script type="text/javascript" src="${ctx}/static/zui/lib/datatable/zui.datatable.min.js"></script>
<script type="text/javascript" src="${ctx}/static/zui/lib/chosen/chosen.js"></script>
<script type="text/javascript" src="${ctx}/static/laypage/laypage/laypage.js"></script>
<script type="text/javascript">
    $(function () {
        /* 初始化数据表格 */
        $('#projectTable').datatable();

        /* 初始化select */
        $('select.chosen-select').chosen({
            no_results_text: '没有找到',    // 当检索时没有找到匹配项时显示的提示文本
            disable_search_threshold: 10, // 10 个以下的选择项则不显示检索框
            search_contains: true         // 从任意位置开始检索
        });
        /* 初始化重置按钮 */
        $("#reset").click(function () {
            $("#form :input").not(":button, :submit, :reset, :hidden").val("").removeAttr("checked").remove("selected");
        });

        /* 初始化修改、删除提示消息框 */
        $('[data-toggle="tooltip"]').tooltip({});

        /* 初始化分页 */
        laypage({
            cont: $('#page'), //容器。值支持id名、原生dom对象，jquery对象,
            pages: ${projectPage.totalPage}, //总页数
            curr: ${projectPage.pageNumber} || 1, //当前页
            skip : true, //是否开启跳页
            groups : 3, //连续显示分页数
            jump : function (e, first) { //触发分页后的回调
                if (!first) { //一定要加此判断，否则初始时会无限刷新
                    location.href = '?pageNo=' + e.curr;
                }
            }
        });

    });

    function toAdd() {
        window.location.href = "${ctx}/project/toAdd";
    }

    function removeProject(id) {
        webUtil.confirm("确定要删除吗？",function () {
            $.ajax({
                url: "${ctx }/project/remove",
                type: 'post',
                data: {'id': id},
                dataType: 'json',
                traditional: true,
                success: function (rsData) {
                    if (rsData.code == 'SUCCESS') {
                        webUtil.info("操作成功",function () {
                            window.location.href='${ctx}/project/list'
                        });
                    } else {
                        webUtil.error(rsData.message,null);
                    }
                }
            });
        },null);

    }

</script>
</@head>
<@body>
<ol class="breadcrumb">
    <li><a href="javascript:void(0);" onclick="goUrl('/index')">首页</a></li>
    <li><a href="javascript:void(0);">系统管理</a></li>
    <li class="active">项目管理</li>
</ol>


<form class="form-inline" id="form" action="${ctx}/project/list" method="post">
    <div class="form-group">
        <input type="text" id="code" name="jadminProject.code" value="${jadminProject.code!}" class="form-control"
               placeholder="请输入项目编码">
    </div>
    <button type="submit" class="btn btn-primary"><i class="icon icon-search"></i> 搜索</button>
    <button type="button" id="reset" class="btn"><i class="icon icon-refresh"></i> 重置</button>
    <button type="button" onclick="toAdd();" class="btn btn-success"><i class="icon icon-plus-sign"></i> 添加</button>
</form>
</p>
<!-- HTML 代码 -->
<table class="table datatable" id="projectTable">
    <thead>
    <tr>
        <th>项目名称</th>
        <th>项目编码</th>
        <th>是否有效</th>
        <th>创建时间</th>
        <th>更新时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
        <#if projectPage.list?? && projectPage.list?size gt 0>
            <#list projectPage.list as list>
            <tr>
                <td>${list.name}</td>
                <td>${list.code}</td>
                <td>${list.isEnable}</td>
                <td>${list.createTime}</td>
                <td>${list.updateTime}</td>
                <td>
                    <a href="${ctx}/project/toUpdate?id=${list.id}" data-toggle="tooltip" data-tip-class="tooltip-primary"
                    title="修改项目"><i class="icon icon-edit"></i></a>&nbsp;
                    <a href="javascript:void(0);" data-toggle="tooltip" data-tip-class="tooltip-danger" onclick="removeProject('${list.id}');"
                    title="删除项目" style="color: red"><i class="icon icon-trash"></i></a>
                </td>
            </tr>
            </#list>
        <#else>
            <tr>
                <td colspan="4" style="text-align: center">暂无数据</td>
            </tr>
        </#if>

    </tbody>
</table>
<div id="page" style="text-align: center"></div>
</@body>