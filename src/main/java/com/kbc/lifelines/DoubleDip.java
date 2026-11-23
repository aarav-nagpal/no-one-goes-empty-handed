package com.kbc.lifelines;

import com.kbc.models.PlayerSession;
import com.kbc.models.Question;

/**
 * DOUBLE_DIP Lifeline: Allows 2 guesses on the current question.
 * Marks doubleDipActive = true and usedDoubleDip = true on the PlayerSession.
 * Player cannot quit once activated.
 */
public class DoubleDip implements Lifeline {

    public static final String NAME = "DOUBLE_DIP";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isAvailable(PlayerSession session) {
        return session != null && !session.isUsedDoubleDip();
    }

    /**
     * Activates DoubleDip on the session.
     * Sets doubleDipActive = true and usedDoubleDip = true.
     *
     * @param session the active player session
     * @param currentQuestion the current question (optional)
     * @return Boolean.TRUE indicating successful activation
     */
    @Override
    public Boolean execute(PlayerSession session, Question currentQuestion) {
        activate(session);
        return Boolean.TRUE;
    }

    /**
     * Activates DoubleDip on the session.
     *
     * @param session the player session to update
     */
    public void activate(PlayerSession session) {
        if (session != null) {
            session.setUsedDoubleDip(true);
            session.setDoubleDipActive(true);
        }
    }
}
