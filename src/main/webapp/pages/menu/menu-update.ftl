<form class="form-horizontal" name="menu_form" id="menu_form" data-validator-option="{timely:2, theme:'simple_right'}">
    <div class="col-md-8">
        <div class="col-md-12 ">
            <div class="panel">
                <div class="panel-heading">
                    菜单详情
                </div>
                <div class="panel-body" style="height: 500px;">
                    <div class="form-group">
                        <label class="col-sm-2">项目编码</label>
                        <div class="col-md-6 col-sm-10">
                            <input type="text" class="form-control" value="${project.code}" disabled>
                            <input type="hidden" class="form-control" value="${project.code}" id="projectCode" name="jadminMenu.projectCode"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2">项目名称</label>
                        <div class="col-md-6 col-sm-10">
                            <input type="text" class="form-control" value="${project.name}" disabled>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 required">菜单名称</label>
                        <div class="col-md-6 col-sm-10">
                            <input type="text" class="form-control" id="menuName" name="jadminMenu.name"
                                   <#if menu.code == 'CDGL'>disabled</#if>
                                   value="${menu.name}"
                                   placeholder="4-20位的字母、数字、符号、中文"
                                   data-rule="菜单名称:required;name;"
                                   data-rule-name="[/^(?=.*\d)[a-z\d]{4,20}$/, '请输入4-20位字母、数字、符号、中文组合']"
                            >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 required">菜单编码</label>
                        <div class="col-md-6 col-sm-10">
                            <input type="text" class="form-control" id="menuCode" name="jadminMenu.code"
                                   <#if menu.code == 'CDGL'>disabled</#if>
                                   value="${menu.code}"
                                   placeholder="4-20位的字母、数字、符号"
                                   data-rule="菜单编码:required;code;"
                                   data-rule-code="[/^(?=.*\d)[a-z\d]{4,20}$/, '请输入4-20位数字、字母组合']"
                            >
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 required">菜单类型</label>
                        <div class="col-md-6 col-sm-10">
                            <select name="jadminMenu.projectCode" data-placeholder="选择一个所属项目..."
                                    <#if menu.code == 'CDGL'>disabled</#if>
                                    class="chosen-select form-control" tabindex="2"
                                    data-rule="项目:required;" data-target="#projectCodeError">
                                <option value=""></option>
                                <option value="0" <#if menu.type == 0>selected</#if>>菜单</option>
                                <option value="1" <#if menu.type == 1>selected</#if>>按钮</option>
                            </select>
                            <span id="projectCodeError"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 required">菜单路径</label>
                        <div class="col-md-6 col-sm-10">
                            <input type="text" class="form-control" id="url" name="jadminMenu.url" value="${menu.url}"
                                   <#if menu.code == 'CDGL'>disabled</#if>
                                   placeholder="4-200位字母、数字、符号"
                                   data-rule="菜单路径:required;url;"
                                   data-rule-url="[/^(?=.*\d)[a-z\d]{3,20}$/, '请输入4-200位字母、数字、符号组合']"
                            >
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 required">菜单层级</label>
                        <div class="col-md-6 col-sm-10">
                            <input type="number" class="form-control" id="level" name="jadminMenu.level" maxlength="1"
                                   <#if menu.code == 'CDGL'>disabled</#if>
                                   value="${menu.level}"
                                   placeholder="1位数字"
                                   data-rule="菜单层级:required;level;"
                                   data-rule-level="[/^[\d]{1,1}$/, '请输入1位数字']"
                            >
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 required">排序</label>
                        <div class="col-md-6 col-sm-10">
                            <input type="number" class="form-control" id="sort" name="jadminMenu.sort" maxlength="3"
                                   value="${menu.sort}" <#if menu.code == 'CDGL'>disabled</#if>
                                   placeholder="1-3位数字"
                                   data-rule="排序:required;sort;"
                                   data-rule-sort="[/^[\d]{1,3}$/, '请输入1-3位数字']"
                            >
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 required">菜单状态</label>
                        <div class="col-md-6 col-sm-10">
                            <div class="switch">
                                <input type="checkbox" name="isEnable" <#if menu.isEnable == 1>checked</#if>
                                       <#if menu.code == 'CDGL'>disabled</#if>>
                                <label>是否有效</label>
                            </div>
                        </div>
                    </div>

                    <input type="hidden" id="menuId" name="jadminMenu.id" value="${menu.id}"/>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                        <#if menu.code != 'CDGL'>
                            <button type="button" class="btn btn-success" onclick="update();"><i
                                    class="icon icon-save"></i> 保存
                            </button>
                            <button type="button" class="btn btn-danger" onclick="removeMenu('${menu.id}');"><i
                                    class="icon icon-trash"></i> 删除
                            </button>
                        </#if>

                            <button type="button" class="btn btn-success" onclick="toAdd();"><i
                                    class="icon icon-save"></i> 添加子菜单
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
