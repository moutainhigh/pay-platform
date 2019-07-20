package com.pay.platform.api.lzyh.service.impl;

import com.pay.platform.api.lzyh.dao.LzyhPayDao;
import com.pay.platform.api.lzyh.service.LzyhPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/5/22 16:02
 */
@Service
public class LzyhPayServiceImpl implements LzyhPayService {

    private static final Logger logger = LoggerFactory.getLogger(LzyhPayServiceImpl.class);

    @Autowired
    private LzyhPayDao lzyhPayDao;

    @Override
    public List<Map<String, Object>> getWaitQrCodeData(String codeNum) {
        return lzyhPayDao.getWaitQrCodeData(codeNum);
    }

}