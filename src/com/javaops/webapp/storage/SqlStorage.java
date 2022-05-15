package com.javaops.webapp.storage;

import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.ContactTypes;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SqlStorage implements Storage {

    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());
    protected static final Comparator<Resume> RESUME_COMPARATOR = ((Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid)));

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.executeQuery("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        sqlHelper.transactionalExecute(conn -> {
            deleteResume(conn, resume.getUuid());
            insertResume(conn, resume);
            insertContact(conn, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        sqlHelper.transactionalExecute(conn -> {
                    insertResume(conn, resume);
                    insertContact(conn, resume);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.transactionalExecute(conn -> {
            deleteResume(conn, uuid);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.executeQuery(
                "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return getResumesFromResultSet(rs).get(0);

                });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        return getResumesFromPreparedStatement();
    }

    @Override
    public int size() {
        return sqlHelper.executeQuery("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }

    private List<Resume> getResumesFromResultSet(ResultSet rs) throws SQLException {
        List<Resume> resumes = new ArrayList<>();
        Resume resume = null;
        do {
            String uuid = rs.getString("uuid");
            if (resume == null || !resume.getUuid().equals(uuid)) {
                String fullName = rs.getString("full_name");
                resume = new Resume(fullName, uuid);
                resumes.add(resume);
            }
            String contactType = rs.getString("type");
            if (contactType != null) {
                String value = rs.getString("value");
                ContactTypes type = ContactTypes.valueOf(contactType);
                resume.setContact(type, value);
            }
        } while (rs.next());
        return resumes;
    }

    private List<Resume> getResumesFromPreparedStatement() {
        return sqlHelper.transactionalExecute(conn -> {
            PreparedStatement psResume = conn.prepareStatement(
                    "SELECT * FROM resume " +
                            "ORDER BY full_name, uuid"
            );
            ResultSet rsResume = psResume.executeQuery();
            Map<String, Resume> uuidResumeMap = new HashMap<>();
            while (rsResume.next()) {
                String uuid = rsResume.getString("uuid");
                String fullName = rsResume.getString("full_name");
                Resume resume = new Resume(fullName, uuid);
                uuidResumeMap.put(uuid, resume);
            }

            PreparedStatement psContact = conn.prepareStatement(
                    "SELECT * FROM contact " +
                            "ORDER BY resume_uuid"
            );
            ResultSet rsContact = psContact.executeQuery();
            Resume resume = null;
            while (rsContact.next()) {
                String resumeUuid = rsContact.getString("resume_uuid");
                if (resume == null || !resume.getUuid().equals(resumeUuid)) {
                    resume = uuidResumeMap.get(resumeUuid);
                }
                String contactType = rsContact.getString("type");
                String value = rsContact.getString("value");
                ContactTypes type = ContactTypes.valueOf(contactType);
                resume.setContact(type, value);
            }

            return uuidResumeMap
                    .values()
                    .stream()
                    .sorted(RESUME_COMPARATOR)
                    .collect(Collectors.toList());
        });
    }

    private void insertResume(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        }
    }

    private void insertContact(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactTypes, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteResume(Connection conn, String uuid) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
            ps.setString(1, uuid);
            ps.execute();
        }
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        }
    }
}