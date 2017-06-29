<#include "/pages/common/layout.ftl"/>
<@head title="首页">
<link rel="stylesheet" href="${ctx}/static/zui/lib/datatable/zui.datatable.min.css">
<link rel="stylesheet" href="${ctx}/static/zui/lib/chosen/chosen.css">
<script type="text/javascript" src="${ctx}/static/zui/lib/datatable/zui.datatable.min.js"></script>
<script type="text/javascript" src="${ctx}/static/zui/lib/chosen/chosen.js"></script>
<script type="text/javascript" src="${ctx}/static/laypage/laypage/laypage.js"></script>
<link href="${ctx}/static/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
<script src="${ctx}/static/zTree/js/jquery.ztree.all.min.js"></script>
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
            url: "${ctx }/user/listInit",
            type: 'post',
            data: $('#list_form').serialize(),
            dataType: 'html',
            traditional: true,
            success: function (rsData) {
                $('#userDiv').html("").html(rsData);
            }
        });
    }

    /**
     * 加载添加页面
     * 在页面中点击添加按钮时加载
     */
    function loadAddPage() {
        $.ajax({
            url: "${ctx }/user/toAdd",
            type: 'post',
            data: {},
            dataType: 'html',
            traditional: true,
            success: function (rsData) {
                $('#userDiv').html("").html(rsData);
            }
        });
    }

    /**
     * 加载修改页面
     * 在点击列表修改按钮时加载
     */
    function loadUpdatePage(id) {
        $.ajax({
            url: "${ctx }/user/toUpdate",
            type: 'post',
            data: {"id": id},
            dataType: 'html',
            traditional: true,
            success: function (rsData) {
                $('#userDiv').html("").html(rsData);
            }
        });
    }

    /**
     * 加载修改密码页面
     * 在点击列表修改密码按钮时加载
     */
    function loadChangePwdPage(id) {
        $.ajax({
            url: "${ctx }/user/toChangePwd",
            type: 'post',
            data: {"id": id},
            dataType: 'html',
            traditional: true,
            success: function (rsData) {
                $('#userDiv').html("").html(rsData);
            }
        });
    }

    /**
     * 动画返回
     */
    function gobackList() {
        $('#user_form').removeClass('animated bounceInDown').addClass('animated bounceOutUp');
        setTimeout(function () {
            loadListPage();
        }, 600)
    }
</script>
</@head>
<@body>
<div id="userDiv">
</div>

</@body>