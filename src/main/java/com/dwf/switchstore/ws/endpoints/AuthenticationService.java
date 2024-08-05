package com.dwf.switchstore.ws.endpoints;

import com.dwf.switchstore.ws.model.Users;
import com.dwf.switchstore.ws.model.dao.UsersDAO;
import com.dwf.switchstore.ws.util.JwtUtil;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
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
     *
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
                String token = JwtUtil.generateToken(user.getUsername(), user.getId());

                // Create a JSON object with user data (excluding password)
                JsonObject userJson = Json.createObjectBuilder()
                        .add("id", user.getId())
                        .add("username", user.getUsername())
                        .add("name", user.getName())
                        .add("created_at", user.getCreated_at())
                        .build();

                // Return a JSON object with the token and user data
                JsonObject responseJson = Json.createObjectBuilder()
                        .add("message", "Login successful")
                        .add("token", token)
                        .add("user", userJson)
                        .build();
                return Response.ok(responseJson.toString()).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("{\"message\": \"Invalid credentials\"}").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\": \"Database error: " + e.getMessage() + "\"}").build();
        }
    }

    /**
     * This method is used to log out a user
     *
     * @param authHeader the Authorization header containing the JWT token
     * @return a response indicating the result of the logout
     */
    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@HeaderParam("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\": \"Missing or invalid Authorization header\"}").build();
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix
        if (JwtUtil.validateToken(token)) {
            JwtUtil.blacklistToken(token);
            return Response.ok("{\"message\": \"Logout successful\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"message\": \"Invalid or expired token\"}").build();
        }
    }

    /**
     * This method is used to register a new user
     *
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

    /**
     * This method is used to validate a JWT token
     *
     * @param jsonInput the JSON object containing the token to validate
     * @return a response indicating the result of the token validation
     */
    @POST
    @Path("/validate-token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateToken(JsonObject jsonInput) {

        if (jsonInput == null || jsonInput.isEmpty() || !jsonInput.containsKey("token") || jsonInput.getString("token", null) == null || jsonInput.getString("token", null).isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\": \"Token is required\"}").build();
        }

        if (JwtUtil.validateToken(jsonInput.getString("token", null))) {
            return Response.ok("{\"message\": \"Token is valid\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"message\": \"Invalid or expired token\"}").build();
        }
    }

    /**
     * This method is used to access a protected endpoint
     *
     * @param authHeader the Authorization header containing the JWT token
     * @return a response indicating the result of the protected endpoint access
     */
    @GET
    @Path("/protected")
    @Produces(MediaType.APPLICATION_JSON)
    public Response protectedEndpoint(@HeaderParam("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"message\": \"Missing or invalid Authorization header\"}").build();
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix
        if (JwtUtil.validateToken(token)) {
            String username = JwtUtil.getUsernameFromToken(token);
            return Response.ok("{\"message\": \"Access granted to protected resource\", \"username\": \"" + username + "\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"message\": \"Invalid or expired token\"}").build();
        }
    }
}
