package com.dwf.switchstore.ws.model.dao;

import com.dwf.switchstore.ws.model.AppConnection;
import com.dwf.switchstore.ws.model.Games;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The GamesDAO class provides methods to perform CRUD operations on the games table in the database.
 */
public class GamesDAO extends AppConnection {

    /**
     * Inserts a new game into the games table.
     *
     * @param game the game to insert
     * @throws SQLException if a database access error occurs
     */
    public void insert(Games game) throws SQLException {
        connect();
        pstmt = conn.prepareStatement("INSERT INTO games (title, genre, price) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, game.getTitle());
        pstmt.setString(2, game.getGenre());
        pstmt.setDouble(3, game.getPrice());
        pstmt.executeUpdate();

        ResultSet keys = pstmt.getGeneratedKeys();

        keys.next();
        game.setId(keys.getInt(1));

        close();
    }

    /**
     * Updates an existing game in the games table.
     *
     * @param game the game to update
     * @throws SQLException if a database access error occurs
     */
    public void update(Games game) throws SQLException {
        connect();
        pstmt = conn.prepareStatement("UPDATE games SET title = ?, genre = ?, price = ? WHERE id = ?");
        pstmt.setString(1, game.getTitle());
        pstmt.setString(2, game.getGenre());
        pstmt.setDouble(3, game.getPrice());
    }

    /**
     * Deletes a game from the games table by its id.
     *
     * @param id the id of the game to delete
     * @throws SQLException if a database access error occurs
     */
    public void delete(int id) throws SQLException {
        connect();
        pstmt = conn.prepareStatement("DELETE FROM games WHERE id = ?");
        pstmt.setInt(1, id);
        pstmt.execute();
        close();
    }

    /**
     * Fetches all games from the games table.
     *
     * @return a list of all games
     * @throws SQLException if a database access error occurs
     */
    public ArrayList<Games> fetchAll() throws SQLException {
        connect();
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM games");
        ArrayList<Games> gamesList = new ArrayList<>();

        while (rs.next()) {
            Games game = new Games();
            game.setId(rs.getInt("id"));
            game.setTitle(rs.getString("title"));
            game.setGenre(rs.getString("genre"));
            game.setPrice(rs.getDouble("price"));
            gamesList.add(game);
        }

        close();
        return gamesList;
    }

    /**
     * Fetches a game from the games table by its id.
     *
     * @param id the id of the game to fetch
     * @return the game with the specified id
     * @throws SQLException if a database access error occurs
     */
    public Games fetchById(int id) throws SQLException {
        connect();

        pstmt = conn.prepareStatement("SELECT * FROM games WHERE id = ?");
        pstmt.setInt(1, id);
        rs = pstmt.executeQuery();
        Games game = new Games();

        if (rs.next()) {
            game.setId(rs.getInt("id"));
            game.setTitle(rs.getString("title"));
            game.setGenre(rs.getString("genre"));
            game.setPrice((rs.getDouble("price")));
        }

        close();
        return game;
    }
}