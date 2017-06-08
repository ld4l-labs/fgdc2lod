package org.ld4l.bib2lod.ontology.fgdc;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.ObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;

public enum FgdcObjectProp implements ObjectProp {

    GENRE_FORM(Ld4lNamespace.BIBFRAME, "genreForm"),
    GEOGRAPHIC_COVERAGE(Ld4lNamespace.BIBFRAME, "geographicCoverage"),
    HAS_COORDINATES(CartographyNamespace.RDAU, "P60109"),
    HAS_PROJECTION(Ld4lNamespace.BIBFRAME, "projection"), // TODO: move to bib2lod project?
    HAS_SOURCE(Ld4lNamespace.BIBLIOTEKO, "hasSource"); // TODO: move to bib2lod project?
   
    private String uri;
    private Property property;
    
    /**
     * Constructor
     */
    private FgdcObjectProp(Namespace namespace, String localName) {
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
