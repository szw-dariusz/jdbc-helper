package dev.caladh.utils.jdbc;

import java.util.List;
import java.util.Optional;

public interface Operations {

    <T> Optional<T> fetchOne(String sql, RowMapper<T> mapper);

    <T> Optional<T> fetchOne(String sql, RowMapper<T> mapper, Object parameter);

    <T> Optional<T> fetchOne(String sql, RowMapper<T> mapper, Object... parameters);

    <T> List<T> fetchMany(String sql, RowMapper<T> mapper);

    <T> List<T> fetchMany(String sql, RowMapper<T> mapper, Object parameter);

    <T> List<T> fetchMany(String sql, RowMapper<T> mapper, Object... parameters);

    <T> T fetch(String sql, Setter setter, Extractor<T> extractor, Handler<T> handler);

    int update(String sql);

    int update(String sql, Object parameter);

    int update(String sql, Object... parameters);

    int update(String sql, Setter setter, Handler<Integer> handler);
}
