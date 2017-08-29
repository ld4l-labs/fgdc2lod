/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.fgdc;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAnnotationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTextualBodyType;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcTextField;

/**
 * Builds an Annotation Entity.
 */
public class FgdcToAnnotationBuilder extends FgdcToLd4lEntityBuilder {

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.entitybuilders.EntityBuilder#build(org.ld4l.bib2lod.entitybuilders.BuildParams)
	 */
	@Override
	public Entity build(BuildParams params) throws EntityBuilderException {
        
        Entity bibEntity = params.getParent();
        if (bibEntity == null) {
            throw new EntityBuilderException(
                    "A related Entity is required to build an Annotation.");
        }

        if (params.getField() == null) {
        	throw new EntityBuilderException("field is null");
        }
        if ( !(params.getField() instanceof FgdcTextField)) {
        	throw new EntityBuilderException("field not instanceof FgdcTextOnlyField");
        }
        FgdcTextField field = (FgdcTextField) params.getField();
        
        Entity annotation = new Entity(Ld4lAnnotationType.ANNOTATION);
        Entity textualBody = new Entity(Ld4lTextualBodyType.TEXTUAL_BODY);
		textualBody.addAttribute(Ld4lDatatypeProp.VALUE, field.getTextValue());
		annotation.addRelationship(Ld4lObjectProp.HAS_BODY, textualBody);
        switch (field.getFieldName()) {
        	case "abstract":
        		annotation.addExternalRelationship(Ld4lObjectProp.MOTIVATED_BY,
        				Ld4lNamedIndividual.SUMMARIZING.uri());
        		annotation.addExternalRelationship(Ld4lObjectProp.HAS_CREATOR, Ld4lNamedIndividual._134059638.uri());
        		break;
        	case "purpose":
				annotation.addExternalRelationship(Ld4lObjectProp.MOTIVATED_BY,
						Ld4lNamedIndividual.PROVIDING_PURPOSE.uri());
				annotation.addExternalRelationship(Ld4lObjectProp.HAS_CREATOR, Ld4lNamedIndividual._134059638.uri());
				break;
			default:
				throw new EntityBuilderException(field.getFieldName() + " type Annotation not yet supported.");
        }
		bibEntity.addRelationship(Ld4lObjectProp.HAS_ANNOTATION, annotation);
        
        return annotation;
	}

}
