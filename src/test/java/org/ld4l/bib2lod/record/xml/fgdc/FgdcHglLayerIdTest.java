package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;

/**
 * Tests class FgdcHglLayerId.
 */
public class FgdcHglLayerIdTest extends AbstractTestClass {

    private static final String NO_VALUE = "<metadata/>";
    
    private static final String NO_ATTRIBUTE_VALUE = 
            "<metadata layerid=\"\"></metadata>";
      
    private static final String VALID_record = 
            "<metadata layerid=\"some_value\"></metadata>";

 
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void noValue_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "text value is empty");
    	buildFgdcRecordFromString(NO_VALUE);
    }
    
    @Test
    public void noTextValue_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "text value is empty");
    	buildFgdcRecordFromString(NO_ATTRIBUTE_VALUE);
    }
    
    @Test
    public void validrecord_Valid() throws Exception {
        // No exception
    	FgdcRecord record = buildFgdcRecordFromString(VALID_record);
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcRecord buildFgdcRecordFromString(String s) 
            throws RecordException {
    	Element element = XmlTestUtils.buildElementFromString(s);
    	return new FgdcRecord(element);
    }
}
