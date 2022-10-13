package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.ExceptionUtil;
import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.util.JsonParser;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {

    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        LOG.info("CLear DB");
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get from DB " + uuid);
        return sqlHelper.transactionalExecute(connection -> {
            Resume r;

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
            }

            addContacts(connection, r);
            addSections(connection, r);
            return r;
        });
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save in DB " + resume);
        sqlHelper.<Void>transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("" +
                    "INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }

            insertContacts(connection, resume);
            insertSections(connection, resume);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update in DB " + resume);
        sqlHelper.<Void>transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("" +
                    "UPDATE resume SET full_name=? WHERE uuid=?")) {
                String uuid = resume.getUuid();
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                ExceptionUtil.isExist(ps, uuid);
            }

            deleteContacts(connection, resume);
            deleteSections(connection, resume);
            insertContacts(connection, resume);
            insertSections(connection, resume);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete from DB " + uuid);
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ExceptionUtil.isExist(preparedStatement, uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted from DB");
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();

            try (PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT * FROM resume r " +
                    "ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }

            for (Map.Entry<String, Resume> entry : resumes.entrySet()) {
                addContacts(connection, entry.getValue());
                addSections(connection, entry.getValue());
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        LOG.info("Get size of DB");
        return sqlHelper.execute("SELECT count(*) FROM resume", preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private static void addContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
            ps.setString(1, r.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addContact(rs, r);
            }
        }
    }

    private static void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private static void addSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section s WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addSection(rs, r);
            }
        }
    }

    private static void addSection(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            resume.addSection(type, JsonParser.read(value, AbstractSection.class));
        }
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("" +
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("" +
                "INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, JsonParser.write(entry.getValue(), AbstractSection.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume r) throws SQLException {
        deleteAttributes(conn, r, "DELETE FROM contact WHERE resume_uuid=?");
    }

    private void deleteSections(Connection conn, Resume r) throws SQLException {
        deleteAttributes(conn, r, "DELETE FROM section WHERE resume_uuid=?");
    }

    private void deleteAttributes(Connection conn, Resume r, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }
}
