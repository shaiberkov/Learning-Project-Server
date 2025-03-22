package org.example.learningprojectserver.service;

import org.example.learningprojectserver.entities.UserStatisticsEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class ActiveUserService {
    private final ConcurrentMap<String, Boolean> activeUsers = new ConcurrentHashMap<>();

    public void connectUser(String userName) {
        activeUsers.put(userName, true);
    }

    public void disconnectUser(String userName) {
        activeUsers.remove(userName);
    }

    public boolean isUserActive(String userName) {
        return activeUsers.containsKey(userName);
    }

    public ConcurrentMap<String, Boolean> getActiveUsers() {
        return activeUsers;
    }

}
