/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.MockBib2LodObjectFactory;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.records.Record.RecordException;

/**
 * Tests the FgdcToLd4lTitleBuilder class.
 */
public class FgdcToLd4lTitleBuilderTest {
	
    private static MockBib2LodObjectFactory factory;
    
	private EntityBuilder titleBuilder;

	private static final String VALID_record = 
            "<metadata layerid='some_value'>" +
        		"<idinfo>" +
					"<citation>" +
						"<citeinfo>" +
							"<title>Test Title Text</title>" +
						"</citeinfo>" +
					"</citation>" +
				"</idinfo>" +
			"</metadata>";

	private static final String INVALID_no_title_text_record = 
            "<metadata layerid='some_value'>" +
        		"<idinfo>" +
					"<citation>" +
						"<citeinfo>" +
							"<title/>" +
						"</citeinfo>" +
					"</citation>" +
				"</idinfo>" +
			"</metadata>";
    
    @BeforeClass
    public static void setUpClass() {
        factory = new MockBib2LodObjectFactory();
        factory.addInstance(EntityBuilderFactory.class, new FgdcToLd4lEntityBuilderFactory());
        Bib2LodObjectFactory.setFactoryInstance(factory);
    }

    @Before
    public void setUp() {
        titleBuilder = new FgdcToLd4lTitleBuilder();
    }
	
	@Test
	public void testValidRecord() throws Exception {
		
		FgdcRecord record = buildFgdcRecordFromString(VALID_record);
		
		Entity titleEntity = testCommonAssertions(record);
		
		List<Attribute> labels = titleEntity.getValues(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labels);
		Assert.assertEquals(1, labels.size());
	}
	
	@Test
	public void testInvalidTitleRecord() throws Exception {
		
		FgdcRecord record = buildFgdcRecordFromString(INVALID_no_title_text_record);
		
		Entity titleEntity = testCommonAssertions(record);

		List<Attribute> labels = titleEntity.getValues(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labels);
		// No text so should be no label
		Assert.assertEquals(0, labels.size());
	}


    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
	
	private Entity testCommonAssertions(FgdcRecord record) throws Exception {
		
		titleBuilder = titleBuilder.getBuilder(Ld4lTitleType.class);
		Assert.assertNotNull(titleBuilder);

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
        return (FgdcRecord) XmlTestUtils.buildRecordFromString(
        		FgdcRecord.class, s);
    }
}
