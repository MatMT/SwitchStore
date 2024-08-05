package com.dwf.switchstore.ws.util;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/**
 * This class is used to listen for session creation and destruction events.
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    /**
     * This method is called when a session is created.
     * @param se The HttpSessionEvent object
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session created: " + se.getSession().getId());
    }

    /**
     * This method is called when a session is destroyed.
     * @param se The HttpSessionEvent object
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("Session destroyed: " + se.getSession().getId());
    }
}