package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;

/**
 * Tests class FgdcRecord.
 */
public class FgdcRecordTest extends AbstractTestClass {

    private static final String NO_VALUE = "<metadata/>";
    
    private static final String NO_ATTRIBUTE_VALUE = 
            "<metadata layerid=''></metadata>";
      
    private static final String VALID_record = 
            "<metadata layerid='some_value'></metadata>";

 
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void noAttribute_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "layerId attribute is empty");
    	buildFgdcRecordFromString(NO_VALUE);
    }
    
    @Test
    public void noAttributeValue_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "layerId attribute is empty");
    	buildFgdcRecordFromString(NO_ATTRIBUTE_VALUE);
    }
    
    @Test
    public void validRecord_Valid() throws Exception {
        // No exception
    	buildFgdcRecordFromString(VALID_record);
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
