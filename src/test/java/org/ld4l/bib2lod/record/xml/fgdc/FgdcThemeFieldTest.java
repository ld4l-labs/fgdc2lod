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
 * Tests class FgdcThemeField.
 */
public class FgdcThemeFieldTest extends AbstractTestClass {

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void validThemeField() throws Exception {
    	FgdcThemeField fgdcThemeField = buildFgdcThemeFieldFromString(FgdcTestData.VALID_THEME);
    	BaseFgdcField themeKt = fgdcThemeField.getThemeKt();
    	Assert.assertNotNull(themeKt);
    	List<FgdcTextField> themeKeys = fgdcThemeField.getThemeKeys();
    	Assert.assertNotNull(themeKeys);
    	Assert.assertEquals(3, themeKeys.size());
    }
    
    @Test
    public void missingThemeKtValue_invalid() throws Exception {
       	expectException(RecordFieldException.class, "themekt is null");
    	buildFgdcThemeFieldFromString(FgdcTestData.INVALID_THEME_MISSING_THEME_KT);
    }
    
    @Test
    public void emtpyThemeKtValue_invalid() throws Exception {
       	expectException(RecordFieldException.class, "text value is null");
    	buildFgdcThemeFieldFromString(FgdcTestData.INVALID_THEME_EMPTY_THEME_KT);
    }
    
    @Test
    public void missingThemeKeyValue_invalid() throws Exception {
       	expectException(RecordFieldException.class, "themekeys is empty");
    	buildFgdcThemeFieldFromString(FgdcTestData.INVALID_THEME_MISSING_THEME_KEY);
    }
    
    @Test
    public void emptyThemeKeyValue_invalid() throws Exception {
       	expectException(RecordFieldException.class, "text value is null");
    	buildFgdcThemeFieldFromString(FgdcTestData.INVALID_THEME_EMPTY_THEME_KEY);
    }
    
    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcThemeField buildFgdcThemeFieldFromString(String xmlString) 
            throws RecordException {
    	
    	Element element = XmlTestUtils.buildElementFromString(xmlString);
		return new FgdcThemeField(element);
    }
}
