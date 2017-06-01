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
 * Tests class FgdcKeywordsField.
 */
public class FgdcKeywordsFieldTest extends AbstractTestClass {

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void validKeywordsField() throws Exception {
    	FgdcKeywordsField fgdcKeywordsField = buildFgdcCiteinfoFieldFromString(FgdcTestData.VALID_KEYWORDS);
    	List<FgdcThemeField> themes = fgdcKeywordsField.getThemes();
    	Assert.assertNotNull(themes);
    	Assert.assertEquals(3, themes.size());
    	List<FgdcPlaceField> places = fgdcKeywordsField.getPlaces();
    	Assert.assertNotNull(places);
    	Assert.assertEquals(1, places.size());
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcKeywordsField buildFgdcCiteinfoFieldFromString(String xmlString) 
            throws RecordException {
    	
    	Element element = XmlTestUtils.buildElementFromString(xmlString);
		return new FgdcKeywordsField(element);
    }
}
