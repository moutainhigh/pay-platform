package ${packageName}.dao;

import org.mybatis.spring.annotation.MapperScan;
import java.util.List;
import java.util.Map;
import ${packageName}.model.${classPrefix}Model;

/**
 * User: zjt
 * DateTime: 2016/10/19 20:28
 */
@MapperScan
public interface ${classPrefix}Dao {

    /**
     * 查询${moduleName}列表
     * @param ${propertyPrefix}Model
     * @return
     */
    List<${classPrefix}Model> query${classPrefix}List(${classPrefix}Model ${propertyPrefix}Model);

    /**
     * 根据id查询${moduleName}
     * @param id
     * @return
     */
    ${classPrefix}Model query${classPrefix}ById(String id);

    /**
     * 新增${moduleName}
     * @param model
     * @return
     */
    Integer add${classPrefix}(${classPrefix}Model model);

    /**
     * 获取下一个排序值
     * @param params
     * @return
     */
    Integer queryNextSeq(Map<String, Object> params);

    /**
     * 删除${moduleName}
     * @param ids
     * @return
     */
    Integer delete${classPrefix}(String[] ids);

    /**
     * 逻辑删除${moduleName}
     * @param ids
     * @return
     */
    Integer delete${classPrefix}ByLogic(String[] ids);

    /**
     * 更新${moduleName}
     * @param model
     * @return
     */
    Integer update${classPrefix}(${classPrefix}Model model);

    /**
     * 修改${moduleName}位置
     * @param model
     * @return
     */
    Integer updatePosition(${classPrefix}Model model);

}