package com.pay.platform.modules.sysmgr.role.controller;

import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.sysmgr.log.annotation.SystemControllerLog;
import com.pay.platform.modules.sysmgr.permission.model.PermissionModel;
import com.pay.platform.modules.sysmgr.role.model.RoleModel;
import com.pay.platform.modules.sysmgr.role.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:14
 */
@Controller
@RequestMapping("/sysmgr/role")
public class RoleController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    /**
     * 新增角色
     * @param response
     * @param role
     * @throws Exception
     */
    @RequestMapping(value = "/addRole" , produces = "application/json")
    @SystemControllerLog(module = "角色管理" , operation = "新增角色")
    public void addRole(HttpServletResponse response, RoleModel role) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = roleService.addRole(role);

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
     * 删除角色
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deleteRole" , produces = "application/json")
    @SystemControllerLog(module = "角色管理" , operation = "删除角色")
    public void deleteRole(HttpServletResponse response,String ids) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = roleService.deleteRole(ids.split(","));

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
     * 修改角色信息
     * @param request
     * @param response
     * @param role
     * @throws Exception
     */
    @RequestMapping(value = "/updateRole" , produces = "application/json",method= RequestMethod.POST)
    @SystemControllerLog(module = "角色管理" , operation = "修改角色")
    public void updateRole(HttpServletRequest request, HttpServletResponse response, RoleModel role) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = roleService.updateRole(role);

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
     * 分页查询角色列表
     * @param request
     * @param response
     * @param name
     * @param code
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryRoleList" , produces = "application/json")
    public PageInfo<RoleModel> queryRoleList(HttpServletRequest request,HttpServletResponse response,String name , String code) throws Exception{
        setPageInfo(request);
        return roleService.queryRoleList(name,code);
    }

    /**
     * 根据id查询
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/queryRoleById" , produces = "application/json")
    public void queryRoleById(HttpServletResponse response,String id) throws Exception{

        JSONObject json = new JSONObject();

        RoleModel role = roleService.queryRoleById(id);
        json.put("success",true);
        json.put("data",role);

        writeJson(response,json.toString());

    }

    /**
     * 授予角色权限
     * @param request
     * @param response
     * @param roleId
     * @param permissionIds
     * @throws Exception
     */
    @RequestMapping(value = "/grantPermission" , produces = "application/json",method= RequestMethod.POST)
    @SystemControllerLog(module = "角色管理" , operation = "授予角色权限")
    public void grantPermission(HttpServletRequest request, HttpServletResponse response, String roleId, String permissionIds) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = roleService.grantPermission(roleId,permissionIds.split(","));

        if(count > 0){
            json.put("success",true);
            json.put("msg","保存成功");
        }else{
            json.put("success",false);
            json.put("msg","保存失败");
        }

        writeJson(response , json.toString());

    }

    /**
     * 查询权限列表, 角色授予权限时 - 回显
     * @param roleId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryPermissionListForGrant" , produces = "application/json")
    public List<PermissionModel> queryPermissionListForGrant(String roleId) throws Exception{
        return roleService.queryPermissionListForGrant(roleId);
    }

}