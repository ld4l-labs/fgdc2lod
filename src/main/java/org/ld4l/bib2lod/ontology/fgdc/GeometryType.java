package org.ld4l.bib2lod.ontology.fgdc;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;

/**
 * Enumerates the cartography types used in the LD4L BIBFRAME 2 extensions and
 * application profile.
 */
public enum GeometryType implements Type {
    
	GEOMETRY(CartographyNamespace.GEO, "Geometry"),
    PROJECTION(CartographyNamespace.RDAU, "P60109"); // domain - bf:Cartography, range - geo:Geometry
	// Identifier class is in org.ld4l.bib2lod.ontology.ld4l.Ld4lIdentifierType
    
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    GeometryType(CartographyNamespace namespace, String localName) {
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
        return GEOMETRY;
    }

}
