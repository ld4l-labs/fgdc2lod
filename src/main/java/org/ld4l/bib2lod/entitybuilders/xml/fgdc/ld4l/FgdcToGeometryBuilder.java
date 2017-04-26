/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.fgdc.FgdcDatatypeProp;
import org.ld4l.bib2lod.ontology.fgdc.FgdcObjectProp;
import org.ld4l.bib2lod.ontology.fgdc.GeometryType;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcGeometry;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;

/**
 * Builds a Geometry Entity.
 */
public class FgdcToGeometryBuilder extends FgdcToLd4lEntityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
    	FgdcRecord record = (FgdcRecord) params.getRecord();
        Entity bibEntity = params.getRelatedEntity();
        // huh? nothing done with this if !isValid(); if valid, this is overwritten
        Entity geometry = new Entity(GeometryType.superClass()); // (no rdfs:label added)
        geometry.addType(GeometryType.CARTOGRAPIC);
        
        FgdcGeometry fgdcGeometry = record.getGeometry();
        if (fgdcGeometry.isValid()) {
        	
        	geometry = addWellKnownText(geometry, fgdcGeometry);
        	bibEntity.addChild(FgdcObjectProp.HAS_COORDINATES, geometry);
        }
        
        return geometry; // nothing done with return value???
    }
       
    private Entity addWellKnownText(Entity geometry, FgdcGeometry fgdcGeometry) {
    	
        geometry.addAttribute(FgdcDatatypeProp.AS_WKT, fgdcGeometry.getWKT());
        return geometry;
    }
    
}
