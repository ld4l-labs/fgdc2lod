package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;

/**
 * Tests class FgdcPublisherActivity.
 */
public class FgdcPublisherActivityTest extends AbstractTestClass {

	private static final String INVALID_NO_CONTENT =
			"<citeinfo />";

	private static final String INVALID_MISSING_AGENT_LOCATION_AND_DATE =
			"<citeinfo>" +
                "<title>Public Libraries, Cambridge, Massachusetts, 2014</title>" +
                "<pubinfo>" +
                "</pubinfo>" +
            "</citeinfo>";

	private static final String VALID_MISSING_DATE =
			"<citeinfo>" +
                "<origin>Cambridge (Mass.). Geographic Information Systems</origin>" +
                "<origin>Cambridge (Mass.). Public Library</origin>" +
//                "<pubdate>2014</pubdate>" +
                "<title>Public Libraries, Cambridge, Massachusetts, 2014</title>" +
                "<pubinfo>" +
                    "<pubplace>Cambridge, Massachusetts</pubplace>" +
                    "<publish>Cambridge (Mass.). Geographic Information Systems</publish>" +
                "</pubinfo>" +
            "</citeinfo>";

	private static final String VALID_MISSING_AGENT_AND_LOCATION =
			"<citeinfo>" +
                "<origin>Cambridge (Mass.). Geographic Information Systems</origin>" +
                "<origin>Cambridge (Mass.). Public Library</origin>" +
                "<pubdate>2014</pubdate>" +
                "<title>Public Libraries, Cambridge, Massachusetts, 2014</title>" +
                "<pubinfo>" +
//                    "<pubplace>Cambridge, Massachusetts</pubplace>" +
//                    "<publish>Cambridge (Mass.). Geographic Information Systems</publish>" +
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
    	expectException(RecordFieldException.class, "some value is null");
    	buildPublisherActivityFromString(INVALID_NO_CONTENT);
    }
    
    @Test
    public void noOrigin_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "some value is null");
    	buildPublisherActivityFromString(INVALID_MISSING_AGENT_LOCATION_AND_DATE);
    }
    
    @Test
    public void noDate_Valid() throws Exception {
    	expectException(RecordFieldException.class, "some value is null");
    	buildPublisherActivityFromString(VALID_MISSING_DATE);
    }
    
    @Test
    public void noAgentAndLocation_Valid() throws Exception {
    	expectException(RecordFieldException.class, "some value is null");
    	buildPublisherActivityFromString(VALID_MISSING_AGENT_AND_LOCATION);
    }
    
    @Test
    public void activity_Valid() throws Exception {
        // No exception
    	buildPublisherActivityFromString(VALID_ORIGINATOR);
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcPublisherActivity buildPublisherActivityFromString(String s) 
            throws RecordException {
    	Element element = XmlTestUtils.buildElementFromString(s);
    	return new FgdcPublisherActivity(element);
    }

}
