package ${packageName}.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${packageName}.model.${classPrefix}Model;
import ${packageName}.service.${classPrefix}Service;
import ${packageName}.dao.${classPrefix}Dao;


/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
@Service
public class ${classPrefix}ServiceImpl implements ${classPrefix}Service {

    @Autowired
    private ${classPrefix}Dao ${propertyPrefix}Dao;

    @Override
    public PageInfo<${classPrefix}Model> query${classPrefix}List(${classPrefix}Model ${propertyPrefix}){
        return new PageInfo(${propertyPrefix}Dao.query${classPrefix}List(${propertyPrefix}));
    }

    @Override
    public ${classPrefix}Model query${classPrefix}ById(String id) {
        return ${propertyPrefix}Dao.query${classPrefix}ById(id);
    }

    @Override
    public Integer add${classPrefix}(${classPrefix}Model ${propertyPrefix}) {
        return ${propertyPrefix}Dao.add${classPrefix}(${propertyPrefix});
    }

    @Override
    public Integer delete${classPrefix}(String[] ids) {
        return ${propertyPrefix}Dao.delete${classPrefix}(ids);
    }

    @Override
    public Integer delete${classPrefix}ByLogic(String[] ids) {
        return ${propertyPrefix}Dao.delete${classPrefix}ByLogic(ids);
    }

    @Override
    public Integer update${classPrefix}(${classPrefix}Model ${propertyPrefix}) {
        return ${propertyPrefix}Dao.update${classPrefix}(${propertyPrefix});
    }

}