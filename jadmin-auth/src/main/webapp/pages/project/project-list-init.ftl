

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
            $("#list_form :input").not(":button, :submit, :reset, :hidden").val("").removeAttr("checked").remove("selected");
        });

    });

    function loadListData(pageNo) {
        var loading = webUtil.loading();
        $.ajax({
            url: "${ctx }/project/listData?pageNo=" + pageNo,
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
     * 0-添加；1-修改;
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
        }, 500)
    }

    function removeProject(id, obj) {
        webUtil.confirm("确定要删除吗？", function () {
            $.ajax({
                url: "${ctx }/project/remove",
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


    <ol class="breadcrumb">
        <li><a href="javascript:void(0);" onclick="goUrl('/index')">首页</a></li>
        <li><a href="javascript:void(0);">系统管理</a></li>
        <li class="active">项目管理</li>
    </ol>
<div id="searchDiv" class="animated fadeInLeft">
    <form class="form-inline" id="list_form">
        <div class="form-group">
            <input type="text" id="code" name="jadminProject.code" class="form-control" placeholder="请输入项目编码">
        </div>
        <button type="button" class="btn btn-primary" onclick="loadListData(1)"><i class="icon icon-search"></i> 搜索</button>
        <button type="button" id="reset" class="btn"><i class="icon icon-refresh"></i> 重置</button>
        <button type="button" onclick="toAddOrUpdatePage('0',null);" class="btn btn-success"><i class="icon icon-plus-sign"></i> 添加</button>
    </form>
</div>
</p>
<div id="dataDiv" class="animated fadeInRight">
