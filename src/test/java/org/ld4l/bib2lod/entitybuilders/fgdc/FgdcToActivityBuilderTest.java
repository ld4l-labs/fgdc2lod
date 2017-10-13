/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.fgdc;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.caching.CachingService;
import org.ld4l.bib2lod.caching.MapCachingService;
import org.ld4l.bib2lod.configuration.ConfigurationNode;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ObjectProp;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.fgdc.BaseFgdcField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcTextField;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.FgdcTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.ld4l.bib2lod.uris.RandomUriMinter;
import org.ld4l.bib2lod.uris.UriService;
import org.ld4l.bib2lod.util.collections.MapOfLists;
import org.w3c.dom.Element;

/**
 * Tests the FgdcToActivityBuilder class.
 */
public class FgdcToActivityBuilderTest extends AbstractTestClass {
    
	private EntityBuilder activityBuilder;
	private FgdcRecord fgdcRecord;
	private Entity relatedEntity;
	private BaseFgdcField originatorField;
	
    private static BaseMockBib2LodObjectFactory factory;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();
        factory.addInstance(EntityBuilderFactory.class, new FgdcToLd4lEntityBuilderFactory());
        ConfigurationNode config = new ConfigurationNode.Builder()
        		.addAttribute("localNamespace", "http://localhost/individual/").build();
        factory.addInstance(UriService.class, new RandomUriMinter(), config);
        factory.addInstance(CachingService.class, new MapCachingService());
    }

    @Before
    public void setUp() throws RecordException, EntityBuilderException {
        activityBuilder = new FgdcToActivityBuilder();
        fgdcRecord = buildFgdcRecordFromString(FgdcTestData.VALID_ACTIVITIES);
        relatedEntity = new Entity(Ld4lWorkType.CARTOGRAPHY);
        originatorField = new FgdcTextField(XmlTestUtils.buildElementFromString(FgdcTestData.VALID_ABSTRACT), "originator");
    }
	
	@Test
	public void validOriginatorActivityRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setRecord(fgdcRecord)
				.setParent(relatedEntity)
				.setField(originatorField)
				.setType(Ld4lActivityType.ORIGINATOR_ACTIVITY);
		
		Entity activityEntity = activityBuilder.build(params);

		Assert.assertNotNull(activityEntity);
		List<Type> types = activityEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertTrue(types.contains(Ld4lActivityType.ORIGINATOR_ACTIVITY));
		
		List<String> labels = activityEntity.getValues(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labels);
		Assert.assertEquals(1, labels.size());
		Assert.assertEquals(Ld4lActivityType.ORIGINATOR_ACTIVITY.label(), labels.get(0));
		
		MapOfLists<ObjectProp, Entity> relationships = activityEntity.getRelationships();
		Assert.assertNotNull(relationships);
		Assert.assertEquals(1, relationships.keys().size());
		List<Entity> agents = relationships.getValues(Ld4lObjectProp.HAS_AGENT);
		Assert.assertNotNull(agents);
		Assert.assertEquals(1, agents.size());

	}
	
	@Test
	public void validPublisherActivityRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setRecord(fgdcRecord)
				.setParent(relatedEntity)
				.setType(Ld4lActivityType.PUBLISHER_ACTIVITY);
		
		Entity activityEntity = activityBuilder.build(params);

		Assert.assertNotNull(activityEntity);
		List<Type> types = activityEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertTrue(types.contains(Ld4lActivityType.PUBLISHER_ACTIVITY));
		
		List<String> labels = activityEntity.getValues(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labels);
		Assert.assertEquals(1, labels.size());
		Assert.assertEquals(Ld4lActivityType.PUBLISHER_ACTIVITY.label(), labels.get(0));
		
		MapOfLists<ObjectProp, Entity> relationships = activityEntity.getRelationships();
		Assert.assertNotNull(relationships);
		Assert.assertEquals(1, relationships.keys().size());
		
		List<Entity> agents = relationships.getValues(Ld4lObjectProp.HAS_AGENT);
		Assert.assertNotNull(agents);
		Assert.assertEquals(1, agents.size());
		
		String location = activityEntity.getExternal(Ld4lObjectProp.HAS_LOCATION);
		Assert.assertNotNull(location);
		Assert.assertEquals("http://sws.geonames.org/4932004", location);
		
		List<String> dates = activityEntity.getValues(Ld4lDatatypeProp.DATE);
		Assert.assertNotNull(dates);
		Assert.assertEquals(1, dates.size());
		Assert.assertEquals("2014", dates.get(0));
	}
	
	@Test
	public void validPublisherActivityRecordNoConcordanceMatch() throws Exception {

        fgdcRecord = buildFgdcRecordFromString(FgdcTestData.VALID_ACTIVITIES_NO_CONCORDANCE_MATCH);

		BuildParams params = new BuildParams()
				.setRecord(fgdcRecord)
				.setParent(relatedEntity)
				.setType(Ld4lActivityType.PUBLISHER_ACTIVITY);
		
		Entity activityEntity = activityBuilder.build(params);

		Assert.assertNotNull(activityEntity);
		List<Type> types = activityEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertTrue(types.contains(Ld4lActivityType.PUBLISHER_ACTIVITY));
		
		List<String> labels = activityEntity.getValues(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labels);
		Assert.assertEquals(1, labels.size());
		Assert.assertEquals(Ld4lActivityType.PUBLISHER_ACTIVITY.label(), labels.get(0));
		
		MapOfLists<ObjectProp, Entity> relationships = activityEntity.getRelationships();
		Assert.assertNotNull(relationships);
		Assert.assertEquals(2, relationships.keys().size());
		
		List<Entity> agents = relationships.getValues(Ld4lObjectProp.HAS_AGENT);
		Assert.assertNotNull(agents);
		Assert.assertEquals(1, agents.size());
		
		List<Entity> locations = relationships.getValues(Ld4lObjectProp.HAS_LOCATION);
		Assert.assertNotNull(locations);
		Assert.assertEquals(1, locations.size());
		Attribute locationAttr = locations.get(0).getAttribute(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(locationAttr);
		Assert.assertEquals("Upper Slabovia", locationAttr.getValue());
		
		List<String> dates = activityEntity.getValues(Ld4lDatatypeProp.DATE);
		Assert.assertNotNull(dates);
		Assert.assertEquals(1, dates.size());
		Assert.assertEquals("2014", dates.get(0));
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A FgdcRecord is required to build an Activity.");
		BuildParams params = new BuildParams()
				.setRecord(null)
				.setParent(relatedEntity)
				.setType(Ld4lActivityType.ORIGINATOR_ACTIVITY);
		
		activityBuilder.build(params);
	}
	
	@Test
	public void nullOriginatorFieldEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A FgdcField originField is required to build an Activity.");
		BuildParams params = new BuildParams()
				.setRecord(fgdcRecord)
				.setParent(relatedEntity)
				.setField(null)
				.setType(Ld4lActivityType.ORIGINATOR_ACTIVITY);
		
		activityBuilder.build(params);
	}
	
	@Test
	public void nullRelatedEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A related Entity is required to build an Activity.");
		BuildParams params = new BuildParams()
				.setRecord(fgdcRecord)
				.setParent(null)
				.setType(Ld4lActivityType.ORIGINATOR_ACTIVITY);
		
		activityBuilder.build(params);
	}
	
	@Test
	public void nullActivityType_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "An Ld4lActivityType is required to build an Activity.");
		BuildParams params = new BuildParams()
				.setRecord(fgdcRecord)
				.setParent(relatedEntity)
				.setType(null);
		
		activityBuilder.build(params);
	}
	
	@Test
	public void nonSpecificActivityType_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "Non-specific Activity type not indicated: " + Ld4lActivityType.ACTIVITY.label());
		BuildParams params = new BuildParams()
				.setRecord(fgdcRecord)
				.setParent(relatedEntity)
				.setType(Ld4lActivityType.ACTIVITY);
		
		activityBuilder.build(params);
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
