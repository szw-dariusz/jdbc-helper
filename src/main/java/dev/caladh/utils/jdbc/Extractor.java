package dev.caladh.utils.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface Extractor<T> {

    static <T> Extractor<Optional<T>> extractOne(RowMapper<T> mapper, Handler<Optional<T>> handler) {
        return rs -> {
            if (!rs.next()) {
                return handler.handle(null);
            }
            return Optional.ofNullable(mapper.map(rs));
        };
    }

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

    T process(ResultSet resultSet) throws SQLException;
}
