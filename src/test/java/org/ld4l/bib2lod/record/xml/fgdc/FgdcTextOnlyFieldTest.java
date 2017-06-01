/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;

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
    	expectException(RecordFieldException.class, "text value is null");
    	buildTextOnlyFieldFromString(NO_VALUE, null);
    }
    
    @Test
    public void noValueWithFieldName_Invalid() throws Exception {
       	expectException(RecordFieldException.class, "text value is null");
    	buildTextOnlyFieldFromString(NO_VALUE, FIELD_NAME);
    }
    
    @Test
    public void noTextValue_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "text value is null");
    	buildTextOnlyFieldFromString(NO_TEXT_VALUE, null);
    }
    
    @Test
    public void noTextValueWithFieldName_Invalid() throws Exception {
       	expectException(RecordFieldException.class, "text value is null");
    	buildTextOnlyFieldFromString(NO_TEXT_VALUE, FIELD_NAME);
    }
    
    @Test
    public void validTextNoFieldName_Valid() throws Exception {
    	FgdcTextOnlyField field = buildTextOnlyFieldFromString(VALID_TITLE, null);
        Assert.assertEquals("", field.getFieldName());
    }
    
    @Test
    public void validTextWithFieldName_Valid() throws Exception {
    	FgdcTextOnlyField field = buildTextOnlyFieldFromString(VALID_TITLE, FIELD_NAME);
        Assert.assertEquals(FIELD_NAME, field.getFieldName());
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcTextOnlyField buildTextOnlyFieldFromString(String xmlString, String fieldName) 
            throws RecordException {
    	
    	Element element = XmlTestUtils.buildElementFromString(xmlString);
		return new FgdcTextOnlyField(element, fieldName);
    }
}
