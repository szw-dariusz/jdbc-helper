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
