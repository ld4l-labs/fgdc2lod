/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.XmlElement;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Tests class FgdcTitle.
 */
public class FgdcTextOnlyFieldTest extends AbstractTestClass {

    private static final String NO_VALUE = "<title/>";
    
    private static final String NO_TEXT_VALUE = 
            "<title><subfield>Some Title</subfield></title>";
      
    private static final String VALID_TITLE = 
            "<title>Some Title</title>";

    private static final String FIELD_NAME = "someField";
 
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void noValue_Invalid() throws Exception {
    	FgdcTextOnlyField field = buildTextOnlyFieldFromString(NO_VALUE, null);
        Assert.assertFalse(field.isValid());
        Assert.assertEquals("", field.getFieldName());
    }
    
    @Test
    public void noValueWithFieldName_Invalid() throws Exception {
    	FgdcTextOnlyField field = buildTextOnlyFieldFromString(NO_VALUE, FIELD_NAME);
    	Assert.assertFalse(field.isValid());
        Assert.assertEquals(FIELD_NAME, field.getFieldName());
    }
    
    @Test
    public void noTextValue_Invalid() throws Exception {
    	FgdcTextOnlyField field = buildTextOnlyFieldFromString(NO_TEXT_VALUE, null);
        Assert.assertFalse(field.isValid());
        Assert.assertEquals("", field.getFieldName());
    }
    
    @Test
    public void noTextValueWithFieldName_Invalid() throws Exception {
    	FgdcTextOnlyField field = buildTextOnlyFieldFromString(NO_TEXT_VALUE, FIELD_NAME);
    	Assert.assertFalse(field.isValid());
        Assert.assertEquals(FIELD_NAME, field.getFieldName());
    }
    
    @Test
    public void validText_Valid() throws Exception {
    	FgdcTextOnlyField field = buildTextOnlyFieldFromString(VALID_TITLE, null);
        Assert.assertTrue(field.isValid());
        Assert.assertEquals("", field.getFieldName());
    }
    
    @Test
    public void validTextWithFieldName_Valid() throws Exception {
    	FgdcTextOnlyField field = buildTextOnlyFieldFromString(VALID_TITLE, FIELD_NAME);
        Assert.assertTrue(field.isValid());
        Assert.assertEquals(FIELD_NAME, field.getFieldName());
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcTextOnlyField buildTextOnlyFieldFromString(String xmlString, String fieldName) 
            throws RecordFieldException {
    	
    	if (fieldName == null) {
    		return (FgdcTextOnlyField) XmlTestUtils.buildElementFromString(
    				FgdcTextOnlyField.class, xmlString);
    	} else {
    		return instance(FgdcTextOnlyField.class, xmlString, fieldName);
    	}
    }

    private FgdcTextOnlyField instance(Class<?> elementClass, String xmlString, String fieldName) 
            throws RecordFieldException {
        try {
        	Element element = DocumentBuilderFactory
        			.newInstance()
        			.newDocumentBuilder()
        			.parse(new ByteArrayInputStream(xmlString.getBytes()))
        			.getDocumentElement();
            return (FgdcTextOnlyField) elementClass
                    .getConstructor(Element.class, String.class)
                    .newInstance(element, fieldName);
        } catch (InstantiationException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException | SAXException | IOException | ParserConfigurationException e) {
            throw new RecordFieldException(e);
        }     
    }
}
