package com.skronawi.keyvalueservice.it;

import com.skronawi.keyvalueservice.core.keystores.InMemoryKeyValueStore;

public class InMemoryKeyValueStoreReInitTest extends KeyValueStoreReInitializeTest{

    @Override
    protected void teardown() {

    }

    @Override
    protected void create() {
        keyValueStore = new InMemoryKeyValueStore();
    }
}
