package com.kbc.models;

/**
 * Immutable Question model constructed using the Builder Design Pattern.
 */
public class Question {

    private final int id;
    private final int roundNumber;
    private final String questionText;
    private final String optA;
    private final String optB;
    private final String optC;
    private final String optD;
    private final String correctOpt;
    private final long prizeAmount;

    // Private constructor enforcing instantiation strictly through the Builder
    private Question(Builder builder) {
        this.id = builder.id;
        this.roundNumber = builder.roundNumber;
        this.questionText = builder.questionText;
        this.optA = builder.optA;
        this.optB = builder.optB;
        this.optC = builder.optC;
        this.optD = builder.optD;
        this.correctOpt = builder.correctOpt;
        this.prizeAmount = builder.prizeAmount;
    }

    public int getId() {
        return id;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public int getRound() {
        return roundNumber;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOptA() {
        return optA;
    }

    public String getOptionA() {
        return optA;
    }

    public String getOptB() {
        return optB;
    }

    public String getOptionB() {
        return optB;
    }

    public String getOptC() {
        return optC;
    }

    public String getOptionC() {
        return optC;
    }

    public String getOptD() {
        return optD;
    }

    public String getOptionD() {
        return optD;
    }

    public String getCorrectOpt() {
        return correctOpt;
    }

    public String getCorrectOption() {
        return correctOpt;
    }

    public long getPrizeAmount() {
        return prizeAmount;
    }

    public long getPrize() {
        return prizeAmount;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Static inner Builder class for Question.
     */
    public static class Builder {
        private int id;
        private int roundNumber;
        private String questionText;
        private String optA;
        private String optB;
        private String optC;
        private String optD;
        private String correctOpt;
        private long prizeAmount;

        public Builder() {
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder roundNumber(int roundNumber) {
            this.roundNumber = roundNumber;
            return this;
        }

        public Builder round(int round) {
            this.roundNumber = round;
            return this;
        }

        public Builder questionText(String questionText) {
            this.questionText = questionText;
            return this;
        }

        public Builder optA(String optA) {
            this.optA = optA;
            return this;
        }

        public Builder optionA(String optionA) {
            this.optA = optionA;
            return this;
        }

        public Builder optB(String optB) {
            this.optB = optB;
            return this;
        }

        public Builder optionB(String optionB) {
            this.optB = optionB;
            return this;
        }

        public Builder optC(String optC) {
            this.optC = optC;
            return this;
        }

        public Builder optionC(String optionC) {
            this.optC = optionC;
            return this;
        }

        public Builder optD(String optD) {
            this.optD = optD;
            return this;
        }

        public Builder optionD(String optionD) {
            this.optD = optionD;
            return this;
        }

        public Builder options(String optA, String optB, String optC, String optD) {
            this.optA = optA;
            this.optB = optB;
            this.optC = optC;
            this.optD = optD;
            return this;
        }

        public Builder correctOpt(String correctOpt) {
            this.correctOpt = correctOpt;
            return this;
        }

        public Builder correctOption(String correctOption) {
            this.correctOpt = correctOption;
            return this;
        }

        public Builder prizeAmount(long prizeAmount) {
            this.prizeAmount = prizeAmount;
            return this;
        }

        public Builder prize(long prize) {
            this.prizeAmount = prize;
            return this;
        }

        public Question build() {
            return new Question(this);
        }
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", roundNumber=" + roundNumber +
                ", questionText='" + questionText + '\'' +
                ", optA='" + optA + '\'' +
                ", optB='" + optB + '\'' +
                ", optC='" + optC + '\'' +
                ", optD='" + optD + '\'' +
                ", correctOpt='" + correctOpt + '\'' +
                ", prizeAmount=" + prizeAmount +
                '}';
    }
}
