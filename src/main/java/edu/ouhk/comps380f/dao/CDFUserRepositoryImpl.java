/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.CDFUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author LAM
 */
@Repository
public class CDFUserRepositoryImpl implements CDFUserRepository {

    private DataSource dataSource;
    private JdbcOperations jdbcOp;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcOp = new JdbcTemplate(this.dataSource);
    }

    private static final class CDFUserRowMapper implements RowMapper<CDFUser> {

        @Override
        public CDFUser mapRow(ResultSet rs, int i) throws SQLException {
            CDFUser user = new CDFUser();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    }

    private static final String SQL_INSERT_USER = "insert into users (username, password, email) values (?, ?, ?)";
    private static final String SQL_INSERT_ROLE = "insert into user_roles (username, role) values (?, ?)";

    @Override
    public void create(CDFUser user) {
        jdbcOp.update(SQL_INSERT_USER,
                user.getUsername(),
                user.getPassword(),
                user.getEmail());
        for (String role : user.getRoles()) {
            jdbcOp.update(SQL_INSERT_ROLE,
                    user.getUsername(),
                    role);
        }
    }

    private static final String SQL_SELECT_ALL_USER = "select username, password, email from users";
    private static final String SQL_SELECT_ROLES = "select username, role from user_roles where username = ?";

    @Override
    public List<CDFUser> findAll() {
        List<CDFUser> users = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_USER);

        for (Map<String, Object> row : rows) {
            CDFUser user = new CDFUser();
            String username = (String) row.get("username");
            user.setUsername(username);
            user.setPassword((String) row.get("password"));
            user.setEmail((String) row.get("email"));
            List<Map<String, Object>> roleRows = jdbcOp.queryForList(SQL_SELECT_ROLES, username);
            for (Map<String, Object> roleRow : roleRows) {
                user.addRole((String) roleRow.get("role"));
            }
            users.add(user);
        }
        return users;
    }
    private static final String SQL_SELECT_USER = "select username, password, email from users where username = ?";

    @Override
    public CDFUser findByUsername(String username) {
        try {
            CDFUser user = jdbcOp.queryForObject(SQL_SELECT_USER, new CDFUserRowMapper(), username);
            List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ROLES, username);
            for (Map<String, Object> row : rows) {
                user.addRole((String) row.get("role"));
            }
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String SQL_DELETE_USER = "delete from users where username = ?";
    private static final String SQL_DELETE_ROLES = "delete from user_roles where username = ?";

    @Override
    public void deleteByUsername(String username) {
        jdbcOp.update(SQL_DELETE_ROLES, username);
        jdbcOp.update(SQL_DELETE_USER, username);
    }

    private static final String SQL_UPDATE_USER = "update users set password = ?, email = ? where username = ?";

    @Override
    public void update(CDFUser user) {
        jdbcOp.update(SQL_UPDATE_USER,
                user.getPassword(),
                user.getEmail(),
                user.getUsername());
        jdbcOp.update(SQL_DELETE_ROLES, user.getUsername());
        for (String role : user.getRoles()) {
            jdbcOp.update(SQL_INSERT_ROLE,
                    user.getUsername(),
                    role);
        }
    }
}
