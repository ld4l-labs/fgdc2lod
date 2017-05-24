/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcTextOnlyField;

/**
 * Builds an Cartography individual from a Record.
 */
public class FgdcToLd4lInstanceBuilder extends FgdcToLd4lEntityBuilder {
    
    private FgdcRecord record;
    private Entity work;
    private Entity instance;
  
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        this.record = (FgdcRecord) params.getRecord();
        this.work = params.getRelatedEntity();
        this.instance = new Entity(Ld4lInstanceType.INSTANCE);
        
        buildTitle();
        buildItem();
        buildPublisherActivity();
        buildEditionStatement();
        work.addRelationship(Ld4lObjectProp.HAS_INSTANCE, instance);
        
        return instance;
    }
    
    private void buildTitle() throws EntityBuilderException { 
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.class);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(instance);
        builder.build(params);
    }
    
    private void buildItem() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lItemType.class);

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(instance);        
        builder.build(params);
    }   
    
    private void buildPublisherActivity() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lActivityType.class);
        
        BuildParams params = new BuildParams()
                .setRecord(record)     
                .setRelatedEntity(instance)
                .setType(Ld4lActivityType.PUBLISHER_ACTIVITY);
        builder.build(params);
    }
    
    private void buildEditionStatement() {
    	FgdcTextOnlyField field = record.getFgdcEdition();
    	if (field.isValid()) {
    		instance.addAttribute(Ld4lDatatypeProp.EDITION_STATEMENT, field.getTextValue());
    	}
    }

}
