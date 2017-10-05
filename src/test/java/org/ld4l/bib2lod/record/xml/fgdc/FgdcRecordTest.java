package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Element;

/**
 * Tests class FgdcRecord.
 */
public class FgdcRecordTest extends AbstractTestClass {

    private static final String NO_VALUE = "<metadata/>";
    
    private static final String NO_ATTRIBUTE_VALUE = 
            "<metadata layerid=''></metadata>";
      
    private static final String VALID_record = 
            "<metadata layerid='some_value' hollisno='1234'></metadata>";

 
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
    	FgdcRecord record = buildFgdcRecordFromString(VALID_record);
    	Assert.assertNotNull(record);
    	String hollisNumber = record.getHollisNumber();
    	Assert.assertNotNull(hollisNumber);
    	Assert.assertEquals("1234", hollisNumber);
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
