package com.drools.core.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class ThreadPoolExecutorUtil {


    public static ThreadPoolExecutor start = new ThreadPoolExecutor(
            1,
            3,
            10,
            TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new DefaultThreadFactory("start"),
            new ThreadPoolExecutor.AbortPolicy() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                    throw new RejectedExecutionException("start线程异常：" + e.toString());
                }
            }
    );


    /**
     * The default thread factory
     */
    static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory(String name) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = name + "-pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }

    }

}
