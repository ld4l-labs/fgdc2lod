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
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.fgdc.FgdcToAgentBuilder;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.record.xml.fgdc.BaseFgdcField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcTextField;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.FgdcTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.ld4l.bib2lod.uris.RandomUriMinter;
import org.ld4l.bib2lod.uris.UriService;

/**
 * Tests the FgdcToAgentBuilder class.
 */
public class FgdcToAgentBuilderTest extends AbstractTestClass {
    
	private EntityBuilder agentBuilder;
	private BaseFgdcField agentField;
	private Entity relatedEntity;
	
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
        agentBuilder = new FgdcToAgentBuilder();
        agentField = new FgdcTextField(XmlTestUtils.buildElementFromString(FgdcTestData.VALID_AGENT), Ld4lAgentType.AGENT.toString());
        relatedEntity = new Entity(Ld4lActivityType.ORIGINATOR_ACTIVITY);
    }
	
	@Test
	public void validRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setField(agentField)
				.setParent(relatedEntity);
		
		Entity agentEntity = agentBuilder.build(params);

		Assert.assertNotNull(agentEntity);
		List<Type> types = agentEntity.getTypes();
		Assert.assertEquals(1, types.size());
		Assert.assertEquals(Ld4lAgentType.AGENT, types.get(0));
		
		List<String> labels = agentEntity.getValues(Ld4lDatatypeProp.LABEL);
		Assert.assertEquals(1, labels.size());
		Assert.assertEquals("Agent Text", labels.get(0));
	}
	
	@Test
	public void nullAgent_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "An agentField is required to build an Agent.");
		BuildParams params = new BuildParams()
				.setParent(relatedEntity)
				.setField(null);
		
		agentBuilder.build(params);
	}
	
	@Test
	public void nullRelatedEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A related Entity is required to build a title.");
		BuildParams params = new BuildParams()
				.setParent(null)
				.setField(agentField);
		
		agentBuilder.build(params);
	}
	
	@Test
	public void sameUrlForSameAgent() throws Exception {
		
		BuildParams params = new BuildParams()
				.setField(agentField)
				.setParent(relatedEntity);
		
		Entity agentEntity1 = agentBuilder.build(params);
		Assert.assertNotNull(agentEntity1);
		agentEntity1.buildResource();
		Resource agentResource1 = agentEntity1.getResource();
		Assert.assertNotNull(agentResource1);
		String agentUri1 = agentResource1.getURI();
		Assert.assertNotNull(agentUri1);
		LOGGER.debug("agentUri1: {}", agentUri1);

        agentField = new FgdcTextField(XmlTestUtils.buildElementFromString(FgdcTestData.VALID_DUPLICATE_AGENT), Ld4lAgentType.AGENT.toString());
        params = new BuildParams()
				.setField(agentField)
				.setParent(relatedEntity);
		
		Entity agentEntity2 = agentBuilder.build(params);
		Assert.assertNotNull(agentEntity2);
		agentEntity2.buildResource();
		Resource agentResource2 = agentEntity2.getResource();
		Assert.assertNotNull(agentResource2);
		String agentUri2 = agentResource2.getURI();
		Assert.assertNotNull(agentUri2);
		LOGGER.debug("agentUri2: {}", agentUri2);
		Assert.assertEquals(agentUri1, agentUri2);
	}
}
