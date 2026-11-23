package com.kbc.ui.player;

import com.kbc.engine.GameEngine;
import com.kbc.engine.GameEngine.AnswerResult;
import com.kbc.lifelines.LifelineType;
import com.kbc.models.PlayerSession;
import com.kbc.models.Question;
import java.util.List;
import java.util.Scanner;

/**
 * Terminal Console Runner for playing the KBC Java game.
 * Formats questions, handles lifelines, prints "aaaagg🔥" on wrong answers,
 * and outputs "7 croree!! hame dedo" on winning Round 10.
 */
public class ConsoleRunner {

    public static void main(String[] args) {
        playGame(new Scanner(System.in));
    }

    /**
     * Executes the CLI game loop using the provided Scanner and a default GameEngine.
     */
    public static void playGame(Scanner scanner) {
        playGame(scanner, new GameEngine());
    }

    /**
     * Executes the CLI game loop using the provided Scanner and GameEngine instance.
     */
    public static void playGame(Scanner scanner, GameEngine engine) {
        System.out.println("==================================================================");
        System.out.println("          NO ONE GOES EMPTY HANDED - KBC JAVA EDITION             ");
        System.out.println("==================================================================");
        System.out.println("Welcome to the Ultimate Core Java Engine Challenge!");
        System.out.println("Climb all 10 Rounds to claim the ultimate 7 Crore prize!");
        System.out.println("==================================================================");

        System.out.print("Enter your player name: ");
        String name = "";
        if (scanner.hasNextLine()) {
            name = scanner.nextLine().trim();
        }
        if (name.isEmpty()) {
            name = "Player 1";
        }

        engine.startNewGame(name);

        System.out.println("\nWelcome, " + name + "! Let the game begin!\n");

        while (!engine.isGameOver()) {
            PlayerSession session = engine.getSession();
            Question q = engine.getCurrentQuestion();
            if (q == null) {
                System.out.println("Error: Unable to fetch question for round " + session.getCurrentRound());
                break;
            }

            printRoundHeader(session, q);
            printQuestionAndOptions(q, engine.getCurrentHiddenOptions());
            printLifelineStatus(engine);

            boolean questionResolved = false;
            while (!questionResolved && !engine.isGameOver()) {
                System.out.print("\nEnter your choice (A/B/C/D), Lifeline (L1/L2/L3), or Quit (Q): ");
                if (!scanner.hasNextLine()) {
                    return;
                }
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("Q")) {
                    if (!engine.canQuit()) {
                        System.out.println("⚠️  Double Dip is active! You cannot quit on this question—you must guess!");
                        continue;
                    }
                    System.out.print("Are you sure you want to quit and take home ₹" + session.getCurrentPrize() + "? (Y/N): ");
                    String confirm = scanner.hasNextLine() ? scanner.nextLine().trim() : "N";
                    if (confirm.equalsIgnoreCase("Y")) {
                        engine.quitGame();
                        System.out.println("\n==================================================================");
                        System.out.println("Game Over! You chose to walk away with your winnings.");
                        System.out.println("Player: " + session.getPlayerName());
                        System.out.println("Total Prize Won: ₹" + session.getCurrentPrize());
                        System.out.println("==================================================================");
                        return;
                    } else {
                        continue;
                    }
                }

                // Lifeline shortcuts: L1 / 1 (50:50), L2 / 2 (Double Dip), L3 / 3 (Flip)
                if (input.equalsIgnoreCase("L1") || input.equals("1")) {
                    handleFiftyFifty(engine);
                    q = engine.getCurrentQuestion();
                    printQuestionAndOptions(q, engine.getCurrentHiddenOptions());
                    continue;
                } else if (input.equalsIgnoreCase("L2") || input.equals("2")) {
                    handleDoubleDip(engine);
                    continue;
                } else if (input.equalsIgnoreCase("L3") || input.equals("3")) {
                    handleFlip(engine);
                    q = engine.getCurrentQuestion();
                    printQuestionAndOptions(q, engine.getCurrentHiddenOptions());
                    continue;
                }

                String upperChoice = input.toUpperCase();
                if (!upperChoice.equals("A") && !upperChoice.equals("B") && !upperChoice.equals("C") && !upperChoice.equals("D")) {
                    System.out.println("Invalid input. Please enter A, B, C, D, a Lifeline (L1, L2, L3), or Q to quit.");
                    continue;
                }

                // Check if user picked an eliminated option
                if (engine.getCurrentHiddenOptions().contains(upperChoice)) {
                    System.out.println("Option [" + upperChoice + "] was eliminated by 50:50! Please select a visible option.");
                    continue;
                }

                // Process the answer
                AnswerResult result = engine.submitAnswer(upperChoice);

                if (result == AnswerResult.CORRECT) {
                    System.out.println("\n✅ Sahi Jawab (Correct)! You earned ₹" + q.getPrizeAmount() + "!");
                    System.out.println("Current Bank Balance: ₹" + session.getCurrentPrize() + "\n");
                    questionResolved = true;
                } else if (result == AnswerResult.GAME_WON) {
                    System.out.println("\n✅ Sahi Jawab (Correct)!");
                    System.out.println("7 croree!! hame dedo");
                    System.out.println("\n==================================================================");
                    System.out.println("🏆 CHAMPION! Congratulations " + session.getPlayerName() + "!");
                    System.out.println("You conquered all 10 rounds of Core Java internals!");
                    System.out.println("Total Prize Won: ₹" + session.getCurrentPrize());
                    System.out.println("==================================================================");
                    return;
                } else if (result == AnswerResult.DOUBLE_DIP_FIRST_WRONG) {
                    System.out.println("\n❌ Incorrect! But DOUBLE DIP is active! You get ONE MORE chance on this question.");
                    // loop continues to accept the second answer attempt
                } else if (result == AnswerResult.WRONG_GAME_OVER) {
                    System.out.println("\naaaagg🔥");
                    System.out.println("Galat Jawab (Wrong Answer)! The correct option was [" + q.getCorrectOpt() + "].");
                    System.out.println("\n==================================================================");
                    System.out.println("GAME OVER!");
                    System.out.println("Player: " + session.getPlayerName());
                    System.out.println("Highest Round: " + session.getCurrentRound());
                    System.out.println("Total Prize Saved: ₹" + session.getCurrentPrize());
                    System.out.println("==================================================================");
                    return;
                }
            }
        }
    }

    private static void printRoundHeader(PlayerSession session, Question q) {
        System.out.println("------------------------------------------------------------------");
        System.out.println("ROUND " + session.getCurrentRound() + " OF " + GameEngine.TOTAL_ROUNDS +
                " | Bank: ₹" + session.getCurrentPrize() +
                " | Question Value: ₹" + q.getPrizeAmount());
        System.out.println("------------------------------------------------------------------");
    }

    private static void printQuestionAndOptions(Question q, List<String> hiddenOptions) {
        System.out.println("\n" + q.getQuestionText() + "\n");

        printOption("A", q.getOptA(), hiddenOptions);
        printOption("B", q.getOptB(), hiddenOptions);
        printOption("C", q.getOptC(), hiddenOptions);
        printOption("D", q.getOptD(), hiddenOptions);
    }

    private static void printOption(String letter, String text, List<String> hiddenOptions) {
        if (hiddenOptions != null && hiddenOptions.contains(letter)) {
            System.out.println("  [" + letter + "] ------------ [ELIMINATED] ------------");
        } else {
            System.out.println("  [" + letter + "] " + text);
        }
    }

    private static void printLifelineStatus(GameEngine engine) {
        System.out.println("\nLifelines Available:");
        System.out.println("  [L1] 50:50: " + (engine.isLifelineAvailable(LifelineType.FIFTY_FIFTY) ? "Available" : "USED"));
        System.out.println("  [L2] DOUBLE DIP: " + (engine.isLifelineAvailable(LifelineType.DOUBLE_DIP) ? "Available" : "USED"));
        System.out.println("  [L3] FLIP QUESTION: " + (engine.isLifelineAvailable(LifelineType.FLIP) ? "Available" : "USED"));
        if (engine.getSession().isDoubleDipActive()) {
            System.out.println("  ⭐ DOUBLE DIP IS CURRENTLY ACTIVE! (You have 2 attempts; quitting disabled)");
        }
        System.out.println("------------------------------------------------------------------");
    }

    private static void handleFiftyFifty(GameEngine engine) {
        try {
            @SuppressWarnings("unchecked")
            List<String> hidden = (List<String>) engine.useLifeline(LifelineType.FIFTY_FIFTY);
            System.out.println("\n🔮 50:50 Activated! Eliminating two incorrect options: " + hidden + "\n");
        } catch (IllegalStateException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void handleDoubleDip(GameEngine engine) {
        try {
            engine.useLifeline(LifelineType.DOUBLE_DIP);
            System.out.println("\n🎯 DOUBLE DIP Activated! You have 2 attempts on this question.");
            System.out.println("Note: Quitting is disabled until this question is resolved.\n");
        } catch (IllegalStateException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void handleFlip(GameEngine engine) {
        try {
            engine.useLifeline(LifelineType.FLIP);
            System.out.println("\n🔄 FLIP QUESTION Activated! Here is your replacement question:\n");
        } catch (IllegalStateException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
}
