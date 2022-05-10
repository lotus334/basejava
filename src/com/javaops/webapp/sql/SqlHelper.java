package com.javaops.webapp.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class SqlHelper {


    private static final Logger LOG = Logger.getLogger(SqlHelper.class.getName());


    private ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> void executeQuery(String request)  {
        executeQuery(request, PreparedStatement::execute, null);
    }

    public <T> T executeQuery(String request, SqlProcessor<T> sqlProcessor)  {
        return executeQuery(request, sqlProcessor, null);
    }

    public <T> T executeQuery(String request, SqlProcessor<T> sqlProcessor, String uuid)  {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(request)) {
            return sqlProcessor.executeQuery(ps);
        } catch (SQLException e) {
            throw SqlExceptionUtil.convertException(e, uuid);
        }
    }
}