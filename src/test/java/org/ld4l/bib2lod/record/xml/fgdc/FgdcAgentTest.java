package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class FgdcAgent.
 */
public class FgdcAgentTest extends AbstractTestClass {

    private static final String NO_VALUE = "<publish/>";
    
    private static final String NO_TEXT_VALUE = 
            "<publish><subfield>Some Agent</subfield></publish>";
      
    private static final String VALID_AGENT = 
            "<publish>Some Agent</publish>";

 
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void noValue_Invalid() throws Exception {
    	FgdcAgent agent = buildTitleFromString(NO_VALUE);
        Assert.assertFalse(agent.isValid());
    }
    
    @Test
    public void noTextValue_Invalid() throws Exception {
    	FgdcAgent agent = buildTitleFromString(NO_TEXT_VALUE);
        Assert.assertFalse(agent.isValid());
    }
    
    @Test
    public void validTitle_Valid() throws Exception {
    	FgdcAgent agent = buildTitleFromString(VALID_AGENT);
        Assert.assertTrue(agent.isValid());
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcAgent buildTitleFromString(String s) 
            throws RecordFieldException {
        return (FgdcAgent) XmlTestUtils.buildElementFromString(
        		FgdcAgent.class, s);
    }
}
