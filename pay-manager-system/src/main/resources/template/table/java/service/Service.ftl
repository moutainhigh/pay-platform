package ${packageName}.service;

import com.github.pagehelper.PageInfo;
import java.util.List;
import ${packageName}.model.${classPrefix}Model;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
public interface ${classPrefix}Service {

    /**
    * 分页查询${moduleName}
    * @param ${propertyPrefix}
    * @return
    */
    PageInfo<${classPrefix}Model> query${classPrefix}List(${classPrefix}Model ${propertyPrefix});

    /**
    * 根据id查询${moduleName}
    * @param id
    * @return
    */
    ${classPrefix}Model query${classPrefix}ById(String id);

    /**
     * 新增${moduleName}
     * @param ${propertyPrefix}
     * @return
     */
    Integer add${classPrefix}(${classPrefix}Model ${propertyPrefix});

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
     * @param ${propertyPrefix}
     * @return
     */
    Integer update${classPrefix}(${classPrefix}Model ${propertyPrefix});

}