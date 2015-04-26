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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


/**
 * An implementation of {@link ByteStore} that writes to files on the local file
 * system.
 *
 * @author Keith Webster Johnston.
 */
public class FileByteStore implements ByteStore {

    private final File _directory;

    /**
     * Constructor.
     *
     * @param directory The directory where we will store the files.
     */
    public FileByteStore(final File directory) {
        if (!directory.exists()) {
            throw new RuntimeException("Directory doesn't exist"); //$NON-NLS-1$
        }
        if (!directory.isDirectory()) {
            throw new RuntimeException("Not a directory."); //$NON-NLS-1$
        }
        if (!directory.canWrite()) {
            throw new RuntimeException("Directory is read only."); //$NON-NLS-1$
        }
        _directory = directory;
    }

    /** {@inheritDoc} */
    @Override
    public UUID create(final InputStream in) {
        final UUID filename = UUID.randomUUID();
        update(filename, in);
        return filename;
    }

    /** {@inheritDoc} */
    @Override
    public void delete(final UUID id) {
        final File f = new File(_directory, id.toString());
        if (!f.delete()) {
            throw new RuntimeException("Failed to delete file."); //$NON-NLS-1$
        }
    }

    /** {@inheritDoc} */
    @Override
    public void read(final UUID id, final OutputStream out) {
        final File f = new File(_directory, id.toString());
        try {
            final FileInputStream in = new FileInputStream(f);
            try {
                write(in, out);
            } catch (final IOException e1) {
                throw new RuntimeException(e1);
            } finally {
                try {
                    in.close();
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (final FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void update(final UUID id, final InputStream in) {
        final File f = new File(_directory, id.toString());
        try {
            final FileOutputStream out = new FileOutputStream(f);
            try {
                write(in, out);
            } catch (final IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    out.close();
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (final FileNotFoundException e) {
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
