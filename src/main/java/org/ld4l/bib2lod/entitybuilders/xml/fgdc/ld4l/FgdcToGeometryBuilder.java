/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.Entity;
import org.ld4l.bib2lod.ontology.fgdc.GeometryType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcTitle;

/**
 * Builds a Geometry Entity.
 */
public class FgdcToGeometryBuilder extends FgdcToLd4lEntityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
    	FgdcRecord record = (FgdcRecord) params.getRecord();
        Entity bibEntity = params.getRelatedEntity();
        Entity geometry = new Entity(GeometryType.superClass());
        
        FgdcTitle fgdcTitle = record.getTitle();
        if (fgdcTitle.isValid()) {
        	String titleValue = fgdcTitle.getTextValue();
        	geometry = buildWellKnownTextElement(Ld4lTitleType.TITLE, titleValue);
        	bibEntity.addChild(Ld4lObjectProp.HAS_TITLE, geometry);
        }
        
        return geometry;
    }
       
    private Entity buildWellKnownTextElement(
    		Ld4lTitleType elementClass, String label) {
        
         Entity titleElement = new Entity(elementClass);
//         titleElement.addAttribute(FgdcDatatypeProp.AS_WKT, label);
         return titleElement;
    }
    
}
