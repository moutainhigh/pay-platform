package com.pay.platform.modules.sysmgr.permission.controller;

import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.sysmgr.log.annotation.SystemControllerLog;
import com.pay.platform.modules.sysmgr.permission.model.PermissionModel;
import com.pay.platform.modules.sysmgr.permission.service.PermissionService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User:
 * DateTime: 16/10/5 14:06
 *
 * 权限模块控制器
 */
@Controller
@RequestMapping("/sysmgr/permission")
public class PermissionController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionService permissionService;

    /**
     * 查询权限列表
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryPermissionList" , produces = "application/json;charset=UTF-8")
    public List<PermissionModel> queryPermissionList() throws Exception{
        return permissionService.queryPermissionList();
    }

    /**
     * 添加权限
     * @param response
     * @param permission
     * @throws Exception
     */
    @RequestMapping(value = "/addPermission" , produces = "application/json;charset=UTF-8")
    @SystemControllerLog(module = "权限管理" , operation = "新增权限")
    public void addPermission(HttpServletResponse response,PermissionModel permission) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = permissionService.addPermission(permission);

        if(count > 0){
            json.put("success",true);
            json.put("msg","新增成功");
        }else{
            json.put("success",false);
            json.put("msg","新增失败");
        }

        writeJson(response , json.toString());

    }

    /**
     * 删除权限
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deletePermission" , produces = "application/json;charset=UTF-8")
    @SystemControllerLog(module = "权限管理" , operation = "删除权限")
    public void deletePermission(HttpServletResponse response,String ids) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = permissionService.deletePermission(ids.split(","));

        if(count > 0){
            json.put("success",true);
            json.put("msg","删除成功");
        }else{
            json.put("success",false);
            json.put("msg","删除失败");
        }

        writeJson(response , json.toString());

    }

    /**
     * 前往编辑权限界面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/toEditPermission")
    public String toEditPermission(Model model,String id){
        model.addAttribute("permission",permissionService.queryPermissionById(id));
        return "sysmgr/permission/permission_edit";
    }

    /**
     * 修改权限
     * @param request
     * @param response
     * @param permission
     * @throws Exception
     */
    @RequestMapping(value = "/updatePermission" , produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    @SystemControllerLog(module = "权限管理" , operation = "修改权限")
    public void updatePermission(HttpServletRequest request, HttpServletResponse response, PermissionModel permission) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = permissionService.updatePermission(permission);

        if(count > 0){
            json.put("success",true);
            json.put("msg","修改成功");
        }else{
            json.put("success",false);
            json.put("msg","修改失败");
        }

        writeJson(response , json.toString());

    }

    /**
     * 修改权限位置
     * @param request
     * @param response
     * @param id1
     * @param seq1
     * @param id2
     * @param seq2
     * @throws Exception
     */
    @RequestMapping(value = "/updatePosition" , produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    @SystemControllerLog(module = "权限管理" , operation = "修改权限位置")
    public void updatePosition(HttpServletRequest request, HttpServletResponse response, String id1,Integer seq1,String id2,Integer seq2) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = permissionService.updatePosition(id1,seq1,id2,seq2);

        if(count > 0){
            json.put("success",true);
            json.put("msg","移动成功");
        }else{
            json.put("success",false);
            json.put("msg","移动失败");
        }

        writeJson(response , json.toString());

    }

    /**
     * 根据权限id查询
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/queryPermissionById" , produces = "application/json;charset=UTF-8")
    public void queryPermissionById(HttpServletResponse response,String id) throws Exception{

        JSONObject json = new JSONObject();

        PermissionModel permission = permissionService.queryPermissionById(id);
        json.put("success",true);
        json.put("data",permission);

        writeJson(response,json.toString());

    }

}