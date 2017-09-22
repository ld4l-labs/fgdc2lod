package org.ld4l.bib2lod.ontology.fgdc;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Enumerates named individuals related to FGDC.
 */
public enum FgdcNamedIndividualType implements Type {
    
	EPSG4326_PROJECTION(CartographyNamespace.CARTOTEKO, "ProjectionEpsg4326"),
	MERCATOR_PROJECTION(CartographyNamespace.CARTOTEKO, "Mercator");
    
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    FgdcNamedIndividualType(Namespace namespace, String localName) {
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
