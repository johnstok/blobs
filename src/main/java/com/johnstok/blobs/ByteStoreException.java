/*-----------------------------------------------------------------------------
 * Copyright © 2015 Keith Webster Johnston.
 * All rights reserved.
 *
 * This file is part of blobs.
 *
 * blobs is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * blobs is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with blobs. If not, see <http://www.gnu.org/licenses/>.
 *---------------------------------------------------------------------------*/
package com.johnstok.blobs;


/**
 * Represents an exception that occurs during a {@link ByteStore} operation.
 *
 * @author Keith Webster Johnston.
 */
public class ByteStoreException
    extends
        Exception {

    /** serialVersionUID : long. */
    private static final long serialVersionUID = -4185821226317071148L;


    /**
     * Constructor.
     *
     * @param e The cause of this exception.
     */
    public ByteStoreException(final Exception e) {
        super(e);
    }
}
