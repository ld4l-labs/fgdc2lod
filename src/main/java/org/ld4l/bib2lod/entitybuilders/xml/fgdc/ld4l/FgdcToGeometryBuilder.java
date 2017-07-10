/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.datatypes.fgdc.FgdcCustomDatatype;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.fgdc.CartographyType;
import org.ld4l.bib2lod.ontology.fgdc.FgdcDatatypeProp;
import org.ld4l.bib2lod.ontology.fgdc.FgdcNamedIndividualType;
import org.ld4l.bib2lod.ontology.fgdc.FgdcObjectProp;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcBoundingField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;

/**
 * Builds a Geometry Entity.
 */
public class FgdcToGeometryBuilder extends FgdcToLd4lEntityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
    	FgdcRecord record = (FgdcRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "A FgdcRecord is required to build a geometry.");
        }

        Entity cartography = params.getRelatedEntity();
        if (cartography == null) {
            throw new EntityBuilderException(
                    "A related Entity is required to build a geometry.");
        }

        Entity geometry = new Entity(CartographyType.superClass()); // (no rdfs:label added)
        
        FgdcBoundingField fgdcBoundingField = record.getBoundingField();
        if (fgdcBoundingField != null) {
        	
        	Attribute wktAttr = new Attribute(fgdcBoundingField.getWKT(),
        			FgdcCustomDatatype.GeoDatatype.WKT_DATATYPE);
        	geometry.addAttribute(FgdcDatatypeProp.AS_WKT, wktAttr);
        	addProjection(geometry);

        	// when converting FGDC record, the Geometry always has its Cartography as source
        	geometry.addRelationship(FgdcObjectProp.HAS_SOURCE, cartography);
        	cartography.addRelationship(FgdcObjectProp.HAS_COORDINATES, geometry);
        }
        
        return geometry;
    }
    
    private Entity addProjection(Entity geometry) {
    	
        Type namedProjection = FgdcNamedIndividualType.EPSG4326_PROJECTION;
        geometry.addExternalRelationship(FgdcObjectProp.HAS_PROJECTION, namedProjection.uri());
        return geometry;
    }

}
