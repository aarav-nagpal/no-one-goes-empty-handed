package com.kbc.models;

import java.sql.Timestamp;

/**
 * Model representing a player's game record stored in the database.
 * Used for querying player history and populating the Admin JTable.
 */
public class PlayerResult {

    private final int id;
    private final String playerName;
    private final int highestRound;
    private final long prizeWon;
    private final String status;
    private final Timestamp playedAt;

    public PlayerResult(int id, String playerName, int highestRound, long prizeWon, String status, Timestamp playedAt) {
        this.id = id;
        this.playerName = playerName;
        this.highestRound = highestRound;
        this.prizeWon = prizeWon;
        this.status = status;
        this.playedAt = playedAt;
    }

    public PlayerResult(String playerName, int highestRound, long prizeWon, String status) {
        this(0, playerName, highestRound, prizeWon, status, new Timestamp(System.currentTimeMillis()));
    }

    public int getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getHighestRound() {
        return highestRound;
    }

    public long getPrizeWon() {
        return prizeWon;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getPlayedAt() {
        return playedAt;
    }

    /**
     * Converts this result into an Object array suitable for a Swing JTable row.
     */
    public Object[] toTableRow() {
        return new Object[]{id, playerName, highestRound, prizeWon, status, playedAt};
    }

    @Override
    public String toString() {
        return "PlayerResult{" +
                "id=" + id +
                ", playerName='" + playerName + '\'' +
                ", highestRound=" + highestRound +
                ", prizeWon=" + prizeWon +
                ", status='" + status + '\'' +
                ", playedAt=" + playedAt +
                '}';
    }
}
