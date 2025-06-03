package org.example.learningprojectserver.projection;

public interface UserCredentialsProjection {
    String getSalt();
    String getPasswordHash();
    String getPhoneNumber();
}
