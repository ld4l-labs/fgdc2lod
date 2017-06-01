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
 * Tests class FgdcThemeField.
 */
public class FgdcThemeFieldTest extends AbstractTestClass {

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void validPlaceField() throws Exception {
    	FgdcThemeField fgdcThemeField = buildFgdcCiteinfoFieldFromString(FgdcTestData.VALID_THEME);
    	FgdcField themeKt = fgdcThemeField.getThemeKt();
    	Assert.assertNotNull(themeKt);
    	List<FgdcField> themeKeys = fgdcThemeField.getThemeKeys();
    	Assert.assertNotNull(themeKeys);
    	Assert.assertEquals(3, themeKeys.size());
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcThemeField buildFgdcCiteinfoFieldFromString(String xmlString) 
            throws RecordException {
    	
    	Element element = XmlTestUtils.buildElementFromString(xmlString);
		return new FgdcThemeField(element);
    }
}
