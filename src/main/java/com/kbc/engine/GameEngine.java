package com.kbc.engine;

import com.kbc.database.PlayerDAO;
import com.kbc.database.QuestionDAO;
import com.kbc.lifelines.FiftyFifty;
import com.kbc.lifelines.FlipQuestion;
import com.kbc.lifelines.Lifeline;
import com.kbc.lifelines.LifelineFactory;
import com.kbc.lifelines.LifelineType;
import com.kbc.models.PlayerSession;
import com.kbc.models.Question;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * GameEngine manages the 10-round game loop, lifeline triggers via LifelineFactory,
 * answer validation, Double Dip retry logic, and saving game results via PlayerDAO.
 * Completely UI-independent to decouple backend from CLI (ConsoleRunner) and Swing GUI.
 */
public class GameEngine {

    public static final int TOTAL_ROUNDS = 10;
    public static final long[] PRIZE_LADDER = {
        1000L,        // Round 1
        2000L,        // Round 2
        5000L,        // Round 3
        10000L,       // Round 4
        20000L,       // Round 5
        40000L,       // Round 6
        80000L,       // Round 7
        160000L,      // Round 8
        320000L,      // Round 9
        70000000L     // Round 10 (7 Crores)
    };

    /**
     * Outcomes of submitting an answer.
     */
    public enum AnswerResult {
        CORRECT,
        DOUBLE_DIP_FIRST_WRONG,
        WRONG_GAME_OVER,
        GAME_WON
    }

    private final QuestionDAO questionDAO;
    private final PlayerDAO playerDAO;
    private final LifelineFactory lifelineFactory;

    private PlayerSession session;
    private Question currentQuestion;
    private List<String> currentHiddenOptions = new ArrayList<>();
    private boolean doubleDipFirstAttemptMade = false;
    private boolean gameOver = false;

    public GameEngine() {
        this(new QuestionDAO(), new PlayerDAO());
    }

    public GameEngine(QuestionDAO questionDAO, PlayerDAO playerDAO) {
        this.questionDAO = questionDAO != null ? questionDAO : new QuestionDAO();
        this.playerDAO = playerDAO != null ? playerDAO : new PlayerDAO();
        this.lifelineFactory = new LifelineFactory(this.questionDAO);
    }

    /**
     * Initializes and starts a new game session for the player.
     *
     * @param playerName name of the player
     */
    public void startNewGame(String playerName) {
        String name = (playerName != null && !playerName.trim().isEmpty()) ? playerName.trim() : "Player 1";
        this.session = new PlayerSession(name);
        this.session.setCurrentRound(1);
        this.session.setCurrentPrize(0L);
        this.currentHiddenOptions.clear();
        this.doubleDipFirstAttemptMade = false;
        this.gameOver = false;
        loadQuestionForRound(1);
    }

    /**
     * Loads a random question for the specified round.
     */
    private void loadQuestionForRound(int round) {
        this.currentHiddenOptions.clear();
        this.doubleDipFirstAttemptMade = false;
        this.currentQuestion = questionDAO.getRandomQuestionByRound(round, 0);
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public PlayerSession getSession() {
        return session;
    }

    public List<String> getCurrentHiddenOptions() {
        return Collections.unmodifiableList(currentHiddenOptions);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Checks if a lifeline is available for use in the current session.
     */
    public boolean isLifelineAvailable(LifelineType type) {
        if (session == null || gameOver) {
            return false;
        }
        Lifeline lifeline = lifelineFactory.getLifeline(type);
        return lifeline.isAvailable(session);
    }

    /**
     * Triggers the specified lifeline via LifelineFactory.
     *
     * @param type the lifeline to activate
     * @return the result object of the lifeline execution
     */
    public Object useLifeline(LifelineType type) {
        if (session == null || gameOver) {
            throw new IllegalStateException("Game is not active.");
        }
        if (!isLifelineAvailable(type)) {
            throw new IllegalStateException("Lifeline " + type + " has already been used!");
        }

        Lifeline lifeline = lifelineFactory.getLifeline(type);
        Object result = lifeline.execute(session, currentQuestion);

        if (type == LifelineType.FIFTY_FIFTY) {
            @SuppressWarnings("unchecked")
            List<String> hidden = (List<String>) result;
            this.currentHiddenOptions = new ArrayList<>(hidden);
        } else if (type == LifelineType.FLIP) {
            Question newQ = (Question) result;
            if (newQ != null) {
                this.currentQuestion = newQ;
            }
            this.currentHiddenOptions.clear();
            this.doubleDipFirstAttemptMade = false;
        } else if (type == LifelineType.DOUBLE_DIP) {
            this.doubleDipFirstAttemptMade = false;
        }

        return result;
    }

    /**
     * Submits an answer for the current question and returns the result.
     * Handles Double Dip retry logic and saves game results via PlayerDAO on finish.
     *
     * @param selectedOption the selected option ("A", "B", "C", or "D")
     * @return AnswerResult indicating the outcome
     */
    public AnswerResult submitAnswer(String selectedOption) {
        if (session == null || gameOver) {
            throw new IllegalStateException("Game is not active.");
        }
        if (currentQuestion == null) {
            throw new IllegalStateException("No active question.");
        }

        String chosen = selectedOption != null ? selectedOption.trim().toUpperCase() : "";
        String correct = currentQuestion.getCorrectOpt() != null ? currentQuestion.getCorrectOpt().trim().toUpperCase() : "";
        boolean isCorrect = chosen.equals(correct);

        if (isCorrect) {
            // Correct answer
            if (session.isDoubleDipActive()) {
                session.setDoubleDipActive(false);
            }
            session.setCurrentPrize(currentQuestion.getPrizeAmount());

            if (session.getCurrentRound() >= TOTAL_ROUNDS) {
                // Round 10 Won! Game Completed!
                gameOver = true;
                playerDAO.savePlayerResult(session, "WON");
                return AnswerResult.GAME_WON;
            } else {
                // Advance to next round
                int nextRound = session.getCurrentRound() + 1;
                session.setCurrentRound(nextRound);
                loadQuestionForRound(nextRound);
                return AnswerResult.CORRECT;
            }
        } else {
            // Incorrect answer
            if (session.isDoubleDipActive() && !doubleDipFirstAttemptMade) {
                // First wrong guess with Double Dip active: allow second attempt
                doubleDipFirstAttemptMade = true;
                return AnswerResult.DOUBLE_DIP_FIRST_WRONG;
            } else {
                // Second wrong guess or standard wrong guess: Game Over
                if (session.isDoubleDipActive()) {
                    session.setDoubleDipActive(false);
                }
                gameOver = true;
                playerDAO.savePlayerResult(session, "LOST");
                return AnswerResult.WRONG_GAME_OVER;
            }
        }
    }

    /**
     * Checks if the player can voluntarily quit the game.
     * Player cannot quit if Double Dip is active.
     */
    public boolean canQuit() {
        return session != null && !session.isDoubleDipActive() && !gameOver;
    }

    /**
     * Quits the game and saves the session result with status "QUIT".
     */
    public void quitGame() {
        if (!canQuit()) {
            throw new IllegalStateException("Player cannot quit once Double Dip is active or game is over!");
        }
        gameOver = true;
        playerDAO.savePlayerResult(session, "QUIT");
    }

    /**
     * Retrieves the prize amount for a given round number (1-10).
     */
    public static long getPrizeForRound(int round) {
        if (round >= 1 && round <= TOTAL_ROUNDS) {
            return PRIZE_LADDER[round - 1];
        }
        return 0L;
    }
}
