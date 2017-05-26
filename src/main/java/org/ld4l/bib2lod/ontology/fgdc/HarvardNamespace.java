/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.ontology.fgdc;

import org.ld4l.bib2lod.ontology.Namespace;

/**
 * Define Harvard-related ontology namespace.
 */
// TODO See if we can use ontology files to define these instead.
public enum HarvardNamespace implements Namespace {

	METAL("http://harvcore.org/ontology/", "metal");
    
    private final String uri;
    private final String prefix;
    
    HarvardNamespace(String uri, String prefix) {
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
