package com.skronawi.keyvalueservice.core;

import com.skronawi.keyvalueservice.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public class LifeCycleImpl implements LifeCycle {

    private static final Logger LOGGER = LoggerFactory.getLogger(LifeCycleImpl.class);

    private KeyValueStore keyValueStore;
    private KeyValueServiceConfiguration keyValueServiceConfiguration;
    private AtomicBoolean isInitialized = new AtomicBoolean(false);
    //prevent re-initialization. thus any already provided KeyValueStore instance cannot get in-valid or stale.
    private AtomicBoolean hasBeenInitializedOnce = new AtomicBoolean(false);

    @Override
    public synchronized void init(KeyValueServiceConfiguration keyValueServiceConfiguration)
            throws AlreadyInitializedException {

        if (hasBeenInitializedOnce.getAndSet(true)) {
            throw new AlreadyInitializedException();
        }

        LOGGER.debug("init");
        this.keyValueServiceConfiguration = keyValueServiceConfiguration;
        keyValueStore = KeyValueStoreFactory.from(keyValueServiceConfiguration.getKeyValueStoreConfiguration());
        keyValueStore.init();
        isInitialized.set(true);
    }

    @Override
    public void teardown() {
        isInitialized.set(false);
        LOGGER.debug("teardown");
        if (keyValueStore != null) {
            keyValueStore.teardown();
        }
        keyValueStore = null;
    }

    @Override
    public boolean isInitialized() {
        return isInitialized.get();
    }

    @Override
    public KeyValueServiceConfiguration getServiceConfiguration() throws NotInitializedException {
        assertInitialized();
        return keyValueServiceConfiguration;
    }

    private void assertInitialized() throws NotInitializedException {
        if (!isInitialized()) {
            throw new NotInitializedException();
        }
    }

    public KeyValueStore getKeyValueStore() {
        assertInitialized();
        return keyValueStore;
    }
}
