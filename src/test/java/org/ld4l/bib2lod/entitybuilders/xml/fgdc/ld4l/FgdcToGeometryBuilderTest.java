/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.DatatypeProp;
import org.ld4l.bib2lod.ontology.ObjectProp;
import org.ld4l.bib2lod.ontology.fgdc.FgdcDatatypeProp;
import org.ld4l.bib2lod.ontology.fgdc.FgdcObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.FgdcTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.ld4l.bib2lod.util.collections.MapOfLists;
import org.w3c.dom.Element;

/**
 * Tests the FgdcToGeometryBuilder class.
 */
public class FgdcToGeometryBuilderTest extends AbstractTestClass {
    
	private EntityBuilder geometryBuilder;
	private FgdcRecord fgdcRecord;
	private Entity relatedEntity;

    @Before
    public void setUp() throws RecordException {
        geometryBuilder = new FgdcToGeometryBuilder();
        fgdcRecord = buildFgdcRecordFromString(FgdcTestData.VALID_BOUNDING);
        relatedEntity = new Entity(Ld4lWorkType.CARTOGRAPHY);
    }
	
	@Test
	public void testValidRecord() throws Exception {

		BuildParams params = new BuildParams();
		params.setRecord(fgdcRecord);
		params.setRelatedEntity(relatedEntity);
		
		Entity geometryEntity = geometryBuilder.build(params);
		
		Assert.assertNotNull(geometryEntity);
		
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
		Assert.assertEquals(fgdcRecord.getBoundingField().getWKT(), literal);
		
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
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A FgdcRecord is required to build a geometry.");
		BuildParams params = new BuildParams();
		params.setRecord(null);
		params.setRelatedEntity(relatedEntity);
		
		geometryBuilder.build(params);
	}
	
	@Test
	public void nullRelatedEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A related Entity is required to build a geometry.");
		BuildParams params = new BuildParams();
		params.setRecord(fgdcRecord);
		params.setRelatedEntity(null);
		
		geometryBuilder.build(params);
	}
    
    private FgdcRecord buildFgdcRecordFromString(String s) 
            throws RecordException {
    	Element element = XmlTestUtils.buildElementFromString(s);
    	return new FgdcRecord(element);
    }
}
