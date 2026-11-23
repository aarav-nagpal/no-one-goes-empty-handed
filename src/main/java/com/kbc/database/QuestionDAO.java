package com.kbc.database;

import com.kbc.models.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for Question entities.
 * Queries MySQL database with seamless fallback to DefaultQuestionBank if offline or empty.
 */
public class QuestionDAO {

    private final DatabaseManager dbManager;

    public QuestionDAO() {
        this(DatabaseManager.getInstance());
    }

    public QuestionDAO(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Retrieves a random question for a given round using PreparedStatement and Question.Builder.
     * If excludeQuestionId > 0, that specific question will be excluded from the query (useful for FLIP lifeline).
     *
     * @param round the round number (1-10)
     * @param excludeQuestionId question ID to exclude, or <= 0 if no question needs to be excluded
     * @return a Question object constructed via Question.Builder, or null if none found
     */
    public Question getRandomQuestionByRound(int round, int excludeQuestionId) {
        String sql;
        if (excludeQuestionId > 0) {
            sql = "SELECT id, round_number, question_text, opt_a, opt_b, opt_c, opt_d, correct_opt, prize_amount " +
                  "FROM questions WHERE round_number = ? AND id != ? ORDER BY RAND() LIMIT 1";
        } else {
            sql = "SELECT id, round_number, question_text, opt_a, opt_b, opt_c, opt_d, correct_opt, prize_amount " +
                  "FROM questions WHERE round_number = ? ORDER BY RAND() LIMIT 1";
        }

        if (dbManager != null) {
            try {
                Connection conn = dbManager.getConnection();
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, round);
                    if (excludeQuestionId > 0) {
                        stmt.setInt(2, excludeQuestionId);
                    }
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            return Question.builder()
                                    .id(rs.getInt("id"))
                                    .roundNumber(rs.getInt("round_number"))
                                    .questionText(rs.getString("question_text"))
                                    .optA(rs.getString("opt_a"))
                                    .optB(rs.getString("opt_b"))
                                    .optC(rs.getString("opt_c"))
                                    .optD(rs.getString("opt_d"))
                                    .correctOpt(rs.getString("correct_opt"))
                                    .prizeAmount(rs.getLong("prize_amount"))
                                    .build();
                        }
                    }
                }
            } catch (SQLException e) {
                // Database query failed or unavailable, fallback to DefaultQuestionBank
            }
        }

        // Offline / initial fallback to default question pool
        return DefaultQuestionBank.getRandomQuestionByRound(round, excludeQuestionId);
    }

    /**
     * Convenience overload to retrieve a random question without excluding any question ID.
     */
    public Question getRandomQuestionByRound(int round) {
        return getRandomQuestionByRound(round, 0);
    }
}
