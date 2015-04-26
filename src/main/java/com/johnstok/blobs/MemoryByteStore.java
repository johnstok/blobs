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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Implementation of {@link ByteStore} that stores all bytes in memory.
 *
 * @author Keith Webster Johnston.
 */
public class MemoryByteStore implements ByteStore {

    private final Map<UUID, byte[]> _bytes =
        new ConcurrentHashMap<UUID, byte[]>();

    /** {@inheritDoc} */
    @Override
    public UUID create(final InputStream in) {
        final UUID id = UUID.randomUUID();
        update(id, in);
        return id;
    }

    /** {@inheritDoc} */
    @Override
    public void delete(final UUID id) {
        _bytes.remove(id);
    }

    /** {@inheritDoc} */
    @Override
    public void read(final UUID id, final OutputStream out) {
        try {
            final ByteArrayInputStream in =
                new ByteArrayInputStream(_bytes.get(id));
            write(in, out);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void update(final UUID id, final InputStream in) {
        try {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            write(in, out);
            _bytes.put(id, out.toByteArray());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void write(final InputStream in,
                       final OutputStream out) throws IOException {
        int b = in.read();
        while (-1 != b) {
            out.write(b);
            b = in.read();
        }
    }
}
