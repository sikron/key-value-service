package com.skronawi.keyvalueservice.it;

import com.skronawi.keyvalueservice.core.keystores.InMemoryKeyValueStore;
import org.testng.annotations.BeforeClass;

public class InMemoryKeyValueStoreTest extends KeyValueStoreTest {

    @BeforeClass
    public void setup() throws Exception{
        keyValueStore = new InMemoryKeyValueStore();
        keyValueStore.init();
    }
}
