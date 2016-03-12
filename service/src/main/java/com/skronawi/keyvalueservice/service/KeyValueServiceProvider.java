package com.skronawi.keyvalueservice.service;

import com.skronawi.keyvalueservice.api.AlreadyInitializedException;
import com.skronawi.keyvalueservice.api.KeyValueService;
import com.skronawi.keyvalueservice.api.NotInitializedException;

public interface KeyValueServiceProvider {

    void init() throws AlreadyInitializedException;

    void teardown();

    KeyValueService get() throws NotInitializedException;
}
