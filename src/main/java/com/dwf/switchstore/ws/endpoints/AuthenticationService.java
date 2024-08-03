package com.dwf.switchstore.ws.endpoints;

import com.dwf.switchstore.ws.model.Users;
import com.dwf.switchstore.ws.model.dao.UsersDAO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

import com.dwf.switchstore.ws.util.Validator;

/**
 * The AuthenticationService class provides RESTful endpoints for authenticating and registering users.
 * It supports operations such as authenticating a user and registering a new user.
 * <p>
 * Base URL: http://localhost:8080/switchstore-1.0-SNAPSHOT/api/auth/
 */
@Path("/auth")
public class AuthenticationService {

    private UsersDAO usersDAO = new UsersDAO();

    /**
     * This method is used to authenticate a user
     * @param loginUser the user to authenticate
     * @return a response indicating the result of the authentication
     * @throws SQLException if a database access error occurs
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Users loginUser) throws SQLException {

        // Validate user input
        if (Validator.validateUser(loginUser, false)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Username and password fields are required\"}").build();
        }

        try {
            Users user = usersDAO.fetchByUsername(loginUser.getUsername());
            if (user != null && user.getPassword().equals(loginUser.getPassword())) {
                // SOON WILL USE AUTH TOKENS SYSTEM
                return Response.ok("{\"message\": \"Login successful\", \"userId\": " + user.getId() + "}").build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("{\"message\": \"Invalid credentials\"}").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\": \"Database error: " + e.getMessage() + "\"}").build();
        }
    }

    /**
     * This method is used to register a new user
     * @param user the user to register
     * @return a response indicating the result of the registration
     * @throws SQLException if a database access error occurs
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(Users user) throws SQLException {

        // Validate user input
        if (Validator.validateUser(user, true)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\": \"Username, password, and name fields are required\"}").build();
        }

        try {
            // Check if username already exists
            Users existingUser = usersDAO.fetchByUsername(user.getUsername());
            if (existingUser != null) {
                return Response.status(Response.Status.CONFLICT).entity("{\"message\": \"Username already exists\"}").build();
            }

            // Insert new user
            usersDAO.insert(user);
            return Response.status(Response.Status.CREATED).entity("{\"message\": \"User registered successfully\", \"id\": " + user.getId() + "}").build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\": \"Database error: " + e.getMessage() + "\"}").build();
        }
    }
}
