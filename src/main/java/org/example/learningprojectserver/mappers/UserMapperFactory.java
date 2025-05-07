package org.example.learningprojectserver.mappers;


import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapperFactory {

    private final UserToTeacherMapper userToTeacherMapper;
    private final UserToManagerMapper userToManagerMapper;

    @Autowired
    public UserMapperFactory(
            UserToTeacherMapper userToTeacherMapper, UserToManagerMapper userToManagerMapper) {
        this.userToTeacherMapper = userToTeacherMapper;
        this.userToManagerMapper = userToManagerMapper;
    }

    public <T> Mapper<UserEntity, T> getMapper(Role targetRole) {
        return switch (targetRole) {
            case TEACHER -> (Mapper<UserEntity, T>) userToTeacherMapper;
            case SCHOOLMANAGER -> (Mapper<UserEntity, T>)userToManagerMapper;
            default -> throw new IllegalArgumentException("No mapper found for role: " + targetRole);
        };
    }
}