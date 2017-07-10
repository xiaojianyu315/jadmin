<#macro head title >
<!doctype html>
<html class="no-js" lang="zh-CN">
<head>
    <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="Content-Language" CONTENT="zh-CN"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-store, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
    <META HTTP-EQUIV="expires" CONTENT="0">
    <title>${title}</title>
    <style type="text/css">
        .ui-layout-west {
            padding:    0px!important;
            position:   relative;
            overflow:   auto;
        }
        .ui-layout-north {
            color: white;
            border-bottom: none!important;
            font-size: 24px;
            background: #0099FF!important;
            padding:    6px!important;
            position:   relative;
            overflow:   auto;
        }
        .ui-layout-center {
        }

    </style>

    <link type="text/css" rel="stylesheet" href="${ctx}/static/animate.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/zui/css/zui.min.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/layout/layout-default-latest.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/nice-validator/jquery.validator.css"/>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/jquery-1.11.2.min.js"></script>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/layout/jquery.layout-latest.js"></script>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/zui/js/zui.min.js"></script>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/nice-validator/jquery.validator.min.js"></script>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/nice-validator/local/zh-CN.js"></script>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/layer/layer/layer.js"></script>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/web-util.js"></script>
    <script>
        $(document).ready(function () {
            $('body').layout({
                applyDefaultStyles: true,
                north__closable: false,//可以被关闭
                north__resizable: false,//可以改变大小
                north__size: 50,//pane的大小
                west__size: 202,//pane的大小
                spacing_open: 0,//边框的间隙

            });
            // 手动通过点击模拟高亮菜单项
//            $('#treeMenu').on('click', 'a', function () {
//                $('#treeMenu li.active').removeClass('active');
//                $(this).closest('li').addClass('active');
//            });
            initUrlActive();
            var item = localStorage.getItem("layout_animated");
            if(item==null){
                $('#layout_center').removeClass("animated bounceInRight").addClass("animated bounceInRight");
                $('#layout_north').removeClass("animated bounceInUp").addClass("animated bounceInUp");
                $('#layout_west').removeClass("animated bounceInLeft").addClass("animated bounceInLeft");
                localStorage.setItem("layout_animated","off")
            }
        });

        function goUrl(url) {
            localStorage.setItem("menuId",url.split("/").join(''));
            window.location.href = '${ctx}' + url;
        }
        function initUrlActive() {
            var menuId = localStorage.getItem("menuId");
            $('#treeMenu li.active').removeClass('active');
            $("#"+menuId).closest('li').addClass('active');
        }
        function logout() {
            webUtil.confirm("确定退出吗？",function () {
                window.location.href="${ctx}/logout";
            },null)
        }

    </script>
    <#nested>
</head>
</#macro>

<#macro body>
<body >
<div id="layout_center" class="ui-layout-center "><#nested></div>
<div id="layout_north" class="ui-layout-north ">
    <div style="float:right;margin-right:3px;"><a href="javascript:void(0);" onclick="logout();"><img title="退出" src="${ctx}/static/img/logout.png"/></a></div>
</div>
<#--<div class="ui-layout-south">South</div>-->
<#--<div class="ui-layout-east">East</div>-->
<div id="layout_west" class="ui-layout-west ">
    <nav data-ride="menu" style="width: 200px">
        <ul id="treeMenu" class="tree tree-menu" data-ride="tree">
            <li><a id="${"/index"?replace("/","")}" href="javascript:void(0);" onclick="goUrl('/index')"><i class="icon icon-th"></i>首页</a></li>
            <li class="open">
                <a href="#"><i class="icon icon-time"></i>系统管理</a>
                <ul>
                    <li><a id="${"/project/init"?replace("/","")}" href="javascript:void(0);" onclick="goUrl('/project/init')"><i class="icon icon-server"></i>项目管理</a></li>
                    <li><a id="${"/user/init"?replace("/","")}" href="javascript:void(0);" onclick="goUrl('/user/init')"><i class="icon icon-user"></i>用户管理</a></li>
                    <li><a id="${"/menu/list"?replace("/","")}" href="javascript:void(0);" onclick="goUrl('/menu/list')"><i class="icon icon-list-alt"></i>菜单管理</a></li>
                    <li><a id="${"/log/list"?replace("/","")}" href="javascript:void(0);" onclick="goUrl('/log/list')"><i class="icon icon-file-text-o"></i>系统日志</a></li>
                </ul>
            </li>
        </ul>
    </nav>
</div>
</body>
</html>
</#macro>