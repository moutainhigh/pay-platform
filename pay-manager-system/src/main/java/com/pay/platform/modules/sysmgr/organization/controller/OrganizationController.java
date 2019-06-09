package com.pay.platform.modules.sysmgr.organization.controller;

import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.sysmgr.log.annotation.SystemControllerLog;
import com.pay.platform.modules.sysmgr.organization.service.OrganizationService;
import com.pay.platform.modules.sysmgr.organization.model.OrganizationModel;
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
 * User:
 * DateTime: 2016/10/19 20:13
 *
 * 组织机构 - 控制器
 */
@Controller
@RequestMapping("/sysmgr/organization")
public class OrganizationController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private OrganizationService organizationService;

    /**
     * 查询组织机构
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryOrganizationList" , produces = "application/json;charset=UTF-8")
    public List<OrganizationModel> queryOrganizationList() throws Exception{
        return organizationService.queryOrganizationList();
    }

    /**
     * 根据id查询组织机构
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/queryOrganizationById" , produces = "application/json;charset=UTF-8")
    public void queryOrganizationById(HttpServletResponse response,String id) throws Exception{

        JSONObject json = new JSONObject();

        OrganizationModel organization = organizationService.queryOrganizationById(id);
        json.put("success",true);
        json.put("data",organization);

        writeJson(response,json.toString());

    }

    /**
     * 添加组织机构
     * @param response
     * @param organization
     * @throws Exception
     */
    @RequestMapping(value = "/addOrganization" , produces = "application/json;charset=UTF-8")
    @SystemControllerLog(module = "组织机构管理" , operation = "新增组织机构")
    public void addOrganization(HttpServletResponse response, OrganizationModel organization) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = organizationService.addOrganization(organization);

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
     * 删除组织机构
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/deleteOrganization" , produces = "application/json;charset=UTF-8")
    @SystemControllerLog(module = "组织机构管理" , operation = "删除组织机构")
    public void deleteOrganization(HttpServletResponse response,String id) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = organizationService.deleteOrganization(id);

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
     * 修改组织机构
     * @param request
     * @param response
     * @param organization
     * @throws Exception
     */
    @RequestMapping(value = "/updateOrganization" , produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    @SystemControllerLog(module = "组织机构管理" , operation = "修改组织机构")
    public void updateOrganization(HttpServletRequest request, HttpServletResponse response, OrganizationModel organization) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = organizationService.updateOrganization(organization);

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
     * 修改位置
     * @param request
     * @param response
     * @param id1
     * @param seq1
     * @param id2
     * @param seq2
     * @throws Exception
     */
    @RequestMapping(value = "/updatePosition" , produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    @SystemControllerLog(module = "组织机构管理" , operation = "修改组织机构位置")
    public void updatePosition(HttpServletRequest request, HttpServletResponse response, String id1,Integer seq1,String id2,Integer seq2) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = organizationService.updatePosition(id1,seq1,id2,seq2);

        if(count > 0){
            json.put("success",true);
            json.put("msg","移动成功");
        }else{
            json.put("success",false);
            json.put("msg","移动失败");
        }

        writeJson(response , json.toString());

    }

}