package org.xjy.jadmin.handler;

import com.jfinal.handler.Handler;
import org.xjy.jadmin.constant.AppConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiaojianyu on 17/6/15.
 */
public class FreeMarkerHandler extends Handler {

    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        request.setAttribute(AppConstant.CONTEXT_PATH_NAME, AppConstant.PUBLISH_URL);
        this.next.handle(target, request, response, isHandled);
    }
}
