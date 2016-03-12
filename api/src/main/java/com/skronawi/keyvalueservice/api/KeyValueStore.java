package com.skronawi.keyvalueservice.api;

import java.util.Map;
import java.util.Set;

public interface KeyValueStore {

    String get(String key) throws KeyNotExistingException, NotInitializedException;

    Set<String> getKeys() throws NotInitializedException;

    Map<String, String> getAll() throws NotInitializedException;

    Map<String, String> getByPrefix(String keyPrefix) throws NotInitializedException;

    void set(String key, String value) throws NotInitializedException;

    void setAll(Map<String, String> keyValues) throws NotInitializedException;

    void delete(String key) throws NotInitializedException;

    void deleteByPrefix(String keyPrefix) throws NotInitializedException;

    void deleteAll() throws NotInitializedException;

    void init() throws AlreadyInitializedException;

    void teardown();
}
