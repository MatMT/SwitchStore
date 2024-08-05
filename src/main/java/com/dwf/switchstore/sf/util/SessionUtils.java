package com.dwf.switchstore.sf.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@ApplicationScoped
public class SessionUtils implements Serializable {

    public void add(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }

    public Object get(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    public void remove(String key) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(key);
    }
}