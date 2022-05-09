package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.sql.ConnectionFactory;
import com.javaops.webapp.sql.SqlHelper;
import com.javaops.webapp.sql.SqlProcessor;
import com.javaops.webapp.sql.SqlVoidProcessor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        String query = "DELETE FROM resume";
        SqlVoidProcessor sqlProcessor = ps -> {
            ps.execute();
        };
        SqlHelper.makeVoidQuery(connectionFactory, query, sqlProcessor);
    }

    @Override
    public Resume get(String uuid) {
        String query = "SELECT * FROM resume r WHERE r.uuid =?";
        SqlProcessor<Resume> sqlProcessor = ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(rs.getString("full_name"), uuid);
        };
        return SqlHelper.makeQuery(connectionFactory, query, sqlProcessor);
    }

    @Override
    public void update(Resume r) {
        String query = "UPDATE resume r SET full_name = ? WHERE uuid = ?";
        SqlVoidProcessor sqlProcessor = ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
        };
        SqlHelper.makeVoidQuery(connectionFactory, query, sqlProcessor);
    }

    @Override
    public void save(Resume r) {
        String query = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        SqlProcessor<Integer> sqlProcessor = ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            return 0;
        };

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
        } catch (SQLException e) {
            if (get(r.getUuid()) != null) {
                throw new ExistStorageException(r.getUuid());
            }
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        String query = "DELETE FROM resume r WHERE r.uuid =?";
        SqlVoidProcessor sqlProcessor = ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        };
        SqlHelper.makeVoidQuery(connectionFactory, query, sqlProcessor);
    }

    @Override
    public List<Resume> getAllSorted() {
        String query = "SELECT * FROM resume r";
        SqlProcessor<List<Resume>> sqlProcessor = ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                String fullName = rs.getString("full_name");
                String uuid = rs.getString("uuid").replaceAll("\\s+","");
                resumes.add(new Resume(fullName, uuid));
            }
            resumes.sort((o1, o2) -> o2.getUuid().compareTo(o1.getUuid()));
            return resumes;
        };
        return SqlHelper.makeQuery(connectionFactory, query, sqlProcessor);
    }

    @Override
    public int size() {
        String query = "SELECT count(*) FROM resume";
        SqlProcessor<Integer> sqlProcessor = ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        };
        return SqlHelper.makeQuery(connectionFactory, query, sqlProcessor);
    }
}
