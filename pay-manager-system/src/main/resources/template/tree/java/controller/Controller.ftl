package ${packageName}.controller;

import com.pay.platform.security.CommonRequest;
import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.sysmgr.log.annotation.SystemControllerLog;

import com.github.pagehelper.StringUtil;
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

import ${packageName}.model.${classPrefix}Model;
import ${packageName}.service.${classPrefix}Service;

/**
 * User:
 * DateTime: 2016/10/19 20:24
 *
 */
@Controller
@RequestMapping("${requestUrl}")
public class ${classPrefix}Controller extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(${classPrefix}Controller.class);

    @Autowired
    private ${classPrefix}Service ${propertyPrefix}Service;

    /**
     * 查询${moduleName}列表
     * @param ${propertyPrefix}
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/query${classPrefix}List" , produces = "application/json;charset=UTF-8")
    public List<${classPrefix}Model> query${classPrefix}List(${classPrefix}Model ${propertyPrefix}) throws Exception{
        return ${propertyPrefix}Service.query${classPrefix}List(${propertyPrefix});
    }

    /**
     * 根据id查询${moduleName}
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/query${classPrefix}ById" , produces = "application/json;charset=UTF-8")
    public void query${classPrefix}ById(HttpServletResponse response,String id) throws Exception{

        JSONObject json = new JSONObject();

        ${classPrefix}Model ${propertyPrefix} = ${propertyPrefix}Service.query${classPrefix}ById(id);
        json.put("success",true);
        json.put("data",${propertyPrefix});

        writeJson(response,json.toString());

    }

    /**
     * 添加${moduleName}
     * @param response
     * @param ${propertyPrefix}
     * @throws Exception
     */
    @RequestMapping(value = "/add${classPrefix}" , produces = "application/json;charset=UTF-8")
    @SystemControllerLog(module = "${moduleName}管理" , operation = "新增${moduleName}")
    public void add${classPrefix}(HttpServletResponse response, ${classPrefix}Model ${propertyPrefix}) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = ${propertyPrefix}Service.add${classPrefix}(${propertyPrefix});

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
     * 删除${moduleName}
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/delete${classPrefix}" , produces = "application/json;charset=UTF-8")
    @SystemControllerLog(module = "${moduleName}管理" , operation = "删除${moduleName}")
    public void delete${classPrefix}(HttpServletResponse response,String ids) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = ${propertyPrefix}Service.delete${classPrefix}(ids.split(","));

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
     * 逻辑删除${moduleName} - 字段为isDel
     * @param response
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/delete${classPrefix}ByLogic" , produces = "application/json")
    @SystemControllerLog(module = "${moduleName}管理" , operation = "删除${moduleName}")
    public void delete${classPrefix}ByLogic(HttpServletResponse response,String ids) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = ${propertyPrefix}Service.delete${classPrefix}ByLogic(ids.split(","));

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
     * 前往编辑${moduleName}界面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/toEdit${classPrefix}")
    public String toEdit${classPrefix}(Model model,String id){
        model.addAttribute("${propertyPrefix}",${propertyPrefix}Service.query${classPrefix}ById(id));
        return "${requestUrl}/${propertyPrefix}_edit";
    }

    /**
     * 修改${moduleName}信息
     * @param request
     * @param response
     * @param ${propertyPrefix}
     * @throws Exception
     */
    @RequestMapping(value = "/update${classPrefix}" , produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    @SystemControllerLog(module = "${moduleName}管理" , operation = "修改${moduleName}")
    public void update${classPrefix}(HttpServletRequest request, HttpServletResponse response, ${classPrefix}Model ${propertyPrefix}) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = ${propertyPrefix}Service.update${classPrefix}(${propertyPrefix});

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
     * 修改${moduleName}位置
     * @param request
     * @param response
     * @param id1
     * @param seq1
     * @param id2
     * @param seq2
     * @throws Exception
     */
    @RequestMapping(value = "/updatePosition" , produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    @SystemControllerLog(module = "${moduleName}管理" , operation = "修改${moduleName}位置")
    public void updatePosition(HttpServletRequest request, HttpServletResponse response, String id1,Integer seq1,String id2,Integer seq2) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = ${propertyPrefix}Service.updatePosition(id1,seq1,id2,seq2);

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