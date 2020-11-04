package dev.caladh.utils.jdbc;

import java.sql.SQLException;
import java.util.function.Function;

/**
 * Handler of exceptional situation
 *
 * @param <T> Type of produced object.
 * @author Dariusz Szwarc
 */
@FunctionalInterface
public interface Handler<T> {

    /**
     * Returns a Handler that handles errors by returning default result.
     *
     * @param <T>     Type of produced object.
     * @param handler Function that accepts an SQLException and returns a default result.
     * @return Object obtained from the Supplier.
     */
    static <T> Handler<T> returning(Function<SQLException, T> handler) {
        return handler::apply;
    }

    /**
     * Returns a Handler that handles errors by throwing a {@link RuntimeException}.
     *
     * @param exception Supplier of RuntimeException to be thrown.
     * @param <T>       Type of produced object.
     * @return Nothing as this method is expected to throw an exception.
     */
    static <T> Handler<T> throwing(Function<SQLException, ? extends RuntimeException> exception) {
        return e -> {
            throw exception.apply(e);
        };
    }

    /**
     * Handle an exceptional situation. This method may throw RuntimeExceptions instead of returning values.
     *
     * @param e Exception that is captured by handler and requires handling. May be null.
     * @return Default object.
     */
    T handle(SQLException e);
}
