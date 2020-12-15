package org.example.util;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

public class DBUtilTest {
    @Test
    public void testConnection() {
        Connection connection = DBUtil.getConnection();
        System.out.println(connection);
        Assert.assertNotNull(connection);
    }
}
