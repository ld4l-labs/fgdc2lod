package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;

/**
 * Tests class FgdcAgent.
 */
public class FgdcAgentTest extends AbstractTestClass {

    private static final String NO_VALUE = "<publish/>";
    
    private static final String NO_TEXT_VALUE = 
            "<publish></publish>";
      
    private static final String VALID_AGENT = 
            "<publish>Some Agent</publish>";

 
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void noValue_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "text value is null");
    	buildTitleFromString(NO_VALUE);
    }
    
    @Test
    public void noTextValue_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "text value is null");
    	buildTitleFromString(NO_TEXT_VALUE);
    }
    
    @Test
    public void validTitle_Valid() throws Exception {
        // No exception
    	buildTitleFromString(VALID_AGENT);
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcAgent buildTitleFromString(String s) 
            throws RecordException {
    	Element element = XmlTestUtils.buildElementFromString(s);
        return new FgdcAgent(element);
    }
}
