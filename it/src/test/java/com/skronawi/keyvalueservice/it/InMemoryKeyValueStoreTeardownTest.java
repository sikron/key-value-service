package com.skronawi.keyvalueservice.it;

import com.skronawi.keyvalueservice.core.keystores.InMemoryKeyValueStore;

public class InMemoryKeyValueStoreTeardownTest extends KeyValueStoreTeardownTest{

    @Override
    protected void initKeystore() throws Exception{
        keyValueStore = new InMemoryKeyValueStore();
        keyValueStore.init();
    }
}
