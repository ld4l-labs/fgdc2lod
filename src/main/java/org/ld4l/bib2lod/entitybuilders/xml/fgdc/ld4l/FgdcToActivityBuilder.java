/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lLocationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.xml.fgdc.BaseFgdcField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcTextField;

/**
 * Builds a Geometry Entity.
 */
public class FgdcToActivityBuilder extends FgdcToLd4lEntityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
    	FgdcRecord record = (FgdcRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "A FgdcRecord is required to build an Activity.");
        }

        Entity bibEntity = params.getParent();
        if (bibEntity == null) {
            throw new EntityBuilderException(
                    "A related Entity is required to build an Activity.");
        }

        Ld4lActivityType type = (Ld4lActivityType) params.getType();
        if (type == null) {
        	throw new EntityBuilderException("An Ld4lActivityType is required to build an Activity.");
        }
        
        Entity activity = new Entity(type);
        activity.addAttribute(Ld4lDatatypeProp.LABEL, new Attribute(type.label()));

    	if (type.equals(Ld4lActivityType.ORIGINATOR_ACTIVITY)){
    		BaseFgdcField originField = (BaseFgdcField)params.getField();
            if (originField == null) {
                throw new EntityBuilderException(
                        "A FgdcField originField is required to build an Activity.");
            }
			EntityBuilder builder = getBuilder(Ld4lAgentType.AGENT);
			BuildParams params2 = new BuildParams()
					.setRecord(record)
					.setField(originField)
					.setParent(activity);
			builder.build(params2);
			bibEntity.addRelationship(Ld4lObjectProp.HAS_ACTIVITY, activity);
	    	
    	} else if (type.equals(Ld4lActivityType.PUBLISHER_ACTIVITY)) {
    		FgdcTextField agentField = record.getCiteinfoField().getPublish();
    		FgdcTextField pubplaceField = record.getCiteinfoField().getPubplace();
    		FgdcTextField pubdateField = record.getCiteinfoField().getPubdate();
    		boolean atLeastOneField = false;

    		if (agentField != null) {
    			EntityBuilder builder = getBuilder(Ld4lAgentType.AGENT);
    	        BuildParams params2 = new BuildParams()
    	                .setRecord(record)
    	                .setField(agentField)
    	                .setParent(activity);
    	        builder.build(params2);
				atLeastOneField = true;
			}
			
			// create and add location
			if (pubplaceField != null) {
				Entity location = new Entity(Ld4lLocationType.LOCATION);
				location.addAttribute(Ld4lDatatypeProp.LABEL, pubplaceField.getTextValue());
				activity.addRelationship(Ld4lObjectProp.HAS_LOCATION, location);
				atLeastOneField = true;
			}
			
			// add date
			if (pubdateField != null) {
				activity.addAttribute(Ld4lDatatypeProp.DATE, pubdateField.getTextValue());
				atLeastOneField = true;
			}
			
	    	if (atLeastOneField) {
	    		bibEntity.addRelationship(Ld4lObjectProp.HAS_ACTIVITY, activity);
	    	}
	    	
    	} else {
    		throw new EntityBuilderException("Non-specific Activity type not indicated: " + type.label());
    	}
    	
        return activity;
    }

}
