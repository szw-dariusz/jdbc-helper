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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Setter of parameters for JDBC Prepared statements.
 *
 * @author Dariusz Szwarc
 */
@FunctionalInterface
public interface Setter {

    /**
     * Creates a noop Setter.
     *
     * @return Created Setter.
     */
    static Setter noParameters() {
        return statement -> {
        };
    }

    /**
     * Creates a Setter that sets a single parameter.
     *
     * @param parameter Parameter that will be set.
     * @return Created Setter.
     */
    static Setter forParameter(Object parameter) {
        return statement -> statement.setObject(1, parameter);
    }

    /**
     * Creates a Setter that sets all parameters in encounter order.
     *
     * @param parameters Parameters that will be set.
     * @return Created Setter.
     * @apiNote If an array is provided instead of a varargs argument, this method will return optimal setter based
     * on provided array size.
     */
    static Setter forParameters(Object... parameters) {
        Objects.requireNonNull(parameters);
        return switch (parameters.length) {
            case 0 -> noParameters();
            case 1 -> forParameter(parameters[0]);
            default -> statement -> {
                for (int i = 0; i < parameters.length; i++) {
                    statement.setObject(i + 1, parameters[i]);
                }
            };
        };
    }

    /**
     * Sets up the prepared statement by setting all provided parameters.
     *
     * @param statement PreparedStatement that will be set up.
     * @throws SQLException When prepared statement raises an exception when setting the parameters.
     */
    void setup(PreparedStatement statement) throws SQLException;
}
