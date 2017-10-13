/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.fgdc;

import java.io.FileNotFoundException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.caching.CachingService;
import org.ld4l.bib2lod.caching.CachingService.CachingServiceException;
import org.ld4l.bib2lod.caching.CachingService.MapType;
import org.ld4l.bib2lod.csv.fgdc.UriLabelConcordanceBean;
import org.ld4l.bib2lod.csv.fgdc.UriLabelConcordanceManager;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcTextField;
import org.ld4l.bib2lod.uris.UriService;

/**
 * Builds an Agent Entity.
 */
public class FgdcToAgentBuilder extends FgdcToLd4lEntityBuilder {

    private UriLabelConcordanceManager concordanceManager;
    private CachingService cachingService;

	private static final Logger LOGGER = LogManager.getLogger();

	public FgdcToAgentBuilder() throws EntityBuilderException {
    	try {
			this.concordanceManager = UriLabelConcordanceManager.getAgentsConcordanceManager();
		} catch ( FileNotFoundException e) {
			throw new EntityBuilderException("Could not instantiate AgentsConcordanceManager", e);
		}
	}

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
    	this.cachingService = CachingService.instance();
        
        Entity parentEntity = params.getParent();
        if (parentEntity == null) {
        	throw new EntityBuilderException("A related Entity is required to build a title.");
        }
        
        FgdcTextField agentField = (FgdcTextField)params.getField();
        if (agentField == null) {
        	throw new EntityBuilderException("An agentField is required to build an Agent.");
        }
        
        Entity agentEntity = null;
        UriLabelConcordanceBean concordanceBean = null;
        String agentText = agentField.getTextValue();
        concordanceBean = concordanceManager.getConcordanceEntry(agentField.getTextValue());
        if (concordanceBean != null) {
    		// for concordance match add external relationship to corresponding URI
    		String uri = concordanceBean.getUri();
    		parentEntity.addExternalRelationship(Ld4lObjectProp.HAS_AGENT, uri);
        } else {
        	agentEntity = new Entity(Ld4lAgentType.AGENT);
        	agentEntity.addAttribute(Ld4lDatatypeProp.LABEL, agentText);

        	Map<String, String> mapToUriCache = cachingService.getMap(MapType.NAMES_TO_URI);
        	String cachedAgentUri = mapToUriCache.get(agentText);
        	if (cachedAgentUri != null) {
        		agentEntity.buildResource(cachedAgentUri);
        	} else {
            	String uri = UriService.getUri(agentEntity);
            	agentEntity.buildResource(uri);
            	try {
    				cachingService.putUri(MapType.NAMES_TO_URI, agentText, uri);
    			} catch (CachingServiceException e) {
    				throw new EntityBuilderException(e);
    			}
        	}
        	
        	parentEntity.addRelationship(Ld4lObjectProp.HAS_AGENT, agentEntity);
        }
        
        return agentEntity; // *could* be null if external relationship created instead of this Entity
    }
       
}
