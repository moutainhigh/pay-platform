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
 * DateTime: 2016/10/19 20:28
 */
@Service
public class ${classPrefix}ServiceImpl implements ${classPrefix}Service{

    @Autowired
    private ${classPrefix}Dao ${propertyPrefix}Dao;

    @Override
    public List<${classPrefix}Model> query${classPrefix}List(${classPrefix}Model ${propertyPrefix}Model) {
        return ${propertyPrefix}Dao.query${classPrefix}List(${propertyPrefix}Model);
    }

    @Override
    public ${classPrefix}Model query${classPrefix}ById(String id) {
        return ${propertyPrefix}Dao.query${classPrefix}ById(id);
    }

    @Override
    public Integer add${classPrefix}(${classPrefix}Model model) {

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("level",model.getLevel());
        params.put("parentId",model.getParentId());

        Integer nextSeq = ${propertyPrefix}Dao.queryNextSeq(params);
        model.setSeq(nextSeq);

        return ${propertyPrefix}Dao.add${classPrefix}(model);
    }

    @Override
    public Integer delete${classPrefix}(String[] ids){
        return ${propertyPrefix}Dao.delete${classPrefix}(ids);
    }

    @Override
    public Integer delete${classPrefix}ByLogic(String[] ids) {
        return ${propertyPrefix}Dao.delete${classPrefix}ByLogic(ids);
    }

    @Override
    public Integer update${classPrefix}(${classPrefix}Model model) {
        return ${propertyPrefix}Dao.update${classPrefix}(model);
    }

    @Override
    public Integer updatePosition(String id1, Integer seq1, String id2, Integer seq2) {

        Integer count = 0;

        ${classPrefix}Model ${propertyPrefix}1 = new ${classPrefix}Model();
        ${propertyPrefix}1.setId(id1);
        ${propertyPrefix}1.setSeq(seq2);

        ${classPrefix}Model ${propertyPrefix}2 = new ${classPrefix}Model();
        ${propertyPrefix}2.setId(id2);
        ${propertyPrefix}2.setSeq(seq1);

        count += ${propertyPrefix}Dao.updatePosition(${propertyPrefix}1);
        count += ${propertyPrefix}Dao.updatePosition(${propertyPrefix}2);

        return count;

    }

}