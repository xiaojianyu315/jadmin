<#include "/pages/common/layout.ftl"/>
<@head title="首页">
<link rel="stylesheet" href="${ctx}/static/zui/lib/datatable/zui.datatable.min.css" >
<link rel="stylesheet" href="${ctx}/static/zui/lib/chosen/chosen.css" >
<script type="text/javascript" src="${ctx}/static/zui/lib/datatable/zui.datatable.min.js"></script>
<script type="text/javascript" src="${ctx}/static/zui/lib/chosen/chosen.js"></script>
<script type="text/javascript" src="${ctx}/static/laypage/laypage/laypage.js"></script>
<script type="text/javascript">
    $(function () {
        loadListPage();
    });
    /**
     * 加载列表页面
     * 在页面进入时加载
     */
    function loadListPage() {
        $.ajax({
            url: "${ctx }/project/listInit",
            type: 'post',
            data: $('#list_form').serialize(),
            dataType: 'html',
            traditional: true,
            success: function (rsData) {
                $('#pageDiv').html("").html(rsData);
            }
        });
    }

    /**
     * 加载添加页面
     * 在页面中点击添加按钮时加载
     */
    function loadAddPage() {
        $.ajax({
            url: "${ctx }/project/toAdd",
            type: 'post',
            data: {},
            dataType: 'html',
            traditional: true,
            success: function (rsData) {
                $('#pageDiv').html("").html(rsData);
            }
        });
    }

    /**
     * 加载修改页面
     * 在点击列表修改按钮时加载
     */
    function loadUpdatePage(id) {
        $.ajax({
            url: "${ctx }/project/toUpdate",
            type: 'post',
            data: {"id": id},
            dataType: 'html',
            traditional: true,
            success: function (rsData) {
                $('#pageDiv').html("").html(rsData);
            }
        });
    }

    /**
     * 动画返回
     */
    function gobackList() {
        $('#project_form').removeClass('animated bounceInDown').addClass('animated bounceOutUp');
        setTimeout(function () {
            loadListPage();
        }, 600)
    }

</script>
</@head>
<@body>
<div id="pageDiv">
</div>

</@body>