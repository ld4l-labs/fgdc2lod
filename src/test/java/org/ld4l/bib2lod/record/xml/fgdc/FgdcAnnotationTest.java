/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class FgdcAnnotationTest extends AbstractTestClass {

	private static final String NO_VALUE = "<descript />";

	private static final String NO_TEXT_VALUE = "<descript></descript>";
      
    private static final String VALID_record = 
            "<abstract>Some text.</abstract>";

	 
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void noValue_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "text value is null");
    	buildFgdcAnnotationFromString(NO_VALUE, FgdcAnnotation.AnnotationType.SUMMARIZING);
    }
    
    @Test
    public void noTextValue_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "text value is null");
    	buildFgdcAnnotationFromString(NO_TEXT_VALUE, FgdcAnnotation.AnnotationType.SUMMARIZING);
    }
    
    @Test
    public void validrecord_Valid() throws Exception {
        // No exception
    	buildFgdcAnnotationFromString(VALID_record, FgdcAnnotation.AnnotationType.SUMMARIZING);
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcAnnotation buildFgdcAnnotationFromString(String s, FgdcAnnotation.AnnotationType type) 
            throws RecordException {
    		
		Element element;
		try {
			element = DocumentBuilderFactory
			        .newInstance()
			        .newDocumentBuilder()
			        .parse(new ByteArrayInputStream(s.getBytes()))
			        .getDocumentElement();
			return new FgdcAnnotation(element, type);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new RecordFieldException(e);
		}
    }
}
