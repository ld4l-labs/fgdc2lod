/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.ontology.fgdc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.ontology.Namespace;

/**
 * Define the FGDC-related ontology namespaces.
 */
public enum CartographyNamespace implements Namespace {

	GEO("http://www.opengis.net/ont/geosparql#", "geo"),
	// FIXME: This needs a correct namespace
	CARTOTEKO("http://ld4l.org/ontology/cartotek-o/", "cart"),
    RDAU("http://rdaregistry.info/Elements/u/", "rdau");

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    private final String uri;
    private final String prefix;
    
    CartographyNamespace(String uri, String prefix) {
        this.uri = uri;
        this.prefix = prefix;
    }
    
    public String uri() {
        return this.uri;
    }
    
    public String prefix() {
        return this.prefix;
    }
    
}
