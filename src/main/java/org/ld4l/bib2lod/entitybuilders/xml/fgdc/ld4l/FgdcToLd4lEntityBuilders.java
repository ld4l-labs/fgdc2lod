package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import java.util.HashMap;

import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilders;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.fgdc.GeometryType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;

public class FgdcToLd4lEntityBuilders extends BaseEntityBuilders {

    private static HashMap<Class<? extends Type>, Class<? extends EntityBuilder>> typeToBuilder = 
            new HashMap<>();
    static {
        typeToBuilder.put(Ld4lWorkType.class, FgdcToLd4lCartographyBuilder.class);
        typeToBuilder.put(Ld4lTitleType.class, FgdcToLd4lTitleBuilder.class);
        typeToBuilder.put(GeometryType.class, FgdcToGeometryBuilder.class);
    }
    
    @Override
    public HashMap<Class<? extends Type>, Class<? extends EntityBuilder>> 
            getTypeToBuilderClassMap() {
        return typeToBuilder;
    }
      
}
