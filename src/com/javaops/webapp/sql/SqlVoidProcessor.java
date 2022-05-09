package com.javaops.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlVoidProcessor {
    void makeQuery(PreparedStatement ps) throws SQLException;
}
