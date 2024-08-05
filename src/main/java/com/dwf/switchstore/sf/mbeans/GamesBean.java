package com.dwf.switchstore.sf.mbeans;

import com.dwf.switchstore.sf.client.GamesClient;
import com.dwf.switchstore.sf.model.Games;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class GamesBean implements Serializable {

    @Inject // Inject the AuthBean to get the user token
    private AuthBean authBean;

    private GamesClient gamesClient = new GamesClient();
    private List<Games> games;
    private Games game = new Games();
    private boolean isEditing = false;
    private String message;

    @PostConstruct
    public void init() {
        try {
            String token = authBean.getCurrentUser().getToken();
            games = gamesClient.getAllGames(token);

            if (games == null || games.isEmpty()) {
                System.out.println("No games found");
            } else {
                System.out.println("Games loaded: " + games.size());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String createGame() {
        try {
            String token = authBean.getCurrentUser().getToken();

            // Validations
            if (game.getTitle() == null || game.getTitle().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Title cannot be empty", null));
                return null;
            }

            if (game.getGenre() == null || game.getGenre().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Genre cannot be empty", null));
                return null;
            }

            if (game.getPrice() <= 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Price must be a positive number", null));
                return null;
            }

            if (isEditing) {
                gamesClient.updateGame(game.getId(), game, token);
                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("successMessage", "Game updated successfully");
            } else {
                gamesClient.createGame(game, token);
                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("successMessage", "Game created successfully");
            }
            return goBack();
        } catch (IOException | InterruptedException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error saving game:" + e.getMessage(), null));
            return null;
        }
    }

    public void deleteGame(int id) {
        try {
            String token = authBean.getCurrentUser().getToken();
            gamesClient.deleteGame(id, token);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("successMessage", "Game deleted successfully");
            init();
        } catch (IOException | InterruptedException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error deleting game: " + e.getMessage(), null));
        }
    }

    // =============================================================================================================== ||

    public String goToCreateForm() {
        game = new Games();
        isEditing = false;
        return "form?faces-redirect=true";
    }

    public String goToEditForm(int id) {
        try {
            String token = authBean.getCurrentUser().getToken();
            game = gamesClient.getGame(id, token);
            isEditing = true;
            return "form?faces-redirect=true";
        } catch (IOException | InterruptedException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error loading game for editing: " + e.getMessage(), null));
            return null;
        }
    }

    public String goBack() {
        init();
        return "list?faces-redirect=true";
    }

    // =============================================================================================================== ||
    public List<Games> getGames() { return games; }
    public Games getGame() { return game; }
    public void setGame(Games game) { this.game = game; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public boolean getIsEditing() { return isEditing; }
    public void setIsEditing(boolean isEditing) { this.isEditing = isEditing; }
}
