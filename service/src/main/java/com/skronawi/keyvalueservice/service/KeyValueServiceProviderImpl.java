package com.skronawi.keyvalueservice.service;

import com.skronawi.keyvalueservice.api.AlreadyInitializedException;
import com.skronawi.keyvalueservice.api.KeyValueService;
import com.skronawi.keyvalueservice.api.NotInitializedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.atomic.AtomicBoolean;

public class KeyValueServiceProviderImpl implements KeyValueServiceProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyValueServiceProviderImpl.class);

    private AnnotationConfigApplicationContext annotationConfigApplicationContext;
    private AtomicBoolean isInitialized = new AtomicBoolean(false);
    private AtomicBoolean hasBeenInitializedOnce = new AtomicBoolean(false);

    @Override
    public void init() throws AlreadyInitializedException {
        if (hasBeenInitializedOnce.getAndSet(true)) {
            throw new AlreadyInitializedException();
        }
        LOGGER.debug("init");
        annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(KeyValueServiceProvisioning.class);
        isInitialized.set(true);
    }

    @Override
    public void teardown() {
        isInitialized.set(false);
        LOGGER.debug("teardown");
        if (annotationConfigApplicationContext == null) {
            return;
        }
        annotationConfigApplicationContext.getBean(KeyValueService.class).getLifeCycle().teardown();
    }

    @Override
    public KeyValueService get() throws NotInitializedException {
        assertInitialized();
        return annotationConfigApplicationContext.getBean(KeyValueService.class);
    }

    private void assertInitialized() {
        if (!isInitialized.get()) {
            throw new NotInitializedException();
        }
    }
}
