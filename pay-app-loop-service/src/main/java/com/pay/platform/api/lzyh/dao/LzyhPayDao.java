package com.pay.platform.api.lzyh.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * User:
 * DateTime: 2016/10/7 20:16
 */
@MapperScan
public interface LzyhPayDao {

    List<Map<String,Object>> getWaitQrCodeData(@Param("codeNum") String codeNum);

}