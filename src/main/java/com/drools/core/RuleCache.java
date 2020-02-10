package com.drools.core;

/**
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 * @date 2019/9/28
 * @since 1.0.0
 */
public class RuleCache implements Runnable{

    private KieTemplate kieTemplate;

    public RuleCache(KieTemplate kieTemplate) {
        this.kieTemplate = kieTemplate;
    }

    @Override
    public void run() {
        kieTemplate.doRead0();
    }
}
