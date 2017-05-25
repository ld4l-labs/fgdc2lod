package org.ld4l.bib2lod.record.xml.fgdc;

import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;

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
    	expectException(RecordFieldException.class, "A bounding coordinate is null");
    	buildGeometryFromString(NO_VALUE);
    }
    
    @Test
    public void missingCoordinate_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "A bounding coordinate is null");
    	buildGeometryFromString(MISSING_COORDINATE);
    }
    
    @Test
    public void noTextCoordinate_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "A bounding coordinate is empty");
    	buildGeometryFromString(NO_TEXT_COORDINATE);
    }
    
    @Test
    public void geometry_Valid() throws Exception {
    	// No exception
    	buildGeometryFromString(VALID_GEOMETRY);
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcGeometry buildGeometryFromString(String s) 
            throws RecordException {
    	Element element = XmlTestUtils.buildElementFromString(s);
    	return new FgdcGeometry(element);
    }
}
