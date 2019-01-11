package com.pay.platform.modules.sysmgr.dictionary.dao;

import com.pay.platform.modules.sysmgr.dictionary.model.DictionaryModel;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2016/10/19 20:28
 */
@MapperScan
public interface DictionaryDao {

    /**
     * 新增字典
     * @param model
     * @return
     */
    Integer addDictionary(DictionaryModel model);

    /**
     * 获取下一个排序值
     * @param params
     * @return
     */
    Integer queryNextSeq(Map<String,Object> params);

    /**
     * 根据级别和字典类型查询
     * @param params
     * @return
     */
    DictionaryModel queryDictionaryByLevelAndDictType(Map<String, Object> params);

    /**
     * 删除字典
     * @param id
     * @return
     */
    Integer deleteDictionary(String id);

    /**
     * 更新字典
     * @param model
     * @return
     */
    Integer updateDictionary(DictionaryModel model);

    /**
     * 修改字典位置
     * @param model
     * @return
     */
    Integer updatePosition(DictionaryModel model);

    /**
     * 查询字典列表
     * @param dictionaryModel
     * @return
     */
    List<DictionaryModel> queryDictionaryList(DictionaryModel dictionaryModel);

    /**
     * 根据id查询字典
     * @param id
     * @return
     */
    DictionaryModel queryDictionaryById(String id);

    /**
     * 根据字典类型查询
     * @param dictType
     * @return
     */
    List<DictionaryModel> queryDictionaryListByDictType(String dictType);

    /**
     * 查询字典itemDec
     * @param dictType
     * @param itemCodeArray
     * @return
     */
    String queryDictionaryItermDesc(@Param("dictType") String dictType, @Param("itemCodeArray") String[] itemCodeArray);
}