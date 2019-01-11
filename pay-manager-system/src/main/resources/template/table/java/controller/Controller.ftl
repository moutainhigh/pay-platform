package ${packageName}.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.gzqinghui.base.ui.BaseController;
import cn.com.gzqinghui.sysextmgr.log.annotation.SystemControllerLog;

import ${packageName}.model.${classPrefix}Model;
import ${packageName}.service.${classPrefix}Service;


/**
 * User: zjt
 * DateTime: 2017/03/20 12:14
 */
@Controller
@RequestMapping("${requestUrl}")
public class ${classPrefix}Controller extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(${classPrefix}Controller.class);

    @Autowired
    private ${classPrefix}Service ${propertyPrefix}Service;

    /**
     * 分页查询${moduleName}列表
     * @param request
     * @param response
     * @param ${propertyPrefix}
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/query${classPrefix}List" , produces = "application/json")
    public PageInfo<${classPrefix}Model> query${classPrefix}List(HttpServletRequest request,HttpServletResponse response,${classPrefix}Model ${propertyPrefix}) throws Exception{
        setPageInfoWithMybatis(request);
        return ${propertyPrefix}Service.query${classPrefix}List(${propertyPrefix});
    }

    /**
     * 根据id查询
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/query${classPrefix}ById" , produces = "application/json")
    public void query${classPrefix}ById(HttpServletResponse response,String id) throws Exception{

        JSONObject json = new JSONObject();

        ${classPrefix}Model ${propertyPrefix} = ${propertyPrefix}Service.query${classPrefix}ById(id);
        json.put("success",true);
        json.put("data",${propertyPrefix});

        writeJson(response,json.toString());

    }

    /**
     * 新增${moduleName}
     * @param response
     * @param ${propertyPrefix}
     * @throws Exception
     */
    @RequestMapping(value = "/add${classPrefix}" , produces = "application/json")
    @SystemControllerLog(model = "${moduleName}管理" , description = "新增${moduleName}")
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
    @RequestMapping(value = "/delete${classPrefix}" , produces = "application/json")
    @SystemControllerLog(model = "${moduleName}管理" , description = "删除${moduleName}")
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
    @SystemControllerLog(model = "${moduleName}管理" , description = "删除${moduleName}")
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
     * 修改${moduleName}信息
     * @param request
     * @param response
     * @param ${propertyPrefix}
     * @throws Exception
     */
    @RequestMapping(value = "/update${classPrefix}" , produces = "application/json",method= RequestMethod.POST)
    @SystemControllerLog(model = "${moduleName}管理" , description = "修改${moduleName}")
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

}