package com.dwf.switchstore.sf.mbeans;

import com.dwf.switchstore.sf.client.AuthClient;
import com.dwf.switchstore.sf.model.Users;
import com.dwf.switchstore.sf.util.SessionUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

/**
 * This class is a Managed Bean that handles authentication
 */
@Named
@ApplicationScoped
public class AuthBean implements Serializable {

    @Inject
    private AuthClient authClient;

    @Inject
    private SessionUtils sessionUtils;

    private Users user = new Users();
    private String loginUsername;
    private String loginPassword;

    /**
     * Login to the application
     * @return the next page to navigate to after login
     */
    public String login() {
        try {
            Users loggedInUser = authClient.login(loginUsername, loginPassword);
            sessionUtils.add("currentUser", loggedInUser);
            return "/games/list?faces-redirect=true";
        } catch (IOException | InterruptedException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Login failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Register a new user
     * @return the next page to navigate to after registration
     */
    public String register() {
        try {
            authClient.register(user);
            addMessage(FacesMessage.SEVERITY_INFO, "Registration successful. You can now login.");
            return "/auth/login?faces-redirect=true";
        } catch (IOException | InterruptedException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Registration failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Logout from the application
     * @return the login page
     */
    public String logout() {
        sessionUtils.remove("currentUser");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/auth/login?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return sessionUtils.get("currentUser") != null;
    }

    public Users getCurrentUser() {
        return (Users) sessionUtils.get("currentUser");
    }

    private void addMessage(FacesMessage.Severity severity, String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, null));
    }

    // Getters and setters
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
