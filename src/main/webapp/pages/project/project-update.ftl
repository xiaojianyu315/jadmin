<#include "/pages/common/layout.ftl"/>
<@head title="首页">
<script type="text/javascript">
    $(function () {

    });



    function update() {
        $('#project_form').isValid(function(v){
            if (v) {
                webUtil.confirm("确定修改吗？",function () {
                    var loading = webUtil.loading();
                    $.ajax({
                        url: "${ctx }/project/update",
                        type: 'post',
                        data: $('#project_form').serialize(),
                        dataType: 'json',
                        traditional: true,
                        success: function (rsData) {
                            webUtil.closeLoading(loading);
                            if (rsData.code == 'SUCCESS') {
                                webUtil.info("操作成功",function () {
                                    window.location.href='${ctx}/project/list'
                                });
                            } else {
                                webUtil.error(rsData.message,null);
                            }

                        }
                    });
                },null)
            }
        });

    }


</script>
</@head>
<@body>
<ol class="breadcrumb">
    <li><a href="javascript:void(0);" onclick="goUrl('/index')">首页</a></li>
    <li><a href="javascript:void(0);">系统管理</a></li>
    <li><a href="${ctx}/project/list">项目管理</a></li>
    <li class="active">添加</li>
</ol>

</p>
<form class="form-horizontal" name="project_form" id="project_form"data-validator-option="{timely:2, theme:'simple_right'}" >
    <input type="hidden" name="jadminProject.id" value="${project.id}"/>
    <div class="form-group">
        <label class="col-sm-2 required">项目名称</label>
        <div class="col-md-6 col-sm-10">
            <input type="text"
                   class="form-control"
                   id="name"
                   name="jadminProject.name"
                   maxlength="20"
                   data-rule="项目名称:required;"
                   value="${project.name}"
            >

        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 required">项目编码</label>
        <div class="col-md-6 col-sm-10">
            <input type="text"
                   class="form-control"
                   id="code"
                   name="jadminProject.code"
                   maxlength="20"
                   placeholder="4-20位以上的字母"
                   data-rule="项目编码:required;code;"
                   data-rule-code="[/^[\w]{4,20}$/, '请输入4-20位数字和字母组合']"
                   value="${project.code}"
            >
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 required">密钥</label>
        <div class="col-md-6 col-sm-10">
            <input type="text"
                   class="form-control"
                   id="secretKey"
                   name="jadminProject.secretKey"
                   placeholder="与其他系统交互时需要的密钥"
                   data-rule="密钥:required;"
                   value="${project.secretKey}"
            >
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="button" class="btn btn-success" onclick="update();"><i class="icon icon-save"></i> 保存</button>
            <button type="button" class="btn btn-default" onclick="window.location.href='${ctx}/project/list'"><i class="icon icon-reply"></i> 返回</button>
        </div>
    </div>
</form>
</@body>