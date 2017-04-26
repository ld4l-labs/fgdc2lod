/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcTitle;

/**
 * Builds a Title Entity.
 */
public class FgdcToLd4lTitleBuilder extends FgdcToLd4lEntityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
    	FgdcRecord record = (FgdcRecord) params.getRecord();
        Entity bibEntity = params.getRelatedEntity();
        // huh? nothing done with this if !isValid(); if valid, this is overwritten
        Entity title = new Entity(Ld4lTitleType.superClass());
        
        FgdcTitle fgdcTitle = record.getTitle();
        if (fgdcTitle.isValid()) {
        	String titleValue = fgdcTitle.getTextValue();
        	title = buildTitleElement(Ld4lTitleType.TITLE, titleValue);
        	bibEntity.addChild(Ld4lObjectProp.HAS_TITLE, title);
        	// add title as label of parent (Cartography)
        	bibEntity.addAttribute(Ld4lDatatypeProp.LABEL, titleValue);
        }
        
        return title; // nothing done with return value???
    }
       
    private Entity buildTitleElement(
    		Ld4lTitleType elementClass, String label) {
        
         Entity titleElement = new Entity(elementClass);
         titleElement.addAttribute(Ld4lDatatypeProp.LABEL, label);
         return titleElement;
    }
    
}
