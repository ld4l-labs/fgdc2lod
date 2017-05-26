/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.fgdc.CartographyType;
import org.ld4l.bib2lod.ontology.fgdc.HarvardType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAnnotationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;

/**
 * Builds an Cartography individual from a Record.
 */
public class FgdcToCartographyBuilder extends FgdcToLd4lEntityBuilder {
    
    private FgdcRecord record;
    private Entity work;
  
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        this.record = (FgdcRecord) params.getRecord();
        
        this.work = new Entity(CartographyType.DATASET);
        this.work.addType(Ld4lWorkType.CARTOGRAPHY); // (This is defined as two different types.)
        
        buildTitle();
        buildInstances();
        buildOriginatorActivity();
        addIdentifiers();
        buildGeometry();
        buildAnnotations();

        return this.work;
    }
    
    private void buildTitle() throws EntityBuilderException { 
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.class);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(work);
        builder.build(params);
    }
    
    private void buildInstances() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lInstanceType.class);

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
    	
		Entity identifier = new Entity(HarvardType.HGLID);
		String layerId = record.getLayerId(); // should be validated as non-null
		identifier.addAttribute(Ld4lDatatypeProp.VALUE, layerId);
		work.addRelationship(Ld4lObjectProp.IS_IDENTIFIED_BY, identifier);
    }
    
    private void buildOriginatorActivity() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lActivityType.class);
    	BuildParams params = new BuildParams()
    			.setRecord(record)     
    			.setRelatedEntity(work)
    			.setType(Ld4lActivityType.ORIGINATOR_ACTIVITY);
    	builder.build(params);
    }
    
    private void buildAnnotations() throws EntityBuilderException {
        EntityBuilder builder = getBuilder(Ld4lAnnotationType.class);
        
        FgdcField field = record.getPurposeField();
        if (field != null) {
        	BuildParams params = new BuildParams()
        			.setRecord(record)
        			.setField(field)
        			.setRelatedEntity(work);
        	builder.build(params);
        }

        field = record.getAbstractField();
        if (field != null) {
        	BuildParams params = new BuildParams()
        			.setRecord(record)
        			.setField(field)
        			.setRelatedEntity(work);
        	builder.build(params);
        }
    }
        
}
