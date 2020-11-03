package dev.caladh.utils.jdbc;

import java.sql.SQLException;
import java.util.function.Function;

@FunctionalInterface
public interface Handler<T> {

    static <T> Handler<T> returning(Function<SQLException, T> handler) {
        return handler::apply;
    }

    static <T> Handler<T> throwing(Function<SQLException, ? extends RuntimeException> exception) {
        return e -> {
            throw exception.apply(e);
        };
    }

    T handle(SQLException e);
}
