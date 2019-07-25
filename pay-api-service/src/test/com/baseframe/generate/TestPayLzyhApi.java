package com.baseframe.generate;

import com.pay.platform.api.pay.lzyh.util.LzyhUtil;
import com.pay.platform.common.util.*;
import com.pay.platform.security.util.ApiSignUtil;
import com.pay.platform.security.util.AppSignUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;

/**
 * User:
 * DateTime: 2019/3/3 11:33
 */
public class TestPayLzyhApi {

    String merchantNo = "10931018752";                                  //商家编号
    String merchantSecret = "617641b2e9b146dc80ad8c1fcbb758ac";         //商家密钥：调接口签名用
    String notifySecret = "b42d2abaad7cdb72";                           //回调密钥：接收支付回调,解密数据用

//    String serverURL = "http://www.b9y37.cn:20021";                //生产环境请求地址

    String serverURL = "http://localhost:8080";        //系统请求地址

    /**
     * 测试统一下单
     *
     * @throws Exception
     */
    @Test
    public void unifiedCreateOrder() throws Exception {

        for (int i = 0; i < 40; i++) {

            Thread.sleep(1000);

            Map<String, String> params = new HashMap();
            params.put("merchantNo", merchantNo);
            params.put("merchantOrderNo", System.currentTimeMillis() + "");
            params.put("orderAmount", "800");
            params.put("payWay", "lzyhWechat");
            params.put("notifyUrl", serverURL + "/openApi/testMerchantNotify");
            params.put("returnUrl", "");
            params.put("clientIp", "58.249.126.142");
            params.put("timestamp", System.currentTimeMillis() + "");
            params.put("sign", ApiSignUtil.buildSignByMd5(params, merchantSecret));

            String jsonStr = JsonUtil.parseToJsonStr(params);
            String result = HttpClientUtil.doPost(serverURL + "/api/unifiedCreateOrder", jsonStr);

            System.out.println(" ---> " + result);

        }

    }

    /**
     * 测试获取支付链接
     *
     * @throws Exception
     */
    @Test
    public void testGetPayLink() throws Exception {

        String tradeId = "6daa865f-b8e6-4504-a66c-ebf6277bb8a9";

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
     * 测试查询订单
     *
     * @throws Exception
     */
    @Test
    public void testFindOrder() throws Exception {

        Map<String, String> params = new HashMap();
        params.put("merchantNo", merchantNo);
        params.put("merchantOrderNo", "test2019061021574201685191136");
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("sign", ApiSignUtil.buildSignByMd5(params, merchantSecret));

        String jsonStr = JsonUtil.parseToJsonStr(params);
        String result = HttpClientUtil.doPost(serverURL + "/api/findOrder", jsonStr);

        System.out.println(" ---> " + result);


    }

    /**
     * 测试柳行app回调
     *
     * @throws Exception
     */
    @Test
    public void testPayNotifyByLzyh() throws Exception {

        String codeNum = "lzyh00001";
        String appSecret = "zzzz0001";
        String amount = "100";

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

    /**
     * 将httpResponse解析成json字符串
     *
     * @param response
     * @return
     */
    public static String getJsonStrFromHttpResponse(HttpResponse response) throws Exception {

        InputStream in = response.getEntity().getContent();

        StringBuilder resultJson = new StringBuilder();
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            resultJson.append(new String(buf, 0, len));
        }
        return resultJson.toString();

    }


    @Test
    public void testDecimalFloat() {

        double payFloatAmount = DecimalCalculateUtil.sub(300, 0.01);

        List<Double> usedPayFloatAmount = new ArrayList<>();
        double d = 300;
        for (int i = 0; i < 50; i++) {
            d = DecimalCalculateUtil.sub(d, 0.01);
            usedPayFloatAmount.add(d);
        }
        System.out.println(JsonUtil.parseToJsonStr(usedPayFloatAmount));

        long beginTime = System.currentTimeMillis();
        if (usedPayFloatAmount != null && usedPayFloatAmount.size() > 0) {
            for (int i = 0; i < 50; i++) {

                //判断在过去1小时内是否使用过该金额,如果使用过,则继续向下浮动
                if (!usedPayFloatAmount.contains(payFloatAmount)) {
                    break;
                }

                //每次向下浮动1分钱;
                payFloatAmount = DecimalCalculateUtil.sub(payFloatAmount, 0.01);

//                //判断在过去1小时内是否使用过该金额,如果使用过,则继续向下浮动
//                boolean isUse = false;
//                for (Double amount : usedPayFloatAmount) {
//                    if (DecimalCalculateUtil.compareTo(amount, payFloatAmount) == 0) {
//                        isUse = true;
//                    }
//                }
//
//                //已使用,则继续向下浮动
//                if (isUse) {
//                    payFloatAmount = DecimalCalculateUtil.sub(payFloatAmount, 0.01);
//                } else {
//                    break;
//                }

            }
        }
        long endTime = System.currentTimeMillis();

        System.out.println("payFloatAmount:" + payFloatAmount);
        System.out.println("耗时:" + (endTime - beginTime));

    }


    @Test
    public void testDoubleEquals() {

        Double d1 = new Double(123.50);

        Double d2 = new Double(123);

        boolean b = d1.equals(d2);
        System.out.println(b);


    }

    @Test
    public void testGetPayFloatAmountList() {

//        List<Double> payFloatAmountList = LzyhUtil.getPayFloatAmountList(300.00);
//
//        List<Double> usedPayFloatAmountList = new ArrayList<>();
//        usedPayFloatAmountList.add(301.00);
//        usedPayFloatAmountList.add(302.00);
//        usedPayFloatAmountList.add(303.00);
//
//        System.out.println(JsonUtil.parseToJsonStr(payFloatAmountList));
//
//        payFloatAmountList.removeAll(usedPayFloatAmountList);
//        System.out.println(JsonUtil.parseToJsonStr(payFloatAmountList));
//
//        int randomIndex = new Random().nextInt(payFloatAmountList.size());
//        System.out.println(" ---> " + payFloatAmountList.get(randomIndex));

        System.out.println(Math.round(Double.parseDouble("300.0000")));          //去除小数点;

    }

}