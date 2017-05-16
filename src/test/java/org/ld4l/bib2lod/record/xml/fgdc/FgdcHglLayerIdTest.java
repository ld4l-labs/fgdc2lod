package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractTestClass;

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
    	FgdcRecord record = buildFgdcRecordFromString(NO_VALUE);
        Assert.assertFalse(record.isValid());
    }
    
    @Test
    public void noTextValue_Invalid() throws Exception {
    	FgdcRecord record = buildFgdcRecordFromString(NO_ATTRIBUTE_VALUE);
        Assert.assertFalse(record.isValid());
    }
    
    @Test
    public void validrecord_Valid() throws Exception {
    	FgdcRecord record = buildFgdcRecordFromString(VALID_record);
        Assert.assertTrue(record.isValid());
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcRecord buildFgdcRecordFromString(String s) 
            throws RecordException {
        return (FgdcRecord) XmlTestUtils.buildRecordFromString(
        		FgdcRecord.class, s);
    }
}
