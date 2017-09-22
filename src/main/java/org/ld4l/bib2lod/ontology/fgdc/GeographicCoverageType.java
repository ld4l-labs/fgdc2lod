package org.ld4l.bib2lod.ontology.fgdc;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;

/**
 * Enumerates the Geographic Coverage types used in the Bibliotek-o extensions and
 * application profile.
 */
public enum GeographicCoverageType implements Type {
    
	GEOGRAPHIC_COVERAGE(Ld4lNamespace.BIBFRAME, "GeographicCoverage");
    
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    GeographicCoverageType(Namespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.ontClass = ResourceFactory.createResource(uri);
    }
    
    @Override
    public String uri() {
        return uri;
    }

    @Override
    public Resource ontClass() {
        return ontClass;
    } 

    public static Type superClass() {
        return GEOGRAPHIC_COVERAGE;
    }

	@Override
	public Type superclass() {
		return GEOGRAPHIC_COVERAGE;
	}

}
