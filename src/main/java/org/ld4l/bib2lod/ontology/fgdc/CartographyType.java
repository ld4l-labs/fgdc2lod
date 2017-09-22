package org.ld4l.bib2lod.ontology.fgdc;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;

/**
 * Enumerates the cartography types used in the LD4L BIBFRAME 2 extensions and
 * application profile.
 */
public enum CartographyType implements Type {
    
	GEOMETRY(CartographyNamespace.GEO, "Geometry"),
	DATASET(Ld4lNamespace.BIBFRAME, "Dataset"),
	EPSG_CODE(CartographyNamespace.CARTOTEKO, "EpsgCode"),
    PROJECTION(Ld4lNamespace.BIBFRAME, "Projection");
    
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    CartographyType(Namespace namespace, String localName) {
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

	@Override
	public Type superclass() {
		return null;
	}

}
