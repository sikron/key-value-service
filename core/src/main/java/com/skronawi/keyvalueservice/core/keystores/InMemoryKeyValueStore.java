package com.skronawi.keyvalueservice.core.keystores;

import com.skronawi.keyvalueservice.api.KeyNotExistingException;
import com.skronawi.keyvalueservice.api.NotInitializedException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InMemoryKeyValueStore extends AbstractKeyValueStore {

    private Map<String, String> keyValues;

    public InMemoryKeyValueStore() {
        keyValues = new HashMap<>();
    }

    @Override
    public String get(String key) throws KeyNotExistingException, NotInitializedException {
        assertInitialized();
        String value = keyValues.get(key);
        if (null == value) {
            throw new KeyNotExistingException(key);
        }
        return value;
    }

    @Override
    public Map<String, String> getAll() throws NotInitializedException {
        assertInitialized();
        return new HashMap<>(keyValues);
    }

    @Override
    public Map<String, String> getByPrefix(String keyPrefix) throws NotInitializedException {
        assertInitialized();
        HashMap<String, String> prefixedKeyValues = new HashMap<>();
        keyValues.entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith(keyPrefix))
                .forEach(entry -> prefixedKeyValues.put(entry.getKey(), entry.getValue()));
        return prefixedKeyValues;
    }

    @Override
    public void set(String key, String value) throws NotInitializedException {
        assertInitialized();
        keyValues.put(key, value);
    }

    @Override
    public void setAll(Map<String, String> keyValues) throws NotInitializedException {
        assertInitialized();
        this.keyValues.putAll(keyValues);
    }

    @Override
    public void delete(String key) throws NotInitializedException {
        assertInitialized();
        keyValues.remove(key);
    }

    @Override
    public void deleteByPrefix(String keyPrefix) throws NotInitializedException {
        assertInitialized();
        Set<String> keysToRemove = new HashSet<>();
        keyValues.entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith(keyPrefix))
                .forEach(entry -> keysToRemove.add(entry.getKey()));
        for (String keyToRemove : keysToRemove) {
            keyValues.remove(keyToRemove);
        }
    }

    @Override
    public void deleteAll() throws NotInitializedException {
        assertInitialized();
        keyValues = new HashMap<>();
    }

    @Override
    public Set<String> getKeys() throws NotInitializedException {
        assertInitialized();
        return new HashSet<>(keyValues.keySet());
    }
}
