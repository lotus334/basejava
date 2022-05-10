package com.javaops.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlProcessor<T> {
    T executeQuery(PreparedStatement ps) throws SQLException;
}
