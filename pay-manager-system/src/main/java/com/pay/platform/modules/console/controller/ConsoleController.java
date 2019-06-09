package com.pay.platform.modules.console.controller;

import com.pay.platform.common.context.AppContext;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Enumeration;

/**
 * UserModel:
 * DateTime: 16/10/4 10:23
 *
 * 后台管理系统 - 控制台,主要是登录页,首页相关操作
 */
@Controller
@RequestMapping("/console")
public class ConsoleController {

    /**
     * 返回主界面
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpSession session){

        ModelAndView model = new ModelAndView();
        model.setViewName("/console/index");

        UserModel userModel = AppContext.getCurrentUser();
        if(session.getAttribute("currentUser") == null){
            session.setAttribute("currentUser",userModel);
        }

        return model;
    }

    /**
     * 返回登录界面
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(boolean isLoginError){

        ModelAndView model = new ModelAndView();

        //已登录,则调到首页
        if(AppContext.getCurrentUser() != null){
            model.setViewName("redirect:/console/index");
        }else{

            model.setViewName("/console/login");

            //系统采用spring security完成登录, 当发生错误,注销时会跳转到此界面, 并给出相对应提示
            if(isLoginError){
                model.addObject("msg", "登录失败, 请检查用户名密码!");
            }

        }

        return model;
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request){

        //清空session信息
        Enumeration<String> em = request.getSession().getAttributeNames();
        while (em.hasMoreElements()) {
            request.getSession().removeAttribute(em.nextElement().toString());
        }

        request.getSession().invalidate();

        return "redirect:/console/login";
    }


}