/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcTextOnlyField;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.FgdcTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;

/**
 * Tests the FgdcToAgentBuilder class.
 */
public class FgdcToAgentBuilderTest extends AbstractTestClass {
    
	private EntityBuilder agentBuilder;
	private FgdcField agentField;
	private Entity relatedEntity;

    @Before
    public void setUp() throws RecordException {
        agentBuilder = new FgdcToAgentBuilder();
        agentField = new FgdcTextOnlyField(XmlTestUtils.buildElementFromString(FgdcTestData.VALID_AGENT), Ld4lAgentType.AGENT.toString());
        relatedEntity = new Entity(Ld4lActivityType.ORIGINATOR_ACTIVITY);
    }
	
	@Test
	public void validRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setField(agentField)
				.setRelatedEntity(relatedEntity);
		
		Entity agentEntity = agentBuilder.build(params);

		Assert.assertNotNull(agentEntity);
		List<Type> types = agentEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertEquals(1, types.size());
		Assert.assertEquals(Ld4lAgentType.AGENT, types.get(0));
		
		List<Attribute> labels = agentEntity.getValues(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labels);
		Assert.assertEquals(1, labels.size());
		Assert.assertEquals("Agent Text", labels.get(0).getValue());
	}
	
	@Test
	public void nullAgent_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "An agentField is required to build an Agent.");
		BuildParams params = new BuildParams()
				.setRelatedEntity(relatedEntity)
				.setField(null);
		
		agentBuilder.build(params);
	}
	
	@Test
	public void nullRecordEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A related Entity is required to build a title.");
		BuildParams params = new BuildParams()
				.setRelatedEntity(null)
				.setField(agentField);
		
		agentBuilder.build(params);
	}
}
