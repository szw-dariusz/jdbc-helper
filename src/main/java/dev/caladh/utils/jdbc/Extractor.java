package dev.caladh.utils.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Extractor of complete resultSet to object or a collection of objects.
 *
 * @param <T> Type of produced object.
 * @author Dariusz Szwarc
 */
@FunctionalInterface
public interface Extractor<T> {

    /**
     * Extractor that processes a single row from result set. The processing is done after advancing the result set
     * by one row.
     *
     * @param mapper  Mapper used to process the result set row.
     * @param handler Handler invoked when the result set is empty or is positioned at last row.
     * @param <T>     Type of mapped object.
     * @return Mapped object.
     */
    static <T> Extractor<Optional<T>> extractOne(RowMapper<T> mapper, Handler<Optional<T>> handler) {
        return rs -> {
            if (!rs.next()) {
                return handler.handle(null);
            }
            return Optional.ofNullable(mapper.map(rs));
        };
    }

    /**
     * Extractor that processes multiple rows from result set. The processing is done after advancing the result set
     * by one row.
     *
     * @param mapper  Mapper used to process the result set rows.
     * @param handler Handler invoked when the result set is empty or is positioned at last row.
     * @param <T>     Type of mapped object.
     * @return Mapped object.
     */
    static <T> Extractor<List<T>> extractMany(RowMapper<T> mapper, Handler<List<T>> handler) {
        return rs -> {
            if (!rs.next()) {
                return handler.handle(null);
            }
            List<T> results = new ArrayList<>();
            do {
                results.add(mapper.map(rs));
            } while (rs.next());
            return results;
        };
    }

    /**
     * Processes resultSet.
     *
     * @param resultSet Processed result set.
     * @return Object extracted from result set.
     * @throws SQLException when result set raises an exception during processing.
     */
    T process(ResultSet resultSet) throws SQLException;
}
