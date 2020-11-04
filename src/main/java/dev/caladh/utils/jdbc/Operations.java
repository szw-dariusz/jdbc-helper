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

package dev.caladh.utils.jdbc;

import java.util.List;
import java.util.Optional;

/**
 * Available operations on JDBC.
 *
 * @author Dariusz Szwarc
 */
public interface Operations {

    /**
     * Fetches a single object.
     *
     * @param sql    SQL query.
     * @param mapper Mapper used to map result set.
     * @param <T>    Type of produced object.
     * @return {@code Optional} of result or empty if the database does not contain any results.
     */
    <T> Optional<T> fetchOne(String sql, RowMapper<T> mapper);

    /**
     * Fetches a single object using provided parameter.
     *
     * @param sql       Parameterized SQL query.
     * @param mapper    Mapper used to map result set.
     * @param parameter Parameter that is passed to the parameterized query.
     * @param <T>       Type of produced object.
     * @return {@code Optional} of result or empty if the database does not contain any results.
     */
    <T> Optional<T> fetchOne(String sql, RowMapper<T> mapper, Object parameter);

    /**
     * Fetches a single object using provided parameters.
     *
     * @param sql        Parameterized SQL query.
     * @param mapper     Mapper used to map result set.
     * @param parameters Parameters that are passed to the parameterized query.
     * @param <T>        Type of produced object.
     * @return {@code Optional} of result or empty if the database does not contain any results.
     */
    <T> Optional<T> fetchOne(String sql, RowMapper<T> mapper, Object... parameters);

    /**
     * Fetches a list of objects.
     *
     * @param sql    SQL query.
     * @param mapper Mapper used to map result set.
     * @param <T>    Type of produced object.
     * @return {@code List} of results.
     */
    <T> List<T> fetchMany(String sql, RowMapper<T> mapper);

    /**
     * Fetches a list of objects using provided parameter.
     *
     * @param sql       Parameterized SQL query.
     * @param mapper    Mapper used to map result set.
     * @param parameter Parameter that is passed to the parameterized query.
     * @param <T>       Type of produced object.
     * @return {@code List} of results.
     */
    <T> List<T> fetchMany(String sql, RowMapper<T> mapper, Object parameter);

    /**
     * Fetches a list of objects using provided parameters.
     *
     * @param sql        Parameterized SQL query.
     * @param mapper     Mapper used to map result set.
     * @param parameters Parameters that are passed to the parameterized query.
     * @param <T>        Type of produced object.
     * @return {@code List} of results.
     */
    <T> List<T> fetchMany(String sql, RowMapper<T> mapper, Object... parameters);

    /**
     * Fetches an object.
     *
     * @param sql       SQL query.
     * @param setter    Setter of parameters for parameterized SQL query.
     * @param extractor Extractor of result set.
     * @param handler   Handler of exceptions raised during execution of query and processing of result.
     * @param <T>       Type of produced object.
     * @return Object mapped from result set obtained by executing the SQL query.
     */
    <T> T fetch(String sql, Setter setter, Extractor<T> extractor, Handler<T> handler);

    /**
     * Executes an update.
     *
     * @param sql SQL query.
     * @return Number of affected rows.
     */
    int update(String sql);

    /**
     * @param sql       Parameterized SQL query.
     * @param parameter Parameter that is passed to the parameterized query.
     * @return Number of affected rows.
     */
    int update(String sql, Object parameter);

    /**
     * @param sql        Parameterized SQL query.
     * @param parameters Parameters that are passed to the parameterized query.
     * @return Number of affected rows.
     */
    int update(String sql, Object... parameters);

    /**
     * @param sql     SQL query.
     * @param setter  Setter of parameters for parameterized SQL query.
     * @param handler Handler of exceptions raised during execution of query and processing of result.
     * @return Number of affected rows.
     */
    int update(String sql, Setter setter, Handler<Integer> handler);
}
