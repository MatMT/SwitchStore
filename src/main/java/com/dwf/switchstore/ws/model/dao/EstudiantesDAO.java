package com.dwf.switchstore.ws.model.dao;

import com.dwf.switchstore.ws.model.AppConnection;
import com.dwf.switchstore.ws.model.Estudiantes;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class EstudiantesDAO extends AppConnection {

    public void insert(Estudiantes estudiante) throws SQLException {
        connect();
        pstmt = conn.prepareStatement("INSERT INTO estudiantes (dui, nombre_completo, direccion, email, fecha_nacimiento, telefono, sexo) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, estudiante.getDui());
        pstmt.setString(2, estudiante.getNombreCompleto());
        pstmt.setString(3, estudiante.getDireccion());
        pstmt.setString(4, estudiante.getEmail());
        pstmt.setString(5, estudiante.getFechaNacimiento());
        pstmt.setString(6, estudiante.getTelefono());
        pstmt.setString(7, estudiante.getSexo());
        pstmt.executeUpdate();

        ResultSet keys = pstmt.getGeneratedKeys();
        keys.next();
        estudiante.setId(keys.getInt(1));

        close();
    }

    public void update(Estudiantes estudiante) throws SQLException {
        connect();
        pstmt = conn.prepareStatement("UPDATE estudiantes SET dui = ?, nombre_completo = ?, direccion = ?, email = ?, fecha_nacimiento = ?, telefono = ?, sexo = ? WHERE id = ?");
        pstmt.setString(1, estudiante.getDui());
        pstmt.setString(2, estudiante.getNombreCompleto());
        pstmt.setString(3, estudiante.getDireccion());
        pstmt.setString(4, estudiante.getEmail());
        pstmt.setString(5, estudiante.getFechaNacimiento()); // Asegúrate de que la fecha esté en el formato correcto
        pstmt.setString(6, estudiante.getTelefono());
        pstmt.setString(7, estudiante.getSexo());
        pstmt.setInt(8, estudiante.getId());
        pstmt.executeUpdate();
        close();
    }

    public void delete(int id) throws SQLException {
        connect();
        pstmt = conn.prepareStatement("DELETE FROM estudiantes WHERE id = ?");
        pstmt.setInt(1, id);
        pstmt.execute();
        close();
    }

    public ArrayList<Estudiantes> fetchAll() throws SQLException {
        connect();
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM estudiantes");
        ArrayList<Estudiantes> estudiantesList = new ArrayList<>();

        while (rs.next()) {
            Estudiantes estudiante = new Estudiantes();
            estudiante.setId(rs.getInt("id"));
            estudiante.setDui(rs.getString("dui"));
            estudiante.setNombreCompleto(rs.getString("nombre_completo"));
            estudiante.setDireccion(rs.getString("direccion"));
            estudiante.setEmail(rs.getString("email"));
            estudiante.setFechaNacimiento(String.valueOf(rs.getDate("fecha_nacimiento").toLocalDate())); // Asegúrate de que la fecha esté en el formato correcto
            estudiante.setTelefono(rs.getString("telefono"));
            estudiante.setSexo(rs.getString("sexo"));
            estudiantesList.add(estudiante);
        }

        close();
        return estudiantesList;
    }

    public Estudiantes fetchById(int id) throws SQLException {
        connect();
        pstmt = conn.prepareStatement("SELECT * FROM estudiantes WHERE id = ?");
        pstmt.setInt(1, id);
        rs = pstmt.executeQuery();
        Estudiantes estudiante = new Estudiantes();

        if (rs.next()) {
            estudiante.setId(rs.getInt("id"));
            estudiante.setDui(rs.getString("dui"));
            estudiante.setNombreCompleto(rs.getString("nombre_completo"));
            estudiante.setDireccion(rs.getString("direccion"));
            estudiante.setEmail(rs.getString("email"));
            estudiante.setFechaNacimiento(String.valueOf(rs.getDate("fecha_nacimiento").toLocalDate())); // Asegúrate de que la fecha esté en el formato correcto
            estudiante.setTelefono(rs.getString("telefono"));
            estudiante.setSexo(rs.getString("sexo"));
        }

        close();
        return estudiante;
    }
}
