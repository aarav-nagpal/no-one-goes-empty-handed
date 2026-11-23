package com.kbc.lifelines;

import com.kbc.database.QuestionDAO;

/**
 * Factory class for creating KBC Lifeline instances.
 * Implements the Factory Design Pattern.
 */
public class LifelineFactory {

    private final QuestionDAO questionDAO;

    public LifelineFactory() {
        this(new QuestionDAO());
    }

    public LifelineFactory(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO != null ? questionDAO : new QuestionDAO();
    }

    /**
     * Creates a Lifeline instance based on the LifelineType enum.
     */
    public Lifeline getLifeline(LifelineType type) {
        return createLifeline(type, this.questionDAO);
    }

    /**
     * Creates a Lifeline instance based on a String identifier.
     */
    public Lifeline getLifeline(String type) {
        return createLifeline(type, this.questionDAO);
    }

    /**
     * Static factory method accepting LifelineType.
     */
    public static Lifeline createLifeline(LifelineType type) {
        return createLifeline(type, new QuestionDAO());
    }

    /**
     * Static factory method accepting LifelineType and custom QuestionDAO.
     */
    public static Lifeline createLifeline(LifelineType type, QuestionDAO questionDAO) {
        if (type == null) {
            throw new IllegalArgumentException("LifelineType cannot be null");
        }
        switch (type) {
            case FIFTY_FIFTY:
                return new FiftyFifty();
            case DOUBLE_DIP:
                return new DoubleDip();
            case FLIP:
                return new FlipQuestion(questionDAO);
            default:
                throw new IllegalArgumentException("Unsupported LifelineType: " + type);
        }
    }

    /**
     * Static factory method accepting a String representation of the lifeline.
     * Supports formats such as "50:50", "FIFTY_FIFTY", "DOUBLE_DIP", "FLIP".
     */
    public static Lifeline createLifeline(String type) {
        return createLifeline(type, new QuestionDAO());
    }

    /**
     * Static factory method accepting String and custom QuestionDAO.
     */
    public static Lifeline createLifeline(String type, QuestionDAO questionDAO) {
        if (type == null) {
            throw new IllegalArgumentException("Lifeline type cannot be null");
        }
        String normalized = type.trim().toUpperCase().replace(" ", "_").replace(":", "_");
        switch (normalized) {
            case "50_50":
            case "5050":
            case "FIFTY_FIFTY":
            case "FIFTYFIFTY":
                return new FiftyFifty();
            case "DOUBLE_DIP":
            case "DOUBLEDIP":
                return new DoubleDip();
            case "FLIP":
            case "FLIP_QUESTION":
            case "FLIPQUESTION":
                return new FlipQuestion(questionDAO);
            default:
                throw new IllegalArgumentException("Unknown lifeline type: " + type);
        }
    }
}
