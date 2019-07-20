package com.pay.platform.api.base.service.impl;

import com.pay.platform.api.base.dao.BaseDao;
import com.pay.platform.api.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/7/20 16:47
 */
@Service
public class BaseServiceImpl implements BaseService{

    @Autowired
    private BaseDao baseDao;

    @Override
    public Map<String, Object> queryTradeCodeByCudeNum(String codeNum) {
        return baseDao.queryTradeCodeByCudeNum(codeNum);
    }

}