package org.example.learningprojectserver.mappers;

import org.example.learningprojectserver.entities.SchoolEntity;
import org.example.learningprojectserver.entities.UserEntity;

public interface EntityMapper<T> {
    T map(UserEntity userEntity);

    default EntityMapper<T> setSchool(SchoolEntity school) {
        return this;
    }
}
