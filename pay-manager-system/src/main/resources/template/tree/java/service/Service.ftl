package ${packageName}.service;

import java.util.List;
import ${packageName}.model.${classPrefix}Model;

/**
 * User: zjt
 * DateTime: 2016/10/19 20:28
 */
public interface ${classPrefix}Service {

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
     * 修改${moduleName}
     * @param model
     * @return
     */
    Integer update${classPrefix}(${classPrefix}Model model);

    /**
     * 更新${moduleName}的位置
     * @param id1
     * @param seq1
     * @param id2
     * @param seq2
     * @return
     */
    Integer updatePosition(String id1, Integer seq1, String id2, Integer seq2);

}