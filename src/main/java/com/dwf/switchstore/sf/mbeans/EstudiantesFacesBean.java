package com.dwf.switchstore.sf.mbeans;

import com.dwf.switchstore.sf.client.EstudiantesClient;
import com.dwf.switchstore.sf.model.Estudiantes;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class EstudiantesFacesBean implements Serializable {

    private EstudiantesClient estudiantesClient = new EstudiantesClient();
    private List<Estudiantes> estudiantes;

    @PostConstruct
    public void init() {
        try {
            estudiantes = estudiantesClient.getAllEstudiantes();

            if (estudiantes == null || estudiantes.isEmpty()) {
                System.out.println("No students found");
            } else {
                System.out.println("Students loaded: " + estudiantes.size());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Estudiantes> getEstudiantes() { return estudiantes; }
}
