package com.pay.platform.modules.sysmgr.dictionary.controller;

import com.pay.platform.security.CommonRequest;
import com.pay.platform.modules.base.controller.BaseController;
import com.pay.platform.modules.sysmgr.dictionary.model.DictionaryModel;
import com.pay.platform.modules.sysmgr.dictionary.service.DictionaryService;
import com.pay.platform.modules.sysmgr.log.annotation.SystemControllerLog;
import com.github.pagehelper.StringUtil;
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
 * DateTime: 2016/10/19 20:24
 *
 * 字典模块 - 控制器
 */
@Controller
@RequestMapping("/sysmgr/dictionary")
public class DictionaryController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 查询字典列表
     * @param dictionaryModel
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryDictionaryList" , produces = "application/json;charset=UTF-8")
    public List<DictionaryModel> queryDictionaryList(DictionaryModel dictionaryModel) throws Exception{
        return dictionaryService.queryDictionaryList(dictionaryModel);
    }

    /**
     * 根据id查询字典
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/queryDictionaryById" , produces = "application/json;charset=UTF-8")
    public void queryDictionaryById(HttpServletResponse response,String id) throws Exception{

        JSONObject json = new JSONObject();

        DictionaryModel dictionary = dictionaryService.queryDictionaryById(id);
        json.put("success",true);
        json.put("data",dictionary);

        writeJson(response,json.toString());

    }

    /**
     * 添加字典
     * @param response
     * @param dictionary
     * @throws Exception
     */
    @RequestMapping(value = "/addDictionary" , produces = "application/json;charset=UTF-8")
    @SystemControllerLog(module = "字典管理" , operation = "新增字典")
    public void addDictionary(HttpServletResponse response, DictionaryModel dictionary) throws Exception{

        JSONObject json = new JSONObject();

        //判断字典类型是否已存在
        if(dictionary.getLevel() == 0 && StringUtil.isNotEmpty(dictionary.getDictType())){

            DictionaryModel result = dictionaryService.queryDictionaryByLevelAndDictType(null,dictionary.getLevel(),dictionary.getDictType());

            if(result != null ){

                json.put("success",false);
                json.put("msg","新增失败,字典已存在!");

                writeJson(response,json.toString());
                return;
            }

        }

        Integer count = dictionaryService.addDictionary(dictionary);

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
     * 删除字典
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/deleteDictionary" , produces = "application/json;charset=UTF-8")
    @SystemControllerLog(module = "字典管理" , operation = "删除字典")
    public void deleteDictionary(HttpServletResponse response,String id) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = dictionaryService.deleteDictionary(id);

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
     * 修改字典信息
     * @param request
     * @param response
     * @param dictionary
     * @throws Exception
     */
    @RequestMapping(value = "/updateDictionary" , produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    @SystemControllerLog(module = "字典管理" , operation = "修改字典")
    public void updateDictionary(HttpServletRequest request, HttpServletResponse response, DictionaryModel dictionary) throws Exception{

        JSONObject json = new JSONObject();

        //判断字典类型是否已存在
        if(dictionary.getLevel() == 0 && StringUtil.isNotEmpty(dictionary.getDictType())){

            DictionaryModel result = dictionaryService.queryDictionaryByLevelAndDictType(dictionary.getId(),dictionary.getLevel(),dictionary.getDictType());

            if(result != null ){

                json.put("success",false);
                json.put("msg","新增失败,字典已存在!");

                writeJson(response,json.toString());
                return;
            }

        }

        Integer count = dictionaryService.updateDictionary(dictionary);

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
     * 修改字典位置
     * @param request
     * @param response
     * @param id1
     * @param seq1
     * @param id2
     * @param seq2
     * @throws Exception
     */
    @RequestMapping(value = "/updatePosition" , produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    @SystemControllerLog(module = "字典管理" , operation = "修改字典位置")
    public void updatePosition(HttpServletRequest request, HttpServletResponse response, String id1,Integer seq1,String id2,Integer seq2) throws Exception{

        JSONObject json = new JSONObject();

        Integer count = dictionaryService.updatePosition(id1,seq1,id2,seq2);

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
     * 根据字典类型查询
     * @param request
     * @param response
     * @param dictType
     */
    @RequestMapping(value = "/queryDictionaryListByDictType" , produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    @SystemControllerLog(module = "字典管理" , operation = "根据字典类型查询")
    @CommonRequest
    public void queryDictionaryListByDictType(HttpServletRequest request, HttpServletResponse response , String dictType) throws Exception{

        JSONObject json = new JSONObject();

        List<DictionaryModel> data = dictionaryService.queryDictionaryListByDictType(dictType);
        json.put("success",true);
        json.put("data",data);

        writeJson(response,json.toString());

    }

    /**
     * 查询字典itemDesc,按逗号分隔
     * @param request
     * @param response
     * @param dictType
     */
    @RequestMapping(value = "/queryDictionaryItermDesc" , produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    @SystemControllerLog(module = "字典管理" , operation = "根据字典类型查询")
    @CommonRequest
    public void queryDictionaryItermDesc(HttpServletRequest request, HttpServletResponse response , String dictType , String itemCode) throws Exception{

        JSONObject json = new JSONObject();

        String itemDesc = dictionaryService.queryDictionaryItermDesc(dictType , itemCode);
        json.put("success",true);
        json.put("data",itemDesc);

        writeJson(response,json.toString());

    }


}