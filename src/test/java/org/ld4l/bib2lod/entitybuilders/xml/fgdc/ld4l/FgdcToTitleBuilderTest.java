/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.FgdcTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Element;

/**
 * Tests the FgdcToTitleBuilder class.
 */
public class FgdcToTitleBuilderTest extends AbstractTestClass {
	
    private static BaseMockBib2LodObjectFactory factory;
    
	private EntityBuilder titleBuilder;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();
        factory.addInstance(EntityBuilderFactory.class, new FgdcToLd4lEntityBuilderFactory());
    }

    @Before
    public void setUp() {
        titleBuilder = new FgdcToTitleBuilder();
    }
	
	@Test
	public void testValidRecord() throws Exception {
		
		FgdcRecord record = buildFgdcRecordFromString(FgdcTestData.VALID_CITEINFO);
		
		Entity titleEntity = testCommonAssertions(record);
		
		List<Attribute> labels = titleEntity.getValues(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labels);
		Assert.assertEquals(1, labels.size());
	}

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
	
	private Entity testCommonAssertions(FgdcRecord record) throws Exception {
		
		titleBuilder = titleBuilder.getBuilder(Ld4lTitleType.class);
		Assert.assertNotNull(titleBuilder);
		Assert.assertTrue(titleBuilder instanceof FgdcToTitleBuilder);

		BuildParams params = new BuildParams();
		Entity cartography = new Entity(Ld4lWorkType.CARTOGRAPHY);
		params.setRecord(record);
		params.setRelatedEntity(cartography);
		
		Entity titleEntity = titleBuilder.build(params);
		Assert.assertNotNull(titleEntity);
		
		List<Type> types = titleEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertTrue(types.contains(Ld4lTitleType.TITLE));
		
		return titleEntity;
	}
    
    private FgdcRecord buildFgdcRecordFromString(String s) 
            throws RecordException {
    	Element element = XmlTestUtils.buildElementFromString(s);
    	return new FgdcRecord(element);
    }
}
