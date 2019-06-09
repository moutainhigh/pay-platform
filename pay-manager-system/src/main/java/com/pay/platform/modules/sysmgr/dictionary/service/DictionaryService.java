package com.pay.platform.modules.sysmgr.dictionary.service;

import com.pay.platform.modules.sysmgr.dictionary.model.DictionaryModel;

import java.util.List;

/**
 * User:
 * DateTime: 2016/10/19 20:28
 */
public interface DictionaryService {

    /**
     * 新增字段
     * @param model
     * @return
     */
    Integer addDictionary(DictionaryModel model);

    /**
     * 删除字典
     * @param id
     * @return
     */
    Integer deleteDictionary(String id);

    /**
     * 修改字典
     * @param model
     * @return
     */
    Integer updateDictionary(DictionaryModel model);

    /**
     * 更新字典的位置
     * @param id1
     * @param seq1
     * @param id2
     * @param seq2
     * @return
     */
    Integer updatePosition(String id1,Integer seq1,String id2,Integer seq2);

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
     * 根据级别和字典类型查询
     * @param id
     * @param level
     * @param dictType
     * @return
     */
    DictionaryModel queryDictionaryByLevelAndDictType(String id, Integer level, String dictType);

    /**
     * 根据字典类型查询
     * @param dictType
     * @return
     */
    List<DictionaryModel> queryDictionaryListByDictType(String dictType);

    /**
     * 查询字典itemDec
     * @param dictType
     * @param itemCode
     * @return
     */
    String queryDictionaryItermDesc(String dictType, String itemCode);

}