/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.CDFPoll;
import edu.ouhk.comps380f.model.CDFPollAnswer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class CDFPollRepositoryImpl implements CDFPollRepository {

    private DataSource dataSource;
    private JdbcOperations jdbcOp;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcOp = new JdbcTemplate(this.dataSource);
    }

    private static final class CDFPollRowMapper implements RowMapper<CDFPoll> {

        @Override
        public CDFPoll mapRow(ResultSet rs, int i) throws SQLException {
            CDFPoll poll = new CDFPoll();
            poll.setId(rs.getInt("poll_id"));
            poll.setQuestion(rs.getString("question"));
            poll.setOptionA(rs.getString("option_a"));
            poll.setOptionB(rs.getString("option_b"));
            poll.setOptionC(rs.getString("option_c"));
            poll.setOptionD(rs.getString("option_d"));
            poll.setCreateTimestamp(rs.getTimestamp("create_timestamp"));
            poll.setEnable(rs.getBoolean("enable"));
            return poll;
        }
    }

    private static final class CDFPollAnswerRowMapper implements RowMapper<CDFPollAnswer> {

        @Override
        public CDFPollAnswer mapRow(ResultSet rs, int i) throws SQLException {
            CDFPollAnswer pollAnswer = new CDFPollAnswer();
            pollAnswer.setId(rs.getInt("poll_answer_id"));
            pollAnswer.setUsername(rs.getString("username"));
            pollAnswer.setAnswer(rs.getString("answer"));
            pollAnswer.setCreateTimestamp(rs.getTimestamp("create_timestamp"));
            pollAnswer.setPollId(rs.getInt("poll_id"));
            return pollAnswer;
        }
    }

    private static final String SQL_INSERT_POLL = "insert into polls (question, option_a, option_b, option_c, option_d, create_timestamp, enable) values (?, ?, ?, ?, ?, current_timestamp, ?)";

    @Override
    public void create(CDFPoll poll) {
        jdbcOp.update(SQL_INSERT_POLL,
                poll.getQuestion(),
                poll.getOptionA(),
                poll.getOptionB(),
                poll.getOptionC(),
                poll.getOptionD(),
                poll.isEnable());
    }

    private static final String SQL_INSERT_POLL_ANSWER = "insert into poll_answers (username, answer, create_timestamp, poll_id) values (?, ?, current_timestamp, ?)";

    @Override
    public void answer(CDFPollAnswer pollAnswer) {
        CDFPollAnswer existedPollAnswer = findByUsername(pollAnswer.getUsername(), pollAnswer.getPollId());
        if (existedPollAnswer == null) {
            jdbcOp.update(SQL_INSERT_POLL_ANSWER,
                    pollAnswer.getUsername(),
                    pollAnswer.getAnswer(),
                    pollAnswer.getPollId()
            );
        }
    }

    private static final String SQL_SELECT_ALL_POLL = "select * from polls order by poll_id desc";
    private static final String SQL_SELECT_ALL_POLLANSWER = "select * from poll_answers where poll_id = ?";

    @Override
    public List<CDFPoll> findAll(boolean hasPollAnswers) {
        List<CDFPoll> polls = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_POLL);

        for (Map<String, Object> row : rows) {
            CDFPoll poll = new CDFPoll();
            poll.setId((int) row.get("poll_id"));
            poll.setQuestion((String) row.get("question"));
            poll.setOptionA((String) row.get("option_a"));
            poll.setOptionB((String) row.get("option_b"));
            poll.setOptionC((String) row.get("option_c"));
            poll.setOptionD((String) row.get("option_d"));
            poll.setCreateTimestamp((Timestamp) row.get("create_timestamp"));
            poll.setEnable((boolean) row.get("enable"));

            if (hasPollAnswers) {
                List<CDFPollAnswer> pollAnswers = new ArrayList<>();
                List<Map<String, Object>> pollAnswerRows = jdbcOp.queryForList(SQL_SELECT_ALL_POLLANSWER, (int) row.get("poll_id"));
                for (Map<String, Object> pollAnswerRow : pollAnswerRows) {
                    CDFPollAnswer pollAnswer = new CDFPollAnswer();
                    pollAnswer.setId((int) pollAnswerRow.get("poll_answer_id"));
                    pollAnswer.setUsername((String) pollAnswerRow.get("username"));
                    pollAnswer.setAnswer((String) pollAnswerRow.get("answer"));
                    pollAnswer.setCreateTimestamp((Timestamp) pollAnswerRow.get("create_timestamp"));
                    pollAnswer.setPollId((int) pollAnswerRow.get("poll_id"));
                    pollAnswers.add(pollAnswer);
                }
                poll.setPollAnswers(pollAnswers);
            }

            polls.add(poll);
        }
        return polls;
    }

    private static final String SQL_SELECT_LAST_POLL = "select * from polls where enable = true order by create_timestamp desc fetch first row only";

    @Override
    public CDFPoll findLast(boolean hasPollAnswers) {
        try {
            CDFPoll poll = jdbcOp.queryForObject(SQL_SELECT_LAST_POLL, new CDFPollRowMapper());

            if (hasPollAnswers) {
                List<CDFPollAnswer> pollAnswers = new ArrayList<>();
                List<Map<String, Object>> pollAnswerRows = jdbcOp.queryForList(SQL_SELECT_ALL_POLLANSWER, (int) poll.getId());
                for (Map<String, Object> pollAnswerRow : pollAnswerRows) {
                    CDFPollAnswer pollAnswer = new CDFPollAnswer();
                    pollAnswer.setId((int) pollAnswerRow.get("poll_answer_id"));
                    pollAnswer.setUsername((String) pollAnswerRow.get("username"));
                    pollAnswer.setAnswer((String) pollAnswerRow.get("answer"));
                    pollAnswer.setCreateTimestamp((Timestamp) pollAnswerRow.get("create_timestamp"));
                    pollAnswer.setPollId((int) pollAnswerRow.get("poll_id"));
                    pollAnswers.add(pollAnswer);
                }
                poll.setPollAnswers(pollAnswers);
            }

            return poll;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String SQL_SELECT_POLL = "select * from polls where poll_id = ?";

    @Override
    public CDFPoll findByPollId(int pollId, boolean hasPollAnswers) {
        try {
            CDFPoll poll = jdbcOp.queryForObject(SQL_SELECT_POLL, new CDFPollRowMapper(), pollId);

            if (hasPollAnswers) {
                List<CDFPollAnswer> pollAnswers = new ArrayList<>();
                List<Map<String, Object>> pollAnswerRows = jdbcOp.queryForList(SQL_SELECT_ALL_POLLANSWER, (int) poll.getId());
                for (Map<String, Object> pollAnswerRow : pollAnswerRows) {
                    CDFPollAnswer pollAnswer = new CDFPollAnswer();
                    pollAnswer.setId((int) pollAnswerRow.get("poll_answer_id"));
                    pollAnswer.setUsername((String) pollAnswerRow.get("username"));
                    pollAnswer.setAnswer((String) pollAnswerRow.get("answer"));
                    pollAnswer.setCreateTimestamp((Timestamp) pollAnswerRow.get("create_timestamp"));
                    pollAnswer.setPollId((int) pollAnswerRow.get("poll_id"));
                    pollAnswers.add(pollAnswer);
                }
                poll.setPollAnswers(pollAnswers);
            }

            return poll;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String SQL_SELECT_POLLANSWER = "select * from poll_answers where username = ? and poll_id = ?";

    @Override
    public CDFPollAnswer findByUsername(String username, int pollId) {
        try {
            CDFPollAnswer pollAnswer = jdbcOp.queryForObject(SQL_SELECT_POLLANSWER, new CDFPollAnswerRowMapper(), username, pollId);
            return pollAnswer;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String SQL_DELETE_POLL = "delete from users where poll_id = ?";
    private static final String SQL_DELETE_POLL_ANSWER = "delete from poll_answers where poll_id = ?";

    @Override
    public void deleteByPollId(int pollId) {
        jdbcOp.update(SQL_DELETE_POLL_ANSWER, pollId);
        jdbcOp.update(SQL_DELETE_POLL, pollId);
    }
}
