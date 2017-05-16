package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;

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
    	FgdcOriginatorActivity activity = buildOriginatorActivityFromString(INVALID_NO_CONTENT);
        Assert.assertFalse(activity.isValid());
    }
    
    @Test
    public void noOrigin_Invalid() throws Exception {
    	FgdcOriginatorActivity activity = buildOriginatorActivityFromString(INVALID_MISSING_ORIGINS);
        Assert.assertFalse(activity.isValid());
    }
    
    @Test
    public void activity_Valid() throws Exception {
    	FgdcOriginatorActivity activity = buildOriginatorActivityFromString(VALID_ORIGINATOR);
        Assert.assertTrue(activity.isValid());
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcOriginatorActivity buildOriginatorActivityFromString(String s) 
            throws RecordFieldException {
        return (FgdcOriginatorActivity) XmlTestUtils.buildElementFromString(
        		FgdcOriginatorActivity.class, s);
    }

}
