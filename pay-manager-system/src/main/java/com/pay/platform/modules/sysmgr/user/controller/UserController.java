package com.pay.platform.modules.sysmgr.user.controller;

import com.pay.platform.common.context.AppContext;
import com.pay.platform.security.CommonRequest;
import com.pay.platform.common.util.StringUtil;
import com.pay.platform.common.util.encrypt.Md5Util;
import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.sysmgr.log.annotation.SystemControllerLog;
import com.pay.platform.modules.sysmgr.organization.model.OrganizationModel;
import com.pay.platform.modules.sysmgr.organization.service.OrganizationService;
import com.pay.platform.modules.sysmgr.role.model.RoleModel;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import com.pay.platform.modules.sysmgr.user.service.UserService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * UserModel: zjt
 * DateTime: 16/9/30 14:08
 * <p>
 * 用户管理
 */
@Controller
@RequestMapping("/sysmgr/user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    /**
     * 分页查询用户
     *
     * @param request
     * @param nickname
     * @param account
     * @param orgId
     * @param isContainChildren
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryUserList", produces = "application/json;charset=UTF-8")
    public PageInfo<UserModel> queryUserList(HttpServletRequest request, String nickname, String account, String orgId, Integer isContainChildren) throws Exception {

        List<String> orgIdList = new ArrayList<String>();
        UserModel userModel = AppContext.getCurrentUser();

//        //选择机构,根据选择机构查看
//        if (StringUtil.isNotEmpty(orgId)) {
//            orgIdList.add(orgId);
//            if (isContainChildren == 1) {
//                organizationService.treeById(orgId, orgIdList);      //包含下级,则递归获取
//            }
//        }
//        //没有选择机构,默认查看当前用户可查看的所有信息
//        else {
//            orgId = userModel.getOrgId();
//            orgIdList.add(orgId);
//            organizationService.treeById(orgId, orgIdList);          //递归获取
//        }

        //设置分页信息
        setPageInfo(request);
        return userService.queryUserList(nickname, account, null);
    }

    /**
     * 新增用户
     *
     * @param response
     * @param user
     * @throws Exception
     */
    @RequestMapping(value = "/addUser", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @SystemControllerLog(module = "用户管理", operation = "新增用户")
    public synchronized void addUser(HttpServletResponse response, UserModel user) throws Exception {

        JSONObject json = new JSONObject();

        Integer exists = userService.queryUserExistsByAccount(user.getAccount());
        if (exists > 0) {
            json.put("success", false);
            json.put("msg", "新增失败,账号已存在!");
        } else {

            Integer count = userService.addUser(user);
            if (count > 0) {
                json.put("success", true);
                json.put("msg", "新增成功");
            } else {
                json.put("success", false);
                json.put("msg", "新增失败");
            }

        }

        writeJson(response, json.toString());

    }

    /**
     * 删除用户
     *
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/deleteUser", produces = "application/json;charset=UTF-8")
    @SystemControllerLog(module = "用户管理", operation = "删除用户")
    public void deleteUser(HttpServletResponse response, String ids) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = userService.deleteUser(ids.split(","));
        if (count > 0) {
            json.put("success", true);
            json.put("msg", "删除成功");
        } else {
            json.put("success", false);
            json.put("msg", "删除失败");
        }

        writeJson(response, json.toString());

    }

    /**
     * 修改用户 (通用模块,因为可修改个人信息)
     *
     * @param request
     * @param response
     * @param user
     * @throws Exception
     */
    @RequestMapping(value = "/updateUser", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @SystemControllerLog(module = "用户管理", operation = "修改用户")
    @CommonRequest
    public void updateUser(HttpServletRequest request, HttpServletResponse response, UserModel user) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = userService.updateUser(user);

        if (count > 0) {
            json.put("success", true);
            json.put("msg", "修改成功");

            //如果修改自身,则需要刷新session用户数据
            UserModel currentUser = AppContext.getCurrentUser();
            if (currentUser.getId().equals(user.getId())) {
                currentUser.setNickname(user.getNickname());
                currentUser.setAccount(user.getAccount());
                currentUser.setPassword(Md5Util.md5_32(user.getPassword()));
                currentUser.setOrgId(user.getOrgId());
                currentUser.setAvatar(user.getAvatar());
            }

        } else {
            json.put("success", false);
            json.put("msg", "修改失败");
        }

        writeJson(response, json.toString());

    }

    /**
     * 查询角色列表 - 授予角色时,回显
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryRoleListForGrant", produces = "application/json;charset=UTF-8")
    public List<RoleModel> queryRoleListForGrant(String userId) throws Exception {
        return userService.queryRoleListForGrant(userId);
    }

    /**
     * 授予用户角色
     *
     * @param request
     * @param response
     * @param userId
     * @param roleIds
     * @throws Exception
     */
    @RequestMapping(value = "/grantRole", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @SystemControllerLog(module = "用户管理", operation = "授予用户角色")
    public void grantRole(HttpServletRequest request, HttpServletResponse response, String userId, String roleIds) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = userService.grantRole(userId, roleIds.split(","));

        if (count > 0) {
            json.put("success", true);
            json.put("msg", "保存成功");
        } else {
            json.put("success", false);
            json.put("msg", "保存失败");
        }

        writeJson(response, json.toString());

    }

    /**
     * 查询当前用户的组织机构
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryCurrentUserOrgList", produces = "application/json;charset=UTF-8")
    @CommonRequest
    public List<OrganizationModel> queryCurrentUserOrgList() throws Exception {

        List<OrganizationModel> orgList = new ArrayList<OrganizationModel>();

        //获取当前用户的组织机构
        String orgId = AppContext.getCurrentUser().getOrgId();
        orgList.add(organizationService.queryOrganizationById(orgId));

        //递归获取所有子节点
        organizationService.treeByModel(orgId, orgList);

        return orgList;

    }

    /**
     * 查询用户组织机构 - 回显
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryUserOrganizationListForEdit", produces = "application/json;charset=UTF-8")
    public List<OrganizationModel> queryUserOrganizationListForEdit(String userId) throws Exception {

        List<OrganizationModel> orgList = new ArrayList<OrganizationModel>();

        //获取当前用户的组织机构
        String orgId = AppContext.getCurrentUser().getOrgId();
        orgList.add(organizationService.queryOrganizationById(orgId));

        //递归获取所有子节点
        organizationService.treeByModel(orgId, orgList);

        //查询该用户的组织机构 , 决定是否回显
        if (StringUtil.isNotEmpty(userId)) {
            OrganizationModel userOrgModel = userService.queryOrgInfoByUserId(userId);
            if (userOrgModel != null) {
                orgList.forEach(orgModel -> {
                    if (orgModel.getId().equalsIgnoreCase(userOrgModel.getId())) {
                        orgModel.setChecked(true);
                    }
                });
            }
        }

        return orgList;

    }

    /**
     * 修改用户组织机构
     *
     * @param request
     * @param response
     * @param userId
     * @param orgId
     * @throws Exception
     */
    @RequestMapping(value = "/updateUserOrganization", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @SystemControllerLog(module = "用户管理", operation = "修改用户组织机构")
    public void updateUserOrganization(HttpServletRequest request, HttpServletResponse response, String userId, String orgId) throws Exception {

        JSONObject json = new JSONObject();

        Integer count = userService.updateUserOrganization(userId, orgId);

        if (count > 0) {
            json.put("success", true);
            json.put("msg", "保存成功");
        } else {
            json.put("success", false);
            json.put("msg", "保存失败");
        }

        writeJson(response, json.toString());

    }


}