package com.kbc.lifelines;

import com.kbc.database.QuestionDAO;
import com.kbc.models.PlayerSession;
import com.kbc.models.Question;

/**
 * FLIP Lifeline: Replaces the current question with another random question
 * from the same round pool, excluding the current question ID.
 */
public class FlipQuestion implements Lifeline {

    public static final String NAME = "FLIP";
    private final QuestionDAO questionDAO;

    public FlipQuestion() {
        this(new QuestionDAO());
    }

    public FlipQuestion(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO != null ? questionDAO : new QuestionDAO();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isAvailable(PlayerSession session) {
        return session != null && !session.isUsedFlip();
    }

    /**
     * Executes the FLIP lifeline: marks usedFlip = true on PlayerSession and
     * uses QuestionDAO to fetch a new question from the same round.
     *
     * @param session the active player session
     * @param currentQuestion the question to be replaced
     * @return replacement Question, or null if unavailable
     */
    @Override
    public Question execute(PlayerSession session, Question currentQuestion) {
        if (session != null) {
            session.setUsedFlip(true);
        }
        if (currentQuestion == null) {
            return null;
        }

        Question newQuestion = questionDAO.getRandomQuestionByRound(
                currentQuestion.getRoundNumber(),
                currentQuestion.getId()
        );

        // Fallback to any round question if no distinct question is found
        if (newQuestion == null) {
            newQuestion = questionDAO.getRandomQuestionByRound(currentQuestion.getRoundNumber(), 0);
        }
        return newQuestion;
    }

    /**
     * Convenience method to flip the current question.
     */
    public Question flip(PlayerSession session, Question currentQuestion) {
        return execute(session, currentQuestion);
    }
}
