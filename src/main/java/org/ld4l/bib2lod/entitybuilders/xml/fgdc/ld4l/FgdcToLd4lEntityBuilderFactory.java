/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import java.util.HashMap;

import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.fgdc.CartographyType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAnnotationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;

public class FgdcToLd4lEntityBuilderFactory extends BaseEntityBuilderFactory {

    private static HashMap<Class<? extends Type>, Class<? extends EntityBuilder>> typeToBuilder = 
            new HashMap<>();
    static {
        typeToBuilder.put(Ld4lInstanceType.class, FgdcToInstanceBuilder.class);
        typeToBuilder.put(Ld4lWorkType.class, FgdcToCartographyBuilder.class);
        typeToBuilder.put(Ld4lTitleType.class, FgdcToTitleBuilder.class);
        typeToBuilder.put(CartographyType.class, FgdcToGeometryBuilder.class);
        typeToBuilder.put(Ld4lActivityType.class, FgdcToActivityBuilder.class);
        typeToBuilder.put(Ld4lAnnotationType.class, FgdcToAnnotationBuilder.class);
        typeToBuilder.put(Ld4lItemType.class, FgdcToItemBuilder.class);
        typeToBuilder.put(Ld4lAgentType.class, FgdcToAgentBuilder.class);
    }
    
    @Override
    public HashMap<Class<? extends Type>, Class<? extends EntityBuilder>> 
            getTypeToBuilderClassMap() {
        return typeToBuilder;
    }
      
}
