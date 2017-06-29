
<link rel="stylesheet" href="${ctx}/static/laypage/laypage/skin/laypage.css">
<link rel="stylesheet" href="${ctx}/static/zui/lib/datatable/zui.datatable.min.css">
<link rel="stylesheet" href="${ctx}/static/zui/lib/chosen/chosen.css">
<script type="text/javascript" src="${ctx}/static/zui/lib/datatable/zui.datatable.min.js"></script>
<script type="text/javascript" src="${ctx}/static/zui/lib/chosen/chosen.js"></script>
<script type="text/javascript" src="${ctx}/static/laypage/laypage/laypage.js"></script>
<script type="text/javascript">
    $(function () {
        loadListData(1);

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

    });



    function loadListData(pageNo) {
        if($('#projectCode').val() == ''){
            return;
        }
        var loading = webUtil.loading();
        $.ajax({
            url: "${ctx }/user/listData?pageNo="+pageNo,
            type: 'post',
            data: $('#list_form').serialize(),
            dataType: 'html',
            traditional: true,
            success: function (rsData) {
                webUtil.closeLoading(loading);
                $('#dataDiv').html(rsData);
            }
        });
    }

    /**
     * 跳转到添加或修改页面，带动画
     * 0-添加；1-修改;2-修改密码
     * @param pageFlag
     */
    function toAddOrUpdatePage(pageFlag,id) {
        $('#searchDiv').removeClass().addClass("animated fadeOutLeft");
        $('#dataDiv').removeClass().addClass("animated fadeOutRight");
        setTimeout(function () {
            if('0' == pageFlag){
                loadAddPage();
            }
            if('1' == pageFlag){
                loadUpdatePage(id);
            }
            if('2' == pageFlag){
                loadChangePwdPage(id);
            }

        }, 500)
    }

    function removeUser(id, obj) {
        webUtil.confirm("确定要删除吗？", function () {
            $.ajax({
                url: "${ctx }/user/remove",
                type: 'post',
                data: {'id': id},
                dataType: 'json',
                traditional: true,
                success: function (rsData) {
                    if (rsData.code == 'SUCCESS') {
                        webUtil.info("操作成功", function () {
                            $(obj).parent().parent().remove();
                        });
                    } else {
                        webUtil.error(rsData.message, null);
                    }
                }
            });
        }, null);
    }


</script>

<div id="searchDiv" class="animated fadeInLeft">
    <ol class="breadcrumb">
        <li><a href="javascript:void(0);" onclick="goUrl('/index')">首页</a></li>
        <li><a href="javascript:void(0);">系统管理</a></li>
        <li class="active">用户管理</li>
    </ol>

    <form class="form-inline" id="list_form" action="${ctx}/user/list" method="post">
        <div class="form-group">
            <select id="projectCode" name="jadminUser.projectCode" data-placeholder="选择一个所属项目..."
                    class="chosen-select form-control"
                    style="width: 200px;" tabindex="1">
                <option value=""></option>
                <#if projectList?? && projectList?size gt 0>
                    <#list projectList as list>
                        <option value="${list.code}">${list.name}</option>
                    </#list>
                </#if>
            </select>
        </div>
        <div class="form-group">
            <input type="text" id="userName" name="jadminUser.userName"
                   class="form-control"
                   placeholder="请输入用户名">
        </div>
        <button type="button" onclick="loadListData(1)" class="btn btn-primary"><i class="icon icon-search"></i> 搜索</button>
        <button type="button" id="reset" class="btn"><i class="icon icon-refresh"></i> 重置</button>
        <button type="button" onclick="toAddOrUpdatePage('0',null);" class="btn btn-success"><i class="icon icon-plus-sign"></i> 添加</button>
        <span class="label label-info">默认不加载列表数据，请先选择项目</span>
    </form>
</div>

</p>
<div id="dataDiv" class="animated fadeInRight">

</div>
