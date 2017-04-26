package org.ld4l.bib2lod.ontology.fgdc;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.DatatypeProp;

public enum FgdcDatatypeProp implements DatatypeProp {

    AS_WKT(CartographyNamespace.GEO, "asWKT");
    
    private String uri;
    private Property property;
    
    /**
     * Constructor
     */
    FgdcDatatypeProp(CartographyNamespace namespace, String localName) {
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
