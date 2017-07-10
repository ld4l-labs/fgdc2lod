/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.File;
import java.util.Objects;

import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;

/**
 * OutputService that writes to files in a directory.
 */
public class FgdcFileOutputService extends FileOutputService {

	private File destination;

    @Override
    public void configure(Configuration config) {
        this.destination = new File(
                Objects.requireNonNull(config.getAttribute("destination"),
                        "You must provide an output destination."));

        if (!destination.exists()) {
            throw new ConfigurationException(
                    "Output destination doesn't exist: '" + destination + "'");
        }

        if (!destination.isDirectory()) {
            throw new ConfigurationException(
                    "Output destination must be a directory: '" + destination
                            + "'");
        }

        if (!destination.canWrite()) {
            throw new ConfigurationException(
                    "Can't write output destination: " + destination);
        }

    }

}
