package com.pay.platform.modules.generate.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

/**
 * User:
 * DateTime: 2017/1/2 13:47
 * <p>
 * freemarker工具类,用于渲染模板,输出文件
 */
public class FreeMarkerUtil {

    private static final Logger logger = LoggerFactory.getLogger(FreeMarkerUtil.class);

    /**
     * 获取模板 , 路径从/template下开始
     *
     * @param templatePath
     * @return
     */
    public static Template getTemplate(String templatePath) {

        try {
            Configuration cfg = new Configuration();
            cfg.setClassForTemplateLoading(FreeMarkerUtil.class, "/");
            Template template = cfg.getTemplate(templatePath);
            return template;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 输出模板内容
     *
     * @param templatePath
     * @param root
     */
    public static void print(String templatePath, Map<String, Object> root) {

        try {

            //获取模板
            Template template = getTemplate(templatePath);

            //渲染内容,并输出到System.out
            template.process(root, new PrintWriter(System.out));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 将模板渲染后,写入到文件
     *
     * @param templatePath: 模板路径
     * @param root:         渲染所需数据
     * @param outFilePath:  输出文件路径
     */
    public static void writeToFile(String templatePath, Object root, String outFilePath) {

        FileWriter out = null;
        try {

            //1, 获取输出路径
            File outFile = new File(outFilePath);
            if (!outFile.exists()) {
                outFile.getParentFile().mkdirs();
            }
            out = new FileWriter(outFile);

            //2, 加载模板内容
            Template temp = getTemplate(templatePath);

            //3, 渲染模板,并输出
            temp.process(root, out);

            System.out.println("写入成功: " + outFilePath);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}