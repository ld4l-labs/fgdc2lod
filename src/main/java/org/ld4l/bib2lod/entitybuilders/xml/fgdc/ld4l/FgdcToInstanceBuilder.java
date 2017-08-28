/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcTextField;

/**
 * Builds an Instance individual from a Record.
 */
public class FgdcToInstanceBuilder extends FgdcToLd4lEntityBuilder {
    
    private FgdcRecord record;
    private Entity work;
    private Entity instance;
  
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        this.record = (FgdcRecord) params.getRecord();
		if (record == null) {
			throw new EntityBuilderException(
					"A FgdcRecord is required to build an Instance.");
		}

		this.work = params.getParent();
        if (work == null) {
            throw new EntityBuilderException(
                    "A related Entity is required to build an Instance.");
        }

        // need to pass title along
        this.instance = new Entity(Ld4lInstanceType.INSTANCE);
        
        buildTitle();
        buildItem();
        buildPublisherActivity();
        buildEditionStatement();
        work.addRelationship(Ld4lObjectProp.HAS_INSTANCE, instance);
        
        return instance;
    }
    
    private void buildTitle() throws EntityBuilderException { 
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.TITLE);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(instance);
        builder.build(params);
    }
    
    private void buildItem() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lItemType.ITEM);

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(instance);        
        builder.build(params);
    }   
    
    private void buildPublisherActivity() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lActivityType.ACTIVITY);
        
        BuildParams params = new BuildParams()
                .setRecord(record)     
                .setParent(instance)
                .setType(Ld4lActivityType.PUBLISHER_ACTIVITY);
        builder.build(params);
    }
    
    private void buildEditionStatement() {
    	FgdcTextField field = record.getCiteinfoField().getEdition();
    	if (field != null) {
    		instance.addAttribute(Ld4lDatatypeProp.EDITION_STATEMENT, field.getTextValue());
    	}
    }

}
