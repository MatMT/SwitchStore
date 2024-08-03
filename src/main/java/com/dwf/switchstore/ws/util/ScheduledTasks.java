package com.dwf.switchstore.ws.util;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Singleton
@Startup
public class ScheduledTasks {

    /**
     * This method is used to clean up the token blacklist every hour
     */
    @Schedule(hour = "*", minute = "0", persistent = false)
    public void cleanupTokenBlacklist() {
        JwtUtil.cleanupBlacklist();
        System.out.println("Token blacklist has been cleaned up successfully!");
    }
}