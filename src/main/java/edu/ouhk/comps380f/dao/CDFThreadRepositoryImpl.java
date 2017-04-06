/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.CDFAttachment;
import edu.ouhk.comps380f.model.CDFReply;
import edu.ouhk.comps380f.model.CDFThread;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author LAM
 */
@Repository
public class CDFThreadRepositoryImpl implements CDFThreadRepository {

    @Autowired
    private ServletContext servletContext;

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

    private static final class ReplyRowMapper implements RowMapper<CDFReply> {

        @Override
        public CDFReply mapRow(ResultSet rs, int i) throws SQLException {
            CDFReply reply = new CDFReply();
            reply.setId(rs.getInt("reply_id"));
            reply.setUsername(rs.getString("username"));
            reply.setContent(rs.getString("content"));
            reply.setThreadId(rs.getInt("thread_id"));
            return reply;
        }
    }

    private static final class AttachmentRowMapper implements RowMapper<CDFAttachment> {

        @Override
        public CDFAttachment mapRow(ResultSet rs, int i) throws SQLException {
            CDFAttachment attachment = new CDFAttachment();
            attachment.setId(rs.getInt("attachment_id"));
            attachment.setName(rs.getString("filename"));
            attachment.setMimeContentType(rs.getString("filetype"));
            attachment.setPath(rs.getString("filepath"));
            attachment.setReferenceId(rs.getInt("thread_id"));
            return attachment;
        }
    }

    private static final String SQL_INSERT_THREAD = "insert into threads (username, title, content, category) values (?, ?, ?, ?)";
    private static final String SQL_INSERT_THREAD_ATTACHMENT = "insert into thread_attachments (filename, filetype, filepath, thread_id) values (?, ?, ?, ?)";
    private static final String SQL_UPDATE_THREAD_ATTACHMENT = "update thread_attachments set filepath = ? where attachment_id = ?";
    
    @Override
    public void create(final CDFThread thread) throws IOException {
        /*int attachmentId = 1;
        CDFAttachment lastThreadAttachment = findLastThreadAttachment();
        if (lastThreadAttachment != null) {
            attachmentId = lastThreadAttachment.getId() + 1;
        }*/
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOp.update(
            new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(SQL_INSERT_THREAD, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, thread.getUsername());
                    ps.setString(2, thread.getTitle());
                    ps.setString(3, thread.getContent());
                    ps.setString(4, thread.getCategory());
                    return ps;
                }
            }, keyHolder);
        thread.setId(keyHolder.getKey().intValue());
        
        for (int i = 0; i < thread.getAttachments().size(); i++) {
            final CDFAttachment attachment = thread.getAttachments().get(i);

            keyHolder = new GeneratedKeyHolder();
            jdbcOp.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(SQL_INSERT_THREAD_ATTACHMENT, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, attachment.getName());
                        ps.setString(2, attachment.getMimeContentType());
                        ps.setString(3, "");
                        ps.setInt(4, thread.getId());
                        return ps;
                    }
                }, keyHolder);
            attachment.setId(keyHolder.getKey().intValue());
        
            String directoryPath = servletContext.getRealPath("/attachments/" + thread.getCategory() + "/" + thread.getId());

            File directory = new File(directoryPath);
            if (!directory.exists() && !directory.isDirectory()) {
                directory.mkdirs();
            }
            String path = directoryPath + "/thread_" + attachment.getId() + "_" + attachment.getName();

            attachment.setPath(path);
            attachment.getFile().transferTo(new File(path));
            
            jdbcOp.update(SQL_UPDATE_THREAD_ATTACHMENT,
                    attachment.getPath(),
                    attachment.getId());
        }
        /*
        int threadId = 1;
        CDFThread lastThread = findLastThread(false);
        if (lastThread != null) {
            threadId = lastThread.getId() + 1;
        }

        int attachmentId = 1;
        CDFAttachment lastThreadAttachment = findLastThreadAttachment();
        if (lastThreadAttachment != null) {
            attachmentId = lastThreadAttachment.getId() + 1;
        }

        thread.setId(threadId);

        jdbcOp.update(SQL_INSERT_THREAD,
                thread.getUsername(),
                thread.getTitle(),
                thread.getContent(),
                thread.getCategory());

        for (int i = 0; i < thread.getAttachments().size(); i++) {
            CDFAttachment attachment = thread.getAttachments().get(i);

            String directoryPath = servletContext.getRealPath("/attachments/" + thread.getCategory() + "/" + threadId + "/thread/" + (attachmentId + i));

            File directory = new File(directoryPath);
            if (!directory.exists() && !directory.isDirectory()) {
                directory.mkdirs();
            }
            String path = directoryPath + "/" + attachment.getName();

            attachment.setPath(path);
            attachment.getFile().transferTo(new File(path));

            jdbcOp.update(SQL_INSERT_THREAD_ATTACHMENT,
                    attachment.getName(),
                    attachment.getMimeContentType(),
                    attachment.getPath(),
                    thread.getId());
        }
         */
    }

    private static final String SQL_INSERT_REPLY = "insert into replies (username, content, thread_id) values (?, ?, ?)";
    private static final String SQL_INSERT_REPLY_ATTACHMENT = "insert into reply_attachments (filename, filetype, filepath, reply_id) values (?, ?, ?, ?)";
    private static final String SQL_UPDATE_REPLY_ATTACHMENT = "update reply_attachments set filepath = ? where attachment_id = ?";
    
    @Override
    public void reply(final CDFReply reply) throws IOException {
        /*
        int attachmentId = 1;
        CDFAttachment lastReplyAttachment = findLastReplyAttachment();
        if (lastReplyAttachment != null) {
            attachmentId = lastReplyAttachment.getId() + 1;
        }
        */
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOp.update(
            new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(SQL_INSERT_REPLY, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, reply.getUsername());
                    ps.setString(2, reply.getContent());
                    ps.setInt(3, reply.getThreadId());
                    return ps;
                }
            }, keyHolder);
        reply.setId(keyHolder.getKey().intValue());
        
        CDFThread thread = findByThreadId(reply.getThreadId(), false);
        
        for (int i = 0; i < reply.getAttachments().size(); i++) {
            final CDFAttachment attachment = reply.getAttachments().get(i);

            keyHolder = new GeneratedKeyHolder();
            jdbcOp.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(SQL_INSERT_REPLY_ATTACHMENT, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, attachment.getName());
                        ps.setString(2, attachment.getMimeContentType());
                        ps.setString(3, "");
                        ps.setInt(4, reply.getId());
                        return ps;
                    }
                }, keyHolder);
            attachment.setId(keyHolder.getKey().intValue());
        
            String directoryPath = servletContext.getRealPath("/attachments/" + thread.getCategory() + "/" + thread.getId());

            File directory = new File(directoryPath);
            if (!directory.exists() && !directory.isDirectory()) {
                directory.mkdirs();
            }
            String path = directoryPath + "/reply_" + attachment.getId() + "_" + attachment.getName();
            
            attachment.setPath(path);
            attachment.getFile().transferTo(new File(path));
            
            jdbcOp.update(SQL_UPDATE_REPLY_ATTACHMENT,
                    attachment.getPath(),
                    attachment.getId());
        }
        /*
        int replyId = 1;
        CDFReply lastReply = findLastReply(false);
        if (lastReply != null) {
            replyId = lastReply.getId() + 1;
        }

        int attachmentId = 1;
        CDFAttachment lastThreadAttachment = findLastThreadAttachment();
        if (lastThreadAttachment != null) {
            attachmentId = lastThreadAttachment.getId() + 1;
        }

        reply.setId(replyId);

        CDFThread thread = findByThreadId(reply.getThreadId(), false);

        jdbcOp.update(SQL_INSERT_REPLY,
                reply.getUsername(),
                reply.getContent(),
                reply.getThreadId());

        for (int i = 0; i < reply.getAttachments().size(); i++) {
            CDFAttachment attachment = reply.getAttachments().get(i);

            String directoryPath = servletContext.getRealPath("/attachments/" + thread.getCategory() + "/" + reply.getThreadId() + "/reply/" + (attachmentId + i));

            File directory = new File(directoryPath);
            if (!directory.exists() && !directory.isDirectory()) {
                directory.mkdirs();
            }
            String path = directoryPath + "/" + attachment.getName();

            attachment.setPath(path);
            attachment.getFile().transferTo(new File(path));

            jdbcOp.update(SQL_INSERT_REPLY_ATTACHMENT,
                    attachment.getName(),
                    attachment.getMimeContentType(),
                    attachment.getPath(),
                    reply.getId());
        }
        */
    }

    private static final String SQL_SELECT_ALL_THREAD = "select * from threads where category = ? order by thread_id desc";
    private static final String SQL_SELECT_ALL_REPLY = "select * from replies where thread_id = ? order by reply_id desc";
    private static final String SQL_SELECT_THREAD_ATTACHMENT = "select * from thread_attachments where thread_id = ?";
    private static final String SQL_SELECT_REPLY_ATTACHMENT = "select * from reply_attachments where reply_id = ?";

    @Override
    public List<CDFThread> findAllByCategory(String category, boolean hasAttachments) {
        List<CDFThread> threads = new ArrayList<>();
        List<Map<String, Object>> threadRows = jdbcOp.queryForList(SQL_SELECT_ALL_THREAD, category);

        for (Map<String, Object> threadRow : threadRows) {
            CDFThread thread = new CDFThread();
            thread.setId((int) threadRow.get("thread_id"));
            thread.setUsername((String) threadRow.get("username"));
            thread.setTitle((String) threadRow.get("title"));
            thread.setContent((String) threadRow.get("content"));
            thread.setCategory((String) threadRow.get("category"));

            if (hasAttachments) {
                List<CDFAttachment> threadAttachments = new ArrayList<>();
                List<Map<String, Object>> threadAttachmentRows = jdbcOp.queryForList(SQL_SELECT_THREAD_ATTACHMENT, (int) threadRow.get("thread_id"));
                for (Map<String, Object> threadAttachmentRow : threadAttachmentRows) {
                    CDFAttachment threadAttachment = new CDFAttachment();
                    threadAttachment.setId((int) threadAttachmentRow.get("attachment_id"));
                    threadAttachment.setName((String) threadAttachmentRow.get("filename"));
                    threadAttachment.setMimeContentType((String) threadAttachmentRow.get("filetype"));
                    threadAttachment.setPath((String) threadAttachmentRow.get("filepath"));
                    threadAttachment.setReferenceId((int) threadAttachmentRow.get("thread_id"));
                    threadAttachments.add(threadAttachment);
                }
                thread.setAttachments(threadAttachments);
            }

            List<CDFReply> replies = new ArrayList<>();
            List<Map<String, Object>> replyRows = jdbcOp.queryForList(SQL_SELECT_ALL_REPLY, (int) threadRow.get("thread_id"));
            for (Map<String, Object> replyRow : replyRows) {
                CDFReply reply = new CDFReply();
                reply.setId((int) replyRow.get("reply_id"));
                reply.setUsername((String) replyRow.get("username"));
                reply.setContent((String) replyRow.get("content"));
                reply.setThreadId((int) replyRow.get("thread_id"));

                if (hasAttachments) {
                    List<CDFAttachment> replyAttachments = new ArrayList<>();
                    List<Map<String, Object>> replyAttachmentRows = jdbcOp.queryForList(SQL_SELECT_REPLY_ATTACHMENT, (int) replyRow.get("reply_id"));
                    for (Map<String, Object> replyAttachmentRow : replyAttachmentRows) {
                        CDFAttachment replyAttachment = new CDFAttachment();
                        replyAttachment.setId((int) replyAttachmentRow.get("attachment_id"));
                        replyAttachment.setName((String) replyAttachmentRow.get("filename"));
                        replyAttachment.setMimeContentType((String) replyAttachmentRow.get("filetype"));
                        replyAttachment.setPath((String) replyAttachmentRow.get("filepath"));
                        replyAttachment.setReferenceId((int) replyAttachmentRow.get("reply_id"));
                        replyAttachments.add(replyAttachment);
                    }
                    reply.setAttachments(replyAttachments);
                }

                replies.add(reply);
            }
            thread.setReplies(replies);
            threads.add(thread);
        }
        return threads;
    }

    private static final String SQL_SELECT_THREAD = "select * from threads where thread_id = ?";

    @Override
    public CDFThread findByThreadId(int threadId, boolean hasAttachments) {
        try {
            CDFThread thread = jdbcOp.queryForObject(SQL_SELECT_THREAD, new ThreadRowMapper(), threadId);
            if (hasAttachments) {
                List<CDFAttachment> threadAttachments = new ArrayList<>();
                List<Map<String, Object>> threadAttachmentRows = jdbcOp.queryForList(SQL_SELECT_THREAD_ATTACHMENT, (int) thread.getId());
                for (Map<String, Object> threadAttachmentRow : threadAttachmentRows) {
                    CDFAttachment threadAttachment = new CDFAttachment();
                    threadAttachment.setId((int) threadAttachmentRow.get("attachment_id"));
                    threadAttachment.setName((String) threadAttachmentRow.get("filename"));
                    threadAttachment.setMimeContentType((String) threadAttachmentRow.get("filetype"));
                    threadAttachment.setPath((String) threadAttachmentRow.get("filepath"));
                    threadAttachment.setReferenceId((int) threadAttachmentRow.get("thread_id"));
                    threadAttachments.add(threadAttachment);
                }
                thread.setAttachments(threadAttachments);
            }

            List<CDFReply> replies = new ArrayList<>();
            List<Map<String, Object>> replyRows = jdbcOp.queryForList(SQL_SELECT_ALL_REPLY, (int) thread.getId());
            for (Map<String, Object> replyRow : replyRows) {
                CDFReply reply = new CDFReply();
                reply.setId((int) replyRow.get("reply_id"));
                reply.setUsername((String) replyRow.get("username"));
                reply.setContent((String) replyRow.get("content"));
                reply.setThreadId((int) replyRow.get("thread_id"));

                if (hasAttachments) {
                    List<CDFAttachment> replyAttachments = new ArrayList<>();
                    List<Map<String, Object>> replyAttachmentRows = jdbcOp.queryForList(SQL_SELECT_REPLY_ATTACHMENT, (int) replyRow.get("reply_id"));
                    for (Map<String, Object> replyAttachmentRow : replyAttachmentRows) {
                        CDFAttachment replyAttachment = new CDFAttachment();
                        replyAttachment.setId((int) replyAttachmentRow.get("attachment_id"));
                        replyAttachment.setName((String) replyAttachmentRow.get("filename"));
                        replyAttachment.setMimeContentType((String) replyAttachmentRow.get("filetype"));
                        replyAttachment.setPath((String) replyAttachmentRow.get("filepath"));
                        replyAttachment.setReferenceId((int) replyAttachmentRow.get("reply_id"));
                        replyAttachments.add(replyAttachment);
                    }
                    reply.setAttachments(replyAttachments);
                }

                replies.add(reply);
            }
            thread.setReplies(replies);
            return thread;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String SQL_SELECT_REPLY = "select * from replies where reply_id = ?";

    @Override
    public CDFReply findByReplyId(int replyId, boolean hasAttachments) {
        try {
            CDFReply reply = jdbcOp.queryForObject(SQL_SELECT_REPLY, new ReplyRowMapper(), replyId);

            if (hasAttachments) {
                List<CDFAttachment> replyAttachments = new ArrayList<>();
                List<Map<String, Object>> replyAttachmentRows = jdbcOp.queryForList(SQL_SELECT_REPLY_ATTACHMENT, (int) reply.getId());
                for (Map<String, Object> replyAttachmentRow : replyAttachmentRows) {
                    CDFAttachment replyAttachment = new CDFAttachment();
                    replyAttachment.setId((int) replyAttachmentRow.get("attachment_id"));
                    replyAttachment.setName((String) replyAttachmentRow.get("filename"));
                    replyAttachment.setMimeContentType((String) replyAttachmentRow.get("filetype"));
                    replyAttachment.setPath((String) replyAttachmentRow.get("filepath"));
                    replyAttachment.setReferenceId((int) replyAttachmentRow.get("reply_id"));
                    replyAttachments.add(replyAttachment);
                }
                reply.setAttachments(replyAttachments);
            }

            return reply;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
/*
    private static final String SQL_SELECT_LAST_THREAD = "select * from threads order by thread_id desc fetch first row only";

    public CDFThread findLastThread(boolean hasAttachments) {
        try {
            CDFThread thread = jdbcOp.queryForObject(SQL_SELECT_LAST_THREAD, new ThreadRowMapper());
            if (hasAttachments) {
                List<CDFAttachment> threadAttachments = new ArrayList<>();
                List<Map<String, Object>> threadAttachmentRows = jdbcOp.queryForList(SQL_SELECT_THREAD_ATTACHMENT, (int) thread.getId());
                for (Map<String, Object> threadAttachmentRow : threadAttachmentRows) {
                    CDFAttachment threadAttachment = new CDFAttachment();
                    threadAttachment.setId((int) threadAttachmentRow.get("attachment_id"));
                    threadAttachment.setName((String) threadAttachmentRow.get("filename"));
                    threadAttachment.setMimeContentType((String) threadAttachmentRow.get("filetype"));
                    threadAttachment.setPath((String) threadAttachmentRow.get("filepath"));
                    threadAttachment.setReferenceId((int) threadAttachmentRow.get("thread_id"));
                    threadAttachments.add(threadAttachment);
                }
                thread.setAttachments(threadAttachments);
            }
            List<CDFReply> replies = new ArrayList<>();
            List<Map<String, Object>> replyRows = jdbcOp.queryForList(SQL_SELECT_ALL_REPLY, (int) thread.getId());
            for (Map<String, Object> replyRow : replyRows) {
                CDFReply reply = new CDFReply();
                reply.setId((int) replyRow.get("reply_id"));
                reply.setUsername((String) replyRow.get("username"));
                reply.setContent((String) replyRow.get("content"));
                reply.setThreadId((int) replyRow.get("thread_id"));

                if (hasAttachments) {
                    List<CDFAttachment> replyAttachments = new ArrayList<>();
                    List<Map<String, Object>> replyAttachmentRows = jdbcOp.queryForList(SQL_SELECT_REPLY_ATTACHMENT, (int) replyRow.get("reply_id"));
                    for (Map<String, Object> replyAttachmentRow : replyAttachmentRows) {
                        CDFAttachment replyAttachment = new CDFAttachment();
                        replyAttachment.setId((int) replyAttachmentRow.get("attachment_id"));
                        replyAttachment.setName((String) replyAttachmentRow.get("filename"));
                        replyAttachment.setMimeContentType((String) replyAttachmentRow.get("filetype"));
                        replyAttachment.setPath((String) replyAttachmentRow.get("filepath"));
                        replyAttachment.setReferenceId((int) replyAttachmentRow.get("reply_id"));
                        replyAttachments.add(replyAttachment);
                    }
                    reply.setAttachments(replyAttachments);
                }

                replies.add(reply);
            }
            thread.setReplies(replies);
            return thread;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String SQL_SELECT_LAST_THREAD_ATTACHMENT = "select * from thread_attachments order by attachment_id desc fetch first row only";

    public CDFAttachment findLastThreadAttachment() {
        try {
            CDFAttachment attachment = jdbcOp.queryForObject(SQL_SELECT_LAST_THREAD_ATTACHMENT, new AttachmentRowMapper());
            return attachment;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String SQL_SELECT_LAST_REPLY = "select * from replies order by reply_id desc fetch first row only";

    public CDFReply findLastReply(boolean hasAttachments) {
        try {
            CDFReply reply = jdbcOp.queryForObject(SQL_SELECT_LAST_REPLY, new ReplyRowMapper());

            if (hasAttachments) {
                List<CDFAttachment> replyAttachments = new ArrayList<>();
                List<Map<String, Object>> replyAttachmentRows = jdbcOp.queryForList(SQL_SELECT_REPLY_ATTACHMENT, (int) reply.getId());
                for (Map<String, Object> replyAttachmentRow : replyAttachmentRows) {
                    CDFAttachment replyAttachment = new CDFAttachment();
                    replyAttachment.setId((int) replyAttachmentRow.get("attachment_id"));
                    replyAttachment.setName((String) replyAttachmentRow.get("filename"));
                    replyAttachment.setMimeContentType((String) replyAttachmentRow.get("filetype"));
                    replyAttachment.setPath((String) replyAttachmentRow.get("filepath"));
                    replyAttachment.setReferenceId((int) replyAttachmentRow.get("reply_id"));
                    replyAttachments.add(replyAttachment);
                }
                reply.setAttachments(replyAttachments);
            }

            return reply;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }*

    private static final String SQL_SELECT_LAST_REPLY_ATTACHMENT = "select * from reply_attachments order by attachment_id desc fetch first row only";

    public CDFAttachment findLastReplyAttachment() {
        try {
            CDFAttachment attachment = jdbcOp.queryForObject(SQL_SELECT_LAST_REPLY_ATTACHMENT, new AttachmentRowMapper());
            return attachment;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }*/

    private static final String SQL_DELETE_THREAD = "delete from threads where thread_id = ?";
    private static final String SQL_DELETE_THREAD_ATTACHMENT = "delete from thread_attachments where thread_id = ?";

    @Override
    public void deleteByThreadId(int threadId) throws IOException {
        CDFThread thread = findByThreadId(threadId, true);
        if (thread != null) {
            for (CDFAttachment attachment : thread.getAttachments()) {
                File file = new File(attachment.getPath());
                Files.deleteIfExists(file.toPath());
            }
        }
        if (thread != null) {
            for (CDFReply reply : thread.getReplies()) {
                deleteByReplyId(reply.getId());
            }
        }
        jdbcOp.update(SQL_DELETE_THREAD_ATTACHMENT, threadId);
        jdbcOp.update(SQL_DELETE_THREAD, threadId);
    }

    private static final String SQL_DELETE_REPLY = "delete from replies where reply_id = ?";
    private static final String SQL_DELETE_REPLY_ATTACHMENT = "delete from reply_attachments where reply_id = ?";

    @Override
    public void deleteByReplyId(int replyId) throws IOException {
        CDFReply reply = findByReplyId(replyId, true);
        if (reply != null) {
            for (CDFAttachment attachment : reply.getAttachments()) {
                File file = new File(attachment.getPath());
                Files.deleteIfExists(file.toPath());
            }
        }
        jdbcOp.update(SQL_DELETE_REPLY_ATTACHMENT, replyId);
        jdbcOp.update(SQL_DELETE_REPLY, replyId);
    }
}
