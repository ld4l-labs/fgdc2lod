package org.ld4l.bib2lod.ontology.fgdc;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Enumerates the cartography sub-types used in the LD4L BIBFRAME 2 extensions and
 * application profile.
 */
public enum CartographySubType implements Type {
    
    CART_SUBTYPE(CartographyNamespace.GEOSPATIAL_AND_CARTOGRAPHIC, "CartSubtype"),
    CART_VECTOR(CartographyNamespace.GEOSPATIAL_AND_CARTOGRAPHIC, "VectorData"),
    CART_MAPS(CartographyNamespace.GEOSPATIAL_AND_CARTOGRAPHIC, "Maps"),
    CART_GEOREFERENCED_RESOURCES(CartographyNamespace.GEOSPATIAL_AND_CARTOGRAPHIC, "GeoreferencedCartographicResources"),
    CART_RASTER(CartographyNamespace.GEOSPATIAL_AND_CARTOGRAPHIC, "RasterData"),
    CART_REMOTE(CartographyNamespace.GEOSPATIAL_AND_CARTOGRAPHIC, "RemoteSensingImages");
    
	private String localName;
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    CartographySubType(Namespace namespace, String localName) {
    	this.localName = localName;
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
    
    public static CartographySubType getTypeByLocalName(String localName) {
    	for (CartographySubType type : CartographySubType.values()) {
    		if (type.localName.equals(localName)) {
    			return type;
    		}
    	}
    	return null;
    }

	@Override
	public Type superclass() {
		return null;
	}

}
