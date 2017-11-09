package org.ld4l.bib2lod.ontology.fgdc;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Enumerates the cartography types used in the LD4L BIBFRAME 2 extensions and
 * application profile.
 */
public enum HarvardType implements Type {
    
    HGLID(HarvardNamespace.HARVARD, "HGLID"),
    HOLLIS_NUMBER(HarvardNamespace.HARVARD, "HOLLISNumber");
    
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    HarvardType(Namespace namespace, String localName) {
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

	@Override
	public Type superclass() {
		return null;
	} 

}
