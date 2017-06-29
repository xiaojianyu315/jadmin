<script type="text/javascript">

    $(function () {
        /* 初始化数据表格 */
        $('#userTable').datatable();



        /* 初始化修改、删除提示消息框 */
        $('[data-toggle="tooltip"]').tooltip({});

        /* 初始化分页 */
        laypage({
                    cont: $('#page'), //容器。值支持id名、原生dom对象，jquery对象,
                    pages: ${userPage.totalPage}, //总页数
                    curr: ${userPage.pageNumber} || 1, //当前页
                skip
        :
        true, //是否开启跳页
                groups
        :
        3, //连续显示分页数
                jump
        :
        function (e, first) { //触发分页后的回调
            if (!first) { //一定要加此判断，否则初始时会无限刷新
                loadListData(e.curr);
            }
        }
    })
        ;

    });
</script>
    <!-- HTML 代码 -->
    <table class="table datatable" id="userTable">
        <thead>
        <tr>
            <th>用户名</th>
            <th>真实姓名</th>
            <th>联系手机号</th>
            <th>是否有效</th>
            <th>项目编码</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
            <#if userPage.list?? && userPage.list?size gt 0>
                <#list userPage.list as list>
                <tr>
                    <td>${list.userName}</td>
                    <td>${list.realName}</td>
                    <td>${list.mobile!}</td>
                    <td>${list.isEnable}</td>
                    <td>${list.projectCode}</td>
                    <td>
                        <a href="javascript:void(0);"
                           onclick="toAddOrUpdatePage('1','${list.id}')"
                           data-toggle="tooltip"
                           data-tip-class="tooltip-primary"
                           title="修改用户"><i class="icon icon-edit"></i></a>&nbsp;
                        <a href="javascript:void(0);" data-toggle="tooltip" data-tip-class="tooltip-primary"
                           onclick="toAddOrUpdatePage('2','${list.id}');"
                           title="修改密码"><i class="icon icon-eye-close"></i></a>&nbsp;
                        <a  href="javascript:void(0);" data-toggle="tooltip" data-tip-class="tooltip-danger"
                           onclick="removeUser('${list.id}',this);"
                           title="删除用户" style="color: red"><i class="icon icon-trash"></i></a>
                    </td>
                </tr>
                </#list>
            <#else>
            <tr>
                <td colspan="6" style="text-align: center">暂无数据</td>
            </tr>
            </#if>

        </tbody>
    </table>
    <div id="page" style="text-align: center"></div>
