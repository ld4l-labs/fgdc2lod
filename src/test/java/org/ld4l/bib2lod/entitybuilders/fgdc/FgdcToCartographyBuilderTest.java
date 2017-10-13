/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.fgdc;

import java.util.List;

import org.apache.jena.rdf.model.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.caching.CachingService;
import org.ld4l.bib2lod.caching.MapCachingService;
import org.ld4l.bib2lod.configuration.ConfigurationNode;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ObjectProp;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.fgdc.CartographyType;
import org.ld4l.bib2lod.ontology.fgdc.FgdcObjectProp;
import org.ld4l.bib2lod.ontology.fgdc.HarvardType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
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
        ConfigurationNode config = new ConfigurationNode.Builder()
        		.addAttribute("localNamespace", "http://localhost/individual/").build();
        factory.addInstance(UriService.class, new RandomUriMinter(), config);
        factory.addInstance(CachingService.class, new MapCachingService());
    }

    @Before
    public void setUp() throws RecordException, EntityBuilderException {
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
		Entity layerId = cartographyEntity.getChild(Ld4lObjectProp.IDENTIFIED_BY, HarvardType.HGLID);
		Assert.assertNotNull(layerId);
		Assert.assertEquals(fgdcRecord.getLayerId(), layerId.getAttribute(Ld4lDatatypeProp.VALUE).getValue());

		// Hollis number
		Entity hollisNumber = cartographyEntity.getChild(Ld4lObjectProp.IDENTIFIED_BY, HarvardType.HOLLIS_NUMBER);
		Assert.assertNotNull(hollisNumber);
		Assert.assertEquals(fgdcRecord.getHollisNumber(), hollisNumber.getAttribute(Ld4lDatatypeProp.VALUE).getValue());
		
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
		for (Entity e : subjects) {
			LOGGER.warn("Subject type: [{}] Label: [{}]", e.getType(), e.getAttribute(Ld4lDatatypeProp.LABEL).getValue());
		}
		Assert.assertEquals(4, subjects.size());

		// external URI's from concordance files
		MapOfLists<ObjectProp, String> externals = cartographyEntity.getExternalRelationships();
		Assert.assertNotNull(externals);
		List<String> externalUris = externals.getValues(Ld4lObjectProp.HAS_SUBJECT);
		Assert.assertNotNull(externalUris);
		Assert.assertEquals(2, externalUris.size());
		
		// place keywords
		List<Entity> geographicCoverages = relationships.getValues(FgdcObjectProp.GEOGRAPHIC_COVERAGE);
		Assert.assertNotNull(geographicCoverages);
		Assert.assertEquals(1, geographicCoverages.size());
		
		String uri = cartographyEntity.getExternal(FgdcObjectProp.GEOGRAPHIC_COVERAGE);
		Assert.assertNotNull(uri);
		
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
	
	/**
	 * This tests caching of URI for LayerId Entity
	 */
	@Test
	public void sameUriForSameLayerId() throws Exception {
		
		fgdcRecord = buildFgdcRecordFromString(FgdcTestData.VALID_CITEINFO);
		BuildParams params = new BuildParams()
				.setRecord(fgdcRecord);
		Entity cartography1 = cartographyBuilder.build(params);
		
		fgdcRecord = buildFgdcRecordFromString(FgdcTestData.VALID_CITEINFO_DUPLICATE_LAYER_ID_AND_HOLLIS);
		params = new BuildParams()
				.setRecord(fgdcRecord);
		Entity cartography2 = cartographyBuilder.build(params);
		
		Assert.assertNotNull(cartography1);
		Assert.assertNotNull(cartography2);

		Entity layerId1 = cartography1.getChild(Ld4lObjectProp.IDENTIFIED_BY, HarvardType.HGLID);
		Assert.assertNotNull(layerId1);
		layerId1.buildResource();
		Resource layerId1Resource = layerId1.getResource();
		Assert.assertNotNull(layerId1Resource);
		String layerId1Uri = layerId1Resource.getURI();
		Assert.assertNotNull(layerId1Uri);
		LOGGER.debug("layerId1Uri: {}", layerId1Uri);
		
		Entity layerId2 = cartography2.getChild(Ld4lObjectProp.IDENTIFIED_BY, HarvardType.HGLID);
		Assert.assertNotNull(layerId2);
		layerId2.buildResource();
		Resource layerId2Resource = layerId2.getResource();
		Assert.assertNotNull(layerId2Resource);
		String layerId2Uri = layerId2Resource.getURI();
		Assert.assertNotNull(layerId2Uri);
		LOGGER.debug("layerId1Uri: {}", layerId2Uri);
		Assert.assertEquals(layerId1Uri, layerId2Uri);
	}
	
	/**
	 * This tests caching of URI for Hollis number Entity
	 */
	@Test
	public void sameUriForSameHollisNumber() throws Exception {
		
		fgdcRecord = buildFgdcRecordFromString(FgdcTestData.VALID_CITEINFO);
		BuildParams params = new BuildParams()
				.setRecord(fgdcRecord);
		Entity cartography1 = cartographyBuilder.build(params);
		
		fgdcRecord = buildFgdcRecordFromString(FgdcTestData.VALID_CITEINFO_DUPLICATE_LAYER_ID_AND_HOLLIS);
		params = new BuildParams()
				.setRecord(fgdcRecord);
		Entity cartography2 = cartographyBuilder.build(params);
		
		Assert.assertNotNull(cartography1);
		Assert.assertNotNull(cartography2);

		Entity hollisEntity1 = cartography1.getChild(Ld4lObjectProp.IDENTIFIED_BY, HarvardType.HOLLIS_NUMBER);
		Assert.assertNotNull(hollisEntity1);
		hollisEntity1.buildResource();
		Resource hollisResource1 = hollisEntity1.getResource();
		Assert.assertNotNull(hollisResource1);
		String hollisUri1 = hollisResource1.getURI();
		Assert.assertNotNull(hollisUri1);
		LOGGER.debug("hollisUri1: {}", hollisUri1);
		
		Entity hollisEntity2 = cartography2.getChild(Ld4lObjectProp.IDENTIFIED_BY, HarvardType.HOLLIS_NUMBER);
		Assert.assertNotNull(hollisEntity2);
		hollisEntity2.buildResource();
		Resource hollisResource2 = hollisEntity2.getResource();
		Assert.assertNotNull(hollisResource2);
		String hollisUri2 = hollisResource2.getURI();
		Assert.assertNotNull(hollisUri2);
		LOGGER.debug("layerId1Uri: {}", hollisUri2);
		Assert.assertEquals(hollisUri1, hollisUri2);
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
