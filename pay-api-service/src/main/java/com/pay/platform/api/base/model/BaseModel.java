package com.pay.platform.api.base.model;

import com.pay.platform.common.util.JsonUtil;

/**
 * User: zjt
 * DateTime: 2016/10/6 10:16
 *
 * 模型类的基类
 */
public class BaseModel {

    @Override
    public String toString() {
        return JsonUtil.parseToJsonStr(this);
    }

}