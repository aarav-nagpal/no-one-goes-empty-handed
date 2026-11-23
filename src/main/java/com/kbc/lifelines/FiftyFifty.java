package com.kbc.lifelines;

import com.kbc.models.PlayerSession;
import com.kbc.models.Question;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 50:50 Lifeline: Hides 2 incorrect options randomly from the current question.
 * UI-independent: returns the list of 2 wrong option letters (e.g. ["A", "C"]).
 */
public class FiftyFifty implements Lifeline {

    public static final String NAME = "50:50";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isAvailable(PlayerSession session) {
        return session != null && !session.isUsedFiftyFifty();
    }

    /**
     * Executes the 50:50 lifeline on the session and question.
     * Marks usedFiftyFifty = true on PlayerSession and returns 2 wrong option letters.
     *
     * @param session the active player session
     * @param currentQuestion the current question
     * @return List of 2 option letters (e.g. ["B", "D"]) to hide
     */
    @Override
    public List<String> execute(PlayerSession session, Question currentQuestion) {
        if (session != null) {
            session.setUsedFiftyFifty(true);
        }
        return getWrongOptionsToHide(currentQuestion);
    }

    /**
     * Computes 2 incorrect option letters to hide without modifying session state.
     *
     * @param currentQuestion the question to analyze
     * @return List of 2 incorrect option letters
     */
    public List<String> getWrongOptionsToHide(Question currentQuestion) {
        if (currentQuestion == null) {
            return Collections.emptyList();
        }

        String correctOpt = currentQuestion.getCorrectOpt();
        if (correctOpt == null) {
            correctOpt = "";
        }
        correctOpt = correctOpt.trim().toUpperCase();

        List<String> wrongOptions = new ArrayList<>();
        String[] allOptions = {"A", "B", "C", "D"};
        for (String opt : allOptions) {
            if (!opt.equalsIgnoreCase(correctOpt)) {
                wrongOptions.add(opt);
            }
        }

        Collections.shuffle(wrongOptions);

        List<String> toHide = new ArrayList<>();
        int countToTake = Math.min(2, wrongOptions.size());
        for (int i = 0; i < countToTake; i++) {
            toHide.add(wrongOptions.get(i));
        }
        Collections.sort(toHide);
        return toHide;
    }
}
