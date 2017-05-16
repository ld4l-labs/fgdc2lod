package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;

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
    	FgdcPublisherActivity activity = buildOriginatorActivityFromString(INVALID_NO_CONTENT);
        Assert.assertFalse(activity.isValid());
    }
    
    @Test
    public void noOrigin_Invalid() throws Exception {
    	FgdcPublisherActivity activity = buildOriginatorActivityFromString(INVALID_MISSING_AGENT_LOCATION_AND_DATE);
        Assert.assertFalse(activity.isValid());
    }
    
    @Test
    public void noDate_Valid() throws Exception {
    	FgdcPublisherActivity activity = buildOriginatorActivityFromString(VALID_MISSING_DATE);
        Assert.assertTrue(activity.isValid());
    }
    
    @Test
    public void noAgentAndLocation_Valid() throws Exception {
    	FgdcPublisherActivity activity = buildOriginatorActivityFromString(VALID_MISSING_AGENT_AND_LOCATION);
        Assert.assertTrue(activity.isValid());
    }
    
    @Test
    public void activity_Valid() throws Exception {
    	FgdcPublisherActivity activity = buildOriginatorActivityFromString(VALID_ORIGINATOR);
        Assert.assertTrue(activity.isValid());
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcPublisherActivity buildOriginatorActivityFromString(String s) 
            throws RecordFieldException {
        return (FgdcPublisherActivity) XmlTestUtils.buildElementFromString(
        		FgdcPublisherActivity.class, s);
    }

}
