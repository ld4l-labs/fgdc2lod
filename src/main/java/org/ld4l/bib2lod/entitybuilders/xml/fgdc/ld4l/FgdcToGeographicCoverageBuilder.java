/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.fgdc.FgdcObjectProp;
import org.ld4l.bib2lod.ontology.fgdc.GeographicCoverageType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcPlaceField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;

public class FgdcToGeographicCoverageBuilder extends FgdcToLd4lEntityBuilder {

	/*
	 * (non-Javadoc)
	 * @see org.ld4l.bib2lod.entitybuilders.EntityBuilder#build(org.ld4l.bib2lod.entitybuilders.BuildParams)
	 */
	@Override
	public Entity build(BuildParams params) throws EntityBuilderException {

        Entity work = params.getRelatedEntity();
        FgdcPlaceField placeField = (FgdcPlaceField) params.getField();
        
        Entity source = new Entity() {};
        source.addAttribute(Ld4lDatatypeProp.LABEL, placeField.getPlaceKt().getTextValue());
        
        // create a GeographicCoverage for each key and add each to Work (Cartography)
        for (FgdcField key : placeField.getPlaceKeys()) {
        	Entity geographicCoverage = new Entity(GeographicCoverageType.superClass());
        	geographicCoverage.addAttribute(Ld4lDatatypeProp.LABEL, key.getTextValue());
        	geographicCoverage.addRelationship(FgdcObjectProp.HAS_SOURCE, source);
        	work.addRelationship(FgdcObjectProp.GEOGRAPHIC_COVERAGE, geographicCoverage);
        }

        return null;//geographicCoverage;
	}

}
