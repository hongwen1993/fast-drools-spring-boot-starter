package com.drools.core.listener;

import org.kie.api.event.process.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 * @date 2020/9/9
 * @since 7.37.0.Final
 */
public class DefaultProcessEventListener implements ProcessEventListener {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void beforeProcessStarted(ProcessStartedEvent event) {
        System.out.println("beforeProcessStarted()");
    }

    @Override
    public void afterProcessStarted(ProcessStartedEvent event) {
        System.out.println("afterProcessStarted()");
    }

    @Override
    public void beforeProcessCompleted(ProcessCompletedEvent event) {
        System.out.println("beforeProcessCompleted()");
    }

    @Override
    public void afterProcessCompleted(ProcessCompletedEvent event) {
        System.out.println("afterProcessCompleted()");
    }

    @Override
    public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
        System.out.println("beforeNodeTriggered()");
    }

    @Override
    public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {
        System.out.println("afterNodeTriggered()");
    }

    @Override
    public void beforeNodeLeft(ProcessNodeLeftEvent event) {
        System.out.println("beforeNodeLeft()");
    }

    @Override
    public void afterNodeLeft(ProcessNodeLeftEvent event) {
        System.out.println("afterNodeLeft()");
    }

    @Override
    public void beforeVariableChanged(ProcessVariableChangedEvent event) {
        System.out.println("beforeVariableChanged()");
    }

    @Override
    public void afterVariableChanged(ProcessVariableChangedEvent event) {
        System.out.println("afterVariableChanged()");
    }
}
