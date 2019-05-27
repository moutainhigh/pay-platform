package com.baseframe.generate;

import com.pay.platform.common.util.JsonUtil;
import com.pay.platform.security.util.ApiSignUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * User:
 * DateTime: 2019/1/13 21:48
 */
public class TestApiSign {

    @Test
    public void generateSign(){

        String merchantSecret = "asd23123xc33";

        Map<String,String> params = new HashMap<>();
        params.put("timestamp" , System.currentTimeMillis() + "");
        params.put("merchantNo" , "10011");
        params.put("phone" , "18617369762");

        System.out.println(" ---> " + JsonUtil.parseToJsonStr(params));

        String sign = ApiSignUtil.buildSignByMd5(params , merchantSecret);
        System.out.println(" ---> " + sign);


    }


}