/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;

/**
 * Builds a Title Entity.
 */
public class FgdcToTitleBuilder extends FgdcToLd4lEntityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
    	FgdcRecord record = (FgdcRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "A FgdcRecord is required to build a title.");
        }

        Entity bibEntity = params.getRelatedEntity();
        if (bibEntity == null) {
            throw new EntityBuilderException(
                    "A related Entity is required to build a title.");
        }

        Entity title = new Entity(Ld4lTitleType.superClass());
        
        FgdcField fgdcTitle = record.getCiteinfoField().getTitle();
        
    	String titleValue = fgdcTitle.getTextValue();
    	title = buildTitleElement(Ld4lTitleType.TITLE, titleValue);
    	bibEntity.addRelationship(Ld4lObjectProp.HAS_TITLE, title);
    	// add title as label of parent (Cartography)
    	bibEntity.addAttribute(Ld4lDatatypeProp.LABEL, titleValue);
        
        return title;
    }
       
    private Entity buildTitleElement(
    		Ld4lTitleType elementClass, String label) {
        
         Entity titleElement = new Entity(elementClass);
         titleElement.addAttribute(Ld4lDatatypeProp.LABEL, label);
         return titleElement;
    }
    
}
