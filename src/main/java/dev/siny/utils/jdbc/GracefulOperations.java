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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Class defining operations that capture any SQL exceptions and consume them silently.
 *
 * @author Dariusz Szwarc
 */
public interface GracefulOperations extends Operations {

    @Override
    default <T> Optional<T> fetchOne(String sql, RowMapper<T> mapper) {
        Handler<Optional<T>> handler = Handler.returning(e -> Optional.empty());
        return fetch(sql, Setter.noParameters(), Extractor.extractOne(mapper, handler), handler);
    }

    @Override
    default <T> Optional<T> fetchOne(String sql, RowMapper<T> mapper, Object parameter) {
        Handler<Optional<T>> handler = Handler.returning(e -> Optional.empty());
        return fetch(sql, Setter.forParameter(parameter), Extractor.extractOne(mapper, handler), handler);
    }

    @Override
    default <T> Optional<T> fetchOne(String sql, RowMapper<T> mapper, Object... parameters) {
        Handler<Optional<T>> handler = Handler.returning(e -> Optional.empty());
        return fetch(sql, Setter.forParameters(parameters), Extractor.extractOne(mapper, handler), handler);
    }

    @Override
    default <T> List<T> fetchMany(String sql, RowMapper<T> mapper) {
        Handler<List<T>> handler = Handler.returning(e -> Collections.emptyList());
        return fetch(sql, Setter.noParameters(), Extractor.extractMany(mapper, handler), handler);
    }

    @Override
    default <T> List<T> fetchMany(String sql, RowMapper<T> mapper, Object parameter) {
        Handler<List<T>> handler = Handler.returning(e -> Collections.emptyList());
        return fetch(sql, Setter.forParameter(parameter), Extractor.extractMany(mapper, handler), handler);
    }

    @Override
    default <T> List<T> fetchMany(String sql, RowMapper<T> mapper, Object... parameters) {
        Handler<List<T>> handler = Handler.returning(e -> Collections.emptyList());
        return fetch(sql, Setter.forParameters(parameters), Extractor.extractMany(mapper, handler), handler);
    }

    @Override
    default int update(String sql) {
        return update(sql, Setter.noParameters(), Handler.returning(e -> 0));
    }

    @Override
    default int update(String sql, Object parameter) {
        return update(sql, Setter.forParameter(parameter), Handler.returning(e -> 0));
    }

    @Override
    default int update(String sql, Object... parameters) {
        return update(sql, Setter.forParameters(parameters), Handler.returning(e -> 0));
    }
}
