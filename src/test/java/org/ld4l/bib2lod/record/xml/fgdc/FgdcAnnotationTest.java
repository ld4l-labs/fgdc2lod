/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class FgdcAnnotationTest {

	private static final String NO_VALUE = "<descript />";

	private static final String NO_TEXT_VALUE = "<abstract><subfield>Some text.</subfield></abstract>";
      
    private static final String VALID_record = 
            "<abstract>Some text.</abstract>";

	 
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void noValue_Invalid() throws Exception {
    	FgdcAnnotation record = buildFgdcAnnotationFromString(NO_VALUE, FgdcAnnotation.AnnotationType.SUMMARIZING);
        Assert.assertFalse(record.isValid());
    }
    
    @Test
    public void noTextValue_Invalid() throws Exception {
    	FgdcAnnotation record = buildFgdcAnnotationFromString(NO_TEXT_VALUE, FgdcAnnotation.AnnotationType.SUMMARIZING);
        Assert.assertFalse(record.isValid());
    }
    
    @Test
    public void validrecord_Valid() throws Exception {
    	FgdcAnnotation record = buildFgdcAnnotationFromString(VALID_record, FgdcAnnotation.AnnotationType.SUMMARIZING);
        Assert.assertTrue(record.isValid());
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private FgdcAnnotation buildFgdcAnnotationFromString(String s, FgdcAnnotation.AnnotationType type) 
            throws RecordFieldException {
    		
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
