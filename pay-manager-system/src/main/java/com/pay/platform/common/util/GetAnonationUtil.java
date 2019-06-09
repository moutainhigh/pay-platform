package com.pay.platform.common.util;

import com.pay.platform.security.CommonRequest;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * User:
 * DateTime: 2017/1/6 23:05
 * <p>
 * 获取注解的工具类
 */
public class GetAnonationUtil {

    private static List<String> commonRequestUrl = new ArrayList<>();

    static {
        getCommonRequetsUrl("com.pay.platform");
    }

    /**
     * 获取标识了@CommonRequest的requestUrl,代表无需进行权限过滤的模块
     *
     * @param packageName
     * @return
     */
    public static void getCommonRequetsUrl(String packageName) {

        List<Class<?>> classes = new ArrayList<Class<?>>();

        //是否循环迭代
        boolean recursive = true;

        //获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');

        //定义一个枚举的集合 并进行循环来处理这个目录下的文件
        Enumeration<URL> dirs;
        try {

            //读取指定package下的所有class
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();

                //得到协议的名称
                String protocol = url.getProtocol();

                //判断是否以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    //获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    //以文件的方式扫描整个包下的文件 并添加到集合中
                    findControllerClassesByPackage(packageName, filePath, recursive, classes);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Class<?> clazz : classes) {

            //获取类上定义的@CommonRequest/RequestMapping注解
            String clazzRequestUrl = "";
            CommonRequest classCommonRequest = clazz.getAnnotation(CommonRequest.class);
            RequestMapping classRequestMapping = clazz.getAnnotation(RequestMapping.class);
            if (classRequestMapping != null && classRequestMapping.value() != null) {
                clazzRequestUrl = classRequestMapping.value()[0];
            }

            //遍历类的所有方法,并获取方法上的@CommonRequest/RequestMapping注解
            for (Method method : clazz.getMethods()) {

                CommonRequest commonRequest = method.getAnnotation(CommonRequest.class);
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

                //如果类上也定义了@CommonRequest注解,则代表整个都是通用模块
                if (classCommonRequest != null && requestMapping != null) {
                    String[] valueArr = requestMapping.value();
                    for (String value : valueArr) {
                        commonRequestUrl.add(clazzRequestUrl + value);
                    }
                }
                //通用模块
               else if (commonRequest != null && requestMapping != null) {
                    String[] valueArr = requestMapping.value();
                    for (String value : valueArr) {
                        commonRequestUrl.add(clazzRequestUrl + value);
                    }
                }

            }

        }

    }

    /**
     * 递归扫描包下的Controller类,并将Class加入到List中
     *
     * @param packageName: 扫描包名
     * @param packagePath: 扫描包路径
     * @param recursive:
     * @param classes:     将扫描到的类加到此集合中
     */
    public static void findControllerClassesByPackage(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {

        //获取此包的目录 建立一个File
        File dir = new File(packagePath);

        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }

        //如果存在 就获取包下的所有文件 包括目录
        //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
        File[] dirfiles = dir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });

        //循环所有文件
        for (File file : dirfiles) {

            //如果是目录 则继续扫描
            if (file.isDirectory()) {
                findControllerClassesByPackage(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {

                //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                if (className.contains("Controller") || className.contains("controller")) {

                    try {
                        Class clazz = Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className);
                        classes.add(clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }

            }

        }

    }

    public static List<String> getCommonRequestUrl() {
        return commonRequestUrl;
    }

}