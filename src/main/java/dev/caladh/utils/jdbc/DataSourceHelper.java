package dev.caladh.utils.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public final class DataSourceHelper implements Operations {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DataSource dataSource;

    private DataSourceHelper(DataSource dataSource) {
        this.dataSource = requireNonNull(dataSource, "data source");
    }

    public static DataSourceHelper create(DataSource dataSource) {
        return new DataSourceHelper(dataSource);
    }

    @Override
    public <T> Optional<T> fetchOne(String sql, RowMapper<T> mapper) {
        Handler<Optional<T>> handler = Handler.returning(e -> Optional.empty());
        return fetch(sql, Setter.noParameters(), Extractor.extractOne(mapper, handler), handler);
    }

    @Override
    public <T> Optional<T> fetchOne(String sql, RowMapper<T> mapper, Object parameter) {
        Handler<Optional<T>> handler = Handler.returning(e -> Optional.empty());
        return fetch(sql, Setter.forParameter(parameter), Extractor.extractOne(mapper, handler), handler);
    }

    @Override
    public <T> Optional<T> fetchOne(String sql, RowMapper<T> mapper, Object... parameters) {
        Handler<Optional<T>> handler = Handler.returning(e -> Optional.empty());
        return fetch(sql, Setter.forParameters(parameters), Extractor.extractOne(mapper, handler), handler);
    }

    @Override
    public <T> List<T> fetchMany(String sql, RowMapper<T> mapper) {
        Handler<List<T>> handler = Handler.returning(e -> Collections.emptyList());
        return fetch(sql, Setter.noParameters(), Extractor.extractMany(mapper, handler), handler);
    }

    @Override
    public <T> List<T> fetchMany(String sql, RowMapper<T> mapper, Object parameter) {
        Handler<List<T>> handler = Handler.returning(e -> Collections.emptyList());
        return fetch(sql, Setter.forParameter(parameter), Extractor.extractMany(mapper, handler), handler);
    }

    @Override
    public <T> List<T> fetchMany(String sql, RowMapper<T> mapper, Object... parameters) {
        Handler<List<T>> handler = Handler.returning(e -> Collections.emptyList());
        return fetch(sql, Setter.forParameters(parameters), Extractor.extractMany(mapper, handler), handler);
    }

    @Override
    public <T> T fetch(String sql, Setter setter, Extractor<T> extractor, Handler<T> handler) {
        log.debug("Executing query:\n {}", sql);
        try (var connection = dataSource.getConnection(); var statement = connection.prepareStatement(sql)) {
            setter.setup(statement);
            try (var resultSet = statement.executeQuery()) {
                return extractor.process(resultSet);
            }
        } catch (SQLException e) {
            log(e);
            return handler.handle(e);
        }
    }

    @Override
    public int update(String sql) {
        return update(sql, Setter.noParameters(), Handler.returning(e -> 0));
    }

    @Override
    public int update(String sql, Object parameter) {
        return update(sql, Setter.forParameter(parameter), Handler.returning(e -> 0));
    }

    @Override
    public int update(String sql, Object... parameters) {
        return update(sql, Setter.forParameters(parameters), Handler.returning(e -> 0));
    }

    @Override
    public int update(String sql, Setter setter, Handler<Integer> handler) {
        log.debug("Executing update:\n {}", sql);
        try (var connection = dataSource.getConnection(); var statement = connection.prepareStatement(sql)) {
            setter.setup(statement);
            return statement.executeUpdate();
        } catch (SQLException e) {
            log(e);
            return handler.handle(e);
        }
    }

    public void log(SQLException e) {
        log.error("Error executing: {}", e.getMessage());
        log.debug("Details:", e);
    }
}
