package com.pay.platform.modules.sysmgr.dictionary.service.impl;

import com.pay.platform.modules.sysmgr.dictionary.dao.DictionaryDao;
import com.pay.platform.modules.sysmgr.dictionary.model.DictionaryModel;
import com.pay.platform.modules.sysmgr.dictionary.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2016/10/19 20:28
 */
@Service
public class DictionaryServiceImpl implements DictionaryService{

    @Autowired
    private DictionaryDao dictionaryDao;

    @Override
    public Integer addDictionary(DictionaryModel model) {

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("level",model.getLevel());
        params.put("parentId",model.getParentId());

        Integer nextSeq = dictionaryDao.queryNextSeq(params);
        model.setSeq(nextSeq);

        return dictionaryDao.addDictionary(model);
    }

    @Override
    public DictionaryModel queryDictionaryByLevelAndDictType(String id,Integer level, String dictType) {

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",id);
        params.put("level",level);
        params.put("dictType",dictType);

        return dictionaryDao.queryDictionaryByLevelAndDictType(params);
    }

    @Override
    public List<DictionaryModel> queryDictionaryListByDictType(String dictType) {
        return dictionaryDao.queryDictionaryListByDictType(dictType);
    }

    @Override
    public String queryDictionaryItermDesc(String dictType, String itemCode) {
        return dictionaryDao.queryDictionaryItermDesc(dictType , itemCode.split(","));
    }

    @Override
    public Integer deleteDictionary(String id) {
        return dictionaryDao.deleteDictionary(id);
    }

    @Override
    public Integer updateDictionary(DictionaryModel model) {
        return dictionaryDao.updateDictionary(model);
    }

    @Override
    public Integer updatePosition(String id1, Integer seq1, String id2, Integer seq2) {

        Integer count = 0;

        DictionaryModel dictionary1 = new DictionaryModel();
        dictionary1.setId(id1);
        dictionary1.setSeq(seq2);

        DictionaryModel dictionary2 = new DictionaryModel();
        dictionary2.setId(id2);
        dictionary2.setSeq(seq1);

        count += dictionaryDao.updatePosition(dictionary1);
        count += dictionaryDao.updatePosition(dictionary2);

        return count;

    }

    @Override
    public List<DictionaryModel> queryDictionaryList(DictionaryModel dictionaryModel) {
        return dictionaryDao.queryDictionaryList(dictionaryModel);
    }

    @Override
    public DictionaryModel queryDictionaryById(String id) {
        return dictionaryDao.queryDictionaryById(id);
    }


}