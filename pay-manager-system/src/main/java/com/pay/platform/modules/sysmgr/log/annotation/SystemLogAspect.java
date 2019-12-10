package com.pay.platform.modules.sysmgr.log.annotation;

import com.pay.platform.common.context.AppContext;
import com.pay.platform.common.util.IpUtil;
import com.pay.platform.modules.sysmgr.log.model.OperationLogModel;
import com.pay.platform.modules.sysmgr.log.service.OperationLogService;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * User:
 * DateTime: 2016/10/22 16:20
 *
 * 通过aop进行拦截, 定义切点 (即指定要拦截的类,方法,以及拦截操作)
 */
@Aspect
@Component
public class SystemLogAspect {

    @Autowired
    private OperationLogService operationLogService;

    //本地异常日志记录对象
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect. class);

    /**
     * Controller层切入点 - 指定拦截类,方法
     */
    @Pointcut("@annotation(com.pay.platform.modules.sysmgr.log.annotation.SystemControllerLog)")
    public void controllerAspect() {

    }

    /**
     * 前置通知 - 用于拦截Controller层记录用户的操作
     * @param joinPoint
     */
    @AfterReturning("controllerAspect()")
    public void doControllerAfterReturning(JoinPoint joinPoint) {

        try {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            OperationLogModel operationLogModel = new OperationLogModel();

            UserModel user = AppContext.getCurrentUser();
            if(user != null){
                operationLogModel.setUserId(user.getId());
                operationLogModel.setUserName(user.getNickname());
            }else{
                operationLogModel.setUserId("000000000000000000000000000000000000");
                operationLogModel.setUserName("匿名用户");
            }


//            //类名
//            String className = joinPoint.getTarget().getClass().getName();
//            //请求方法
//            String method =  joinPoint.getSignature().getName() + "()";
//            //方法参数
//            String methodParam = JSON.toJSONString(joinPoint.getArgs());
//            //方法描述
//            String methodDescription = getControllerMethodDescription(joinPoint);

            operationLogModel.setIp(IpUtil.getIpAddress(request));
            operationLogModel.setStatus(1);
            operationLogModel.setModule(getControllerMethodModule(joinPoint));
            operationLogModel.setOperation(getControllerMethodOperation(joinPoint));
            operationLogModel.setDescription(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()");     //描述信息为调用方法名

            operationLogService.addOperationLog(operationLogModel);         //保存到数据库

        }
        catch (Exception e){
            logger.error("异常信息:{}", e.getMessage());
        }

    }

    /**
     * 异常通知 - 用于拦截Controller层记录用户的操作, 并记录错误信息
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
    public void doControllerAfterThrowing(JoinPoint joinPoint, Throwable e) {

        System.out.println(" doControllerAfterThrowing ");

        try {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            OperationLogModel operationLogModel = new OperationLogModel();

            UserModel user = AppContext.getCurrentUser();
            if(user != null){
                operationLogModel.setUserId(user.getId());
                operationLogModel.setUserName(user.getNickname());
            }else{
                operationLogModel.setUserId("000000000000000000000000000000000000");
                operationLogModel.setUserName("匿名用户");
            }

            operationLogModel.setIp(IpUtil.getIpAddress(request));
            operationLogModel.setModule(getControllerMethodModule(joinPoint));
            operationLogModel.setOperation(getControllerMethodOperation(joinPoint));

            StringBuilder errorInfo = new StringBuilder();
            errorInfo.append("调用方法: " + joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "() \n");
            errorInfo.append("异常类型：" + e.getClass().getName() + "\n");
            errorInfo.append("异常描述："+e.getMessage());

            operationLogModel.setDescription(errorInfo.toString());
            operationLogModel.setStatus(0);

            operationLogService.addOperationLog(operationLogModel);         //保存到数据库

        }
        catch (Exception e2){
            logger.error("异常信息:{}", e2.getMessage());
        }

    }

    /**
     * 获取注解上定义的属性 - module
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private static String getControllerMethodModule(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String module = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    module = method.getAnnotation(SystemControllerLog.class).module();
                    break;
                }
            }
        }
        return module;
    }

    /**
     * 获取注解上定义的属性 - operation
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private static String getControllerMethodOperation(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String module = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    module = method.getAnnotation(SystemControllerLog.class).operation();
                    break;
                }
            }
        }
        return module;
    }

}