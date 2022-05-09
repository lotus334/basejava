package com.javaops.webapp.sql;

import com.javaops.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public static <T> T makeQuery(ConnectionFactory connectionFactory, String request, SqlProcessor<T> sqlProcessor)  {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(request)) {
            return sqlProcessor.makeQuery(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public static void makeVoidQuery(ConnectionFactory connectionFactory, String request, SqlVoidProcessor sqlProcessor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(request)) {
            sqlProcessor.makeQuery(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}