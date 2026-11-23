package com.kbc.models;

/**
 * Tracks the state of an active player session in the game.
 */
public class PlayerSession {

    private String playerName;
    private int currentRound;
    private long currentPrize;
    private boolean usedFiftyFifty;
    private boolean usedDoubleDip;
    private boolean usedFlip;
    private boolean doubleDipActive;

    public PlayerSession() {
        this.currentRound = 1;
        this.currentPrize = 0L;
        this.usedFiftyFifty = false;
        this.usedDoubleDip = false;
        this.usedFlip = false;
        this.doubleDipActive = false;
    }

    public PlayerSession(String playerName) {
        this();
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public long getCurrentPrize() {
        return currentPrize;
    }

    public void setCurrentPrize(long currentPrize) {
        this.currentPrize = currentPrize;
    }

    public boolean isUsedFiftyFifty() {
        return usedFiftyFifty;
    }

    public void setUsedFiftyFifty(boolean usedFiftyFifty) {
        this.usedFiftyFifty = usedFiftyFifty;
    }

    public boolean isUsedDoubleDip() {
        return usedDoubleDip;
    }

    public void setUsedDoubleDip(boolean usedDoubleDip) {
        this.usedDoubleDip = usedDoubleDip;
    }

    public boolean isUsedFlip() {
        return usedFlip;
    }

    public void setUsedFlip(boolean usedFlip) {
        this.usedFlip = usedFlip;
    }

    public boolean isDoubleDipActive() {
        return doubleDipActive;
    }

    public void setDoubleDipActive(boolean doubleDipActive) {
        this.doubleDipActive = doubleDipActive;
    }

    @Override
    public String toString() {
        return "PlayerSession{" +
                "playerName='" + playerName + '\'' +
                ", currentRound=" + currentRound +
                ", currentPrize=" + currentPrize +
                ", usedFiftyFifty=" + usedFiftyFifty +
                ", usedDoubleDip=" + usedDoubleDip +
                ", usedFlip=" + usedFlip +
                ", doubleDipActive=" + doubleDipActive +
                '}';
    }
}
