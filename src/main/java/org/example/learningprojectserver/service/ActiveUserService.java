package org.example.learningprojectserver.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class ActiveUserService {
    private final ConcurrentMap<String, Boolean> activeUsers = new ConcurrentHashMap<>();

    public void connectUser(String userId) {
        activeUsers.put(userId, true);
    }

    public void disconnectUser(String userId) {
        activeUsers.remove(userId);
    }

    public boolean isUserActive(String userId) {
        return activeUsers.containsKey(userId);
    }

    public ConcurrentMap<String, Boolean> getActiveUsers() {
        return activeUsers;
    }

}
