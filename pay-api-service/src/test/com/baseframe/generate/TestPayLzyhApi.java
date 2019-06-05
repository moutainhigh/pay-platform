package com.baseframe.generate;

import com.pay.platform.common.enums.PayChannelEnum;
import com.pay.platform.common.util.*;
import com.pay.platform.security.util.ApiSignUtil;
import com.pay.platform.security.util.AppSignUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.baseframe.generate.TestByLzyhApi.getJsonStrFromHttpResponse;

/**
 * User:
 * DateTime: 2019/3/3 11:33
 */
public class TestPayLzyhApi {

    String merchantNo = "17818991616";                                  //商家编号
    String merchantSecret = "9ae482f4380b412d8554018f7bf2d023";         //商家密钥：调接口签名用
    String notifySecret = "b7874e246f916b97";                           //回调密钥：接收支付回调,解密数据用

    String serverURL = "http://localhost:8080";        //系统请求地址

    /**
     * 测试下单
     *
     * @throws Exception
     */
    @Test
    public void createOrderByLzyh() throws Exception {

//        for (int i = 0; i < 20; i++) {

        Map<String, String> params = new HashMap();
        params.put("merchantNo", merchantNo);
        params.put("merchantOrderNo", System.currentTimeMillis() + "");
        params.put("orderAmount", "1");
        params.put("payWay", PayChannelEnum.lzyhWechat.getCode());
        params.put("notifyUrl", serverURL + "/openApi/testMerchantNotify");
        params.put("returnUrl", "");
        params.put("clientIp", "58.249.126.142");
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("sign", ApiSignUtil.buildSignByMd5(params, merchantSecret));

        String jsonStr = JsonUtil.parseToJsonStr(params);
        String result = HttpClientUtil.doPost(serverURL + "/api/unifiedCreateOrder", jsonStr);

        System.out.println(" ---> " + result);

//        }

    }

    /**
     * 测试获取支付链接
     *
     * @throws Exception
     */
    @Test
    public void testGetPayLink() throws Exception {

        String tradeId = "7be6d47f-05fa-4dd4-8bad-46cfd0a80576";

        Map<String, String> params = new HashMap();
        params.put("tradeId", tradeId);
        params.put("merchantNo", merchantNo);
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("sign", ApiSignUtil.buildSignByMd5(params, merchantSecret));

        String jsonStr = JsonUtil.parseToJsonStr(params);
        String result = HttpClientUtil.doPost(serverURL + "/api/getPayLink", jsonStr);

        System.out.println(" ---> " + result);

    }

    /**
     * 测试柳行app回调
     *
     * @throws Exception
     */
    @Test
    public void testPayNotifyByLzyh() throws Exception {

        String codeNum = "LZYH00004";
        String appSecret = "aaxx0004";
        String amount = "199.99";

        Map<String, String> params = new HashMap();
        params.put("codeNum", codeNum);
        params.put("amount", amount);
        params.put("payTime", DateUtil.getCurrentDateTime());
        params.put("payCode", "LZYH" + OrderNoUtil.getOrderNoByUUId());

        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("sign", AppSignUtil.buildAppSign(params, appSecret).trim());

        String jsonStr = JsonUtil.parseToJsonStr(params);
        String result = HttpClientUtil.doPost(serverURL + "/app/payNotifyByLzyh", jsonStr);
        System.out.println(" ---> " + result);

    }


    /**
     * 测试支付回调
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/openApi/testMerchantNotify", method = {RequestMethod.POST, RequestMethod.GET})
    public void testMerchantNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String text = IOUtils.toString(request.getInputStream(), "utf-8");
        System.out.println(" 回调报文 ---> " + text);

        String params = AESUtil.decrypt(text, notifySecret);
        System.out.println(" 解密后 ---> " + params);

        //业务操作...

        response.getWriter().write("SUCCESS");          //需返回SUCCESS给平台
        response.getWriter().flush();

    }

    /**
     * 调用柳州银行api查询订单
     *
     * @throws Exception
     */
    @Test
    public void testQueryOrder() throws Exception {

        String url = "https://mpay.bolz.cn/napApi/cpOrder/list?endDate=2019-06-01&txnSta=02&mchtNO=49561445251A001&startDate=2019-05-15&pageSize=10&pageNum=1";

        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0OTU2MTQ0NTI1MUEwMDEiLCJzY29wZXMiOlsiMDAwMDAwMDAwMSJdLCJpc3MiOiJTRVJWSUNFRkxBRk9STSIsImlhdCI6MTU1OTM1NDcwMCwiZXhwIjoxNTU5MzU1NjAwfQ.M9ap4q_t1dta734J4fQLBG_SUL49hjF3A3miDVt2W5RBg0v9sNYKALiVotV99mOXOyp5PHPuhyvBhR3T0rcqZw";

        HttpClient httpClient = new DefaultHttpClient();

        // 设置响应头信息
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-type", "application/json; charset=utf-8");
        httpGet.setHeader("Accept", "application/json");
        httpGet.addHeader("Connection", "close");
        httpGet.addHeader("X-Authorization", token);
        httpGet.getParams().setParameter("http.socket.timeout", 10000);

        try {

            HttpResponse response = httpClient.execute(httpGet);

            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                System.out.println(" ---> " + getJsonStrFromHttpResponse(response));
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            httpGet.releaseConnection();
            httpGet.abort();
        }

    }

    @Test
    public void testDateFormat() {

        Random rand = new Random();
        int amount = rand.nextInt(200);

        System.out.println(" ---> " + String.valueOf(rand.nextInt(200)));

//        String dateStr = "20190528221908";
//
//        try {
//            //先转出Date
//            SimpleDateFormat d1 = new SimpleDateFormat("yyyyMMddHHmmss");
//            Date date = d1.parse(dateStr);
//
//            //再转成String
//            SimpleDateFormat d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            System.out.println(d2.format(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

    }

}