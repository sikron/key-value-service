package com.skronawi.keyvalueservice.core;

import com.skronawi.keyvalueservice.api.NotInitializedException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class KeyValueServiceTest {

    @Test
    public void test() throws Exception {
        KeyValueServiceImpl keyValueService = new KeyValueServiceImpl();
        Assert.assertNotNull(keyValueService.getLifeCycle());
        try {
            keyValueService.getKeyValueStore();
            Assert.fail();
        } catch (NotInitializedException e) {

        }
    }
}
