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

import java.sql.SQLException;
import java.util.function.Function;

/**
 * Handler of exceptional situation
 *
 * @param <T> Type of produced object.
 * @author Dariusz Szwarc
 */
@FunctionalInterface
public interface Handler<T> {

    /**
     * Returns a Handler that handles errors by returning default result.
     *
     * @param <T>     Type of produced object.
     * @param handler Function that accepts an SQLException and returns a default result.
     * @return Object obtained from the Supplier.
     */
    static <T> Handler<T> returning(Function<SQLException, T> handler) {
        return handler::apply;
    }

    /**
     * Returns a Handler that handles errors by throwing a {@link RuntimeException}.
     *
     * @param exception Supplier of RuntimeException to be thrown.
     * @param <T>       Type of produced object.
     * @return Nothing as this method is expected to throw an exception.
     */
    static <T> Handler<T> throwing(Function<SQLException, ? extends RuntimeException> exception) {
        return e -> {
            throw exception.apply(e);
        };
    }

    /**
     * Handle an exceptional situation. This method may throw RuntimeExceptions instead of returning values.
     *
     * @param e Exception that is captured by handler and requires handling. May be null.
     * @return Default object.
     */
    T handle(SQLException e);
}
