/*
 * Copyright (c) 2020 Dariusz Szwarc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.siny.utils.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Class for performing basic operations on {@link DataSource}.
 *
 * @author Dariusz Szwarc
 */
public final class DataSourceHelper implements Operations {

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Data source that will be used to obtain connections.
     */
    private final DataSource dataSource;

    /**
     * Creates instance of DataSourceHelper.
     *
     * @param dataSource Data source that will be used to obtain connections. Must not be null.
     */
    private DataSourceHelper(DataSource dataSource) {
        this.dataSource = requireNonNull(dataSource, "data source");
    }

    /**
     * Creates a DataSourceHelper.
     * @param dataSource Data source that will be wrapped by the DataSourceHelper.
     * @return New DataSourceHelper object.
     */
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

    /**
     * Allows to log SQL Exceptions using default logger.
     * @param e Exception that will be logged.
     */
    public void log(SQLException e) {
        log.error("Error executing: {}", e.getMessage());
        log.debug("Details:", e);
    }
}
