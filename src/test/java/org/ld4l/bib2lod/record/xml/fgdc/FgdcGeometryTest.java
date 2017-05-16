package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class FgdcGeometry.
 */
public class FgdcGeometryTest extends AbstractTestClass {

    private static final String NO_VALUE = "<bounding />";
    
    private static final String MISSING_COORDINATE = 
            "<bounding>"
	            + "<westbc>124</westbc>"
	            + "<eastbc>124</eastbc>"
	            + "<northbc>124</northbc>"
            + "</bounding>";
    
    private static final String NO_TEXT_COORDINATE = 
            "<bounding>"
	            + "<westbc>124</westbc>"
	            + "<eastbc>124</eastbc>"
	            + "<northbc>124</northbc>"
	            + "<southbc></southbc>"
            + "</bounding>";
    
    private static final String VALID_GEOMETRY = 
            "<bounding>"
	            + "<westbc>124</westbc>"
	            + "<eastbc>124</eastbc>"
	            + "<northbc>124</northbc>"
	            + "<southbc>124</southbc>"
            + "</bounding>";

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void noValue_Invalid() throws Exception {
    	FgdcGeometry geometry = buildGeometryFromString(NO_VALUE);
        Assert.assertFalse(geometry.isValid());
    }
    
    @Test
    public void missingCoordinate_Invalid() throws Exception {
    	FgdcGeometry geometry = buildGeometryFromString(MISSING_COORDINATE);
        Assert.assertFalse(geometry.isValid());
    }
    
    @Test
    public void noTextCoordinate_Invalid() throws Exception {
    	FgdcGeometry geometry = buildGeometryFromString(NO_TEXT_COORDINATE);
        Assert.assertFalse(geometry.isValid());
    }
    
    @Test
    public void geometry_Valid() throws Exception {
    	FgdcGeometry geometry = buildGeometryFromString(VALID_GEOMETRY);
        Assert.assertTrue(geometry.isValid());
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcGeometry buildGeometryFromString(String s) 
            throws RecordFieldException {
        return (FgdcGeometry) XmlTestUtils.buildElementFromString(
        		FgdcGeometry.class, s);
    }
}
