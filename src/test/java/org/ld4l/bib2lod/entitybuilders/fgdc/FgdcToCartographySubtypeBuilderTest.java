/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.fgdc;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.fgdc.CartographySubType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.FgdcTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Element;

/**
 * Tests the FgdcToCartographySubtypeBuilder class.
 */
public class FgdcToCartographySubtypeBuilderTest extends AbstractTestClass {
    
	private EntityBuilder subtypeBuilder;
	private FgdcRecord fgdcRecord;
	private Entity relatedEntity;

    @Before
    public void setUp() throws RecordException, EntityBuilderException {
        subtypeBuilder = new FgdcToCartographySubtypeBuilder();
        fgdcRecord = buildFgdcRecordFromString(FgdcTestData.VALID_KEYWORDS);
        relatedEntity = new Entity(Ld4lWorkType.CARTOGRAPHY);
    }
	
	@Test
	public void validRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setRecord(fgdcRecord)
				.setParent(relatedEntity);
		
		Entity cartographyEntity = subtypeBuilder.build(params);

		Assert.assertNotNull(cartographyEntity);
		List<Type> types = cartographyEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertEquals(3, types.size());
		// Only check subtypes
		Assert.assertTrue(types.contains(CartographySubType.CART_MAPS));
		Assert.assertTrue(types.contains(CartographySubType.CART_GEOREFERENCED_RESOURCES));
	}
	
	@Test
	public void validButNonMatchingConcordanceRecord() throws Exception {

		fgdcRecord = buildFgdcRecordFromString(FgdcTestData.VALID_BUT_NON_MATCHING_CONCORDANCE_SUBTYPE);
		
		BuildParams params = new BuildParams()
				.setRecord(fgdcRecord)
				.setParent(relatedEntity);
		
		Entity cartographyEntity = subtypeBuilder.build(params);

		Assert.assertNotNull(cartographyEntity);
		List<Type> types = cartographyEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertFalse(types.contains(CartographySubType.CART_MAPS));
		Assert.assertFalse(types.contains(CartographySubType.CART_GEOREFERENCED_RESOURCES));
		
		Attribute commentAttr = cartographyEntity.getAttribute(Ld4lDatatypeProp.COMMENT);
		Assert.assertNotNull(commentAttr);
		Assert.assertTrue(commentAttr.getValue().contains(FgdcTestData.NON_MATCHING_CONCORDANCE_SUBTYPE_TEXT));
		Assert.assertNotNull(commentAttr.getDatatype());
		Assert.assertEquals(Ld4lCustomDatatypes.BibDatatype.LEGACY_SOURCE_DATA, commentAttr.getDatatype());
	}
	
	@Test
	public void missingGeoForm() throws Exception {
		// valid test but with no <geoform> element subtype won't be added to Entity
		FgdcRecord noGeoformRecord = buildFgdcRecordFromString(FgdcTestData.VALID_CITEINFO);
		BuildParams params = new BuildParams()
				.setRecord(noGeoformRecord)
				.setParent(relatedEntity);
		
		Entity cartographyEntity = subtypeBuilder.build(params);

		Assert.assertNotNull(cartographyEntity);
		List<Type> types = cartographyEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertEquals(1, types.size());
		Assert.assertTrue(types.contains(Ld4lWorkType.CARTOGRAPHY));
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A FgdcRecord is required to build a CartographySubtype.");
		BuildParams params = new BuildParams()
				.setParent(relatedEntity)
				.setRecord(null);
		
		subtypeBuilder.build(params);
	}
	
	@Test
	public void nullRelatedEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A related Entity is required to build a cartography subtype.");
		BuildParams params = new BuildParams()
				.setParent(null)
				.setRecord(fgdcRecord);
		
		subtypeBuilder.build(params);
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
