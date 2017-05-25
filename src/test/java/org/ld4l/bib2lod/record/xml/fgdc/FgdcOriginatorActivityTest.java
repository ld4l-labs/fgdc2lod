package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;

/**
 * Tests class FgdcOriginatorActivity.
 */
public class FgdcOriginatorActivityTest extends AbstractTestClass {

	private static final String INVALID_NO_CONTENT =
			"<citeinfo />";

	private static final String INVALID_MISSING_ORIGINS =
			"<citeinfo>" +
                "<pubdate>2014</pubdate>" +
                "<title>Public Libraries, Cambridge, Massachusetts, 2014</title>" +
                "<pubinfo>" +
                    "<pubplace>Cambridge, Massachusetts</pubplace>" +
                    "<publish>Cambridge (Mass.). Geographic Information Systems</publish>" +
                "</pubinfo>" +
            "</citeinfo>";

	private static final String VALID_ORIGINATOR =
			"<citeinfo>" +
                "<origin>Cambridge (Mass.). Geographic Information Systems</origin>" +
                "<origin>Cambridge (Mass.). Public Library</origin>" +
                "<pubdate>2014</pubdate>" +
                "<title>Public Libraries, Cambridge, Massachusetts, 2014</title>" +
                "<pubinfo>" +
                    "<pubplace>Cambridge, Massachusetts</pubplace>" +
                    "<publish>Cambridge (Mass.). Geographic Information Systems</publish>" +
                "</pubinfo>" +
            "</citeinfo>";

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void noContent_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "origins value is null");
    	buildOriginatorActivityFromString(INVALID_NO_CONTENT);
    }
    
    @Test
    public void noOrigin_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "origins value is null");
    	buildOriginatorActivityFromString(INVALID_MISSING_ORIGINS);
    }
    
    @Test
    public void activity_Valid() throws Exception {
        // No exception
    	buildOriginatorActivityFromString(VALID_ORIGINATOR);
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcOriginatorActivity buildOriginatorActivityFromString(String s) 
            throws RecordException {
    	Element element = XmlTestUtils.buildElementFromString(s);
    	return new FgdcOriginatorActivity(element);
    }

}
