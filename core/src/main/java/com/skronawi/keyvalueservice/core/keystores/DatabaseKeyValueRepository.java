package com.skronawi.keyvalueservice.core.keystores;

import com.skronawi.keyvalueservice.api.KeyNotExistingException;
import com.skronawi.keyvalueservice.api.KeyValueStoreConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class DatabaseKeyValueRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseKeyValueRepository.class);

    private final KeyValueStoreConfiguration keyValueStoreConfiguration;
    private final String tableName;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public DatabaseKeyValueRepository(KeyValueStoreConfiguration keyValueStoreConfiguration) {
        this.keyValueStoreConfiguration = keyValueStoreConfiguration;
        tableName = keyValueStoreConfiguration.getName();
    }

    public String get(String key) {
        Set<String> values = new HashSet<>();
        jdbcTemplate.query(
                "SELECT the_value FROM " + tableName + " WHERE the_key = ?",
                new Object[]{key},
                (row, rowNum) -> row.getString("the_value")).forEach(values::add);

        if (values.isEmpty()) {
            throw new KeyNotExistingException(key);
        }
        return values.iterator().next();
    }

    public Set<String> getKeys() {
        return getAll().keySet();
    }

    public Map<String, String> getAll() {
        Map<String, String> keyValues = new HashMap<>();
        jdbcTemplate.query("SELECT the_key, the_value FROM " + tableName,
                (row, rowNum) -> keyValues.put(row.getString("the_key"), row.getString("the_value")));
        return keyValues;
    }

    public Map<String, String> getByPrefix(String keyPrefix) {
        Map<String, String> keyValues = new HashMap<>();
        jdbcTemplate.query(
                "SELECT the_key, the_value FROM " + tableName + " WHERE the_key LIKE ?",
                new Object[]{keyPrefix + "%"},
                (row, rowNum) -> keyValues.put(row.getString("the_key"), row.getString("the_value")));
        return keyValues;
    }

    @Transactional
    public void set(String key, String value) {
        //TODO what about upsert?
        try {
            get(key);
        } catch (KeyNotExistingException e) {
            jdbcTemplate.update(
                    "insert into " + tableName + " (the_key, the_value) values (?, ?)",
                    key, value);
            return;
        }
        jdbcTemplate.update(
                "update " + tableName + " set the_value = ? WHERE the_key = ?",
                value, key);
    }

    @Transactional
    public void setAll(Map<String, String> keyValues) {
        //TODO batch update, but what if some keys need update, and some insert?
        for (AbstractMap.Entry<String, String> entry : keyValues.entrySet()) {
            set(entry.getKey(), entry.getValue());
        }
    }

    public void delete(String key) {
        jdbcTemplate.update(
                "delete from " + tableName + " WHERE the_key = ?", key);
    }

    @Transactional
    public void deleteByPrefix(String keyPrefix) {
        jdbcTemplate.update(
                "delete from " + tableName + " WHERE the_key LIKE ?",
                new Object[]{keyPrefix + "%"});
    }

    @Transactional
    public void deleteAll() {
        jdbcTemplate.execute("delete from " + tableName);
    }

    public void teardown() {
        LOGGER.debug("teardown");
    }
}
