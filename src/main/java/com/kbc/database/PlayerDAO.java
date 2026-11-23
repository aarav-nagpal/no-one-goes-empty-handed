package com.kbc.database;

import com.kbc.models.PlayerResult;
import com.kbc.models.PlayerSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Player game session records.
 */
public class PlayerDAO {

    private final DatabaseManager dbManager;

    public PlayerDAO() {
        this(DatabaseManager.getInstance());
    }

    public PlayerDAO(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Saves a completed or terminated player session result to the database.
     *
     * @param session the active PlayerSession containing player stats
     * @param status the final status of the player (e.g. "WON", "LOST", "QUIT")
     * @return true if saved successfully, false otherwise
     */
    public boolean savePlayerResult(PlayerSession session, String status) {
        String sql = "INSERT INTO players (player_name, highest_round, prize_won, status) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = dbManager.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, session.getPlayerName());
                stmt.setInt(2, session.getCurrentRound());
                stmt.setLong(3, session.getCurrentPrize());
                stmt.setString(4, status);
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error saving player result: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all recorded player results, ordered by most recent first.
     * Useful for displaying in the Admin GUI JTable.
     *
     * @return List of PlayerResult objects
     */
    public List<PlayerResult> getAllPlayerResults() {
        List<PlayerResult> results = new ArrayList<>();
        String sql = "SELECT id, player_name, highest_round, prize_won, status, played_at FROM players ORDER BY played_at DESC, id DESC";
        try {
            Connection conn = dbManager.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PlayerResult result = new PlayerResult(
                            rs.getInt("id"),
                            rs.getString("player_name"),
                            rs.getInt("highest_round"),
                            rs.getLong("prize_won"),
                            rs.getString("status"),
                            rs.getTimestamp("played_at")
                    );
                    results.add(result);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all player results: " + e.getMessage());
        }
        return results;
    }

    /**
     * Returns the player results as a 2D Object array for easy consumption by Swing DefaultTableModel / JTable.
     *
     * @return 2D array of row objects: [id, player_name, highest_round, prize_won, status, played_at]
     */
    public Object[][] getAllPlayerResultsAsRowData() {
        List<PlayerResult> list = getAllPlayerResults();
        Object[][] data = new Object[list.size()][6];
        for (int i = 0; i < list.size(); i++) {
            data[i] = list.get(i).toTableRow();
        }
        return data;
    }

    /**
     * Standard table column names for the Admin JTable.
     */
    public static String[] getTableColumnNames() {
        return new String[]{"ID", "Player Name", "Highest Round", "Prize Won", "Status", "Played At"};
    }
}
