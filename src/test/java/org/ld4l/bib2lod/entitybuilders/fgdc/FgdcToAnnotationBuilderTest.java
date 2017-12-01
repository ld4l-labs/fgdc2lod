/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.fgdc;

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
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAnnotationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.fgdc.BaseFgdcField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcKeywordsField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcTextField;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.FgdcTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;

/**
 * Tests the FgdcToAgentBuilder class.
 */
public class FgdcToAnnotationBuilderTest extends AbstractTestClass {
    
	private EntityBuilder annotationBuilder;
	private FgdcTextField abstractField;
	private FgdcTextField purposeField;
	
	private Entity relatedEntity;

    @Before
    public void setUp() throws RecordException {
        annotationBuilder = new FgdcToAnnotationBuilder();
        abstractField = new FgdcTextField(XmlTestUtils.buildElementFromString(FgdcTestData.VALID_ABSTRACT), "abstract");
        purposeField = new FgdcTextField(XmlTestUtils.buildElementFromString(FgdcTestData.VALID_ABSTRACT), "purpose");
        relatedEntity = new Entity(Ld4lWorkType.CARTOGRAPHY);
    }
	
	@Test
	public void validPurposeRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setField(purposeField)
				.setParent(relatedEntity);
		
		Entity purposeEntity = annotationBuilder.build(params);

		Assert.assertNotNull(purposeEntity);
		List<Type> types = purposeEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertEquals(1, types.size());
		Assert.assertEquals(Ld4lAnnotationType.ANNOTATION, types.get(0));
		
		List<String> externals = purposeEntity.getExternals(Ld4lObjectProp.MOTIVATED_BY);
		Assert.assertEquals(1, externals.size());
		String motivator = externals.get(0);
		Assert.assertEquals(Ld4lNamedIndividual.PROVIDING_PURPOSE.uri(), motivator);

		Entity creator = purposeEntity.getChild(Ld4lObjectProp.HAS_CREATOR);
		Assert.assertNotNull(creator);
		Assert.assertEquals(Ld4lNamedIndividual._134059638.uri(), creator.getResource().getURI());
		Attribute attr = creator.getAttribute(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(attr);
		Assert.assertEquals("Harvard Map Collection", attr.getValue());
		
		Entity textualBody = purposeEntity.getChild(Ld4lObjectProp.HAS_BODY);
		Assert.assertNotNull(textualBody);
		Assert.assertEquals(abstractField.getTextValue(), textualBody.getAttribute(Ld4lDatatypeProp.VALUE).getValue());
	}
	
	@Test
	public void validAbstractRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setField(abstractField)
				.setParent(relatedEntity);
		
		Entity abstractRecordEntity = annotationBuilder.build(params);

		Assert.assertNotNull(abstractRecordEntity);
		List<Type> types = abstractRecordEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertEquals(1, types.size());
		Assert.assertEquals(Ld4lAnnotationType.ANNOTATION, types.get(0));
		
		List<String> externals = abstractRecordEntity.getExternals(Ld4lObjectProp.MOTIVATED_BY);
		Assert.assertEquals(1, externals.size());
		String motivator = externals.get(0);
		Assert.assertEquals(Ld4lNamedIndividual.SUMMARIZING.uri(), motivator);

		Entity creator = abstractRecordEntity.getChild(Ld4lObjectProp.HAS_CREATOR);
		Assert.assertNotNull(creator);
		Assert.assertEquals(Ld4lNamedIndividual._134059638.uri(), creator.getResource().getURI());
		Attribute attr = creator.getAttribute(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(attr);
		Assert.assertEquals("Harvard Map Collection", attr.getValue());
		
		Entity textualBody = abstractRecordEntity.getChild(Ld4lObjectProp.HAS_BODY);
		Assert.assertNotNull(textualBody);
		Assert.assertEquals(abstractField.getTextValue(), textualBody.getAttribute(Ld4lDatatypeProp.VALUE).getValue());
	}
	
	@Test
	public void nullAgent_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "field is null");
		BuildParams params = new BuildParams()
				.setParent(relatedEntity)
				.setField(null);
		
		annotationBuilder.build(params);
	}
	
	@Test
	public void nullRecordEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A related Entity is required to build an Annotation.");
		BuildParams params = new BuildParams()
				.setParent(null)
				.setField(purposeField);
		
		annotationBuilder.build(params);
	}
	
	@Test
	public void nullField_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "field is null");
		BuildParams params = new BuildParams()
				.setParent(relatedEntity)
				.setField(null);
		
		annotationBuilder.build(params);
	}
	
	@Test
	public void wrongFieldType_ThrowsException() throws Exception {
		BaseFgdcField keywordsField = new FgdcKeywordsField(XmlTestUtils.buildElementFromString(FgdcTestData.VALID_KEYWORDS));
		expectException(EntityBuilderException.class, "field not instanceof FgdcTextOnlyField");
		BuildParams params = new BuildParams()
				.setParent(relatedEntity)
				.setField(keywordsField);
		
		annotationBuilder.build(params);
	}
	
	@Test
	public void unknownAnnotationTypeField_ThrowsException() throws Exception {
		FgdcTextField unknownFieldName = new FgdcTextField(XmlTestUtils.buildElementFromString(FgdcTestData.VALID_ABSTRACT), "unknown");
		expectException(EntityBuilderException.class, unknownFieldName.getFieldName() + " type Annotation not yet supported.");
		BuildParams params = new BuildParams()
				.setParent(relatedEntity)
				.setField(unknownFieldName);
		
		annotationBuilder.build(params);
	}
}
