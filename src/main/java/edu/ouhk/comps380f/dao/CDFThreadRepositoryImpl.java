/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.CDFReply;
import edu.ouhk.comps380f.model.CDFThread;
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
public class CDFThreadRepositoryImpl implements CDFThreadRepository {
    private DataSource dataSource;
    private JdbcOperations jdbcOp;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcOp = new JdbcTemplate(this.dataSource);
    }

    private static final class ThreadRowMapper implements RowMapper<CDFThread> {
        @Override
        public CDFThread mapRow(ResultSet rs, int i) throws SQLException {
            CDFThread thread = new CDFThread();
            thread.setId(rs.getInt("thread_id"));
            thread.setUsername(rs.getString("username"));
            thread.setTitle(rs.getString("title"));
            thread.setContent(rs.getString("content"));
            thread.setCategory(rs.getString("category"));
            return thread;
        }
    }

    private static final String SQL_INSERT_THREAD = "insert into threads (username, title, content, category) values (?, ?, ?, ?)";

    @Override
    public void create(CDFThread thread) {
        jdbcOp.update(SQL_INSERT_THREAD,
                thread.getUsername(),
                thread.getTitle(),
                thread.getContent(),
                thread.getCategory());
    }

    private static final String SQL_INSERT_REPLY = "insert into replies (username, content, thread_id) values (?, ?, ?)";

    @Override
    public void reply(CDFReply reply) {
        jdbcOp.update(SQL_INSERT_REPLY,
                reply.getUsername(),
                reply.getContent(),
                reply.getThreadId());
    }
    
    private static final String SQL_SELECT_ALL_THREAD = "select * from threads where category = ? ORDER BY thread_id DESC";
    private static final String SQL_SELECT_ALL_REPLY = "select * from replies where thread_id = ? ORDER BY reply_id DESC";
 
    @Override
    public List<CDFThread> findAllByCategory(String category) {
        List<CDFThread> threads = new ArrayList<>();
        List<Map<String, Object>> threadRows = jdbcOp.queryForList(SQL_SELECT_ALL_THREAD, category);
        
        for (Map<String, Object> threadRow : threadRows) {
            CDFThread thread = new CDFThread();
            thread.setId((int) threadRow.get("thread_id"));
            thread.setUsername((String) threadRow.get("username"));
            thread.setTitle((String) threadRow.get("title"));
            thread.setContent((String) threadRow.get("content"));
            thread.setCategory((String) threadRow.get("category"));
            List<CDFReply> replies = new ArrayList<>();
            List<Map<String, Object>> replyRows = jdbcOp.queryForList(SQL_SELECT_ALL_REPLY, (int) threadRow.get("thread_id"));
            for (Map<String, Object> replyRow : replyRows) {
                CDFReply reply = new CDFReply();
                reply.setId((int) replyRow.get("reply_id"));
                reply.setUsername((String) replyRow.get("username"));
                reply.setContent((String) replyRow.get("content"));
                reply.setThreadId((int) replyRow.get("thread_id"));
                replies.add(reply);
            }
            thread.setReplies(replies);
            threads.add(thread);
        }
        return threads;
    }
    
    private static final String SQL_SELECT_THREAD = "select * from threads where thread_id = ?";

    @Override
    public CDFThread findByThreadId(int threadId) {
        try {
            CDFThread thread = jdbcOp.queryForObject(SQL_SELECT_THREAD, new ThreadRowMapper(), threadId);
            List<CDFReply> replies = new ArrayList<>();
            List<Map<String, Object>> replyRows = jdbcOp.queryForList(SQL_SELECT_ALL_REPLY, (int) thread.getId());
            for (Map<String, Object> replyRow : replyRows) {
                CDFReply reply = new CDFReply();
                reply.setId((int) replyRow.get("reply_id"));
                reply.setUsername((String) replyRow.get("username"));
                reply.setContent((String) replyRow.get("content"));
                reply.setThreadId((int) replyRow.get("thread_id"));
                replies.add(reply);
            }
            thread.setReplies(replies);
            return thread;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String SQL_DELETE_THREAD = "delete from threads where thread_id = ?";

    @Override
    public void deleteByThreadId(int threadId) {
        jdbcOp.update(SQL_DELETE_THREAD, threadId);
    }
}
