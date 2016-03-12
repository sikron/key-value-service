package com.skronawi.keyvalueservice.it;

import com.skronawi.keyvalueservice.api.Credentials;
import com.skronawi.keyvalueservice.api.KeyValueServiceConfiguration;
import com.skronawi.keyvalueservice.api.KeyValueStoreConfiguration;
import com.skronawi.keyvalueservice.core.keystores.DatabaseKeyValueStore;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseKeyValueStoreTest extends KeyValueStoreTest {

    public static final String DRIVER_CLASS_NAME = "org.hsqldb.jdbcDriver";
    public static final String URL = "jdbc:hsqldb:mem:test";
    public static final String USER = "sa";
    public static final String PASSWORD = "";
    public static final String TABLE = "key_values";

//    public static final String URL = "jdbc:postgresql://localhost:5432/test";
//    public static final String USER = "postgres";
//    public static final String PASSWORD = "postgres";
//    public static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";

    @BeforeClass
    public void setup() throws Exception {

        createTable();

        keyValueStore = new DatabaseKeyValueStore(keyValueStoreConfig());
        keyValueStore.init();
    }

    private void createTable() throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement stmt = connection.createStatement();
        stmt.execute(IOUtils.toString(getClass().getResourceAsStream("/create-db.sql")));
        if (!connection.getAutoCommit()) {
            connection.commit();
        }
        connection.close();
    }

    private KeyValueStoreConfiguration keyValueStoreConfig() {
        KeyValueStoreConfiguration kvsc = new KeyValueStoreConfiguration();
        kvsc.setCredentials(new Credentials(USER, PASSWORD));
        kvsc.setKeyValueStoreType(KeyValueServiceConfiguration.KeyValueStoreType.DB);
        kvsc.setDriverClassName(DRIVER_CLASS_NAME);
        kvsc.setUrl(URL);
        kvsc.setName(TABLE);
        return kvsc;
    }
}
