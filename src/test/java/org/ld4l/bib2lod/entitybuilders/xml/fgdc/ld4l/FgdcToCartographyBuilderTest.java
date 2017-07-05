/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ObjectProp;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.fgdc.CartographyType;
import org.ld4l.bib2lod.ontology.fgdc.FgdcObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.FgdcTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.ld4l.bib2lod.util.collections.MapOfLists;
import org.w3c.dom.Element;

/**
 * Tests the FgdcToCartographyBuilder class.
 */
public class FgdcToCartographyBuilderTest extends AbstractTestClass {
    
	private EntityBuilder cartographyBuilder;
	private FgdcRecord fgdcRecord;
	
    private static BaseMockBib2LodObjectFactory factory;

    private static final Logger LOGGER = LogManager.getLogger();
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();
        factory.addInstance(EntityBuilderFactory.class, new FgdcToLd4lEntityBuilderFactory());
    }

    @Before
    public void setUp() throws RecordException {
        cartographyBuilder = new FgdcToCartographyBuilder();
        fgdcRecord = buildFgdcRecordFromString(FgdcTestData.VALID_KEYWORDS);
    }
	
	@Test
	public void validInstanceRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setRecord(fgdcRecord);
		
		Entity cartographyEntity = cartographyBuilder.build(params);

		Assert.assertNotNull(cartographyEntity);
		
		// types
		List<Type> types = cartographyEntity.getTypes();
		Assert.assertNotNull(types);
		// types in CartographyBuilder - others in CartographySubtypeBuilder
		types.contains(Ld4lWorkType.CARTOGRAPHY);
		types.contains(CartographyType.DATASET);
		
		// labels
		List<String> labels = cartographyEntity.getValues(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labels);
		Assert.assertEquals(1, labels.size());
		Assert.assertEquals(fgdcRecord.getCiteinfoField().getTitle().getTextValue(), labels.get(0));
		
		MapOfLists<ObjectProp, Entity> relationships = cartographyEntity.getRelationships();
		Assert.assertNotNull(relationships);
		Assert.assertEquals(8, relationships.keys().size());
		
		for (ObjectProp prop : relationships.keys()) {
			LOGGER.info("Have ObjectProp: {}", prop.uri());
		}
		
		// titles
		List<Entity> titles = relationships.getValues(Ld4lObjectProp.HAS_TITLE);
		Assert.assertNotNull(titles);
		Assert.assertEquals(1, titles.size());
		
		// instances
		List<Entity> instances = relationships.getValues(Ld4lObjectProp.HAS_INSTANCE);
		Assert.assertNotNull(instances);
		Assert.assertEquals(1, instances.size());
		
		// geometry
		List<Entity> geometries = relationships.getValues(FgdcObjectProp.HAS_COORDINATES);
		Assert.assertNotNull(geometries);
		Assert.assertEquals(1, geometries.size());
		
		// layer ID
		List<Entity> layerIds = relationships.getValues(Ld4lObjectProp.IDENTIFIED_BY);
		Assert.assertNotNull(layerIds);
		Assert.assertEquals(1, layerIds.size());
		Assert.assertEquals(fgdcRecord.getLayerId(), layerIds.get(0).getAttribute(Ld4lDatatypeProp.VALUE).getValue());
		
		// activities
		List<Entity> activities = relationships.getValues(Ld4lObjectProp.HAS_ACTIVITY);
		Assert.assertNotNull(activities);
		Assert.assertEquals(2, activities.size());
		
		// annotations
		List<Entity> annotations = relationships.getValues(Ld4lObjectProp.HAS_ANNOTATION);
		Assert.assertNotNull(annotations);
		Assert.assertEquals(2, annotations.size());
		
		// theme keywords
		List<Entity> subjects = relationships.getValues(Ld4lObjectProp.HAS_SUBJECT);
		Assert.assertNotNull(subjects);
		Assert.assertEquals(5, subjects.size());
		
		// place keywords
		List<Entity> geographicCoverages = relationships.getValues(FgdcObjectProp.GEOGRAPHIC_COVERAGE);
		Assert.assertNotNull(geographicCoverages);
		Assert.assertEquals(2, geographicCoverages.size());
		
		// language
		String language = cartographyEntity.getExternal(Ld4lObjectProp.HAS_LANGUAGE);
		Assert.assertNotNull(language);
		Assert.assertEquals("http://lexvo.org/id/iso639-3/eng", language);
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A FgdcRecord is required to build a Cartography.");
		BuildParams params = new BuildParams()
				.setRecord(null);
		
		cartographyBuilder.build(params);
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
