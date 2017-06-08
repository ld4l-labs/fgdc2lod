/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.FgdcTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
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
    	FgdcPlaceField fgdcPlaceField = buildFgdcPlaceFieldFromString(FgdcTestData.VALID_PLACE);
    	FgdcField placeKt = fgdcPlaceField.getPlaceKt();
    	Assert.assertNotNull(placeKt);
    	List<FgdcField> placeKeys = fgdcPlaceField.getPlaceKeys();
    	Assert.assertNotNull(placeKeys);
    	Assert.assertEquals(2, placeKeys.size());
    }
    
    @Test
    public void missingThemeKtValue_invalid() throws Exception {
       	expectException(RecordFieldException.class, "placekt is null");
       	buildFgdcPlaceFieldFromString(FgdcTestData.INVALID_PLACE_MISSING_PLACE_KT);
    }
    
    @Test
    public void emtpyThemeKtValue_invalid() throws Exception {
       	expectException(RecordFieldException.class, "text value is null");
       	buildFgdcPlaceFieldFromString(FgdcTestData.INVALID_PLACE_EMPTY_PLACE_KT);
    }
    
    @Test
    public void missingThemeKeyValue_invalid() throws Exception {
       	expectException(RecordFieldException.class, "placekeys is empty");
       	buildFgdcPlaceFieldFromString(FgdcTestData.INVALID_PLACE_MISSING_PLACE_KEY);
    }
    
    @Test
    public void emptyThemeKeyValue_invalid() throws Exception {
       	expectException(RecordFieldException.class, "text value is null");
       	buildFgdcPlaceFieldFromString(FgdcTestData.INVALID_PLACE_EMPTY_PLACE_KEY);
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcPlaceField buildFgdcPlaceFieldFromString(String xmlString) 
            throws RecordException {
    	
    	Element element = XmlTestUtils.buildElementFromString(xmlString);
    	return new FgdcPlaceField(element);
    }
}
