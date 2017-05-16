/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lLocationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcAgent;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcOriginatorActivity;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcPublisherActivity;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;

/**
 * Builds a Geometry Entity.
 */
public class FgdcToActivityBuilder extends FgdcToLd4lEntityBuilder {
	
	private Entity bibEntity;
	private FgdcRecord record;
	private Ld4lActivityType type;
	private Entity activity;

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
    	this.record = (FgdcRecord) params.getRecord();
        this.bibEntity = params.getRelatedEntity();
        
        Type typeParam = params.getType();
        this.type = (Ld4lActivityType) (typeParam != null ? 
                typeParam : Ld4lActivityType.superClass());
        this.activity = new Entity(type);

    	if (type.equals(Ld4lActivityType.ORIGINATOR_ACTIVITY)){
    		FgdcOriginatorActivity fgdcOriginatorActivity = this.record.getFgdcOriginatorActivity();
    		if (fgdcOriginatorActivity.isValid()) {
    			// add Agents
    			if ( !fgdcOriginatorActivity.getOrigins().isEmpty()) {
    				for (FgdcAgent agent : fgdcOriginatorActivity.getOrigins()) {
    					Entity agentEntity = new Entity(Ld4lAgentType.AGENT);
    					agentEntity.addAttribute(Ld4lDatatypeProp.LABEL, agent.getTextValue());
    					this.activity.addRelationship(Ld4lObjectProp.HAS_AGENT, agentEntity);
    				}
    			}
		    	
		        addLabel();
		        this.bibEntity.addRelationship(Ld4lObjectProp.HAS_ACTIVITY, this.activity);
    		}
    	}
    	
    	if (type.equals(Ld4lActivityType.PUBLISHER_ACTIVITY)) {
    		FgdcPublisherActivity fgdcPublisherActivity = this.record.getFgdcPublisherActivity();
    		if (fgdcPublisherActivity.isValid()) {
    			// create and add Agent
    			if (fgdcPublisherActivity.getAgent() != null) {
    				Entity agentEntity = new Entity(Ld4lAgentType.AGENT);
    				agentEntity.addAttribute(Ld4lDatatypeProp.LABEL, fgdcPublisherActivity.getAgent().getTextValue());
    				this.activity.addRelationship(Ld4lObjectProp.HAS_AGENT, agentEntity);
    			}
				
				// create and add location
				if (fgdcPublisherActivity.getLocation() != null) {
					Entity location = new Entity(Ld4lLocationType.LOCATION);
					location.addAttribute(Ld4lDatatypeProp.LABEL, fgdcPublisherActivity.getLocation());
					this.activity.addRelationship(Ld4lObjectProp.IS_AT_LOCATION, location);
				}
				// add date
				if (fgdcPublisherActivity.getDate() != null) {
					this.activity.addAttribute(Ld4lDatatypeProp.DATE, fgdcPublisherActivity.getDate());
				}
		    	
		        addLabel();
		        this.bibEntity.addRelationship(Ld4lObjectProp.HAS_ACTIVITY, this.activity);
    		}
    	}

        return this.activity;
    }
        
    private void addLabel() {
        activity.addAttribute(Ld4lDatatypeProp.LABEL, new Attribute(type.label()));
    }

}
