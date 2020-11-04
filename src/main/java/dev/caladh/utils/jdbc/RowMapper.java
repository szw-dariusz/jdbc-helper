package dev.caladh.utils.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper of single ResultSet row to object.
 *
 * @param <T> Type of produced object.
 * @author Dariusz Szwarc
 */
@FunctionalInterface
public interface RowMapper<T> {

    /**
     * Maps a single row of result set to an object and then returns it.
     *
     * @param resultSet Result set, positioned at row to be mapped.
     * @return Mapped object.
     * @throws SQLException when result set raises an exception during processing.
     */
    T map(ResultSet resultSet) throws SQLException;
}
