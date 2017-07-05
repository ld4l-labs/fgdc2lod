/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.FgdcTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Element;

/**
 * Tests the FgdcToTitleBuilder class.
 */
public class FgdcToTitleBuilderTest extends AbstractTestClass {
    
	private EntityBuilder titleBuilder;
	private FgdcRecord fgdcRecord;
	private Entity relatedEntity;

    @Before
    public void setUp() throws RecordException {
        titleBuilder = new FgdcToTitleBuilder();
        fgdcRecord = buildFgdcRecordFromString(FgdcTestData.VALID_CITEINFO);
        relatedEntity = new Entity(Ld4lWorkType.CARTOGRAPHY);
    }
	
	@Test
	public void validRecord() throws Exception {
		
		BuildParams params = new BuildParams();
		params.setRecord(fgdcRecord);
		params.setRelatedEntity(relatedEntity);
		
		Entity titleEntity = titleBuilder.build(params);

		Assert.assertNotNull(titleEntity);
		List<Type> types = titleEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertTrue(types.contains(Ld4lTitleType.TITLE));
		
		List<String> labels = titleEntity.getValues(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labels);
		Assert.assertEquals(1, labels.size());
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A FgdcRecord is required to build a title.");
		BuildParams params = new BuildParams();
		params.setRecord(null);
		params.setRelatedEntity(relatedEntity);
		
		titleBuilder.build(params);
	}
	
	@Test
	public void nullRelatedEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A related Entity is required to build a title.");
		BuildParams params = new BuildParams();
		params.setRecord(fgdcRecord);
		params.setRelatedEntity(null);
		
		titleBuilder.build(params);
	}

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
	
    private FgdcRecord buildFgdcRecordFromString(String s) 
            throws RecordException {
    	Element element = XmlTestUtils.buildElementFromString(s);
    	return new FgdcRecord(element);
    }
}
