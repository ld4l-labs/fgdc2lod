/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.FgdcTestData;
import org.w3c.dom.Element;

/**
 * Tests class FgdcPlaceField.
 */
public class FgdcPlaceFieldTest extends AbstractTestClass {

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void validPlaceField() throws Exception {
    	FgdcPlaceField fgdcPlaceField = buildFgdcCiteinfoFieldFromString(FgdcTestData.VALID_PLACE);
    	FgdcField placeKt = fgdcPlaceField.getPlaceKt();
    	Assert.assertNotNull(placeKt);
    	List<FgdcField> placeKeys = fgdcPlaceField.getPlaceKeys();
    	Assert.assertNotNull(placeKeys);
    	Assert.assertEquals(2, placeKeys.size());
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcPlaceField buildFgdcCiteinfoFieldFromString(String xmlString) 
            throws RecordException {
    	
    	Element element = XmlTestUtils.buildElementFromString(xmlString);
    	return new FgdcPlaceField(element);
    }
}
