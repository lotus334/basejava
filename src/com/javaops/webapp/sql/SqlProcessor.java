package com.javaops.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlProcessor<T> {
    T makeQuery(PreparedStatement ps) throws SQLException;
}
