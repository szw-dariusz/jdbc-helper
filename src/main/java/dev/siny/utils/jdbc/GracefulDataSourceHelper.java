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

import javax.sql.DataSource;

/**
 * Basic implementation of Jdbc Operations allowing to execute queries and updates using JDBC data source in a
 * simplified way. All errors are handled gracefully meaning all errors are consumed and default responses are returned.
 *
 * @author Dariusz Szwarc
 */
public final class GracefulDataSourceHelper extends DataSourceHelper implements GracefulOperations {

    /**
     * Creates instance of JdbcHelper.
     *
     * @param dataSource Data source that will be used to obtain connections. Must not be null
     */
    GracefulDataSourceHelper(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * Creates instance of JdbcHelper.
     *
     * @param dataSource Data source that will be used to obtain connections. Must not be null
     * @return Create instance.
     */
    public static GracefulDataSourceHelper create(DataSource dataSource) {
        return new GracefulDataSourceHelper(dataSource);
    }
}
