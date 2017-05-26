/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;

public class FgdcToItemBuilder extends FgdcToLd4lEntityBuilder {

	/*
	 * (non-Javadoc)
	 * @see org.ld4l.bib2lod.entitybuilders.EntityBuilder#build(org.ld4l.bib2lod.entitybuilders.BuildParams)
	 */
	@Override
	public Entity build(BuildParams params) throws EntityBuilderException {

    	FgdcRecord record = (FgdcRecord) params.getRecord();
        Entity instance = params.getRelatedEntity();
        
        Entity  item = new Entity(Ld4lItemType.superClass());
        FgdcField electronicLocatorField = record.getCiteinfoField().getOnlink();
        if (electronicLocatorField != null) {
        	item.addExternalRelationship(Ld4lObjectProp.ELECTRONIC_LOCATOR, electronicLocatorField.getTextValue());
        }
    	FgdcField titleField = record.getCiteinfoField().getTitle();
		item.addAttribute(Ld4lDatatypeProp.LABEL, titleField.getTextValue());
        instance.addRelationship(Ld4lObjectProp.HAS_ITEM, item);
        return item;
	}

}
