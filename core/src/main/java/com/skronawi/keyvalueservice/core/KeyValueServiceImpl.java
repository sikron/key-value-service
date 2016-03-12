package com.skronawi.keyvalueservice.core;

import com.skronawi.keyvalueservice.api.KeyValueService;
import com.skronawi.keyvalueservice.api.KeyValueStore;
import com.skronawi.keyvalueservice.api.LifeCycle;
import com.skronawi.keyvalueservice.api.NotInitializedException;

public class KeyValueServiceImpl implements KeyValueService {

    private final LifeCycleImpl lifeCycle;

    public KeyValueServiceImpl() {
        lifeCycle = new LifeCycleImpl();
    }

    @Override
    public LifeCycle getLifeCycle() {
        return lifeCycle;
    }

    @Override
    public KeyValueStore getKeyValueStore() throws NotInitializedException {
        if (!lifeCycle.isInitialized()) {
            throw new NotInitializedException();
        }
        return lifeCycle.getKeyValueStore();
    }
}
