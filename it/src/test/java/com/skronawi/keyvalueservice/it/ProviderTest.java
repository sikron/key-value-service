package com.skronawi.keyvalueservice.it;

import com.skronawi.keyvalueservice.api.AlreadyInitializedException;
import com.skronawi.keyvalueservice.api.KeyValueService;
import com.skronawi.keyvalueservice.api.LifeCycle;
import com.skronawi.keyvalueservice.api.NotInitializedException;
import com.skronawi.keyvalueservice.service.KeyValueServiceProvider;
import com.skronawi.keyvalueservice.service.KeyValueServiceProviderImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ProviderTest {

    @Test
    public void testServiceLoader() throws Exception {

        ServiceLoader<KeyValueServiceProvider> serviceLoader = ServiceLoader.load(KeyValueServiceProvider.class);
        Iterator<KeyValueServiceProvider> iterator = serviceLoader.iterator();

        Assert.assertTrue(iterator.hasNext());
        KeyValueServiceProvider keyValueServiceProvider = iterator.next();
        Assert.assertFalse(iterator.hasNext());

        Assert.assertTrue(keyValueServiceProvider.getClass() == KeyValueServiceProviderImpl.class);
    }

    @Test(expectedExceptions = NotInitializedException.class)
    public void testGetWithoutInit() throws Exception {
        KeyValueServiceProvider keyValueServiceProvider = getKeyValueServiceProvider();
        keyValueServiceProvider.get();
    }

    @Test
    public void testInitGetTeardown() throws Exception {

        KeyValueServiceProvider keyValueServiceProvider = getKeyValueServiceProvider();
        keyValueServiceProvider.init();

        KeyValueService keyValueService = keyValueServiceProvider.get();
        Assert.assertNotNull(keyValueService);

        LifeCycle lifeCycle = keyValueService.getLifeCycle();
        Assert.assertNotNull(lifeCycle);
        Assert.assertTrue(lifeCycle.isInitialized());
        Assert.assertNotNull(keyValueService.getKeyValueStore());

        keyValueServiceProvider.teardown();
    }

    @Test
    public void testTeardownWithoutInit() throws Exception {
        KeyValueServiceProvider keyValueServiceProvider = getKeyValueServiceProvider();
        keyValueServiceProvider.teardown();
    }

    @Test
    public void multipleTeardowns() throws Exception{
        KeyValueServiceProvider keyValueServiceProvider = getKeyValueServiceProvider();
        keyValueServiceProvider.teardown();
        keyValueServiceProvider.teardown();
        keyValueServiceProvider.teardown();
    }

    @Test
    public void reInitNotPossible() throws Exception{

        KeyValueServiceProvider keyValueServiceProvider = getKeyValueServiceProvider();
        keyValueServiceProvider.init();

        try {
            keyValueServiceProvider.init();
            Assert.fail();
        } catch (AlreadyInitializedException e) {
            //ok
        }

        keyValueServiceProvider.teardown();

        try {
            keyValueServiceProvider.init();
            Assert.fail();
        } catch (AlreadyInitializedException e) {
            //ok
        }
    }

    private KeyValueServiceProvider getKeyValueServiceProvider() {
        return ServiceLoader.load(KeyValueServiceProvider.class).iterator().next();
    }
}
