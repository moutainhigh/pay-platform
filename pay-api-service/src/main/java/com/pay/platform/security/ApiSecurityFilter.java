package com.pay.platform.security;

import com.pay.platform.api.merchant.model.MerchantModel;
import com.pay.platform.api.merchant.service.MerchantService;
import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: zjt
 * DateTime: 2019/1/6 19:47
 * <p>
 * 接口安全过滤器
 */
public class ApiSecurityFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ApiSecurityFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        JSONObject respJson = new JSONObject();

        try {

            //替换默认的HttpServletRequest,getInputStream()只能读取一次,避免在filter总读取后,后续的Controller无法读取到提交的数据
            ApiSecuirtyRequest secuirtyRequest = new ApiSecuirtyRequest(request);

            //获取请求提交数据
            String text = IOUtils.toString(secuirtyRequest.getInputStream(), "utf-8");
            if (StringUtil.isEmpty(text)) {
                respJson.put("code", "0");
                respJson.put("msg", "请求参数为空!");
                writeJson(response, respJson.toString());
                return;
            }
            JSONObject reqJson = new JSONObject(text);
            logger.info("接口请求提交数据：" + text);

            //1,判断是否包含timestamp,sign参数
            if (!reqJson.has("timestamp") || !reqJson.has("sign") || !reqJson.has("merchantNo") ) {
                respJson.put("code", "0");
                respJson.put("msg", "参数错误!");
                writeJson(response, respJson.toString());
                return;
            }

            //2,判断请求时间跟当前时间是否超过最大间隔,防止请求url盗用进行重放攻击 (当前限制为1分钟,即可6万毫秒)
            Long timestamp = reqJson.getLong("timestamp");
            Long currentMills = System.currentTimeMillis();
            if (Math.abs(currentMills - timestamp) > 60000) {
                respJson.put("code", "0");
                respJson.put("msg", "请求已过期!");
                writeJson(response, respJson.toString());
                return;
            }

            //3,对所有请求参数和时间戳进行排序  ->  并“参数=参数值”的模式用“&”字符拼接成字符串 + 加上商家密钥 -> MD5生成sign签名
            String merchantNo = reqJson.getString("merchantNo");
            String merchantSecret = MerchantSecretCacheUtil.getMerchantSecret(merchantNo);
            if(StringUtil.isEmpty(merchantSecret)){
                respJson.put("code", "0");
                respJson.put("msg", "无效的商户编号!");
                writeJson(response, respJson.toString());
                return;
            }

            Map<String, String> params = new HashMap<String, String>();
            String[] names = JSONObject.getNames(reqJson);
            for (String key : names) {
                params.put(key, reqJson.getString(key));
            }

            String sign = reqJson.getString("sign").trim();
            String currentSign = ApiSignUtil.buildSignByMd5(params, merchantSecret).trim();

            if (!sign.equalsIgnoreCase(currentSign)) {
                respJson.put("code", "0");
                respJson.put("msg", "签名错误!");
                writeJson(response, respJson.toString());
                return;
            }

            filterChain.doFilter(secuirtyRequest, response);                   //不进行拦截

        } catch (Exception e) {
            e.printStackTrace();
            respJson.put("code", "0");
            respJson.put("msg", "系统错误!" + e.getMessage());
            writeJson(response, respJson.toString());
        }

    }

    /**
     * 输出响应数据
     *
     * @param response
     * @param json
     * @throws IOException
     */
    private void writeJson(HttpServletResponse response, String json) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(json);
    }

}