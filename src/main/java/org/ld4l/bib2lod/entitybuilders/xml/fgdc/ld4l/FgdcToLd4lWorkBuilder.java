/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.fgdc.CartographyType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lIdentifierType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;

/**
 * Builds an Cartography individual from a Record.
 */
public class FgdcToLd4lWorkBuilder extends FgdcToLd4lEntityBuilder {
    
    private FgdcRecord record;
    private Entity instance;
    private Entity work;
  
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        this.record = (FgdcRecord) params.getRecord();
        this.instance = params.getRelatedEntity();
        this.work = new Entity(Ld4lWorkType.CARTOGRAPHY);
        this.work.addType(CartographyType.DATASET);
        
        buildTitle();
        buildGeometry();
        addIdentifiers();
        instance.addRelationship(Ld4lObjectProp.IS_INSTANCE_OF, work);

        return this.work;
    }
    
    private void buildTitle() throws EntityBuilderException { 
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.class);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(work);
        builder.build(params);
    }
    
    private void buildGeometry() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(CartographyType.class);

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(work);        
        builder.build(params);
    }   
    
    private void addIdentifiers() {
    	
    	if ( !record.getLayerId().isEmpty()) {
    		Entity identifier = new Entity(Ld4lIdentifierType.LOCAL);
    		identifier.addAttribute(Ld4lDatatypeProp.VALUE, record.getLayerId());
    		work.addRelationship(Ld4lObjectProp.IS_IDENTIFIED_BY, identifier);
    	}
    }
        
}
