/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.w3c.dom.Element;

/**
 * Tests the FgdcToLd4lTitleBuilder class.
 */
public class FgdcToLd4lTitleBuilderTest extends AbstractTestClass {
	
    private static BaseMockBib2LodObjectFactory factory;
    
	private EntityBuilder titleBuilder;

	private static final String VALID_record = 
            "<metadata layerid='some_value'>" +
        		"<idinfo>" +
					"<citation>" +
						"<citeinfo>" +
							"<origin>Cambridge (Mass.). Public Library</origin>" +
							"<pubdate>2014</pubdate>" +
							"<title>Test Title Text</title>" +
							"<edition>2014 revised ed.</edition>" +
							"<pubinfo>" +
								"<pubplace>Cambridge, Massachusetts</pubplace>" +
								"<publish>Cambridge (Mass.). Geographic Information Systems</publish>" +
							"</pubinfo>" +
							"<onlink>http://hgl.harvard.edu:8080/HGL/hgl.jsp?action=VColl&amp;VCollName=CAMBRIDGE14PUBLICLIBRARIES</onlink>" +
						"</citeinfo>" +
					"</citation>" +
					"<descript>" +
						"<abstract>This layer contains point features of all public libraries in Cambridge.</abstract>" +
						"<purpose>Created for general use by City staff.</purpose>" +
					"</descript>" +
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
    public static void setUpClass() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();
        factory.addInstance(EntityBuilderFactory.class, new FgdcToLd4lEntityBuilderFactory());
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
    	expectException(RecordFieldException.class, "text value is null");
		
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
    	Element element = XmlTestUtils.buildElementFromString(s);
    	return new FgdcRecord(element);
    }
}
