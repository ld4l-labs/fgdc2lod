/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.fgdc.GeometryType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;

/**
 * Builds an Cartography individual from a Record.
 */
public class FgdcToLd4lCartographyBuilder extends FgdcToLd4lEntityBuilder {
    
    private FgdcRecord record;
    private Entity instance;
  
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        this.record = (FgdcRecord) params.getRecord();  
        this.instance = new Entity(Ld4lWorkType.CARTOGRAPHY);
        
        buildTitle();
        buildGeometry();
        
        return instance;
    }
    
    private void buildTitle() throws EntityBuilderException { 
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.class);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(instance);
        builder.build(params);
    }
    
    private void buildGeometry() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(GeometryType.class);

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(instance);        
        builder.build(params);
    }   
        
}
