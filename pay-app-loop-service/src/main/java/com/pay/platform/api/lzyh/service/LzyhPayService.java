package com.pay.platform.api.lzyh.service;

import java.util.List;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/5/22 16:02
 */
public interface LzyhPayService {

    List<Map<String,Object>> getWaitQrCodeData(String codeNum);

}