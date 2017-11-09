/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.ontology.fgdc;

import org.ld4l.bib2lod.ontology.Namespace;

/**
 * Define Harvard-related ontology namespace.
 */
public enum HarvardNamespace implements Namespace {

	HARVARD("http://ontology.library.harvard.edu/harvcore/", "hcore");
    
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
