/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion.xml.fgdc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.BaseConverter;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.parsing.xml.fgdc.FgdcParser;

/**
 * Converts FGDC records.
 */
public class FgdcConverter extends BaseConverter {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    private static final Class<?> PARSER_CLASS = FgdcParser.class;

	/**
	 * Constructor
	 */
	public FgdcConverter() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.conversion.BaseConverter#getParserClass()
	 */
	@Override
	protected Class<?> getParserClass() {
        return PARSER_CLASS;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ld4l.bib2lod.conversion.BaseConverter#buildEntity(org.ld4l.bib2lod.records.Record)
	 */
	@Override
	protected org.ld4l.bib2lod.entity.Entity buildEntity(org.ld4l.bib2lod.records.Record record)
			throws EntityBuilderException {
        EntityBuilder instanceBuilder = getBuilder(Ld4lWorkType.class);
        BuildParams params = new BuildParams()
                .setRecord(record);
        return instanceBuilder.build(params);
	}
	
	
}
