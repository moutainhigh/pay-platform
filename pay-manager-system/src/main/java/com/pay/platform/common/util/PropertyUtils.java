package com.pay.platform.common.util;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class PropertyUtils{

    static Map<String, String> parameterMap = new HashMap<>();


    public static String getParameterVlue(String key) throws IOException {
        try {
            if(parameterMap.size()==0){
                Properties ps = PropertiesLoaderUtils.loadAllProperties("config/parameterValue.properties");
                Iterator it = ps.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Double> entry = (Map.Entry) it.next();
                    String mkey = entry.getKey();
                    String value = String.valueOf(entry.getValue());
                    parameterMap.put(mkey, value);
                }
            }
        } catch (IOException e) {
             System.out.println("读取parameterValue配置文件失败");
             throw e;
        }
        return  parameterMap.get(key);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getParameterVlue("ENCODING_AES_KEY"));
    }

}
