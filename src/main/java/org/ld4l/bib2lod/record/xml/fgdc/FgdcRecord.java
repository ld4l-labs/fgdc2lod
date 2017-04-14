/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.records.xml.BaseXmlRecord;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Represents a FGDC XML record.
 */
public class FgdcRecord extends BaseXmlRecord {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    private enum Field {
        TITLE("title"),
        BOUNDING("bounding");
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }
    
    private FgdcTitle title;
    // private Fgdc FgdcGeometry bounding;

	/**
	 * Constructor
	 * 
	 * @param element - The top-most FGDC XML element.
	 */
	public FgdcRecord(Element element) {
		super(element);
		
		this.title = buildTitle(element);
		// this.bounding = buildBounding(element);
	}
	
    /*
     * Builds this Record's title from the FGDC input. Only a single title
     * is allowed; others are ignored. Returns null if no title node is found.
     */
	private FgdcTitle buildTitle(Element element) {
        NodeList titleNodes = element.getElementsByTagName(Field.TITLE.tagName);
        if (titleNodes.getLength() == 0) {
            return null;
        }
        // There should be only one leader - ignore any others.
        return new FgdcTitle((Element) titleNodes.item(0));        
    }

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.record.Record#isValid()
	 */
	@Override
	public boolean isValid() {

		if (!hasTitle()) {
			return false;
		}
		
		return true;
	}
    
    private boolean hasTitle() {
        return this.title != null;
    }
    
    public FgdcTitle getTitle() {
    	return this.title;
    }

}
