/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.csv.fgdc.AgentsConcordanceBean;
import org.ld4l.bib2lod.csv.fgdc.AgentsConcordanceManager;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcField;

/**
 * Builds an Agent Entity.
 */
public class FgdcToAgentBuilder extends FgdcToLd4lEntityBuilder {

    private AgentsConcordanceManager concordanceManager;

	private static final Logger LOGGER = LogManager.getLogger();

	public FgdcToAgentBuilder() throws EntityBuilderException {
    	try {
			this.concordanceManager = new AgentsConcordanceManager();
		} catch ( URISyntaxException | IOException e) {
			throw new EntityBuilderException("Could not instantiate AgentsConcordanceManager", e);
		}
	}

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
        Entity bibEntity = params.getRelatedEntity();
        if (bibEntity == null) {
        	throw new EntityBuilderException("A related Entity is required to build a title.");
        }
        
        FgdcField agentField = (FgdcField)params.getField();
        if (agentField == null) {
        	throw new EntityBuilderException("An agentField is required to build an Agent.");
        }
        
        Entity agentEntity = null;
        AgentsConcordanceBean concordanceBean = null;
        concordanceBean = concordanceManager.getConcordanceEntry(agentField.getTextValue());
        if (concordanceBean != null) {
    		// for concordance match add external relationship to corresponding URI
    		String uri = concordanceBean.getUri();
    		bibEntity.addExternalRelationship(Ld4lObjectProp.HAS_AGENT, uri);
        } else {
        	agentEntity = new Entity(Ld4lAgentType.AGENT);
        	agentEntity.addAttribute(Ld4lDatatypeProp.LABEL, agentField.getTextValue());
        	bibEntity.addRelationship(Ld4lObjectProp.HAS_AGENT, agentEntity);
        }
        
        return agentEntity; // *could* be null if external relationship created instead of this Entity
    }
       
}
