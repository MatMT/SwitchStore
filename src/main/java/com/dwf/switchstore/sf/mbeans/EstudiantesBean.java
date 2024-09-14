package com.dwf.switchstore.sf.mbeans;

import com.dwf.switchstore.sf.client.EstudiantesClient;
import com.dwf.switchstore.sf.model.Estudiantes;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.IOException;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Named
@SessionScoped
public class EstudiantesBean implements Serializable {

    private EstudiantesClient estudiantesClient = new EstudiantesClient();
    private List<Estudiantes> estudiantes;
    private Estudiantes estudiante = new Estudiantes();
    private boolean isEditing = false;
    private String message;

    public String createEstudiante() {
        try {
            // Validations
            if (estudiante.getDui() == null || estudiante.getDui().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "DUI cannot be empty", null));
                return null;
            }

            if (estudiante.getNombreCompleto() == null || estudiante.getNombreCompleto().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Full name cannot be empty", null));
                return null;
            }

            if (estudiante.getDireccion() == null || estudiante.getDireccion().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Address cannot be empty", null));
                return null;
            }

            if (estudiante.getEmail() == null || estudiante.getEmail().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email cannot be empty", null));
                return null;
            }

            if (estudiante.getFechaNacimiento() == null || estudiante.getFechaNacimiento().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Birthdate cannot be empty", null));
                return null;
            }

            if (estudiante.getTelefono() == null || estudiante.getTelefono().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Phone number cannot be empty", null));
                return null;
            }

            if (estudiante.getSexo() == null || estudiante.getSexo().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gender cannot be empty", null));
                return null;
            }

            if (isEditing) {
                estudiantesClient.updateEstudiante(estudiante.getId(), estudiante);
                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("successMessage", "Estudiante actualizado exitosamente");
            } else {
                estudiantesClient.createEstudiante(estudiante);
                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("successMessage", "Estudiante creado exitosamente");
            }
            return goList();
        } catch (IOException | InterruptedException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error saving student: " + e.getMessage(), null));
            return null;
        }
    }

    public void deleteEstudiante(int id) {
        try {
            estudiantesClient.deleteEstudiante(id);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Estudiante eliminado correctamente"));
            PrimeFaces.current().executeScript("setTimeout(function(){ window.location.href = window.location.href; }, 3000);");
        } catch (IOException | InterruptedException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al eliminar estudiante: " + e.getMessage()));
        }
    }

    public String goToCreateForm() {
        estudiante = new Estudiantes();
        isEditing = false;
        return "form?faces-redirect=true";
    }

    public String goToEditForm(int id) {
        try {
            estudiante = estudiantesClient.getEstudiante(id);
            isEditing = true;
            return "form?faces-redirect=true";
        } catch (IOException | InterruptedException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error loading student for editing: " + e.getMessage(), null));
            return null;
        }
    }

    public String goBack() {
        return "index?faces-redirect=true";
    }

    public String goList() {
        return "list?faces-redirect=true";
    }

    public List<Estudiantes> getEstudiantes() {
        return estudiantes;
    }

    public Estudiantes getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiantes estudiante) {
        this.estudiante = estudiante;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }
}
