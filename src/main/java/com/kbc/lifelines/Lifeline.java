package com.kbc.lifelines;

import com.kbc.models.PlayerSession;
import com.kbc.models.Question;

/**
 * Common interface representing a KBC Lifeline.
 * Implementations are UI-independent and can be used across CLI and GUI.
 */
public interface Lifeline {

    /**
     * Returns the user-facing name/identifier of this lifeline.
     */
    String getName();

    /**
     * Checks if this lifeline has not yet been used in the current session.
     *
     * @param session the current player session
     * @return true if the lifeline is still available, false otherwise
     */
    boolean isAvailable(PlayerSession session);

    /**
     * Executes the lifeline action.
     *
     * @param session the current player session
     * @param currentQuestion the current active question
     * @return result of the lifeline execution
     */
    Object execute(PlayerSession session, Question currentQuestion);

    /**
     * Convenience alias for execute.
     */
    default Object use(PlayerSession session, Question currentQuestion) {
        return execute(session, currentQuestion);
    }
}
