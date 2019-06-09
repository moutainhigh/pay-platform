package com.pay.platform.modules.base.controller;

import com.pay.platform.common.util.FieldConvertUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User:
 * DateTime: 2016/10/5 20:30
 * <p>
 * 控制器基类,命名方式
 * 返回页面,以toXxx格式,例如: toList,toAdd,toEdit,toDetail
 * 数据操作,例如: add,delete,update,queryList,queryById
 */
@Controller
public class BaseController {

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
     * 设置分页参数,此方法会拦截之后执行的第一条查询语句进行分页/排序(所需的查询语句前一行调用)
     *
     * @param request
     */
    protected void setPageInfo(HttpServletRequest request) {

        Integer offset = 0;                             //开始索引
        Integer limit = 10;                             //显示条数
        String sort = "";               //字段名称
        String order = "";              //排序方式

        if (StringUtil.isNotEmpty(request.getParameter("offset"))) {
            offset = Integer.valueOf(request.getParameter("offset"));
        }

        if (StringUtil.isNotEmpty(request.getParameter("limit"))) {
            limit = Integer.valueOf(request.getParameter("limit"));
        }

        if (StringUtil.isNotEmpty(request.getParameter("sort")) && StringUtil.isNotEmpty(request.getParameter("order"))) {
            sort = request.getParameter("sort");
            order = request.getParameter("order");
        }

        //设置分页信息,之后执行的第一条查询语句,将会拦截,并加上分页,排序信息
        if (StringUtil.isNotEmpty(sort) && StringUtil.isNotEmpty(order)) {
            String orderBy = FieldConvertUtil.parseCamelToUnderline(sort) + " " + order;             //将排序字段 -> 转为数据库字段命名方式(即下划线)
            PageHelper.offsetPage(offset, limit, orderBy);
        } else {
            PageHelper.offsetPage(offset, limit);
        }

    }

    /**
     * 获取当前对象的日志输出工具
     *
     * @return
     */
    public Logger getCurrentLogger() {
        return LoggerFactory.getLogger(this.getClass());
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

            //转发跳转到500界面
            request.getRequestDispatcher("/view/error/500.jsp").forward(request, response);

            //根据不同异常提示错误信息
            if (e instanceof RuntimeException) {
                request.setAttribute("msg", "服务器内部错误");
            }

        }

    }

}