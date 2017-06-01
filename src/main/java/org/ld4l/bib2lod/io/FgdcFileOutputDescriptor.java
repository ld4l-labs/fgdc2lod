/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.ld4l.bib2lod.io.FileOutputService.Format;
import org.ld4l.bib2lod.io.OutputService.OutputServiceException;

/**
 * A wrapper for a file-based output item.
 */
public class FgdcFileOutputDescriptor extends FileOutputDescriptor {
//    private final File file;
//    private final Format format;
//    private FileOutputStream output;

    public FgdcFileOutputDescriptor(Format format, File destination,
            String inputName) throws IOException {
        super(format, destination, inputName);
    }

    @Override
    synchronized public void writeModel(Model model)
            throws IOException, OutputServiceException {
//        if (output == null) {
//            throw new OutputServiceException(
//                    "Attempting to write after close.");
//        }
        
        Map <String, String> map = new HashMap<>();
        map.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        map.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        map.put("bf", "http://id.loc.gov/ontologies/bibframe/");
        map.put("cart", "http://ld4l.org/ontology/cartotek-o/");
        map.put("dcterms", "http://purl.org/dc/terms/");
        map.put("geo", "http://www.opengis.net/ont/geosparql#");
        map.put("ld4l", "http://bib.ld4l.org/ontology/");
        map.put("ld4l-cm", "http://ld4l.org/ontology/ld4lcm/");
        map.put("rdau", "http://rdaregistry.info/Elements/u/");
        map.put("prov", "http://www.w3.org/ns/prov#");
        map.put("foaf", "http://xmlns.com/foaf/0.1/");
        map.put("oa", "http://www.w3.org/ns/oa#");
        map.put("metal", "http://harvcore.org/ontology/");
        model.setNsPrefixes(map);
        
        super.writeModel(model);
        
//        model.write(output, format.getLanguage());
    }
}
