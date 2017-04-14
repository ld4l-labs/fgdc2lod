/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.record.RecordField.RecordFieldException;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class MarcxmlLeader.
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
    	FgdcTitle title = buildTitleFromString(NO_VALUE);
        Assert.assertFalse(title.isValid());
    }
    
    @Test
    public void noTextValue_Invalid() throws Exception {
    	FgdcTitle title = buildTitleFromString(NO_TEXT_VALUE);
        Assert.assertFalse(title.isValid());
    }
    
    @Test
    public void validTitle_Valid() throws Exception {
    	FgdcTitle title = buildTitleFromString(VALID_TITLE);
        Assert.assertTrue(title.isValid());
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcTitle buildTitleFromString(String s) 
            throws RecordFieldException {
        return (FgdcTitle) XmlTestUtils.buildElementFromString(
        		FgdcTitle.class, s);
    }
}
