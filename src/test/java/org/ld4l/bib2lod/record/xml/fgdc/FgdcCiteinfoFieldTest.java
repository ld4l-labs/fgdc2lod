/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Test;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.FgdcTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Element;

/**
 * Tests class FgdcCiteinfoField.
 */
public class FgdcCiteinfoFieldTest extends AbstractTestClass {

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------

    @Test
    public void missingTitle_Invalid() throws Exception {
       	expectException(RecordFieldException.class, "title is null");
       	buildFgdcCiteinfoFieldFromString(FgdcTestData.INVALID_CITEINFO_MISSING_TITLE);
    }
    
    @Test
    public void validCiteinfo() throws Exception {
    	buildFgdcCiteinfoFieldFromString(FgdcTestData.VALID_CITEINFO);
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcCiteinfoField buildFgdcCiteinfoFieldFromString(String xmlString) 
            throws RecordException {
    	
    	Element element = XmlTestUtils.buildElementFromString(xmlString);
		return new FgdcCiteinfoField(element);
    }
}
