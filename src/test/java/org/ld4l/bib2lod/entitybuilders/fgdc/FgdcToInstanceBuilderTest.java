/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.fgdc;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.fgdc.FgdcToInstanceBuilder;
import org.ld4l.bib2lod.entitybuilders.fgdc.FgdcToLd4lEntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ObjectProp;
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
 * Tests the FgdcToInstanceBuilder class.
 */
public class FgdcToInstanceBuilderTest extends AbstractTestClass {
    
	private EntityBuilder instanceBuilder;
	private FgdcRecord fgdcRecord;
	private Entity relatedEntity;
	
    private static BaseMockBib2LodObjectFactory factory;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();
        factory.addInstance(EntityBuilderFactory.class, new FgdcToLd4lEntityBuilderFactory());
    }

    @Before
    public void setUp() throws RecordException {
        instanceBuilder = new FgdcToInstanceBuilder();
        fgdcRecord = buildFgdcRecordFromString(FgdcTestData.VALID_ACTIVITIES);
        relatedEntity = new Entity(Ld4lWorkType.CARTOGRAPHY);
    }
	
	@Test
	public void validInstanceRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setRecord(fgdcRecord)
				.setParent(relatedEntity);
		
		Entity instanceEntity = instanceBuilder.build(params);

		Assert.assertNotNull(instanceEntity);
		
		List<String> labels = instanceEntity.getValues(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labels);
		Assert.assertEquals(1, labels.size());
		Assert.assertEquals(fgdcRecord.getCiteinfoField().getTitle().getTextValue(), labels.get(0));
		
		MapOfLists<ObjectProp, Entity> relationships = instanceEntity.getRelationships();
		Assert.assertNotNull(relationships);
		Assert.assertEquals(3, relationships.keys().size());
		
		List<Entity> titles = relationships.getValues(Ld4lObjectProp.HAS_TITLE);
		Assert.assertNotNull(titles);
		Assert.assertEquals(1, titles.size());
		Assert.assertEquals(fgdcRecord.getCiteinfoField().getTitle().getTextValue(), titles.get(0).getAttribute(Ld4lDatatypeProp.LABEL).getValue());

		List<Entity> items = relationships.getValues(Ld4lObjectProp.HAS_ITEM);
		Assert.assertNotNull(items);
		Assert.assertEquals(1, items.size());
		
		List<Entity> activities = relationships.getValues(Ld4lObjectProp.HAS_ACTIVITY);
		Assert.assertNotNull(activities);
		Assert.assertEquals(1, activities.size());
		
		List<String> editions = instanceEntity.getValues(Ld4lDatatypeProp.EDITION_STATEMENT);
		Assert.assertNotNull(editions);
		Assert.assertEquals(1, editions.size());
		Assert.assertEquals(fgdcRecord.getCiteinfoField().getEdition().getTextValue(), editions.get(0));
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A FgdcRecord is required to build an Instance.");
		BuildParams params = new BuildParams()
				.setRecord(null)
				.setParent(relatedEntity);
		
		instanceBuilder.build(params);
	}
	
	@Test
	public void nullRelatedEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A related Entity is required to build an Instance.");
		BuildParams params = new BuildParams()
				.setRecord(fgdcRecord)
				.setParent(null);
		
		instanceBuilder.build(params);
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
