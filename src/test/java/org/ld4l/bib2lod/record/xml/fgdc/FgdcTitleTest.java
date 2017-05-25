/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;

/**
 * Tests class FgdcTitle.
 */
public class FgdcTitleTest extends AbstractTestClass {

    private static final String NO_VALUE = "<title/>";
    
    private static final String NO_TEXT_VALUE = 
            "<title><subfield>Some Title</subfield></title>";
      
    private static final String VALID_TITLE = 
            "<title>Some Title</title>";

 
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void noValue_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "text value is null");
    	buildTitleFromString(NO_VALUE);
    }
    
    @Test
    public void noTextValue_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "text value is null");
    	buildTitleFromString(NO_TEXT_VALUE);
    }
    
    @Test
    public void validTitle_Valid() throws Exception {
        // No exception
    	buildTitleFromString(VALID_TITLE);
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcTitle buildTitleFromString(String s) 
            throws RecordException {
    	Element element = XmlTestUtils.buildElementFromString(s);
    	return new FgdcTitle(element);
    }
}
