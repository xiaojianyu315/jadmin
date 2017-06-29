
<script type="text/javascript">
    $(function () {


    });

    /**
     * 同步方法
     **/
    function save() {
        $('#user_form').isValid(function (v) {
            if (v) {
                webUtil.confirm("确定保存吗？", function () {
                    var loading = webUtil.loading();
                    $.ajax({
                        url: "${ctx }/user/changePwd",
                        type: 'post',
                        data: $('#user_form').serialize(),
                        dataType: 'json',
                        traditional: true,
                        success: function (rsData) {
                            webUtil.closeLoading(loading);
                            if (rsData.code == 'SUCCESS') {
                                webUtil.info("操作成功", function () {
                                    gobackAnimation();
                                    setTimeout(function () {
                                        loadListPage();
                                    }, 500)
                                });
                            } else {
                                webUtil.msg(rsData.message, null);
                            }
                        }
                    });
                }, null);
            }
        });
    }

    function goback() {
        gobackAnimation();
        setTimeout(function () {
            loadListPage();
        }, 500)
    }
    function gobackAnimation() {
        $('#passwordOldDiv').removeClass("animated fadeInLeft").addClass("animated fadeOutLeft");
        $('#passwordOldNewConfirmDiv').removeClass("animated fadeInLeft").addClass("animated fadeOutLeft");

        $('#passwordOldNewDiv').removeClass("animated fadeInRight").addClass("animated fadeOutRight");
        $('#submitDiv').removeClass("animated fadeInRight").addClass("animated fadeOutRight");
    }

</script>

<ol class="breadcrumb">
    <li><a href="javascript:void(0);" onclick="goUrl('/index')">首页</a></li>
    <li><a href="javascript:void(0);">系统管理</a></li>
    <li><a href="javascript:void(0)" onclick="goback()">用户管理</a></li>
    <li class="active">修改密码</li>
</ol>

</p>
<form class="form-horizontal" name="user_form" id="user_form" data-validator-option="{timely:2, theme:'simple_right'}">
    <input type="hidden" id="userId" name="userId" value="${id}"/>

    <div id="passwordOldDiv" class="form-group animated fadeInLeft">
        <label class="col-sm-2 required">原密码</label>
        <div class="col-md-6 col-sm-10">
            <input type="password" class="form-control" id="userName" name="passwordOld" placeholder="10位以上的字母、数字、符号"
                   placeholder="10位以上的字母、数字、符号"
                   data-rule="原密码:required;password;"
                   data-rule-password="[/^[\w\d]{10,30}$/, '请输入10-30位数字和字母组合']"
            >
        </div>
    </div>

    <div id="passwordOldNewDiv" class="form-group animated fadeInRight">
        <label class="col-sm-2 required">新密码</label>
        <div class="col-md-6 col-sm-10">
            <input type="password" class="form-control" id="realName" name="passwordOldNew"
                   placeholder="10位以上的字母、数字、符号"
                   data-rule="新密码:required;password;"
                   data-rule-password="[/^[\w\d]{10,30}$/, '请输入10-30位数字和字母组合']">
        </div>
    </div>
    <div id="passwordOldNewConfirmDiv" class="form-group animated fadeInLeft">
        <label class="col-sm-2 ">确认密码</label>
        <div class="col-md-6 col-sm-10">
            <input type="password" class="form-control" id="mobile"
                   name="passwordOldNewConfirm"
                   placeholder="10位以上的字母、数字、符号"
                   data-rule="确认密码:required;password;"
                   data-rule-password="[/^[\w\d]{10,30}$/, '请输入10-30位数字和字母组合']">
        </div>
    </div>

    <div id="submitDiv" class="form-group animated fadeInRight">

        <div class="col-sm-offset-2 col-sm-10">
            <button type="button" class="btn btn-success" onclick="save();"><i class="icon icon-save"></i> 保存</button>
            <button type="button" class="btn btn-info" onclick="toAddOrUpdatePage('2','${id}');"><i class="icon icon-refresh"></i> 刷新</button>
            <button type="button" class="btn btn-default" onclick="goback();"><i
                    class="icon icon-reply"></i> 返回
            </button>
        </div>
    </div>
</form>
