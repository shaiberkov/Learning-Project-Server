package org.example.learningprojectserver.mappers;

import java.util.function.Function;

public interface Mapper<I, O> extends Function<I, O> {
    @Override
    O apply(I input);
}
