/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.fgdc;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.fgdc.FgdcToItemBuilder;
import org.ld4l.bib2lod.ontology.ObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.FgdcTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.ld4l.bib2lod.util.collections.MapOfLists;
import org.w3c.dom.Element;

/**
 * Tests the FgdcToItemBuilder class.
 */
public class FgdcToItemBuilderTest extends AbstractTestClass {
    
	private EntityBuilder itemBuilder;
	private FgdcRecord fgdcRecord;
	private Entity relatedEntity;
	
    @Before
    public void setUp() throws RecordException {
        itemBuilder = new FgdcToItemBuilder();
        fgdcRecord = buildFgdcRecordFromString(FgdcTestData.VALID_ACTIVITIES);
        relatedEntity = new Entity(Ld4lInstanceType.INSTANCE);
    }
	
	@Test
	public void validItemRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setRecord(fgdcRecord)
				.setParent(relatedEntity);
		
		Entity itemEntity = itemBuilder.build(params);

		Assert.assertNotNull(itemEntity);
		
		List<String> labels = itemEntity.getValues(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labels);
		Assert.assertEquals(1, labels.size());
		Assert.assertEquals(fgdcRecord.getCiteinfoField().getTitle().getTextValue(), labels.get(0));
		
		MapOfLists<ObjectProp, String> externals = itemEntity.getExternalRelationships();
		Assert.assertNotNull(externals);
		Assert.assertEquals(1, externals.keys().size());

		List<String> electronicLocators = externals.getValues(Ld4lObjectProp.HAS_ELECTRONIC_LOCATOR);
		Assert.assertNotNull(electronicLocators);
		Assert.assertEquals(1, electronicLocators.size());
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A FgdcRecord is required to build an Item.");
		BuildParams params = new BuildParams()
				.setRecord(null)
				.setParent(relatedEntity);
		
		itemBuilder.build(params);
	}
	
	@Test
	public void nullRelatedEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A related Entity is required to build an Item.");
		BuildParams params = new BuildParams()
				.setRecord(fgdcRecord)
				.setParent(null);
		
		itemBuilder.build(params);
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
