package com.skronawi.keyvalueservice.core;

import com.skronawi.keyvalueservice.api.DefaultKeyValueServiceConfiguration;
import com.skronawi.keyvalueservice.api.KeyValueServiceConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DefaultConfigTest {

    @Test
    public void test() {

        KeyValueServiceImpl keyValueService = new KeyValueServiceImpl();
        keyValueService.getLifeCycle().init(new DefaultKeyValueServiceConfiguration());

        KeyValueServiceConfiguration serviceConfiguration = keyValueService.getLifeCycle().getServiceConfiguration();
        Assert.assertEquals(serviceConfiguration.getKeyValueStoreConfiguration().getKeyValueStoreType(),
                KeyValueServiceConfiguration.KeyValueStoreType.IN_MEMORY);
    }
}
