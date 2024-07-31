package com.dwf.switchstore.ws.endpoints;

import com.dwf.switchstore.ws.model.Games;
import com.dwf.switchstore.ws.model.dao.GamesDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.print.attribute.standard.Media;
import java.sql.SQLException;

/**
 * The GamesRest class provides RESTful endpoints for managing games.
 * It supports operations such as fetching all games, fetching a game by ID,
 * creating a new game, updating an existing game, and deleting a game.
 * <p>
 * Base URL: http://localhost:8080/switchstore-1.0-SNAPSHOT/api/games/
 */
@Path("/games")
public class GamesRest {

    private final GamesDAO gamesDAO = new GamesDAO();

    /**
     * Fetches all games from the database.
     *
     * @return a Response object containing the list of all games in JSON format
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGames() throws SQLException {
        return Response.status(200).entity(gamesDAO.fetchAll()).build();
    }

    /**
     * Fetches a game by its ID.
     *
     * @param id the ID of the game to fetch
     * @return a Response object containing the game in JSON format, or a 404 status if not found
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame(@PathParam("id") int id) throws SQLException {
        Games game = gamesDAO.fetchById(id);

        if (game == null || game.getId() == 0) {
            return Response.status(404)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Game not found")
                    .build();
        }

        return Response.status(200).entity(game).build();
    }

    /**
     * Creates a new game.
     *
     * @param game the game object to create
     * @return a Response object containing the created game in JSON format, or a 400 status if validation fails
     * @throws SQLException if a database access error occurs
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGame(Games game) throws SQLException {

        // Validation
        if (!game.isValid()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Empty data for game")
                    .build();
        }

        gamesDAO.insert(game);

        return Response.status(201)
                .header("Access-Control-Allow-Origin", "*")
                .entity(game)
                .build();
    }

    /**
     * Deletes a game by its ID.
     *
     * @param id the ID of the game to delete
     * @return a Response object indicating the result of the deletion
     * @throws SQLException if a database access error occurs
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response deleteGame(@PathParam("id") int id) throws SQLException {
        Games game = gamesDAO.fetchById(id);

        if (game == null || game.getId() == 0) {
            return Response.status(400)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Game apparently doesn't exist")
                    .build();
        }

        gamesDAO.delete(id);

        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .entity("Game deleted")
                .build();
    }

    /**
     * Updates an existing game by its ID.
     *
     * @param id          the ID of the game to update
     * @param updatedGame the updated game object
     * @return a Response object containing the updated game in JSON format, or a 404 status if not found
     * @throws SQLException if a database access error occurs
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updateGame(@PathParam("id") int id, Games updatedGame) throws SQLException {
        Games game = gamesDAO.fetchById(id);

        if (game == null || game.getId() == 0) {
            return Response.status(404)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Game not found")
                    .build();
        }

        game.setTitle(updatedGame.getTitle());
        game.setGenre(updatedGame.getGenre());
        game.setPrice(updatedGame.getPrice());

        if (!game.isValid()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Empty data for game")
                    .build();
        }

        gamesDAO.update(game);

        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .entity(game)
                .build();
    }

}