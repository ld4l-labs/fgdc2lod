/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.DatatypeProp;
import org.ld4l.bib2lod.ontology.ObjectProp;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.fgdc.CartographyType;
import org.ld4l.bib2lod.ontology.fgdc.FgdcDatatypeProp;
import org.ld4l.bib2lod.ontology.fgdc.FgdcObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
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
 * Tests the FgdcToGeometryBuilder class.
 */
public class FgdcToGeometryBuilderTest extends AbstractTestClass {
	
    private static BaseMockBib2LodObjectFactory factory;
    
	private EntityBuilder geometryBuilder;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();
        factory.addInstance(EntityBuilderFactory.class, new FgdcToLd4lEntityBuilderFactory());
    }

    @Before
    public void setUp() {
        geometryBuilder = new FgdcToGeometryBuilder();
    }
	
	@Test
	public void testValidRecord() throws Exception {
		
		FgdcRecord record = buildFgdcRecordFromString(FgdcTestData.VALID_BOUNDING);
		
		Entity geometryEntity = testCommonAssertions(record);
		
		List<Attribute> labels = geometryEntity.getValues(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labels);
		Assert.assertEquals(0, labels.size()); // no labels on this entity
		
		// check datatype properties
		MapOfLists<DatatypeProp, Attribute> attrs = geometryEntity.getAttributes();
		Set<DatatypeProp> dataProps = attrs.keys();
		Assert.assertNotNull(dataProps);
		Assert.assertTrue(dataProps.contains(FgdcDatatypeProp.AS_WKT));
		Attribute attr = attrs.getValue(FgdcDatatypeProp.AS_WKT);
		Assert.assertNotNull(attr);
		String literal = attr.getValue();
		Assert.assertNotNull(literal);
		Assert.assertEquals(record.getBoundingField().getWKT(), literal);
		
		// check object properties
		MapOfLists<ObjectProp, Entity> relationships = geometryEntity.getRelationships();
		Assert.assertNotNull(relationships);
		Set<ObjectProp> objectProps = relationships.keys();
		Assert.assertNotNull(objectProps);
		Assert.assertTrue(objectProps.contains(FgdcObjectProp.HAS_SOURCE));
		
		// check external properties
		MapOfLists<ObjectProp, String> externals = geometryEntity.getExternalRelationships();
		Assert.assertNotNull(externals);
		objectProps = externals.keys();
		Assert.assertNotNull(objectProps);
		Assert.assertTrue(objectProps.contains(FgdcObjectProp.HAS_PROJECTION));
	}

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
	
	private Entity testCommonAssertions(FgdcRecord record) throws Exception {
		
		geometryBuilder = geometryBuilder.getBuilder(CartographyType.class);
		Assert.assertNotNull(geometryBuilder);
		Assert.assertTrue(geometryBuilder instanceof FgdcToGeometryBuilder);

		BuildParams params = new BuildParams();
		Entity cartography = new Entity(Ld4lWorkType.CARTOGRAPHY);
		params.setRecord(record);
		params.setRelatedEntity(cartography);
		
		Entity geometryEntity = geometryBuilder.build(params);
		Assert.assertNotNull(geometryEntity);
		
		List<Type> types = geometryEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertTrue(types.contains(CartographyType.GEOMETRY));
		
		return geometryEntity;
	}
    
    private FgdcRecord buildFgdcRecordFromString(String s) 
            throws RecordException {
    	Element element = XmlTestUtils.buildElementFromString(s);
    	return new FgdcRecord(element);
    }
}
