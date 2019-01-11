package com.pay.platform.api.base.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.pay.platform.common.util.FieldConvertUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: zjt
 * DateTime: 2016/10/5 20:30
 * <p>
 * 控制器基类,命名方式
 * 返回页面,以toXxx格式,例如: toList,toAdd,toEdit,toDetail
 * 数据操作,例如: add,delete,update,queryList,queryById
 */
@Controller
public class BaseController {

    /**
     * 获取当前对象的日志输出工具
     *
     * @return
     */
    public Logger getCurrentLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    /**
     * 返回json数据
     *
     * @param response
     * @param json
     * @throws Exception
     */
    protected void writeJson(HttpServletResponse response, String json) throws Exception {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(json);
    }

    /**
     * 统一异常处理 - controller中直接抛出即可
     *
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler
    public void handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {

        //将堆栈信息打印到控制台,并写入到日志文件
        getCurrentLogger().error(e.getMessage(), e);

        String uri = request.getRequestURI();
        if (uri.contains("?")) {
            uri = uri.substring(0, uri.indexOf("?"));
        }

        //ajax请求
        if (request.getHeader("accept").contains("application/json") || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest"))) {
            JSONObject json = new JSONObject();
            json.put("success", false);
            json.put("msg", "服务器内部错误");
            writeJson(response, json.toString());
        }
        //其它请求
        else {

            JSONObject json = new JSONObject();
            json.put("success", false);
            json.put("msg", "服务器内部错误");
            writeJson(response, json.toString());

        }

    }

}