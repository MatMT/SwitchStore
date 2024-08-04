package com.dwf.switchstore.ws.endpoints;

import com.dwf.switchstore.ws.model.Estudiantes;
import com.dwf.switchstore.ws.model.dao.EstudiantesDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

/**
 * The EstudiantesRest class provides RESTful endpoints for managing estudiantes.
 * It supports operations such as fetching all students, fetching a student by ID,
 * creating a new student, updating an existing student, and deleting a student.
 * <p>
 * Base URL: http://localhost:8080/switchstore-1.0-SNAPSHOT/api/estudiantes/
 */
@Path("/estudiantes")
public class EstudiantesRest {

    private final EstudiantesDAO estudiantesDAO = new EstudiantesDAO();

    /**
     * Fetches all students from the database.
     *
     * @return a Response object containing the list of all students in JSON format
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEstudiantes() throws SQLException {
        return Response.status(200).entity(estudiantesDAO.fetchAll()).build();
    }

    /**
     * Fetches a student by its ID.
     *
     * @param id the ID of the student to fetch
     * @return a Response object containing the student in JSON format, or a 404 status if not found
     * @throws SQLException if a database access error occurs
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEstudiante(@PathParam("id") int id) throws SQLException {
        Estudiantes estudiante = estudiantesDAO.fetchById(id);

        if (estudiante == null || estudiante.getId() == 0) {
            return Response.status(404)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Estudiante not found")
                    .build();
        }

        return Response.status(200).entity(estudiante).build();
    }

    /**
     * Creates a new student.
     *
     * @param estudiante the student object to create
     * @return a Response object containing the created student in JSON format, or a 400 status if validation fails
     * @throws SQLException if a database access error occurs
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEstudiante(Estudiantes estudiante) throws SQLException {

        estudiantesDAO.insert(estudiante);

        return Response.status(201)
                .header("Access-Control-Allow-Origin", "*")
                .entity(estudiante)
                .build();
    }

    /**
     * Deletes a student by its ID.
     *
     * @param id the ID of the student to delete
     * @return a Response object indicating the result of the deletion
     * @throws SQLException if a database access error occurs
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response deleteEstudiante(@PathParam("id") int id) throws SQLException {
        Estudiantes estudiante = estudiantesDAO.fetchById(id);

        if (estudiante == null || estudiante.getId() == 0) {
            return Response.status(400)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Estudiante apparently doesn't exist")
                    .build();
        }

        estudiantesDAO.delete(id);

        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .entity("Estudiante deleted")
                .build();
    }

    /**
     * Updates an existing student by its ID.
     *
     * @param id            the ID of the student to update
     * @param updatedEstudiante the updated student object
     * @return a Response object containing the updated student in JSON format, or a 404 status if not found
     * @throws SQLException if a database access error occurs
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updateEstudiante(@PathParam("id") int id, Estudiantes updatedEstudiante) throws SQLException {
        Estudiantes estudiante = estudiantesDAO.fetchById(id);

        if (estudiante == null || estudiante.getId() == 0) {
            return Response.status(404)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Estudiante not found")
                    .build();
        }

        estudiante.setDui(updatedEstudiante.getDui());
        estudiante.setNombreCompleto(updatedEstudiante.getNombreCompleto());
        estudiante.setDireccion(updatedEstudiante.getDireccion());
        estudiante.setEmail(updatedEstudiante.getEmail());
        estudiante.setFechaNacimiento(updatedEstudiante.getFechaNacimiento());
        estudiante.setTelefono(updatedEstudiante.getTelefono());
        estudiante.setSexo(updatedEstudiante.getSexo());

        estudiantesDAO.update(estudiante);

        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .entity(estudiante)
                .build();
    }

}
