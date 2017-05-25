/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAnnotationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcAnnotation;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;

/**
 * Builds an Annotation Entity.
 */
public class FgdcToAnnotationBuilder extends FgdcToLd4lEntityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.entitybuilders.EntityBuilder#build(org.ld4l.bib2lod.entitybuilders.BuildParams)
	 */
	@Override
	public Entity build(BuildParams params) throws EntityBuilderException {
        
    	FgdcRecord record = (FgdcRecord) params.getRecord();
        Entity cartography = params.getRelatedEntity();
        List<FgdcAnnotation> fgdcAnnotations = record.getFgdcAnnotations();
        for (FgdcAnnotation annot : fgdcAnnotations) {
    		Entity annotation = new Entity(Ld4lAnnotationType.ANNOTATION);
    		Entity textualBody = new Entity(Ld4lAnnotationType.TEXTUAL_BODY);
    		textualBody.addAttribute(Ld4lDatatypeProp.VALUE, annot.getTextValue());
    		annotation.addRelationship(Ld4lObjectProp.HAS_BODY, textualBody);
    		switch (annot.getAnnotationType()) {
				case DESCRIBING:
					// when needed, add: dcterms:creator <agent> and oa:motivatedBy oa:describing
					throw new UnsupportedOperationException(FgdcAnnotation.AnnotationType.DESCRIBING.name() + " type Annotation not yet implemented.");
//					break;
				case PROVIDING_PURPOSE:
					annotation.addExternalRelationship(Ld4lObjectProp.MOTIVATED_BY,
							Ld4lNamedIndividual.PROVIDING_PURPOSE.uri());
					annotation.addExternalRelationship(Ld4lObjectProp.CREATOR, Ld4lNamedIndividual._134059638.uri());
					break;
				case SUMMARIZING:
					annotation.addExternalRelationship(Ld4lObjectProp.MOTIVATED_BY,
							Ld4lNamedIndividual.SUMMARIZING.uri());
					annotation.addExternalRelationship(Ld4lObjectProp.CREATOR, Ld4lNamedIndividual._134059638.uri());
					break;
				default:
					throw new UnsupportedOperationException(FgdcAnnotation.AnnotationType.DESCRIBING.name() + " type Annotation not yet implemented.");
//					break;
    		}
    		cartography.addRelationship(Ld4lObjectProp.HAS_ANNOTATION, annotation);
        }
 
//    	return annotation;
        return null;
	}

}
