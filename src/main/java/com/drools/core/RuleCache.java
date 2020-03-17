package com.drools.core;

import com.drools.core.process.AutoProcess;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 * @date 2019/9/28
 * @since 1.0.0
 */
public class RuleCache implements Runnable{

    private KieTemplate kieTemplate;

    private ApplicationContext applicationContext;

    public RuleCache(KieTemplate kieTemplate, ApplicationContext applicationContext) {
        this.kieTemplate = kieTemplate;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run() {
        System.out.println("run");
        boolean tf = applicationContext.containsBean("redisAutoProcess");
        System.out.println("tf : " + tf);
        if (tf) {
            AutoProcess autoProcess = applicationContext.getBean("redisAutoProcess", AutoProcess.class);
            autoProcess.show();
            System.out.println("加锁成功");
        }
        //springInvokeMethod(redisProcess, );

        kieTemplate.doRead0();

    }

    /**
     * @param serviceName
     *            服务名称
     * @param methodName
     *            方法名称
     * @param params
     *            参数
     * @return
     * @throws Exception
     */
    public Object springInvokeMethod(String serviceName, String methodName, Object[] params) throws Exception {
        Object service = applicationContext.getBean(serviceName);
        Class<? extends Object>[] paramClass = null;
        if (params != null) {
            int paramsLength = params.length;
            paramClass = new Class[paramsLength];
            for (int i = 0; i < paramsLength; i++) {
                paramClass[i] = params[i].getClass();
            }
        }
        // 找到方法
        Method method = ReflectionUtils.findMethod(service.getClass(), methodName, paramClass);
        // 执行方法
        return ReflectionUtils.invokeMethod(method, service, params);
    }
}
