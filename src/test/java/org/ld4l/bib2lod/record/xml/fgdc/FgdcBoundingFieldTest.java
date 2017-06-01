/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.FgdcTestData;
import org.w3c.dom.Element;

/**
 * Tests class FgdcBoundingField.
 */
public class FgdcBoundingFieldTest extends AbstractTestClass {

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------

    @Test
    public void missingWestValue_Invalid() throws Exception {
       	expectException(RecordFieldException.class, "west bounding is null");
       	buildFgdcBoundingFieldFromString(FgdcTestData.INVALID_BOUNDING_MISSING_WEST);
    }
    
    @Test
    public void emptyWestValue_Invalid() throws Exception {
       	expectException(RecordFieldException.class, "text value is null"); // node not empty yet text value is null
       	buildFgdcBoundingFieldFromString(FgdcTestData.INVALID_BOUNDING_EMPTY_WEST);
    }
    
    @Test
    public void validBounding() throws Exception {
    	buildFgdcBoundingFieldFromString(FgdcTestData.VALID_BOUNDING);
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcBoundingField buildFgdcBoundingFieldFromString(String xmlString) 
            throws RecordException {
    	
    	Element element = XmlTestUtils.buildElementFromString(xmlString);
    	return new FgdcBoundingField(element);
    }
}
