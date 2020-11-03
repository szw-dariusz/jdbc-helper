package dev.caladh.utils.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

@FunctionalInterface
public interface Setter {

    static Setter noParameters() {
        return statement -> {
        };
    }

    static Setter forParameter(Object parameter) {
        return statement -> statement.setObject(1, parameter);
    }

    static Setter forParameters(Object... parameters) {
        Objects.requireNonNull(parameters);
        return switch (parameters.length) {
            case 0 -> noParameters();
            case 1 -> forParameter(parameters[0]);
            default -> statement -> {
                for (int i = 0; i < parameters.length; i++) {
                    statement.setObject(i + 1, parameters[i]);
                }
            };
        };
    }

    void setup(PreparedStatement statement) throws SQLException;
}
