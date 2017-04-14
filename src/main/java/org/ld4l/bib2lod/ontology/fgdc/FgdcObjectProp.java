package org.ld4l.bib2lod.ontology.fgdc;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.OntologyProp;

public enum FgdcObjectProp implements OntologyProp {

    HAS_COORDINATES(CartographyNamespace.RDAU, "P60109");
   
    private String uri;
    private Property property;
    
    /**
     * Constructor
     */
    FgdcObjectProp(CartographyNamespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.property = ResourceFactory.createProperty(uri);
    }
    
    public String uri() {
        return uri;
    }
    
    public Property property() {
        return property;
    }

}
