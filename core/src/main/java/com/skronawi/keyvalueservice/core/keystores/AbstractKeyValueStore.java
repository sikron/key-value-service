package com.skronawi.keyvalueservice.core.keystores;

import com.skronawi.keyvalueservice.api.AlreadyInitializedException;
import com.skronawi.keyvalueservice.api.KeyValueStore;
import com.skronawi.keyvalueservice.api.NotInitializedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractKeyValueStore implements KeyValueStore {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private AtomicBoolean isInitialized = new AtomicBoolean(false);
    //prevent re-initialization
    private AtomicBoolean hasBeenInitialized = new AtomicBoolean(false);

    @Override
    public void init() throws AlreadyInitializedException {
        if (hasBeenInitialized.getAndSet(true)) {
            throw new AlreadyInitializedException();
        }
        LOGGER.debug("init");
        isInitialized.set(true);
    }

    @Override
    public void teardown() {
        LOGGER.debug("teardown");
        isInitialized.set(false);
    }

    protected void assertInitialized() {
        if (!isInitialized.get()) {
            throw new NotInitializedException();
        }
    }
}
