/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.fgdc;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.csv.fgdc.FgdcGeoformConcordanceBean;
import org.ld4l.bib2lod.csv.fgdc.FgdcGeoformConcordanceManager;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.fgdc.CartographySubType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcCiteinfoField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcTextField;

/**
 * Builds an CartographySubtype individual from a Record.
 */
public class FgdcToCartographySubtypeBuilder extends FgdcToLd4lEntityBuilder {

	FgdcGeoformConcordanceManager concordanceManager;
	
    private static final Logger LOGGER = LogManager.getLogger();
    
    public FgdcToCartographySubtypeBuilder() throws EntityBuilderException {
    	try {
			this.concordanceManager = new FgdcGeoformConcordanceManager();
		} catch ( FileNotFoundException e) {
			throw new EntityBuilderException("Could not instantiate FgdcGeoformConcordanceManager", e);
		}
	}
    
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

    	FgdcRecord record = (FgdcRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "A FgdcRecord is required to build a CartographySubtype.");
        }
        
        Entity work = params.getParent();
        if (work == null) {
        	throw new EntityBuilderException("A related Entity is required to build a cartography subtype.");
        }
        
        FgdcCiteinfoField citeinfoField = record.getCiteinfoField();
        // when no data to add just return the related Entity
        if (citeinfoField == null || citeinfoField.getGeoform() == null) {
        	return work;
        }
        
        FgdcTextField geoFormField = citeinfoField.getGeoform();
    	FgdcGeoformConcordanceBean concordanceBean = concordanceManager.getConcordanceEntry(geoFormField.getTextValue());
    	// see if field shows up in concordance file
    	if (concordanceBean != null) {
    		// could be more than one type
    		String[] mappingEquivalents = concordanceBean.getMappingEquivalentArray();
    		for (String equivalent : mappingEquivalents) {
    			CartographySubType type = CartographySubType.getTypeByLocalName(equivalent);
    			if (type != null) {
    				work.addType(type);
    			} else {
    				LOGGER.warn("FGDC <geoform> value [{}] does not have a matching equivalent in the appropriate concordance file", geoFormField.getTextValue());
    			}
    		}
    		
    	} else {
    		// If no concordance match, create a comment attribute with text that doesn't find a match.
    		String editorialNote = "geospatial data presentation form literal: " + geoFormField.getTextValue();
        	Attribute commentAttr = new Attribute(editorialNote,
        			Ld4lCustomDatatypes.BibDatatype.LEGACY_SOURCE_DATA);
        	work.addAttribute(Ld4lDatatypeProp.COMMENT, commentAttr);
    	}
    	
        return work;
    }
        
}
