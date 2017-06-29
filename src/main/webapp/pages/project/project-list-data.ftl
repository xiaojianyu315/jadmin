<script type="text/javascript">
    $(function () {
        /* 初始化数据表格 */
        $('#projectTable').datatable();

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
</script>
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
                    <a href="javascript:void(0);" data-toggle="tooltip" data-tip-class="tooltip-primary" onclick="toAddOrUpdatePage('1','${list.id}');"
                    title="修改项目"><i class="icon icon-edit"></i></a>&nbsp;
                    <a href="javascript:void(0);" data-toggle="tooltip" data-tip-class="tooltip-danger" onclick="removeProject('${list.id}',this);"
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
