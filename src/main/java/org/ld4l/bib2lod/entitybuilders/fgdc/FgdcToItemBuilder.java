/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.fgdc;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcTextField;

public class FgdcToItemBuilder extends FgdcToLd4lEntityBuilder {

	/*
	 * (non-Javadoc)
	 * @see org.ld4l.bib2lod.entitybuilders.EntityBuilder#build(org.ld4l.bib2lod.entitybuilders.BuildParams)
	 */
	@Override
	public Entity build(BuildParams params) throws EntityBuilderException {

    	FgdcRecord record = (FgdcRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "A FgdcRecord is required to build an Item.");
        }

        Entity instance = params.getParent();
        if (instance == null) {
            throw new EntityBuilderException(
                    "A related Entity is required to build an Item.");
        }
        
        Entity  item = new Entity(Ld4lItemType.superClass());
        FgdcTextField electronicLocatorField = record.getCiteinfoField().getOnlink();
        if (electronicLocatorField != null) {
        	item.addExternalRelationship(Ld4lObjectProp.HAS_ELECTRONIC_LOCATOR, electronicLocatorField.getTextValue());
        }
        FgdcTextField titleField = record.getCiteinfoField().getTitle();
		item.addAttribute(Ld4lDatatypeProp.LABEL, titleField.getTextValue());
        instance.addRelationship(Ld4lObjectProp.HAS_ITEM, item);
        return item;
	}

}
