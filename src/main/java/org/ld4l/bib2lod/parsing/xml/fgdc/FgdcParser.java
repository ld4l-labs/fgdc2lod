/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing.xml.fgdc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.parsing.xml.XmlParser;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.xml.XmlRecord;
import org.w3c.dom.Element;

/**
 * Parses a FGDC XML record.
 */
public class FgdcParser extends XmlParser {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
              
    private static final String RECORD_TAG_NAME = "metadata";   

	public FgdcParser() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.parsing.xml.XmlParser#getRecordTagName()
	 */
	@Override
	protected String getRecordTagName() {
		return RECORD_TAG_NAME;
	}

	@Override
	protected XmlRecord createRecord(Element recordElement) throws RecordException {
		return new FgdcRecord(recordElement);
	}

}
