<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Wopop</title>
    <link href="${ctx}/static/login/style_log.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/login/style.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/login/userpanel.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/login/jquery.ui.all.css">
    <link type="text/css" rel="stylesheet" href="${ctx}/static/nice-validator/jquery.validator.css"/>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/jquery-1.11.2.min.js"></script>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/nice-validator/jquery.validator.min.js"></script>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/nice-validator/local/zh-CN.js"></script>
    <script>
        /**
         * 同步方法
         **/
        function doLogin() {
            $.ajax({
                url: "${ctx }/doLogin",
                type: 'post',
                data: $('#login_form').serialize(),
                dataType: 'json',
                async: false,
                traditional: true,
                success: function (rsData) {
                    if (rsData.code == 'SUCCESS') {
                        localStorage.setItem("menuId","index");
                        window.location.href = '${ctx}/index';
                    } else {
                        $('#error_info').html(rsData.message);
                    }
                }
            });
        }
    </script>

</head>

<body class="login" mycollectionplug="bind">
<form id="login_form">
    <div class="login_m">
        <div class="login_logo"><img src="${ctx}/static/login/logo.png" width="196" height="46"></div>
        <div class="login_boder">

            <div class="login_padding" id="login_model">

                <h2>用户名</h2>
                <label>
                    <input type="text" name="username" id="username" class="txt_input txt_input2"<#---->/>
                </label>
                <h2>密码</h2>
                <label>
                    <input type="password" name="password" id="password" class="txt_input"/>
                </label>
                <div class="rem_sub">
                    <div class="rem_sub_l">
                        <label for="checkbox" style="color: red" id="error_info"></label>
                    </div>
                    <label>
                        <input type="button" class="sub_button" name="button" id="button" value="登录"
                               onclick="doLogin();" style="opacity: 0.7;">
                    </label>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>