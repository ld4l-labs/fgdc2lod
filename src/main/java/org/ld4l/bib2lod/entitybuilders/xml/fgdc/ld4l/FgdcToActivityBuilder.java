/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import java.util.List;

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
import org.ld4l.bib2lod.record.xml.fgdc.FgdcField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;

/**
 * Builds a Geometry Entity.
 */
public class FgdcToActivityBuilder extends FgdcToLd4lEntityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
    	FgdcRecord record = (FgdcRecord) params.getRecord();
    	Entity bibEntity = params.getRelatedEntity();
        Ld4lActivityType type = (Ld4lActivityType) params.getType();
        if (type == null) {
        	throw new EntityBuilderException("type is null");
        }
        
        Entity activity = new Entity(type);
        activity.addAttribute(Ld4lDatatypeProp.LABEL, new Attribute(type.label()));

    	if (type.equals(Ld4lActivityType.ORIGINATOR_ACTIVITY)){
    		List<FgdcField> origins = record.getCiteinfoField().getOrigins();
    		if (origins.size() > 0) {
    			for (FgdcField agentField : origins) {
    				EntityBuilder builder = getBuilder(Ld4lAgentType.class);
    				BuildParams params2 = new BuildParams()
    						.setRecord(record)
    						.setField(agentField)
    						.setRelatedEntity(activity);
    				builder.build(params2);
    			}
    			bibEntity.addRelationship(Ld4lObjectProp.HAS_ACTIVITY, activity);
    		}
	    	
    	} else if (type.equals(Ld4lActivityType.PUBLISHER_ACTIVITY)) {
    		FgdcField agentField = record.getCiteinfoField().getPublish();
    		FgdcField pubplaceField = record.getCiteinfoField().getPubplace();
    		FgdcField pubdateField = record.getCiteinfoField().getPubdate();
    		boolean atLeastOneField = false;

    		if (agentField != null) {
    			EntityBuilder builder = getBuilder(Ld4lAgentType.class);
    	        BuildParams params2 = new BuildParams()
    	                .setRecord(record)
    	                .setField(agentField)
    	                .setRelatedEntity(activity);
    	        builder.build(params2);
				atLeastOneField = true;
			}
			
			// create and add location
			if (pubplaceField != null) {
				Entity location = new Entity(Ld4lLocationType.LOCATION);
				location.addAttribute(Ld4lDatatypeProp.LABEL, pubplaceField.getTextValue());
				activity.addRelationship(Ld4lObjectProp.IS_AT_LOCATION, location);
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
    		LOGGER.warn("Specific type not indicated: {}", type);
    	}
    	
        return activity;
    }

}
